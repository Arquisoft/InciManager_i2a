package domain_tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import domain.User;
import util.JasyptEncryptor;

/**
 * Created by Damian on 15/02/2017. Modified by Marcos on 17/02/2018
 */
public class UserTest {

	private User marcos;
	private User alba;
	private User javier;

	@Before
	public void setUp() {
		marcos = new User("Marcos", "Oviedo", "User1@hola.com", "user1Password",
				"10", "Person", 1);
		alba = new User("Alba", "Gijon", "User2@hola.com", "user2Password",
				"11", "Person", 1);
		javier = new User("Javier", "Aviles", "User3@hola.com", "user3Password",
				"12", "Person", 1);
	}

	@Test
	public void firstNameTest() {
		Assert.assertEquals("Marcos", marcos.getName());
		Assert.assertEquals("Alba", alba.getName());
		Assert.assertEquals("Javier", javier.getName());

		marcos.setName("Antonio");
		Assert.assertEquals("Antonio", marcos.getName());

		alba.setName("Pepe");
		Assert.assertEquals("Pepe", alba.getName());

		javier.setName("Roberto");
		Assert.assertEquals("Roberto", javier.getName());
	}

	@Test
	public void locationTest() {
		Assert.assertEquals("Oviedo", marcos.getLocation());
		Assert.assertEquals("Gijon", alba.getLocation());
		Assert.assertEquals("Aviles", javier.getLocation());

		marcos.setLocation("Madrid");
		Assert.assertEquals("Madrid", marcos.getLocation());
	}

	@Test
	public void kindTest() {
		Assert.assertEquals("Person", marcos.getKind());
		Assert.assertEquals("Person", alba.getKind());
		Assert.assertEquals("Person", javier.getKind());

		marcos.setKind("Sensor");
		Assert.assertEquals("Sensor", marcos.getKind());
	}

	@Test
	public void kindCodeTest() {
		Assert.assertEquals(1, marcos.getKindCode());
		Assert.assertEquals(1, alba.getKindCode());
		Assert.assertEquals(1, javier.getKindCode());

		marcos.setKindCode(2);
		Assert.assertEquals(2, marcos.getKindCode());
	}

	@Test
	public void emailTest() {

		marcos.setEmail(javier.getEmail());
		Assert.assertEquals("User3@hola.com", marcos.getEmail());

		alba.setEmail("pepe@pepemail.com");
		Assert.assertEquals("pepe@pepemail.com", alba.getEmail());

		javier.setEmail(alba.getEmail());
		Assert.assertEquals("pepe@pepemail.com", javier.getEmail());
	}

	@Test
	public void passwordTest() {

		JasyptEncryptor encryptor = new JasyptEncryptor();

		marcos.setPassword("1234");

		Assert.assertTrue(
				encryptor.checkPassword("1234", marcos.getPassword()));

		alba.setPassword("abcd");
		Assert.assertTrue(encryptor.checkPassword("abcd", alba.getPassword()));

		javier.setPassword("yay");
		Assert.assertTrue(encryptor.checkPassword("yay", javier.getPassword()));
	}

	@Test
	public void nifTest() {

		Assert.assertEquals("10", marcos.getUserId());
		System.out.println(marcos.toString());
		Assert.assertEquals("11", alba.getUserId());

		Assert.assertEquals("12", javier.getUserId());

		marcos.setUserId("13");
		Assert.assertEquals("13", marcos.getUserId());
	}

	@Test
	public void toStringTest() {
		String output = "{name=\'Marcos\',location=\'Oviedo\',email=\'User1@hola.com\',id=\'10\',kind=\'Person\'"
				+ ",kindCode=\'1\'}";
		Assert.assertEquals(output, marcos.toString());
	}

}
