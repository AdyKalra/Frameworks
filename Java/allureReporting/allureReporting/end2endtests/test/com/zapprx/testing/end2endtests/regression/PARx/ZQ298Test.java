package com.zapprx.testing.end2endtests.regression.PARx;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step1: Login admin and select PA Workflow mode as 'Manual'
 * for 'Neuromuscular Clinic'. Step 2: Switch to physician portal, login as
 * physician and register new patient. Step 3: Complete Rx and submit PA to
 * PARx. Step 4: Now switch to pharmacy portal, access PA and update PA status
 * to 'More Info Requested' and fetch the notification count. Step 5: Login
 * physician and resubmit PA to PARx. Step 6: Now switch to pharmacy portal and
 * verify notification count and new notification message is present.
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ298Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ298Test(String baseUrl) {
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
	public void Step03_completeRxAndSubmitPAToPARx() {
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
		test.patientCommonWorkflow.createPASubmitAndVerifyPAStatus(
				getYamlValue("physician.indication3.medicines.medicine2.name"),
				getYamlValue("physician.PASubmissionMsg"), getYamlValue("physician.paStatus.PAStatus1"),
				getYamlValue("physician.priorAuthButton.submit"));
		test.headerPage.logOut();
	}

	@Test
	public void Step04_onPharmacyPortalVerifyNewNotfctnAndReqMoreInfo() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailPA"),
				getYamlValue("pharmacy.password"));
		test.leftnavigationPage.clickOnNotifications();
		test.notificationsPage.verifyNotificationMessage(getYamlValue("pharmacy.notifications.message2"));
		test.notificationsPage.clickNotificationMsg(getYamlValue("physician.indication3.medicines.medicine2.name"));
		test.priorAuthPage.requestMoreInfo();
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus2"));
		test.notificationsPage.fetchNotificationCount();
		test.headerPage.logOut();
	}

	@Test
	public void Step05_loginPhysicianVerifyNewNotctnAndResubmitPA() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailPA"),
				getYamlValue("physician.passwordPA"));
		test.leftnavigationPage.clickOnNotifications();
		test.notificationsPage.verifyNotificationMessage(getYamlValue("physician.notifications.message2"));
		test.notificationsPage.clickNotificationMsg(getYamlValue("physician.indication3.medicines.medicine2.name"));
		test.priorAuthorizationPage.drawSignAndSubmit(getYamlValue("physician.priorAuthButton.reSubmit"),
				getYamlValue("physician.PASubmissionMsg"));
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus1"));
		test.headerPage.logOut();
	}

	@Test
	public void Step06_onPharmacyPortalVerifyNewNotification() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailPA"),
				getYamlValue("pharmacy.password"));
		test.leftnavigationPage.clickOnNotifications();
		test.notificationsPage.verifyNewNotificationCount();
		test.notificationsPage.verifyNotificationMessage(getYamlValue("pharmacy.notifications.message3"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
