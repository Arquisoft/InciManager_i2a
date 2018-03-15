package manager_tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import manager.incidents.InciState;
import manager.incidents.Incident;

public class IncidentTests {
	
	private Incident i1;
	private Incident i2;

	@Before
	public void setUp() {
		i1 = new Incident("i1", "System failure", "alumnossi","seguridad", "failure,fatal",
				"31N28W", InciState.OPEN);
		i2 = new Incident("i2", "Overheated system", "alumnossi","seguridad", "heat,minor",
				"31S28E", InciState.OPEN);
	}
	
	@Test
	public void testNameAndDescr(){
		assertEquals(i1.getName(),"i1");
		assertEquals(i2.getName(),"i2");
		assertEquals(i1.getDescription(),"System failure");
		assertEquals(i2.getDescription(),"Overheated system");
		i1.setName("i4");
		assertEquals(i1.getName(),"i4");
		i2.setDescription("Sample");
		assertEquals(i2.getDescription(),"Sample");
	}
	
	@Test
	public void testAgent(){
		assertEquals(i1.getUsername(),"alumnossi");
		assertEquals(i2.getUsername(),"alumnossi");
		assertEquals(i1.getPassword(),"seguridad");
		assertEquals(i2.getPassword(),"seguridad");
		i1.setUsername("i4");
		assertEquals(i1.getUsername(),"i4");
		i2.setPassword("Sample");
		assertEquals(i2.getPassword(),"Sample");
	}
	
	@Test
	public void testLocationTagsAndState(){
		assertEquals(i1.getLocation(),"31N28W");
		assertEquals(i2.getLocation(),"31S28E");
		assertEquals(i1.getTags(),"failure,fatal");
		assertEquals(i2.getState(),InciState.OPEN);
		i1.setLocation("0N0W");
		assertEquals(i1.getLocation(),"0N0W");
		i1.setTags("linkin,park");
		assertEquals(i1.getTags(),"linkin,park");
		i2.setState(InciState.INPROCESS);
		assertEquals(i2.getState(),InciState.INPROCESS);
	}

}
