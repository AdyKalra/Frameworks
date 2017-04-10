package com.zapprx.testing.end2endtests.regression.Registration;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician and register a new patient Step
 * 2: Switch to patient portal and login as newly registered patient with the
 * incorrect password to validate error message is being displayed
 */
public class ZQ132Test extends BaseTest{
	String [] loginEmailAndMRN;
	String[] patientLastNameDOBAndGender = null;
	   
	private ZQ132Test(String baseUrl) {
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
		loginEmailAndMRN = test.patientCommonWorkflow.completeProfile(
				getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"),
				getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step02_verifyErrorMsgWithIncorrectPassword() {
		test.openUrl(getYamlValue("patient.baseUrl"));
		test.loginPage.loginUser(loginEmailAndMRN[0],
				getYamlValue("physician.incorrectPassword"));
		test.loginPage
				.verifyErrorMsgForIncorrectPassword(getYamlValue("physician.loginErrorMsg"));
	}
	
	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow
				.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
