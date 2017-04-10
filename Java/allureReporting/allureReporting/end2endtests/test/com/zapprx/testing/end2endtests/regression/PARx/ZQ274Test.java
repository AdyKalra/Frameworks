package com.zapprx.testing.end2endtests.regression.PARx;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login admin and select PA Workflow mode as 'Manual'
 * for 'Neuromuscular Clinic'. Step 2: Switch to physician portal, login as
 * physician and register new patient. Step 3: Prescribe patient, on diagnosis
 * page add additional medication and enter special character in 'Reason Ended'
 * field, authorize it and click on 'Prior Authorization' and verify PA form
 * title.
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ274Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ274Test(String baseUrl) {
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
	public void Step03_presPatAddAdditionalMedAndVerifyPAForm() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication3.name"));
		boolean remsValue = test.patientCommonWorkflow.chooseMedication(
				getYamlValue("physician.indication3.medicines.medicine6.name"), patientLastNameDOBAndGender[0]);
		test.patientDetailPage.enterPatientDetails(getYamlValue("physician.medicalHistory.knownAllergy1"),
				getYamlValue("physician.indication3.medicines.medicine6.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2]);
		test.patientDetailPage.clickOnDiagnosisStep();
		test.patientDetailPage.clickOnPatientProfileModal();
		test.diagnosisPage.enterDiagnosisDetails(getYamlValue("physician.indication3.diagnosis.diagnosis1"),
				getYamlValue("physician.indication3.diagnosis.newDiagnosis"),
				getYamlValue("physician.indication3.otherDetails"));
		test.diagnosisPage.addAdditionalMedication(getYamlValue("physician.indication3.medicines.medicine3.name"),
				getYamlValue("physician.specialCharacter"));
		test.diagnosisPage.clickOnDosage();
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication3.medicines.medicine6.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication3.medicines.medicine6.name"), getYamlValue("physician.passwordPA"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.verifyPAFormIsDisplayed(getYamlValue("physician.PAFormTitle"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
