using OpenQA.Selenium;
using System;
using System.Collections.Generic;
using AutoIt;
using OpenQA.Selenium.Remote;
using OpenQA.Selenium.Interactions;
using OpenQA.Selenium.Support.UI;
using System.Drawing.Imaging;
using System.IO;
using Selenium.Core.Framework.Utilities;
using Selenium.Core.Framework.Logger;
using Selenium.Core.Framework.Exceptions;
using System.Configuration;

namespace Selenium.Core.Framework.Driver
{
    public class UiDriver
    {
        private static IWebDriver _nativeWebDriver;
        private static ICapabilities _capabilities;
        private static Actions _actions;
        private static IJavaScriptExecutor _jsExecutor;

        static UiDriver()
        {
            var nativeWebDriver = NativeWebDriver.Instance;
            _nativeWebDriver = nativeWebDriver.NativeDriver;
            _capabilities = ((RemoteWebDriver)_nativeWebDriver).Capabilities;
            _actions = new Actions(_nativeWebDriver);
            _jsExecutor = (IJavaScriptExecutor)_nativeWebDriver;
        }

        public UiDriver()
        {
        }

        #region Browser related methods
        public string GetUrl()
        {
            return _nativeWebDriver.Url;
        }

        public string GetTitle()
        {
            return _nativeWebDriver.Title;
        }

        public string GetPageSource()
        {
            return _nativeWebDriver.PageSource;
        }

        public string ExecuteScript(string script)
        {
            try
            {
                var iJavaexecutor = (IJavaScriptExecutor)_nativeWebDriver;
                var result = iJavaexecutor.ExecuteScript(script);
                return result.ToString();
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public string ExecuteAsyncScript(string script)
        {
            try
            {
                var iJavaexecutor = (IJavaScriptExecutor)_nativeWebDriver;
                var result = iJavaexecutor.ExecuteAsyncScript(script);
                return result.ToString();
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public void Scroll()
        {
            var jse = (IJavaScriptExecutor)_nativeWebDriver;
            jse.ExecuteScript("window.scrollTo(0, document.body.scrollHeight);");
        }

        public void ScrollUp(int scrollValue)
        {
            string scrollScript = string.Format("window.scrollBy(0,{0})", scrollValue);
            var jse = (IJavaScriptExecutor)_nativeWebDriver;
            jse.ExecuteScript(scrollScript, "");
        }

        public string GetScrollPosition()
        {
            return ExecuteScript("return window.scrollY;");
        }

        public void RefreshBrowser()
        {
            _nativeWebDriver.Navigate().Refresh();
        }

        public void NavigateBack()
        {
            _nativeWebDriver.Navigate().Back();
        }

        public void NavigateToUrl(string url)
        {
            FileLogger.Log("Launching url: " + url);
            _nativeWebDriver.Navigate().GoToUrl(url);
        }

        public string GetBrowserName()
        {
            ICapabilities capabilities = ((RemoteWebDriver)_nativeWebDriver).Capabilities;
            return capabilities.BrowserName;
        }

        public void Maximize()
        {
            _nativeWebDriver.Manage().Window.Maximize();
        }

        public void DisposeDriver()
        {
            NativeWebDriver.Instance.Dispose();
        }
        #endregion

        #region Cookie Related Methods
        public void AddCookie(string name, string value, string domain, string path, DateTime expires)
        {
            Cookie cookie = new Cookie(name, value, domain, path, expires);
            _nativeWebDriver.Manage().Cookies.AddCookie(cookie);
        }

        public void AddCookie(string name, string value, string path, DateTime expires)
        {
            Cookie cookie = new Cookie(name, value, path, expires);
            _nativeWebDriver.Manage().Cookies.AddCookie(cookie);
        }

        public void AddCookie(string name, string value, string path)
        {
            Cookie cookie = new Cookie(name, value, path);
            _nativeWebDriver.Manage().Cookies.AddCookie(cookie);
        }

        public void DeleteAllCookies()
        {
            _nativeWebDriver.Manage().Cookies.DeleteAllCookies();
        }

        public void DeleteCookie(Cookie cookie)
        {
            _nativeWebDriver.Manage().Cookies.DeleteCookie(cookie);
        }

        public void DeleteCookie(string cookiename)
        {
            _nativeWebDriver.Manage().Cookies.DeleteCookieNamed(cookiename);
        }

        public IReadOnlyCollection<Cookie> GetAllCookies()
        {
            return _nativeWebDriver.Manage().Cookies.AllCookies;
        }

        public Cookie GetCookie(string cookieName)
        {
            return _nativeWebDriver.Manage().Cookies.GetCookieNamed(cookieName);
        }
        #endregion

        #region Alert Related Methods
        public void AcceptAlert()
        {
            _nativeWebDriver.SwitchTo().Alert().Accept();
        }

        public void DismissAlert()
        {
            _nativeWebDriver.SwitchTo().Alert().Dismiss();
        }

        public string GetAlertText()
        {
            return _nativeWebDriver.SwitchTo().Alert().Text;
        }

        public bool IsAlertPresent()
        {
            var alert = _nativeWebDriver.SwitchTo().Alert();

            bool result = alert != null && alert.Text.Length > 0 ? true : false;
            return result;
        }
        #endregion

        #region Frames Related Methods
        public void SwitchToDefaultFrame()
        {
            _nativeWebDriver.SwitchTo().DefaultContent();
        }

        public void SwitchToParentframe()
        {
            _nativeWebDriver.SwitchTo().ParentFrame();
        }

        public void SwitchToFrame(int frameindex)
        {
            _nativeWebDriver.SwitchTo().Frame(frameindex);
        }

        public void SwitchToFrame(string frameName)
        {
            _nativeWebDriver.SwitchTo().Frame(frameName);
        }

        public void SwitchToFrame(IWebElement webElement)
        {
            _nativeWebDriver.SwitchTo().Frame(webElement);
        }
        #endregion

        #region Windowhandle related methods
        public void SwitchToDefaultWindow()
        {
            _nativeWebDriver.SwitchTo().DefaultContent();
        }

        public void SwitchToWindow(string windowName)
        {
            _nativeWebDriver.SwitchTo().Window(windowName);
        }

        public string GetCurrentWindowHandle()
        {
            return _nativeWebDriver.CurrentWindowHandle;
        }

        public IReadOnlyCollection<string> GetAllWindowHandles()
        {
            return _nativeWebDriver.WindowHandles;
        }

        #endregion

        #region Find Element Methods

        internal By GetByLocator(How findBy, string findByValue)
        {
            switch (findBy)
            {
                case How.Id:
                    return By.Id(findByValue);

                case How.TagName:
                    return By.TagName(findByValue);

                case How.Name:
                    return By.Name(findByValue);

                case How.ClassName:
                    return By.ClassName(findByValue);

                case How.Xpath:
                    return By.XPath(findByValue);

                case How.CssSelector:
                    return By.CssSelector(findByValue);

                case How.LinkText:
                    return By.LinkText(findByValue);

                case How.PartialLinkText:
                    return By.PartialLinkText(findByValue);

                default:
                    throw new Exception("Not a valid Locator type: " + findBy);
            }
        }

        internal IWebElement FindElement(How findBy, string findByvalue)
        {
            try
            {
                var bylocator = GetByLocator(findBy, findByvalue);
                var webElement = _nativeWebDriver.FindElement(bylocator);
                return webElement;
            }

            catch(Exception ex)
            {
                //TakeScreenshot("FindElementException");
                FileLogger.Log(string.Format("No Such Element Found. FindBy Type: {0} FIndByValue:{1}", findBy, findByvalue));
                throw new NoSuchElementFoundException(findBy, findByvalue, ex);
            }
        }

        internal IReadOnlyCollection<IWebElement> FindElements(How findBy, string findByvalue)
        {
            try
            {
                var bylocator = GetByLocator(findBy, findByvalue);
                var elements = _nativeWebDriver.FindElements(bylocator);
                return elements;
            }

            catch (Exception ex)
            {
                TakeScreenshot("FindElementsException");
                FileLogger.Log(string.Format("No Such Element Found. FindBy Type: {0} FIndByValue:{1}", findBy, findByvalue));
                throw new NoSuchElementFoundException(findBy, findByvalue, ex);
            }
        }

        #endregion

        #region Element Actions
        internal void Click(How findBy, string findByValue)
        {
            var webElement = FindElement(findBy, findByValue);
            webElement.Click();
        }

        public string GetAttributeValue(How findBy, string findByValue, string attributeName)
        {
            var webElement = FindElement(findBy, findByValue);
            return webElement.GetAttribute(attributeName);
        }

        public string GetTextAttribute(How findBy, string findByValue)
        {
            var webElement = FindElement(findBy, findByValue);
            return webElement.Text;
        }

        public string GetCssValue(How findBy, string findByValue, string propertyName)
        {
            var webElement = FindElement(findBy, findByValue);
            return webElement.GetCssValue(propertyName);
        }

        public bool IsDisplayed(How findBy, string findByValue)
        {
            var webElement = FindElement(findBy, findByValue);
            return webElement.Displayed;
        }

        public bool IsEnabled(How findBy, string findByValue)
        {
            var webElement = FindElement(findBy, findByValue);
            return webElement.Enabled;
        }

        public void Submit(How findBy, string findByValue)
        {
            var webElement = FindElement(findBy, findByValue);
            webElement.Submit();
        }

        public void SetText(How findBy, string findByValue, string value)
        {
            var webElement = FindElement(findBy, findByValue);
            webElement.SendKeys(value);
        }

        public void Clear(How findBy, string findByValue)
        {
            var webElement = FindElement(findBy, findByValue);
            webElement.Clear();
        }
        #endregion

        #region Wait Methods
        internal void WaitTillElementisVisible(How findBy, string findByValue, int seconds = 60)
        {
            try
            {
                var wait = new WebDriverWait(_nativeWebDriver, TimeSpan.FromSeconds(seconds));
                wait.Until(ExpectedConditions.ElementIsVisible(GetByLocator(findBy, findByValue)));
            }

            catch(Exception ex)
            {
                throw new WaitForElementException(findBy, findByValue, ex);
            }
            
        }

        internal void WaitTillElementExists(How findBy, string findByValue, int seconds = 60)
        {
            try
            {
                var wait = new WebDriverWait(_nativeWebDriver, TimeSpan.FromSeconds(seconds));
                wait.Until(ExpectedConditions.ElementExists(GetByLocator(findBy, findByValue)));
            }

            catch(Exception ex)
            {
                throw new WaitForElementException(findBy, findByValue, ex);
            }
            
        }

        internal void WaitTillElementToBeClickable(How findBy, string findByValue, int seconds = 60)
        {
            try
            {
                var wait = new WebDriverWait(_nativeWebDriver, TimeSpan.FromSeconds(seconds));
                wait.Until(ExpectedConditions.ElementToBeClickable(GetByLocator(findBy, findByValue)));
            }

            catch(Exception ex)
            {
                throw new WaitForElementException(findBy, findByValue, ex);
            }
            
        }

        internal void WaitTillElementToBeSelected(How findBy, string findByValue, int seconds = 60)
        {
            try
            {
                var wait = new WebDriverWait(_nativeWebDriver, TimeSpan.FromSeconds(seconds));
                wait.Until(ExpectedConditions.ElementToBeSelected(GetByLocator(findBy, findByValue)));
            }

            catch(Exception ex)
            {
                throw new WaitForElementException(findBy, findByValue, ex);
            }
            
        }

        internal void WaitTillElementisInvisible(How findBy, string findByValue, int seconds = 60)
        {
            try
            {
                var wait = new WebDriverWait(_nativeWebDriver, TimeSpan.FromSeconds(seconds));
                wait.Until(ExpectedConditions.InvisibilityOfElementLocated(GetByLocator(findBy, findByValue)));
            }
            
            catch(Exception ex)
            {
                throw new WaitForElementException(findBy, findByValue, ex);
            }
        }

        internal void WaitTillTextToBePresent(How findBy, string findByValue, string text, int seconds = 60)
        {
            try
            {
                var wait = new WebDriverWait(_nativeWebDriver, TimeSpan.FromSeconds(seconds));
                wait.Until(ExpectedConditions.TextToBePresentInElement(FindElement(findBy, findByValue), text));
            }
            
            catch(Exception ex)
            {
                throw new WaitForElementException("Text", text, ex);
            }
        }

        public void WaitTillTitleContains(string title, int seconds = 60)
        {
            try
            {
                var wait = new WebDriverWait(_nativeWebDriver, TimeSpan.FromSeconds(seconds));
                wait.Until(ExpectedConditions.TitleContains(title));
            }
            
            catch(Exception ex)
            {
                throw new WaitForElementException("Title", title, ex);
            }
        }

        public void WaitTillTitleIs(string title, int seconds = 60)
        {
            try
            {
                var wait = new WebDriverWait(_nativeWebDriver, TimeSpan.FromSeconds(seconds));
                wait.Until(ExpectedConditions.TitleIs(title));
            }

            catch(Exception ex)
            {
                throw new WaitForElementException("Title", title, ex);
            }
            
        }

        public void WaitTillUrlContains(string url, int seconds = 60)
        {
            try
            {
                var wait = new WebDriverWait(_nativeWebDriver, TimeSpan.FromSeconds(seconds));
                wait.Until(ExpectedConditions.UrlContains(url));
            }
            
            catch(Exception ex)
            {
                throw new WaitForElementException("URL", url, ex);
            }
        }

        public void WaitTillUrlToBe(string url, int seconds = 60)
        {
            try
            {
                var wait = new WebDriverWait(_nativeWebDriver, TimeSpan.FromSeconds(seconds));
                wait.Until(ExpectedConditions.UrlToBe(url));
            }

            catch(Exception ex)
            {
                throw new WaitForElementException("URL", url, ex);
            }
            
        }

        internal void WaitTillElementsArePresent(How findBy, string findByvalue, int seconds = 60)
        {
            try
            {
                var wait = new WebDriverWait(_nativeWebDriver, TimeSpan.FromSeconds(seconds));
                wait.Until(ExpectedConditions.PresenceOfAllElementsLocatedBy(GetByLocator(findBy, findByvalue)));
            }

            catch(Exception ex)
            {
                throw new WaitForElementException(findBy, findByvalue, ex);
            }
           
        }
        #endregion

        #region ActionMethods
        internal void MouseHover(How findBy, string findByValue)
        {
            var element = FindElement(findBy, findByValue);
            _actions.MoveToElement(element).Perform();
        }

        internal void ClickAndHold(How findBy, string findByValue)
        {
            var element = FindElement(findBy, findByValue);
            _actions.ClickAndHold(element).Perform();
        }

        internal void DoubleClick(How findBy, string findByValue)
        {
            var element = FindElement(findBy, findByValue);
            _actions.DoubleClick(element).Perform();
        }

        internal void DragAndDrop(How findBy, string findByValue, How targetFindBy, string targetFindByValue)
        {
            var element = FindElement(findBy, findByValue);
            var targetElement = FindElement(targetFindBy, targetFindByValue);
            _actions.DragAndDrop(element, targetElement).Perform();
        }

        internal void MouseHoverAndClick(How findBy, string findByValue)
        {
            var element = FindElement(findBy, findByValue);
            _actions.MoveToElement(element).Click(element).Perform();
        }

        internal void RightClick(How findBy, string findByValue)
        {
            var element = FindElement(findBy, findByValue);
            _actions.ContextClick(element).Build().Perform();
        }

        public void KeyEnter()
        {
            _actions.SendKeys(Keys.Enter);
        }

        public void KeyTab()
        {
            _actions.SendKeys(Keys.Tab);
        }
        #endregion

        #region Screenshot Capture Methods
        public void TakeScreenshot(string fileName)
        {
            try
            {
                var folderPath = Path.Combine(AppDomain.CurrentDomain.BaseDirectory +
                ConfigurationManager.AppSettings["ScreenshotFolderPath"]);

                fileName = folderPath + "\\" + fileName + GenerateRandomNumber() + ".png";

                var screenShot = ((ITakesScreenshot)_nativeWebDriver).GetScreenshot();
                screenShot.SaveAsFile(fileName, ImageFormat.Png);
            }

            catch(Exception ex)
            {
                throw new Exception("Exception while taking screenshot. Message: " + ex.Message);
            }
        }

        public string GenerateRandomNumber()
        {
            var random = new Random();
            return Convert.ToString(random.Next(999));
        }
        #endregion

        #region Windows Popups/Alerts
        public void HandleFileUploadWindow(string filepath)
        {
            try
            {
                string windowTitle = GetBrowserName() == "internet explorer" ?
                 "Choose File to Upload" : "Open";

                AutoItX.WinWait(windowTitle, "File &name:", 10);
                AutoItX.ControlSetText(windowTitle, "", "[CLASS:Edit; INSTANCE:1]", filepath);
                AutoItX.ControlClick(windowTitle, "", "[CLASS:Button; INSTANCE:1]");
                AutoItX.WinWaitClose(windowTitle, "File &name:", 10);
            }

            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public void WindowsWarningOrConfirmationAccept(string alertTitle)
        {
            try
            {
                if (AutoItX.WinExists(alertTitle) != 0)
                {
                    AutoItX.WinWaitActive(alertTitle);
                    AutoItX.ControlClick(alertTitle, "", "[CLASS:Button; INSTANCE:0; TEXT:&Yes]");
                    AutoItX.WinWaitClose(alertTitle);
                }

            }

            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public void WindowsWarningOrConfirmationDismiss(string alertTitle)
        {
            try
            {
                if (AutoItX.WinExists(alertTitle) != 0)
                {
                    AutoItX.WinWaitActive(alertTitle);
                    AutoItX.ControlClick(alertTitle, "", "[CLASS:Button; INSTANCE:1; TEXT:&No]");
                    AutoItX.WinWaitClose(alertTitle);
                }
            }

            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        #endregion
    }
}
