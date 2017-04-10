package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.RA;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician and register new patient Step 2:
 * Physician prescribes medication to newly registered patient,and validates
 * user is unable to authorize without choosing an option under directions field
 * 
 * @author vivekdua
 */

@Test(groups = "TestExclude")
public class ZQ190Test extends BaseTest{
	String[] patientLastNameDOBAndGender = null;
	   
	private ZQ190Test(String baseUrl) {
		super("physician.baseUrl");
	}
	
	@Test
	public void Step01_loginPhysicianToRegisterPatient() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"),
				getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step02_presPatAndVerifyUserIsUnableToAuthorizeWithoutSelectingDirectionsField() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication2.name"));
		test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication2.medicines.medicine3.name"),
				getYamlValue("physician.indication2.diagnosis.diagnosis1"),
				getYamlValue("physician.indication2.medicines.medicine3.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.RADosagePage.verifyDirOptionsArePresentAndNotSelected();
		test.commonDosagePage.clickOnAuthorize();
		test.commonDosagePage.verifyUserIsUnableToAuthorize();
	}
	
	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow
				.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
