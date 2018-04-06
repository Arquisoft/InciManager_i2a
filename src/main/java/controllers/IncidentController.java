package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Agent;
import manager.incidents.Incident;
import manager.incidents.IncidentDTO;
import manager.producers.KafkaProducer;
import services.AgentsService;
import services.IncidentService;
import util.SerializerLinker;
import validators.AddInciValidator;

@Controller
public class IncidentController {
	
	private boolean error = false;

	@Autowired
	private AgentsService agentsService;

	@Autowired
	private IncidentService incidentService;

	@Autowired
	private KafkaProducer kafkaProducer;
	
	@Autowired
	private AddInciValidator addValidator;

	@RequestMapping("/incident/add")
	public String landing(Model model, HttpSession session) {
		model.addAttribute("incident", new IncidentDTO());
		model.addAttribute("error",error);
		return "/incident/add";
	}

	@RequestMapping("/send")
	public String send(Model model, @Validated IncidentDTO incident, BindingResult result, @RequestParam String type,
			HttpSession session) throws IOException {
		addValidator.validate(incident, result);
		if (result.hasErrors()) {
				error = true;
				return "redirect:/incident/add";
		}
		error = false;
		Agent agentSession = (Agent) session.getAttribute("user");
		Agent agent = agentsService.getAgentByName(agentSession.getUsername());
		if (agentSession.getUsername().equals(agent.getUsername())
				&& agentSession.getPassword().equals(agent.getPassword())) {
			incident.setType(type);
			Incident incidentFinal = incident.getIncident();
			incidentFinal.setAgentId(agent.getUsername());
			incidentFinal.setKindCode(agent.getKindCode());
			if (agent.getKind().equals("Sensor")) {
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