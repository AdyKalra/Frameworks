package com.zapprx.testing.end2endtests.regression.PARx;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login admin and select PA Workflow mode as 'Manual'
 * for 'Neuromuscular Clinic'. Step 2: Switch to physician portal, login as
 * physician and register new patient. Step 3: Complete Rx, submit Prior
 * Authorization entering values for general and medication questions under
 * clinical section. Step 4: Now switch to pharmacy portal access the PA form
 * and verify Questions and Corresponding values under clinical section on PA
 * form is same as that of values on PA review form
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ265Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;
	List<String> clinicalQuestion, clinicalValue = null;

	private ZQ265Test(String baseUrl) {
		super("admin.baseUrl");
	}

	@Test
	public void Step01_loginAdminSelectManualWorkflowMode() {
		test.patientCommonWorkflow.verifyAdminIsAbleToLogin(getYamlValue("admin.emailId1"),
				getYamlValue("admin.password1"));
		test.pracPage.selectPractice(getYamlValue("admin.practice.practice2"));
		test.pracPage.clickOnEditPractice();
		test.pracPage.selectPAWorkflowMode(getYamlValue("admin.workflowMode.mode1"));
		test.patientCommonWorkflow.savePracProfileAndLogout();
	}

	@Test
	public void Step02_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailPA"),
				getYamlValue("physician.passwordPA"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
	}

	@Test
	public void Step03_completeRxSubmitPAEnteringValuesForClinicalQuestion() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication3.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication3.medicines.medicine6.name"),
				getYamlValue("physician.indication3.diagnosis.diagnosis1"),
				getYamlValue("physician.indication3.medicines.medicine6.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0],
				getYamlValue("physician.indication3.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication3.medicines.medicine6.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication3.medicines.medicine6.name"), getYamlValue("physician.passwordPA"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.enterGeneralQuestionValue(getYamlValue("physician.edssScore"));
		test.priorAuthorizationPage.enterGeneralOrMedicationQuestionAns(getYamlValue("physician.medicationQuestions.que1"),
				getYamlValue("physician.medicationQuestions.ans1"), getYamlValue("physician.medicationQuestions.que2"),
				getYamlValue("physician.medicationQuestions.ans2"),
				getYamlValue("physician.medicationQuestions.medicationRequest.req1"),
				getYamlValue("physician.medicationQuestions.que4"), getYamlValue("physician.medicationQuestions.ans4"),
				getYamlValue("physician.medicationQuestions.que5"), getYamlValue("physician.medicationQuestions.ans5"),
				getYamlValue("physician.medicationQuestions.que6"), getYamlValue("physician.medicationQuestions.ans6"),
				getYamlValue("physician.medicationQuestions.que7"), getYamlValue("physician.medicationQuestions.ans7"));
		test.priorAuthorizationPage.drawSignAndSubmit(getYamlValue("physician.priorAuthButton.submit"),
				getYamlValue("physician.PASubmissionMsg"));
		test.priorAuthorizationPage
				.clickOnViewPresDetails(getYamlValue("physician.indication3.medicines.medicine6.name"));
		test.presDetailsPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus1"));
	}

	@Test
	public void Step04_onPharmacyPortalVerifyClinicalSectionOnPAandReviewPA() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailPA"),
				getYamlValue("pharmacy.password"));
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.verifyPAStatusOnPriorAuthPage(getYamlValue("pharmacy.paStatus.PAStatus1"),
				patientLastNameDOBAndGender[0]);
		test.priorAuthPage.clickOnViewPADetails();
		clinicalQuestion = test.priorAuthPage.getTextOfQuestionsOnPAForm();
		clinicalValue = test.priorAuthPage.getAnswersOfQuestionsOnPAForm();
		test.priorAuthPage.clickOnReview();
		test.priorAuthPage.verifyClinicalSectionOnPAReview(clinicalQuestion, clinicalValue);
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
