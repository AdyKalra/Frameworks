using Selenium.Core.Framework.Driver;
using Selenium.Core.Framework.Utilities;

namespace Selenium.Core.Framework.UiControls
{
    public class Element
    {
        internal UiDriver _driver;
        internal How _findBy;
        internal string _findByValue;

        public Element(UiDriver driver, How findBy, string findByValue)
        {
            _driver = driver;
            _findBy = findBy;
            _findByValue = findByValue;
        }

        public Element()
        {

        }

        public virtual void Click()
        {
            _driver.Click(_findBy, _findByValue);
        }

        public string GetAttributeValue(string attributeName)
        {
            return _driver.GetAttributeValue(_findBy, _findByValue, attributeName);
        }

        public string GetTextAttribute()
        {
            return _driver.GetTextAttribute(_findBy, _findByValue);
        }

        public string GetCssValue(string propertyName)
        {
            return _driver.GetCssValue(_findBy, _findByValue, propertyName);
        }

        public bool IsDisplayed()
        {
            return _driver.IsDisplayed(_findBy, _findByValue);
        }

        public bool IsEnabled()
        {
            return _driver.IsEnabled(_findBy, _findByValue);
        }

        public void DragAndDrop(Element targetelement)
        {
            _driver.DragAndDrop(_findBy, _findByValue, targetelement._findBy, targetelement._findByValue);
        }

        public void ClickAndHold()
        {
            _driver.ClickAndHold(_findBy, _findByValue);
        }

        public void RightClick()
        {
            _driver.RightClick(_findBy, _findByValue);
        }

        public void MouseHover()
        {
            _driver.MouseHover(_findBy, _findByValue);
        }

        public void MouseHoverAndClick()
        {
            _driver.MouseHoverAndClick(_findBy, _findByValue);
        }

        public void DoubleClick()
        {
            _driver.DoubleClick(_findBy, _findByValue);
        }

        public void WaitTillVisible(int timeoutInSeconds = 60)
        {
            _driver.WaitTillElementisVisible(_findBy, _findByValue, timeoutInSeconds);
        }

        public void WaitTillInvisible(int timeOutInSeconds = 60)
        {
            _driver.WaitTillElementisInvisible(_findBy, _findByValue, timeOutInSeconds);
        }

        public void WaitTillExists(int timeOutInSeconds = 60)
        {
            _driver.WaitTillElementExists(_findBy, _findByValue, timeOutInSeconds);
        }

        public void WaitTillClickable(int timeOutInSeconds = 60)
        {
            _driver.WaitTillElementToBeClickable(_findBy, _findByValue, timeOutInSeconds);
        }

        public void WaitTillToBeSelected(int timeOutInSeconds = 60)
        {
            _driver.WaitTillElementToBeSelected(_findBy, _findByValue, timeOutInSeconds);
        }

        public void WaitTillTextToBePresent(string text, int timeOutInSeconds = 60)
        {
            _driver.WaitTillTextToBePresent(_findBy, _findByValue, text, timeOutInSeconds);
        }
    }
}

