package steps;

import com.project.pages.MainPageMailPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by User on 15.02.2017.
 */
public class MainPageMailPageSteps extends BaseSteps {

    @Autowired
    private MainPageMailPage mainPageMailPage;

    @Value("${user.email}")
    private String user;

    @Value("${user.password}")
    private String password;


    @Given("^I am on the mail page$")
    public void iAmOnInboxPage() {
        mainPageMailPage.open();
    }

    @When("^I login as \"([^\"]*)\" user from mail page$")
    public void iLoginAsUserFromMailPage(String username) {
        switch (username.toLowerCase()) {
            case "yatestexample@yandex.ru":
                mainPageMailPage.login(user, password);
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
