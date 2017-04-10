package com.zapprx.testing.end2endtests.regression.PatientProfile;

/**
 * Steps Automated:
 * Step 1: Login physician to register a new patient
 * Step 2: Physician view the patient's profile, edit the fields under medical tab and
 * verify that values are updated successfully 
 */
import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

public class ZQ147Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ147Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step02_editMedicalDetailsAndVerify() {
		test.patientCommonWorkflow.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
		test.patientPage.clickOnViewProfile();
		test.patientProfilePage.clickOnClinicalTab();
		test.patientProfilePage.clickOnEdit();
		test.patientProfilePage.clickOnMedical();
		test.patientProfilePage.editAllKnownAllergies(getYamlValue("physician.medicalHistory.knownAllergy2"));
		test.patientProfilePage.editWeight();
		test.patientProfilePage.editHeight();
		test.patientProfilePage.clickOnSaveProfile();
		test.patientProfilePage.verifyEditedFields(getYamlValue("physician.medicalHistory.knownAllergy2"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
