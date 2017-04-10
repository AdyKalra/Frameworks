package com.zapprx.testing.end2endtests.pageActions.physician;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class AuthorizationPageActions extends GetPage {
	WebDriver driver;

	public AuthorizationPageActions(WebDriver driver) {
		super(driver, "physician/AuthorizationPage");
		this.driver = driver;
	}

	/**
	 * This method enter signature and save
	 */
	public void enterSignAndSave(String gender) {
		clickOnDrawSignature();
		drawSignatureOnCanvas(element("canvas_maleSig"));
		if (gender.equalsIgnoreCase("female") && elements("li_drwSig").size() > 1) {
			isElementDisplayed("link_femaleDrwSig");
			element("link_femaleDrwSig").click();
			logMessage("User clicks on draw signature");
			isElementDisplayed("canvas_femalesig");
			drawSignatureOnCanvas(element("canvas_femalesig"));
		}
		clickOnSaveButton();
		clickOnContinueButton();
	}

	/**
	 * This method clicks on draw signature
	 */
	public void clickOnDrawSig() {
		isElementDisplayed("link_drwSig");
		wait.waitForElementToBeClickable(element("link_drwSig"));
		wait.waitForStableDom(250);
		executeJavascript("document.getElementById('sig-pad-flow').getElementsByTagName('a')[1].click();");
		logMessage("User clicks on Draw Signature while prescribing");
	}

	/**
	 * This method clicks on draw signature
	 *
	 */
	public void clickOnDrawSignature() {
		wait.waitForElementToBeVisible(element("link_maleDrwSig"));
		executeJavascript("document.getElementById('sig-pad-share').getElementsByTagName('a')[1].click();");
		logMessage("User clicks on Draw Signature");
	}

	/**
	 * This method clicks on Submit button after authorization
	 */
	public void clickOnSubmitButton() {
		scrollDown(element("btn_submit"));
		wait.waitForElementToBeVisible(element("btn_submit"));
		executeJavascript("document.getElementById('submit_button').click();");
		logMessage("User clicks on Submit button");
		wait.waitForElementToDisappear(element("btn_submit"));
	}

	/**
	 * This method validates error message for signature
	 * 
	 * @param signErrorMsg
	 */
	public void verifyErrorMsgForSign(String signErrorMsg) {
		isElementDisplayed("p_signErrorMsg");
		wait.waitForElementTextToContain(element("p_signErrorMsg"), signErrorMsg);
		Assert.assertEquals(element("p_signErrorMsg").getText(), signErrorMsg,
				"Assertion Failed: Error Message " + signErrorMsg + " is not getting dispalyed");
		logMessage("Assertion Failed: Error Message " + signErrorMsg + " is getting dispalyed");
	}

	/**
	 * This method clicks on Save button
	 */
	private void clickOnSaveButton() {
		scrollDown(element("btn_save"));
		wait.waitForElementToBeVisible(element("btn_save"));
		executeJavascript("document.getElementById('patient-consent-btn-submit').click();");
		logMessage("User clicks on Save button");
	}

	/**
	 * This method clicks on continue button
	 */
	private void clickOnContinueButton() {
		wait.waitForElementToBeClickable(element("btn_continue"));
		element("btn_continue").click();
		logMessage("User clicks on continue button on the pop-up");
	}

	/**
	 * This method clicks on Review on Confirmation Success
	 */
	public void clickReviewOnConfirmationModal() {
		isElementDisplayed("div_review");
		executeJavascript("document.getElementById('done_modal').click();");
		logMessage("User clicks Review on Confirmation Success Modal");
	}

	/**
	 * This method verifies user is on Authorization Confirmation modal
	 */
	public void verifyAuthorizationConfirmationModal() {
		Assert.assertTrue(element("div_review").isDisplayed(),
				"Assertion Failed: Authorization Confirmation Success Modal is not displayed");
		logMessage("Assertion Passed: Authorization Confirmation Success Modal is displayed");
	}

	/**
	 * This method clicks on Previous button
	 */
	public void clickOnPreviousButton() {
		isElementDisplayed("btn_previous");
		element("btn_previous").click();
	}

	/**
	 * This method enter prescriber authorization details for female patient on
	 * Authorization Page
	 * 
	 * @param remsValue
	 */
	public void enterPrescriberAuth(String gender, boolean remsValue, String medicineName) {
		isElementDisplayed("btn_submit");
		isElementDisplayed("div_msg");
		if (medicineName.equalsIgnoreCase("Tracleer")) {
			verifyUserIsOnAuthorizePage();
			isElementDisplayed("select_liverTest");
			selectProvidedTextFromDropDown(element("select_liverTest"), "Yes");
			logMessage("User selects value from liver function tests.");
			isElementDisplayed("select_inPatFac");
			selectProvidedTextFromDropDown(element("select_inPatFac"), "Yes");
			logMessage("User selects value from Is patient currently in an inpatient facility?");
		}
		if (gender.equalsIgnoreCase("Female") && remsValue == true) {
			isElementDisplayed("select_femalePatient");
			selectProvidedTextFromDropDown(element("select_femalePatient"), "Yes");
			isElementDisplayed("select_pregTest");
			selectProvidedTextFromDropDown(element("select_pregTest"), "Yes");
		}
	}

	/**
	 * This method clicks on Apply Saved Signature
	 */
	public void clickOnApplySavedSig() {
		wait.waitForElementToBeVisible(element("li_savedSig"));
		executeJavascript("document.getElementsByClassName('use-saved-sig')[0].getElementsByTagName('a')[0].click();");
		logMessage("User clicks on Apply Saved Signature");
	}

	/**
	 * This method enters password to enter physician signature
	 * 
	 * @param signature
	 */
	public void enterPhysicianPassword(String signature) {
		enterText(element("inp_passwrd"), signature);
		logMessage("User enters the physician saved Signature");
	}

	/**
	 * This method clicks on verify button
	 */
	public void clickOnVerifyButton() {
		element("btn_verify").click();
		logMessage("User clicks on verify button");
	}

	/**
	 * This method apply the saved signature for authorization
	 * 
	 * @param signature
	 */
	public void enterPhysicianSavedSig(String signature) {
		clickOnApplySavedSig();
		enterPhysicianPassword(signature);
		clickOnVerifyButton();
		clickOnSubmitButton();
	}

	/**
	 * This method clicks on 'Prior Autho'
	 */
	public void clickOnPriorAuth() {
		element("btn_priorAuth").click();
		logMessage("User clicks on Prior Auth button");
	}

	/**
	 * This method verifies if Prior Auth link is diaplayed on modal and clicks
	 * on Prior Auth link
	 */
	public void verifyPriorAuthLinkAndClickPA() {
		isElementDisplayed("btn_priorAuth");
		clickOnPriorAuth();
	}

	/**
	 * This method verify user is on authorization page
	 */
	public void verifyUserIsOnAuthorizePage() {
		isElementDisplayed("div_msg");
	}

	/**
	 * This method draw's signature and submit
	 */
	public void verifyUserIsAbleToDrawSig() {
		clickOnDrawSig();
		drawSignatureOnCanvas(element("canvas_sig"));
		logMessage("Physician draw signature on canvas");
		clickOnSubmitButton();
	}

	/**
	 * This method clicks on capture patient consent
	 */
	public void clickOnCaptPatientConsent() {
		scrollDown(element("btn_capPatCon"));
		executeJavascript("document.getElementById('capture-patient-consent-btn').click();");
		logMessage("User clicks on Capture Patient Consent");
	}

	/**
	 * This method capture patient consent
	 * 
	 * @param gender
	 * @param medicationName
	 */
	public void capturePatientConsent(String gender, String medicationName) {
		enterSignOfPatientConsentForShareAuth();
		if (gender.equalsIgnoreCase("female") || medicationName.equalsIgnoreCase("Tysabri")) {
			enterSignOfPatientConsentForRems();
		}
		savePatientConsent();
		clickContinueForPatientConsent();
	}

	/**
	 * This method enters signature on patient consent for Share Authorization
	 * 
	 * @param signature
	 */
	private void enterSignOfPatientConsentForShareAuth() {
		element("li_drwShare").click();
		logMessage("User clicks on draw signature");
		drawSignatureOnCanvas(element("canvas_signShare"));
	}

	/**
	 * This method enters signature on patient consent for REMS
	 * 
	 * @param signature
	 */
	private void enterSignOfPatientConsentForRems() {
		element("li_drwRems").click();
		logMessage("User clicks on draw signature");
		drawSignatureOnCanvas(element("canvas_signRems"));
	}

	/**
	 * This method save the Patient Consent
	 */
	private void savePatientConsent() {
		element("btn_savePatientConsent").click();
		logMessage("User save the patient consent");
	}

	/**
	 * This method clicks continue for patient consent
	 */
	private void clickContinueForPatientConsent() {
		isElementDisplayed("div_continueConsent");
		element("div_continueConsent").click();
		logMessage("User clicks continue on patient consent confirmation modal");
	}

	/**
	 * This method validates authorization success message
	 * 
	 * @param authSuccessMsg
	 */
	public void verifyAuthSuccessMsg(String authSuccessMsg) {
		Assert.assertEquals(element("p_successMsg").getText().trim(), authSuccessMsg,
				"Assertion Failed: Authorization success message: " + authSuccessMsg + " is not getting dispalyed");
		logMessage("Assertion Passed: Authorization success message: " + authSuccessMsg + " is getting dispalyed");
	}

	public void verifyMasterDoctorSignature(String masterDocName) {
		isElementDisplayed("img_masterSig");
		Assert.assertEquals(element("span_masterSigMsg").getText(),
				"I am authorized to apply the physician's signature on behalf of Dr. " + masterDocName.split(",")[0]
						+ ".",
				"Assertion Failed: Master doctor '" + masterDocName + "' signature is not displayed");
		logMessage("Assertion Passed: Master doctor '" + masterDocName + "' signature is displayed");
	}
}
