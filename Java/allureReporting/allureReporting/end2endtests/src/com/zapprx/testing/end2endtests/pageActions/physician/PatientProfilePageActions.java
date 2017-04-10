package com.zapprx.testing.end2endtests.pageActions.physician;

import static com.zapprx.testing.end2endtests.automation.utils.CustomFunctions.getCurrentDateWithSep;
import static com.zapprx.testing.end2endtests.automation.utils.CustomFunctions.getDateWithSeparator;
import static com.zapprx.testing.end2endtests.automation.utils.CustomFunctions.getRandomIntegerValue;
import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;
import com.zapprx.testing.end2endtests.automation.utils.CustomFunctions;

public class PatientProfilePageActions extends GetPage {
	WebDriver driver;
	String weight, height;

	public PatientProfilePageActions(WebDriver driver) {
		super(driver, "physician/PatientProfilePage");
		this.driver = driver;
		custom.setMedication();
	}

	/**
	 * This method upload the file and hide the uploaded file
	 * 
	 * @param medicalType
	 * @param fileName
	 */
	public void uploadFileAndRemove(String medicalType, String fileName) {
		clickOnEdit();
		selectMedicalRecord(medicalType);
		uploadFileAndVerify(fileName);
		removeUploadedFileAndContinue();
		clickOnSaveProfile();
	}

	/**
	 * This method clicks on Medical Tab
	 */

	public void clickOnClinicalTab() {
		wait.waitForLoaderToDisappear();
		isElementDisplayed("span_tab", "Clinical");
		executeJavascript("document.getElementsByClassName('tabs')[0].getElementsByTagName('li')[2].click();");
		logMessage("User clicks on Clinical tab");
	}

	/**
	 * This method clicks on the Insurance Tab
	 */
	public void clickOnInsuranceTab() {
		wait.waitForLoaderToDisappear();
		isElementDisplayed("span_tab", "Insurance");
		wait.waitForElementToBeClickable(element("span_tab", "Insurance"));
		clickUsingXpathInJavaScriptExecutor(element("span_tab", "Insurance"));
		logMessage("User clicks on Insurance tab");
	}

	/**
	 * This method clicks on Prior Auth Tab
	 */
	public void clickOnPriorAuthTab() {
		wait.waitForLoaderToDisappear();
		wait.waitForElementToBeClickable(element("span_tab", "Prior Auth"));
		element("span_tab", "Prior Auth").click();
		logMessage("User clicks on Prior Auth tab");
	}

	/**
	 * This method verifies known allergy on Medical Details
	 * 
	 * @param allergyName
	 */
	public void verifyKnownDrugAllergies(String allergyName) {
		isElementDisplayed("inp_drugAllergy");
		Assert.assertEquals(element("inp_drugAllergy").getAttribute("value"), allergyName,
				"Assertion Failed: Known Drug allergies is displayed incorrect");
		logMessage("Assertion Passed: Known Drug allergies is displayed correctly");
	}

	/**
	 * This method verifies values of options selected under Medical Details
	 * 
	 * @param inPatient
	 * @param diabetic
	 * @param bloodPressure
	 */
	public void verifyMedicalDetails(String inPatient, String diabetic, String bloodPressure) {
		verifyInPatientValue(inPatient);
		verifyDiabeticValue(diabetic);
		verifyHighBloodPressureValue(bloodPressure);
	}

	/**
	 * This method verifies In-patient value
	 * 
	 * @param inPatient
	 */
	public void verifyInPatientValue(String inPatient) {
		clickOnMedical();
		isElementDisplayed("inp_inPatient");
		Assert.assertEquals(element("inp_inPatient").getAttribute("value"), inPatient,
				"Assertion Failed: In-Patient value is displayed incorrect");
		logMessage("Assertion Passed: In-Patient value is displayed correctly");
	}

	/**
	 * This method clicks on Medical
	 */
	public void clickOnMedical() {
		executeJavascript("document.getElementById('dosage-collapse-medical').click()");
		logMessage("User clicks on Medical");
	}

	/**
	 * This method verifies diabetic value
	 * 
	 * @param diabetic
	 */
	public void verifyDiabeticValue(String diabetic) {
		Assert.assertEquals(element("inp_diabetic").getAttribute("value"), diabetic,
				"Assertion Failed: Diabetic value is displayed incorrect");
		logMessage("Assertion Passed: Diabetic value is displayed correctly");
	}

	/**
	 * This method verifies high blood pressure value
	 * 
	 * @param bloodPressure
	 */
	public void verifyHighBloodPressureValue(String bloodPressure) {
		Assert.assertEquals(element("inp_hasBloodPressure").getAttribute("value"), bloodPressure,
				"Assertion Failed: Has Blood Pressure is displayed incorrect");
		logMessage("Assertion Passed: Has Blood Pressure value is displayed correctly");
	}

	/**
	 * This method edit address and save the profile
	 * 
	 * @param address
	 */
	public void editAddressAndSaveProfile(String address) {
		clickOnEdit();
		editAddress(address);
		clickOnSaveProfile();
	}

	/**
	 * This method click on Edit button
	 */
	public void clickOnEdit() {
		wait.waitForLoaderToDisappear();
		scrollDown(element("btn_edit"));
		isElementDisplayed("btn_edit");
		executeJavascript("document.getElementById('edit_button').click();");
		logMessage("User clicks on Edit");
	}

	/**
	 * This method edit the address
	 * 
	 * @param address
	 */
	private void editAddress(String address) {
		enterText(element("inp_address"), address);
		logMessage("User edit the address in Address1 field");
	}

	/**
	 * This method clicks on Save Profile
	 */
	public void clickOnSaveProfile() {
		isElementDisplayed("btn_save");
		scrollDown(element("btn_save"));
		wait.waitForElementToBeClickable(element("btn_save"));
		executeJavascript("document.getElementById('save_button').click()");
		logMessage("User clicks on Save Profile");
	}

	/**
	 * This method verifies address is updated or not
	 * 
	 * @param address
	 */
	public void verifyUpdatedAddress(String address) {
		wait.resetImplicitTimeout(5);
		try {
			isElementDisplayed("inp_address");
			Assert.assertEquals(element("inp_address").getAttribute("value"), address,
					"Assertion Failed: Address is not updated successfully");
			logMessage("Assertion Passed: Address is updated successfully");
		} catch (StaleElementReferenceException e) {
			isElementDisplayed("inp_address");
			Assert.assertEquals(element("inp_address").getAttribute("value"), address,
					"Assertion Failed: Address is not updated successfully");
			logMessage("Assertion Passed: Address is updated successfully");
		}
		wait.resetImplicitTimeout(wait.timeout);
	}

	/**
	 * This method verifies medication name under Prior Auth tab
	 * 
	 * @param medicationName
	 */
	public void verifyMedicationOnPriorAuth(String medicationName) {
		Assert.assertEquals(element("td_medicationName", medicationName).getText(), medicationName,
				"Assertion Failed: Medication Name " + medicationName
						+ " is not getting displayed on Prior Authorization page");
		logMessage("Assertion Passed: Medication Name " + medicationName
				+ " is getting displayed on Prior Authorization page");
	}

	/**
	 * This method clicks on medication name under Prior Auth tab
	 * 
	 * @param medicationName
	 */
	public void clickMedicationOnPriorAuth(String medicationName) {
		wait.waitForLoaderToDisappear();
		element("td_medicationName", medicationName).click();
		logMessage("User clicks on medication" + medicationName + " under Prior Auth ");
	}

	/**
	 * This method selects a medical record to upload a new file
	 * 
	 * @param medicalType
	 */
	public void selectMedicalRecord(String medicalType) {
		selectProvidedTextFromDropDown(element("select_upload"), medicalType);
		logMessage("User selects " + medicalType + " from medical record dropdown inorder to upload a new file");
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
	public void verifyFileIsUploaded(String fileName) {
		Assert.assertEquals(element("link_fileName").getText(), fileName,
				"Assertion Failed: File is not uploaded successfully");
		logMessage("Assertion Passed: File is uploaded successfully");
	}

	/**
	 * This method clicks on Remove button and on confirmation modal to remove
	 * the uploaded file
	 */
	public void removeUploadedFileAndContinue() {
		clickOnRemove();
		clickHideOnConfirmationModal();
		clickOnContinue();
	}

	/**
	 * This method clicks on Remove button
	 */
	private void clickOnRemove() {
		element("btn_remove").click();
		logMessage("User clicks on Remove button to hide uploaded file");
	}

	/**
	 * This method clicks remove on confirmation modal
	 */
	private void clickHideOnConfirmationModal() {
		element("div_remove").click();
		logMessage("User clicks on Remove on confirmation modal");
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
	 * This method verifies uploaded file is not displayed once hidden
	 */
	public void verifyUploadedFileIsNotDisplayed() {
		isElementNotDisplayed("link_fileName");
		logMessage("Uploaded file is not displayed after hiding the file");
	}

	/**
	 * This method adds medication and start date to patient's clinical profile
	 * 
	 */
	public void addMedication(String medicineName) {
		selectProvidedTextFromDropDown(element("select_med"), medicineName);
		enterStartDate();
	}

	/**
	 * This method enters indication details for patients clinical profile
	 * 
	 * @param medication
	 * @param diagnosis
	 * @param otherDetails
	 */
	public void enterIndicationDetails(String indication, String diagnosis, String otherDetails) {
		selectProvidedTextFromDropDown(element("select_indication"), indication);
		selectProvidedTextFromDropDown(element("select_diagnosis"), diagnosis);
		selectProvidedTextFromDropDown(element("select_otherDetails"), otherDetails);
	}

	/**
	 * This method save profile and verify medication
	 */
	public void saveProfileAndVerifyAddedMed(String medicineName) {
		clickOnSaveProfile();
		verifyMedicationAdded(medicineName);
	}

	/**
	 * This method clicks on Add Medication
	 */
	public void clickOnAddMedication() {
		isElementDisplayed("span_addMedication");
		element("span_addMedication").click();
		logMessage("User clicks on Add Medication");
	}

	/**
	 * This method clicks on Clinical Profile
	 */
	public void clickOnClinicalProfile() {
		executeJavascript("document.getElementById('dosage-collapse-clinical-profile').click();");
		logMessage("User clicks on Clinical Profile");
	}

	/**
	 * This method verifies that indication option is correctly displayed
	 * 
	 * @param medication
	 */
	public void verifyIndicationOptionIsDisplayed(String medication) {
		Assert.assertEquals(getProvidedTextFromDropDown(element("select_indication")), medication,
				"Assertion Failed: " + medication + " indication is not displayed under patient clinical profile");
		logMessage("Assertion Passed: " + medication + " indication is displayed under patient clinical profile");
	}

	/**
	 * This method verifies that diagnosis option is correctly displayed
	 * 
	 * @param diagnosis
	 */
	public void verifyDiagnosisOptionIsDisplayed(String diagnosis) {
		Assert.assertEquals(getProvidedTextFromDropDown(element("select_diagnosis")), diagnosis, "Assertion Failed: "
				+ diagnosis + " primary diagnosis is not displayed under patient clinical profile");
		logMessage("Assertion Passed: " + diagnosis + " primary diagnosis is displayed under patient clinical profile");
	}

	/**
	 * This method verifies that other details option is correctly displayed
	 * 
	 * @param otherDetails
	 */
	public void verifyOtherDetailsOptionIsDisplayed(String otherDetails) {
		Assert.assertEquals(getProvidedTextFromDropDown(element("select_otherDetails")), otherDetails,
				"Assertion Failed: " + otherDetails + " other details is not displayed under patient clinical profile");
		logMessage("Assertion Passed: " + otherDetails + " other details is displayed under patient clinical profile");
	}

	/**
	 * This method verifies clinical profile
	 */
	public void verifyClinicalInfo(String medication, String diagnosis, String otherDetails) {
		isElementDisplayed("select_indication");
		verifyIndicationOptionIsDisplayed(medication);
		verifyDiagnosisOptionIsDisplayed(diagnosis);
		verifyOtherDetailsOptionIsDisplayed(otherDetails);
	}

	/**
	 * This method verifies clinical tab links(Clinical Profile, Medical,
	 * Prescription History, Labs) are displayed
	 */
	public void verifyClinicalTabLinksAreDisplayed() {
		isElementDisplayed("link_clincalProfile");
		isElementDisplayed("link_medical");
		isElementDisplayed("link_presDetails");
		isElementDisplayed("link_labs");
	}

	/**
	 * This method verifies medical info is displayed
	 */
	public void verifyMedicalInfoIsDisplayed(String... medicalInfo) {
		element("link_medical").click();
		for (int i = 0; i < medicalInfo.length; i++)
			isElementDisplayed("div_medicalInfo", medicalInfo[i]);
	}

	/**
	 * This method verifies prescription details info is displayed
	 */
	public void verifyPresHistoryInfoIsDisplayed(String header) {
		element("link_presDetails").click();
		isElementDisplayed("div_formHeader", header);
	}

	/**
	 * This method verifies labs info is displayed
	 */
	public void verifyLabsInfoIsDisplayed(String header) {
		element("link_labs").click();
		isElementDisplayed("div_formHeader", header);
	}

	/**
	 * This method enters value for start date
	 */
	private void enterStartDate() {
		enterText(element("inp_datePres"), getCurrentDateWithSep());
		logMessage("User enter Start Date");
	}

	/**
	 * This method verify that newly added medication is displayed once saved
	 */
	public void verifyMedicationAdded(String medicineName) {
		wait.waitForLoaderToDisappear();
		Assert.assertEquals(element("td_newMedication", medicineName).getText(), medicineName,
				"Assertion Failed: Added medication is not displayed");
		logMessage("Assertion Passed: Added Medication is displayed");
	}

	/**
	 * This method verifies value insurance name under primary insurance
	 * 
	 * @param primaryInsurance
	 */
	public void verifyPrimaryInsurance(String primaryInsurance) {
		Assert.assertEquals(element("inp_insuranceName", "Primary Insurance", "Carrier").getAttribute("value"),
				primaryInsurance,
				"Assertion Failed: The value for Primary Insurance " + primaryInsurance + " is displayed incorrect");
		logMessage("Assertion Passed: The value for Primary Insurance " + primaryInsurance + " is displayed correct");
	}

	/**
	 * This method verifies value insurance name under secondary insurance
	 * 
	 * @param secondaryInsurance
	 */
	public void verifySecondaryInsurance(String secondaryInsurance) {
		Assert.assertEquals(element("inp_insuranceName", "Secondary Insurance", "Carrier").getAttribute("value"),
				secondaryInsurance,
				"Assertion Failed: The value for Primary Insurance " + secondaryInsurance + " is displayed incorrect");
		logMessage(
				"Assertion Passed: The value for Primary Insurance " + secondaryInsurance + " is displayed incorrect");
	}

	/**
	 * This method edit the value of All Known Allergies
	 * 
	 * @param allergyName
	 */
	public void editAllKnownAllergies(String allergyName) {
		isElementDisplayed("inp_drugAllergy");
		enterText(element("inp_drugAllergy"), allergyName);
		logMessage("User edit the value for All Known Allergies and enters " + allergyName);
	}

	/**
	 * This method edits values for weight field
	 */
	public void editWeight() {
		weight = "" + CustomFunctions.getRandomIntegerValue();
		enterText(element("inp_weight"), weight);
		logMessage("This method edit the weight field and enters " + weight);
	}

	/**
	 * This method edits values for height field
	 */
	public void editHeight() {
		height = "" + CustomFunctions.getRandomIntegerValue();
		enterText(element("inp_height"), height);
		logMessage("This method edit the height field and enters " + height);
	}

	/**
	 * This method verifies value has been updated for weight field
	 */
	public void verifyWeightValue() {
		Assert.assertEquals(element("inp_disWeight").getAttribute("value").split("k")[0].trim(), weight,
				"Assertion Failed: Weight value is displayed incorrect");
		logMessage("Assertion Passed: Weight value is displayed correctly");
	}

	/**
	 * This method verifies value has been updated for height field
	 */
	public void verifyHeightValue() {
		Assert.assertEquals(element("inp_disHeight").getAttribute("value").split("i")[0].trim(), height,
				"Assertion Failed: Height value is displayed incorrect");
		logMessage("Assertion Passed: Height value is displayed correctly");
	}

	/**
	 * This method verifies the update data
	 * 
	 * @param allergyName
	 */
	public void verifyEditedFields(String allergyName) {
		verifyKnownDrugAllergies(allergyName);
		verifyHeightValue();
		verifyWeightValue();
	}

	/**
	 * This method clicks on Labs
	 */
	public void clickOnLabs() {
		isElementDisplayed("link_labs");
		executeJavascript("document.getElementById('dosage-collapse-labs').click();");
		logMessage("User click on Labs");
	}

	/**
	 * This method enters value in 'Reason Ended' field
	 * 
	 * @param reason
	 */

	private void enterReasonEnded(String reason) {
		enterText(element("td_reason"), reason);
		logMessage("User edit reason ended field");
	}

	/**
	 * This method selects status from 'Status' dropdown
	 * 
	 * @param status
	 */
	private void selectStatus(String status) {
		selectProvidedTextFromDropDown(element("select_status"), status);
		logMessage("User selects status from status field");
	}

	/**
	 * This method edit Prior And Current Medication Info and click on save
	 * 
	 * @param reason
	 * @param status
	 */

	public void editPriorAndCurrentMedicationInfoAndSave(String reason, String status) {
		enterReasonEnded(reason);
		selectStatus(status);
		clickOnSaveProfile();
	}

	/**
	 * This method verifies value has been updated for 'Reason Ended' field
	 * 
	 * @param reason
	 */
	private void verifyReasonEndedField(String reason) {
		Assert.assertEquals(element("td_priorReasonTxt").getText(), reason,
				"Assertion Failed: Reason Ended is displayed incorrectly");
		logMessage("Assertion Passed: Reason Ended is displayed correctly");
	}

	/**
	 * This method verifies value has been updated for 'Status' field
	 * 
	 * @param status
	 */
	private void verifyStatusField(String status) {
		Assert.assertEquals(element("td_priorStatusTxt").getText(), status,
				"Assertion Failed: Status is displayed incorrectly");
		logMessage("Assertion Passed: Status is displayed correctly");
	}

	/**
	 * This method verifies Prior And Current Medication Info
	 * 
	 * @param reason
	 * @param status
	 */
	public void verifyPriorAndCurrentMedicationInfo(String reason, String status) {
		verifyReasonEndedField(reason);
		verifyStatusField(status);
	}

	/**
	 * This method verifies secondary insurance type
	 * 
	 * @param insurance
	 */
	public void verifySecondaryInsuranceHeader(String insurance) {
		Assert.assertEquals(element("h4_insurancePBMHeader", insurance).getText(), insurance,
				"Assertion Failed: Insurnace type '" + insurance + "' is not displayed");
		logMessage("Assertion Passed: Insurnace type '" + insurance + "' is displayed");
	}

	/**
	 * This element verifies addition and deletion of Secondary Insurance
	 */
	public void verifyAddAndRemoveInsurance(String insurance) {
		verifySecondaryInsuranceHeader(insurance);
		isElementDisplayed("btn_removeInsuracePBM", insurance);
		clickOnRemoveInsurancePBM(insurance);
		clickOnDelete();
		isElementNotDisplayed("h4_insurancePBMHeader", insurance);
	}

	/**
	 * This element verifies addition and deletion of Pharmacy benefits
	 */
	public void verifyAddAndRemovePBM(String insurance) {
		isElementDisplayed("h4_insurancePBMHeader", insurance);
		isElementDisplayed("btn_removeInsuracePBM", insurance);
		isElementNotDisplayed("btn_addPharBenefit");
		clickOnRemoveInsurancePBM(insurance);
		clickOnDelete();
		isElementNotDisplayed("h4_insurancePBMHeader", insurance);
		isElementDisplayed("btn_addPharBenefit");
	}

	/**
	 * This method clicks on 'Add Another Insurance Plan'
	 */
	public void clickOnAddInsurance() {
		element("btn_addInsurance").click();
		logMessage("User clicks on 'Add Another Insurance Plan'");
	}

	/**
	 * This method clicks on 'Add Pharmacy Benefit'
	 */
	public void clickOnAddPharmacyBenefit() {
		element("btn_addPharBenefit").click();
		logMessage("User clicks on 'Add Pharmacy Benefits'");
	}

	/**
	 * This method verifies button 'Add Pharmacy Benefit'
	 */
	public void verifyAddPharmacyBenefit() {
		isElementDisplayed("btn_addPharBenefit");
	}

	/**
	 * This method clicks on remove PBM
	 */
	private void clickOnRemoveInsurancePBM(String insurance) {
		element("btn_removeInsuracePBM", insurance).click();
		logMessage("User clicks on 'Remove' Insurance/PBM");
	}

	/**
	 * This method clicks on 'Delete'
	 */
	private void clickOnDelete() {
		element("btn_delete").click();
		logMessage("User clicks on 'Delete' from modal");
		wait.waitForElementToBeInVisible(getLocator("btn_delete"));
	}

	/**
	 * This method verifies that 'Remove PBM' text is displayed to remove
	 * Pharmacy Benefits
	 * 
	 * @param status
	 */
	public void verifyRemovePBMText(String insurance) {
		Assert.assertEquals(element("btn_removeInsuracePBM", insurance).getText(), "Remove PBM",
				"Assertion Failed: Remove PBM text is displayed incorrectly");
		logMessage("Assertion Passed: Remove PBM text is displayed correctly");
	}

	/**
	 * This method verifies all the Prior and Current medications present under
	 * patient profile
	 * 
	 * @param medications
	 */
	public void verifyPriorAndCurrentMedications(String... medications) {
		scrollDown(element("span_medications"));
		for (int i = 0; i < medications.length; i++) {
			Assert.assertTrue(elements("span_medications").get(i).getText().equalsIgnoreCase(medications[i]),
					"Assertion Failed: Medication " + medications[i]
							+ " is not dispalyed under Prior and Current Medications");
			logMessage("Assertion Passed: Medication " + medications[i]
					+ " is dispalyed under Prior and Current Medications");
		}
	}

	/**
	 * This method clicks on 'cross' to remove medication
	 */
	private void clickOnCross() {
		element("i_medRemove").click();
		logMessage("User clicks on Remove Medication Button");
	}

	/**
	 * This method verifies remove medication confirmation message
	 */
	public void verifyRemoveConfirmationMessage(String removeMsg) {
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
		clickOnAddMedication();
		isElementDisplayed("select_med");
		isElementDisplayed("i_medRemove");
		clickOnCross();
		verifyRemoveConfirmationMessage(removeMsg);
		clickOnYesFromModal();
		isElementNotDisplayed("select_med");
	}

	/**
	 * This method validates no currently approved PA message under 'Prior Auth'
	 * tab i.e. PA does not exist on patient profile page
	 */
	public void verifyPriorAuthNotExist(String priorAuthMsg) {
		Assert.assertEquals(element("section_priorAuthMsg").getText(), priorAuthMsg,
				"Assertion Failed: No Prior Authorization exist under 'Prior Auth' tab");
		logMessage("Assertion Passed: Prior Authorization exist under 'Prior Auth' tab");
	}

	/**
	 * This method verifies General questions
	 */
	public void verifyGeneralQuestions() {
		scrollDown(element("h5_generalques"));
		isElementDisplayed("h5_generalques");
	}

	/**
	 * This method clicks on 'Medication-specific Questions'
	 */
	private void clickOnMedSpecificQuestions() {
		element("h5_medSpecificQues").click();
		logMessage("User clicks on 'Medication-specific Questions'");
	}

	/**
	 * This method verifies medications under Prior And Current Medication
	 * 
	 * @param medicineName
	 */
	public void verifyMedUnderPriorAndCurrentMedication(String medicineName) {
		scrollDown(element("span_medications"));
		List<String> priorMedications = new ArrayList<String>();
		for (WebElement medications : elements("span_medications")) {
			priorMedications.add(medications.getText().trim());
		}
		Assert.assertTrue(priorMedications.contains(medicineName), "Assertion Failed: Medication " + medicineName
				+ " is not dispalyed under Prior and Current Medications");
		logMessage(
				"Assertion Passed: Medication " + medicineName + " is dispalyed under Prior and Current Medications");
	}

	/**
	 * This method compares Medication Speific questions on Patient profile with
	 * that on PA Form
	 * 
	 * @param paMedQues
	 * @param medicineName
	 */
	public void verifyMedSpecificQuestions(List<String> paMedQues, String medicineName) {
		clickOnMedSpecificQuestions();
		scrollDown(elements("div_medQues", medicineName).get(0));
		for (int count = 0; count < elements("div_medQues", medicineName).size(); count++) {
			Assert.assertEquals(elements("div_medQues", medicineName).get(count).getText().trim(), paMedQues.get(count),
					"Assertion Failed: Value " + paMedQues.get(count) + " is displayed incorrectly");
			logMessage("Assertion Passed: Value " + paMedQues.get(count) + " is displayed correctly");
		}
	}

	/**
	 * This method clicks on 'Add additional symptom'
	 */
	public void clickOnAddAdditionalSymptom() {
		element("btn_addAddtnlSymptom").click();
		logMessage("User clicks on 'Add Additional Symptom' button");
	}

	/**
	 * This method selects symptom name from dropdown
	 * 
	 * @param symptomName
	 */
	private void selectSymptomName(String symptomName) {
		selectProvidedTextFromDropDown(element("select_symptomName"), symptomName);
		logMessage("User selects Symptom Name from dropdown");
	}

	/**
	 * This method enters value for start date under 'Add New Symptom'
	 */
	private void enterSymptomStartDate() {
		enterText(element("inp_symStartDate"), getDateWithSeparator());
		logMessage("User enters Start Date");
	}

	/**
	 * This method enters value for end date under 'Add New Symptom'
	 */
	private void enterSymptomEndDate() {
		enterText(element("inp_symEndDate"), getCurrentDateWithSep());
		logMessage("User enters Start Date");
	}

	/**
	 * This method clicks on 'Save' button on Symptom modal
	 */
	private void clickOnSaveOnSymptomModal() {
		element("btn_symSave").click();
		logMessage("User clicks on 'Save' on Symtom modal");
	}

	/**
	 * This method verifies Symptom Name
	 * 
	 * @param symptomName
	 */
	private void verifySymptomName(String symptomName) {
		Assert.assertEquals(element("td_symptomName", symptomName).getText(), symptomName,
				"Assertion Failed: Symptom name : " + symptomName + " is not displayed");
		logMessage("Assertion Passed: Symptom name : " + symptomName + " is displayed");
	}

	/**
	 * This method clicks on Symptom Name
	 * 
	 * @param symptomName
	 */
	private void clickOnSymptomName(String symptomName) {
		wait.waitForStableDom(250);
		element("td_symptomName", symptomName).click();
		logMessage("User clicks on Symptom Name");
	}

	/**
	 * This method clicks on 'Delete' button on Symptom modal
	 */
	private void clickOnDeleteFromSymptomModal() {
		element("btn_symDelete").click();
		logMessage("User clicks on 'Delete' from symptom modal");
	}

	/**
	 * This method clicks on 'Yes' on Symptom modal
	 */
	private void clickOnYes() {
		wait.waitForElementToBeVisible(element("btn_symYes"));
		element("btn_symYes").click();
		logMessage("User clicks on 'Yes' from symptom modal");
	}

	/**
	 * This method clicks on Okay
	 */
	private void clickOnOkay() {
		element("btn_okay").click();
		logMessage("User clicks on 'Okay' from symptom modal");
	}

	/**
	 * This method adds new symptom
	 * 
	 * @param symptomName
	 */
	public void addNewSymptom(String symptomName) {
		clickOnAddAdditionalSymptom();
		selectSymptomName(symptomName);
		enterSymptomStartDate();
		enterSymptomEndDate();
		clickOnSaveOnSymptomModal();
		clickOnOkay();
	}

	/**
	 * This method deletes symptom
	 * 
	 * @param symptomName
	 */
	public void deleteSymptom(String symptomName) {
		clickOnSymptomName(symptomName);
		clickOnDeleteFromSymptomModal();
		clickOnYes();
		clickOnOkay();
	}

	/**
	 * This method validated addition and deletion of 'Additional Symptoms'
	 * 
	 * @param symptomName
	 */
	public void verifyAdditionAndRemovalOfAdditionalSymptoms(String symptomName) {
		addNewSymptom(symptomName);
		clickOnSaveProfile();
		verifySymptomName(symptomName);
		clickOnEdit();
		deleteSymptom(symptomName);
		isElementNotDisplayed("td_symptomName", symptomName);
	}

	/**
	 * This method validates contact name unde Home Address section
	 * 
	 * @param firstname
	 * @param lastname
	 */
	public void verifyContactNameUnderHomeAddress(String firstname, String lastname) {
		Assert.assertEquals(element("inp_contactName").getAttribute("value"), firstname + " " + lastname,
				"Assertion Failed: Contact name : " + firstname + " " + lastname + " is not displayed");
		logMessage("Assertion Passed: Contact name : " + firstname + " " + lastname + " is displayed");
	}

	/**
	 * This method verifies height/weight will be required if weight_units
	 * selected, and vice versa
	 */
	public void verifyHeightWeightReqFields() {
		enterHeightAndWeight();
		verifyHeightWeightUnitsIsRequiredField();
		clearHeightAndWeight();
		selectHeightAndWeightUnits();
		verifyHeightWeightIsRequiredField();
	}

	/**
	 * This method enters values in height and weight fields
	 */
	public void enterHeightAndWeight() {
		enterText(element("inp_height"), "" + getRandomIntegerValue());
		logMessage("User enter value in Height field");
		enterText(element("inp_weight"), "" + getRandomIntegerValue());
		logMessage("User enter value in Weight field");
	}

	/**
	 * This method clears values of height and weight fields
	 */
	public void clearHeightAndWeight() {
		element("inp_height").clear();
		element("inp_weight").clear();
	}

	/**
	 * This method selects height and weight units
	 */
	public void selectHeightAndWeightUnits() {
		selectProvidedTextFromDropDown(element("select_heightUnits"),
				getYamlValue("physician.generalInfo.heightUnits"));
		logMessage("User selects " + getYamlValue("physician.generalInfo.heightUnits") + " from Height Units dropdown");
		selectProvidedTextFromDropDown(element("select_weightUnits"),
				getYamlValue("physician.generalInfo.weightUnits"));
		logMessage("User selects " + getYamlValue("physician.generalInfo.weightUnits") + " from Weight Units dropdown");
	}

	/**
	 * This method verifies 'Height Units' and 'Weight Units' is required field
	 * after entering values in height and weight fields
	 */
	public void verifyHeightWeightUnitsIsRequiredField() {
		Assert.assertTrue(element("select_heightUnits").getAttribute("required").equalsIgnoreCase("true"),
				"Assertion Failed: 'Height Units' is not a mandatory field");
		logMessage("Assertion Passed: 'Height Units' is a mandatory field");
		Assert.assertTrue(element("select_weightUnits").getAttribute("required").equalsIgnoreCase("true"),
				"Assertion Failed: 'Weight Units' is not a mandatory field");
		logMessage("Assertion Passed: 'Weight Units' is a mandatory field");
	}

	/**
	 * This method verifies 'Height' and 'Weight' is required field after
	 * selecting units for 'Height Units' and 'Weight Units' fields
	 */
	public void verifyHeightWeightIsRequiredField() {
		Assert.assertTrue(element("inp_height").getAttribute("required").equalsIgnoreCase("true"),
				"Assertion Failed: 'Height' is not a mandatory field");
		logMessage("Assertion Passed: 'Height' is a mandatory field");
		Assert.assertTrue(element("inp_weight").getAttribute("required").equalsIgnoreCase("true"),
				"Assertion Failed: 'Weight' is not a mandatory field");
		logMessage("Assertion Passed: 'Weight' is a mandatory field");
	}

	/**
	 * This method clicks on Cancel Button
	 */
	public void clickOnCancelButton() {
		element("btn_cancel").click();
		logMessage("User clicks on Cancel button");
	}

	public void verifyPriorAuthExist(String medicine) {
		Assert.assertEquals(element("td_medicationName", medicine).getText(), medicine,
				"Assertion Failed: Prior Authorization does not exist under 'Prior Auth' tab");
		logMessage("Assertion Passed: Prior Authorization exist under 'Prior Auth' tab");
	}
}
