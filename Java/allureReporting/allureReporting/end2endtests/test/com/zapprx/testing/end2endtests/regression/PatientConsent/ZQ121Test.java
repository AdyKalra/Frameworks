package com.zapprx.testing.end2endtests.regression.PatientConsent;

/**
 Step 1. Create a new patient
 Step 2. Select the patient from the patient list
 Step 3. Click on patient consent.
 Step 4. Select indication and drug
 Step 5. Verify that you are able to click on "Previous" button and that it takes you back to the Indication/Drug page
 Step 6. Verify that you are able to change the indication and the drug list changes accordingly
 Step 7. Select the indication/drug, and verify that you are able to sign and save
 Step 8. Verify that "Continue" modal is displayed and click on Continue
 Step 9. Verify that you are on the Rx Status page and the Rx has a status of "Patient Signed" in the "Progress" column

 */

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

public class ZQ121Test extends BaseTest{
	String[] patientLastNameDOBAndGender = null;
	
	private ZQ121Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(
				getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow
				.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(
				getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"),
				getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
		test.patientCommonWorkflow
				.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
	}

	@Test
	public void Step02_patientSignedUsingPatientConsent() {
		test.patientPage.clickOnPatientConsent();
		test.patientCommonWorkflow
				.clickPatConsentAndSelectIndication_Medication(
						getYamlValue("physician.indication1.name"),
						getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.patientCommonWorkflow
				.verifyUserIsOnPatientConsentPageToAuthorize();
		test.authorizationPage.clickOnPreviousButton();
		test.patientCommonWorkflow
				.clickPatConsentAndSelectIndication_Medication(
						getYamlValue("physician.indication1.name"),
						getYamlValue("physician.indication1.medicines.medicine5.name"));
		test.authorizationPage.enterSignAndSave(patientLastNameDOBAndGender[2]);
		test.rxStatusPage.verifyPatientAndStatusOnRxStatusPage(
				patientLastNameDOBAndGender[0],
				getYamlValue("physician.rxStatus.patientSigned"));
	}
	
	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow
				.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}

}
