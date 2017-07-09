using Selenium.Core.Framework.Driver;
using Selenium.Core.Framework.Utilities;
using System;
using System.Collections.Generic;
using System.Reflection;

namespace Selenium.Core.Framework.UiControls
{
    public class WebElements<T> where T : Element, new() 
    {
        UiDriver _driver;
        How _findBy;
        string _findbyValue;

        public WebElements(UiDriver driver, How findBy, string findbyvalue)
        {
            _driver = driver;
            _findBy = findBy;
            _findbyValue = findbyvalue;
        }

        public List<T> GetElements()
        {
            List<T> webElements = new List<T>();
            var elements = _driver.FindElements(_findBy, _findbyValue);

            for(int i= 0; i< elements.Count; i++)
            {
                Type classType = typeof(T);
                ConstructorInfo classConstructor = classType.GetConstructor(new Type[] { typeof(UiDriver), typeof(How), typeof(string) });
                T classInstance = (T)classConstructor.Invoke(new object[] { _driver, _findBy, "(" + _findbyValue + ")" + "[" + (i+1) + "]" });
                webElements.Add(classInstance);
            }

            return webElements;
        }

        public void WaitTillPresent(int timeOutInSeconds = 60)
        {
            _driver.WaitTillElementsArePresent(_findBy, _findbyValue, timeOutInSeconds);
        }
    }
}
