package domain_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import domain.Agent;
import domain.AgentInfo;
import domain.AgentInfoAdapter;

/**
 * Created by Nicolás on 18/02/2017. Modified by Marcos on 17/02/2018
 */
public class UserAdapterTest {

	private Agent user1;
	private Agent user2;

	@Before
	public void setUp() {
		user1 = new Agent("User1", "Oviedo", "User1@hola.com", "user1Password",
				"10", "Person", 1);
		user2 = new Agent("User2", "Gijon", "User2@hola.com", "user2Password",
				"11", "Person", 1);
	}

	@Test
	public void testUserInfo() {
		AgentInfoAdapter adapter = new AgentInfoAdapter(user1);
		AgentInfo info = adapter.userToInfo();
		AgentInfoAdapter adapter1 = new AgentInfoAdapter(user2);
		AgentInfo info1 = adapter1.userToInfo();
		AgentInfo info2 = new AgentInfo();
		info2.setId("i2");
		assertEquals(info2.getId().hashCode(),info2.hashCode());
		info.setEmail("a@b.com");
		assertEquals(info.getEmail(), "a@b.com");
		info.setKind("Sensor");
		assertEquals(info.getKind(), "Sensor");
		info.setKindCode(2);
		assertEquals(info.getKindCode(), 2);
		info.setLocation("Aviles");
		assertEquals(info.getLocation(), "Aviles");
		info.setName("Nuevo");
		assertEquals(info.getName(), "Nuevo");
		String output = "{name=\'Nuevo\',location=\'Aviles\',email=\'a@b.com\',"
				+ "id=\'10\',kind=\'Sensor\',kindCode=\'2\'}";
		assertEquals(output, info.toString());

		info1.setEmail("a@b.com");
		info1.setKind("Sensor");
		info1.setKindCode(2);
		info1.setLocation("Aviles");
		info1.setName("Nuevo");
		assertFalse(info1.equals(info));
		info1.setId("10");
		assertTrue(info1.equals(info));
	}

	@Test
	public void testAdapter() {
		AgentInfoAdapter adapter = new AgentInfoAdapter(user1);
		AgentInfo info = adapter.userToInfo();
		assertEquals(info.getName(), user1.getUsername());
		assertEquals(info.getEmail(), user1.getEmail());
		assertEquals(info.getKind(), user1.getKind().toString());
		assertEquals(info.getKindCode(), user1.getKindCode());
		assertEquals(info.getLocation(), user1.getLocation());
		assertEquals(info.getId(), user1.getUserId());

		AgentInfoAdapter adapter1 = new AgentInfoAdapter(user2);
		AgentInfo info1 = adapter1.userToInfo();
		assertEquals(info1.getName(), user2.getUsername());
		assertEquals(info1.getEmail(), user2.getEmail());
		assertEquals(info1.getKind(), user2.getKind().toString());
		assertEquals(info1.getKindCode(), user2.getKindCode());
		assertEquals(info1.getLocation(), user2.getLocation());
		assertEquals(info1.getId(), user2.getUserId());
	}

}
