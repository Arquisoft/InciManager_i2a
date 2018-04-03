package manager_tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Agent;
import manager.incidents.InciState;
import manager.incidents.Incident;
import manager.incidents.LatLng;
import util.SerializerLinker;

public class IncidentToJSONTests {

	private String incidentJson = "{\"name\":\"Prueba\",\"description\":\"Sunday rain\","
			+ "\"agentId\":\"AA\",\"kindCode\":\"1\",\"tags\":[\"heat\",\"failure\"],\"location\""
			+ ":{\"lat\":\"15.6\",\"lng\":\"125.0\"},\"state\":\"INPROCESS\",\"multimedia\""
			+ ":[\"heat.jpg\",\"fire!.jpg\"],\"properties\":{\"type\":\"Bombero\"}}";

	@Test
	public void serialize() throws Exception {
		Agent a = new Agent("agent1", "pass-01", "Person");
		a.setUserId("AA");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new SerializerLinker());

		Incident incident = new Incident();
		incident.setAgent(a);
		incident.setName("Prueba");
		incident.setKindCode(1);
		incident.setDescription("Sunday rain");
		incident.setLocation(new LatLng(15.6, 125));
		incident.setTags(new ArrayList<String>());
		incident.setState(InciState.INPROCESS);
		incident.addTag("heat");
		incident.addTag("failure");
		incident.setMultimedia(new ArrayList<String>());
		incident.addFile("heat.jpg");
		incident.addFile("fire!.jpg");
		incident.setProperties(new HashMap<String, Object>());
		incident.getProperties().put("type", "Bombero");

		String json = objectMapper.writeValueAsString(incident);
		assertEquals(incidentJson, json);
	}
	
	

}
