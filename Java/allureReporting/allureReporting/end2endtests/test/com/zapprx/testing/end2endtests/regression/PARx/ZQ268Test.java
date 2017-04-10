package com.zapprx.testing.end2endtests.regression.PARx;

import com.zapprx.testing.end2endtests.basetests.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

/**
 * Steps Automated: Step 1: Login admin and select PA Workflow mode as 'Manual'
 * for 'Neuromuscular Clinic'. Step 2: Switch to physician portal, login as
 * physician to register new patient and complete profile having 'Tufts Health
 * Plan' insurance. Step 3: Prescribe Ampyra medication, authorize it, capture
 * patient consent from prescription details page and then submit PA to PARx and
 * click on 'Ampyra Rx' to view Prescrition details and verify PA status under
 * quick info. Step 4: Now verify under Checklist that Prior Authorization is
 * checked and click on 'Prior Authorization' and verify 'Submit' button is not
 * displayed and then click on patient name and verify that no PA is present
 * under 'Prior Auth' tab on patient profile.
 * 
 * @author vivekdua
 */

public class ZQ268Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ268Test(String baseUrl) {
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
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName11"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
	}

	@Test
	public void Step03_completeRxAndSubmitPA() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication3.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication3.medicines.medicine3.name"),
				getYamlValue("physician.indication3.diagnosis.diagnosis1"),
				getYamlValue("physician.indication3.medicines.medicine3.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0],
				getYamlValue("physician.indication3.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication3.medicines.medicine3.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication3.medicines.medicine3.name"), getYamlValue("physician.passwordPA"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.presDetailsPage.clickOnPatientConsent();
		test.presDetailsPage.capturePatientConsent(patientLastNameDOBAndGender[0],
				getYamlValue("physician.indication3.medicines.medicine3.name"));
		test.patientCommonWorkflow.createPASubmitAndVerifyPAStatus(
				getYamlValue("physician.indication3.medicines.medicine3.name"),
				getYamlValue("physician.PASubmissionMsg"), getYamlValue("physician.paStatus.PAStatus1"),
				getYamlValue("physician.priorAuthButton.submit"));
	}

	@Test
	public void Step04_verifyChecklistAndPatientProfilePATab() {
		test.presDetailsPage.verifyPriorAuthorizationChecklist();
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.verifySubmitButtonIsNotDisplayed(getYamlValue("physician.priorAuthButton.submit"));
		test.priorAuthorizationPage.clickOnPatientName();
		test.patientProfilePage.clickOnPriorAuthTab();
		test.patientProfilePage.verifyPriorAuthExist(getYamlValue("physician.indication3.medicines.medicine3.name"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
