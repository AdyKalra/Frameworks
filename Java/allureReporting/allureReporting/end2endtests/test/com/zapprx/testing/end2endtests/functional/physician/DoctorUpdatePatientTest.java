package com.zapprx.testing.end2endtests.functional.physician;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and search
 * the newly created patient Step 2: Update the address in Profile and verify
 * the updated address
 * 
 * @author vivekdua
 *
 */
public class DoctorUpdatePatientTest extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private DoctorUpdatePatientTest(String baseUrl) {
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
	public void Step02_searchPatientToUpdateAddressAndVerify() {
		test.patientCommonWorkflow
				.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
		test.patientPage.clickOnViewProfile();
		test.patientProfilePage
				.editAddressAndSaveProfile(getYamlValue("physician.generalInfo.address"));
		test.patientProfilePage
				.verifyUpdatedAddress(getYamlValue("physician.generalInfo.address"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow
				.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
