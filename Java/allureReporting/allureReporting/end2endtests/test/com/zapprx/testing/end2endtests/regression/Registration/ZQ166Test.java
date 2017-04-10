package com.zapprx.testing.end2endtests.regression.Registration;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician and register a new patient, enter
 * password and then hide password and email address fields. Validate that user
 * navigates to Insurance Information page.
 **/

public class ZQ166Test extends BaseTest {
	String loginEmail;
	String[] patientLastNameDOBAndGender = null;

	private ZQ166Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_regPatWithoutEmailAndVerifyUserIsOnInsuranceInfo() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.headerPage.verifyHeaderOfPage("New Patient Registration");
		test.patientRegistrationPage.enterLoginEmailId(getYamlValue("physician.firstName"));
		test.patientRegistrationPage.enterPasswordForPatientIdentification(getYamlValue("physician.patientPassword"),
				patientLastNameDOBAndGender[0]);
		test.patientRegistrationPage.hideEmailPasswrdField();
		test.patientRegistrationPage.enterInfoForPatientStatistics();
		test.patientRegistrationPage.enterInfoForHomeAddress(getYamlValue("physician.generalInfo.state"));
		test.patientRegistrationPage.enterInfoForEmergencyContact();
		test.patientRegistrationPage.clickOnAddInsurance();
		test.headerPage.verifyUserIsOnCorrectPage("Insurance Information");
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
