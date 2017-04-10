package com.zapprx.testing.end2endtests.regression.PARx;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step1: Login admin and select PA Workflow mode as 'Manual'
 * for 'ZappRx Practice'. Step 2: Switch to physician portal, login as physician
 * and register new patient. Step 3: Prescribe patient 'Letairis' medication and
 * authorize it. Step 4: Initiate PA and select 'Yes' under Right-Heart
 * Catheterization and enter decimal values in Vascular resistance, Pulmonary
 * Artery Mean, Pulmonary capillary wedge mean, Cardiac output fields and submit
 * PA to PARx. Step 5: Now verify saved values of Right-Heart Catheterization
 * fields.
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ306Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ306Test(String baseUrl) {
		super("admin.baseUrl");
	}

	@Test
	public void Step01_loginAdminSelectManualWorkflowMode() {
		test.patientCommonWorkflow.verifyAdminIsAbleToLogin(getYamlValue("admin.emailId1"),
				getYamlValue("admin.password1"));
		test.pracPage.selectPractice(getYamlValue("admin.practice.practice3"));
		test.pracPage.clickOnEditPractice();
		test.pracPage.selectPAWorkflowMode(getYamlValue("admin.workflowMode.mode1"));
		test.patientCommonWorkflow.savePracProfileAndLogout();
	}

	@Test
	public void Step02_loginPhysicianRegPatAndCompleteProfile() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
	}

	@Test
	public void Step03_completeRxSubmitPAToPARxAndVerifyMarkPAUrgent() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine3.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine3.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine3.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
	}

	@Test
	public void Step04_enterValuesInRightHeartCatheterizationFields() {
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.enterGeneralOrMedicationQuestionAns(
				getYamlValue("physician.indication1.medicines.medicine3.generalQuestions.que1"),
				getYamlValue("physician.indication1.medicines.medicine3.generalQuestions.ans1"));
		test.priorAuthorizationPage.selectVascularResistanceUnit(
				getYamlValue("physician.indication1.medicines.medicine3.vascularResistance.que1"),
				getYamlValue("physician.indication1.medicines.medicine3.vascularResistance.ans1"));
		test.priorAuthorizationPage.enterDecimalValuesForRightHeartCatheterizationFields(
				getYamlValue("physician.indication1.medicines.medicine3.vascularResistance.que2"),
				getYamlValue("physician.indication1.medicines.medicine3.vascularResistance.ans2"),
				getYamlValue("physician.indication1.medicines.medicine3.pulmonaryArteryMean.que1"),
				getYamlValue("physician.indication1.medicines.medicine3.pulmonaryArteryMean.ans1"),
				getYamlValue("physician.indication1.medicines.medicine3.pulmonaryArteryMean.que2"),
				getYamlValue("physician.indication1.medicines.medicine3.pulmonaryArteryMean.ans2"),
				getYamlValue("physician.indication1.medicines.medicine3.pulmonaryCapillaryWedgeMean.que1"),
				getYamlValue("physician.indication1.medicines.medicine3.pulmonaryCapillaryWedgeMean.ans1"),
				getYamlValue("physician.indication1.medicines.medicine3.pulmonaryCapillaryWedgeMean.que2"),
				getYamlValue("physician.indication1.medicines.medicine3.pulmonaryCapillaryWedgeMean.ans2"),
				getYamlValue("physician.indication1.medicines.medicine3.cardiacOutput.que1"),
				getYamlValue("physician.indication1.medicines.medicine3.cardiacOutput.ans1"),
				getYamlValue("physician.indication1.medicines.medicine3.cardiacOutput.que2"),
				getYamlValue("physician.indication1.medicines.medicine3.cardiacOutput.ans2"));
		test.priorAuthorizationPage.drawSignAndSubmit(getYamlValue("physician.priorAuthButton.submit"),
				getYamlValue("physician.PASubmissionMsg"));
	}

	@Test
	public void Step05_verifySavedRightHeartCatheterizationFields() {
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus1"));
		test.priorAuthorizationPage.verifySavedValuesOfRightHeartCatheterizationFields(
				getYamlValue("physician.indication1.medicines.medicine3.vascularResistance.que2"),
				getYamlValue("physician.indication1.medicines.medicine3.vascularResistance.ans2"),
				getYamlValue("physician.indication1.medicines.medicine3.pulmonaryArteryMean.que1"),
				getYamlValue("physician.indication1.medicines.medicine3.pulmonaryArteryMean.ans1"),
				getYamlValue("physician.indication1.medicines.medicine3.pulmonaryArteryMean.que2"),
				getYamlValue("physician.indication1.medicines.medicine3.pulmonaryArteryMean.ans2"),
				getYamlValue("physician.indication1.medicines.medicine3.pulmonaryCapillaryWedgeMean.que1"),
				getYamlValue("physician.indication1.medicines.medicine3.pulmonaryCapillaryWedgeMean.ans1"),
				getYamlValue("physician.indication1.medicines.medicine3.pulmonaryCapillaryWedgeMean.que2"),
				getYamlValue("physician.indication1.medicines.medicine3.pulmonaryCapillaryWedgeMean.ans2"),
				getYamlValue("physician.indication1.medicines.medicine3.cardiacOutput.que1"),
				getYamlValue("physician.indication1.medicines.medicine3.cardiacOutput.ans1"),
				getYamlValue("physician.indication1.medicines.medicine3.cardiacOutput.que2"),
				getYamlValue("physician.indication1.medicines.medicine3.cardiacOutput.ans2"));
	}

	@Test
	public void Step06_loginAdminSelectZapprxWorkflowMode() {
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
