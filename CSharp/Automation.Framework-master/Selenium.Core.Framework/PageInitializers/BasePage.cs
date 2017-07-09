using Selenium.Core.Framework.Driver;

namespace Selenium.Core.Framework.PageInitializers
{
    public class BasePage
    {
        public BasePage(UiDriver driver)
        {
            PageInitializer.Initialize(this, driver);
        }
    }
}
