package com.zapprx.testing.end2endtests.regression.Registration;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician and register a new patient, and
 * complete its profile Step 2: Register another patient and enter the existing
 * email to validate error message appears on entering the value on patients
 * page.
 **/
public class ZQ180Test extends BaseTest {
	String[] loginEmailAndMRN;
	String[] patientLastNameDOBAndGender = null;

	private ZQ180Test(String baseUrl) {
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
		test.patientRegistrationPage.clickHomeOnRegistrationSuccess();
	}

	@Test
	public void Step02_registerAnotherPatientWithExistingEmailId() {
		test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientRegistrationPage.enterLoginEmailId(getYamlValue("physician.firstName"), loginEmailAndMRN[0]);
		test.patientRegistrationPage.enterPasswordForPatientIdentification(getYamlValue("physician.patientPassword"),
				patientLastNameDOBAndGender[0]);
		test.patientRegistrationPage.enterInfoForPatientStatistics();
		test.patientRegistrationPage.enterInfoForHomeAddress(getYamlValue("physician.generalInfo.state"));
		test.patientRegistrationPage.enterInfoForEmergencyContact();
		test.patientRegistrationPage.clickOnAddInsurance();
		test.patientRegistrationPage.verifyExistingEmailMsg(getYamlValue("physician.emailErrorMsg"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}