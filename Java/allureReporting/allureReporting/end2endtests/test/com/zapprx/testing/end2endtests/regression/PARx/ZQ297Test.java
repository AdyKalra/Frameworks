package com.zapprx.testing.end2endtests.regression.PARx;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login admin and select PA Workflow mode as 'Manual'
 * for 'Neuromuscular Clinic'. Step 2: Switch to physician portal, login as
 * physician, register a new patient entering value for 'EDSS Score' under
 * General Questions on clinical profile section and complete profile. Step 3:
 * Now prescribe MS medication to newly registered patient and authorize and
 * verify value for 'EDSS Score' under General Questions on PA form.
 * 
 * @author QAIT\priyankasingh
 */
public class ZQ297Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ297Test(String baseUrl) {
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
	public void Step02_loginPhysicianRegPatEnteringEDSSScore() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailPA"),
				getYamlValue("physician.passwordPA"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.enterGeneralInfoAndInsuranceInfo(
				getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
		test.headerPage.verifyUserIsOnCorrectPage("Clinical Information");
		test.patientRegistrationPage.enterMedicalHistory(getYamlValue("physician.medicalHistory.knownAllergy1"));
		test.patientRegistrationPage.enterClinicalProfileDetails(getYamlValue("physician.indication3.name"),
				getYamlValue("physician.indication3.diagnosis.diagnosis1"),
				getYamlValue("physician.indication3.otherDetails"));
		test.patientRegistrationPage.expandGeneralQuestions();
		test.patientRegistrationPage.enterValueForEDSSScore(getYamlValue("physician.edssScore"));
		test.patientRegistrationPage.clickOnCompleteRegistration();
	}

	@Test
	public void Step03_prescribePatientAndVerifyEDSSScoreOnPAForm() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication3.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication3.medicines.medicine2.name"),
				getYamlValue("physician.indication3.diagnosis.diagnosis1"),
				getYamlValue("physician.indication3.medicines.medicine2.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0],
				getYamlValue("physician.indication3.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication3.medicines.medicine2.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication3.medicines.medicine2.name"), getYamlValue("physician.passwordPA"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.verifyGeneralQuestions();
		test.priorAuthorizationPage.verifyValueOfEDSSScore(getYamlValue("physician.edssScore"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
