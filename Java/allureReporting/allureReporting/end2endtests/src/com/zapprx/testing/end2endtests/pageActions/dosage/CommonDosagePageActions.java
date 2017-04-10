package com.zapprx.testing.end2endtests.pageActions.dosage;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class CommonDosagePageActions extends GetPage {
	WebDriver driver;

	public CommonDosagePageActions(WebDriver driver) {
		super(driver, "physician/DosagePage");
		this.driver = driver;
	}

	/**
	 * This method verify all mandatory fields on Dosage Page
	 */
	public void verifyMandatoryFieldsOnDosage() {
		Assert.assertTrue(element("div_frequency").getAttribute("class").contains("required"),
				"Assertion Failed: 'Frequency' is not a mandatory field");
		logMessage("Assertion Passed: 'Frequency' is a mandatory field");
		Assert.assertTrue(element("inp_daysSupply").getAttribute("class").contains("required"),
				"Assertion Failed: 'Days supply' is not a mandatory field");
		logMessage("Assertion Passed: 'Days supply' is a mandatory field");
		Assert.assertTrue(element("div_refills").getAttribute("class").contains("required"),
				"Assertion Failed: 'Refills' is not a mandatory field");
		logMessage("Assertion Passed: 'Refills' is a mandatory field");
	}

	/**
	 * This method clicks on Step 5:Authorize
	 */
	public void clickOnAuthorize() {
		wait.waitForLoaderToDisappear();
		isElementDisplayed("btn_authorize&SavePres");
		scrollDown(element("btn_authorize&SavePres"));
		wait.waitForStableDom(250);
		wait.waitForElementToBeClickable(element("btn_authorize&SavePres"));
		element("btn_authorize&SavePres").click();
		logMessage("User clicks on Step 4:Authorize");
	}

	/**
	 * This method clicks on Save Draft to save prescription
	 */
	public void clickOnSaveDraftForPres() {
		wait.waitForLoaderToDisappear();
		scrollDown(element("btn_authorize&SavePres"));
		element("btn_authorize&SavePres").click();
		logMessage("User clicks on Save Draft For Prescription");
	}

	/**
	 * This method clicks on Save Draft button
	 */
	public void clickOnSaveDraft() {
		wait.waitForLoaderToDisappear();
		scrollDown(element("btn_saveDraft"));
		executeJavascript("document.getElementById('mid_button').click();");
		logMessage("User clicks on Save Draft button");
	}

	/**
	 * This method clicks on Finish&Review button
	 */
	public void clickOnFinishAndReview() {
		isElementDisplayed("div_finish&Review");
		executeJavascript("document.getElementById('done_modal').click()");
		logMessage("User clicks on Finish&Review button on confirmation popup");
	}

	/**
	 * This method verifies message on modal after saving a prescription as
	 * draft
	 * 
	 * @param savePresMsg
	 */
	public void verifySavedPrescriptionMsg(String savePresMsg) {
		Assert.assertTrue(element("p_savePrescptnMsg").getText().contains(savePresMsg),
				"Assertion Failed: The msessage " + savePresMsg + " after save prescription as draft is not displayed");
		logMessage("Assertion Passed: The msessage " + savePresMsg + " after save prescription as draft is displayed");
	}

	/**
	 * This method selects Medical Record under Supporting Documents
	 * 
	 * @param medicalRecordType
	 */
	public void selectMedicalRecord(String medicalRecordType) {
		selectProvidedTextFromDropDown(element("select_medicalRecord"), medicalRecordType);
		logMessage("User selects " + medicalRecordType + " under supporting documents dropdown");
	}

	/**
	 * This method upload the file and verifies file is uploaded successfully
	 * 
	 * @param fileName
	 */
	public void uploadFileAndVerify(String fileName) {
		String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "com"
				+ File.separator + "zapprx" + File.separator + "testing" + File.separator + "end2endtests"
				+ File.separator + "main" + File.separator + "resources" + File.separator + "testdata" + File.separator
				+ "file" + File.separator + fileName;
		wait.waitForElementToBeVisible(element("btn_selectFile"));
		executeJavascript("document.getElementById('fileUpload_medical').parentNode.removeAttribute('style');");
		executeJavascript("document.getElementById('fileUpload_medical').parentNode.style.display='block';");
		element("inp_fileUpload").sendKeys(filePath);
		executeJavascript("document.getElementById('fileUpload_medical').parentNode.style.display='none';");
		verifyFileIsUploaded(fileName);
	}

	/**
	 * This method verifies file is uploaded successfully
	 * 
	 * @param fileName
	 */
	private void verifyFileIsUploaded(String fileName) {
		Assert.assertEquals(element("link_fileName").getText(), fileName,
				"Assertion Failed: File is not uploaded successfully");
		logMessage("Assertion Passed: File is uploaded successfully");
	}

	/**
	 * This method clicks on Hide button and on confirmation modal to hide the
	 * uploaded file
	 */
	public void deleteFileAndVerifyMsg(String deleteFileMsg) {
		clickOnDelete();
		clickDeleteOnConfirmationModal();
		verifyDeleteSuccessMsg(deleteFileMsg);
		clickOnContinue();
		verifyUploadedFileIsNotDisplayed();
	}

	/**
	 * This method clicks on Delete button
	 */
	private void clickOnDelete() {
		element("btn_delete").click();
		logMessage("User clicks on Delete button to delete uploaded file");
	}

	/**
	 * This method verifies success message is displayed on deleting the file
	 * 
	 * @param deleteFileMsg
	 */
	private void verifyDeleteSuccessMsg(String deleteFileMsg) {
		Assert.assertEquals(element("p_delPresMsg").getText(), deleteFileMsg,
				"Assertion Failed: Message " + deleteFileMsg + " is not getting displayed on deleting the File");
		logMessage("Assertion Passed: Message " + deleteFileMsg + " is getting displayed on deleting the File");
	}

	/**
	 * This method clicks hide on confirmation modal
	 */
	private void clickDeleteOnConfirmationModal() {
		element("div_delete").click();
		logMessage("User clicks on Delete on confirmation modal");
	}

	/**
	 * This method clicks on Continue button
	 */
	private void clickOnContinue() {
		element("div_continue").click();
		logMessage("User clicks on Continue");
		wait.waitForElementToBeInVisible(getLocator("div_continue"));
	}

	/**
	 * This method verifies uploaded file is not displayed once deleted
	 */
	public void verifyUploadedFileIsNotDisplayed() {
		isElementNotDisplayed("link_fileName");
		logMessage("Uploaded file is not displayed after deleting the file");
	}

	/**
	 * This method clicks on cancel icon
	 */
	public void clickOnCancelIcon() {
		scrollDown(element("icon_cancel"));
		element("icon_cancel").click();
		logMessage("User clicks on cancel icon");
	}

	/**
	 * This method verifies save a draft option is available on dosage page
	 */
	public void verifySaveDraftForPresIsDisplayed() {
		wait.waitForLoaderToDisappear();
		Assert.assertEquals(element("btn_authorize&SavePres").getText(), "Save Draft of Prescription",
				"Assertion Failed: Save Draft is not displayed on Dosage page");
		logMessage("Assertion Passed: Save Draft is displayed on Dosage page");
	}

	/**
	 * This method enters value for other special instruction or Prescriber to
	 * specify any alternative or additional dosing and titration instructions
	 * 
	 * @param spclInst
	 */
	public void enterSpecialInstOrPresSpecfyAlt(String spclInst) {
		enterText(element("txt_othrInst"), spclInst);
		logMessage("User enters " + spclInst + " in Other Special Instructions field");
	}

	/**
	 * This method validates the predefined sig box is selected or not
	 */
	public void verifyPredefinedSigIsChecked() {
		isElementDisplayed("inp_sig");
		Assert.assertTrue(element("inp_sig").isSelected(), "Assertion Failed: Predefined Sig Checkbox is not selected");
		logMessage("Assertion Passed: Predefined Sig Checkbox is selected");
	}

	/**
	 * This method validates required field directions is present on dosage page
	 */
	public void verifyMandatoryFieldIsPresent() {
		isElementDisplayed("select_quantity");
		Assert.assertTrue(element("select_quantity").getAttribute("class").contains("required"),
				"Assertion Failed: Required field Quantity is not present on dosage page");
		logMessage("Assertion Passed: Required field Quantity is present on dosage page");
	}

	/**
	 * This method validates user is still on dosage page
	 */
	public void verifyUserIsUnableToAuthorize() {
		isElementDisplayed("btn_saveDraft");
	}

	/**
	 * This method clicks on view additional enrollment questions
	 */
	public void clickOnViewEnrollmentQue() {
		isElementDisplayed("hdr_details");
		executeJavascript("document.getElementById('dosage-collapse').click();");
		logMessage("User clicks on View additional enrollment questions");
	}

	/**
	 * This method selects value under quantity field
	 * 
	 * @param quantity
	 */
	public void selectQuantity(String quantity) {
		selectProvidedTextFromDropDown(element("select_quantity"), quantity);
		logMessage("User selects value " + quantity + " for Quantity field");
		if (quantity.equalsIgnoreCase("Other"))
			enterDaysSupply(getYamlValue("physician.daysSupply"));
	}

	/**
	 * This method selects the dosage value
	 * 
	 * @param dosageValue
	 */
	public void selectDosage(String dosageValue) {
		if (!element("inp_testDosage", dosageValue).isSelected()) {
			element("inp_testDosage", dosageValue).click();
			logMessage("User selects " + dosageValue + " for Dosage field");
		}
	}

	/**
	 * This method enters days count for supply
	 */
	public void enterDaysSupply(String daysSupply) {
		enterText(element("inp_daysSupply"), daysSupply);
		logMessage("User enters days in Days Supply field");
	}

	/**
	 * This method clicks on capture patient consent
	 */
	public void clickOnCaptPatientConsent() {
		element("btn_capPatCon").click();
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
	 * This method returns refill value of given medication
	 * 
	 * @param medicineName
	 * @return
	 */
	public String getRefillValue(String medicineName) {
		if (medicineName.equalsIgnoreCase("Uptravi"))
			return element("inp_rxRefills").getAttribute("value");
		else if (medicineName.equalsIgnoreCase("Ventavis") || medicineName.equalsIgnoreCase("Remodulin IV")
				|| medicineName.equalsIgnoreCase("Remodulin SC"))
			return getProvidedTextFromDropDown(element("select_refills"));
		else if (medicineName.equalsIgnoreCase("Adempas"))
			return element("inp_strengthRefill").getAttribute("value");
		return element("inp_refills").getAttribute("value");
	}

	/**
	 * This method returns static quantity value of given medication
	 * 
	 * @param medicineName
	 * @return
	 */
	public String getStaticQuantityValue(String medicineName) {
		if (medicineName.equalsIgnoreCase("Opsumit"))
			return element("inp_opsumitQuantity").getAttribute("value");
		return element("p_staticQuantity").getText();
	}

	/**
	 * This method unchecks checked dosage value
	 * 
	 * @param dosageValue
	 */
	public void uncheckCheckedDosageValues(String dosageValue) {
		wait.waitForElementToBeClickable(element("inp_dosageValues", dosageValue));
		if (element("inp_dosageValues", dosageValue).isSelected()) {
			element("inp_dosageValues", dosageValue).click();
			logMessage("User clicks on " + dosageValue + " under Dose field");
		}
	}

	/**
	 * This method save prescription as draft and review
	 */
	public void savePresAndReview() {
		clickOnSaveDraftForPres();
		verifySavedPrescriptionMsg(getYamlValue("physician.savePrescriptionMsg"));
		clickOnFinishAndReview();
	}

	/**
	 * This method clicks on dosage values for multiple medication
	 */
	public void clickOnDosageOptions(String dosageValue, String fieldName) {
		wait.waitForElementToBeClickable(element("inp_dosageValues", fieldName, dosageValue));
		if (!element("inp_dosageValues", fieldName, dosageValue).isSelected()) {
			element("inp_dosageValues", fieldName, dosageValue).click();
			logMessage("User clicks on " + dosageValue + " under " + fieldName + " field");
		}
	}

	/**
	 * This method selects dosage values for multiple medication
	 */
	public void selectDosageOptions(String keyValue, String keyName) {
		selectProvidedTextFromDropDown(element("select_dosageValues", keyName), keyValue);
		logMessage("User selects " + keyValue + " under " + keyName + " field");
	}
}