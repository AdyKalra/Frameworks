package com.zapprx.testing.end2endtests.regression.PARx;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as admin and select 'Manual' PA Workflow mode
 * under 'ZappRx Practice'. Step 2: Login as physician to register new patient
 * and complete profile. Step 3: Prescribe patient 'Remodulin IV' medication
 * under PAH indication, authorize it and then access PA and submit Prior
 * Authorization to PARx filling answers for 'Medication Questions'. Step 4: Now
 * verify that answers under 'Medication Questions' are saved correctly. Step 5:
 * Again prescribe patient 'Remodulin SC' medication under PAH indication,
 * authorize it and then access PA and submit Prior Authorization to PARx
 * filling answers for 'Medication Questions'. Step 6: Now verify that answers
 * under 'Medication Questions' are saved correctly. Step 7: Switch to admin
 * portal and select 'ZappRx' PA Workflow mode under 'ZappRx Practice'.
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ292Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ292Test(String baseUrl) {
		super("admin.baseUrl");
	}

	@Test
	public void Step01_loginAdminSelectManualPAWorkflowMode() {
		test.patientCommonWorkflow.verifyAdminIsAbleToLogin(getYamlValue("admin.emailId1"),
				getYamlValue("admin.password1"));
		test.pracPage.selectPractice(getYamlValue("admin.practice.practice3"));
		test.pracPage.clickOnEditPractice();
		test.pracPage.selectPAWorkflowMode(getYamlValue("admin.workflowMode.mode1"));
		test.patientCommonWorkflow.savePracProfileAndLogout();
	}

	@Test
	public void Step02_loginPhysicianRegisterPatAndCompleteProfile() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
	}

	@Test
	public void Step03_completeRxAnswerMedQuesOnPAAndSubmitPA() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine13.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine13.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine13.name"), getYamlValue("physician.passwordPA"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.verifyPAFormIsDisplayed(getYamlValue("physician.PAFormTitle"));
		test.priorAuthorizationPage.enterGeneralOrMedicationQuestionAns(
				getYamlValue("physician.indication1.medicines.medicine13.medicationQuestions.que1"),
				getYamlValue("physician.indication1.medicines.medicine13.medicationQuestions.ans1"),
				getYamlValue("physician.medicationQuestions.medicationRequest.req1"),
				getYamlValue("physician.indication1.medicines.medicine13.medicationQuestions.que3"),
				getYamlValue("physician.indication1.medicines.medicine13.medicationQuestions.ans3"),
				getYamlValue("physician.indication1.medicines.medicine13.medicationQuestions.que4"),
				getYamlValue("physician.indication1.medicines.medicine13.medicationQuestions.ans4"),
				getYamlValue("physician.indication1.medicines.medicine13.medicationQuestions.que5"),
				getYamlValue("physician.indication1.medicines.medicine13.medicationQuestions.ans5"));
		test.priorAuthorizationPage.drawSignAndSubmit(getYamlValue("physician.priorAuthButton.submit"),
				getYamlValue("physician.PASubmissionMsg"));
	}

	@Test
	public void Step04_verifyAnswersOfMedQuesOnPAForm() {
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus1"));
		test.priorAuthorizationPage.verifySavedMedicationQuestionAns(
				getYamlValue("physician.indication1.medicines.medicine13.medicationQuestions.que1"),
				getYamlValue("physician.indication1.medicines.medicine13.medicationQuestions.ans1"),
				getYamlValue("physician.medicationQuestions.medicationRequest.req1"),
				getYamlValue("physician.indication1.medicines.medicine13.medicationQuestions.que3"),
				getYamlValue("physician.indication1.medicines.medicine13.medicationQuestions.ans3"),
				getYamlValue("physician.indication1.medicines.medicine13.medicationQuestions.que4"),
				getYamlValue("physician.indication1.medicines.medicine13.medicationQuestions.ans4"),
				getYamlValue("physician.indication1.medicines.medicine13.medicationQuestions.que5"),
				getYamlValue("physician.indication1.medicines.medicine13.medicationQuestions.ans5"));
	}

	@Test
	public void Step05_againPrescribePatAnswerMedQuesOnPAAndSubmitPA() {
		test.patientCommonWorkflow.clickPatientsSearchAndSelect(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow
				.clickPresOnPatientModalAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine14.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine14.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine14.name"), getYamlValue("physician.passwordPA"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.verifyPAFormIsDisplayed(getYamlValue("physician.PAFormTitle"));
		test.priorAuthorizationPage.enterGeneralOrMedicationQuestionAns(
				getYamlValue("physician.indication1.medicines.medicine14.medicationQuestions.que1"),
				getYamlValue("physician.indication1.medicines.medicine14.medicationQuestions.ans1"),
				getYamlValue("physician.medicationQuestions.medicationRequest.req1"),
				getYamlValue("physician.indication1.medicines.medicine14.medicationQuestions.que3"),
				getYamlValue("physician.indication1.medicines.medicine14.medicationQuestions.ans3"),
				getYamlValue("physician.indication1.medicines.medicine14.medicationQuestions.que4"),
				getYamlValue("physician.indication1.medicines.medicine14.medicationQuestions.ans4"),
				getYamlValue("physician.indication1.medicines.medicine14.medicationQuestions.que5"),
				getYamlValue("physician.indication1.medicines.medicine14.medicationQuestions.ans5"));
		test.priorAuthorizationPage.drawSignAndSubmit(getYamlValue("physician.priorAuthButton.submit"),
				getYamlValue("physician.PASubmissionMsg"));
	}

	@Test
	public void Step06_verifyAnswersOfMedQuesOnPAForm() {
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus1"));
		test.priorAuthorizationPage.verifySavedMedicationQuestionAns(
				getYamlValue("physician.indication1.medicines.medicine14.medicationQuestions.que1"),
				getYamlValue("physician.indication1.medicines.medicine14.medicationQuestions.ans1"),
				getYamlValue("physician.medicationQuestions.medicationRequest.req1"),
				getYamlValue("physician.indication1.medicines.medicine14.medicationQuestions.que3"),
				getYamlValue("physician.indication1.medicines.medicine14.medicationQuestions.ans3"),
				getYamlValue("physician.indication1.medicines.medicine14.medicationQuestions.que4"),
				getYamlValue("physician.indication1.medicines.medicine14.medicationQuestions.ans4"),
				getYamlValue("physician.indication1.medicines.medicine14.medicationQuestions.que5"),
				getYamlValue("physician.indication1.medicines.medicine14.medicationQuestions.ans5"));
	}

	@Test
	public void Step07_switchToAdminSelectZappRxPAWorkflowMode() {
		test.patientCommonWorkflow.switchToAdminPortal(getYamlValue("admin.emailId1"), getYamlValue("admin.password1"));
		test.pracPage.selectPractice(getYamlValue("admin.practice.practice3"));
		test.pracPage.clickOnEditPractice();
		test.pracPage.selectPAWorkflowMode(getYamlValue("admin.workflowMode.mode2"));
		test.patientCommonWorkflow.savePracProfileAndLogout();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
