package com.zapprx.testing.end2endtests.pageActions.physician;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class PriorAuthForPatientPageActions extends GetPage {
	WebDriver driver;

	public PriorAuthForPatientPageActions(WebDriver driver) {
		super(driver, "physician/PriorAuthForPatientPage");
		this.driver = driver;
	}

	/**
	 * This method select the form and review the prescription
	 */
	public void userSelectFormAndSave() {
		userSelectForm();
		clickOnSaveButton();
		wait.waitForLoaderToDisappear();
	}

	/**
	 * This method select the form
	 */
	public void userSelectForm() {
		try {
			if (elements("inp_form").size() >= 1) {
				elements("inp_form").get(0).click();
				logMessage("User selects the form");
				clickOnSelectForm();
			}
		} catch (NoSuchElementException e) {
			logMessage("No options are displayed to select form");
		}
	}

	/**
	 * This method clicks on selected form
	 */
	private void clickOnSelectForm() {
		element("btn_selForm").click();
		logMessage("User clicks on Select Form button");
	}

	/**
	 * This method click on Save button
	 */
	public void clickOnSaveButton() {
		wait.waitForLoaderToDisappear();
		scrollDown(element("btn_save", "Save"));
		element("btn_save", "Save").click();
		logMessage("User clicks on Save button");
	}

	/**
	 * This method clicks on Close
	 */
	public void clickOnClose() {
		element("btn_reviewPres", "Close").click();
		logMessage("User clicks on Close Prescription");
	}

	/**
	 * This method clicks on Review Prescription
	 */
	public void clickOnReviewPrescription() {
		element("btn_reviewPres", "Review Prescription").click();
		logMessage("User clicks on Review Prescription");
	}

	/**
	 * This method clicks on Send To Plan button
	 */
	public void clickOnSendToPlan() {
		wait.waitForLoaderToDisappear();
		wait.waitForElementToBeClickable(element("btn_save", "Send to Plan"));
		scrollDown(element("btn_save", "Send to Plan"));
		element("btn_save", "Send to Plan").click();
		logMessage("User clicks on Send to Plan button");
	}

	/**
	 * This method draw signature on canvas
	 */
	public void drawSignToSubmitAndReviewPres() {
		wait.waitForLoaderToDisappear();
		isElementDisplayed("modal_drawSign");
		isElementDisplayed("link_clearSign");
		drawSignatureOnCanvas(element("modal_drawSign"));
		logMessage("User draw signature in signature field");
		clickOnSubmit();
		clickOnReviewPresOnModal();
	}

	/**
	 * This method clicks on Submit button
	 */
	private void clickOnSubmit() {
		element("div_submit").click();
		logMessage("Use click on Submit button after signature submission");
	}

	/**
	 * This method clicks on Review Prescription on Modal
	 */
	private void clickOnReviewPresOnModal() {
		wait.waitForElementToBeClickable(element("div_reviwPresModal",
				"Review Prescription"));
		element("div_reviwPresModal", "Review Prescription").click();
		logMessage("User clicks on Review Prescription on Save Modal");
	}

	/**
	 * This method verifies patients name on Prior Auth page
	 */
	public void verifyPatientNameOnPriorAuth(String firstName, String lastName) {
		Assert.assertEquals(
				element("inp_firstName").getAttribute("value"),
				firstName,
				"Assertion Failed: Patient FirstName is not getting displayed on Prior Authorization Page");
		logMessage("Assertion Passed: Patient FirstName is getting displayed on Prior Authorization Page");
		Assert.assertEquals(
				element("inp_lastName").getAttribute("value"),
				lastName,
				"Assertion Failed: Patient FirstName is not getting displayed on Prior Authorization Page");
		logMessage("Assertion Passed: Patient LastName is getting displayed on Prior Authorization Page");
	}

	/**
	 * This method verifies the error message for signature on clicking submit
	 * 
	 * @param submitErrorMsg
	 */
	public void verifySignMessage(String submitErrorMsg) {
		clickOnSubmit();
		Assert.assertEquals(
				element("p_errorMsg").getText(),
				submitErrorMsg,
				"Assertion Failed: Error Message "
						+ submitErrorMsg
						+ " is not getting displayed and user is able to Send To Plan without submitting signature");
		logMessage("Assertion Passed: Error Message "
				+ submitErrorMsg
				+ " is getting displayed and user is not able to Send To Plan without submitting signature");
	}

	/**
	 * This method verifies that error message is not displayed for non-required
	 * field
	 */
	public void verifyNoErrorMsgForNonReqField() {
		isElementNotDisplayed("div_drugState");
		isElementDisplayed("div_submit");
	}
}
