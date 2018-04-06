package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import domain.Agent;
import domain.AgentInfoAdapter;
import manager.incidents.Incident;
import manager.incidents.IncidentDTO;
import manager.producers.KafkaProducer;
import services.AgentsService;
import services.IncidentService;
import util.SerializerLinker;

@Controller
public class IncidentController {

	@Autowired
	private AgentsService agentsService;

	@Autowired
	private IncidentService incidentService;

	@Autowired
	private KafkaProducer kafkaProducer;

	@RequestMapping("/incident/add")
	public String landing(Model model, HttpSession session) {
		model.addAttribute("incident", new IncidentDTO());
		return "/incident/add";
	}

	@RequestMapping("/send")
	public String send(Model model, @ModelAttribute IncidentDTO incident, HttpSession session) throws IOException {
		Agent agentSession = (Agent) session.getAttribute("user");
		AgentInfoAdapter agentInfo = new AgentInfoAdapter(agentSession);
		
		String postUrl = "http://localhost:8080/checkAgent";
		Gson gson = new Gson();
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity(gson.toJson(agentInfo.userToInfo()));// gson.tojson() converts your pojo to
																					// json
		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);
		
//		Agent agent = agentsService.getAgentByName(agentSession.getUsername());
//		if (agentSession.getUsername().equals(agent.getUsername())
//				&& agentSession.getPassword().equals(agent.getPassword())) {
		if(response.getStatusLine().getStatusCode() == 200) {
			Incident incidentFinal = incident.getIncident();
			incidentFinal.setAgentId(agentSession.getUsername());//habria que coger la info del json que recibimos del agent
			incidentFinal.setKindCode(agentSession.getKindCode());
			if (agentSession.getKind().equals("Sensor")) {
				session.setAttribute("incident", incidentFinal);
				return "/incident/sensorAdd";
			} else {
				incidentService.saveIncident(incidentFinal);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new SerializerLinker());
				String json = objectMapper.writeValueAsString(incidentFinal);
				kafkaProducer.send("incident", json);
			}
		}
		return "redirect:data";
	}

	@RequestMapping("/incident/sensorAdd")
	public String sensorAdd(HttpSession session) {
		return "/incident/sensorAdd";
	}

	@RequestMapping("/sensorAdd")
	public String sensorAdd(Model model, @RequestParam String emergency, HttpSession session) throws IOException {
		Incident i = (Incident) session.getAttribute("incident");
		if (!emergency.equals("false")) {
			i.setEmergency(true);
			System.out.println(i.toString());
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new SerializerLinker());
			String json = objectMapper.writeValueAsString(i);
			kafkaProducer.send("incident", json);
		}
		incidentService.saveIncident(i);
		return "redirect:data";
	}

	@RequestMapping("incident/list")
	public String listIncidents(Model model, HttpSession session) {
		Agent agent = (Agent) session.getAttribute("user");
		List<Incident> incidents = incidentService
				.getIncidentsByAgentId(agentsService.getAgentByName(agent.getUsername()).getUsername());
		model.addAttribute("incidList", incidents);
		return "incident/list";
	}

	@RequestMapping("incident/details/{id}")
	public String incidentDetails(Model model, @PathVariable ObjectId id, HttpSession session) {
		Agent agent = (Agent) session.getAttribute("user");
		List<Incident> incidents = incidentService
				.getIncidentsByAgentId(agentsService.getAgentByName(agent.getUsername()).getUsername());
		for (Incident i : incidents) {
			if (i.getId().equals(id))
				model.addAttribute("incident", i);
		}

		return "incident/details";
	}

}