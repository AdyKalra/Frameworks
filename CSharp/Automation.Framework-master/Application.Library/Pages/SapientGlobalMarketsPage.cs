using Selenium.Core.Framework.PageInitializers;
using Selenium.Core.Framework.Driver;
using Selenium.Core.Framework.UiControls;
using Selenium.Core.Framework.Utilities;

namespace Sapient.Automation.Application.Library.Pages
{
    public class SapientGlobalMarketsPage : BasePage
    {
        public SapientGlobalMarketsPage(UiDriver driver) : base(driver)
        {
        }

        [FindsBy]
        private Element ContactMenu;

        [FindsBy]
        private Element PhoneNumber;

        [FindsBy]
        private Element Email;

        [FindsBy]
        private Element Fax;

        [FindsBy]
        private Dropdown LocationDropdown;

        public void ClickContactMenu()
        {
            ContactMenu.Click();
        }

        public string GetPhoneNumber()
        {
            return PhoneNumber.GetTextAttribute();
        }

        public string GetEmail()
        {
            return Email.GetTextAttribute();
        }

        public string GetFax()
        {
            return Fax.GetTextAttribute();
        }

        public void SelectLocation(string location)
        {
            LocationDropdown.SelectByText(location);
        }

        public void WaitTillContactMenuAppears()
        {
            ContactMenu.WaitTillClickable();
        }

        public void WaitTillLocationDropdownIsVisible()
        {
            LocationDropdown.WaitTillVisible();
        }
    }
}
