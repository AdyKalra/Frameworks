package com.zapprx.testing.end2endtests.pageActions.dosage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class MSDosagePageActions extends GetPage {
	WebDriver driver;

	public MSDosagePageActions(WebDriver driver) {
		super(driver, "physician/DosagePage");
		this.driver = driver;
	}

	/**
	 * This method validates days supply field is not present
	 */
	public void verifyDaysSupplyFieldIsNotPresent() {
		isElementNotDisplayed("inp_daysSupply");
	}
	
	/**
	 * This method validates invalid text is displayed or not
	 */
	public void verifyInvalidText(String invalidTxt) {
		for (WebElement hdr : elements("div_hdr")) {
			Assert.assertFalse(hdr.getText().contains(invalidTxt), "Assertion Failed: Invalid Text is displayed");
			logMessage("Assertion Passed: Invalid Text is not displayed");
		}
	}
	
	/**
	 * This method checks additional question is displayed for dosage page
	 */
	public void verifyDrugAdministeredOptionIsDisplayed(String viewAddStat) {
		Assert.assertEquals(element("div_viewAddQusTitle").getText(), viewAddStat,
				"Assertion Failed: View additional enrollment question field contents is not displayed");
		isElementDisplayed("sel_drugAdm");
		logMessage("View additional enrollment question field " + element("div_viewAddQusTitle").getText()
				+ " is displayed");
	}
	
	/**
	 * This method checks Direction option "Inject 44 mcg" is displayed on
	 * dosage page
	 */
	public void directionOptionIsSelected(String dirOption) {
		isElementDisplayed("inp_dirOption", dirOption);
		Assert.assertTrue(element("inp_dirOption", dirOption).isSelected(),
				"Assertion Failed: Direction Option is not Selected");
		logMessage("Direction Option is Selected " + element("inp_dirOption", dirOption).isSelected());

	}
}
