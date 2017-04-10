package com.zapprx.testing.end2endtests.regression.Registration;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register new patient and
 * validate success modal is displayed after Step1
 * 
 * @author vivekdua
 */
public class ZQ59Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ59Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientToVerify() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		String loginEmail = test.patientRegistrationPage.enterLoginEmailId(getYamlValue("physician.firstName"));
		test.patientRegistrationPage.enterPasswordForPatientIdentification(getYamlValue("physician.patientPassword"),
				patientLastNameDOBAndGender[0]);
		test.patientRegistrationPage.enterInfoForPatientStatistics();
		test.patientRegistrationPage.enterInfoForHomeAddress(getYamlValue("physician.generalInfo.state"));
		test.patientRegistrationPage.enterInfoForEmergencyContact();
		test.patientRegistrationPage.clickOnNextButtonForPatientInfo();
		test.headerPage.verifyUserIsOnCorrectPage("Insurance Information");
		test.patientRegistrationPage.clickOnStep1General();
		test.patientRegistrationPage.verifyValuesAreRetained(loginEmail);
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
