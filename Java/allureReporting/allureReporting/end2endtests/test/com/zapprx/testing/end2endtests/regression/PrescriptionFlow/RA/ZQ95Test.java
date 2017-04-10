package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.RA;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register a new patient and
 * validate newly created patient is displayed in Patient's list. Step 2: Now
 * prescribe medication to newly registered patient and logout from application.
 * Step 3: User login on Pharmacy portal to request correction for Date of Birth
 * and logout. Step 4: Physician again login to correct the Date of Birth as per
 * request. Step 5: Pharmacy login to validate that updated date of birth is
 * getting displayed.
 * 
 * @author vivekdua
 *
 */

public class ZQ95Test extends BaseTest {
	String updatedDateOfBirth;
	String[] loginEmailAndMRN;
	String[] patientLastNameDOBAndGender = null;

	private ZQ95Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		loginEmailAndMRN = test.patientCommonWorkflow.completeProfile(
				getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step02_prescribePatientAndLogOut() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication2.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication2.medicines.medicine6.name"),
				getYamlValue("physician.indication2.diagnosis.diagnosis1"),
				getYamlValue("physician.indication2.medicines.medicine6.pharmacy.pharmacy2"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication2.medicines.medicine6.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication2.medicines.medicine6.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step03_loginPharmacyAndRequestCorrection() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailId"),
				getYamlValue("pharmacy.password"));
		test.leftnavigationPage.clickOnNotifications();
		test.notificationsPage.clickNotificationMsg(getYamlValue("physician.indication2.medicines.medicine6.name"));
		test.presDetailsPage.requestCorrectionforDOB(getYamlValue("pharmacy.correctionNoteText"));
		test.presDetailsPage.verifyCorrectionRequestMsg(getYamlValue("pharmacy.correctionNoteMsg"));
		test.presDetailsPage.clickOnContinueAtPharmacyEnd();
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("pharmacy.pageTitle"));
	}

	@Test
	public void Step04_loginPhysicianAndEditDOB() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.leftnavigationPage.clickOnNotifications();
		test.notificationsPage.clickNotificationMsg(getYamlValue("physician.indication2.medicines.medicine6.name"));
		test.presDetailsPage.verifyCorrectionMessage(getYamlValue("pharmacy.correctionNoteText"));
		test.presDetailsPage.expandPresDetails();
		updatedDateOfBirth = test.presDetailsPage.editDOBAuthorizeAndSubmitCorrection();
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step05_loginPharmacyAndVerifyUpdatedDate() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailId"),
				getYamlValue("pharmacy.password"));
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.clickOnViewRxDetails();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.expandPresDetails();
		test.presDetailsPage.verifyDateOfBirth(updatedDateOfBirth);
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
