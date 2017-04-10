package com.zapprx.testing.end2endtests.pageActions.physician;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class MedicationsPageActions extends GetPage {
	WebDriver driver;

	public MedicationsPageActions(WebDriver driver) {
		super(driver, "physician/MedicationsPage");
		this.driver = driver;
	}

	/**
	 * This method clicks on medication name
	 * 
	 * @param medicationName
	 */
	public void clickOnMedication(String medicationName) {
		element("span_medName", medicationName).click();
		logMessage("User clicks on " + medicationName + " on Medications Page");
	}

	/**
	 * This method clicks on Manufacturer link
	 */
	public void clickOnManufacturerLink() {
		element("a_manufacturerLink").click();
		logMessage("User clicks on Manufacturer link");
	}

	/**
	 * This method verifies title for manufacturer page
	 */
	public void verifyManufacturerPage(String manufacturerName) {
		changeWindow(1);
		wait.waitForPageToLoadCompletely();
		Assert.assertEquals(manufacturerName, getPageTitle(),
				"Assertion Failed: User is not on Manufacturer Page");
		logMessage("Assertion Passed: User is on Manufacturer Page");
	}
}
