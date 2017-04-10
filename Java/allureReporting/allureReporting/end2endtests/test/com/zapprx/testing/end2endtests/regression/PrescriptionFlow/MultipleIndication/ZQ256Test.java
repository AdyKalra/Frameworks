package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.MultipleIndication;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated:
 * 
 * Step 1: Login as physician with credentials doctor@neuromuscular.com / zapprx
 * and register a new patient. Step 2: Create Rx for Techfidera medication by
 * capturing patient consent at Patient Detail step and verify that patient
 * consent can't be saved only signing HIPAA and also verify Rx Status On
 * Prescription Details page. Step 3: Login as physician with credentials
 * doctor@pulmonology.com / zapprx and register a female patient. Step 4: Create
 * Rx for Adempas medication by capturing patient consent at Patient Detail step
 * and verify that patient consent can't be saved only signing Share
 * Authorization and also verify Rx Status On Prescription Details page.
 * 
 * @author vivekdua
 *
 */
public class ZQ256Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;
	String[] patientLastNameAndDOB = null;
	String gender = "Female";

	private ZQ256Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_loginWithMSPhysicianAndRegisterPat() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailPA"),
				getYamlValue("physician.passwordPA"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientCommonWorkflow.savePatientLastname(patientLastNameDOBAndGender[0]);
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
	}

	@Test
	public void Step02_completeRxForTechfideraCapturingPatConAtPatientDetailsStep() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication3.name"));
		boolean remsValue = test.patientCommonWorkflow.chooseMedication(
				getYamlValue("physician.indication3.medicines.medicine6.name"), patientLastNameDOBAndGender[0]);
		test.patientDetailPage.enterPatientDetails(getYamlValue("physician.medicalHistory.knownAllergy1"),
				getYamlValue("physician.indication3.medicines.medicine6.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2]);
		test.patientDetailPage.captPatConAndVerifyErrorMsgOnSkipSigForTechfidera(
				getYamlValue("physician.patientConsentAuth.patService"), getYamlValue("physician.patConErrorMsg"),
				getYamlValue("physician.patConSuccessMsgHipaa"), getYamlValue("physician.firstName"),
				patientLastNameDOBAndGender[0]);
		test.patientDetailPage.clickOnDiagnosisStep();
		test.patientDetailPage.clickOnPatientProfileModal();
		test.diagnosisPage.enterDiagnosisDetailsAndClickDosage(
				getYamlValue("physician.indication3.diagnosis.diagnosis1"),
				getYamlValue("physician.indication3.diagnosis.newDiagnosis"),
				getYamlValue("physician.indication3.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication3.medicines.medicine6.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication3.medicines.medicine6.name"), getYamlValue("physician.passwordPA"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.presDetailsPage.verifyRxStatus(getYamlValue("physician.rxStatus.submitted"));
		test.headerPage.logOut();
	}

	@Test
	public void Step03_loginWithPAHPhysicianAndRegisterFemalePat() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameAndDOB = test.patientCommonWorkflow.registerPatientWithGivenGender(gender);
		test.patientCommonWorkflow.savePatientLastname(patientLastNameAndDOB[0]);
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
	}

	@Test
	public void Step04_completeRxForAdempasCapturingPatConAtPatientDetailsStep() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.chooseMedication(
				getYamlValue("physician.indication1.medicines.medicine2.name"), patientLastNameAndDOB[0]);
		test.patientDetailPage.enterPatientDetails(getYamlValue("physician.medicalHistory.knownAllergy1"),
				getYamlValue("physician.indication1.medicines.medicine2.pharmacy.pharmacy1"), gender);
		test.patientDetailPage.captPatConAndVerifyErrorMsgOnSkipSigForAdempas(
				getYamlValue("physician.patientConsentAuth.share"), getYamlValue("physician.patConErrorMsg"),
				getYamlValue("physician.patConSuccessMsgRems"), getYamlValue("physician.firstName"),
				patientLastNameAndDOB[0]);
		test.patientDetailPage.clickOnDiagnosisStep();
		test.patientDetailPage.clickOnPatientProfileModal();
		test.diagnosisPage.enterDiagnosisDetailsAndClickDosage(
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.diagnosis.newDiagnosis"), getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.patientCommonWorkflow.authorizePres(gender, remsValue,
				getYamlValue("physician.indication1.medicines.medicine2.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.presDetailsPage.verifyRxStatus(getYamlValue("physician.rxStatus.submitted"));
		test.headerPage.logOut();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.deletePatients();
	}
}