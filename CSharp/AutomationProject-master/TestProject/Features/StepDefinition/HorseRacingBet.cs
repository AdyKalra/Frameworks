using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;
using System.Threading;
using OpenQA.Selenium;
using WilliamHill.Extensions;
using TechTalk.SpecFlow;
using WilliamHill.PageObjects;
using NUnit.Framework;
using System.Collections;
using System.Text.RegularExpressions;

namespace WilliamHill.Features.StepDefinitions
{
    [Binding]
    public class HorseRacingBet
    {
        private IWebDriver _Driver = (IWebDriver)ScenarioContext.Current["Driver"];

        private HorseRacingPage horseracing;

        // For additional details on SpecFlow step definitions see http://go.specflow.org/doc-stepdef

        [Given(@"I am navigated to '(.*)' url")]
        public void GivenIAmNavigatedToUrl(string p0)
        {
            _Driver.Navigate().GoToUrl("https://www.williamhill.com.au");
        }

        [Then(@"I click on RaceList")]
        public void ThenIClickOnRaceList()
        {
            horseracing = new HorseRacingPage(_Driver);
            horseracing.ClickingRaceList();
        }

        [Then(@"I click on Horse Racing")]
        public void ThenIClickOnHorseRacing()
        {
            horseracing.GotoHorseRacing();
        }

        [Then(@"I Click on England-ascot Race(.*)")]
        public void ThenIClickOnEngland_AscotRace(int p0)
        {
            horseracing.ClickEnglandAscotR6Option();
        }

        [Then(@"I select Kaspersky")]
        public void ThenISelectKaspersky()
        {
            horseracing.SelectOptionToBet();
        }

        [Then(@"I place a bet of '(.*)' and add bet to slip")]
        public void ThenIPlaceABetOfAndAddBetToSlip(string value)
        {
            horseracing.EnterAmount(value);
            horseracing.AddtoBetSlip();
        }

        [Then(@"I click on BetSlip and verify bet is added correctly")]
        public void ThenIClickOnBetSlipAndVerifyBetIsAddedCorrectly()
        {
            horseracing.clickonBetSlip();
            string expectedtext = "15. Miss Temple City";
            string actualtext = horseracing.GetTextFromBetSlip;
            CheckPoint.VerifyAreEqual(expectedtext, actualtext, "Value doesn't match");
        }
    }
}