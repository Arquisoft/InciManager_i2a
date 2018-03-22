package view_tests;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Jorge.7
 * Test for the AgentsDataController, mainly focused on REST requests
 * Modified by Marcos on 17/02/2018
 */
import dbmanagement.AgentsRepository;
import domain.Agent;
import domain.AgentKind;
import main.Application;
import view.UserNotFoundException;

@SpringBootTest(classes = { Application.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class AgentsDataControllerTest {

	private static final String QUERY = "{\"login\":\"%s\", \"password\":\"%s\""
			+ ", \"kind\":\"%s\"}";

	@Autowired
	private WebApplicationContext context;

	// MockMvc --> Para realizar peticiones y comprobar resultado, usado para
	// respuestas con informacion json.
	private MockMvc mockMvc;

	@Autowired
	private AgentsRepository repo;

	private MockHttpSession session;

	private Agent javier;
	private Agent marcos;
	private Agent alba;

	private String plainPassword;

	@Before
	public void setUp() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

		session = new MockHttpSession();

		// Setting up users
		plainPassword = "pass14753";
		javier = new Agent("Javier", "Aviles", "User3@hola.com", plainPassword,
				"12", AgentKind.PERSON, 1);
		repo.insert(javier);

		plainPassword = "pass14753";
		marcos = new Agent("Marcos", "Oviedo", "User1@hola.com", plainPassword,
				"10", AgentKind.PERSON, 1);
		repo.insert(marcos);

		plainPassword = "pass14753";
		alba = new Agent("Alba", "Gijon", "User2@hola.com", plainPassword, "11",
				AgentKind.PERSON, 1);
		repo.insert(alba);
	}

	@After
	public void tearDown() {
		repo.delete(javier);
		repo.delete(alba);
		repo.delete(marcos);
	}

	@Test
	public void userInsertInformation() throws Exception {
		String payload = String.format(QUERY, javier.getUsername(), plainPassword,
				javier.getKind());
		// We send a POST request to that URI (from http:localhost...)
		MockHttpServletRequestBuilder request = post("/user").session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.content(payload.getBytes());
		boolean thrown = false;
		try {
			mockMvc.perform(request).andDo(print())
					// The state of the response must be OK. (200);
					.andExpect(status().isOk())
					// We can do jsonpaths in order to check 1996
					.andExpect(jsonPath("$.name", is(javier.getUsername())))
					.andExpect(jsonPath("$.location", is(javier.getLocation())))
					.andExpect(jsonPath("$.email", is(javier.getEmail())))
					.andExpect(jsonPath("$.id", is(javier.getUserId())))
					.andExpect(jsonPath("$.kind", is(javier.getKind().toString())))
					.andExpect(
							jsonPath("$.kindCode", is(javier.getKindCode())));
		} catch (Exception e) {
			thrown = true;
		}
		assertFalse(thrown);

		String payload1 = String.format(QUERY, alba.getUsername(), plainPassword,
				alba.getKind());
		// We send a POST request to that URI (from http:localhost...)
		MockHttpServletRequestBuilder request1 = post("/user").session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.content(payload1.getBytes());
		boolean thrown1 = false;
		try {
			mockMvc.perform(request1).andDo(print())
					// The state of the response must be OK. (200);
					.andExpect(status().isOk())
					// We can do jsonpaths in order to check 1996
					.andExpect(jsonPath("$.name", is(alba.getUsername())))
					.andExpect(jsonPath("$.location", is(alba.getLocation())))
					.andExpect(jsonPath("$.email", is(alba.getEmail())))
					.andExpect(jsonPath("$.id", is(alba.getUserId())))
					.andExpect(jsonPath("$.kind", is(alba.getKind().toString())))
					.andExpect(jsonPath("$.kindCode", is(alba.getKindCode())));
		} catch (Exception e) {
			thrown1 = true;
		}
		assertFalse(thrown1);

		String payload2 = String.format(QUERY, marcos.getUsername(), plainPassword,
				marcos.getKind());
		// We send a POST request to that URI (from http:localhost...)
		MockHttpServletRequestBuilder request2 = post("/user").session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.content(payload2.getBytes());
		boolean thrown2 = false;
		try {
			mockMvc.perform(request2).andDo(print())
					// The state of the response must be OK. (200);
					.andExpect(status().isOk())
					// We can do jsonpaths in order to check 1996
					.andExpect(jsonPath("$.name", is(marcos.getUsername())))
					.andExpect(jsonPath("$.location", is(marcos.getLocation())))
					.andExpect(jsonPath("$.email", is(marcos.getEmail())))
					.andExpect(jsonPath("$.id", is(marcos.getUserId())))
					.andExpect(jsonPath("$.kind", is(marcos.getKind().toString())))
					.andExpect(
							jsonPath("$.kindCode", is(marcos.getKindCode())));
		} catch (Exception e) {
			thrown2 = true;
		}
		assertFalse(thrown2);
	}

	@Test
	public void userInsertInformationXML() throws Exception {
		String payload = String.format(
				"<data><login>%s</login><password>%s</password>"
						+ "<kind>%s</kind></data>",
				javier.getUsername(), plainPassword, javier.getKind());
		// POST request with XML content
		MockHttpServletRequestBuilder request = post("/user").session(session)
				.contentType(MediaType.APPLICATION_XML_VALUE)
				.content(payload.getBytes());
		boolean thrown = false;
		try {
			mockMvc.perform(request).andDo(print())
					// The state of the response must be OK. (200);
					.andExpect(status().isOk())
					// We can do jsonpaths in order to check
					.andExpect(jsonPath("$.name", is(javier.getUsername())))
					.andExpect(jsonPath("$.location", is(javier.getLocation())))
					.andExpect(jsonPath("$.email", is(javier.getEmail())))
					.andExpect(jsonPath("$.id", is(javier.getUserId())))
					.andExpect(jsonPath("$.kind", is(javier.getKind().toString())))
					.andExpect(
							jsonPath("$.kindCode", is(javier.getKindCode())));
		} catch (Exception e) {
			thrown = true;
		}
		assertFalse(thrown);
	}

	@Test
	public void userInterfaceInsertInfoCorect() throws Exception {
		MockHttpServletRequestBuilder request = post("/userForm")
				.session(session).param("login", javier.getUsername())
				.param("password", plainPassword)
				.param("kind", javier.getKind().toString());
		boolean thrown = false;
		try {
			mockMvc.perform(request).andExpect(status().isOk());
		} catch (Exception e) {
			thrown = true;
		}
		assertFalse(thrown);

		MockHttpServletRequestBuilder request1 = post("/userForm")
				.session(session).param("login", alba.getUsername())
				.param("password", plainPassword).param("kind", alba.getKind().toString());
		boolean thrown1 = false;
		try {
			mockMvc.perform(request1).andExpect(status().isOk());
		} catch (Exception e) {
			thrown1 = true;
		}
		assertFalse(thrown1);

		MockHttpServletRequestBuilder request2 = post("/userForm")
				.session(session).param("login", marcos.getUsername())
				.param("password", plainPassword)
				.param("kind", marcos.getKind().toString());
		boolean thrown2 = false;
		try {
			mockMvc.perform(request2).andExpect(status().isOk());
		} catch (Exception e) {
			thrown2 = true;
		}
		assertFalse(thrown2);
	}

	@Test
	public void testForNotFound() throws Exception {
		String payload = String.format(QUERY, "Nothing", "Not really",
				"Nothing");
		MockHttpServletRequestBuilder request = post("/user").session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.content(payload.getBytes());
		boolean thrown = false;
		try {
			mockMvc.perform(request).andDo(print())// AndDoPrint it is very
													// usefull
													// to see the http response
													// and
													// see if something went
													// wrong.
					.andExpect(status().isNotFound()); // The state of the
														// response
														// must be OK. (200);
		} catch (UserNotFoundException e) {
			thrown = true;
		}
		assertFalse(thrown);

	}

	/**
	 * Should return a 404 as before
	 */
	@Test
	public void testForIncorrectPassword() throws Exception {
		String payload = String.format(QUERY, javier.getUsername(),
				"Not maria's password", javier.getKind());
		MockHttpServletRequestBuilder request = post("/user").session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.content(payload.getBytes());
		boolean thrown = false;
		try {
			mockMvc.perform(request).andDo(print())
					.andExpect(status().isNotFound());
		} catch (Exception e) {
			thrown = true;
		}
		assertFalse(thrown);
	}

	@Test
	public void testChangePassword() throws Exception {
		MockHttpSession session = new MockHttpSession();
		// We check we have the proper credentials
		MockHttpServletRequestBuilder request = post("/userForm")
				.session(session).param("login", javier.getUsername())
				.param("password", plainPassword)
				.param("kind", javier.getKind().toString());
		mockMvc.perform(request).andExpect(status().isOk());
		// We change it
		request = post("/userChangePassword").session(session)
				.param("password", plainPassword).param("newPassword", "HOLA")
				.param("newPasswordConfirm", "HOLA");
		mockMvc.perform(request).andExpect(status().isOk());

		String payload = String.format(QUERY, javier.getUsername(), "HOLA",
				javier.getKind());
		// We check password has changed
		request = post("/user").session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.content(payload.getBytes());
		boolean thrown = false;
		try {
			mockMvc.perform(request).andDo(print()).andExpect(status().isOk())
					.andExpect(jsonPath("$.name", is(javier.getUsername())))
					.andExpect(jsonPath("$.location", is(javier.getLocation())))
					.andExpect(jsonPath("$.email", is(javier.getEmail())))
					.andExpect(jsonPath("$.id", is(javier.getUserId())))
					.andExpect(jsonPath("$.kind", is(javier.getKind().toString())))
					.andExpect(
							jsonPath("$.kindCode", is(javier.getKindCode())));
		} catch (Exception e) {
			thrown = true;
		}
		assertFalse(thrown);

		request = post("/userChangePassword").session(session)
				.param("password", "HOLA").param("newPassword", plainPassword)
				.param("newPasswordConfirm", plainPassword);
		mockMvc.perform(request).andExpect(status().isOk());
		String payload1 = String.format(QUERY, javier.getUsername(), plainPassword,
				javier.getKind());
		// We check password has changed
		request = post("/user").session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.content(payload1.getBytes());
		boolean thrown1 = false;
		try {
			mockMvc.perform(request).andDo(print()).andExpect(status().isOk())
					.andExpect(jsonPath("$.name", is(javier.getUsername())))
					.andExpect(jsonPath("$.location", is(javier.getLocation())))
					.andExpect(jsonPath("$.email", is(javier.getEmail())))
					.andExpect(jsonPath("$.id", is(javier.getUserId())))
					.andExpect(jsonPath("$.kind", is(javier.getKind().toString())))
					.andExpect(
							jsonPath("$.kindCode", is(javier.getKindCode())));
		} catch (Exception e) {
			thrown1 = true;
		}
		assertFalse(thrown1);
	}

}
