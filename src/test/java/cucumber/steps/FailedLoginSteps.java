package cucumber.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.util.List;

import cucumber.AbstractSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import domain.Agent;

public class FailedLoginSteps extends AbstractSteps{
	
	@Given("^a list of users:$")
	public void a_list_of_users(List<Agent> arg1) throws Throwable {
		for (Agent u: arg1) {
		      System.out.println("Inserting user..." + u.getUsername() + " - " + u.getPassword());
		}
	}

	@When("^I login with name \"([^\"]*)\" and password \"([^\"]*)\" and kindCode (\\d+)$")
	public void i_login_with_name_and_password_and_kindCode(String arg1, String arg2, int arg3) throws Throwable {
		executeGet("http://localhost:8070");
		//POST FAILURE
		//executePost("http://localhost:8070,arg1,arg2,arg3);
	}

	@Then("^I go back to the login page$")
	public void i_go_back_to_the_login_page() throws Throwable {
		assertThat(latestResponse.getBody(),containsString("Login")) ;
	}

}
