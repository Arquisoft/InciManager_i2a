package database_tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dbmanagement.Database;
import dbmanagement.IncidentRepository;
import domain.Agent;
import main.Application;
import manager.incidents.InciState;
import manager.incidents.Incident;
import manager.incidents.LatLng;
import manager.producers.KafkaProducer;
import services.IncidentService;

/**
 * Created by Nicolás on 15/02/2017. Modified by Marcos on 17/02/2018
 */
@SpringBootTest(classes = { Application.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class DatabaseTest {

	@Autowired
	private IncidentRepository inciRepo;
	@Autowired
	private IncidentService inciServ;
	@Autowired
	private KafkaProducer kafka;

	// User to use as reference for test
	private Agent a;
	private Incident incident;

	@Autowired
	private Database dat;

	@Before
	public void setUp() {
		a = new Agent("agent1", "pass-01", "Person");
		a.setUserId("id1");
		a.setKind("Person");
		incident = new Incident();
		incident.setAgent(a);
		incident.setName("Run");
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
		incident.getProperties().put("operator", "Paco");
		dat.saveIncident(incident);
	}

	@After
	public void tearDown() {
		inciRepo.delete(incident);
	}
	
	@Test
	public void testSaveIncident() {
		List<Incident> incids = inciRepo.findAllByAgentId("id1");
		Assert.assertEquals(1,incids.size());
		Assert.assertEquals(incident, incids.get(0));
	}
	
	@Test
	public void testInciService() {
		Assert.assertEquals(inciServ.getIncidentsByAgentId("id0").size(),0);
		Incident i2 = new Incident();
		Agent a2=new Agent();
		a2.setUserId("id0");
		i2.setAgent(a2);
		inciServ.saveIncident(i2);
		Assert.assertEquals(inciServ.getIncidentsByAgentId("id0").size(),1);
		inciRepo.delete(i2);
	}
	
	@Test
	public void kafkaTest() {
		boolean isOkay = true;
		try {
			kafka.send("incidents", "Foo testing");
		}catch(Exception e) {
			isOkay=false;
		}
		Assert.assertTrue(isOkay);
	}
}
