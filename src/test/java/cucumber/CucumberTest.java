package cucumber;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features",
			monochrome = true,
			dryRun = false,
			glue = "cucumber.steps" )
public class CucumberTest {
}