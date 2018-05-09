package cucumber.steps;

import cucumber.AbstractSteps;
import cucumber.api.java.en.When;

public class AccessIncidentDetail  extends AbstractSteps {
	@When("^the client calls /incident/list/\"(.+)\" being a id an incident id that he has sent$")
	public void the_client_calls_for_inci_detail(String id) throws Throwable {
		executeGet("http://localhost:8081/incident/list/"+id);
	}

}