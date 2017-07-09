using Selenium.Core.Framework.Driver;
using Selenium.Core.Framework.Utilities;

namespace Selenium.Core.Framework.UiControls
{
    public class Link : Element
    {
        public Link(UiDriver driver, How findBy, string findByValue) :
            base(driver, findBy, findByValue)
        {

        }

        public Link()
        {

        }

        public string GetHref()
        {
            return _driver.GetAttributeValue(_findBy, _findByValue, "href");
        }
    }
}
