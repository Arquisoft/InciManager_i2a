package manager_tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import domain.User;
import manager.incidents.InciState;
import manager.incidents.Incident;
import manager.incidents.LatLng;

public class IncidentTests {

	private Incident i1;
	private Incident i2;

	@Before
	public void setUp() {
		i1 = new Incident("i1", "System failure", new User("alum", "alumnossi", "seguridad"), new ArrayList<String>(),
				new LatLng(20.2, 17.2), InciState.OPEN, new ArrayList<String>());
		i2 = new Incident("i2", "Overheated system", new User("alum", "alumnossi", "seguridad"),  new ArrayList<String>(),
				new LatLng(20.2, 17.2), InciState.OPEN, new ArrayList<String>());
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
	public void testAgent() {
		assertEquals(i1.getAgent().getEmail(), "alumnossi");
		assertEquals(i2.getAgent().getEmail(), "alumnossi");
		i1.getAgent().setEmail("i4");
		assertEquals(i1.getAgent().getEmail(), "i4");
	}



}
