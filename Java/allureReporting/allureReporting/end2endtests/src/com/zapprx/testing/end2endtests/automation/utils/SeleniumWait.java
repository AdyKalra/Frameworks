package com.zapprx.testing.end2endtests.automation.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SeleniumWait {
	WebDriver driver;
	WebDriverWait wait;

	public int timeout;

	public SeleniumWait(WebDriver driver, int timeout) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, timeout);
		this.timeout = timeout;
	}

	public boolean waitForPageTitleToContain(String expectedPagetitle) {
		return wait.until(ExpectedConditions.titleContains(expectedPagetitle)) != null;
	}

	public WebElement waitForElementToBeVisible(WebElement element) {
		return (WebElement) wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElementTextToContain(WebElement element, String expectedText) {
		wait.until(ExpectedConditions.textToBePresentInElement(element, expectedText));
	}

	public List<WebElement> waitForElementsToBeVisible(List<WebElement> elements) {
		return (List<WebElement>) wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}

	public boolean waitForElementToBeInVisible(By locator) {
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator)) != null;
	}

	public WebElement waitForElementToBeClickable(WebElement element) {
		return (WebElement) wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void waitForPageTitleToBeNonBlank() {
		wait.until(ExpectedConditions
				.not(ExpectedConditions.or(ExpectedConditions.titleIs(""), ExpectedConditions.titleIs("about:blank"))));
	}

	public void waitForElementToDisappear(WebElement element) {
		int i = 0;
		resetImplicitTimeout(2);
		try {
			while (element.isDisplayed() && i <= timeout) {
				i++;
			}
		} catch (Exception e) {
		}
		resetImplicitTimeout(timeout);
	}

	public void resetImplicitTimeout(int newTimeOut) {
		try {
			driver.manage().timeouts().implicitlyWait(newTimeOut, TimeUnit.SECONDS);
		} catch (Exception e) {
		}
	}

	public void waitForLoaderToDisappear() {
		resetImplicitTimeout(5);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loader")));
		} catch (TimeoutException e) {
			Assert.fail("Assertion Failed : Loader does't disappear within " + timeout + " second(s).");
		}
		resetImplicitTimeout(timeout);
	}

	// TODO Implement Wait for page load for page synchronizations
	public void waitForPageToLoadCompletely() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*")));
	}

	public void hardWait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public void waitForStableDom(int threshold /* ms */) {
		// Waits for the DOM to have been stable for `threshold` milliseconds.
		// Requires MutationObserver.
		// Registers observer to track last seen DOM mutation, function to see
		// if the DOM has been stable for a time.
		// https://developer.mozilla.org/en-US/docs/Web/API/MutationObserver
		// The string below is run inside an anonymous function by
		// executeScript().
		String observer = "var lastMod=new Date;" + "var observer=new MutationObserver(function(){lastMod=new Date});"
				+ "observer.observe(document.body,{childList:true,subtree:true});"
				+ "window.__DOM_STABLE_FOR=function(threshold){return ((new Date)-lastMod)>=threshold};";
		if ((Boolean) ((JavascriptExecutor) this.driver)
				.executeScript("return typeof(window.__DOM_STABLE_FOR) === 'undefined'")) {
			// Inject observer
			((JavascriptExecutor) this.driver).executeScript(observer);
		}
		wait.until(ExpectedConditions.jsReturnsValue(String.format("return window.__DOM_STABLE_FOR(%d)", threshold)));
	}

	public int getTimeout() {
		return timeout;
	}
}
