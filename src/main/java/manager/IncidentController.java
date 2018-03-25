package manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import domain.Agent;
import domain.UserInfo;
import domain.UserInfoAdapter;
import manager.incidents.Incident;
import manager.incidents.IncidentDTO;
import manager.producers.KafkaProducer;
import services.AgentsService;
import services.IncidentService;

@Controller
public class IncidentController {

	@Autowired
	private AgentsService agentsService;

	@Autowired
	private IncidentService incidentService;

	@Autowired
	private KafkaProducer kafkaProducer;
	// @Autowired
	// private IncidentToJson incidentJson;
	// @Autowired
	// private JsonGenerator jsonGen;
	// @Autowired
	// private SerializerProvider serial;
	// @Autowired
	// private JsonFactory jsonFactory;

	@RequestMapping("/incident/add")
	public String landing(Model model, HttpSession session) {
		model.addAttribute("incident", new IncidentDTO());
		return "/incident/add";
	}

	@RequestMapping("/send")
	public String send(Model model, @ModelAttribute IncidentDTO incident, HttpSession session) throws IOException {
		Agent agentSession = (Agent) session.getAttribute("user");
		Agent agent = agentsService.getAgentByName(agentSession.getUsername());

		if (agentSession.getUsername().equals(agent.getUsername())
				&& agentSession.getPassword().equals(agent.getPassword())) {
		//	Writer writer = new StringWriter();
			Incident incidentFinal = incident.getIncident();
			incidentFinal.setAgentId(agent.getUsername());
			if (agent.getKind().equals("Sensor")) {
				session.setAttribute("incident", incidentFinal);
				return "/incident/sensorAdd";
			} else {
				incidentService.saveIncident(incidentFinal);
				kafkaProducer.send("incident", incidentFinal.toString());
			}
		}

		UserInfoAdapter adapter = new UserInfoAdapter(agent);
		UserInfo info = adapter.userToInfo();
		model.addAttribute("name", info.getName());
		model.addAttribute("location", info.getLocation());
		model.addAttribute("email", info.getEmail());
		model.addAttribute("kind", info.getKind());
		model.addAttribute("kindCode", info.getKindCode());
		model.addAttribute("user", agent);
		session.setAttribute("user", agent);

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
			kafkaProducer.send("incident", i.toString());
			// Writer writer = new StringWriter();
			// jsonGen=jsonFactory.createJsonGenerator(writer); //no sé si funciona
			// incidentJson.serialize(incidentFinal, jsonGen, serial);
			// kafkaProducer.send("incident", writer.toString());
		}
		incidentService.saveIncident(i);
		return "redirect:data";
	}

	@RequestMapping("incident/list")
	public String listIncidents(Model model, HttpSession session) {
		Agent agent = (Agent) session.getAttribute("user");
		List<Incident> incidents = incidentService
				.getIncidentsByAgentUsername(agentsService.getAgentByName(agent.getUsername()).getUsername());
		model.addAttribute("incidList", incidents);
		return "incident/list";
	}
	
	@RequestMapping("incident/details/{id}")
	public String incidentDetails(Model model, @PathVariable ObjectId id, HttpSession session) {
		Agent agent = (Agent) session.getAttribute("user");
		List<Incident> incidents = incidentService
				.getIncidentsByAgentUsername(agentsService.getAgentByName(agent.getUsername()).getUsername());
		for(Incident i : incidents) {
			if(i.getId().equals(id))
				model.addAttribute("incident", i);
		}
		
		return "incident/details";
	}

}