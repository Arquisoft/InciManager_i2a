package manager;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import domain.Agent;
import manager.incidents.InciValidator;
import manager.incidents.Incident;
import manager.incidents.IncidentDTO;
import manager.producers.KafkaProducer;
import services.AgentsService;
import services.IncidentService;


@Controller
public class IncidentController {

	@Autowired
	private AgentsService agentsServ;

	@Autowired
	private IncidentService incidentServ;
	
	@Autowired
	private KafkaProducer kafkaProducer;
	@Autowired
	private InciValidator inciValidator;
	// @Autowired
	// private IncidentToJson incidentJson;
	// @Autowired
	// private JsonGenerator jsonGen;
	// @Autowired
	// private SerializerProvider serial;
	// @Autowired
	// private JsonFactory jsonFactory;

	@RequestMapping("/incident/add")
	public String landing(Model model) {
		model.addAttribute("incident", new IncidentDTO());
		return "/incident/add";
	}

	@RequestMapping("/send")
	public String send(Model model, @ModelAttribute IncidentDTO incident, HttpSession session) throws IOException {
		Agent agentSession = (Agent) session.getAttribute("user");
		Agent agent = agentsServ.getAgentByName(agentSession.getUsername());
		if (agentSession.getUsername().equals(agent.getUsername()) && agentSession.getPassword().equals(agent.getPassword())) {
			Writer writer = new StringWriter();
			Incident incidentFinal = incident.getIncident();
			incidentFinal.setAgentId(agent.getUserId());
			if(agent.getKind().equals("Sensor")) {
				session.setAttribute("incident", incidentFinal);
				return "/incident/sensorAdd";
			}
			else {
				System.out.println(incidentFinal.toString());
				//jsonGen=jsonFactory.createJsonGenerator(writer); //no sé si funciona
				//incidentJson.serialize(incidentFinal, jsonGen, serial);
				// incidentServ.saveIncident(incidentFinal);
				kafkaProducer.send("incident",incidentFinal.toString() );
			}
		}
		return "data";
	}
		
	
	@RequestMapping("/incident/sensorAdd")
	public String sensorAdd() {
		return "/incident/sensorAdd";
	}
	
	@RequestMapping("/sensorAdd")
	public String sensorAdd(Model model, @RequestParam String emergency, HttpSession session) throws IOException {
		Incident i = (Incident) session.getAttribute("incident");
		if(!emergency.equals("false")){
			i.setEmergency(true);
			System.out.println(i.toString());
			kafkaProducer.send("incident",i.toString() );
			// Writer writer = new StringWriter();
			// jsonGen=jsonFactory.createJsonGenerator(writer); //no sé si funciona
			// incidentJson.serialize(incidentFinal, jsonGen, serial);
			// kafkaProducer.send("incident", writer.toString());
			// incidentServ.saveIncident(incidentFinal);
		}
		return "data";
	}
	
	@RequestMapping("incident/list/{name}")
	public String listIncidents(Model model, Principal principal, @PathVariable String name) {
		//String name = principal.getName(); 
		Agent agent = agentsServ.getAgentByName(name);
		model.addAttribute("incidList", incidentServ.getIncidentsByAgentId(agent.getUserId().toString()));
		return "user/list";
	}

}