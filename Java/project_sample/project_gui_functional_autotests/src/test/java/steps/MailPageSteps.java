package steps;

import com.project.pages.MailPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by User on 15.02.2017.
 */
public class MailPageSteps extends BaseSteps{

    @Autowired
    private MailPage mailPage;

    @When("^I open letter with title \"([^\"]*)\"$")
    public void iOpenLetterWithTitle(String letterTitle) {
        mailPage.openLetter(letterTitle);
        stepContainer.store(stepContainer.TITLE, letterTitle);

        try {
            Thread.sleep(driverUtils.sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driverUtils.waitForPageLoad();
        driverUtils.waitForProcess();
    }

    @Then("^Letter title is displayed and valid$")
    public void letterTitleIsDisplayed() {
        assertThat(driverUtils.isElementDisplayed(mailPage.letterViewBlock.letterTitle))
                .as("Letter title is displayed")
                .isTrue();
         assertThat(mailPage.letterViewBlock.letterTitle.getText().trim())
                .as("Letter title is valid")
                .isEqualTo(stepContainer.getValueAsString(stepContainer.TITLE));
    }

    @And("^Letter body is displayed$")
    public void letterBodyIsDisplayed() {
        assertThat(driverUtils.isElementDisplayed(mailPage.letterViewBlock.letterBody))
                .as("Letter Body is displayed")
                .isTrue();
    }

    @Then("^Mail page logo is displayed$")
    public void mailPageLogoIsDisplayed() {
        assertThat(driverUtils.isElementDisplayed(mailPage.mailLogo))
                .as("Mail logo is displayed")
                .isTrue();
    }

    @When("^Fail$")
    public void fail() {
        Assert.fail();
    }

    @And("^Sender mail is \"([^\"]*)\"$")
    public void senderMailIs(String email) {
            assertThat(mailPage.letterViewBlock.mailSender.getText().trim())
                    .as("Senders mail is valid")
                    .isEqualTo(email);
    }
}
