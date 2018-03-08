package domain_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import domain.User;
import domain.UserInfo;
import domain.UserInfoAdapter;

/**
 * Created by Nicolás on 18/02/2017. Modified by Marcos on 17/02/2018
 */
public class UserAdapterTest {

	private User user1;
	private User user2;

	@Before
	public void setUp() {
		user1 = new User("User1", "Oviedo", "User1@hola.com", "user1Password",
				"10", "Person", 1);
		user2 = new User("User2", "Gijon", "User2@hola.com", "user2Password",
				"11", "Person", 1);
	}

	@Test
	public void testUserInfo() {
		UserInfoAdapter adapter = new UserInfoAdapter(user1);
		UserInfo info = adapter.userToInfo();
		UserInfoAdapter adapter1 = new UserInfoAdapter(user2);
		UserInfo info1 = adapter1.userToInfo();
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
		UserInfoAdapter adapter = new UserInfoAdapter(user1);
		UserInfo info = adapter.userToInfo();
		assertEquals(info.getName(), user1.getName());
		assertEquals(info.getEmail(), user1.getEmail());
		assertEquals(info.getKind(), user1.getKind());
		assertEquals(info.getKindCode(), user1.getKindCode());
		assertEquals(info.getLocation(), user1.getLocation());
		assertEquals(info.getId(), user1.getUserId());

		UserInfoAdapter adapter1 = new UserInfoAdapter(user2);
		UserInfo info1 = adapter1.userToInfo();
		assertEquals(info1.getName(), user2.getName());
		assertEquals(info1.getEmail(), user2.getEmail());
		assertEquals(info1.getKind(), user2.getKind());
		assertEquals(info1.getKindCode(), user2.getKindCode());
		assertEquals(info1.getLocation(), user2.getLocation());
		assertEquals(info1.getId(), user2.getUserId());
	}

}
