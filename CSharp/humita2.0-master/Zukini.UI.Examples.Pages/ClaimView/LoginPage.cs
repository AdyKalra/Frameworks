using Coypu;
using Zukini.UI.Pages;

namespace Zukini.UI.Examples.Pages.ClaimView
{
    public class LoginPage : BasePage
    {
        readonly BrowserSession _browser;

        public LoginPage(SessionConfiguration sessionConfiguration, BrowserSession browser) : base(browser)
        {
           _browser = browser;
           _browser.WaitForNavigation(sessionConfiguration, PageSettings.ClaimViewUrl);
        }

        private string SpinnerLocator = ".//div[@class='spinner-image']";
        public ElementScope EmailTextBox => _browser.FindXPath(".//input[@name='Email']");
        public ElementScope PasswordTextBox => _browser.FindXPath(".//input[@name='Password']");
        public ElementScope LogginButton => _browser.FindXPath(".//input[@class='btn access-btn-submit access-margin-right-4']");
        public ElementScope SummaryValidationError => _browser.FindXPath(".//div[@class='col-md-9 col-lg-8 summary-validation-error ng-binding']");
        public ElementScope Spinner => _browser.FindXPath(SpinnerLocator);
        public string GetSummaryValidationErrorMsg => (string)_browser.ExecuteScript("return arguments[0].innerHTML;", SummaryValidationError);

        public void ClickLoginButton()
        {
            LogginButton.Click();
            WaitForSpinnerDisapear();
        }

        public void FillEmailField(string email)
        {
            EmailTextBox.FillInWith(email);
        }

        public void FillPasswordField(string password)
        {
            PasswordTextBox.FillInWith(password);
        }

        public void WaitForSpinnerDisapear()
        {
            Browser.WaitUntil(() => _browser.FindXPath(SpinnerLocator).Missing(), "Spinner exists");
        }
    }
}
