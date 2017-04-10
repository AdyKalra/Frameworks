package com.zapprx.testing.end2endtests.pageActions.common;

import static com.zapprx.testing.end2endtests.automation.utils.CustomFunctions.getCurrentDateWithSep;
import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import java.io.File;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;
import com.zapprx.testing.end2endtests.automation.utils.CustomFunctions;
import com.zapprx.testing.end2endtests.automation.utils.ReadFromPDF;

public class PrescriptionDetailsPageActions extends GetPage {
	WebDriver driver;
	static int count = 0;

	public PrescriptionDetailsPageActions(WebDriver driver) {
		super(driver, "common/PrescriptionDetailsPage");
		this.driver = driver;
	}

	/**
	 * This method verifies that Prior Authorization link is not displayed
	 */
	public void verifyPriorAuthorizationLinkIsNotDisplayed() {
		isElementNotDisplayed("div_priorAuth");
	}

	/**
	 * This method click on Prior Authorization
	 */
	public void clickOnPriorAuthorization() {
		wait.waitForLoaderToDisappear();
		scrollDown(element("div_priorAuth"));
		isElementDisplayed("div_priorAuth");
		wait.waitForElementToBeClickable(element("div_priorAuth"));
		element("div_priorAuth").click();
		logMessage("User clicks on Prior Authorization button");
	}

	/**
	 * This method click on Expand All to expand prescription details
	 */
	public void expandPresDetails() {
		isElementDisplayed("link_expandAll");
		executeJavascript("document.getElementById('rx-detail-btn-expand').click();");
		logMessage("User clicks on Expand All");
	}

	/**
	 * This method verifies dosage values under 'Dosage' section is selected
	 */
	public void verifyDosageOptionIsChecked(String dosageValue) {
		scrollDown(element("inp_dosage", dosageValue));
		isElementDisplayed("inp_dosage", dosageValue);
		Assert.assertTrue(element("inp_dosage", dosageValue).isSelected(),
				"Assertion Failed: Dosage '" + dosageValue + "' checkbox is not selected");
		logMessage("Assertion Failed: Dosage '" + dosageValue + "' checkbox is selected");
	}

	/**
	 * This method verifies drug value and patient email id
	 */
	public void verifyDrugAndEmail(String medicationName, String emailId) {
		isElementDisplayed("link_drug");
		Assert.assertEquals(element("link_drug").getText(), medicationName,
				"Assertion Failed: Drug " + medicationName + "is not displayed");
		logMessage("Assertion Passed: Drug " + medicationName + "is displayed");
		Assert.assertEquals(element("p_emailId").getText(), emailId,
				"Assertion Failed: Email Id is " + emailId + " is not displayed");
		logMessage("Assertion Passed: Email Id is " + emailId + " is displayed");
	}

	/**
	 * This method clicks on send
	 */
	public void clickOnPrint(String formName, int count) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("form_radiobtn", formName);
		element("form_radiobtn", formName).click();
		element("form_print_btn").click();
	}

	/**
	 * This method verifies Send button with text Send is displayed
	 */
	public void verifySendButtonText() {
		Assert.assertEquals(element("link_print").getText(), "Send",
				"Assertion Failed: Send button with text 'Send' is not displayed");
		logMessage("Assertion Passed: Send button with text 'Send' is displayed");
	}

	/**
	 * This method enter signature to authorize patient
	 */
	private void enterSignature() {
		wait.waitForElementToBeClickable(element("link_drwSig"));
		element("link_drwSig").click();
		logMessage("User clicks on Draw Signature while prescribing");
		wait.waitForStableDom(250);
		wait.waitForElementToBeClickable(element("canvas_sig"));
		drawSignatureOnCanvas(element("canvas_sig"));
	}

	/**
	 * This method enters signature on patient consent for male patient
	 * 
	 * @param signature
	 */
	public void enterSignOfPatientConsentForMale() {
		element("li_drwMale").click();
		logMessage("User clicks on draw signature");
		drawSignatureOnCanvas(element("canvas_signMale"));
	}

	/**
	 * This method enters signature on patient consent for female patient
	 * 
	 * @param signature
	 */
	public void enterSignOfPatientConsentForFemale() {
		element("li_drwFemale").click();
		logMessage("User clicks on draw signature");
		drawSignatureOnCanvas(element("canvas_signFemale"));
	}

	/**
	 * This method clicks on Authorize button
	 */
	public void clickOnAuthorize() {
		isElementDisplayed("btn_authorize");
		wait.waitForStableDom(250);
		executeJavascript("document.getElementById('phys-btn-authorize').click();");
		logMessage("User clicks on Authorize button on Prescription Details Page");
	}

	/**
	 * This method verifies authorization success message is displayed
	 * 
	 * @param authSuccessMsg
	 */
	public void verifyAuthSuccessMsg(String authSuccessMsg) {
		isElementDisplayed("p_authSuccessMsg");
		Assert.assertTrue(element("p_authSuccessMsg").getText().contains(authSuccessMsg),
				"Assertion Failed: Authorization Success Message " + authSuccessMsg + " is not displayed");
		logMessage("Assertion Passed: Authorization Success Message " + authSuccessMsg + " is displayed");
	}

	/**
	 * This method verify the Rx status under Quick Info
	 * 
	 * @param rxStatus
	 */
	public void verifyRxStatus(String rxStatus) {
		wait.resetImplicitTimeout(5);
		try {
			isElementDisplayed("span_status");
			Assert.assertEquals(element("span_status").getText(), rxStatus,
					"Assertion Failed: The status " + rxStatus + " is not displayed under Quick Info");
			logMessage("Assertion Passed: The status " + rxStatus + " is displayed under Quick Info");
		} catch (StaleElementReferenceException e) {
			isElementDisplayed("span_status");
			Assert.assertEquals(element("span_status").getText(), rxStatus,
					"Assertion Failed: The status " + rxStatus + " is not displayed under Quick Info");
			logMessage("Assertion Passed: The status " + rxStatus + " is displayed under Quick Info");
		}
		wait.resetImplicitTimeout(wait.timeout);
	}

	/**
	 * This method verify the complete button is present and click on it
	 */
	public void verifyAndClickOnComplete() {
		isElementDisplayed("btn_complete");
		wait.waitForElementToBeClickable(element("btn_complete"));
		clickOnComplete();
	}

	/**
	 * This method verifies Complete button is not present on Prescription
	 * Details Page
	 */
	public void verifyCompleteButtonIsNotDisplayed() {
		isElementNotDisplayed("btn_complete");
	}

	/**
	 * This method clicks on Complete button
	 */
	private void clickOnComplete() {
		element("btn_complete").click();
		logMessage("User clicks on Complete button");
	}

	/**
	 * This method verifies that request prior auth option is available
	 */
	public void verifyUserIsAbleToRequestPriorAuth() {
		Assert.assertTrue(element("div_priorAuth").isDisplayed(),
				"Assertion Failed: Request Prior Authorization is not present on Rx Details Page");
		logMessage("Assertion Passed: Request Prior Authorization is present on Rx Prescription Page");
	}

	/**
	 * This method verifies SendToEMR is not displayed for physician under
	 * non-athena practices
	 */
	public void verifySendToEMRIsNotDisplayed() {
		isElementNotDisplayed("link_sendToEMR");
	}

	/**
	 * This method request correction of Date of Birth
	 * 
	 * @param correctionNote
	 */
	public void requestCorrectionforDOB(String correctionNote) {
		clickOnRequestCorrections();
		selectDOB();
		enterCorrectionNotes(correctionNote);
		clickOnSaveCorrections();
	}

	/**
	 * This method clicks on Request Corrections
	 */
	private void clickOnRequestCorrections() {
		wait.waitForLoaderToDisappear();
		wait.waitForElementToBeClickable(element("div_rqstCorrctn"));
		executeJavascript("document.getElementById('corr-btn').click();");
		logMessage("User clicks Request Corrections at Pharmacy");
	}

	/**
	 * This method selects DOB checkbox
	 */
	private void selectDOB() {
		wait.waitForStableDom(250);
		executeJavascript("document.getElementsByClassName('form-label')[4].getElementsByTagName('input')[0].click();");
		logMessage("User selects Date of Birth checkbox");
	}

	/**
	 * This method enters Correction Notes
	 * 
	 * @param correctionNote
	 */
	private void enterCorrectionNotes(String correctionNote) {
		enterText(element("txt_crrctNote"), correctionNote);
		logMessage("User enters Correction Note");
	}

	/**
	 * This method clicks on Save Corrections
	 */
	private void clickOnSaveCorrections() {
		wait.waitForElementToBeVisible(element("btn_saveCorrctn", "SAVE CORRECTIONS"));
		executeJavascript("document.getElementsByClassName('btn-primary')[0].click();");
		logMessage("User clicks on Save Corrections");
	}

	/**
	 * This method verifies success message for connection request
	 * 
	 * @param connectionRequestMsg
	 */
	public void verifyCorrectionRequestMsg(String connectionRequestMsg) {
		Assert.assertEquals(element("p_cnnctnRqstMsg").getText(), connectionRequestMsg,
				"Assertion Failed: Success message " + connectionRequestMsg
						+ " for Correction Request is not displayed");
		logMessage("Assertion Passed: Success message " + connectionRequestMsg
				+ " for Correction Request is getiing displayed");
	}

	/**
	 * This method clicks on Continue button
	 */
	public void clickOnContinue() {
		isElementDisplayed("submit_modal");
		element("div_continue").click();
		logMessage("User clicks on Continue button");
		wait.waitForElementToBeInVisible(getLocator("div_continue"));
	}

	/**
	 * This method verifies the message of correction note
	 * 
	 * @param correctionNote
	 */
	public void verifyCorrectionMessage(String correctionNote) {
		Assert.assertEquals(element("div_connectionMsg").getText(), correctionNote,
				"Assertion Failed: The Correction Note " + correctionNote + " is not getting displayed");
		logMessage("Assertion Passed: The Correction Note " + correctionNote + " is getting displayed");
	}

	/**
	 * This method verifies Date of Birth after updation
	 * 
	 * @param dob
	 */
	public void verifyDateOfBirth(String dob) {
		isElementDisplayed("inp_patientDOB");
		Assert.assertEquals(element("inp_patientDOB").getAttribute("value"), dob,
				"Assertion Failed: Date of Birth was not updated correctly");
		logMessage("Assertion Passed: Updated Date of Birth is displayed correctly");
	}

	/**
	 * This method clicks on Continue button at Pharmacy End
	 */
	public void clickOnContinueAtPharmacyEnd() {
		element("div_cnnctnContinue").click();
		logMessage("User clicks on Continue button at Pharmacy End");
	}

	/**
	 * This method edit the DOB and submit correction
	 * 
	 * @param signature
	 * @return
	 */
	public String editDOBAuthorizeAndSubmitCorrection() {
		String dob = getCurrentDateWithSep();
		wait.waitForStableDom(250);
		wait.waitForElementToBeClickable(element("inp_patientDOB"));
		enterText(element("inp_patientDOB"), dob);
		enterSignature();
		clickOnSubmitCorrection();
		clickContinueAfterCorrection();
		return dob;
	}

	/**
	 * This method clicks on Submit Correction
	 */
	private void clickOnSubmitCorrection() {
		element("btn_submitCorrection").click();
		logMessage("User click on Submit Corrections");
	}

	/**
	 * This method clicks on Continue after performing correction
	 */
	private void clickContinueAfterCorrection() {
		element("div_continueCrrctn").click();
		logMessage("User clicks on Continue after submitting correction");
	}

	/**
	 * This method verifies user is unable to authorize prescription
	 */
	public void verifyUserIsAbleToAuthorize() {
		Assert.assertTrue(element("div_authorize").isDisplayed(),
				"Assertion Failed: User is unable to authorize the prescription");
		logMessage("User is able to authorize the prescription");
	}

	/**
	 * This method verifies availability of capture patient consent after saving
	 * the prescription as draft
	 */
	public void verifyPatientConsentBeforeSign() {
		Assert.assertTrue(element("link_patientConsent").isDisplayed(),
				"Assertion Failed: Capture Patient Consent option is not available after saving the prescription as draft");
		logMessage(
				"Assertion Passed: Capture Patient Consent option is available after saving the prescription as draft");
	}

	/**
	 * This method clicks on capture patient consent
	 */
	public void clickOnPatientConsent() {
		wait.resetImplicitTimeout(1);
		try {
			wait.waitForElementToBeVisible(element("link_patientConsent"));
		} catch (StaleElementReferenceException e) {
			wait.waitForElementToBeVisible(element("link_patientConsent"));
		}
		wait.resetImplicitTimeout(wait.timeout);
		executeJavascript("document.getElementById('rx-detail-patient-consent-link').click()");
		logMessage("User clicks on Capture Patient Consent");
	}

	/**
	 * This method save the Patient Consent
	 */
	public void savePatientConsent() {
		element("btn_savePatientConsent").click();
		logMessage("User save the patient consent");
	}

	/**
	 * This method clicks continue for patient consent
	 */
	public void clickContinueForPatientConsent() {
		isElementDisplayed("div_continueConsent");
		element("div_continueConsent").click();
		logMessage("User clicks continue on patient consent confirmation modal");
	}

	/**
	 * This method clicks on Mark As Sent
	 */
	public void clickOnMarkAsSent() {
		wait.waitForElementToBeVisible(element("link_markAsSent"));
		executeJavascript("document.getElementById('rx-detail-btn-transmitted').click();");
		logMessage("User clicks on Mark As Sent");
	}

	/**
	 * This method selects reason for rx to be complete.
	 */
	public void selectReason() {
		isElementDisplayed("sel_reason");
		selectProvidedTextFromDropDown(element("sel_reason"), "The Prescription has been Filled");
	}

	/**
	 * This method selects date
	 */
	public void selectDate() {
		isElementDisplayed("inp_transDate");
		wait.waitForElementToBeClickable(element("inp_transDate"));
		executeJavascript("document.getElementById('markRx').click();");
		wait.waitForElementToBeClickable(element("div_transModal"));
		enterText(element("inp_transDate"), CustomFunctions.getDateWithSeparator());
		wait.waitForElementToBeClickable(element("span_calButton"));
		isElementDisplayed("span_calButton");
		executeJavascript(
				"document.getElementById('mark-rx-modal').getElementsByClassName('input-group-btn')[0].childNodes[2].click();");
		logMessage("User clicks on calendar button");
	}

	/**
	 * This method verifies date picker on mark as sent dialog box
	 */
	public void verifyDateForMarkAsSent() {
		isElementDisplayed("inp_transDate");
		Assert.assertTrue(element("inp_transDate").isDisplayed(),
				"Assertion Failed: Calendar button is not getting displayed for Mark As Sent");
		logMessage("Assertion Passed: Calendar button is getting displayed for Mark As Sent");

	}

	/**
	 * This method clicks on Submit
	 */
	public void clickOnSubmit() {
		wait.waitForStableDom(250);
		wait.waitForElementToBeClickable(element("div_transSubmit"));
		executeJavascript("document.getElementById('mark-btn').click();");
		logMessage("This method clicks on Submit");
	}

	/**
	 * This method clicks continue on confirmation dialog box
	 */
	public void clickContinueOnConfirmationBox() {
		isElementDisplayed("div_transContinue");
		element("div_transContinue").click();
		logMessage("This method clicks on continue button on confirmation dialog box");
	}

	/**
	 * This method clicks on Mark As Complete
	 */
	public void clickOnMarkAsComplete() {
		wait.waitForElementToBeVisible(element("link_markAsComplete"));
		executeJavascript("document.getElementById('rx-detail-btn-complete').click();");
		logMessage("User clicks on Mark As Complete");
	}

	/**
	 * This method verifies titration refill value
	 * 
	 * @param refill
	 */
	public void verifyTitrationRefill(String refill) {
		Assert.assertEquals(element("inp_titRefill", "Titration refills").getAttribute("value"), refill,
				"Assertion Failed: Titration refills value is not updated");
		logMessage("Assertion Passed: Updated value " + refill + " is displayed for Titration refills");
	}

	/**
	 * This method enter prescriber authorization details for female patient on
	 * Prescription Page
	 * 
	 * @param remsValue
	 */
	public void enterPrescriberAuth(String gender, boolean remsValue, String medicationName) {
		isElementDisplayed("li_savedSig");
		if (medicationName.equalsIgnoreCase("Tracleer")) {
			isElementDisplayed("select_liverTest");
			selectProvidedTextFromDropDown(element("select_liverTest"), "Yes");
			logMessage("User selects value from liver function tests.");
			isElementDisplayed("select_inpatientFemale");
			selectProvidedTextFromDropDown(element("select_inpatientFemale"), "Yes");
			logMessage("User selects value from Is patient currently in an inpatient facility?");
		}
		if (gender.equalsIgnoreCase("Female") && remsValue == true) {
			isElementDisplayed("select_reproPotfemale");
			selectProvidedTextFromDropDown(element("select_reproPotfemale"), "Yes");
			isElementDisplayed("select_pregTestFemale");
			selectProvidedTextFromDropDown(element("select_pregTestFemale"), "Yes");
		}
	}

	/**
	 * This method returns prescription id
	 * 
	 * @return
	 */
	public String getPrescriptionId() {
		return driver.getCurrentUrl().split("/")[6];
	}

	/**
	 * This method selects the form and switch the focus back to main window
	 * 
	 * @param count
	 * @return
	 */
	public String userSelectsForm(int count) {
		wait.waitForPageToLoadCompletely();
		wait.waitForElementsToBeVisible(elements("tr_forms"));
		String formName = elements("tr_forms").get(count).getText().trim();
		clickOnPrint(formName, count);
		return formName;
	}

	/**
	 * This method returns the form size
	 * 
	 * @return
	 */
	public int getFormsSize() {
		isElementDisplayed("tr_forms");
		return elements("tr_forms").size();
	}

	/**
	 * This method returns pharmacy forms size
	 * 
	 * @return
	 */
	public int getPharmacyFormsSize() {
		return elements("txt_pharmacyName").size();
	}

	/**
	 * This method validates patient consent option is not available
	 */
	public void verifyNoPatientConsentIsDisplayed() {
		isElementNotDisplayed("link_patientConsent");
	}

	/**
	 * This method validates that Rx is not submitted without entering date
	 */
	public void verifyRxNotSubWithoutDate() {
		Assert.assertTrue(element("div_modal").getAttribute("style").contains("block"),
				"Assertion Failed: User is able to submit without entering date");
		logMessage("Assertion Failed: User is not able to submit without entering date");
	}

	/**
	 * This method validate the value for special Instruction or Prescriber to
	 * specify any alternative or additional dosing and titration instructions
	 * 
	 * @param spclInst
	 */
	public void verifySpecialInstOrPresSpecfyAlt(String spclInst) {
		isElementDisplayed("txt_addInst");
		Assert.assertEquals(element("txt_addInst").getAttribute("value"), spclInst,
				"Assertion Failed: Special Instruction value is not displayed correctly");
		logMessage("Assertion Passed: Special Instruction value is displayed correctly");
	}

	/**
	 * This method validates the refill value
	 * 
	 * @param refillValue
	 */
	public void verifyRefillValue(String refillValue) {
		isElementDisplayed("inp_refills");
		Assert.assertEquals(element("inp_refills").getAttribute("value"), refillValue,
				"Assertion Failed: Refill Value is incorrect");
		logMessage("Assertion Passed: Refill Value is correct");
	}

	/**
	 * This method verify the date is displayed correctly for Sent To Pharmacy
	 * 
	 * @param date
	 */
	public void verifySentToPharmacyDate(String date) {
		Assert.assertEquals(element("span_sendToPharmacy").getText(), date,
				"Assertion Failed: Date is displayed incorrect for Sent To Pharmacy field");
		logMessage("Assertion Passed: Date is displayed correctly for Sent To Pharmacy field");
	}

	/**
	 * This method gets the refill value
	 * 
	 * @return
	 */
	public int getRefillValue() {
		return Integer.valueOf(element("inp_refills").getAttribute("value"));
	}

	/**
	 * This method verify the updated value once the prescription is marked
	 * completed
	 * 
	 * @param refillValue
	 */
	public void verifyUpdatedRefillValue(int refillValue) {
		isElementDisplayed("inp_refills");
		Assert.assertEquals(element("inp_refills").getAttribute("value"), String.valueOf(refillValue - 1),
				"Assertion Failed: Updated Refill Value is incorrect");
		logMessage("Assertion Passed: Updated Refill Value is correct");
	}

	/**
	 * This method verifies other checkbox is selected or not
	 */
	public void verifyOtherIsChecked() {
		isElementDisplayed("inp_other");
		Assert.assertTrue(element("inp_other").isSelected(), "Assertion Failed: Other checkbox is not selected");
		logMessage("Assertion Passed: Other checkbox is selected");
	}

	/**
	 * This method verifies the instruction text
	 * 
	 * @param text
	 */
	public void verifyOtherDetails(String text) {
		Assert.assertEquals(element("txt_inst").getAttribute("value"), text,
				"Assertion Failed: Other details are incorrect");
		logMessage("Assertion Passed: Other details are correct");
	}

	/**
	 * This method clicks on Apply Saved Signature
	 */
	private void clickOnApplySavedSig() {
		isElementDisplayed("li_savedSig");
		executeJavascript("document.getElementsByClassName('use-saved-sig')[0].getElementsByTagName('a')[0].click();");
		logMessage("User clicks on Apply Saved Signature");
	}

	/**
	 * This method enters physician signature
	 * 
	 * @param signature
	 */
	private void enterPhysicianPassword(String signature) {
		isElementDisplayed("div_dosctorSigModal");
		enterText(element("inp_passwrd"), signature);
		logMessage("User enters the physician saved Signature");
	}

	/**
	 * This method clicks on verify button
	 */
	private void clickOnVerifyButton() {
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
	}

	/**
	 * This method verify the Dosage Tyvaso heading on prescription details page
	 * 
	 * @param title
	 */
	public void verifyDosageTyvasoTitle(String title) {
		isElementDisplayed("hdr_titleTyvaso", title);
	}

	/**
	 * This method verify the Dosage Tyvaso CCB yes Option
	 * 
	 * @param option
	 */
	public void verifyDosageTyvasoDetails(String option) {
		isElementDisplayed("inp_ccbOption", option);
	}

	/**
	 * This method verify the Dosage Tyvaso CCB text box value
	 * 
	 * @param expectedValue
	 */

	public void compareCCBValue(String expectedValue) {
		isElementDisplayed("txt_ccbTrial");
		Assert.assertEquals(element("txt_ccbTrial").getAttribute("value"), expectedValue,
				"Assertion Failed: CCB text is incorrect");
		logMessage("Assertion Passed: CCB value is displayed");
	}

	/**
	 * This method verify the checked responses on CCB
	 * 
	 * @param hyperSensRes
	 */
	public void verifyCheckedOnHyperSens(String hyperSensRes) {
		isElementDisplayed("li_ccbResponses", hyperSensRes);
	}

	/**
	 * This method verify the checked responses on CCB
	 * 
	 * @param patientSympRes
	 */
	public void verifyCheckedOnPatientSymp(String patientSympRes) {
		isElementDisplayed("li_ccbResponses", patientSympRes);
	}

	/**
	 * This method verify the checked responses on CCB
	 * 
	 * @param otherRes
	 */
	public void verifyCheckedOnOthers(String otherRes) {
		isElementDisplayed("li_ccbResponses", otherRes);
	}

	/**
	 * This method verify the text box values of responses on CCB
	 * 
	 * @param hypersensValue
	 */
	public void compareHyperSensValue(String hypersensValue) {
		isElementDisplayed("txt_responsesValue", hypersensValue);
		Assert.assertEquals(element("txt_responsesValue", hypersensValue).getAttribute("value"), hypersensValue,
				"Assertion Failed: CCB text is incorrect");
		logMessage("Assertion Passed: Hypersensitive value is displayed");
	}

	/**
	 * This method verify the text box values of responses on CCB
	 * 
	 * @param patientSymp
	 */
	public void comparePatientSympValue(String patientSymp) {
		isElementDisplayed("txt_responsesValue", patientSymp);
		Assert.assertEquals(element("txt_responsesValue", patientSymp).getAttribute("value"), patientSymp,
				"Assertion Failed: Disease continued to progress text is incorrect");
		logMessage("Assertion Passed: Disease continued to progress value is displayed");
	}

	/**
	 * This method verify the text box values of responses on CCB
	 * 
	 * @param otherTxt
	 */
	public void compareOthersRespValue(String otherTxt) {
		isElementDisplayed("txt_responsesValue", otherTxt);
		Assert.assertEquals(element("txt_responsesValue", otherTxt).getAttribute("value"), otherTxt,
				"Assertion Failed: Other text is incorrect");
		logMessage("Assertion Passed: Others value is displayed");
	}

	/**
	 * This method verify Injector 44 mcg option is checked under Directions on
	 * dosage page in Rebif medication
	 * 
	 * @param dirInjectorOption
	 */

	public void verifyInjectorOptionIsChecked(String dirInjectorOption) {
		isElementDisplayed("inp_dirOption", dirInjectorOption);
	}

	/**
	 * This method verifies that Days supply field is updated correctly
	 */
	public void verifyDaysSupplyIsUpdated(String daysSupply) {
		isElementDisplayed("inp_days");
		Assert.assertEquals(element("inp_days").getAttribute("value"), daysSupply,
				"Assertion Failed: Days Supply field is not updated");
		logMessage("Assertion Passed: Days Supply field is updated");
	}

	/**
	 * This method verifies PA Status under quick info
	 * 
	 * @param PAStatus
	 */
	public void verifyPAStatus(String PAStatus) {
		refreshPage();
		isElementDisplayed("span_paStatus");
		Assert.assertEquals(element("span_paStatus").getText(), PAStatus,
				"Assertion Failed: PA Status: " + PAStatus + " is not displayed on prescription details page");
		logMessage("Assertion Passed: PA Status: " + PAStatus + " is displayed on prescription details page");
	}

	/**
	 * This method verify 'Mark PA Submitted' is not displayed
	 */
	public void verifyMarkPASubmittedIsNotDisplayed() {
		isElementNotDisplayed("link_markPASub&Completed", "Mark PA Subbmitted");
	}

	/**
	 * This method clicks on 'Mark PA Submitted' link
	 */
	public void clickOnMarkPASubmitted() {
		wait.waitForElementToBeClickable(element("link_markPASub"));
		executeJavascript("document.getElementById('rx-detail-btn-pa-submitted').click();");
		logMessage("User clicks on Mark PA Submitted link");
	}

	/**
	 * This method selects submitted date
	 */
	public void selectSubmittedDate() {
		isElementDisplayed("inp_subDate");
		wait.waitForElementToBeClickable(element("div_sudModal"));
		wait.waitForElementToBeClickable(element("inp_subDate"));
		enterText(element("inp_subDate"), CustomFunctions.getDateWithSeparator());
		executeJavascript(
				"document.getElementById('approve-pa-modal').getElementsByClassName('input-group-btn')[0].childNodes[2].click();");
		logMessage("User clicks on calendar button");
	}

	/**
	 * This method clicks on Submit
	 */
	public void clickOnPASubmit() {
		wait.waitForStableDom(250);
		wait.waitForElementToBeClickable(element("div_subSubmit"));
		element("div_subSubmit").click();
		logMessage("This method clicks on Submit");
	}

	/**
	 * This method verifies medication is correctly displayed under 'Prior and
	 * Current Medications' section on Prescription review page
	 * 
	 * @param medicineName
	 */
	public void verifyPriorAndCurrentMed(String medicineName) {
		Assert.assertEquals(element("span_priorMed").getText(), medicineName, "Assertion Failed: Medication : "
				+ medicineName + " is not displayed under Prior and Current Medications");
		logMessage(
				"Assertion Passed: Medication : " + medicineName + " is displayed under Prior and Current Medications");
	}

	/**
	 * This method verifies value in 'Reason Ended' field
	 * 
	 * @param medication
	 * @param reason
	 */
	private void verifyReasonEndedField(String medication, String reason) {
		Assert.assertEquals(element("span_reasonEnded", medication).getText(), reason,
				"Assertion Failed: Reason Ended : " + reason + " is not displayed under Prior and Current Medications");
		logMessage("Assertion Passed: Reason Ended: " + reason + " is displayed under Prior and Current Medications");
	}

	/**
	 * This method verifies value in 'Status' field
	 * 
	 * @param medication
	 * @param status
	 */
	private void verifyStatusField(String medication, String status) {
		Assert.assertEquals(element("span_priorStatus", medication).getText(), status,
				"Assertion Failed: Status: " + status + " is not displayed under Prior and Current Medications");
		logMessage("Assertion Passed: Status: " + status + " is displayed under Prior and Current Medications");
	}

	/**
	 * This method verifies that values in Reason Ended and Status fields are
	 * displayed correctly under prior and current medication
	 */
	public void verifyPriorAndCurrentMedFields(String medication, String reason, String status) {
		verifyReasonEndedField(medication, reason);
		verifyStatusField(medication, status);
	}

	/**
	 * This method clicks on Edit
	 */
	public void clickOnEdit() {
		element("btn_edit").click();
		logMessage("User clicks on Edit button");
	}

	/**
	 * This method validates that 'Prior Authorization Submitted to PARx' under
	 * checklist
	 */
	public void verifyPriorAuthorizationChecklist() {
		Assert.assertTrue(
				element("i_priorAuthCheck", "Prior Authorization Submitted to PARx").getAttribute("class")
						.contains("fa-check"),
				"Assertion Failed: Checklist Prior Authorization Submitted to PARx is not checked");
		logMessage("Assertion Passed: Checklist Prior Authorization Submitted to PARx is checked");
	}

	/**
	 * This method clicks on letter of medical necessity
	 */
	public void clickOnLetterOfMedicalNecessity() {
		element("btn_letterOfMedical").click();
		logMessage("User clicks on Letter of Medical Necessity");
	}

	/**
	 * This method clicks on 'Print' to print Letter of Medical Necessity
	 */
	public void clickOnPrintToPrintLetterOfMedical() {
		element("div_print").click();
		logMessage("User clicks on 'Print' to print Letter of Medical Necessity");
	}

	/**
	 * This method validates 'Letter of Medical Necessity' form title
	 */
	public void verifyMedicalNecessityFormTitle() {
		File file = ReadFromPDF.getDownloadedFile();
		Assert.assertTrue(ReadFromPDF.getPdfFileTitle(file).contains("Letter of Medical Necessity"),
				"Assertion Failed: Letter of Medical Necessity is not rendered");
		logMessage("Assertion Passed: Letter of Medical Necessity is rendered");
	}

	public void clickOnSend() {
		wait.waitForElementToBeVisible(element("link_printSend"));
		executeJavascript("document.getElementById('rx-detail-btn-print').click();");
		logMessage("User clicks on Send");
	}

	/**
	 * This method validates Rx form title and its content type
	 */
	public void verifyRxFormFormTitleAndContentType() {
		File file = ReadFromPDF.getDownloadedFile();
		Assert.assertTrue(ReadFromPDF.getPdfFileTitle(file).contains("Fax Cover Page"),
				"Assertion Failed: Prior Authorization form is not rendered");
		logMessage("Assertion Passed: Prior Authorization form is rendered");
		Assert.assertTrue(ReadFromPDF.checkIfFileIsPDFType(file),
				"Assertion Failed: Prior Authorization form can't be downloaded as PDF");
		logMessage("Assertion Passed: Prior Authorization form can be downloaded as PDF");
	}

	/**
	 * This method verifies eFax button is disabled
	 */
	private void verifyEFaxButtonIsDisabled() {
		Assert.assertTrue(element("btn_efax").getAttribute("disabled").equalsIgnoreCase("true"),
				"Assertion Failed: eFax button is not disabled");
		logMessage("Assertion Passed: eFax button is disabled");
	}

	/**
	 * This method verifies eFax button is not disabled
	 */
	private void verifyEFaxButtonIsNotDisabled() {
		Assert.assertTrue(element("btn_efax").getAttribute("disabled") == null,
				"Assertion Failed: eFax button is disabled");
		logMessage("Assertion Passed: eFax button is not disabled");
	}

	/**
	 * This method verifies Print button is disabled
	 */
	private void verifyPrintButtonIsDisabled() {
		Assert.assertTrue(element("btn_printRxForm").getAttribute("disabled").equalsIgnoreCase("true"),
				"Assertion Failed: Print button is not disabled");
		logMessage("Assertion Passed: Print button is disabled");
	}

	/**
	 * This method verifies Print button is not disabled
	 */
	private void verifyPrintButtonIsNotDisabled() {
		Assert.assertTrue(element("btn_printRxForm").getAttribute("disabled") == null,
				"Assertion Failed: Print button is disabled");
		logMessage("Assertion Passed: Print button is not disabled");
	}

	/**
	 * This method clicks on 'Print' to print Rxform
	 */
	public void clickOnPrintRxFormButton() {
		element("btn_printRxForm").click();
		logMessage("User clicks on 'Print' to print RxForm");
	}

	/**
	 * This method clicks Rxform radio button to select RxForm
	 */
	public void clickOnRxFormRadioButton() {
		elements("radioBtn_selectRxFrom").get(0).click();
		logMessage("User clicks on radio button to select RxForm to be printed");
	}

	/**
	 * This method verifies eFax and Print button is disabled
	 */
	public void verifyEFaxAndPrintButtonIsDisabled() {
		verifyEFaxButtonIsDisabled();
		verifyPrintButtonIsDisabled();
	}

	/**
	 * This method verifies eFax and Print button is not disabled
	 */
	public void verifyEFaxAndPrintButtonIsNotDisabled() {
		verifyEFaxButtonIsNotDisabled();
		verifyPrintButtonIsNotDisabled();
	}

	/**
	 * This method clicks on 'eFax' to get eFax confirmation modal
	 */
	public void clickOnEFaxButton() {
		element("btn_efax").click();
		logMessage("User clicks on eFax button");
	}

	/**
	 * This method clicks on 'Send eFax' to send eFax
	 */
	public void clickOnSendEFax() {
		element("btn_sendEFax").click();
		logMessage("User clicks on eFax button");
	}

	/**
	 * This method clicks on 'Okay' on eFax confirmation modal
	 */
	public void clickOnOkay() {
		element("btn_okay").click();
		logMessage("User clicks on Okay button");
	}

	/**
	 * This method verifies Send eFax success text on modal
	 * 
	 * @param sendEFaxSuccessText
	 * @param faxStatus
	 */
	public void verifySendSuccessEFaxConfirmationModal(String sendEFaxSuccessText, String faxStatus) {
		isElementDisplayed("btn_okay");
		Assert.assertEquals(element("p_successText").getText().trim(), sendEFaxSuccessText + "\"" + faxStatus + "\".",
				"Assertion Failed: EFax confirm success message '" + sendEFaxSuccessText + "' is not displayed");
		logMessage("Assertion Passed: EFax confirm success message '" + sendEFaxSuccessText + "' is displayed");
		clickOnOkay();
	}

	/**
	 * This method verifies eFax button is not displayed
	 */
	public void verifyEFaxButtonIsNotDisplayed() {
		isElementNotDisplayed("btn_efax");
	}

	/**
	 * This method verifies fax status under quick information
	 * 
	 * @param faxStatus
	 */
	public void verifyFaxStatus(String faxStatus) {
		isElementDisplayed("span_faxStatus");
		Assert.assertEquals(element("span_faxStatus").getText(), faxStatus,
				"Assertion Failed: The status " + faxStatus + " is not displayed under Quick Info");
		logMessage("Assertion Passed: The status " + faxStatus + " is displayed under Quick Info");
	}

	/**
	 * This method header of verifies eFax Send Confirmation modal
	 * 
	 * @param header
	 */
	public void verifyEFaxModalHeader(String header) {
		Assert.assertEquals(element("h4_eFaxConfirmHeader").getText(), header,
				"Assertion Failed: The eFax confirmation header '" + header + "' is not displayed");
		logMessage("Assertion Passed: The eFax confirmation header '" + header + "' is displayed");
	}

	/**
	 * This method verifies sender and destination header of eFax Send
	 * Confirmation modal
	 * 
	 * @param eFaxSendDestHeader
	 */
	public void verifyEFaxConfirmationModalSendDestHeader(String... eFaxSendDestHeader) {
		count = 0;
		for (WebElement option : elements("div_eFaxSendDestHeader")) {
			Assert.assertEquals(option.getText(), eFaxSendDestHeader[count],
					"Assertion Failed: The eFax confirmation header '" + eFaxSendDestHeader[count]
							+ "' is not displayed");
			logMessage(
					"Assertion Passed: The eFax confirmation header '" + eFaxSendDestHeader[count] + "' is displayed");
			count++;
		}
	}

	/**
	 * This method verifies fax subject of eFax Send Confirmation modal
	 * 
	 * @param eFaxSubject
	 */
	public void verifyEFaxSubjectField(String eFaxSubject) {
		isElementDisplayed("div_eFaxSubject");
		Assert.assertEquals(element("div_eFaxSubject").getText(), eFaxSubject,
				"Assertion Failed: The eFax subject field '" + eFaxSubject + "' is not displayed");
		logMessage("Assertion Passed: The eFax subject field '" + eFaxSubject + "' is displayed");
	}

	/**
	 * This method verifies eFax Send Confirmation modal
	 * 
	 * @param header
	 * @param eFaxSubject
	 * @param eFaxSendDestHeader
	 */
	public void verifyEFaxConfirmationModal(String header, String eFaxSubject, String... eFaxSendDestHeader) {
		verifyEFaxModalHeader(header);
		verifyEFaxSubjectField(eFaxSubject);
		verifyEFaxConfirmationModalSendDestHeader(eFaxSendDestHeader);
	}

	/**
	 * This method performs efax process upto confirmation modal
	 */
	public void eFaxRxFormToGetConfirmationModal() {
		clickOnSend();
		clickOnRxFormRadioButton();
		clickOnEFaxButton();
	}

	/**
	 * This method fulfill precription
	 * 
	 * @param fulfillsuccessMsg
	 */
	public void fulfillPrescription(String fulfillsuccessMsg) {
		clickOnFulfillButton();
		verifyFulfillSuccessModal(fulfillsuccessMsg);
		clickOnFulfillContinue();
	}

	/**
	 * This method clicks on fulfill button
	 */
	public void clickOnFulfillButton() {
		element("btn_fulfill").click();
		logMessage("User clicks on fulfill prescription button");
	}

	/**
	 * This method validates fulfill success message
	 * 
	 * @param fulfillsuccessMsg
	 */
	public void verifyFulfillSuccessModal(String fulfillsuccessMsg) {
		Assert.assertEquals(element("p_fulfillsuccessMsg").getText(), fulfillsuccessMsg,
				"Assertion Failed: Fulfill success message '" + fulfillsuccessMsg + "' is not displayed");
		logMessage("Assertion Passed: Fulfill success message '" + fulfillsuccessMsg + "' is displayed");
	}

	/**
	 * This method clicks on 'Continue' on fulfill confirmation modal
	 */
	public void clickOnFulfillContinue() {
		element("div_fulfillContinue").click();
		logMessage("User clicks on fulfill continue button");
	}

	/**
	 * This method verifies fulfilled date under quick info
	 */
	public void verifyFulfilledDate() {
		Assert.assertEquals(element("span_fulfilledDate").getText(), getCurrentDateWithSep(),
				"Assertion Failed: The fulfilled date " + getCurrentDateWithSep()
						+ " is not displayed under Quick Info");
		logMessage(
				"Assertion Passed: The fulfilled date " + getCurrentDateWithSep() + " is displayed under Quick Info");
	}

	/**
	 * This method enters sign and authorize prescription
	 */
	public void enterSignAndAuthorizePres() {
		enterPhysicianSavedSig(getYamlValue("physician.password1"));
		clickOnAuthorize();
		clickOnContinue();
	}

	/**
	 * This method verify checkboxes are checked in Calcium channel blocker
	 * details on dosage page
	 * 
	 * @param hyperSensTxt
	 * @param patietSympTxt
	 * @param otherTxt
	 */
	public void verifyCheckedResponses(String hypersens, String patientSympRes, String otherRes) {
		verifyCheckedOnHyperSens(hypersens);
		verifyCheckedOnPatientSymp(patientSympRes);
		verifyCheckedOnOthers(otherRes);
	}

	/**
	 * This method compares textboxes values corresponding to expected values in
	 * Calcium channel blocker on dosage page
	 * 
	 * @param hyperSensTxt
	 * @param patietSympTxt
	 * @param otherTxt
	 */
	public void compareResponsesValue(String hypersensValue, String patientSymp, String otherTxt) {
		compareHyperSensValue(hypersensValue);
		comparePatientSympValue(patientSymp);
		compareOthersRespValue(otherTxt);
	}

	/**
	 * This method capture patient consent from prescription details page
	 * 
	 * @param gender
	 * @param medicationName
	 */

	public void capturePatientConsent(String gender, String medicationName) {
		enterSignOfPatientConsentForMale();
		if (gender.equalsIgnoreCase("female") || medicationName.equalsIgnoreCase("Tysabri")) {
			enterSignOfPatientConsentForFemale();
		}
		savePatientConsent();
		clickContinueForPatientConsent();
	}

	/**
	 * This method verifies dosage quantity value of Esbriet medication
	 * 
	 * @param quantityValue
	 */
	public void verifyEsbrietDosageQuantityValue(String quantityValue) {
		Assert.assertEquals(element("inp_esbrietDosageQuantity").getAttribute("value"), quantityValue,
				"Assertion Failed: Dosage quantity value '" + quantityValue + "' is not displayed");
		logMessage("Assertion Passed: Dosage quantity value '" + quantityValue + "' is displayed");
	}

	/**
	 * This method verifies that patient consent link is displayed
	 */
	public void verifyPatientConsentLinkIsDisplayed() {
		isElementDisplayed("link_patientConsent");
	}

	/**
	 * This method verifies that patient consent link is not displayed
	 */
	public void verifyPatientConsentLinkIsNotDisplayed() {
		isElementNotDisplayed("link_patientConsent");
	}

	/**
	 * This method click on 'Cancel' to cancel prescription
	 */
	public void clickOnCancel() {
		element("btn_cancel").click();
		logMessage("This method clicks on 'Cancel' button");
	}

	/**
	 * This method cancel's a prescription
	 * 
	 * @param cancelPresConfirmText
	 * @param cancelPresSuccessText
	 */
	public void cancelPrescription(String cancelPresConfirmText, String cancelPresSuccessText) {
		clickOnCancel();
		verifyCancelPresConfirmText(cancelPresConfirmText);
		verifyCancelPresSuccessText(cancelPresSuccessText);
	}

	/**
	 * This method verifies Cancel Prescription confirmation text on modal and
	 * clicks on 'Yes'
	 * 
	 * @param cancelPresText
	 * @param faxStatus
	 */
	public void verifyCancelPresConfirmText(String cancelPresConfirmText) {
		Assert.assertEquals(element("p_cancelPresConfirmMsg").getText().trim(), cancelPresConfirmText,
				"Assertion Failed: EFax confirm success message '" + cancelPresConfirmText + "' is not displayed");
		logMessage("Assertion Passed: EFax confirm success message '" + cancelPresConfirmText + "' is displayed");
		clickOnConfirmYes();
	}

	/**
	 * This method clicks on 'Yes' on cancel prescription confirm modal
	 */
	public void clickOnConfirmYes() {
		element("btn_cancelPresConfirmYes").click();
		logMessage("User clicks on 'Yes' on cancel prescription confirm modal");
	}

	/**
	 * This method verifies Cancel Prescription success text on modal and clicks
	 * on 'Okay'
	 * 
	 * @param cancelPresText
	 */
	public void verifyCancelPresSuccessText(String cancelPresSuccessText) {
		isElementDisplayed("btn_okay");
		Assert.assertEquals(element("p_successText").getText().trim(), cancelPresSuccessText,
				"Assertion Failed: EFax confirm success message '" + cancelPresSuccessText + "' is not displayed");
		logMessage("Assertion Passed: EFax confirm success message '" + cancelPresSuccessText + "' is displayed");
		clickOnOkay();
	}
}