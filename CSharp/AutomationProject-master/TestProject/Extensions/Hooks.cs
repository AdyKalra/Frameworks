using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Configuration;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.IO;
using OpenQA.Selenium.IE;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Support.Events;
using TechTalk.SpecFlow;
using WilliamHill.Extensions;

//using Mail;

namespace WilliamHill.SpecflowHooks
{
    [Binding]
    public class Hooks
    {
        // For additional details on SpecFlow hooks see http://go.specflow.org/doc-hooks

        private IWebDriver _Driver;
        private EventFiringWebDriver _Edriver;

        [BeforeScenario]
        [Scope(Tag = "UI")]
        public void BeforeScenario()
        {
            _Driver = new ChromeDriver("C:\\CrownBet\\GitHub\\AutomationProject-master\\TestProject\\Drivers\\");

            _Driver.Manage().Timeouts().SetPageLoadTimeout(TimeSpan.FromSeconds(60));
            _Driver.Manage().Timeouts().ImplicitlyWait(TimeSpan.FromSeconds(5));
            _Driver.Manage().Window.Maximize();

            ScenarioContext.Current["Driver"] = _Driver;
        }

        /// <summary>
        /// By Pass Certificate error page
        /// To Do - It is a work around and will fix it with a proper solution.
        /// </summary>
        [BeforeStep]
        [Scope(Tag = "UI")]
        public void IEByPassCertificateErrorPage()
        {
            Utility.ByPassCertificateErrorPage((IWebDriver)ScenarioContext.Current["Driver"]);
        }

        [AfterScenario]
        [Scope(Tag = "UI")]
        public void AfterScenario()
        {
            _Driver.Quit();
        }
    }
}