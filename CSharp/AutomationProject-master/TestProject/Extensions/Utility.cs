using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium;
using System.Collections.Specialized;
using System.Configuration;
using OpenQA.Selenium.Support.UI;
using TechTalk.SpecFlow;

namespace WilliamHill.Extensions
{
    public static class Utility
    {
        /// <summary>
        /// wait for element to get visible and enabled,
        /// </summary>
        /// <param name="elm">element</param>
        /// <param name="waittime">max time out</param>
        /// <param name="polinginterval">poling interval preferbly in millisecond</param>
        public static bool Conditionalwait(IWebElement elm, TimeSpan waittime, TimeSpan polinginterval)
        {
            try
            {
                DefaultWait<IWebElement> wait = new DefaultWait<IWebElement>(elm);
                wait.Timeout = waittime;
                wait.PollingInterval = polinginterval;
                wait.IgnoreExceptionTypes(typeof(NoSuchElementException));
                bool element = wait.Until<bool>((d) =>
                {
                    if (d.Displayed & d.Enabled)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                });
                return true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return false;
            }
        }

        /// <summary>
        /// wait for element to appear and click on elemnet,\
        ///  max time out 20 second
        /// </summary>
        /// <param name="elm">element to wait </param>
        public static void waitandclick(IWebElement elm)
        {
            // System.Threading.Thread.Sleep(5000);
            DefaultWait<IWebElement> wait = new DefaultWait<IWebElement>(elm);
            wait.Timeout = TimeSpan.FromSeconds(30);
            wait.PollingInterval = TimeSpan.FromMilliseconds(500);
            wait.IgnoreExceptionTypes(typeof(NoSuchElementException));
            bool element = wait.Until<bool>((d) =>
            {
                try
                {
                    if (d.Displayed & d.Enabled)
                    {
                        d.Click();

                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                catch (Exception e)
                {
                    string a = e.Message;
                    return false;
                }
            });
        }

        /// <summary>
        /// Function to bypass Certificate error page in IE browsers
        /// </summary>
        /// <param name="driver">webdriver</param>
        public static void ByPassCertificateErrorPage(IWebDriver driver)
        {
            if (driver.Title == "Certificate Error: Navigation Blocked")
            {
                driver.Navigate().GoToUrl("javascript:document.getElementById('overridelink').click()");
            }
        }
    }
}