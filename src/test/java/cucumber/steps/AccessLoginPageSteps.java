package cucumber.steps;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.hamcrest.MatcherAssert;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import controllers.AgentsController;
import cucumber.AbstractSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AccessLoginPageSteps extends AbstractSteps {
	@Mock
	private AgentsController aController;
	private MockMvc mockMvc;
	

	@When("^the client calls /$")
	public void the_client_calls() throws Throwable {
		MockitoAnnotations.initMocks(this);
		 InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	        viewResolver.setPrefix("/WEB-INF/jsp/view/");
	        viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(aController).setViewResolvers(viewResolver).build();
		
		MockHttpServletRequestBuilder request = get("/");
		result = mockMvc.perform(request).andReturn().getResponse();
		
	}

	@Then("^the client receives status code of (\\d+)$")
	public void the_client_receives_status_code_of(int arg1) throws Throwable {
		MatcherAssert.assertThat(result.getStatus(), equalTo(200));	
	}
}

