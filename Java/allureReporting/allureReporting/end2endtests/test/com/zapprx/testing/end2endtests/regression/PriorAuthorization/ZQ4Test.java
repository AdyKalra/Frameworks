package com.zapprx.testing.end2endtests.regression.PriorAuthorization;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and search
 * the newly created patient Step 2: Physician prescribe and save it as draft
 * Step 3: Physician request prior authorization and logout from application
 * Step 4: Login as patient and verify notification message to share
 * authorization is not displayed as physician has not authorized yet Step 5:
 * Login as physician to verify continue button is not displayed and authorize
 * patient for health share sign-off Step 6: Login as patient and sign the
 * health share to authorize
 * 
 * @author vivekdua
 *
 */
public class ZQ4Test extends BaseTest {
	String[] loginEmailAndMRN;
	String[] patientLastNameDOBAndGender = null;

	private ZQ4Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		loginEmailAndMRN = test.patientCommonWorkflow.completeProfile(
				getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.pcnNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step02_prescribeMedicationAndSave() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indications.indication1.name"));
		test.patientCommonWorkflow.prescribePatient(getYamlValue("physician.indication1.medicines.medicine1.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.commonDosagePage.clickOnSaveDraft();
		test.commonDosagePage.verifySavedPrescriptionMsg(getYamlValue("physician.savePrescriptionMsg"));
		test.commonDosagePage.clickOnFinishAndReview();
	}

	@Test
	public void Step03_requestPriorAuthorizationAndLogOut() {
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthForPatientPage.userSelectFormAndSave();
		test.priorAuthForPatientPage.clickOnReviewPrescription();
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step04_loginPatientAndVerifyNotification() {
		test.patientCommonWorkflow.switchToPatientPortal(loginEmailAndMRN[0],
				getYamlValue("physician.patientPassword"));
		test.homePage.verifyNotificationIsNotDisplayed(getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.headerPage.clickOnLogOutAtPatientEnd();
	}

	@Test
	public void Step05_loginPhysicianAndAuthorize() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.presDetailsPage.verifyCompleteButtonIsNotDisplayed();
		test.presDetailsPage.enterPhysicianSavedSig(getYamlValue("physician.password1"));
		test.presDetailsPage.clickOnAuthorize();
		test.presDetailsPage.verifyAuthSuccessMsg(getYamlValue("physician.authSuccessMsg"));
		test.presDetailsPage.clickOnContinue();
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step06_loginPatientAndSignHealthShare() {
		test.patientCommonWorkflow.switchToPatientPortal(loginEmailAndMRN[0],
				getYamlValue("physician.patientPassword"));
		test.patientCommonWorkflow
				.patientSignHealthShare(getYamlValue("physician.indication1.medicines.medicine1.name"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
