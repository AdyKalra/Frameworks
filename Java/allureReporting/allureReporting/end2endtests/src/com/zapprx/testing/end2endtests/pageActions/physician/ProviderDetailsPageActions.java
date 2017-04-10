package com.zapprx.testing.end2endtests.pageActions.physician;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class ProviderDetailsPageActions extends GetPage {
	WebDriver driver;

	public ProviderDetailsPageActions(WebDriver driver) {
		super(driver, "physician/ProviderDetailsPage");
		this.driver = driver;
	}

	/**
	 * This method verify all required fields on Provider Details Page
	 */
	public void verifyMandatoryFieldProviderDetails() {
		verifyMandatoryFieldPhysicianInfo();
		verifyMandatoryFieldContactInfo();
	}

	/**
	 * This method verify all mandatory fields under Physician Information
	 */
	private void verifyMandatoryFieldPhysicianInfo() {
		Assert.assertTrue(
				element("select_requiredField",
						element("slct_provider").getAttribute("id"))
						.getAttribute("class").contains("required"),
				"Assertion Failed: 'Select the Prescribing Provider' is not a mandatory field");
		logMessage("Assertion Passed: 'Select the Prescribing Provider' is a mandatory field");
		Assert.assertTrue(
				element("inp_requiredField",
						element("inp_firstName").getAttribute("id"))
						.getAttribute("class").contains("required"),
				"Assertion Failed: 'First Name' is not a mandatory field");
		logMessage("Assertion Passed: 'First Name' is a mandatory field");
		Assert.assertTrue(
				element("inp_requiredField",
						element("inp_lastName").getAttribute("id"))
						.getAttribute("class").contains("required"),
				"Assertion Failed: 'Last Name' is not a mandatory field");
		logMessage("Assertion Passed: 'Last Name' is a mandatory field");
		Assert.assertTrue(
				element("inp_requiredField",
						element("inp_state").getAttribute("id")).getAttribute(
						"class").contains("required"),
				"Assertion Failed: 'State License Number' is not a mandatory field");
		logMessage("Assertion Passed: 'State License Number' is a mandatory field");
		Assert.assertTrue(
				element("inp_requiredField",
						element("inp_deaNo").getAttribute("id")).getAttribute(
						"class").contains("required"),
				"Assertion Failed: 'DEA Number' is not a mandatory field");
		logMessage("Assertion Passed: 'DEA Number' is a mandatory field");
		Assert.assertTrue(
				element("inp_requiredField",
						element("inp_npiNo").getAttribute("id")).getAttribute(
						"class").contains("required"),
				"Assertion Failed: 'NPI Number' is not a mandatory field");
		logMessage("Assertion Passed: 'NPI Number' is a mandatory field");
	}

	/**
	 * This method verifies all mandatory fields under Contact Information
	 */
	private void verifyMandatoryFieldContactInfo() {
		Assert.assertTrue(
				element("inp_requiredField",
						element("inp_address").getAttribute("id"))
						.getAttribute("class").contains("required"),
				"Assertion Failed: 'Address' is not a mandatory field");
		logMessage("Assertion Passed: 'Address' is a mandatory field");
		Assert.assertTrue(
				element("inp_requiredField",
						element("inp_city").getAttribute("id")).getAttribute(
						"class").contains("required"),
				"Assertion Failed: 'City' is not a mandatory field");
		logMessage("Assertion Passed: 'City' is a mandatory field");
		Assert.assertTrue(
				element("select_requiredField",
						element("select_state").getAttribute("id"))
						.getAttribute("class").contains("required"),
				"Assertion Failed: 'State ' is not a mandatory field");
		logMessage("Assertion Passed: 'State ' is a mandatory field");
		Assert.assertTrue(
				element("inp_requiredField",
						element("inp_zip").getAttribute("id")).getAttribute(
						"class").contains("required"),
				"Assertion Failed: 'Zip' is not a mandatory field");
		logMessage("Assertion Passed: 'Zip' is a mandatory field");
	}
}
