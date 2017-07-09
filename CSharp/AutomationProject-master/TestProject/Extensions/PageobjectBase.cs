using System;
using OpenQA.Selenium.Support.PageObjects;
using OpenQA.Selenium;

namespace WilliamHill.Extensions
{
    public class PageobjectBase
    {
#pragma warning disable 0649
        protected IWebDriver _Driver;

        public PageobjectBase(IWebDriver driver)
        {
            _Driver = driver;
            PageFactory.InitElements(this, new RetryingElementLocator(_Driver, TimeSpan.FromSeconds(60), TimeSpan.FromMilliseconds(500)));
        }
    }
}