package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.RA;

import com.zapprx.testing.end2endtests.basetests.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

/**
 * Steps Automated: Step 1: Login as physician and register new patient Step 2:
 * Physician prescribes medication to newly registered patient,and validates
 * user is unable to authorize if the dose field is left unchecked
 * 
 * @author vivekdua
 */
@Test(groups = "TestExclude")
public class ZQ189Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ189Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_loginPhysicianToRegisterPatient() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step02_presPatAndVerifyUserIsUnableToAuthorizeIfDosageFieldIsUnchecked() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication2.name"));
		test.patientCommonWorkflow.prescribePatient(getYamlValue("physician.indication2.medicines.medicine2.name"),
				getYamlValue("physician.indication2.diagnosis.diagnosis1"),
				getYamlValue("physician.indication2.medicines.medicine2.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.RADosagePage.verifyDoseFieldIsUnchecked();
		test.commonDosagePage.clickOnAuthorize();
		test.commonDosagePage.verifyUserIsUnableToAuthorize();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
