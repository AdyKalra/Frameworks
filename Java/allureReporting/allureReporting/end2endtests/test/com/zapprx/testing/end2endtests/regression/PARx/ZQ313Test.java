package com.zapprx.testing.end2endtests.regression.PARx;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login admin and select PA Workflow mode as 'Manual'
 * for 'ZappRx Practice'. Step 2: Switch to physician portal, login as physician
 * to register new patient and complete profile. Step 3: Complete Rx, save PA
 * and verify signature is not required to save PA. Step 4: Submit PA without
 * any signature and verify that an error modal appears with msg 'A signature is
 * required to submit the PA.', now enter signature, save PA and verify that
 * signature is saved and also submit PA and verify that PA is submitted. Step
 * 5: Switch to pharmacy portal and 'Request More Info'. Step 6: Login physician
 * and verify that signature is required to Re-Submit PA under More Info
 * Requested. Step 7: Login pharmacy and update PA status to 'Submitted to
 * Payer' and then update PA Status to 'Denied'. Step 8: Now login physician and
 * verify that signature is required to Re-Submit PA under Appeal.
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ313Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ313Test(String baseUrl) {
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
	public void Step03_completeRxVerifySigNotReqToSavePA() {
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
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.clickOnSaveSubmitReSubButton(getYamlValue("physician.priorAuthButton.save"));
		test.priorAuthorizationPage.verifySuccessMsgAndClickOkay(getYamlValue("physician.PASavedMsg"));
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus3"));
	}

	@Test
	public void Step04_verifySigReqToSubmitPA() {
		test.priorAuthorizationPage.verifyUserNotAbleToSubmitWithoutSig(
				getYamlValue("physician.priorAuthButton.submit"), getYamlValue("physician.PASigErrorMsg"));
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus3"));
		test.priorAuthorizationPage.drawSignAndSubmit(getYamlValue("physician.priorAuthButton.save"),
				getYamlValue("physician.PASavedMsg"));
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus3"));
		test.priorAuthorizationPage.verifySignatureIsSaved();
		test.priorAuthorizationPage.clickOnSaveSubmitReSubButton(getYamlValue("physician.priorAuthButton.submit"));
		test.priorAuthorizationPage.clickOnConfirmModal();
		test.priorAuthorizationPage.verifySuccessMsgAndClickOkay(getYamlValue("physician.PASubmissionMsg"));
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus1"));
		test.headerPage.logOut();
	}

	@Test
	public void Step05_switchPharmacyPortalReqMoreInfo() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailPA"),
				getYamlValue("pharmacy.password"));
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.clickOnViewPADetails();
		test.priorAuthPage.requestMoreInfo();
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus2"));
		test.headerPage.logOut();
	}

	@Test
	public void Step06_loginPhysicianVerifySigReqToReSubmitPAUnderMoreInfoReq() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.clickOnViewRxDetails();
		test.presDetailsPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus2"));
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus2"));
		test.priorAuthorizationPage.verifyUserNotAbleToSubmitWithoutSig(
				getYamlValue("physician.priorAuthButton.reSubmit"), getYamlValue("physician.PASigErrorMsg"));
		test.priorAuthorizationPage.drawSignAndSubmit(getYamlValue("physician.priorAuthButton.reSubmit"),
				getYamlValue("physician.PASubmissionMsg"));
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus1"));
		test.priorAuthorizationPage.verifySignatureIsSaved();
		test.headerPage.logOut();
	}

	@Test
	public void Step07_loginPharmacyAndUpdateStatusToDenied() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailPA"),
				getYamlValue("pharmacy.password"));
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.clickOnViewPADetails();
		test.priorAuthPage.updatePAStatus(getYamlValue("pharmacy.paStatus.PAStatus3"));
		test.priorAuthPage.updatePAStatus(getYamlValue("pharmacy.paStatus.PAStatus5"));
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus5"));
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("pharmacy.pageTitle"));
	}

	@Test
	public void Step08_loginPhysicianVerifySigReqToReSubmitPAUnderAppeal() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.clickOnViewRxDetails();
		test.presDetailsPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus5"));
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus5"));
		test.priorAuthorizationPage.savePAUnderAppeal();
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus7"));
		test.priorAuthorizationPage.verifySignatureIsSaved();
		test.priorAuthorizationPage.clickOnSaveSubmitReSubButton(getYamlValue("physician.priorAuthButton.reSubmit"));
		test.priorAuthorizationPage.clickOnOkay();
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus6"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
