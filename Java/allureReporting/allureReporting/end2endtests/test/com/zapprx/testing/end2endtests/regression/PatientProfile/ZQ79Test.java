package com.zapprx.testing.end2endtests.regression.PatientProfile;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and search
 * the newly created patient Step 2: Physician view patient's profile, add a new
 * medication and verify that it is getting displayed only once
 * 
 * @author vivekdua
 *
 */
public class ZQ79Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ79Test(String baseUrl) {
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
	public void Step02_addMedicationAndVerify() {
		test.patientCommonWorkflow.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow.viewClinicalProfile();
		test.patientProfilePage.clickOnEdit();
		test.patientProfilePage.enterIndicationDetails(getYamlValue("physician.indication1.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),getYamlValue("physician.otherDetails"));
		test.patientProfilePage.clickOnAddMedication();
		test.patientProfilePage.addMedication(getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.patientProfilePage.saveProfileAndVerifyAddedMed(getYamlValue("physician.indication1.medicines.medicine1.name"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
