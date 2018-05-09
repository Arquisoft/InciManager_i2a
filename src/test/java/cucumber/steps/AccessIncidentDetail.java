package cucumber.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.hamcrest.MatcherAssert;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import controllers.IncidentController;
import cucumber.AbstractSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import domain.AgentInfo;

public class AccessIncidentDetail extends AbstractSteps {
	@Mock
	private IncidentController iController;
	private MockMvc mockMvc;
	
	private MockHttpSession session;
	
	@When("^the client calls /incident/details/\"(.+)\" being a id an incident id that he has sent$")
	public void the_client_calls_for_inci_detail(String id) throws Throwable {
		MockitoAnnotations.initMocks(this);
		 InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	        viewResolver.setPrefix("/WEB-INF/jsp/view/");
	        viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(iController).setViewResolvers(viewResolver).build();
		AgentInfo agentSession = new AgentInfo( "pepe", "10", "@", "555555555A", "1", 1);
		session = new MockHttpSession();
		session.setAttribute("agentInfo", agentSession);
		MockHttpServletRequestBuilder request = get("http://localhost:8081/incident/details/{id}", id);
		result = mockMvc.perform(request).andReturn().getResponse();
		MatcherAssert.assertThat(result.getStatus(), equalTo(200));
		
		
	}
	
	@Then("^the client receives status code of (\\d+) when accessing details page$")
	public void the_client_receives_status_code_of(int arg1) throws Throwable {
		MatcherAssert.assertThat(result.getStatus(), equalTo(arg1));
	}
	
	

}