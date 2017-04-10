package com.zapprx.testing.end2endtests.regression.PARx;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login admin and select PA Workflow mode as 'Manual'
 * for 'ZappRx Practice'. 
 * Step 2: Switch to physician portal, login as physician to register new patient
 * and complete profile.
 * Step 3: Complete Rx and submit PA to PARx and verify PA Status: Submitted to
 * PARx on PA input page, prescription details page and Rx Status page.
 * Step 4: Now switch to pharmacy portal, verify PA Status: Submitted to PARx on
 * PA Listing page and PA input page, now update PA status to 'Submitted to Payer'
 * and verify PA Status: Submitted to Payer on PA Listing page and PA input page.
 * Step 5: Switch to physician portal and verify PA Status: Submitted to Payer on
 * PA input page, prescription details page and Rx Status page.
 * Step 6: Switch to pharmacy portal, update PA status to 'More Info Requested'
 * and verify PA Status: More Info Requested on PA Listing page and PA input page.
 * Step 7: Switch to physician portal and verify PA Status: 'More Info Requested'
 * and verify PA Status: More Info Requested on PA input page, prescription details
 * page and Rx Status page, now re-submit PA to PARx and verify PA Status: Submitted to
 * PARx on PA input page, prescription details page and Rx Status page.
 * Step 8: Switch to pharmacy portal, verify PA Status: Submitted to PARx on PA Listing
 * page and PA input page, now update PA status to 'Submitted to Payer' and
 * again update PA status to Denied and verify PA Status: Denied on PA Listing
 * page and PA input page.
 * Step 9: Switch to physician portal and verify PA Status: Denied on PA input page,
 * prescription details page and Rx Status page, now appeal PA and re-submit PA under
 * appeal to PARx and again verify PA Status: Submitted to PARx (Under Appeal) on PA
 * input page and 'Submitted to PARx' on prescription details page and Rx Status page.
 * Step 10: Switch to pharmacy portal, verify PA Status: Submitted to PARx on PA Listing
 * page and Submitted to PARx (Under Appeal) on PA input page, update PA status to 'More
 * Info Requested' and verify PA Status: More Info Requested (Under Appeal) on
 * PA input page and 'More Info Requested' on PA Listing page.
 * Step 11: Switch to physician portal and verify PA Status: More Info Requested on
 * prescription details page and Rx Status page and 'More Info Requested (Under Appeal)' on
 * PA input page.
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ310Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ310Test(String baseUrl) {
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
	public void Step03_completeRxSubmitPAToPARxVerifyPAStatus() {
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
		test.patientCommonWorkflow.createPASubmitAndVerifyPAStatus(
				getYamlValue("physician.indication1.medicines.medicine3.name"),
				getYamlValue("physician.PASubmissionMsg"), getYamlValue("physician.paStatus.PAStatus1"),
				getYamlValue("physician.priorAuthButton.submit"));
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.verifyPAStatus(patientLastNameDOBAndGender[0], getYamlValue("physician.paStatus.PAStatus1"));
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step04_loginPharmacyUpdateSubmittedToPayerAndVerify() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailPA"),
				getYamlValue("pharmacy.password"));
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.verifyPAStatusOnPriorAuthPage(getYamlValue("pharmacy.paStatus.PAStatus1"),
				patientLastNameDOBAndGender[0]);
		test.priorAuthPage.clickOnViewPADetails();
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus1"));
		test.priorAuthPage.clickOnViewPresDetails(getYamlValue("physician.indication1.medicines.medicine3.name"));
		test.priorAuthPage.verifyPAStatusOnPresDetailsPage(getYamlValue("pharmacy.paStatus.PAStatus1"));
		test.priorAuthPage.clickOnPriorAuthorization();
		test.priorAuthPage.updatePAStatus(getYamlValue("pharmacy.paStatus.PAStatus3"));
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus3"));
		test.priorAuthPage.clickOnViewPresDetails(getYamlValue("physician.indication1.medicines.medicine3.name"));
		test.priorAuthPage.verifyPAStatusOnPresDetailsPage(getYamlValue("pharmacy.paStatus.PAStatus3"));
		test.leftnavigationPage.clickOnPAList();
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.verifyPAStatusOnPriorAuthPage(getYamlValue("pharmacy.paStatus.PAStatus3"),
				patientLastNameDOBAndGender[0]);
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("pharmacy.pageTitle"));
	}

	@Test
	public void Step05_loginPhysicianVerifySubmittedToPayerStatus() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.verifyPAStatus(patientLastNameDOBAndGender[0], getYamlValue("physician.paStatus.PAStatus8"));
		test.rxStatusPage.clickOnViewRxDetails();
		test.presDetailsPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus8"));
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus8"));
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step06_loginPharmacyRequestMoreInfoAndVerify() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailPA"),
				getYamlValue("pharmacy.password"));
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.clickOnViewPADetails();
		test.priorAuthPage.requestMoreInfo();
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus2"));
		test.priorAuthPage.clickOnViewPresDetails(getYamlValue("physician.indication1.medicines.medicine3.name"));
		test.priorAuthPage.verifyPAStatusOnPresDetailsPage(getYamlValue("pharmacy.paStatus.PAStatus2"));
		test.leftnavigationPage.clickOnPAList();
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.verifyPAStatusOnPriorAuthPage(getYamlValue("pharmacy.paStatus.PAStatus2"),
				patientLastNameDOBAndGender[0]);
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("pharmacy.pageTitle"));
	}

	@Test
	public void Step07_loginPhysicianReSubmitPAToPARxAndVerifyStatus() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.verifyPAStatus(patientLastNameDOBAndGender[0], getYamlValue("physician.paStatus.PAStatus2"));
		test.rxStatusPage.clickOnViewRxDetails();
		test.presDetailsPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus2"));
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus2"));
		test.priorAuthorizationPage.drawSignAndSubmit(getYamlValue("physician.priorAuthButton.reSubmit"),
				getYamlValue("physician.PASubmissionMsg"));
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus1"));
		test.priorAuthorizationPage
				.clickOnViewPresDetails(getYamlValue("physician.indication1.medicines.medicine3.name"));
		test.presDetailsPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus1"));
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.verifyPAStatus(patientLastNameDOBAndGender[0], getYamlValue("physician.paStatus.PAStatus1"));
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step08_loginPharmacyUpdateStatusToDeniedAndVerify() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailPA"),
				getYamlValue("pharmacy.password"));
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.verifyPAStatusOnPriorAuthPage(getYamlValue("pharmacy.paStatus.PAStatus1"),
				patientLastNameDOBAndGender[0]);
		test.priorAuthPage.clickOnViewPADetails();
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus1"));
		test.priorAuthPage.clickOnViewPresDetails(getYamlValue("physician.indication1.medicines.medicine3.name"));
		test.priorAuthPage.verifyPAStatusOnPresDetailsPage(getYamlValue("pharmacy.paStatus.PAStatus1"));
		test.priorAuthPage.clickOnPriorAuthorization();
		test.priorAuthPage.updatePAStatus(getYamlValue("pharmacy.paStatus.PAStatus3"));
		test.priorAuthPage.updatePAStatus(getYamlValue("pharmacy.paStatus.PAStatus5"));
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus5"));
		test.priorAuthPage.clickOnViewPresDetails(getYamlValue("physician.indication1.medicines.medicine3.name"));
		test.priorAuthPage.verifyPAStatusOnPresDetailsPage(getYamlValue("pharmacy.paStatus.PAStatus5"));
		test.leftnavigationPage.clickOnPAList();
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.verifyPAStatusOnPriorAuthPage(getYamlValue("pharmacy.paStatus.PAStatus5"),
				patientLastNameDOBAndGender[0]);
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("pharmacy.pageTitle"));
	}

	@Test
	public void Step09_loginPhysicianAppealPAReSubmitPAToPARxAndVerify() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.verifyPAStatus(patientLastNameDOBAndGender[0], getYamlValue("physician.paStatus.PAStatus5"));
		test.rxStatusPage.clickOnViewRxDetails();
		test.presDetailsPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus5"));
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus5"));
		test.priorAuthorizationPage.appealPAAndResubmit();
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus6"));
		test.priorAuthorizationPage
				.clickOnViewPresDetails(getYamlValue("physician.indication1.medicines.medicine3.name"));
		test.presDetailsPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus1"));
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.verifyPAStatus(patientLastNameDOBAndGender[0], getYamlValue("physician.paStatus.PAStatus1"));
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step10_loginPharmacyRequestMoreInfoAndVerify() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailPA"),
				getYamlValue("pharmacy.password"));
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.verifyPAStatusOnPriorAuthPage(getYamlValue("pharmacy.paStatus.PAStatus1"),
				patientLastNameDOBAndGender[0]);
		test.priorAuthPage.clickOnViewPADetails();
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus6"));
		test.priorAuthPage.clickOnViewPresDetails(getYamlValue("physician.indication1.medicines.medicine3.name"));
		test.priorAuthPage.verifyPAStatusOnPresDetailsPage(getYamlValue("pharmacy.paStatus.PAStatus1"));
		test.priorAuthPage.clickOnPriorAuthorization();
		test.priorAuthPage.requestMoreInfo();
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus7"));
		test.priorAuthPage.clickOnViewPresDetails(getYamlValue("physician.indication1.medicines.medicine3.name"));
		test.priorAuthPage.verifyPAStatusOnPresDetailsPage(getYamlValue("pharmacy.paStatus.PAStatus2"));
		test.leftnavigationPage.clickOnPAList();
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.verifyPAStatusOnPriorAuthPage(getYamlValue("pharmacy.paStatus.PAStatus2"),
				patientLastNameDOBAndGender[0]);
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("pharmacy.pageTitle"));
	}

	@Test
	public void Step11_loginPhysicianVerifyRequestMoreInfoStatus() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.verifyPAStatus(patientLastNameDOBAndGender[0], getYamlValue("physician.paStatus.PAStatus2"));
		test.rxStatusPage.clickOnViewRxDetails();
		test.presDetailsPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus2"));
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus9"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
