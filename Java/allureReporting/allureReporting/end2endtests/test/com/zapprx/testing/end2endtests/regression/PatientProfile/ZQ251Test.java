package com.zapprx.testing.end2endtests.regression.PatientProfile;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and search
 * the newly created patient Step 2: Verify addition and removal of Insurance
 * and Pharmacy Benefits
 * 
 * @author vivekdua
 *
 */
public class ZQ251Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ251Test(String baseUrl) {
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
		test.patientCommonWorkflow.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
	}

	@Test
	public void Step02_verifyAddAndRemoveOfInsurancePharmacy() {
		test.patientPage.clickOnViewProfile();
		test.patientProfilePage.clickOnInsuranceTab();
		test.patientProfilePage.clickOnEdit();
		test.patientProfilePage.clickOnAddInsurance();
		test.patientProfilePage.verifyAddAndRemoveInsurance(getYamlValue("physician.insuranceFormHeader.secondary"));
		test.patientProfilePage.clickOnAddPharmacyBenefit();
		test.patientProfilePage.verifyRemovePBMText(getYamlValue("physician.insuranceFormHeader.pharmacy"));
		test.patientProfilePage.verifyAddAndRemovePBM(getYamlValue("physician.insuranceFormHeader.pharmacy"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
