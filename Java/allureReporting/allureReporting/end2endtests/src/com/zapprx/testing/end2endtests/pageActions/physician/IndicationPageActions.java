package com.zapprx.testing.end2endtests.pageActions.physician;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class IndicationPageActions extends GetPage {
	WebDriver driver;

	public IndicationPageActions(WebDriver driver) {
		super(driver, "physician/IndicationPage");
		this.driver = driver;
	}

	// /**
	// * This method selects Practice specialty and indication
	// *
	// * @param specialtyName
	// * @param indicationName
	// */
	// public void selectPracticeSpecialtyAndIndication(String specialtyName,
	// String indicationName) {
	// scrollDown(element("div_practiceSpecialty", specialtyName));
	// element("div_practiceSpecialty", specialtyName).click();
	// logMessage("User selects " + specialtyName + " specialty from Indications
	// Page");
	// wait.waitForElementToBeClickable(element("a_indicationName",
	// specialtyName, indicationName));
	// element("a_indicationName", specialtyName, indicationName).click();
	// logMessage("User selects " + indicationName + " indication under " +
	// specialtyName
	// + "specialty from Indications Page");
	// }

	/**
	 * This method select indication on Indications Page
	 * 
	 * @param indicationName
	 */
	public void selectIndication(String indicationName) {
		scrollDown(element("div_indicationName", indicationName));
		element("div_indicationName", indicationName).click();
		logMessage("User selects " + indicationName + " from Indications Page");
	}

	/**
	 * This method selects the indication for patient consent
	 * 
	 * @param indicationName
	 */
	public void selectIndicationForPatientConsent(String indicationName) {
		isElementDisplayed("patConsent_indicationName");
		selectProvidedTextFromDropDown(element("patConsent_indicationName"), indicationName);
		logMessage("User selects " + indicationName + " from Indications Page for Patient Consent");
	}

	/**
	 * This method verifies Patient name on Rx summary header
	 * 
	 * @param lastName
	 */
	public void verifyPatientNameOnRxSummaryHeader(String lastName) {
		Assert.assertTrue(
				element("h4_rxSummaryHeader").getText().contains(getYamlValue("physician.firstName") + " " + lastName),
				"Assertion Failed: Patient name is not present under Prescribing Summary Header");
		logMessage("Assertion Passed: Patient name is present under Prescribing Summary Header");
	}
}
