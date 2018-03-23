package database_tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dbmanagement.Database;
import dbmanagement.AgentsRepository;
import domain.Agent;
import domain.AgentKind;
import domain.UserInfo;
import domain.UserInfoAdapter;
import main.Application;
import util.JasyptEncryptor;

/**
 * Created by Nicolás on 15/02/2017. Modified by Marcos on 17/02/2018
 */
@SpringBootTest(classes = { Application.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class DatabaseTest {

	@Autowired
	private AgentsRepository repo;

	// User to use as reference for test
	private Agent testedUser;
	private Agent testedUser2;

	@Autowired
	private Database dat;

	/*
	 * Para este test se necesita el siguiente documento en la base de datos: {
	 * "_id" : ObjectId("5893a06ace8c8e1b79d8a9a9"), "_class" : "Model.User",
	 * "firstName" : "Maria", "location" : "20N20E", "password" :
	 * "9gvHm9TI57Z9ZW8/tTu9Nk10NDZayLIgKcFT8WdCVXPeY5gF57AFjS/l4nKNY1Jq",
	 * "dateOfBirth" : ISODate("1982-12-27T23:00:00.000Z"), "address" : "Hallo",
	 * "nationality" : "Core", "userId" : "321", "email" : "asd", "kind":
	 * "Person", "kindCode" :1 } }
	 */
	@Before
	public void setUp() {
		testedUser = new Agent("Luis", "10N20E", "LGracia@gmail.com", "Luis123",
				"100", AgentKind.Person, 1);
		repo.insert(testedUser);

		testedUser2 = new Agent("Maria", "20N20E", "asd", "pass14753", "321",
				AgentKind.Person, 1);
		repo.insert(testedUser2);
	}

	@After
	public void tearDown() {
		repo.delete(testedUser);
		repo.delete(testedUser2);
	}

	@Test
	public void testGetAgent() {
		// It should be previously encoded if the DB is given so this may be
		// changed.
		Agent user = dat.getAgent("Luis");
		user.setLocation("USA");
		Assert.assertEquals(user.getLocation(), "USA");
	}

	@Test
	public void testUpdateInfoWithPassword() {
		// It should be previously encoded if the DB is given so this may be
		// changed.
		Agent user = dat.getAgent("Luis");
		user.setPassword("confidencial");
		JasyptEncryptor encryptor = new JasyptEncryptor();
		dat.updateInfo(user);
		Agent userAfter = dat.getAgent("Luis");
		Assert.assertTrue(encryptor.checkPassword("confidencial",
				userAfter.getPassword())); // They should be the same when we
											// introduce the password.
		Assert.assertEquals(user, userAfter); // They should be the same user by
												// the equals.

	}

	@Test
	public void testUpdateInfoAndAdaptation() {
		Agent user = dat.getAgent("Maria");
		Assert.assertEquals("Maria", user.getUsername());
		Assert.assertEquals("20N20E", user.getLocation());
		Assert.assertEquals("321", user.getUserId());
		Assert.assertEquals("asd", user.getEmail());

		UserInfoAdapter userAdapter = new UserInfoAdapter(user);

		UserInfo userInfo = userAdapter.userToInfo();

		Assert.assertEquals(user.getUsername(), userInfo.getName());
		Assert.assertEquals(user.getEmail(), userInfo.getEmail());
		Assert.assertEquals(user.getUserId(), userInfo.getId());

		user.setUsername("Pepa");
		user.setEmail("asd@gmail.com");

		dat.updateInfo(user);
		Agent updatedUser = dat.getAgent("Pepa");
		Assert.assertEquals("Pepa", updatedUser.getUsername());
		Assert.assertEquals("321", updatedUser.getUserId());
		Assert.assertEquals("asd@gmail.com", updatedUser.getEmail());
	}
}
