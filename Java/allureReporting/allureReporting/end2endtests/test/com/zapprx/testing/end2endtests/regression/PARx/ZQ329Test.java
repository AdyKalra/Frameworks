package com.zapprx.testing.end2endtests.regression.PARx;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login admin and select PA Workflow mode as 'Manual'
 * for 'ZappRx Practice'. Step 2: Switch to physician portal, login as physician
 * with credential 'lindsay.goldman@zapprx.com/zapprx' to register new patient
 * and complete profile. Step 3: Complete Rx and submit PA to PARx. Step 4: Now
 * switch to pharmacy portal and verify PA form is displayed. Step 5: Login
 * physician, cancel prescription and verify PA Status and Rx Status as
 * 'Cancelled' under quick info, Save/Submit button is not displayed on PA page
 * and Update Rx is disabled on Rx status page. Step 6: Again switch to pharmacy
 * portal, verify cancelled PA notification message, click on notification and
 * verify that 'Update PA Status' is not displayed while 'Review' button is
 * displayed on PA form, also verify 'Update' is disabled on PA list page.
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ329Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ329Test(String baseUrl) {
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
	public void Step02_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
	}

	@Test
	public void Step03_completeRxAndSubmitPAToPARx() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine2.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine2.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine2.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.patientCommonWorkflow.createPASubmitAndVerifyPAStatus(
				getYamlValue("physician.indication1.medicines.medicine2.name"),
				getYamlValue("physician.PASubmissionMsg"), getYamlValue("physician.paStatus.PAStatus1"),
				getYamlValue("physician.priorAuthButton.submit"));
		test.headerPage.logOut();
	}

	@Test
	public void Step04_onPharmacyPortalVerifyPAIsSentToPARx() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailPA"),
				getYamlValue("pharmacy.password"));
		test.leftnavigationPage.clickOnNotifications();
		test.notificationsPage.clickNotificationMsg(getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.priorAuthPage.verifyPAFormIsDisplayed(getYamlValue("physician.PAFormTitle"));
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus1"));
		test.headerPage.logOut();
	}

	@Test
	public void Step05_loginPhysicianCancelPresAndVerifyStatus() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.clickOnViewRxDetails();
		test.presDetailsPage.verifyPatientConsentLinkIsDisplayed();
		test.presDetailsPage.cancelPrescription(getYamlValue("physician.cancelPrescription.cancelPresConfirmText"),
				getYamlValue("physician.cancelPrescription.cancelPresSuccessText"));
		test.presDetailsPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus10"));
		test.presDetailsPage.verifyRxStatus(getYamlValue("physician.rxStatus.cancelled"));
		test.presDetailsPage.verifyPatientConsentLinkIsNotDisplayed();
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus10"));
		test.priorAuthorizationPage
				.verifySaveSubmitButtonIsNotDisplayed(getYamlValue("physician.priorAuthButton.save"));
		test.priorAuthorizationPage
				.verifySaveSubmitButtonIsNotDisplayed(getYamlValue("physician.priorAuthButton.submit"));
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.verifyPAStatus(patientLastNameDOBAndGender[0], getYamlValue("physician.paStatus.PAStatus10"));
		test.rxStatusPage.verifyUpdateRxIsDisabled();
		test.headerPage.logOut();
	}

	@Test
	public void Step06_onPharmacyPortalVerifyCancelNotfctn() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailPA"),
				getYamlValue("pharmacy.password"));
		test.leftnavigationPage.clickOnNotifications();
		test.notificationsPage.verifyNotificationMessage(getYamlValue("pharmacy.notifications.message4"));
		test.notificationsPage.clickNotificationMsg(getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.priorAuthPage.verifyPAFormIsDisplayed(getYamlValue("physician.PAFormTitle"));
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus8"));
		test.priorAuthPage.verifyUpdatePAStatusIsNotDisplayed();
		test.priorAuthPage.verifyReviewButtonIsDisplayed();
		test.leftnavigationPage.clickOnPAList();
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.verifyPAStatusOnPriorAuthPage(getYamlValue("pharmacy.paStatus.PAStatus8"),
				patientLastNameDOBAndGender[0]);
		test.priorAuthPage.verifyUpdateIsDisabled();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}