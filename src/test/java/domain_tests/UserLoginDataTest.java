package domain_tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import domain.AgentKind;
import domain.UserLoginData;

/**
 * Created by Nicolás on 18/02/2017.
 */
public class UserLoginDataTest {

	private UserLoginData test;
	private UserLoginData test2;

	@Before
	public void setUp() {
		test = new UserLoginData();
		test.setLogin("hola1");
		test.setPassword("holaPassword");
		test2 = new UserLoginData("hola2", "passwHola", AgentKind.PERSON);
	}

	@Test
	public void getLogin() throws Exception {
		assertEquals("hola1", test.getLogin());
		assertEquals("hola2", test2.getLogin());
	}

	@Test
	public void getPassword() throws Exception {
		assertEquals("holaPassword", test.getPassword());
		assertEquals("passwHola", test2.getPassword());
	}

	@Test
	public void setLogin() throws Exception {
		test.setLogin("HOLAAAAAAAA");
		assertEquals("HOLAAAAAAAA", test.getLogin());
	}

	@Test
	public void setPassword() throws Exception {
		test.setPassword("PASWOOOOOOOORD");
		assertEquals("PASWOOOOOOOORD", test.getPassword());
	}

	@Test
	public void getKind() throws Exception {
		assertEquals(AgentKind.PERSON, test2.getKind());
	}

	@Test
	public void setKind() throws Exception {
		test2.setKind(AgentKind.SENSOR);
		assertEquals(AgentKind.SENSOR, test2.getKind());
	}
	
	@Test
	public void getKindCodes() throws Exception{
		assertEquals("PERSON", test2.getKindCodes().get(0));
		assertEquals("ENTITY", test2.getKindCodes().get(1));
		assertEquals("SENSOR", test2.getKindCodes().get(2));
	}

}