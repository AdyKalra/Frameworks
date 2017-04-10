package com.zapprx.testing.end2endtests.pageActions.admin;

import static com.zapprx.testing.end2endtests.automation.utils.CustomFunctions.getStringWithTimestamp;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class PharmaciesPageActions extends GetPage {
	WebDriver driver;

	public PharmaciesPageActions(WebDriver driver) {
		super(driver, "admin/PharmaciesPage");
		this.driver = driver;
	}

	/**
	 * This method verifies user is on Add New Pharmacy page
	 */
	public void verifyUserIsOnAddNewPharmacy() {
		Assert.assertTrue(element("hdr_newPharmacy").isDisplayed(),
				"Assertion Failed: User is not on New Pharmacy page");
		logMessage("Assertion Passed: User is on New Pharmacy page");
	}

	/**
	 * This method enters practice name
	 */
	public String enterPharmacyName() {
		String pharmacyName = getStringWithTimestamp("PharmacyName");
		enterText(element("inp_name"), pharmacyName);
		logMessage("User enters " + pharmacyName + " in Pharmacy Name field");
		return pharmacyName;
	}

	/**
	 * This method enters address
	 */
	public void enterAddress() {
		String address = getStringWithTimestamp("address");
		enterText(element("inp_street"), address);
		logMessage("User enters " + address + " in Address field");
	}

	/**
	 * This method enters city
	 */
	public void enterCity() {
		String city = getStringWithTimestamp("city");
		enterText(element("inp_city"), city);
		logMessage("User enters " + city + " in City field");
	}

	/**
	 * This method selects state
	 */
	public void selectState(String state) {
		selectProvidedTextFromDropDown(element("select_state"), state);
		logMessage("User selects " + state + " in State field");
	}

	/**
	 * This method enters value in zip field
	 */
	public void enterZip() {
		String zip = getStringWithTimestamp("city");
		enterText(element("inp_zip"), zip);
		logMessage("User enters " + zip + " in Zip field");
	}

	/**
	 * This method clicks on Save Pharmacy Profile
	 */
	public void clickOnSavePharmacyProfile() {
		isElementDisplayed("btn_savePharmacyProfile");
		scrollDown(element("btn_savePharmacyProfile"));
		executeJavascript("document.getElementById('practice-profile-edit-save').click();");
		logMessage("User clicks on Save Pharmacy Profile");
	}

	/**
	 * This method validates the newly created practice name is displayed in the
	 * list
	 * 
	 * @param pracName
	 */
	public void verifyNewlyCreatedPharmacy(String pharName) {
		List<WebElement> pharNames = elements("td_pharName");
		for (WebElement element : pharNames) {
			if (element.getText().contains(pharName)) {
				logMessage("Assertion Passed: Newly created Practice Name " + pharName + " is displayed");
			}
		}
	}

	/**
	 * This method clicks on pharmacy name
	 * 
	 * @param pharmacyName
	 */
	public void clickOnPharmacyName(String pharmacyName) {
		element("td_pharmacy", pharmacyName).click();
		logMessage("User clicks on " + pharmacyName + " from Pharmacies list");
	}

	// ********************************EditPharmacy***************************************//

	/**
	 * This method clicks on edit pharmacy
	 */
	public void clickOnEditPharmacy() {
		isElementDisplayed("btn_editPhar");
		scrollDown(element("btn_editPhar"));
		element("btn_editPhar").click();
		logMessage("This method clicks on Edit Pharmacy");
	}

	/**
	 * This method validated the success message after pharmacy is updated
	 * 
	 * @param pharmacyName
	 */
	public void verifyPharmacyIsUpdated(String pharmacyName) {
		isElementDisplayed("p_editSuccessMsg");
		wait.waitForElementTextToContain(element("p_editSuccessMsg"), pharmacyName);
		Assert.assertEquals(element("p_editSuccessMsg").getText().trim(),
				pharmacyName + " has been updated successfully.",
				"Assertion Failed: Pharmacy " + pharmacyName + " has not been updated successfully");
		logMessage("Assertion Passed: Pharmacy " + pharmacyName + " has been updated successfully");
	}

	/**
	 * This method clicks on Add User
	 */
	public void addUser() {
		clickUsingXpathInJavaScriptExecutor(element("btn_addUser"));
		logMessage("User clicks on Add User on Pharmacy profile");
	}

	/**
	 * This method selects role for new user
	 */

	public void selectRole(String role) {
		selectProvidedTextFromDropDown(element("select_role"), role);
		logMessage("User selects role as " + role);
	}

	/**
	 * This method clicks on Add Pharmacist
	 */
	public void clickOnAddPharmacist() {
		element("btn_addPhar").click();
		logMessage("User clicks on Add Pharmacist");
	}

}