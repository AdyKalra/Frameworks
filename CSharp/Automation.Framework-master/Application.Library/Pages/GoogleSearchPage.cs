using Selenium.Core.Framework.PageInitializers;
using Selenium.Core.Framework.Driver;
using Selenium.Core.Framework.UiControls;
using Selenium.Core.Framework.Utilities;

namespace Sapient.Automation.Application.Library.Pages
{
    public class GoogleSearchPage : BasePage
    {
        public GoogleSearchPage(UiDriver driver) : base(driver)
        {
        }

        [FindsBy]
        private TextField SearchTextBox;
        
        [FindsBy]
        private WebElements<Link> SearchResults;

        public void SetSearchText(string searchText)
        {
            SearchTextBox.SetText(searchText);
            SearchTextBox.KeyEnter();
        }

        public void ClickSearchResult(int searchResultIndex)
        {
            var searchResults = SearchResults.GetElements();
            var firstResult = searchResults[searchResultIndex - 1];
            firstResult.Click();
        }

        public void WaitTillSearchResultsAppear()
        {
            SearchResults.WaitTillPresent(300);
        }
    }
}
