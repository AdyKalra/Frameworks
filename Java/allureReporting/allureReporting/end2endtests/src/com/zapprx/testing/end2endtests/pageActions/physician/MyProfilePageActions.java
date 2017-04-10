package com.zapprx.testing.end2endtests.pageActions.physician;

import static com.zapprx.testing.end2endtests.automation.utils.CustomFunctions.getNextDate;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class MyProfilePageActions extends GetPage {
	WebDriver driver;

	public MyProfilePageActions(WebDriver driver) {
		super(driver, "physician/MyProfilePage");
		this.driver = driver;
	}

	/**
	 * This method clicks on Add New Doctor
	 */
	public void submitSignature() {
		wait.waitForElementToBeVisible(element("inp_address"));
		String address = element("inp_address").getAttribute("value");
		if (address.equalsIgnoreCase("") || address == null) {
			enterAddress();
			enterCity();
			selectState();
			enterZIP();
			enterSignature();
			clickOnSaveProfile();
			verifySignIsSaved();
		} else {
			logMessage("Signature is already present");
		}
	}

	/**
	 * This method enters address
	 */
	private void enterAddress() {
		enterText(element("inp_address"), "address");
		logMessage("User enters address");
	}

	/**
	 * This method enters city
	 */
	private void enterCity() {
		enterText(element("inp_city"), "city");
		logMessage("User enters city");
	}

	/**
	 * This method enters state
	 */
	private void selectState() {
		selectProvidedTextFromDropDown(element("inp_state"), "AL");
		logMessage("User selects state");
	}

	/**
	 * This method enters zip
	 */
	private void enterZIP() {
		enterText(element("inp_zip"), "11111");
		logMessage("User enters zip");
	}

	/**
	 * This method enters signature on canvas
	 */
	private void enterSignature() {
		wait.waitForStableDom(250);
		drawSignatureOnCanvas(element("canvas_pad"));
	}

	/**
	 * This method clicks on save profile
	 */
	private void clickOnSaveProfile() {
		element("btn_saveProfile").click();
		logMessage("User clicks on Save Profile");
	}

	/**
	 * This method clicks on Close/Okay on success modal
	 */
	private void clickOnCloseOkay() {
		isElementDisplayed("btn_closeOkay");
		element("btn_closeOkay").click();
		logMessage("User clicks on Close/Okay on success modal");
	}

	/**
	 * This method verifies signature is saved successfully
	 */
	private void verifySignIsSaved() {
		clickOnCloseOkay();
		refreshPage();
		isElementDisplayed("img_sign");
	}

	// ******************Delegate other users to sign for doctor***************

	/**
	 * This method search and selects User(Nurse/Doctor)
	 * 
	 * @param searchType
	 */
	public void searchAndSelectUser(String firstName) {
		enterUserName(firstName);
		verifyUserIsDisplayed(firstName);
		selectUserByName(firstName);
	}

	/**
	 * This method enters user name
	 * 
	 * @param lastName
	 */
	public void enterUserName(String firstName) {
		enterText(element("inp_userName"), firstName);
		logMessage("User enters " + firstName + "to delegate user to sign for doctor");
	}

	/**
	 * This method verifies user Name is Displayed
	 * 
	 * @param firstName
	 */
	private void verifyUserIsDisplayed(String firstName) {
		wait.resetImplicitTimeout(5);
		try {
			isElementDisplayed("txt_userName", firstName);
		} catch (StaleElementReferenceException e) {
			isElementDisplayed("txt_userName", firstName);
		}
		wait.resetImplicitTimeout(wait.timeout);

	}

	/**
	 * This method select the user
	 * 
	 * @param patientName
	 */
	private void selectUserByName(String firstName) {
		wait.resetImplicitTimeout(1);
		if (element("td_userList").getText().contains(firstName)) {
			try {
				isElementDisplayed("txt_userName", firstName);
				wait.waitForElementToBeClickable(element("txt_userName", firstName));
				executeJavascript(
						"document.getElementsByClassName('datagrid')[0].getElementsByTagName('td')[0].click();");
			} catch (StaleElementReferenceException e) {
				isElementDisplayed("txt_userName", firstName);
				wait.waitForElementToBeClickable(element("txt_userName", firstName));
				executeJavascript(
						"document.getElementsByClassName('datagrid')[0].getElementsByTagName('td')[0].click();");
			}
			logMessage("User clicks on User Name");
			wait.resetImplicitTimeout(wait.timeout);
		}
	}

	/**
	 * This method marks user as signatory
	 * 
	 * @param firstName
	 * @param lasteName
	 */
	public void markUserAsSignatory(String firstName, String lasteName) {
		selectMarkAsSignatory();
		enterExpiryDate();
		clickOnUpdate();
		verifySignatorySuccessModal(firstName, lasteName);
		clickOnCloseOkay();
		verifyUserIsMarkedAsSignatory(firstName);
	}

	/**
	 * This method selects 'Yes' from 'Mark as signatory' dropdown
	 */
	public void selectMarkAsSignatory() {
		selectProvidedTextFromDropDown(element("select_markSignatory"), "Yes");
		logMessage("User selects 'Yes' under Mark as signatory");
	}

	/**
	 * This method enters expiry date
	 */
	public void enterExpiryDate() {
		wait.waitForElementToBeClickable(element("inp_expDate"));
		enterText(element("inp_expDate"), getNextDate());
		logMessage("User enters '" + getNextDate() + "' date under expiry date field");
	}

	/**
	 * This method clicks on Update
	 */
	public void clickOnUpdate() {
		element("btn_update").click();
		logMessage("User clicks on 'Update' button");
	}

	/**
	 * This method verifies signatory success modal
	 * 
	 * @param firstName
	 * @param lastName
	 */
	public void verifySignatorySuccessModal(String firstName, String lastName) {
		Assert.assertEquals(element("p_signatorySuccess").getText(),
				"You have successfully added " + firstName + " " + lastName + " as a signatory.",
				"Assertion Failed: Success modal does not appears");
		logMessage("Assertion Passed: Success modal appears");
	}

	/**
	 * This method verifies if user is marked as signatory(validates the green
	 * checkbox)
	 * 
	 * @param firstName
	 */
	public void verifyUserIsMarkedAsSignatory(String firstName) {
		scrollDown(element("td_signatoryStatus", firstName));
		isElementDisplayed("td_signatoryStatus", firstName);
		Assert.assertEquals(element("td_signatoryStatus").getAttribute("data-original-title"), "User is a signatory",
				"Assertion Failed: User is not marked as signatory");
		logMessage("Assertion Passed: User is successfully marked as signatory");
	}
}
