package com.zapprx.testing.end2endtests.pageActions.physician;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class PatientPageActions extends GetPage {
	WebDriver driver;

	public PatientPageActions(WebDriver driver) {
		super(driver, "physician/PatientPage");
		this.driver = driver;
	}

	/**
	 * This method search Patient and Prescribe Medication
	 * 
	 * @param searchType
	 */
	public void searchPatient(String patientLastName) {
		enterPatientDetail(patientLastName);
		clickOnSearchIcon();
		verifyPatientIsDisplayed(patientLastName);
	}

	/**
	 * This method validates the Date Of Birth
	 * 
	 * @param dob
	 */
	public void verifyDateOfBirth(String dob, String patientLastName) {
		String month = getMonth(element("td_dob", patientLastName).getText().split(" ")[0]);
		String date = element("td_dob", patientLastName).getText().split(" ")[1].split(",")[0];
		// Padding with leading zero if the date is < 10 to deal with assertion
		// error for example DOB 12/05/2016
		String formattedDate = String.format("%02d", Integer.parseInt(date));
		String year = element("td_dob", patientLastName).getText().split(" ")[2];
		isElementDisplayed("td_dob", patientLastName);
		Assert.assertEquals(String.valueOf(month) + "/" + formattedDate + "/" + year, dob,
				"Assertion Failed: The Date Of Birth is displayed incorrect");
		logMessage("Assertion Passed: The Date Of Birth is displayed correctly");
	}

	/**
	 * This method clicks on complete profile
	 */
	public void clickOnCompleteProfile() {
		element("div_cmplteProfile").click();
		logMessage("User clicks on Complete Profile");
	}

	/**
	 * This method enters Patient Name in search box along filter
	 */
	public void enterPatientDetail(String patientDetail) {
		isElementDisplayed("inp_search");
		enterText(element("inp_search"), patientDetail);
		logMessage("User enter the " + patientDetail + " in the search field");
	}

	/**
	 * This method clicks on Search icon
	 */
	public void clickOnSearchIcon() {
		executeJavascript("document.getElementById('doctor-nav-toggle').click();");
		isElementDisplayed("btn_searchIcon");
		wait.waitForElementToBeClickable(element("btn_searchIcon"));
		wait.waitForStableDom(250);
		executeJavascript(
				"document.getElementsByClassName('zp-searchbar-input-wrapper')[0].getElementsByTagName('button')[0].click()");
		logMessage("User click on Search Icon");
		if (getEnv().equalsIgnoreCase("test")) {
			wait.waitForElementToDisappear(element("div_loader"));
		}
	}

	/**
	 * This method verifies Patient Name is Displayed
	 */
	public void verifyPatientIsDisplayed(String patientName) {
		executeJavascript("window.scroll(0,200)");
		executeJavascript("window.scroll(0,0)");
		if (elements("td_patList").size() == 1) {
			wait.resetImplicitTimeout(1);
			try {
				isElementDisplayed("txt_patientName", patientName);
			} catch (StaleElementReferenceException e) {
				isElementDisplayed("txt_patientName", patientName);
			}
			wait.resetImplicitTimeout(wait.getTimeout());
			Assert.assertTrue(element("txt_patientName", patientName).isDisplayed(),
					"Assertion Failed : Patient Name is not getting displayed");
			logMessage("Assertion Passed : Patient Name is getting displayed");
		}
	}

	/**
	 * This method validated MRN Number is getting displayed
	 * 
	 * @param mrnNo
	 */
	public void verifyMRNNoIsDisplayed(String mrnNo) {
		isElementDisplayed("txt_mrnNo", mrnNo);
		Assert.assertTrue(element("txt_mrnNo", mrnNo).isDisplayed(),
				"Assertion Failed : MRN No is not getting displayed");
		logMessage("Assertion Passed : MRN No is getting displayed");
	}

	/**
	 * This method select the patient
	 */
	public void selectPatientByName(String patientName) {
		wait.resetImplicitTimeout(1);
		if (element("td_patList").getText().contains(patientName)) {
			try {
				isElementDisplayed("txt_patientName", patientName);
				wait.waitForElementToBeClickable(element("txt_patientName", patientName));
				executeJavascript("document.getElementById('datagrid').getElementsByClassName('sorted')[0].click();");
			} catch (StaleElementReferenceException e) {
				isElementDisplayed("txt_patientName", patientName);
				wait.waitForElementToBeClickable(element("txt_patientName", patientName));
				executeJavascript("document.getElementById('datagrid').getElementsByClassName('sorted')[0].click();");
			}
			logMessage("User clicks on Patient Name");
			wait.resetImplicitTimeout(wait.timeout);
		}
	}

	/**
	 * This method clicks on Prescribe button
	 */
	public void clickOnPrescribe() {
		wait.waitForLoaderToDisappear();
		element("div_prescribe").click();
		logMessage("User clicks on Prescribe");
	}

	/**
	 * This method clicks on Home
	 */
	public void clickOnHome() {
		element("div_home").click();
		logMessage("User clicks on Home");
	}

	/**
	 * This method clicks prescribe on patient modal
	 */
	public void clickPrescribeOnPatientModal() {
		isElementDisplayed("div_patientPrescribe");
		element("div_patientPrescribe").click();
		logMessage("User clicks on Prescribe on Patient Modal");
	}

	/**
	 * This method clicks on patient consent
	 */
	public void clickOnPatientConsent() {
		isElementDisplayed("div_patientConsent");
		element("div_patientConsent").click();
		logMessage("User clicks on Patient Consent");
	}

	/**
	 * This method verifies header text
	 */
	public void verifyHeaderText() {
		isElementDisplayed("hdrTxt_shareAuth");
	}

	/**
	 * This method clicks on View Profile button
	 */
	public void clickOnViewProfile() {
		wait.waitForLoaderToDisappear();
		element("div_viewProfile").click();
		logMessage("User clicks on View Profile");
	}

}
