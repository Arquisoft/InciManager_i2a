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
	view_tests.AgentsDataControllerTest.class
})

public class AllTests {

}