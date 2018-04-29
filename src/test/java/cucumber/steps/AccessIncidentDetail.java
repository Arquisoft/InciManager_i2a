package cucumber.steps;

import org.springframework.http.HttpStatus;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import cucumber.AbstractSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AccessIncidentDetail  extends AbstractSteps {
	@When("^the client calls /incident/list/\"(.+)\" being a id an incident id that he has sent$")
	public void the_client_calls_for_inci_detail(String id) throws Throwable {
		executeGet("http://localhost:8081/incident/list/"+id);
	}

}