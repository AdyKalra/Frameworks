package com.zapprx.testing.end2endtests.pageActions.dosage;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class RADosagePageActions extends GetPage {
	WebDriver driver;

	public RADosagePageActions(WebDriver driver) {
		super(driver, "physician/DosagePage");
		this.driver = driver;
	}

	/**
	 * This method enters value for sign or instructions
	 */
	public void enterSigOrInst(String instOrSig) {
		enterText(element("txt_directionsInst"), "instOrSig");
		logMessage("User enters value for Sig");
	}

	/**
	 * This method enters the updated value for titration refill
	 * 
	 * @param refill
	 */
	public void editRefill(String refill) {
		enterText(element("inp_refills"), refill);
		logMessage("User edit the Titration refills value and enters " + refill);
	}

	/**
	 * This method verifies dose field is checked or not
	 */
	public void verifyDoseFieldIsUnchecked() {
		isElementDisplayed("inp_dose");
		Assert.assertFalse(element("inp_dose").isSelected(), "Assertion Failed: Dose field is checked");
		logMessage("Assertion Passed: Dose field is unchecked");
	}

	/**
	 * This method validates the options are present under directions field
	 */
	public void verifyDirOptionsArePresentAndNotSelected() {
		isElementDisplayed("inp_otherWeek");
		Assert.assertTrue(element("inp_otherWeek").getAttribute("class").contains("ng-pristine"),
				"Assertion Failed: Every other week option is selected under Directions");
		logMessage("Assertion Passed: Every other week option is not selected under Directions");
		isElementDisplayed("inp_everyWeek");
		Assert.assertTrue(element("inp_everyWeek").getAttribute("class").contains("ng-pristine"),
				"Assertion Failed: Every week option is selected under Directions");
		logMessage("Assertion Passed: Every week option is not selected under Directions");
		isElementDisplayed("inp_dirOther");
		Assert.assertTrue(element("inp_dirOther").getAttribute("class").contains("ng-pristine"),
				"Assertion Failed: Other option is selected under Directions");
		logMessage("Assertion Passed: Other option is not selected under Directions");
	}

	/**
	 * This method enter instructions for Actrema IV
	 * 
	 * @param inst
	 */
	public void enterOtherInstructions(String inst) {
		enterText(element("txt_actremaDirInst"), inst);
		logMessage("User enters " + inst + "in Instructions field for Other field");
	}

	/**
	 * This method validates directions field is a required field
	 * 
	 * @param directionOption
	 */
	public void verifyDirectionIsRequiredField() {
		Assert.assertTrue(element("div_dirctns", "Directions").getAttribute("class").contains("required"),
				"Assertion Failed: Directions is not a required field");
		logMessage("Assertion Passed: Directions is a required field");
	}

	/**
	 * This method enters dosage in Maintain Dosage
	 * 
	 * @param dosage
	 */
	public void enterMaintainDosage(String dosage) {
		enterText(element("inp_maintainDosage"), dosage);
		logMessage("User enters " + dosage + "in Maintain Dosage");
	}
}
