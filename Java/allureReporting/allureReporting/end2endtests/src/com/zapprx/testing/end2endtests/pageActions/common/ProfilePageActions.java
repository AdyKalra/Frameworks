package com.zapprx.testing.end2endtests.pageActions.common;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class ProfilePageActions extends GetPage {
	WebDriver driver;

	public ProfilePageActions(WebDriver driver) {
		super(driver, "common/ProfilePage");
		this.driver = driver;
	}

	/**
	 * This method clicks on Edit Profile
	 */
	public void clickOnEditProfile() {
		wait.waitForLoaderToDisappear();
		element("btn_editProfile").click();
		logMessage("User clicks on Edit Profile");
	}

	/**
	 * This method clear the Date of Birth field
	 */
	public void clearDOBfield() {
		element("inp_dob").clear();
		logMessage("User clears text in Date of Birth field");
	}

	/**
	 * This method clicks on Save button
	 */
	public void clickOnSave() {
		element("btn_save").click();
		logMessage("User clicks on Save");
	}

	/**
	 * This method verifies that user is not able to save profile
	 */
	public void verifyUserisUnableToSaveDetails() {
		isElementNotDisplayed("btn_editProfile");
	}

	/**
	 * This method clicks on Insurance Tab
	 */
	public void clickOnInsurance() {
		isElementDisplayed("span_tab", "Insurance");
		element("span_tab", "Insurance").click();
		logMessage("User clicks on Insurance Tab");
	}

	/**
	 * This method clicks on Medical Tab
	 */
	public void clickOnMedicalTab() {
		isElementDisplayed("span_tab", "Medical");
		element("span_tab", "Medical").click();
		logMessage("User clicks on Medical Tab");
	}

	/**
	 * This method selects value from In-Patient dropdown
	 */
	public void selectInPatValue(String value) {
		selectProvidedTextFromDropDown(element("select_inPat"), value);
		logMessage("User selects " + value + " from In-Patient dropdown");
	}

	/**
	 * This methods validate value for In-Patient
	 * 
	 * @param value
	 */
	public void verifyInPatientValue(String value) {
		refreshPage();
		clickOnMedicalTab();
		isElementDisplayed("slct_inPatDis");
		Assert.assertEquals(element("slct_inPatDis").getAttribute("value"), value,
				"Assertion Failed: Value for In-Patient is displayed incorrect");
		logMessage("Assertion Passed: Value for In-Patient is displayed incorrect");
	}

	/**
	 * This method clicks on Pharmacy Benefit
	 */
	public void clickOnPharBenefit() {
		element("btn_addPharBen").click();
		logMessage("User clicks on Pharmacy Benefit");
	}

	/**
	 * This method enter the values to add a pharmacy benefit
	 * 
	 * @param pharBenValues
	 */
	public void addPharBenefit(String... pharBenValues) {
		enterText(element("inp_pharBen", "Pharmacy Benefits", "Provider"), pharBenValues[0]);
		logMessage("User enters Provider value for Pharmacy Benefit");
		enterText(element("inp_pharBen", "Pharmacy Benefits", "Plan"), pharBenValues[1]);
		logMessage("User enters Plan value for Pharmacy Benefit");
	}

	/**
	 * This method verifies that the pharmacy benefit is added successfully
	 * 
	 * @param pharBenValues
	 */
	public void verifyPharBenefitIsAdded(String... pharBenValues) {
		Assert.assertEquals(element("inp_pharBen", "Pharmacy Benefits", "Provider").getAttribute("value"),
				pharBenValues[0], "Assertion Failed: Incorrect Provider value was displayed");
		logMessage("Assertion Passed: Provider value was displayed correctly");
		Assert.assertEquals(element("inp_pharBen", "Pharmacy Benefits", "Plan").getAttribute("value"), pharBenValues[1],
				"Assertion Failed: Incorrect Plan value was displayed");
		logMessage("Assertion Passed: Plan value was displayed correctly");
	}

	/**
	 * This method clicks on 'Add Another Insurance Plan' button
	 */
	private void clickAddAnotherInsurance() {
		wait.waitForLoaderToDisappear();
		scrollDown(element("btn_addInsurance"));
		element("btn_addInsurance").click();
		logMessage("User clicks on 'Add Another Insurance Plan'");
	}

	/**
	 * This method verifies patient get option to add 'Secondary Insurance'
	 * after clicking on 'Add Another Insurance Plan' button
	 */
	public void verifySecondaryInsuranceOption() {
		clickOnEditProfile();
		clickAddAnotherInsurance();
		Assert.assertEquals(
				executeJavascriptReturnValue(
						"return document.getElementsByClassName('zp-form-header')[3].textContent;"),
				"Secondary Insurance", "Assertion Failed: Patient doesn't get option to add 'Secondary Insurance'");
		logMessage("Assertion Passed: Patient get option to add 'Secondary Insurance'");
	}
}
