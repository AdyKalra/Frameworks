package com.zapprx.testing.end2endtests.regression.PARx;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login admin and select PA Workflow mode as 'Manual'
 * for 'Neuromuscular Clinic'. Step 2: Switch to physician portal, login as
 * physician and register new patient. Step 3: Complete Rx and submit PA to
 * PARx. Step 4: Now switch to pharmacy portal and verify PA Document upload and
 * update status to 'More Info Requested' and also verify 'Add New file' is not
 * displayed. Step 5: Login physician and submit PA to PARx. Step 6: Now switch
 * to pharmacy portal and and verify PA Document upload and update status to
 * 'Submitted To Payer', again verify PA Document upload and update status to
 * 'Approved' PA Status and also verify 'Add New file' is not displayed.
 * 
 * @author vivekdua
 */

public class ZQ252Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ252Test(String baseUrl) {
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
		test.patientCommonWorkflow.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
	}

	@Test
	public void Step03_completeRxAndVerifyPADocumentUpload() {
		boolean remsValue = test.patientCommonWorkflow.capturePatConAndPrescribePatient(
				getYamlValue("physician.indication3.name"),
				getYamlValue("physician.indication3.medicines.medicine9.name"),
				getYamlValue("physician.indication3.diagnosis.diagnosis1"),
				getYamlValue("physician.indication3.medicines.medicine9.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0],
				getYamlValue("physician.indication3.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication3.medicines.medicine9.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication3.medicines.medicine9.name"), getYamlValue("physician.passwordPA"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.expandTabs(getYamlValue("physician.paTabs.medicalRecords"));
		test.priorAuthorizationPage.uploadFileAndVerify(getYamlValue("physician.pdfFilePA"));
		test.priorAuthorizationPage.drawSignAndSubmit(getYamlValue("physician.priorAuthButton.submit"),
				getYamlValue("physician.PASubmissionMsg"));
		test.priorAuthorizationPage
				.clickOnViewPresDetails(getYamlValue("physician.indication3.medicines.medicine9.name"));
		test.presDetailsPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus1"));
		test.headerPage.logOut();
	}

	@Test
	public void Step04_onPharmacyPortalVerifyPADocUploadForMoreInfoRequest() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailPA"),
				getYamlValue("pharmacy.password"));
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.verifyPAStatusOnPriorAuthPage(getYamlValue("pharmacy.paStatus.PAStatus1"),
				patientLastNameDOBAndGender[0]);
		test.priorAuthPage.clickOnViewPADetails();
		test.priorAuthPage.expandMedicalRecords();
		test.priorAuthPage.verifyAddNewFileUpdatePAStatusIsDisplayed();
		test.priorAuthPage.uploadFileAndVerify(getYamlValue("pharmacy.pdfFile.moreInfoRequest"));
		test.priorAuthPage.requestMoreInfo();
		test.priorAuthPage.verifyAddNewFileUpdatePAStatusIsNotDisplayed();
		test.headerPage.logOut();
	}

	@Test
	public void Step05_loginPhysicianAndSubmitPA() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailPA"),
				getYamlValue("physician.passwordPA"));
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.clickOnViewRxDetails();
		test.presDetailsPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus2"));
		test.patientCommonWorkflow.createPASubmitAndVerifyPAStatus(
				getYamlValue("physician.indication3.medicines.medicine9.name"),
				getYamlValue("physician.PASubmissionMsg"), getYamlValue("physician.paStatus.PAStatus1"),
				getYamlValue("physician.priorAuthButton.reSubmit"));
		test.headerPage.logOut();
	}

	@Test
	public void Step06_onPharmacyPortalVerifyPADocumentUplaodForApprovedPA() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailPA"),
				getYamlValue("pharmacy.password"));
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.verifyPAStatusOnPriorAuthPage(getYamlValue("pharmacy.paStatus.PAStatus1"),
				patientLastNameDOBAndGender[0]);
		test.priorAuthPage.clickOnViewPADetails();
		test.priorAuthPage.expandMedicalRecords();
		test.priorAuthPage.verifyAddNewFileUpdatePAStatusIsDisplayed();
		test.priorAuthPage.uploadFileAndVerify(getYamlValue("pharmacy.pdfFile.submittedToPayer"));
		test.priorAuthPage.updatePAStatus(getYamlValue("pharmacy.paStatus.PAStatus3"));
		test.priorAuthPage.verifyAddNewFileUpdatePAStatusIsDisplayed();
		test.priorAuthPage.uploadFileAndVerify(getYamlValue("pharmacy.pdfFile.approved"));
		test.priorAuthPage.updatePAStatus(getYamlValue("pharmacy.paStatus.PAStatus4"));
		test.priorAuthPage.verifyAddNewFileUpdatePAStatusIsNotDisplayed();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}