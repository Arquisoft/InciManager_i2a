package all_tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	database_tests.DatabaseTest.class,
	domain_tests.UserAdapterTest.class,
	domain_tests.UserLoginDataTest.class,
	domain_tests.UserTest.class,
	manager_tests.IncidentDTOTests.class,
	manager_tests.IncidentTests.class,
	manager_tests.IncidentToJSONTests.class,
	cucumber.CucumberTest.class
})

public class AllTests {

}