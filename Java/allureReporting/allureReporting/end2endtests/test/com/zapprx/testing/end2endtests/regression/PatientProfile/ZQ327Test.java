package com.zapprx.testing.end2endtests.regression.PatientProfile;

/**
 * Steps Automated:
 * Step 1: Login physician to register a new patient and complete profile
 * Step 2: Physician view the patient's profile, edit the fields under Insurance tab to verify Add PBM and click on Cancel and again on edit to verify Add PBM is still getting displayed
 */
import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

public class ZQ327Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ327Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step02_verifyAddPBMAfterCancel() {
		test.patientCommonWorkflow.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
		test.patientPage.clickOnViewProfile();
		test.patientProfilePage.clickOnInsuranceTab();
		test.patientProfilePage.clickOnEdit();
		test.patientProfilePage.verifyAddPharmacyBenefit();
		test.patientProfilePage.clickOnCancelButton();
		test.patientProfilePage.clickOnEdit();
		test.patientProfilePage.verifyAddPharmacyBenefit();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
