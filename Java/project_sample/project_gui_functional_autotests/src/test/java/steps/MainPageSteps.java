package steps;

import com.project.pages.MainPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import steps.BaseSteps;

/**
 * Created by User on 15.02.2017.
 */
public class MainPageSteps extends BaseSteps {

    @Autowired
    private MainPage mainPage;
    @Value("${user.email}")
    private String user;
    @Value("${user.password}")
    private String password;



    @Given("^I am on the main page$")
    public void iAmOnTheMainPage() {
        mainPage.open();
    }


    @When("^I login as \"([^\"]*)\" user from main page$")
    public void iLoginAsUserFromMainPage(String username) {
        switch (username.toLowerCase()) {
            case "yatestexample@yandex.ru":
                mainPage.login(user, password);
                break;
            default:
                throw new IllegalArgumentException("Cannot find user: " + username);
        }
        try {
            Thread.sleep(driverUtils.sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driverUtils.waitForPageLoad();
        driverUtils.waitForProcess();
    }
}
