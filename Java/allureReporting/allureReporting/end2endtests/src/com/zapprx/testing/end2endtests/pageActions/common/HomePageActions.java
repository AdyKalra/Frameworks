package com.zapprx.testing.end2endtests.pageActions.common;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;
import com.zapprx.testing.end2endtests.automation.utils.CustomFunctions;

public class HomePageActions extends GetPage {
	WebDriver driver;

	public HomePageActions(WebDriver driver) {
		super(driver, "common/HomePage");
		this.driver = driver;
	}

	/**
	 * This method clicks Register Button on Home Page
	 */
	public void clickOnRegister() {
		wait.waitForLoaderToDisappear();
		isElementDisplayed("link_register");
		executeJavascript("document.getElementsByClassName('container')[1].getElementsByTagName('button')[0].click();");
		logMessage("Physician clicks on Register to create a new patient");
	}

	/**
	 * This method clicks Prescribe Button on Home Page
	 */
	public void clickOnPrescribe() {
		wait.waitForLoaderToDisappear();
		element("link_prescribe").click();
		logMessage("Physician clicks on Prescribe to create a new patient");
	}

	// *****************************************PatientPortal********************************************//
	/**
	 * This method verify notification is not displayed if physician does not
	 * authorize
	 */
	public void verifyNotificationIsNotDisplayed(String medicationName) {
		Assert.assertTrue(elements("link_notification", medicationName).size() <= 1,
				"Assertion Failed: Notification is getting displayed");
		logMessage("Assertion Passed: Notification is not getting displayed");
	}

	/**
	 * This method clicks on Share authorization message
	 * 
	 * @param medicationName
	 */
	public void signHealthShare(String medicationName) {
		element("link_notification", medicationName).click();
		logMessage("User click on share authorization message");
	}

	/**
	 * This method verifies rems notification message is displayed once
	 * physician authorize
	 * 
	 * @param remsAuthMsg
	 */
	public void verifyShareAuthMessage(String medicationName) {
		isElementDisplayed("div_notificationMsg");
		Assert.assertEquals(element("div_notificationMsg").getText().trim(),
				"Your prescription " + medicationName + " requires a REMS or permission-to-share authorization.",
				"Assertion Failed: Health Share Authorization message is not displayed");
		logMessage("Assertion Passed: Health Share Authorization message is displayed");
	}

	/**
	 * This method clicks on Patient Signed
	 */
	public void clickOnPatientSigned() {
		isElementDisplayed("li_patientSigned");
		element("li_patientSigned").click();
		logMessage("User clicks on Patient Signed but Incomplete");
	}

	/**
	 * This method clicks on Ready for pharmacy
	 */
	public void clickOnReadyForPharmacy() {
		wait.waitForElementToBeVisible(element("li_readyForPhar"));
		isElementDisplayed("link_register");
		element("li_readyForPhar").click();
		logMessage("User clicks on Ready For Pharmacy");
	}

	/**
	 * This method clicks on Mark As Sent
	 * 
	 * @param patientLastNAme
	 */
	public void clickOnMarkAsSent(String patientLastName) {
		wait.waitForStableDom(250);
		isElementDisplayed("button_markAsSent", patientLastName);
		element("button_markAsSent", patientLastName).click();
		logMessage("This method clicks on Mark As Sent");
	}

	/**
	 * This method selects current date
	 */
	public String selectCurrentDate() {
		String date = CustomFunctions.getCurrentDateWithSep();
		wait.waitForElementToBeVisible(element("inp_date"));
		element("inp_date").click();
		isElementDisplayed("inp_date");
		enterText(element("inp_date"), date);
		wait.waitForElementToBeClickable(element("span_calButton"));
		wait.waitForStableDom(250);
		executeJavascript(
				"document.getElementById('mark-rx-modal').getElementsByClassName('input-group-btn')[0].childNodes[2].click();");
		logMessage("User clicks on calendar button");
		return date;
	}

	/**
	 * This method clicks on close
	 */
	public void clickOnClose() {
		wait.waitForStableDom(250);
		executeJavascript("document.getElementById('confirmation-modal').getElementsByTagName('i')[0].click();");
		logMessage("User clicks on close");
	}

	/**
	 * This method clicks on patient name
	 */
	public void selectPatient(String patientName) {
		isElementDisplayed("td_patientName", patientName);
		element("td_patientName", patientName).click();
		logMessage("User clicks on patient name");
	}

	/**
	 * This method clicks on patient name under Patient Signed but Incomplete
	 * tab
	 */
	public void selectPatientFromPatientSigned(String patientName) {
		scrollDown(element("tr_patientName", patientName));
		isElementDisplayed("tr_patientName", patientName);
		wait.waitForStableDom(250);
		element("tr_patientName", patientName).click();
		logMessage("User clicks on patient name");
	}

	/**
	 * This method clicks on add new practice
	 */
	public void clickOnAddNewPrac() {
		element("btn_addNewPrac").click();
		logMessage("User clicks on Add new practice");
	}

	/**
	 * This method clicks on Pharmacies
	 */
	public void clickOnPharmacies() {
		element("li_pharmacies").click();
		logMessage("User clicks on Pharmacies");
	}

	/**
	 * This method clicks on Add New Pharmacy
	 */
	public void clickOnAddNewPharmacy() {
		element("btn_addNewPharmacy").click();
		logMessage("User clicks on Add New Pharmacy");
	}

}
