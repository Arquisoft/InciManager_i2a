package cucumber.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import org.springframework.http.HttpStatus;

import cucumber.AbstractSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AccessLoginPageSteps extends AbstractSteps {
	@When("^the client calls /$")
	public void the_client_calls() throws Throwable {
		executeGet("http://localhost:8070");
	}

	@Then("^the client receives status code of (\\d+)$")
	public void the_client_receives_status_code_of(int arg1) throws Throwable {
		final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : "+ latestResponse.getBody(), currentStatusCode.value(),is(arg1));
	}

	@Then("^the client receives the string \"(.*?)\"$")
	public void the_client_receives_the_string(String arg1) throws Throwable {
		assertThat(latestResponse.getBody(),containsString(arg1)) ;
	}
}
