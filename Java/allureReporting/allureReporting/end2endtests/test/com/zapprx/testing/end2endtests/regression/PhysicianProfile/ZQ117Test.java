package com.zapprx.testing.end2endtests.regression.PhysicianProfile;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Add a new Doctor under practice profile Step 2:
 * Login with the newly created doctor and verify it is able to register a new
 * patient
 */
public class ZQ117Test extends BaseTest{
	String[] doctorFirstNameAndEmailId = null;
	String[] patientLastNameDOBAndGender = null;
	   
	private ZQ117Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_physicianLoginToAddNewDoctor() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(
				getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.headerPage.selectPracticeProfile();
		test.headerPage.verifyUserIsOnCorrectPage("ZappRx Practice Profile");
		test.practiceProfilePage.clickOnAddNewDoctor();
		doctorFirstNameAndEmailId = test.doctorProfilePage
				.enterDetailsToAddNewDoctor(
						getYamlValue("physician.generalInfo.state"),
						getYamlValue("physician.lastName"),
						getYamlValue("physician.patientPassword"),
						getYamlValue("physician.faxNo"));
		test.doctorProfilePage.saveVerifyAndClose(
				getYamlValue("physician.addDoctorMsg"),
				getYamlValue("physician.lastName"));
		test.headerPage.logOut();
	}

	@Test()
	public void Step02_verifyAddedDoctorIsAbleToRegisterNewpatient() {
		test.patientCommonWorkflow.verifyNewlyAddedDoctorLogin(
				doctorFirstNameAndEmailId[1],
				getYamlValue("physician.patientPassword"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow
				.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(
				getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"),
				getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
		test.patientCommonWorkflow
				.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
	}

	@AfterClass
	public void delete_patient_and_doctor() {
		test.patientCommonWorkflow
				.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow.deleteDoctor(doctorFirstNameAndEmailId[0]);
	}
}
