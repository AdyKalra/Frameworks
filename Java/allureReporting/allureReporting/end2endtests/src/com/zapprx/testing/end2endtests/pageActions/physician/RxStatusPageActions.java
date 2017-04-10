package com.zapprx.testing.end2endtests.pageActions.physician;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class RxStatusPageActions extends GetPage {
	WebDriver driver;

	public RxStatusPageActions(WebDriver driver) {
		super(driver, "physician/RxStatusPage");
		this.driver = driver;
	}

	/**
	 * This method search and select Patient
	 * 
	 * @param patientName
	 */
	public void searchAndSelectPatient(String patientName) {
		enterPatientName(patientName);
		clickOnSearchIcon();
		verifyPatientIsDisplayed(patientName);
		selectPatientByName(patientName);
	}

	/**
	 * This method search and select Patient by name and medicine prescribed
	 * 
	 * @param patientName
	 * @param medicineName
	 */
	public void searchAndSelectPatient(String patientName, String medicineName) {
		enterPatientName(patientName);
		clickOnSearchIcon();
		verifyPatientIsDisplayed(patientName, medicineName);
		selectPatientByNameAndMed(patientName, medicineName);
	}

	/**
	 * This method clicks on Search icon
	 */
	public void clickOnSearchIcon() {
		element("li_openClose").click();
		isElementDisplayed("btn_searchIcon");
		executeJavascript(
				"document.getElementsByClassName('zp-searchbar-input-wrapper')[0].getElementsByTagName('button')[0].click()");
		logMessage("User click on Search Icon");
	}

	/**
	 * This method enters Patient Name in search box along filter
	 */
	public void enterPatientName(String patientName) {
		isElementDisplayed("inp_search");
		enterText(element("inp_search"), patientName);
		logMessage("User enter the First Name in the search field");
	}

	/**
	 * This method verifies Patient Name is Displayed
	 */
	public void verifyPatientIsDisplayed(String firstName) {
		wait.resetImplicitTimeout(5);
		try {
			isElementDisplayed("txt_patientName", firstName);
		} catch (StaleElementReferenceException e) {
			isElementDisplayed("txt_patientName", firstName);
		}
		wait.resetImplicitTimeout(wait.timeout);
	}

	/**
	 * This method verifies Patient Name with prescribed medication is Displayed
	 */
	public void verifyPatientIsDisplayed(String firstName, String medicineName) {
		wait.resetImplicitTimeout(5);
		try {
			isElementDisplayed("txt_patNameMed", firstName, medicineName);
		} catch (StaleElementReferenceException e) {
			isElementDisplayed("txt_patNameMed", firstName, medicineName);
		}
		wait.resetImplicitTimeout(wait.timeout);
	}

	/**
	 * This method select the patient
	 */
	public void selectPatientByName(String patientName) {
		wait.resetImplicitTimeout(2);
		try {
			isElementDisplayed("txt_patientName", patientName);
			if (element("td_patList").getText().contains(patientName)) {
				wait.waitForStableDom(250);
				wait.waitForElementToBeClickable(element("txt_patientName", patientName));
				executeJavascript("document.getElementById('rx-status-table').getElementsByTagName('td')[0].click()");
				logMessage("User clicks on Patient Name");
			}
		} catch (StaleElementReferenceException e) {
			isElementDisplayed("txt_patientName", patientName);
			if (element("td_patList").getText().contains(patientName)) {
				wait.waitForStableDom(250);
				wait.waitForElementToBeClickable(element("txt_patientName", patientName));
				executeJavascript("document.getElementById('rx-status-table').getElementsByTagName('td')[0].click()");
				logMessage("User clicks on Patient Name");
			}
		}
		wait.resetImplicitTimeout(wait.timeout);
	}

	/**
	 * This method selects patient by name and medicine
	 * 
	 * @param patientName
	 * @param medicineName
	 */
	public void selectPatientByNameAndMed(String patientName, String medicineName) {
		wait.resetImplicitTimeout(1);
		if (elements("td_patList").get(0).getText().contains(patientName)) {
			try {
				wait.waitForStableDom(250);
				clickUsingXpathInJavaScriptExecutor(element("txt_patNameMed", patientName, medicineName));
			} catch (StaleElementReferenceException e) {
				wait.waitForStableDom(250);
				clickUsingXpathInJavaScriptExecutor(element("txt_patNameMed", patientName, medicineName));
			}
			logMessage("User clicks on Patient Name");
			wait.resetImplicitTimeout(wait.timeout);
		}
	}

	/**
	 * This method clicks on Drug Name
	 * 
	 * @param drugName
	 */
	public void clickOnDrugName() {
		wait.waitForElementToBeClickable(element("td_drug"));
		element("td_drug").click();
		logMessage("user clicks on Prescribed Drug");
	}

	/**
	 * This method verify the prescribed drug and clicks on the same
	 * 
	 * @param drugName
	 */
	public void verifyAndClickOnPrescribedDrug(String drugName) {
		Assert.assertEquals(element("link_drugName").getText(), drugName,
				"Assertion Failed: The prescribed drug " + drugName + " is not getting displayed");
		logMessage("Assertion Passed: The prescribed drug " + drugName + " is getting displayed");
		element("link_drugName").click();
		logMessage("User clicks on Prescribed drug");
	}

	/**
	 * This method verify the page title for Medication page
	 * 
	 * @param drugName
	 */
	public void verifyPageTitleForMedicationPage(String drugName) {
		verifyPageTitleContains(drugName);
	}

	/**
	 * This method clicks on FirstName
	 * 
	 * @param firstName
	 */
	public void clickOnPatientName(String firstName) {
		element("txt_patientName", firstName).click();
		logMessage("User clicks on First Name");
	}

	/**
	 * This method verifies progress status
	 * 
	 * @param patientName
	 * @param status
	 */
	public void verifyProgressStatus(String patientName, String status) {
		enterPatientName(patientName);
		clickOnSearchIcon();
		wait.waitForLoaderToDisappear();
		verifyPatientIsDisplayed(patientName);
		wait.waitForStableDom(250);
		logMessage(executeJavascriptReturnValue("return document.getElementsByTagName('td')[6].innerText"));
		Assert.assertEquals(executeJavascriptReturnValue("return document.getElementsByTagName('td')[6].innerText"),
				status, "Assertion Failed: Status " + status + " value is not displayed correctly");
		logMessage("Assertion Passed: Status is displayed correctly");
	}

	/**
	 * This method verifies patient is displayed on Rx status page and its
	 * status
	 * 
	 * @param patientName
	 * @param status
	 */
	public void verifyPatientAndStatusOnRxStatusPage(String patientName, String status) {
		enterPatientName(patientName);
		clickOnSearchIcon();
		verifyPatientIsDisplayed(patientName);
		verifyProgressStatus(patientName, status);
	}

	/**
	 * This method clicks on View Rx Details
	 */
	public void clickOnViewRxDetails() {
		isElementDisplayed("btn_viewCompleteRx", "View Rx Details");
		element("btn_viewCompleteRx", "View Rx Details").click();
		logMessage("User clicks on View Rx Details");
	}

	/**
	 * This method clicks on Complete Rx
	 */
	public void clickOnCompleteRx() {
		isElementDisplayed("btn_viewCompleteRx", "Complete Rx");
		executeJavascript("document.getElementById('rx-status-update-view-rx').click();");
		logMessage("User clicks on Complete Rx");
	}

	/**
	 * This method verifies patient is displayed on Rx status page and its PA
	 * status
	 * 
	 * @param patientName
	 * @param status
	 */
	public void verifyPatientAndPAStatusOnRxStatusPage(String patientName, String status) {
		enterPatientName(patientName);
		clickOnSearchIcon();
		verifyPatientIsDisplayed(patientName);
		verifyPAStatus(patientName, status);
	}

	/**
	 * This method verifies PA status
	 * 
	 * @param patientName
	 * @param status
	 */
	public void verifyPAStatus(String patientName, String status) {
		Assert.assertEquals(element("td_paStatus", patientName).getText(), status,
				"Assertion Failed: Status " + status + " value is not displayed correctly");
		logMessage("Assertion Passed: Status " + status + " is displayed correctly");
	}

	/**
	 * This method verifies PA alert icon is displayed on prescription listings
	 * page
	 * 
	 * @param patientName
	 */
	public void verifyPAUrgentAlertIconIsDisplayed(String patientName) {
		isElementDisplayed("i_paUrgntAlertIcon", patientName);
	}

	/**
	 * This method verifies PA alert icon is not displayed on prescription
	 * listings page
	 * 
	 * @param patientName
	 */
	public void verifyPAUrgentAlertIconNotDisplayed(String patientName) {
		isElementNotDisplayed("i_paUrgntAlertIcon", patientName);
	}

	/**
	 * This method verifies that 'Update Rx' button on Rx status page is
	 * disabled after cancelling prescription
	 */
	public void verifyUpdateRxIsDisabled() {
		Assert.assertTrue(element("btn_updateRx").getAttribute("disabled").equalsIgnoreCase("true"),
				"Assertion Failed: Update Rx button is not disabled");
		logMessage("Assertion Passed: Update Rx button is disabled");
	}
}
