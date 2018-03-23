package manager;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import manager.incidents.InciValidator;
import manager.incidents.Incident;
import manager.incidents.IncidentDTO;
import manager.producers.KafkaProducer;
import util.IncidentToJson;

@Controller
public class IncidentController {

	@Autowired
	private KafkaProducer kafkaProducer;
	@Autowired
	private InciValidator inciValidator;
	@Autowired
	private IncidentToJson incidentJson;
	@Autowired
	private JsonGenerator jsonGen;
	@Autowired
	private SerializerProvider serial;
	@Autowired
	private JsonFactory jsonFactory;

	@RequestMapping("/")
	public String landing(Model model) {
		model.addAttribute("incident", new IncidentDTO());
		return "/incident/add";
	}
	
	@RequestMapping("/send")
	public String send(Model model, @ModelAttribute IncidentDTO incident) throws IOException {
		Writer writer=new StringWriter();
		Incident incidentFinal = incident.getIncident();
		//incidentFinal.setAgent();
		//pendientes las properties
		jsonGen=jsonFactory.createJsonGenerator(writer); //no sé si funciona
		if (inciValidator.isEmergency(incidentFinal)) {
			incidentJson.serialize(incidentFinal, jsonGen, serial);
			kafkaProducer.send("incident", writer.toString());
		}
		return "data";
	}

}