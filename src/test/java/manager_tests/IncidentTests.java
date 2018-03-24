package manager_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import domain.Agent;
import manager.incidents.InciState;
import manager.incidents.Incident;
import manager.incidents.LatLng;

public class IncidentTests {

	private Incident i1;
	private Incident i2;

	@Before
	public void setUp() {
		i1 = new Incident("i1", "System failure", new Agent("alum", "alumnossi", "seguridad"), new ArrayList<String>(),
				new LatLng(20.2, 17.2), InciState.OPEN, new ArrayList<String>());
		i2 = new Incident("i2", "Overheated system", new Agent("alum", "alumnossi", "seguridad"),
				new ArrayList<String>(), new LatLng(20.2, 17.2), InciState.OPEN, new ArrayList<String>());
	}

	@Test
	public void testNameAndDescr() {
		assertEquals(i1.getName(), "i1");
		assertEquals(i2.getName(), "i2");
		assertEquals(i1.getDescription(), "System failure");
		assertEquals(i2.getDescription(), "Overheated system");
		i1.setName("i4");
		assertEquals(i1.getName(), "i4");
		i2.setDescription("Sample");
		assertEquals(i2.getDescription(), "Sample");
	}

	

	@Test
	public void testLocation() {
		assertTrue(i1.getLocation().getLat() == 20.2);
		assertTrue(i2.getLocation().getLng() == 17.2);
		i1.setLocation(new LatLng(0.5, 9.2));
		assertTrue(i1.getLocation().getLat() == 0.5);
		assertTrue(i1.getLocation().getLng() == 9.2);
	}

	@Test
	public void testTagsAndMultimedia() {
		assertTrue(i1.getTags().isEmpty());
		List<String> tags = new ArrayList<String>();
		tags.add("Park");
		tags.add("Skyrim");
		i2.setTags(tags);
		assertTrue(i2.getTags().size() == 2);
		assertEquals(i2.getTags().get(1), "Skyrim");
		assertTrue(i1.getMultimedia().isEmpty());
		List<String> multi = new ArrayList<String>();
		multi.add("Park");
		multi.add("Skyrim");
		i2.setMultimedia(multi);
		assertTrue(i2.getMultimedia().size() == 2);
		assertEquals(i2.getMultimedia().get(1), "Skyrim");
	}

	@Test
	public void testStateAndEmergency() {
		assertEquals(i1.getState(), InciState.OPEN);
		i2.setState(InciState.CLOSED);
		assertEquals(i2.getState(), InciState.CLOSED);
		assertFalse(i1.isEmergency());
		i1.setEmergency(true);
		assertTrue(i1.isEmergency());
	}

	@Test
	public void testProperties() {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("temperature", 20.1);
		properties.put("fire", false);
		i1.setProperties(properties);
		assertEquals(i1.getProperties().get("fire"), false);
		assertEquals(i1.getProperties().get("temperature"), 20.1);
	}

	@Test
	public void testToString() {
		String result = "{name='i1',description='System failure',"
				+ "agent='null',tags='[]',location='GeoCords [lat=20.2, lng=17.2]',state='OPEN',multimedia='[]'}";
		assertEquals(i1.toString(),result);
	}

}
