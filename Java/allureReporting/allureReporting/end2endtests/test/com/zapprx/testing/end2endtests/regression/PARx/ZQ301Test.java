package com.zapprx.testing.end2endtests.regression.PARx;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step1: Login admin and select PA Workflow mode as 'Manual'
 * for 'Neuromuscular Clinic'. Step 2: Switch to physician portal, login as
 * physician and register new patient. Step 3: Prescribe patient 'Avonex'
 * medication and authorize it. Step 4: Now click on 'Prior Authorization' and
 * verify Diagnosis details under 'Diagnosis' tab.
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ301Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ301Test(String baseUrl) {
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
	public void Step02_loginPhysicianRegPatAndCompleteProfile() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailPA"),
				getYamlValue("physician.passwordPA"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
	}

	@Test
	public void Step03_prescribePatientAndAuthorize() {
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
	}

	@Test
	public void Step04_verifyDiagnosisDetailsOnPAForm() {
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.expandTabs(getYamlValue("physician.paTabs.diagnosis"));
		test.priorAuthorizationPage.verifyDiagnosisDetails(getYamlValue("physician.indication3.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.diagnosis.newDiagnosis"),
				getYamlValue("physician.indication3.otherDetails"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
