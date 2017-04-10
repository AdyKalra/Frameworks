package com.zapprx.testing.end2endtests.regression.Registration;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician and register a new patient. Step
 * 2: Validate the Date Of Birth is correct on Patient's list for the newly
 * registered patient
 **/
public class ZQ177Test extends BaseTest{
	String[] loginEmailAndMRN;
	String[] patientLastNameDOBAndGender = null;
	   
	private ZQ177Test(String baseUrl) {
		super("physician.baseUrl");
	}
	
	@Test
	public void Step01_registerPatient() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(
				getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow
				.registerPatient();
	}

	@Test
	public void Step02_verifyDateOfBirth() {
		test.patientRegistrationPage.clickOnPatientList();
		test.patientPage.searchPatient(patientLastNameDOBAndGender[0]);
		test.patientPage.verifyDateOfBirth(patientLastNameDOBAndGender[1],
				patientLastNameDOBAndGender[0]);
	}
	
	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow
				.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
