using Data.Utilities;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.IE;
using Selenium.Core.Framework.Exceptions;
using Selenium.Core.Framework.Logger;
using Selenium.Core.Framework.Utilities;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Diagnostics;

namespace Selenium.Core.Framework.Driver
{
    internal class NativeWebDriver
    {
        #region Config Variables
        private static string _platform;
        private static string _driverPath;
        private static string _browserConfigXmlPath;
        private static string _geckoDriverPath;
        private static int _implicitWaitTimeOut;
        private static int _pageLoadTimeOut;
        private static Dictionary<string, string> _firefoxPreferences;
        private static Dictionary<string, string> _iePreferences;
        private static Dictionary<string, string> _chromePreferences;
        #endregion

        private static IWebDriver _nativeWebDriver;

        private NativeWebDriver()
        {
        }

        private static readonly Lazy<NativeWebDriver> _instance = new Lazy<NativeWebDriver>(() =>
        {
            InitializeConfigVariables();
            InitiateDriver(_platform);

            if(_platform != DriverPlatform.Winium)
            {
                SetImplicitWait();
                SetPageTimeout();
            }

            return new NativeWebDriver();
        });

        public static NativeWebDriver Instance
        {
            get
            {
                return _instance.Value;
            }
        }
        
        internal IWebDriver NativeDriver
        {
            get
            {
                return _nativeWebDriver;
            }
        }

        private static void InitiateDriver(string browser)
        {
            try
            {
                FileLogger.Log("Browser Selection:" + browser + "\n");
                switch (browser)
                {
                    case DriverPlatform.InternetExplorer:
                        _nativeWebDriver = InitiateIeDriver();
                        break;

                    case DriverPlatform.Chrome:
                        _nativeWebDriver = InitiateChromeDriver();
                        break;

                    case DriverPlatform.Firefox:
                        _nativeWebDriver = InitiateFirefoxDriver();
                        break;

                    default:
                        throw new Exception(browser + " is not a valid browser option");
                }
            }

            catch(Exception ex)
            {
                throw new BrowserInitializationException(browser, ex);
            }
        }

        private static IWebDriver InitiateChromeDriver()
        {
            FileLogger.Log("Initiating Chrome Driver\n");
            var options = new ChromeOptions();

            FileLogger.Log("Setting Browser Preferences:\n");
            foreach (var attribute in _chromePreferences.Keys)
            {
                FileLogger.Log("Attribute: " + attribute + " Attribute Value:" + _chromePreferences[attribute]);
                options.AddUserProfilePreference(attribute, _chromePreferences[attribute]);
            }

            return new ChromeDriver(_driverPath, options);
        }

        private static IWebDriver InitiateFirefoxDriver()
        {
            FileLogger.Log("Initiating Firefox Driver\n");
            Environment.SetEnvironmentVariable("webdriver.gecko.driver", 
                _geckoDriverPath);

            var options = new FirefoxOptions();

            FileLogger.Log("Setting Browser Preferences:\n");
            foreach (var attribute in _firefoxPreferences.Keys)
            {
                FileLogger.Log("Setting Preference:" + attribute + " Value:" + _firefoxPreferences[attribute]);
                options.SetPreference(attribute, _firefoxPreferences[attribute]);
            }

            return new FirefoxDriver(options);
        }

        private static IWebDriver InitiateIeDriver()
        {
            FileLogger.Log("Initiating IE Driver");
            Environment.SetEnvironmentVariable("webdriver.ie.driver",
                _driverPath + "IEDriverServer.exe");

            var options = new InternetExplorerOptions();

            FileLogger.Log("Setting Browser Preferences:\n");
            foreach (var attribute in _iePreferences.Keys)
            {
                FileLogger.Log("Setting Preference:" + attribute + " Value:" + _iePreferences[attribute]);
                options.AddAdditionalCapability(attribute, _iePreferences[attribute]);
            }

            return new InternetExplorerDriver(_driverPath, options);
        }

        private static void SetPageTimeout()
        {
            _nativeWebDriver.Manage().Timeouts().SetPageLoadTimeout(TimeSpan.FromSeconds(_pageLoadTimeOut));
        }

        private static void SetImplicitWait()
        {
            _nativeWebDriver.Manage().Timeouts().ImplicitlyWait(TimeSpan.FromSeconds(_implicitWaitTimeOut));
        }

        private static Dictionary<string, string> GetBrowserPreferences(string browserName)
        {
            try
            {
                Dictionary<string, string> attributes = new Dictionary<string, string>();

                var nodes = XmlReader.GetDescendantNodes(_browserConfigXmlPath, browserName);

                foreach (var node in nodes)
                {
                    var key = node.Attribute("key").Value;
                    var value = node.Attribute("value").Value;

                    attributes.Add(key, value);
                }

                return attributes;
            }

            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        private static void InitializeConfigVariables()
        {
            var projectPath = AppDomain.CurrentDomain.BaseDirectory + @"../../../Selenium.Core.Framework/";
            _driverPath = projectPath + "bin/debug";
            _geckoDriverPath = projectPath;

            _browserConfigXmlPath = projectPath + "BrowserConfig.xml";

            _platform = ConfigurationManager.AppSettings["Browser"];

            _firefoxPreferences = GetBrowserPreferences(Utilities.DriverPlatform.Firefox);
            _chromePreferences = GetBrowserPreferences(Utilities.DriverPlatform.Chrome);
            _iePreferences = GetBrowserPreferences(Utilities.DriverPlatform.InternetExplorer);

            _implicitWaitTimeOut = Convert.ToInt32
                (XmlReader.GetNodeValue(_browserConfigXmlPath, "ImplicitWaitinSeconds"));

            _pageLoadTimeOut = Convert.ToInt32
                (XmlReader.GetNodeValue(_browserConfigXmlPath, "PageLoadTimeoutinSeconds"));
        }

        internal void Dispose()
        {
            try
            {
                FileLogger.Log("Disposing Driver");
                _nativeWebDriver.Close();
                _nativeWebDriver.Quit();

                switch (_platform)
                {
                    case DriverPlatform.Chrome:
                        KillChromeProcess();
                        break;

                    case DriverPlatform.InternetExplorer:
                        KillIeProcess();
                        break;

                    default:
                        throw new Exception(_platform + " not a valid process to kill");
                }
            }

            catch (Exception ex)
            {
                throw new DriverDisposeException(ex);
            }

        }

        void KillChromeProcess()
        {
            var processes = Process.GetProcessesByName("chromedriver");

            foreach (var process in processes)
            {
                process.Kill();
            }
        }

        void KillIeProcess()
        {
            var processes = Process.GetProcessesByName("IEDriverServer");

            foreach (var process in processes)
            {
                process.Kill();
            }
        }
    }
}
