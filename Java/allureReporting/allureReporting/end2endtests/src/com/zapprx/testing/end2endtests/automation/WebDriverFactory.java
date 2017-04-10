/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapprx.testing.end2endtests.automation;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFactory {
	private static String browser = System.getProperty("browser");
	static String downloadFilePath = System.getProperty("user.dir")
			+ File.separator + "src" + File.separator + "com" + File.separator
			+ "zapprx" + File.separator + "testing" + File.separator
			+ "end2endtests" + File.separator + "main" + File.separator
			+ "resources" + File.separator + "testdata" + File.separator
			+ "downloadPDF";
	private static final DesiredCapabilities capabilities = new DesiredCapabilities();

	public WebDriver getDriver(Map<String, String> seleniumconfig) {
		if (browser == null || browser.isEmpty() || browser.equals(""))
			browser = seleniumconfig.get("browser");

		if (seleniumconfig.get("seleniumserver").equalsIgnoreCase("local")) {
			if (browser.equalsIgnoreCase("firefox")) {
				return getFirefoxDriver();
			} else if (browser.equalsIgnoreCase("chrome")) {
				return getChromeDriver(seleniumconfig.get("driverpath"));
			} else if (browser.equalsIgnoreCase("Safari")) {
				return getSafariDriver();
			} else if ((browser.equalsIgnoreCase("ie")) || (browser.equalsIgnoreCase("internetexplorer"))
					|| (browser.equalsIgnoreCase("internet explorer"))) {
				return getInternetExplorerDriver(seleniumconfig.get("driverpath"));
			}
		}
		if (seleniumconfig.get("seleniumserver").equalsIgnoreCase("remote")) {
			return setRemoteDriver(seleniumconfig);
		}
		return new FirefoxDriver();
	}

	private WebDriver setRemoteDriver(Map<String, String> selConfig) {
		DesiredCapabilities cap = null;
		if (browser == null || browser.isEmpty() || browser.equals(""))
			browser = selConfig.get("browser");
		if (browser.equalsIgnoreCase("firefox")) {
			cap = DesiredCapabilities.firefox();
		} else if (browser.equalsIgnoreCase("chrome")) {
			cap = DesiredCapabilities.chrome();
		} else if (browser.equalsIgnoreCase("Safari")) {
			cap = DesiredCapabilities.safari();
		} else if ((browser.equalsIgnoreCase("ie")) || (browser.equalsIgnoreCase("internetexplorer"))
				|| (browser.equalsIgnoreCase("internet explorer"))) {
			cap = DesiredCapabilities.internetExplorer();
		}
		String seleniuhubaddress = selConfig.get("seleniumserverhost");
		URL selserverhost = null;
		try {
			selserverhost = new URL(seleniuhubaddress);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		cap.setJavascriptEnabled(true);
		return new RemoteWebDriver(selserverhost, cap);
	}
	
	
	public void setTempPath(){
		System.setProperty("java.io.tmpdir", "/tmp");
	}

	private static WebDriver getChromeDriver(String driverpath) {
		System.setProperty("webdriver.chrome.driver", driverpath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--test-type");
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		return new ChromeDriver(cap);
	}

	private static WebDriver getInternetExplorerDriver(String driverpath) {
		System.setProperty("webdriver.ie.driver", driverpath);
		capabilities.setCapability("ignoreZoomSetting", true);
		capabilities.setCapability("ignoreZoomLevel", true);
		return new InternetExplorerDriver(capabilities);
	}

	private static WebDriver getSafariDriver() {
		return new SafariDriver();
	}

	private static WebDriver getFirefoxDriver() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.cache.disk.enable", false);
		profile.setPreference("browser.startup.homepage_override.mstone", "ignore");
		profile.setPreference("startup.homepage_welcome_url.additional", "about:blank");
		profile.setPreference("browser.download.useDownloadDir", true);
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);

		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"application/msword, application/csv, application/ris, text/csv, application/pdf, text/plain, application/zip, application/x-zip, application/x-zip-compressed, application/download, application/octet-stream");
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.manager.focusWhenStarting", false);
		profile.setPreference("browser.download.dir", downloadFilePath);

		profile.setPreference("browser.download.manager.showAlertOnComplete", false);
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("plugin.disable_full_page_plugin_for_types",
				"application/pdf,application/vnd.adobe.xfdf,application/vnd.fdf,application/vnd.adobe.xdp+xml");
		profile.setPreference("pdfjs.disabled", true);
		return new FirefoxDriver(profile);
	}
}
