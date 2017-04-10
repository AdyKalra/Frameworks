package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated:
 * 
 * Step 1: Login as physician. Step 2: Register a new patient and complete
 * patient's Rx capturing patient consent on 'Step 1: Patient' and verify that
 * user is able to authorize. Step 3: Again register a new patient and complete
 * patient's Rx capturing patient consent on 'Step 2: Diagnosis' and verify that
 * user is able to authorize. Step 4: Again register a new patient and complete
 * patient's Rx capturing patient consent on 'Step 3: Dosage' and verify that
 * user is able to authorize. Step 5: Again register a new patient and complete
 * patient's Rx capturing patient consent on 'Step 4: Authorize' and verify that
 * user is able to authorize.
 * 
 * @author vivekdua
 *
 */
public class ZQ234Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;
	boolean remsValue;

	private ZQ234Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_loginPhysician() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
	}

	@Test
	public void Step02_completeRxCapturingPatientConsentAtPatientStep() {
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientCommonWorkflow.savePatientLastname(patientLastNameDOBAndGender[0]);
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		remsValue = test.patientCommonWorkflow.chooseMedication(
				getYamlValue("physician.indication1.medicines.medicine2.name"), patientLastNameDOBAndGender[0]);
		test.patientDetailPage.enterPatientDetails(getYamlValue("physician.medicalHistory.knownAllergy1"),
				getYamlValue("physician.indication1.medicines.medicine2.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2]);
		test.patientDetailPage.clickOnCaptPatientConsent();
		test.patientDetailPage.capturePatientConsent(getYamlValue("physician.patientConsentAuth.share"),
				patientLastNameDOBAndGender[2], getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.patientDetailPage.clickOnDiagnosisStep();
		test.patientDetailPage.clickOnPatientProfileModal();
		test.diagnosisPage.enterDiagnosisDetailsAndClickDosage(
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.diagnosis.newDiagnosis"), getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication3.medicines.medicine1.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
	}

	@Test
	public void Step03_completeRxCapturingPatientConsentAtDiagnosisStep() {
		test.leftnavigationPage.clickOnHome();
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientCommonWorkflow.savePatientLastname(patientLastNameDOBAndGender[0]);
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		remsValue = test.patientCommonWorkflow.chooseMedication(
				getYamlValue("physician.indication1.medicines.medicine2.name"), patientLastNameDOBAndGender[0]);
		test.patientDetailPage.enterPatientDetails(getYamlValue("physician.medicalHistory.knownAllergy1"),
				getYamlValue("physician.indication1.medicines.medicine2.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2]);
		test.patientDetailPage.clickOnDiagnosisStep();
		test.patientDetailPage.clickOnPatientProfileModal();
		test.diagnosisPage.enterDiagnosisDetails(getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.diagnosis.newDiagnosis"), getYamlValue("physician.otherDetails"));
		test.diagnosisPage.clickOnCaptPatientConsent();
		test.diagnosisPage.capturePatientConsent(patientLastNameDOBAndGender[2],
				getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.diagnosisPage.clickOnDosage();
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication3.medicines.medicine1.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
	}

	@Test
	public void Step04_completeRxCapturingPatientConsentAtDosageStep() {
		test.leftnavigationPage.clickOnHome();
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientCommonWorkflow.savePatientLastname(patientLastNameDOBAndGender[0]);
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine2.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.medicines.medicine2.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.commonDosagePage.clickOnCaptPatientConsent();
		test.commonDosagePage.capturePatientConsent(patientLastNameDOBAndGender[2],
				getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication3.medicines.medicine1.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
	}

	@Test
	public void Step05_completeRxCapturingPatientConsentAtAuthorizeStep() {
		test.leftnavigationPage.clickOnHome();
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientCommonWorkflow.savePatientLastname(patientLastNameDOBAndGender[0]);
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine2.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.medicines.medicine2.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.commonDosagePage.clickOnAuthorize();
		test.authorizationPage.verifyUserIsOnAuthorizePage();
		test.authorizationPage.clickOnCaptPatientConsent();
		test.authorizationPage.capturePatientConsent(patientLastNameDOBAndGender[2],
				getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.authorizationPage.enterPrescriberAuth(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.authorizationPage.enterPhysicianSavedSig(getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.deletePatients();
	}
}