package com.zapprx.testing.end2endtests.pageActions.services;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class PresAdminPortalPageActions extends GetPage {
	WebDriver driver;

	public PresAdminPortalPageActions(WebDriver driver) {
		super(driver, "services/PresAdminPortalPage");
		this.driver = driver;
	}

	public String logInDJangoRESTAndSaveResInJSON(String presID, String userName, String password, String formatType) {
		driver.get("localhost:8000/services/prescription/" + presID);
		logMessage("Fetch the response for Prescription Id " + presID);
		if (elements("link_logOut").size() == 0) {
			logInToDjangoREST();
			enterUserName(userName);
			enterPassword(password);
			logInToApplication();
		}
		clickOnGETDropdown();
		clickOnFormatType(formatType);
		return driver.getCurrentUrl();
	}

	/**
	 * This method clicks LogIn on DJango REST Framework page
	 */
	private void logInToDjangoREST() {
		element("link_logIn").click();
		logMessage("User clicks LogIn on Djano REST Framework");
	}

	/**
	 * This method enters username
	 * 
	 * @param userName
	 */
	private void enterUserName(String userName) {
		enterText(element("inp_username"), userName);
		logMessage("User enters username on Djano REST Framework");
	}

	/**
	 * This method enters password
	 * 
	 * @param password
	 */
	private void enterPassword(String password) {
		enterText(element("inp_password"), password);
		logMessage("User enters password on Djano REST Framework");
	}

	/**
	 * This method click on submit to login into the application
	 */
	private void logInToApplication() {
		element("inp_submit").click();
		logMessage("User log in to the application");
	}

	/**
	 * Select the dropdown from GET
	 */
	private void clickOnGETDropdown() {
		element("btn_drpdwn", "json").click();
		logMessage("User clicks on GET dropdown");
	}

	/**
	 * This method selects specific format
	 * 
	 * @param formatType
	 */
	private void clickOnFormatType(String formatType) {
		element("link_format", formatType).click();
		logMessage("User clicks on " + formatType + " under GET dropdown");
	}

	/**
	 * This method return the text from JSON
	 * 
	 * @return
	 */
	public Object getJSONText() {
		return ((JavascriptExecutor) driver).executeScript("return document.getElementsByTagName('pre')[0].innerHTML");
	}
}
