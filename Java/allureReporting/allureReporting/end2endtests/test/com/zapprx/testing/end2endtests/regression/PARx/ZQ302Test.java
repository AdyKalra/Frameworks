package com.zapprx.testing.end2endtests.regression.PARx;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step1: Login admin and select PA Workflow mode as 'Manual'
 * for 'ZappRx Practice'. Step 2: Switch to physician portal, login as
 * physician and register new patient. Step 3: Complete Rx and submit PA to PARx
 * marking PA as urgent and verify that '*PA has been marked as urgent' text is
 * displayed and also verify alert icon on prescription listings page. Step 4:
 * Now switch to pharmacy portal and verify Alert icon on PA listings page and
 * then click on 'View PA Details' and verify '*PA has been marked as urgent' is
 * displayed and update PA status to 'More Info Requested'. Step 5: Login
 * physician, access PA and uncheck 'Mark PA as Urgent' checkbox and resubmit PA
 * to PARx and then verify '*PA has been marked as urgent' text is not displayed
 * on PA form and also verify Alert icon is not displayed on prescription
 * listings page. Step 6: Now switch to pharmacy portal and verify Alert icon on
 * PA listings page is not displayed and then click on 'View PA Details' and
 * verify '*PA has been marked as urgent' text is not displayed.
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ302Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ302Test(String baseUrl) {
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
		test.priorAuthorizationPage.clickOnMarkPAUrgentCheckbox();
		test.priorAuthorizationPage.drawSignAndSubmit(getYamlValue("physician.priorAuthButton.submit"),
				getYamlValue("physician.PASubmissionMsg"));
		test.priorAuthorizationPage.verifyPAIsMarkedAsUrgentTextIsDisplayed(getYamlValue("physician.paMarkedUrgent"));
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.headerPage.verifyUserIsOnCorrectPage("Rx Status");
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.verifyPAUrgentAlertIconIsDisplayed(patientLastNameDOBAndGender[0]);
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step04_onPharmacyPortalVerifyMarkPAUrgentAndReqMoreInfo() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailPA"),
				getYamlValue("pharmacy.password"));
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.verifyPAStatusOnPriorAuthPage(getYamlValue("pharmacy.paStatus.PAStatus1"),
				patientLastNameDOBAndGender[0]);
		test.priorAuthPage.verifyPAUrgentAlertIconIsDisplayed(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.clickOnViewPADetails();
		test.priorAuthPage.verifyPAIsMarkedAsUrgentTextIsDisplayed(getYamlValue("pharmacy.paMarkedUrgent"));
		test.priorAuthPage.requestMoreInfo();
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus2"));
		test.headerPage.logOut();
	}

	@Test
	public void Step05_onPhysicianPortalMarkPAUrgentAndResubmitPA() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.clickOnViewRxDetails();
		test.presDetailsPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus2"));
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.clickOnMarkPAUrgentCheckbox();
		test.priorAuthorizationPage.drawSignAndSubmit(getYamlValue("physician.priorAuthButton.reSubmit"),
				getYamlValue("physician.PASubmissionMsg"));
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus1"));
		test.priorAuthorizationPage.verifyPAIsMarkedAsUrgentTextNotDisplayed();
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.headerPage.verifyUserIsOnCorrectPage("Rx Status");
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.verifyPAUrgentAlertIconNotDisplayed(patientLastNameDOBAndGender[0]);
	}

	@Test
	public void Step06_onPharmacyPortalVerifyAlertIconAndMarkPAUrgentText() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailPA"),
				getYamlValue("pharmacy.password"));
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.verifyPAStatusOnPriorAuthPage(getYamlValue("pharmacy.paStatus.PAStatus1"),
				patientLastNameDOBAndGender[0]);
		test.priorAuthPage.verifyPAUrgentAlertIconNotDisplayed(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.clickOnViewPADetails();
		test.priorAuthPage.verifyPAIsMarkedAsUrgentTextNotDisplayed();
	}
	
	@Test
	public void Step07_loginAdminSelectZapprxWorkflowMode() {
		test.patientCommonWorkflow.switchToAdminPortal(getYamlValue("admin.emailId1"),
				getYamlValue("admin.password1"));
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