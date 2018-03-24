package cucumber.steps;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.cli.Main;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginSteps {
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	@Given("^a list of users:$")
	public void a_list_of_users(List<User> users) throws Throwable {
		for (User u : users) {
			System.out.println("Inserting user..." + u.name + " - " + u.password + " - " + u.kindCode);
		}
	}

	@When("^I login with name \"(.*?)\" and password \"(.*?)\" and kindCode (\\d+)$")
	public void i_login_with_name_and_password_and_kindCode(String arg1, String arg2, int arg3) throws Throwable {
		System.out.println("Login with values..." + arg1 + " - " + arg2+" - "+arg3);
	}

	@Then("^I receive a welcome message$")
	public void i_receive_a_welcome_message() throws Throwable {
		System.out.println("Welcome value received");
	}

	public static class User {
		private String name;
		private String password;
		private int kindCode;
	}

}
