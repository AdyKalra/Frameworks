using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Support.PageObjects;
using WilliamHill.Extensions;

namespace WilliamHill.PageObjects
{
    internal class HorseRacingPage : PageobjectBase
    {
        [FindsBy(How = How.LinkText, Using = "RACE LIST")]
        private IWebElement RaceList;

        [FindsBy(How = How.XPath, Using = "//a[@href='https://www.williamhill.com.au/horse-racing']")]
        private IWebElement HorseRacingLink;

        [FindsBy(How = How.XPath, Using = "//*[@id='app']/div/div[4]/div/div/div[1]/div[1]/div/div[2]/div[3]/div[3]/div[2]/div[2]/div/div[1]/a[1]/div/div[3]")]
        private IWebElement EnglandAscot;

        [FindsBy(How = How.XPath, Using = "//*[@id='app']/div/div[4]/div/div/div[1]/div[1]/div/div[1]/div[4]/div[2]/div[29]/div[2]/div[1]/div[7]/div/button")]
        private IWebElement selectionoptiontobet;

        [FindsBy(How = How.XPath, Using = "/html/body/div[4]/div/div/div[2]/div[2]/div/div[2]/div/div[1]/div/div/input")]
        private IWebElement Stake;

        [FindsBy(How = How.XPath, Using = "/html/body/div[4]/div/div/div[2]/div[2]/div/span/div[2]/button[2]")]
        private IWebElement AddToBetSlip;

        [FindsBy(How = How.CssSelector, Using = "a[class='SideBar_button_l8p SideBar_buttonSelected_3Yl']")]
        private IWebElement BetSlip;

        [FindsBy(How = How.XPath, Using = "//*[@id='app']/div/div[3]/div/div[2]/section/div/section/div/section/hgroup/div[2]/h1")]
        private IWebElement TxtBetSlip;

        public HorseRacingPage(IWebDriver driver)
            : base(driver)
        {
            Utility.Conditionalwait(HorseRacingLink, TimeSpan.FromSeconds(180), TimeSpan.FromMilliseconds(1000));
            // ((IJavaScriptExecutor)_Driver).ExecuteScript("window.scrollTo(0, document.body.scrollHeight)");

            if (!HorseRacingLink.Displayed == true)
            {
                Assert.Fail("Failed to create accounts !!");
            }
            else
            {
                Console.Write("Accounts created successfully");
            }
        }

        public void ClickingRaceList()
        {
            Utility.waitandclick(RaceList);
        }

        public void GotoHorseRacing()
        {
            Utility.waitandclick(HorseRacingLink);
        }

        public void ClickEnglandAscotR6Option()
        {
            Utility.waitandclick(EnglandAscot);
        }

        public void SelectOptionToBet()
        {
            Utility.waitandclick(selectionoptiontobet);
        }

        public void EnterAmount(string value)
        {
            Stake.SendKeys(value);
        }

        public void AddtoBetSlip()
        {
            Utility.waitandclick(AddToBetSlip);
        }

        public void clickonBetSlip()
        {
            Utility.waitandclick(BetSlip);
        }

        public string GetTextFromBetSlip
        {
            get { return TxtBetSlip.Text; }
        }
    }
}