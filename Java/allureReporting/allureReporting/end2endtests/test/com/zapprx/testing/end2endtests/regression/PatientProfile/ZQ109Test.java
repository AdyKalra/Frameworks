package com.zapprx.testing.end2endtests.regression.PatientProfile;

/**
 * Steps Automated:
 * Step 1: Login physician to register a new patient and add
 * primary and secondary insurance information
 * Step 2: Physician validates primary and secondary insurance
 * information on patient profile
 */
import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

public class ZQ109Test extends BaseTest{
	String[] patientLastNameDOBAndGender = null;
	   
	private ZQ109Test(String baseUrl) {
		super("physician.baseUrl");
	}
	
	@Test
	public void Step01_regPatEnterInsInfoAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(
				getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow
				.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.headerPage.verifyHeaderOfPage("New Patient Registration");
		test.patientRegistrationPage.enterGeneralInfo(
				getYamlValue("physician.generalInfo.state"),
				getYamlValue("physician.patientPassword"),
				getYamlValue("physician.firstName"));
		test.headerPage.verifyUserIsOnCorrectPage("Insurance Information");
		test.patientRegistrationPage.enterInsuranceInfo(
				getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"),
				getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
		test.patientRegistrationPage.addSecondaryInsurancePlan(
				getYamlValue("physician.insuranceInfo.insuranceName2"),
				getYamlValue("physician.insuranceInfo.policyId1"),
				getYamlValue("physician.insuranceInfo.groupNumber2"),
				getYamlValue("physician.insuranceInfo.state2"));
		test.patientRegistrationPage.clickOnClinical();
		test.headerPage.verifyUserIsOnCorrectPage("Clinical Information");
		test.patientRegistrationPage
				.enterMedicalHistory(getYamlValue("physician.medicalHistory.knownAllergy1"));
		test.patientRegistrationPage.clickOnCompleteRegistration();
	}

	@Test
	public void Step02_verifyPrimaryAndSecondaryInsurance() {
		test.patientCommonWorkflow
				.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
		test.patientPage.clickOnViewProfile();
		test.patientProfilePage.clickOnInsuranceTab();
		test.patientProfilePage
				.verifyPrimaryInsurance(getYamlValue("physician.insuranceInfo.insuranceName1"));
		test.patientProfilePage
				.verifySecondaryInsurance(getYamlValue("physician.insuranceInfo.insuranceName2"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow
				.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}

}
