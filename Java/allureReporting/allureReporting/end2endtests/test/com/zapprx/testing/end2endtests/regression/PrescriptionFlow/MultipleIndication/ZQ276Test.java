package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.MultipleIndication;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as lindsay.goldman@zapprx.com / zapprx and
 * register new patient. Step 2: Prescribe 'Adcirca' medication to newly
 * registered patient, authorize and logout. Step 3: Login as
 * doctor@neuromuscular.com / zapprx and register new patient. Step 4: Capture
 * patient consent for 'Ampyra' medication and then complete Rx and verify
 * physician is able to authorize
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ276Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ276Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_loginLindsayAndRegisterPatient() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step02_prescribePatientPAHMedAuthorizeAndLogout() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine1.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine1.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.logOut();
	}

	@Test
	public void Step03_loginMSPhysicianAndRegisterPatient() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailPA"),
				getYamlValue("physician.passwordPA"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
		test.patientCommonWorkflow.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
	}

	@Test
	public void Step04_capturePatConPresPatMSMedAndAuthorize() {
		test.patientPage.clickOnPatientConsent();
		boolean remsValue = test.patientCommonWorkflow.clickPatConsentAndSelectIndication_Medication(
				getYamlValue("physician.indication3.name"),
				getYamlValue("physician.indication3.medicines.medicine3.name"));
		test.patientCommonWorkflow.verifyUserIsOnPatientConsentPageToAuthorize();
		test.authorizationPage.enterSignAndSave(patientLastNameDOBAndGender[2]);
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.clickOnCompleteRx();
		test.patientCommonWorkflow.presPatAfterSigningPatCon(getYamlValue("physician.indication3.diagnosis.diagnosis1"),
				getYamlValue("physician.indication3.medicines.medicine3.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], getYamlValue("physician.indication3.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication3.medicines.medicine3.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication3.medicines.medicine3.name"), getYamlValue("physician.password1"));
		test.authorizationPage.verifyAuthSuccessMsg(getYamlValue("physician.authorizationMsg"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}

}
