/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapprx.testing.end2endtests.automation.pageUtils;

import com.zapprx.testing.end2endtests.automation.utils.CustomFunctions;
import com.zapprx.testing.end2endtests.automation.utils.SeleniumWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import static com.zapprx.testing.end2endtests.automation.utils.ConfigPropertyReader.getProperty;

/**
 *
 * @author QAIT
 */
public class BaseUi {
	WebDriver driver;
	protected SeleniumWait wait;
	private String pageName;
	protected CustomFunctions custom;

	protected BaseUi(WebDriver driver, String pageName) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		this.pageName = pageName;
		this.wait = new SeleniumWait(driver, Integer.parseInt(getProperty("Config.properties", "timeout")));
		custom = new CustomFunctions(driver);
		custom.setPatientLastName();
		CustomFunctions.setGender();
		custom.setDoctorFirstName();
		custom.setDoctorEmailId();
	}

	protected String getPageTitle() {
		return driver.getTitle();
	}

	protected void logMessage(String message) {
		Reporter.log(custom.getCurrentTime() + " " + message, true);
	}

	protected String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	/**
	 * Verification of the page title with the title text provided in the page
	 * object repository
	 */
	protected void verifyPageTitleContains(String expectedPagetitle) {
		wait.waitForPageTitleToContain(expectedPagetitle);
		Assert.assertEquals(getPageTitle(), expectedPagetitle,
				"Verifying Page title to validate right " + expectedPagetitle + " - ");
		Reporter.log(
				"Assertion Passed: PageTitle for " + expectedPagetitle + " is exactly: '" + expectedPagetitle + "'.");
		logMessage("User is on " + expectedPagetitle + "Page");
	}

	public String getEnv() {
		String tier = System.getProperty("env");
		if (tier == null)
			tier = getProperty("./Config.properties", "tier");
		return tier;
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	protected void executeJavascript(String script) {
		((JavascriptExecutor) driver).executeScript(script);
	}

	protected String executeJavascriptReturnValue(String script) {
		return (String) ((JavascriptExecutor) driver).executeScript(script);
	}

	protected void clickUsingXpathInJavaScriptExecutor(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public void refreshPage() {
		driver.navigate().refresh();
		logMessage("User refreshed the page");
	}

	public void navigateBack() {
		driver.navigate().back();
		logMessage("User navigates to previous page");
	}

	public void closeWindow() {
		driver.close();
		logMessage("User closes the window");
	}

	public void enterText(WebElement element, String text) {
		element.clear();
		element.sendKeys(text);
	}

	public void drawSignatureOnCanvas(WebElement element) {
		Actions actionBuilder = new Actions(driver);
		scrollDown(element);
		wait.waitForElementToBeVisible(element);
		Action drawOnCanvas = actionBuilder.clickAndHold(element).moveByOffset(5, 50).release(element).build();
		drawOnCanvas.perform();
		logMessage("This method draw signature on canvas");
	}

	protected void selectProvidedTextFromDropDown(WebElement el, String text) {
		wait.waitForElementToBeVisible(el);
		scrollDown(el);
		Select sel = new Select(el);
		sel.selectByVisibleText(text);
	}

	protected void selectTextFromDropDownByIndex(WebElement el, int index) {
		wait.waitForElementToBeVisible(el);
		scrollDown(el);
		Select sel = new Select(el);
		sel.selectByIndex(index);
	}

	protected String getProvidedTextFromDropDown(WebElement el) {
		wait.waitForElementToBeVisible(el);
		scrollDown(el);
		Select sel = new Select(el);
		return sel.getFirstSelectedOption().getText();
	}

	protected void scrollDown(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void changeWindow(int i) {
		Set<String> windows = driver.getWindowHandles();
		if (i > 0) {
			for (int j = 0; j < 5; j++) {
				if (windows.size() < 2) {
					try {
						Thread.sleep(2000);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					break;
				}
			}
			windows = driver.getWindowHandles();
		}
		String wins[] = windows.toArray(new String[windows.size()]);
		driver.switchTo().window(wins[i]);
		wait.waitForPageTitleToBeNonBlank();
		driver.manage().window().maximize();
	}

	protected void click(WebElement element) {
		try {
			wait.waitForElementToBeVisible(element);
			scrollDown(element);
			element.click();
		} catch (StaleElementReferenceException ex1) {
			wait.waitForElementToBeVisible(element);
			scrollDown(element);
			element.click();
			logMessage("Clicked Element " + element + " after catching Stale Element Exception");
		} catch (Exception ex2) {
			logMessage("Element " + element + " could not be clicked! " + ex2.getMessage());
		}
	}

	public String getMonth(String month) {
		Date date;
		try {
			date = new SimpleDateFormat("MMMM").parse(month);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			String mon = (cal.get(Calendar.MONTH) + 1) + "";
			String s = new Integer(mon).toString();
			int length = s.length();
			if (length == 1) {
				return "0" + mon;
			}
			return mon;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;

	}

}
