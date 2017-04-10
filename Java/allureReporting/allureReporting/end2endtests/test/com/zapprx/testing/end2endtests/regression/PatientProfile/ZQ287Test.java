package com.zapprx.testing.end2endtests.regression.PatientProfile;

/**
 * Steps Automated: Step 1: Login physician to register a new 
 * patient and verify addition and removal of 'Additional Symptoms'
 * on clinical profile section. Step 2: Now search and select 
 * patient and then click on view profile and verify
 * 'Additional Symptoms' on clinical profile under clinical tab.
 * 
 * @author QAIT\priyankasingh
 */

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

public class ZQ287Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ287Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndVerifyAdditionalSymptoms() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.headerPage.verifyHeaderOfPage("New Patient Registration");
		test.patientRegistrationPage.enterGeneralInfo(getYamlValue("physician.generalInfo.state"),
				getYamlValue("physician.patientPassword"), getYamlValue("physician.firstName"));
		test.headerPage.verifyUserIsOnCorrectPage("Insurance Information");
		test.patientRegistrationPage.enterInsuranceInfo(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
		test.patientRegistrationPage.clickOnClinical();
		test.headerPage.verifyUserIsOnCorrectPage("Clinical Information");
		test.patientRegistrationPage.enterMedicalHistory(getYamlValue("physician.medicalHistory.knownAllergy1"));
		test.patientRegistrationPage.enterClinicalProfileDetails(getYamlValue("physician.indication1.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"), getYamlValue("physician.otherDetails"));
		test.patientRegistrationPage
				.verifyAdditionAndRemovalOfAdditionalSymtoms(getYamlValue("physician.additionalSymptom.symptom1"));
		test.patientRegistrationPage.clickOnCompleteRegistration();
	}

	@Test
	public void Step02_onPatientProfileVerifyAdditionalSymptoms() {
		test.patientCommonWorkflow.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow.viewClinicalProfile();
		test.patientProfilePage.clickOnEdit();
		test.patientProfilePage
				.verifyAdditionAndRemovalOfAdditionalSymptoms(getYamlValue("physician.additionalSymptom.symptom1"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}

}
