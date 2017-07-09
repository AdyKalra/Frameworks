using Sapient.Automation.Application.Library.Pages;
using TechTalk.SpecFlow;
using Microsoft.Practices.Unity;
using System.Configuration;
using NUnit.Framework;
using Selenium.Core.Framework.Driver;

namespace Application.Tests.StepDefinitions
{
    [Binding]
    public sealed class SampleTestStepDefinitions
    {
        GoogleSearchPage _googleSearchPage;
        SapientGlobalMarketsPage _sapientGMpage;
        UiDriver driver;

        public SampleTestStepDefinitions()
        {
            var unity = TestBase.Unity;
            driver = TestBase.Driver;
            _googleSearchPage = unity.Resolve<GoogleSearchPage>();
            _sapientGMpage = unity.Resolve<SapientGlobalMarketsPage>();
        }

        [Given(@"I Launch the Google Search Page")]
        public void GivenILaunchTheGoogleSearchPage()
        {
            var url = ConfigurationManager.AppSettings["GoogleUrl"];
            driver.NavigateToUrl(url);
            driver.Maximize();
        }

        [Given(@"I search for (.*)")]
        public void GivenISearchFor(string searchText)
        {
            _googleSearchPage.SetSearchText(searchText);
            _googleSearchPage.WaitTillSearchResultsAppear();
        }

        [When(@"I Click the (.*) Search Result")]
        public void WhenIClickTheSearchResult(int index)
        {
            _googleSearchPage.ClickSearchResult(index);
        }

        [Then(@"Validate Sapient Global Markets Page is opened")]
        public void ThenValidateSapientGlobalMarketsPageIsOpened()
        {
            driver.WaitTillUrlContains("sapientglobalmarkets.com", 300);
            bool result = driver.GetUrl().Contains("sapientglobalmarkets.com");
            Assert.IsTrue(result);
        }

        [When(@"I Click the Contact Menu")]
        public void WhenIClickTheContactMenu()
        {
            _sapientGMpage.WaitTillContactMenuAppears();
            _sapientGMpage.ClickContactMenu();
        }

        [When(@"I Scroll down")]
        public void WhenIScrollDown()
        {
            driver.Scroll();
        }

        [When(@"I Select (.*) Location")]
        public void WhenISelectLocation(string location)
        {
            _sapientGMpage.WaitTillLocationDropdownIsVisible();
            _sapientGMpage.SelectLocation(location);
        }

        [Then(@"Validate Phone Number is (.*)")]
        public void ThenValidatePhoneNumberIs(string phoneNumber)
        {
            var phoneNo = _sapientGMpage.GetPhoneNumber();
            Assert.AreEqual(phoneNumber, phoneNo);
        }

        [Then(@"Validate Fax Number is (.*)")]
        public void ThenValidateFaxNumberIs(string faxNumber)
        {
            var faxNo = _sapientGMpage.GetFax();
            Assert.AreEqual(faxNumber, faxNo);
        }

        [Then(@"Validate Email is (.*)")]
        public void ThenValidateEmailIs(string emailInfo)
        {
            var email = _sapientGMpage.GetEmail();
            Assert.AreEqual(emailInfo, email);
        }

    }
}
