package com.zapprx.testing.end2endtests.regression.Registration;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register a new patient and
 * validate Tetrinary Insurance is displayed on Insurance page
 * 
 * @author vivekdua
 *
 */
public class ZQ212Test extends BaseTest {
	String updatedDateOfBirth;
	String[] loginEmailAndMRN;
	String[] patientLastNameDOBAndGender = null;

	private ZQ212Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndVerifyTetrinaryIns() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.headerPage.verifyHeaderOfPage("New Patient Registration");
		test.patientRegistrationPage.enterGeneralInfo(getYamlValue("physician.generalInfo.state"),
				getYamlValue("physician.patientPassword"), getYamlValue("physician.firstName"));
		test.headerPage.verifyUserIsOnCorrectPage("Insurance Information");
		test.patientRegistrationPage.clickOnAddAnotherInsurancePlan();
		test.patientRegistrationPage.clickOnAddAnotherInsurancePlan();
		test.patientRegistrationPage
				.verifyInsurFormHdrIsDisplayed(getYamlValue("physician.insuranceFormHeader.tertiary"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
