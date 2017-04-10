package com.zapprx.testing.end2endtests.regression.PatientProfile;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and search
 * the newly created patient Step 2: Physician uploads the file on patients
 * profile to verify file is successfully uploaded and hide the uploaded file to
 * verify that file is successfully hidden
 * 
 * @author vivekdua
 *
 */
public class ZQ20Test extends BaseTest{
	String[] patientLastNameDOBAndGender = null;
	   
	private ZQ20Test(String baseUrl) {
		super("physician.baseUrl");
	}
	
	@Test
	public void Step01_registerPatientAndCompleteProfile() {
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
	public void Step02_uploadFileAndRemove() {
		test.patientCommonWorkflow
				.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
		test.patientPage.clickOnViewProfile();
		test.patientProfilePage.clickOnClinicalTab();
		test.patientProfilePage.clickOnLabs();
		test.patientProfilePage.uploadFileAndRemove(
				getYamlValue("physician.medicalType"),
				getYamlValue("physician.fileName"));
		test.patientProfilePage.verifyUploadedFileIsNotDisplayed();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow
				.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
