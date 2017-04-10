import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.MDC;

/**
 * Created by User on 15.02.2017.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        plugin = {
                "com.github.kirlionik.cucumberallure.AllureReporter",
                "pretty", "json:target/Cucumber.json",
                "html:target/cucumber-html-report"
        }
        ,tags = {"~@Ignore", "~@InDev"}
)
public class RunTests {

    @BeforeClass
    public static void beforeClass() {
        MDC.put("runnerId", RunTests.class.getName());
    }

    @AfterClass
    public static void afterClass() {

    }

}
