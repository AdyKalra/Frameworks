package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

public class ZQ112Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;
	String[] loginEmailAndMRN;
	String medication;
	String gender = "Female";
	boolean remsValue;

	private ZQ112Test(String baseUrl) {
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
	public void Step02_prescribeMedication_Tracleer() {
		medication = getYamlValue("physician.indication1.medicines.medicine7.name");
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		remsValue = test.patientCommonWorkflow.prescribePatient(medication,
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine7.pharmacy.pharmacy1"), gender,
				patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.commonDosagePage.clickOnSaveDraft();
		test.commonDosagePage.verifySavedPrescriptionMsg(getYamlValue("physician.savePrescriptionMsg"));
		test.commonDosagePage.clickOnFinishAndReview();
	}

	@Test
	public void Step03_loginPhysicianAndAuthorize_Tracleer() {
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.clickOnViewRxDetails();
		test.presDetailsPage.verifyCompleteButtonIsNotDisplayed();
		test.presDetailsPage.enterPrescriberAuth(gender, remsValue,
				getYamlValue("physician.indication1.medicines.medicine7.name"));
		test.presDetailsPage.enterPhysicianSavedSig(getYamlValue("physician.password1"));
		test.presDetailsPage.clickOnAuthorize();
		test.presDetailsPage.verifyAuthSuccessMsg(getYamlValue("physician.authSuccessMsg"));
		test.presDetailsPage.clickOnContinue();
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step04_loginPatientAndSignHealthShare_Tracleer() {
		test.patientCommonWorkflow.switchToPatientPortal(loginEmailAndMRN[0],
				getYamlValue("physician.patientPassword"));
		test.patientCommonWorkflow.verifyREMSSignOffIsDisplayed(medication, gender);
		test.headerPage.clickOnLogOutAtPatientEnd();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("patient.pageTitle"));
	}

	@Test
	public void Step05_prescribeMedication_Opsumit() {
		medication = getYamlValue("physician.indication1.medicines.medicine4.name");
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.leftnavigationPage.clickOnPatients();
		test.patientPage.enterPatientDetail(patientLastNameDOBAndGender[0]);
		test.patientPage.clickOnSearchIcon();
		test.patientPage.verifyPatientIsDisplayed(patientLastNameDOBAndGender[0]);
		test.patientPage.selectPatientByName(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow
				.clickPresOnPatientModalAndSelectIndication(getYamlValue("physician.indication1.name"));
		remsValue = test.patientCommonWorkflow.prescribePatient(medication,
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine4.pharmacy.pharmacy1"), gender,
				patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.commonDosagePage.clickOnSaveDraft();
		test.commonDosagePage.verifySavedPrescriptionMsg(getYamlValue("physician.savePrescriptionMsg"));
		test.commonDosagePage.clickOnFinishAndReview();
	}

	@Test
	public void Step06_loginPhysicianAndAuthorize_Opsumit() {
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.clickOnViewRxDetails();
		test.presDetailsPage.verifyCompleteButtonIsNotDisplayed();
		test.presDetailsPage.enterPrescriberAuth(gender, remsValue,
				getYamlValue("physician.indication1.medicines.medicine4.name"));
		test.presDetailsPage.enterPhysicianSavedSig(getYamlValue("physician.password1"));
		test.presDetailsPage.clickOnAuthorize();
		test.presDetailsPage.verifyAuthSuccessMsg(getYamlValue("physician.authSuccessMsg"));
		test.presDetailsPage.clickOnContinue();
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step07_loginPatientAndSignHealthShare_Opsumit() {
		test.patientCommonWorkflow.switchToPatientPortal(loginEmailAndMRN[0],
				getYamlValue("physician.patientPassword"));
		test.patientCommonWorkflow.verifyREMSSignOffIsDisplayed(medication, gender);
		test.headerPage.clickOnLogOutAtPatientEnd();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("patient.pageTitle"));
	}

	@Test
	public void Step08_prescribeMedication_Letairis() {
		medication = getYamlValue("physician.indication1.medicines.medicine3.name");
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.leftnavigationPage.clickOnPatients();
		test.patientPage.enterPatientDetail(patientLastNameDOBAndGender[0]);
		test.patientPage.clickOnSearchIcon();
		test.patientPage.verifyPatientIsDisplayed(patientLastNameDOBAndGender[0]);
		test.patientPage.selectPatientByName(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow
				.clickPresOnPatientModalAndSelectIndication(getYamlValue("physician.indication1.name"));
		remsValue = test.patientCommonWorkflow.prescribePatient(medication,
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine3.pharmacy.pharmacy1"), gender,
				patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(medication);
		test.commonDosagePage.clickOnSaveDraft();
		test.commonDosagePage.verifySavedPrescriptionMsg(getYamlValue("physician.savePrescriptionMsg"));
		test.commonDosagePage.clickOnFinishAndReview();
	}

	@Test
	public void Step09_loginPhysicianAndAuthorize_Letairis() {
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.clickOnViewRxDetails();
		test.presDetailsPage.verifyCompleteButtonIsNotDisplayed();
		test.presDetailsPage.enterPrescriberAuth(gender, remsValue,
				getYamlValue("physician.indication1.medicines.medicine3.name"));
		test.presDetailsPage.enterPhysicianSavedSig(getYamlValue("physician.password1"));
		test.presDetailsPage.clickOnAuthorize();
		test.presDetailsPage.verifyAuthSuccessMsg(getYamlValue("physician.authSuccessMsg"));
		test.presDetailsPage.clickOnContinue();
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step10_loginPatientAndSignHealthShare_Letairis() {
		test.patientCommonWorkflow.switchToPatientPortal(loginEmailAndMRN[0],
				getYamlValue("physician.patientPassword"));
		test.patientCommonWorkflow.verifyREMSSignOffIsDisplayed(medication, gender);
		test.headerPage.clickOnLogOutAtPatientEnd();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("patient.pageTitle"));
	}

	@Test
	public void Step11_prescribeMedication_Adempas() {
		medication = getYamlValue("physician.indication1.medicines.medicine2.name");
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.leftnavigationPage.clickOnPatients();
		test.patientPage.enterPatientDetail(patientLastNameDOBAndGender[0]);
		test.patientPage.clickOnSearchIcon();
		test.patientPage.verifyPatientIsDisplayed(patientLastNameDOBAndGender[0]);
		test.patientPage.selectPatientByName(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow
				.clickPresOnPatientModalAndSelectIndication(getYamlValue("physician.indication1.name"));
		remsValue = test.patientCommonWorkflow.prescribePatient(medication,
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine2.pharmacy.pharmacy1"), gender,
				patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(medication);
		test.commonDosagePage.clickOnSaveDraft();
		test.commonDosagePage.verifySavedPrescriptionMsg(getYamlValue("physician.savePrescriptionMsg"));
		test.commonDosagePage.clickOnFinishAndReview();
	}

	@Test
	public void Step12_loginPhysicianAndAuthorize_Adempas() {
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.clickOnViewRxDetails();
		test.presDetailsPage.verifyCompleteButtonIsNotDisplayed();
		test.presDetailsPage.enterPrescriberAuth(gender, remsValue,
				getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.presDetailsPage.enterPhysicianSavedSig(getYamlValue("physician.password1"));
		test.presDetailsPage.clickOnAuthorize();
		test.presDetailsPage.verifyAuthSuccessMsg(getYamlValue("physician.authSuccessMsg"));
		test.presDetailsPage.clickOnContinue();
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step13_loginPatientAndSignHealthShare_Adempas() {
		test.patientCommonWorkflow.switchToPatientPortal(loginEmailAndMRN[0],
				getYamlValue("physician.patientPassword"));
		test.patientCommonWorkflow.verifyREMSSignOffIsDisplayed(medication, gender);
		test.headerPage.clickOnLogOutAtPatientEnd();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("patient.pageTitle"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
