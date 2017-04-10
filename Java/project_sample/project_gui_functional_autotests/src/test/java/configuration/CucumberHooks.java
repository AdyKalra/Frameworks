package configuration;

import com.project.pages.MailPage;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import steps.BaseSteps;

/**
 * Created by User on 15.02.2017.
 */
public class CucumberHooks extends BaseSteps {

    @Autowired
    private MailPage mailPage;

    @After
    public void afterFailed(Scenario scenario) {
        if (scenario.isFailed()) {
            allureAttachments.attachCurrentUrl();
            allureAttachments.attachScreenshot();
        }
    }

    @Before
    public void logInfo(Scenario scenario) {
        MDC.put("scenarioName", scenario.getName());
    }


    @After("@LogoutFromMailPageHeaderAfter")
    public void logoutAfter() {
        mailPage.header.username.click();
        mailPage.header.exitButton.click();
    }
}
