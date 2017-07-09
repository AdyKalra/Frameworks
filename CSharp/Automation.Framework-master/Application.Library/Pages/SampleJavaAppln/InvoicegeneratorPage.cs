using Selenium.Core.Framework.PageInitializers;
using Selenium.Core.Framework.Driver;
using Selenium.Core.Framework.UiControls;
using Selenium.Core.Framework.Utilities;

namespace Application.Library.Pages.SampleJavaAppln
{
    public class InvoiceGeneratorPage : BasePage
    {
        public InvoiceGeneratorPage(UiDriver driver) : base(driver)
        {
        }

        [FindsBy]
        private TextField BookedByTextBox;

        [FindsBy]
        private TextField UsedByTextBox;

        [FindsBy]
        private Button GenerateInvoiceButton;

        public void SetBookedBy(string value)
        {
            BookedByTextBox.SetText(value);
        }

        public void SetUsedBy(string value)
        {
            UsedByTextBox.SetText(value);
        }
    }
}
