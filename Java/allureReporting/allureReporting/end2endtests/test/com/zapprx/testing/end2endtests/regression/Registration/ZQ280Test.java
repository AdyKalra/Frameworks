package com.zapprx.testing.end2endtests.regression.Registration;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician and register new patient. Step 2:
 * Click on 'Complete Profile' and on 'General' page verify that 'Contact Name'
 * under 'Home Address' is prefilled with patient name
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ280Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ280Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_loginPhysicianAndRegisterPatient() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
	}

	@Test
	public void Step02_verifyContactNameOnGeneralPage() {
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.headerPage.verifyHeaderOfPage("New Patient Registration");
		test.patientRegistrationPage.verifyContactName(getYamlValue("physician.firstName"),
				patientLastNameDOBAndGender[0]);
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
