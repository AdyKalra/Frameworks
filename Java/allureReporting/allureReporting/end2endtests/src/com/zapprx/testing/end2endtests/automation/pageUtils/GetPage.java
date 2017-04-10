package com.zapprx.testing.end2endtests.automation.pageUtils;

import static com.zapprx.testing.end2endtests.automation.pageUtils.ObjectFileReader.getElementFromFile;
import static com.zapprx.testing.end2endtests.automation.utils.ConfigPropertyReader.getProperty;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.utils.LayoutValidation;

public class GetPage extends BaseUi {
	protected WebDriver webdriver;
	String pageName;
	LayoutValidation layouttest;

	public GetPage(WebDriver driver, String pageName) {
		super(driver, pageName);
		this.webdriver = driver;
		this.pageName = pageName;
		layouttest = new LayoutValidation(driver, pageName);
	}

	public void testPageLayout(List<String> tagsToBeTested) {
		layouttest.checklayout(tagsToBeTested);
	}

	public void testPageLayout(String tagToBeTested) {
		testPageLayout(Arrays.asList(tagToBeTested));
	}

	public void testPageLayout() {
		testPageLayout(Arrays.asList(getProperty("./Config.properties", "browser")));
	}

	protected WebElement element(String elementToken) {
		return element(elementToken, "");
	}

	protected WebElement element(String elementToken, String replacement1, String replacement2)
			throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBeVisible(
					webdriver.findElement(getLocator(elementToken, replacement1, replacement2)));
		} catch (NoSuchElementException excp) {
			fail("FAILED: Element " + elementToken + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}

	protected WebElement element(String elementToken, String replacement) throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBeVisible(webdriver.findElement(getLocator(elementToken, replacement)));
		} catch (NoSuchElementException excp) {
			fail("FAILED: Element " + elementToken + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}

	protected List<WebElement> elements(String elementToken, String replacement) {
		return webdriver.findElements(getLocator(elementToken, replacement));
	}

	protected List<WebElement> elements(String elementToken) {
		return elements(elementToken, "");
	}

	protected boolean isElementDisplayed(String elementName, String elementTextReplace) {
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
		boolean result = element(elementName, elementTextReplace).isDisplayed();
		assertTrue(result,
				"TEST FAILED: element '" + elementName + "with text " + elementTextReplace + "' is not displayed.");
		logMessage("TEST PASSED: element " + elementName + " with text " + elementTextReplace + " is displayed.");
		return result;
	}

	protected boolean isElementDisplayed(String elementName, String elementTextReplace1, String elementTextReplace2) {
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace1, elementTextReplace2));
		boolean result = element(elementName, elementTextReplace1, elementTextReplace2).isDisplayed();
		assertTrue(result, "TEST FAILED: element '" + elementName + "with text " + elementTextReplace1 + " and "
				+ elementTextReplace2 + "' is not displayed.");
		logMessage("TEST PASSED: element " + elementName + " with text " + elementTextReplace1 + " and "
				+ elementTextReplace2 + " is displayed.");
		return result;
	}

	protected boolean isElementDisplayed(String elementName) {
		wait.waitForElementToBeVisible(element(elementName));
		boolean result = element(elementName).isDisplayed();
		assertTrue(result, "Assertion Failed: element '" + elementName + "' is not displayed.");
		logMessage("Assertion Passed: element " + elementName + " is displayed.");
		return result;
	}

	protected void isElementNotDisplayed(String elementName) {
		wait.resetImplicitTimeout(5);
		Boolean status = false;
		try {
			status = webdriver.findElement(getLocator(elementName)).isDisplayed();
			Assert.assertFalse(status, "Assertion Failed : Element " + elementName + " is displayed");
		} catch (NoSuchElementException e) {
			logMessage("Assertion Passed : Element " + elementName + " is not displayed ");
		} finally {
			wait.resetImplicitTimeout(wait.timeout);
		}
	}

	protected void isElementNotDisplayed(String elementName, String elementTextReplace1, String elementTextReplace2) {
		wait.resetImplicitTimeout(5);
		Boolean status = false;
		try {
			status = webdriver.findElement(getLocator(elementName, elementTextReplace1, elementTextReplace2))
					.isDisplayed();
			Assert.assertFalse(status, "Assertion Failed : Element " + elementName + "with text '" + elementTextReplace1
					+ "' and '" + elementTextReplace2 + "' is displayed");
		} catch (NoSuchElementException e) {
			logMessage("Assertion Passed : Element " + elementName + "with text '" + elementTextReplace1 + "' and '"
					+ elementTextReplace2 + "' is not displayed ");
		} finally {
			wait.resetImplicitTimeout(wait.timeout);
		}
	}

	protected void isElementNotDisplayed(String elementName, String elementTextReplace) {
		wait.resetImplicitTimeout(5);
		Boolean status = false;
		try {
			status = webdriver.findElement(getLocator(elementName, elementTextReplace)).isDisplayed();
			Assert.assertFalse(status,
					"Assertion Failed : Element " + elementName + "with text " + elementTextReplace + "' is displayed");
		} catch (NoSuchElementException e) {
			logMessage("Assertion Passed : Element " + elementName + "with text " + elementTextReplace
					+ "' is not displayed ");
		} finally {
			wait.resetImplicitTimeout(wait.timeout);
		}
	}

	protected By getLocator(String elementToken) {
		return getLocator(elementToken, "");
	}

	protected By getLocator(String elementToken, String replacement) {
		String[] locator = getElementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceAll("\\$\\{.+\\}", replacement);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	protected By getLocator(String elementToken, String replacement1, String replacement2) {
		String[] locator = getElementFromFile(this.pageName, elementToken);
		locator[2] = StringUtils.replace(locator[2], "$", replacement1);
		locator[2] = StringUtils.replace(locator[2], "%", replacement2);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	private By getBy(String locatorType, String locatorValue) {
		switch (Locators.valueOf(locatorType)) {
		case id:
			return By.id(locatorValue);
		case xpath:
			return By.xpath(locatorValue);
		case css:
			return By.cssSelector(locatorValue);
		case name:
			return By.name(locatorValue);
		case classname:
			return By.className(locatorValue);
		case linktext:
			return By.linkText(locatorValue);
		default:
			return By.id(locatorValue);
		}
	}

}