package com.zapprx.testing.end2endtests.pageActions.common;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import com.zapprx.testing.end2endtests.automation.TestSessionInitiator;
import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;
import com.zapprx.testing.end2endtests.automation.utils.ReadFromPDF;
import com.zapprx.testing.end2endtests.automation.utils.YamlReader;

public class PatientCommonWorkflow extends GetPage {
	TestSessionInitiator test;
	private ArrayList<String> medications;
	private String[] lastName = new String[4];
	private static int patientCount = 0;
	String medicineValue = YamlReader.getYamlValue("physician.chooseMedication"), med;
	String[] medicineDetails;
	String[] quantity, strength, doses, dosage, frequency, dose, diluent, pump, doseStrength, ampuleTablet;
	String selectedQuantity, selectedStrength, selectedDosage, refillValue;

	public PatientCommonWorkflow(TestSessionInitiator test, WebDriver driver) {
		super(driver, "");
		this.test = test;
	}

	/**
	 * This method verifies that physician is successfully able to login
	 */
	public void verifyPhysicianIsAbleToLogin(String userName, String password) {
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
		test.loginPage.loginUser(userName, password);
		test.headerPage.verifyUserIsOnHomepage(getYamlValue("physician.headerText"));
	}

	/**
	 * This method verifies that admin is successfully able to login
	 */
	public void verifyAdminIsAbleToLogin(String userName, String password) {
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("admin.pageTitle"));
		test.loginPage.loginUser(userName, password);
		test.headerPage.verifyUserIsOnAdminHomepage();
	}

	/**
	 * This method verifies that newly added doctor is successfully able to
	 * login
	 */
	public void verifyNewlyAddedDoctorLogin(String emailId, String password) {
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
		test.loginPage.loginUser(emailId, password);
		test.headerPage.verifyUserIsOnHomepage(getYamlValue("physician.headerText"));
	}

	/**
	 * This method registers a new patient
	 */
	public String[] registerPatient() {
		test.homePage.clickOnRegister();
		String[] lastNameDOBAndGender = test.patientRegistrationPage
				.registerNewPatient(getYamlValue("physician.firstName"));
		return lastNameDOBAndGender;
	}

	/**
	 * This method registers a new patient
	 */
	public String[] registerMalePatient() {
		test.homePage.clickOnRegister();
		String[] lastNameDOBAndGender = test.patientRegistrationPage
				.registerNewMalePatient(getYamlValue("physician.firstName"));
		return lastNameDOBAndGender;
	}

	/**
	 * This method stores patient lastname
	 * 
	 * @return
	 */
	public void savePatientLastname(String lastname) {
		lastName[patientCount] = lastname;
		patientCount = patientCount + 1;
	}

	/**
	 * This method registers patient with provided gender
	 * 
	 * @param gender
	 * @return
	 */
	public String[] registerPatientWithGivenGender(String gender) {
		test.homePage.clickOnRegister();
		String[] lastNameAndDOB = test.patientRegistrationPage
				.enterFirstNameLastNameDob(getYamlValue("physician.firstName"));
		test.patientRegistrationPage.selectGender(gender);
		test.patientRegistrationPage.clickOnSavePatient();
		return lastNameAndDOB;
	}

	/**
	 * This method deletes multiple patients
	 */
	public void deletePatients() {
		test.openUrl(getYamlValue("djangoadmin.baseUrl"));
		test.djangoAdminPage.loginAndSelectActors(getYamlValue("djangoadmin.emailId"),
				getYamlValue("djangoadmin.password"));
		while (patientCount > 0) {
			patientCount = patientCount - 1;
			test.djangoAdminPage.deleteUserAndVerify(lastName[patientCount], getYamlValue("djangoadmin.actionValue"),
					getYamlValue("djangoadmin.successMessage"));
		}
	}

	/**
	 * This method completes patient profile
	 * 
	 * @param insuranceName
	 * @param policyId
	 * @param groupNo
	 * @param state
	 * @return
	 */

	public String[] completeProfile(String insuranceName, String policyId, String groupNo, String state) {
		String[] loginEmailAndMRN = enterGeneralInfoAndInsuranceInfo(insuranceName, policyId, groupNo, state);
		test.headerPage.verifyUserIsOnCorrectPage("Clinical Information");
		test.patientRegistrationPage.enterMedicalHistory(getYamlValue("physician.medicalHistory.knownAllergy1"));
		test.patientRegistrationPage.clickOnCompleteRegistration();
		return loginEmailAndMRN;
	}

	/**
	 * This method enters general and insurance information for the patient
	 * 
	 * @param insuranceName
	 * @param policyId
	 * @param groupNo
	 * @param state
	 * @return
	 */
	public String[] enterGeneralInfoAndInsuranceInfo(String insuranceName, String policyId, String groupNo,
			String state) {
		test.headerPage.verifyHeaderOfPage("New Patient Registration");
		String[] loginEmailAndMRN = test.patientRegistrationPage.enterGeneralInfo(
				getYamlValue("physician.generalInfo.state"), getYamlValue("physician.patientPassword"),
				getYamlValue("physician.firstName"));
		test.headerPage.verifyUserIsOnCorrectPage("Insurance Information");
		test.patientRegistrationPage.enterInsuranceInfo(insuranceName, policyId, groupNo, state);
		test.patientRegistrationPage.clickOnClinical();
		return loginEmailAndMRN;
	}

	/**
	 * This method enters Clinical information and clicks on complete
	 * Registration
	 * 
	 * @param medication
	 * @param diagnosis
	 * @param otherDetails
	 */

	public void enterClinicalInfoAndCompleteReg(String medication, String diagnosis, String otherDetails) {
		test.headerPage.verifyUserIsOnCorrectPage("Clinical Information");
		test.patientRegistrationPage.enterMedicalHistory(getYamlValue("physician.medicalHistory.knownAllergy1"));
		test.patientRegistrationPage.enterClinicalProfileDetails(medication, diagnosis, otherDetails);
		test.patientRegistrationPage.clickOnCompleteRegistration();
	}

	/**
	 * This method search for the newly created patient in Patients list
	 */
	public void searchPatientAndSelect(String patientLastName) {
		test.patientRegistrationPage.verifyUserIsOnRegSuccessModal(getYamlValue("physician.regSuccessMsg"),
				getYamlValue("physician.firstName"));
		test.patientRegistrationPage.clickHomeOnRegistrationSuccess();
		test.homePage.clickOnPrescribe();
		test.patientPage.searchPatient(patientLastName);
		test.patientPage.selectPatientByName(patientLastName);
	}

	/**
	 * This method clicks on View Profile and then clicks on Clinical Profile
	 */
	public void viewClinicalProfile() {
		test.patientPage.clickOnViewProfile();
		test.patientProfilePage.clickOnClinicalTab();
		test.patientProfilePage.clickOnClinicalProfile();
	}

	/**
	 * This method clicks on Patients on left navigation bar and the search and
	 * select patient
	 * 
	 * @param patientName
	 */
	public void clickPatientsSearchAndSelect(String patientName) {
		test.leftnavigationPage.clickOnPatients();
		test.patientPage.searchPatient(patientName);
		test.patientPage.selectPatientByName(patientName);
	}

	/**
	 * This method clicks on Prescribe and select indication
	 * 
	 * @param indicationName
	 */
	public void clickPresAndSelectIndication(String indicationName) {
		test.patientPage.clickOnPrescribe();
		test.headerPage.verifyHeaderText("Choose an indication");
		test.indicationPage.selectIndication(indicationName);
	}

	/**
	 * This method clicks on Prescribe on modal and select indication
	 * 
	 * @param indicationName
	 */
	public void clickPresOnPatientModalAndSelectIndication(String indicationName) {
		test.patientPage.clickPrescribeOnPatientModal();
		test.headerPage.verifyHeaderText("Choose an indication");
		test.indicationPage.selectIndication(indicationName);
	}

	/**
	 * This method clicks on patient consent and select indication and then
	 * choose medication
	 * 
	 * @param indicationName
	 * @param medication
	 */
	public boolean clickPatConsentAndSelectIndication_Medication(String indicationName, String medication) {
		test.headerPage.verifyHeaderText_PatientConsent("Choose an indication");
		test.indicationPage.selectIndicationForPatientConsent(indicationName);
		boolean remsValue = test.chooseMedicationPage.selectMedicationForPatientConsent(medication);
		logMessage("User has selected indication : " + indicationName + " and medication : " + medication);
		return remsValue;
	}

	/**
	 * This method verifies user is on Share Authorization page after patient
	 * consent
	 */
	public void verifyUserIsOnPatientConsentPageToAuthorize() {
		test.patientPage.verifyHeaderText();
		logMessage("User is on Share Authorization page for Patient!!");
	}

	/**
	 * This method selects medication
	 * 
	 */
	public boolean chooseMedication(String medication, String lastname) {
		test.headerPage.verifyHeaderText("Choose a medication");
		boolean remsValue = test.chooseMedicationPage.selectMedication(medication);
		test.chooseMedicationPage.clickOnSkipStep();
		test.headerPage.verifyHeaderText(
				"Complete " + medication + " script for " + getYamlValue("physician.firstName") + " " + lastname);
		ArrayList<String> med = new ArrayList<String>(
				Arrays.asList("Revatio", "Actemra IV", "Actemra SC", "Cosentyx", "Enbrel", "Simponi", "Stelara"));
		if (med.contains(medication)) {
			test.patientDetailPage.sendToHub();
		}
		return remsValue;
	}

	/**
	 * This method perform prescribe medication flow for patient
	 * 
	 * @param medication
	 * @param diagnosis
	 */
	public boolean prescribePatient(String medication, String diagnosis, String hub1, String gender, String lastName,
			String otherDetails) {
		boolean remsValue = chooseMedication(medication, lastName);
		test.patientDetailPage.enterPatientDetails(getYamlValue("physician.medicalHistory.knownAllergy1"), hub1,
				gender);
		test.patientDetailPage.clickOnDiagnosisStep();
		test.patientDetailPage.clickOnPatientProfileModal();
		test.diagnosisPage.enterDiagnosisDetailsAndClickDosage(diagnosis,
				getYamlValue("physician.indication1.diagnosis.newDiagnosis"), otherDetails);
		return remsValue;
	}

	/**
	 * This method authorize prescription
	 */
	public void authorizePres(String gender, boolean remsValue, String medicineName, String password) {
		test.commonDosagePage.clickOnAuthorize();
		test.authorizationPage.enterPrescriberAuth(gender, remsValue, medicineName);
		test.authorizationPage.enterPhysicianSavedSig(password);
	}

	/**
	 * This method switch to Patient portal
	 */
	public void switchToPatientPortal(String userName, String password) {
		test.openUrl(getYamlValue("patient.baseUrl"));
		test.loginPage.loginUser(userName, password);
		test.headerPage.verifyUserIsOnHomepage(getYamlValue("patient.headerText"));
	}

	/**
	 * This method switch to the physician portal
	 * 
	 * @param userName
	 * @param password
	 */
	public void switchToPhysicianPortal(String userName, String password) {
		refreshPage();
		test.openUrl(getYamlValue("physician.baseUrl"));
		test.loginPage.loginUser(userName, password);
		test.headerPage.verifyUserIsOnHomepage(getYamlValue("physician.headerText"));
	}

	/**
	 * This method switch to the pharmacy portal
	 * 
	 * @param userName
	 * @param password
	 */
	public void switchToPharmacyPortal(String userName, String password) {
		refreshPage();
		test.openUrl(getYamlValue("pharmacy.baseUrl"));
		test.loginPage.loginUser(userName, password);
		test.headerPage.verifyUserIsOnHomepage(getYamlValue("pharmacy.headerText"));
	}

	/**
	 * This method switch to the admin portal
	 * 
	 * @param userName
	 * @param password
	 */
	public void switchToAdminPortal(String userName, String password) {
		test.openUrl(getYamlValue("admin.baseUrl"));
		test.loginPage.loginUser(userName, password);
		test.headerPage.verifyUserIsOnAdminHomepage();
	}

	/**
	 * This method request prior authorization and Send To Plan
	 */
	public void requestPriorAuthAndSendToPlan() {
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthForPatientPage.userSelectForm();
		test.priorAuthForPatientPage.clickOnSendToPlan();
		test.priorAuthForPatientPage.drawSignToSubmitAndReviewPres();
	}

	/**
	 * This method login on django admin portal and delete the patient
	 */
	public void loginAdminAndDeletePatient(String patientName) {
		test.openUrl(getYamlValue("djangoadmin.baseUrl"));
		test.djangoAdminPage.loginAndSelectActors(getYamlValue("djangoadmin.emailId"),
				getYamlValue("djangoadmin.password"));
		test.djangoAdminPage.deleteUserAndVerify(patientName, getYamlValue("djangoadmin.actionValue"),
				getYamlValue("djangoadmin.successMessage"));
	}

	/**
	 * This method deletes the doctor/nurse from django admin portal
	 */
	public void deleteDoctor(String firstName) {
		test.djangoAdminPage.deleteUserAndVerify(firstName, getYamlValue("djangoadmin.actionValue"),
				getYamlValue("djangoadmin.successMessage"));
	}

	/**
	 * This method sign the health share at patient end
	 */
	public void patientSignHealthShare(String medicationName) {
		test.homePage.verifyShareAuthMessage(medicationName);
		test.homePage.signHealthShare(medicationName);
		test.documentPage.enterSignatureAndSubmit();
		test.documentPage.verifySignIsSubmitted(getYamlValue("patient.signSuccessMsg"));
		test.headerPage.clickOnLogOutAtPatientEnd();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("patient.pageTitle"));
	}

	/**
	 * This method verifies REMS sign off is displayed
	 */
	public void verifyREMSSignOffIsDisplayed(String medicationName, String gender) {
		medications = new ArrayList<String>(Arrays.asList("Adempas", "Letairis", "Opsumit", "Orenitram", "Tracleer"));
		if (medications.contains(medicationName)) {
			test.homePage.verifyShareAuthMessage(medicationName);
		}
	}

	/**
	 * This method verify Rx status and complete the flow
	 * 
	 * @param patientFirstName
	 */
	public void physicianVerifyRxStatusAndComplete(String patientLastName) {
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.headerPage.verifyUserIsOnCorrectPage("Rx Status");
		test.rxStatusPage.searchAndSelectPatient(patientLastName);
		test.rxStatusPage.clickOnViewRxDetails();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.verifyRxStatus(getYamlValue("physician.rxStatus.patientSigned"));
		test.presDetailsPage.verifyAndClickOnComplete();
		test.presDetailsPage.clickOnContinue();
		test.presDetailsPage.verifyRxStatus(getYamlValue("physician.rxStatus.submitted"));
	}

	/**
	 * This method perform prescribe medication from physician not having submit
	 * priviliges
	 * 
	 * @param medication
	 * @param diagnosis
	 */
	public void prescribePatientAndAssignPhysician(String medication, String physicianName, String diagnosis,
			String hub1, String gender, String otherDetails) {
		test.headerPage.verifyHeaderText("Choose a medication");
		test.chooseMedicationPage.selectMedication(medication);
		test.chooseMedicationPage.clickOnSkipStep();
		test.patientDetailPage.selectPrescribingProvider(physicianName);
		presPatAfterSigningPatCon(diagnosis, hub1, gender, otherDetails);
	}

	/**
	 * This method perform prescription flow after signing patient consent
	 * 
	 * @param diagnosis
	 * @param hub1
	 * @param gender
	 * @param otherDetails
	 */
	public void presPatAfterSigningPatCon(String diagnosis, String hub1, String gender, String otherDetails) {
		test.patientDetailPage.enterPatientDetails(getYamlValue("physician.medicalHistory.knownAllergy1"), hub1,
				gender);
		test.patientDetailPage.clickOnDiagnosisStep();
		test.patientDetailPage.clickOnPatientProfileModal();
		test.diagnosisPage.enterDiagnosisDetailsAndClickDosage(diagnosis,
				getYamlValue("physician.indication1.diagnosis.newDiagnosis"), otherDetails);
	}

	/**
	 * This method search patient and select indication
	 * 
	 * @param patientName
	 */
	public void searchPatientAndSelectIndication(String patientName, String indication) {
		test.leftnavigationPage.clickOnPatients();
		test.patientPage.searchPatient(patientName);
		test.patientPage.clickOnSearchIcon();
		test.patientPage.selectPatientByName(patientName);
		test.patientPage.clickPrescribeOnPatientModal();
		test.indicationPage.selectIndication(indication);
	}

	/**
	 * This method adds a new practice or pharmacy
	 * 
	 * @param state
	 * @param fName
	 * @param password
	 */
	public String addNewPracOrPhar(String type) {
		if (type.equalsIgnoreCase("Practice")) {
			test.homePage.clickOnAddNewPrac();
			test.pracPage.verifyUserIsNewPracPage();
			String pracName = test.pracPage.enterPracName();
			return pracName;
		} else {
			test.homePage.clickOnAddNewPharmacy();
			test.pharPage.verifyUserIsOnAddNewPharmacy();
			String pharName = test.pharPage.enterPharmacyName();
			return pharName;
		}
	}

	/**
	 * This method enters details for creating a new practice or pharamcy
	 * 
	 * @param state
	 * @param fName
	 * @param password
	 */
	public void enterDetails(String state, String fName, String password) {
		test.pracPage.enterAddress();
		test.pracPage.enterCity();
		test.pracPage.selectState(state);
		test.pracPage.enterZip();
		test.pracPage.enterAdminEmail();
		test.pracPage.enterFirstName(fName);
		test.pracPage.enterLastName();
		test.pracPage.enterPassword(password);
		test.pracPage.enterConfirmPassword(password);
		test.pracPage.clickOnSavePracProfile();
	}

	/**
	 * This method fetches values from HTML Form and Rx Form
	 * 
	 * @param medicineName
	 * @param formTitle
	 */
	public void fetchListAndCompare(String medicineName, String formTitle) {
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.getInputElements();
		test.priorAuthorizationPage.enterSign();
		test.priorAuthorizationPage.clickOnPrint();
		test.priorAuthorizationPage.verifyFormTitle(formTitle);
		test.priorAuthorizationPage.getInputElementsForm();
		test.priorAuthorizationPage.compareLists(medicineName);
	}

	/**
	 * This method creates PA, submit PA to PARx and verifies PA status on
	 * prescription details page under quick info
	 * 
	 * @param PASubmissionMsg
	 * @param PAStatus
	 */
	public void createPASubmitAndVerifyPAStatus(String medicineName, String PASubmissionMsg, String PAStatus,
			String buttonName) {
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.drawSignAndSubmit(buttonName, PASubmissionMsg);
		test.priorAuthorizationPage.clickOnViewPresDetails(medicineName);
		test.presDetailsPage.verifyPAStatus(PAStatus);
	}

	/**
	 * Capture patient consent for which patient consent is required for PA
	 * medication and then prescribe, else simply prescribe patient
	 * 
	 * @param medication
	 * @param diagnosis
	 * @param hub1
	 * @param gender
	 * @param lastName
	 * @param otherDetails
	 * @return
	 */

	public boolean capturePatConAndPrescribePatient(String indication, String medication, String diagnosis, String hub1,
			String gender, String lastName, String otherDetails) {
		boolean remsValue;
		ArrayList<String> medList = new ArrayList<String>(Arrays.asList("Adempas", "Opsumit", "Ampyra", "Aubagio",
				"Gilenya", "Tecfidera", "Avonex", "Copaxone", "Glatopa", "Tysabri"));
		if (medList.contains(medication)) {
			test.patientPage.clickOnPatientConsent();
			remsValue = test.patientCommonWorkflow.clickPatConsentAndSelectIndication_Medication(indication,
					medication);
			test.patientCommonWorkflow.verifyUserIsOnPatientConsentPageToAuthorize();
			test.authorizationPage.enterSignAndSave(gender);
			test.rxStatusPage.searchAndSelectPatient(lastName);
			test.rxStatusPage.clickOnCompleteRx();
			test.patientCommonWorkflow.presPatAfterSigningPatCon(diagnosis, hub1, gender, otherDetails);
			return remsValue;
		} else {
			test.patientCommonWorkflow.clickPresOnPatientModalAndSelectIndication(indication);
			remsValue = test.patientCommonWorkflow.prescribePatient(medication, diagnosis, hub1, gender, lastName,
					otherDetails);
		}
		return remsValue;
	}

	/**
	 * This method submit PA in ZappRx workflow mode
	 */
	public void submitPAInZapprxModeAfterSave() {
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.clickOnMarkPASubmitted();
		test.presDetailsPage.selectSubmittedDate();
		test.presDetailsPage.clickOnPASubmit();
		test.presDetailsPage.clickContinueOnConfirmationBox();
	}

	/**
	 * This method saves practice profile and logout from admin protal
	 */
	public void savePracProfileAndLogout() {
		test.pracPage.clickOnSavePracProfile();
		test.pracPage.clickOnContinue();
		test.headerPage.logOutFromAdmin();
	}

	/*********************** Comparatortor *********************************/

	public String storePDFFromTemp(String dirPath, String formName, String medcineName) {
		return test.pdfPage.getStoredPDF(dirPath, formName, medcineName);
	}

	public String downloadAndComparePDF(String dirPath, String medicineName) {
		medicineName = medicineName.replaceAll("\\s", "");
		String fileName = null, formName = null;
		int formsSize = test.presDetailsPage.getFormsSize();
		for (int count = 0; count < formsSize; count++) {
			wait.resetImplicitTimeout(1);
			try {
				formName = test.presDetailsPage.userSelectsForm(count);
			} catch (StaleElementReferenceException e) {
				formName = test.presDetailsPage.userSelectsForm(count);
			}
			wait.resetImplicitTimeout(wait.timeout);
			fileName = storePDFFromTemp(dirPath + "pdf/", formName, medicineName);
			ReadFromPDF.deleteFile(dirPath + "downloadPDF/");
			test.pdfPage.comparePDF(dirPath + "goodPDF/" + fileName, dirPath + "pdf/" + fileName);
			if (count != (formsSize - 1)) {
				test.presDetailsPage.clickOnSend();
			}
		}
		return fileName;
	}
}
