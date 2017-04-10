package com.zapprx.testing.end2endtests.pageActions.services;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class DjangoAdminPortalPageActions extends GetPage {
	WebDriver driver;

	public DjangoAdminPortalPageActions(WebDriver driver) {
		super(driver, "services/DjangoAdminPortalPage");
		this.driver = driver;
	}

	/**
	 * This method login and selects Actors role
	 * 
	 * @param userName
	 * @param password
	 */
	public void loginAndSelectActors(String emailId, String password) {
		enterUserName(emailId);
		enterPassword(password);
		clickOnLogIn();
		clickOnActors();
	}

	/**
	 * This method deletes the selected user and verify the message
	 * 
	 * @param actionValue
	 * @param successMessage
	 */
	public void deleteUserAndVerify(String firstName, String actionValue, String successMessage) {
		selectUser(firstName);
		selectOptionFromAction(actionValue);
		clickOnGo();
		clickOnYesImSure();
		verifyPatientIsDeleted(successMessage);
	}

	/**
	 * This method enters the userName
	 * 
	 * @param userName
	 */
	private void enterUserName(String userName) {
		enterText(element("inp_username"), userName);
		logMessage("User enter username on Admin Portal");
	}

	/**
	 * This method enters the password
	 * 
	 * @param password
	 */
	private void enterPassword(String password) {
		enterText(element("inp_password"), password);
		logMessage("User enter password on Admin Portal");
	}

	/**
	 * This method clicks on LogIn button
	 */
	private void clickOnLogIn() {
		element("inp_logIn").click();
		logMessage("User clicks on LogIn button");
	}

	/**
	 * This method clicks on Actors under Zapprx
	 */
	private void clickOnActors() {
		element("link_role", "Actors").click();
		logMessage("User clicks on Actors under Zapprx");
	}

	/**
	 * This method select the patient from the Actors list
	 */
	private void selectUser(String firstName) {
		wait.waitForStableDom(250);
		element("inp_check", firstName).click();
		logMessage("User select the user to be deleted");
	}

	/**
	 * This method select the value from Action dropdown
	 * 
	 * @param actionValue
	 */
	private void selectOptionFromAction(String actionValue) {
		selectProvidedTextFromDropDown(element("select_action"), actionValue);
		logMessage("User selects " + actionValue + " from Actions Dropdown");
	}

	/**
	 * This method clicks on Go button
	 */
	private void clickOnGo() {
		element("btn_go").click();
		logMessage("User clicks on Go button");
	}

	/**
	 * This method clicks on Yes, I'm Sure button
	 */
	private void clickOnYesImSure() {
		element("inp_yes").click();
		logMessage("User clicks on Yes, I'm Sure button");
	}

	/**
	 * This method verifies Patient deletion message is displayed
	 * 
	 * @param successMessage
	 */
	private void verifyPatientIsDeleted(String successMessage) {
		try {
			Assert.assertEquals(element("li_successMsg").getText(), successMessage);
			logMessage("Assertion Passed: Message " + successMessage + " is displayed");
		} catch (AssertionError e) {
			logMessage("Assertion Failed: Message " + successMessage + " is not displayed");
		}
	}
}
