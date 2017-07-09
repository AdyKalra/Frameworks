using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Interactions;
using WilliamHill.Extensions;
using TechTalk.SpecFlow;

namespace WilliamHill.Extensions
{
    public static class WebdriverExtension
    {
        /// <summary>
        /// Click Extension method. Bring to visibility and clicks on IWebElement
        /// </summary>
        /// <param name="element"></param>
        public static void SwipeAndClick(this IWebElement element)
        {
            try
            {
                var driver = (IWebDriver)ScenarioContext.Current["Driver"];
                Actions builder = new Actions(driver);
                builder.MoveToElement(element);
                builder.Build().Perform();
                //Utility.waitandclick(element);
                //Below code can be replaced with other functions like WaitAndClick to wait until element is visible
                Thread.Sleep(500);
                Assert.True(element.Displayed, "Check for WebElement : {0} and Click", element.Text);
                element.Click();
            }
            catch (Exception ex)
            {
                Assert.Fail("Click Extension method on WebElement : {0} failed with exception:  {1}", element.Text, ex.Message);
            }
        }
    }
}