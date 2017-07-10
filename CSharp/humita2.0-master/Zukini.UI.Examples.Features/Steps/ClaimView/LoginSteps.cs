using BoDi;
using Coypu;
using FluentAssertions;
using TechTalk.SpecFlow;
using TechTalk.SpecFlow.Assist;
using Zukini.UI.Examples.Pages.ClaimView;
using Zukini.UI.Steps;

namespace Zukini.UI.Examples.Features.Steps.ClaimView
{

    [Binding]
    public class LoginSteps: UiSteps
         {
        private readonly SessionConfiguration _sessionConfiguration;
        private LoginPage _loginPage;

        public LoginSteps(IObjectContainer objectContainer, SessionConfiguration sessionConfiguration)
            : base(objectContainer)
        {
            _sessionConfiguration = sessionConfiguration;
        }
        [Given(@"I am at the login page")]
        public void GivenIAmAtTheLoginPage()
        {
            _loginPage = new LoginPage(_sessionConfiguration, Browser);
        }
        
        [When(@"I fill in the following form")]
        public void WhenIFillInTheFollowingForm(Table table)
        {
            dynamic instance = table.CreateDynamicInstance();
            _loginPage.FillEmailField(instance.Email);
            _loginPage.FillPasswordField(instance.Password);
        }
        
        [When(@"I click the login button")]
        public void WhenIClickTheLoginButton()
        {
            _loginPage.ClickLoginButton();
        }
        
        [Then(@"I should be at the Verification Page")]
        public void ThenIShouldBeAtTheVerificationPage()
        {
            var verification = new VerificationPage(Browser);
            verification.ValidateGenerateCodeButtonText();
        }
        
        [Then(@"I should see ""(.*)""")]
        public void ThenIShouldSee(string p0)
        {
            _loginPage.GetSummaryValidationErrorMsg.Should().Contain(p0, "esto falla por... ");
        }
    }
}
