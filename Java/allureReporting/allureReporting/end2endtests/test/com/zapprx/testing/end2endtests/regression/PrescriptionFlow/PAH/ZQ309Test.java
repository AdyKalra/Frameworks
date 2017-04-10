package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login with lindsay.goldman@zapprx.com/zapprx and
 * 'Mark as read' all unread notification and verify that there is no pending
 * notification and logout. Step 2: Login again to physician portal, verify that
 * there is no pending notification, register new patient and complete profile.
 * Step 3: Prescribe patient 'Orenitram' medication, auhtorize and logOut. Step
 * 4: Switch to patient portal and sign health share and logout. Step 5: Login
 * physician and verify new notification count and logout. Step 6 : Login as
 * physician with credential brittany.sorice@zapprx.com/zapprx and verify new
 * notification count.
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ309Test extends BaseTest {
	String[] loginEmailAndMRN;
	String[] patientLastNameDOBAndGender = null;
	String count = "0";

	private ZQ309Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_loginPhysicianVerifyNotificationLogOut() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		if (test.leftnavigationPage.notificationPresent()) {
			test.leftnavigationPage.clickOnNotifications();
			test.notificationsPage.markAsReadAllUnreadNotifications();
		}
		test.leftnavigationPage.verifyNoPendingNotification();
		test.headerPage.logOut();
	}

	@Test
	public void Step02_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.leftnavigationPage.verifyNoPendingNotification();
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		loginEmailAndMRN = test.patientCommonWorkflow.completeProfile(
				getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step03_prescribePatientAndLogOut() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine5.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine5.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine5.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step04_patientSignHealthShareAndLogOut() {
		test.patientCommonWorkflow.switchToPatientPortal(loginEmailAndMRN[0],
				getYamlValue("physician.patientPassword"));
		test.homePage.verifyShareAuthMessage(getYamlValue("physician.indication1.medicines.medicine5.name"));
		test.homePage.signHealthShare(getYamlValue("physician.indication1.medicines.medicine5.name"));
		test.documentPage.enterSignatureAndSubmit();
		test.documentPage.verifySignIsSubmitted(getYamlValue("patient.signSuccessMsg"));
		test.headerPage.clickOnLogOutAtPatientEnd();
	}

	@Test
	public void Step05_loginPhysicianAndVerifyNotification() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.homePage.refreshPage();
		test.leftnavigationPage.verifyNotificationCount(count);
		test.leftnavigationPage.clickOnNotifications();
		test.notificationsPage.verifyNotificationMessage(getYamlValue("physician.notifications.message1"));
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step06_loginAnotherPhysicianAndVerifyNotification() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId2"),
				getYamlValue("physician.password2"));
		test.homePage.refreshPage();
		test.leftnavigationPage.verifyNotificationCount(count);
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
