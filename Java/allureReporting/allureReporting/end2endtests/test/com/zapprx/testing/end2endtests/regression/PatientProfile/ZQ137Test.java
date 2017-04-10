package com.zapprx.testing.end2endtests.regression.PatientProfile;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and enter
 * EMR Id while registration and set In-patient value to Yes Step 2: Physician
 * view the profile of newly registered patient and verifies the value of
 * In-Patient field
 * 
 * @author vivekdua
 *
 */
public class ZQ137Test extends BaseTest{
	String[] patientLastNameDOBAndGender = null;
	   
	private ZQ137Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientToAddEMRIdAndSearch() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(
				getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow
				.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(
				getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"),
				getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step02_verify_InPatientValue() {
		test.patientCommonWorkflow
				.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
		test.patientPage.clickOnViewProfile();
		test.patientProfilePage.clickOnClinicalTab();
		test.patientProfilePage
				.verifyInPatientValue(getYamlValue("physician.medicalHistory.in-patient"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow
				.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}