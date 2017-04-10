package com.zapprx.testing.end2endtests.pageActions.physician;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class DiagnosisPageActions extends GetPage {
	WebDriver driver;
	private ArrayList<String> diagnosis;

	public DiagnosisPageActions(WebDriver driver) {
		super(driver, "physician/DiagnosisPage");
		this.driver = driver;
	}

	/**
	 * This method verify mandatory fields on diagnosis
	 */
	public void verifyMandatoryFieldOnDiagnosis() {
		Assert.assertTrue(
				element("select_requiredField", element("select_primaryDiagnosis").getAttribute("id"))
						.getAttribute("class").contains("required"),
				"Assertion Failed: 'Primary Diagnosis: Pulmonary Arterial Hypertension ' is not a mandatory field");
		logMessage("Assertion Passed: 'Primary Diagnosis: Pulmonary Arterial Hypertension' is a mandatory field");
		Assert.assertTrue(element("select_requiredField", element("select_newDiagnosis").getAttribute("id"))
				.getAttribute("class").contains("required"),
				"Assertion Failed: 'New diagnosis? ' is not a mandatory field");
		logMessage("Assertion Passed: 'New diagnosis?' is a mandatory field");
	}

	/**
	 * This method enter details on Diagnosis Page and clicks on 'Dosage'
	 * 
	 * @param primaryDiagnosis
	 * @param newDiagnosis
	 */
	public void enterDiagnosisDetailsAndClickDosage(String primaryDiagnosis, String newDiagnosis, String otherDetails) {
		enterDiagnosisDetails(primaryDiagnosis, newDiagnosis, otherDetails);
		clickOnDosage();
	}

	/**
	 * This method enter details on Diagnosis Page
	 * 
	 */
	public void enterDiagnosisDetails(String primaryDiagnosis, String newDiagnosis, String otherDetails) {
		selectPrimaryDiagnosis(primaryDiagnosis);
		selectNewDiagnosis(newDiagnosis);
		diagnosis = new ArrayList<String>(Arrays.asList(getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.diagnosis.diagnosis2"),
				getYamlValue("physician.indication3.medicines.medicine1.diagnosis")));
		if (diagnosis.contains(primaryDiagnosis)) {
			selectProvidedTextFromDropDown(element("select_otherDetails"), otherDetails);
		}
	}

	/**
	 * This method clicks on Additional diagnosis checkbox option
	 * 
	 * @param diagnosis
	 */
	public void clickAdditionalDiagnosisCheckbox(String diagnosis) {
		element("additional_diagnosis").click();
		executeJavascript("document.getElementById('secondary_diagnoses').getElementsByTagName('input')[0].click();");
		logMessage("User clicks on " + diagnosis + " additional diagnosis checkbox");
	}

	/**
	 * This method verifies the 'New diagnosis?' box prefilled text
	 * 
	 * @param option
	 */
	public void verifyNewDiagnosisBoxText(String option) {
		Assert.assertEquals(element("option_newDiagnosis").getText(), option,
				"Assertion Failed: 'New Diagnosis?' is not pre filled as '" + option + "'");
		logMessage("Assertion Passed: 'New Diagnosis?' is pre filled as '" + option + "'");
	}

	/**
	 * This method selects Primary Diagnosis from Diagnosis Page
	 * 
	 * @param primaryDiagnosis
	 */
	private void selectPrimaryDiagnosis(String primaryDiagnosis) {
		isElementDisplayed("select_primaryDiagnosis");
		wait.waitForElementTextToContain(element("select_primaryDiagnosis"), primaryDiagnosis);
		selectProvidedTextFromDropDown(element("select_primaryDiagnosis"), primaryDiagnosis);
		logMessage("User selects " + primaryDiagnosis + " from Primary Diagnosis dropdown");
	}

	/**
	 * This method selects New Diagnosis from Diagnosis Page
	 * 
	 * @param newDiagnosis
	 */
	private void selectNewDiagnosis(String newDiagnosis) {
		selectProvidedTextFromDropDown(element("select_newDiagnosis"), newDiagnosis);
		logMessage("User selects " + newDiagnosis + " from New Diagnosis dropdown");
	}

	/**
	 * This method clicks on Step4: Dosage
	 */
	public void clickOnDosage() {
		isElementDisplayed("btn_dosage");
		executeJavascript("document.getElementById('next_button').click();");
		logMessage("User clicks on Step3: Dosage");
	}

	/**
	 * This method verifies that user is on diagnosis page after clicking on
	 * 'Dosage' Button
	 */
	public void verifyUserIsOnDiagnosisPage() {
		// isElementNotDisplayed("hdr_details");
		isElementDisplayed("select_newDiagnosis");
	}

	/**
	 * This method clicks on capture patient consent
	 */
	public void clickOnCaptPatientConsent() {
		isElementDisplayed("btn_capPatCon");
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
	 * This method clicks on Add Additional Medication
	 */
	private void clickOnAddAdditionalMedication() {
		element("sapn_addAdditionalMed").click();
		logMessage("User clicks on Remove Medication Button");
	}

	/**
	 * This method clicks on 'cross' to remove medication
	 */
	private void clickOnCross() {
		element("i_medRemove").click();
		logMessage("User clicks on Remove Medication Button");
	}

	/**
	 * This method verifies remove medication confirmation message after removing Additional Medication
	 */
	private void verifyRemoveConfirmationMessage(String removeMsg) {
		Assert.assertEquals(element("p_removeMedConfirm").getText().trim(), removeMsg,
				"Assertion Failed: Remove medication confirmation message : " + removeMsg + " is not displayed");
		logMessage("Assertion Passed: Remove medication confirmation message : " + removeMsg + " is displayed");
	}

	/**
	 * This method clicks on 'Yes' from med removal confirmation modal
	 */
	private void clickOnYesFromModal() {
		element("btn_modalYes").click();
		logMessage("User clicks on Yes from modal");
	}

	/**
	 * This method verifies addition and removal of 'Add Additional Medications'
	 * 
	 * @param removeMsg
	 */
	public void verifyAdditionAndRemovalOfAdditionalMed(String removeMsg) {
		clickOnAddAdditionalMedication();
		isElementDisplayed("select_medication");
		isElementDisplayed("i_medRemove");
		clickOnCross();
		verifyRemoveConfirmationMessage(removeMsg);
		clickOnYesFromModal();
		isElementNotDisplayed("select_medication");
	}

	/**
	 * This method validates that the value is displayed selected
	 * 
	 * @param diagnosis
	 */
	public void verifyPrimaryDiagnosis(String diagnosis) {
		Assert.assertEquals(getProvidedTextFromDropDown(element("select_primaryDiagnosis")), diagnosis,
				"Assertion Failed: " + diagnosis + " value is not appeared as selected");
		logMessage("Assertion Passed: " + diagnosis + " value is appeared as selected");
	}

	/**
	 * This method verifies medication is correctly displayed under 'Prior and
	 * Current Medications' section on Diagnosis page
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
	 * This method clicks on Step1: Patient
	 */
	public void clickOnPatient() {
		isElementDisplayed("a_patient");
		element("a_patient").click();
		logMessage("User clicks on Step1: Patient");
	}

	/**
	 * This method selects additional medication
	 */
	private void selectAdditionalMedication(String medication) {
		selectProvidedTextFromDropDown(element("select_medication"), medication);
		logMessage("User selects medication from dropdown");
	}

	/**
	 * This method enters value in 'Reason Ended' field
	 * 
	 * @param specialChar
	 */
	private void enterValueInReasonEnded(String specialChar) {
		enterText(element("inp_reasonEnded"), specialChar);
		logMessage("This method enters '" + specialChar + "' in 'Reason Ended' field");
	}

	/**
	 * This method adds addiltional medication
	 * 
	 * @param medication
	 * @param specialChar
	 */
	public void addAdditionalMedication(String medication, String specialChar) {
		clickOnAddAdditionalMedication();
		selectAdditionalMedication(medication);
		enterValueInReasonEnded(specialChar);
	}

	/**
	 * This method verifies general question is not displayed
	 */
	public void verifyGeneralQuestions() {
		isElementNotDisplayed("h5_generalques");
	}

	/**
	 * This method validates that the value in 'Other Details' field is
	 * displayed selected
	 * 
	 * @param otherDetails
	 */
	public void verifyOtherDetails(String otherDetails) {
		Assert.assertEquals(getProvidedTextFromDropDown(element("select_otherDetails")), otherDetails,
				"Assertion Failed: " + otherDetails + " value is not appeared as selected");
		logMessage("Assertion Passed: " + otherDetails + " value is appeared as selected");
	}

}
