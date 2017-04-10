package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.MS;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register new patient and
 * complete patient's profile adding clinical information i.e. select 'Multiple
 * Sclerosis' as indication and 'Relapsing/remitting' as Other Details. Step 2:
 * Now prescribe patient 'Tecfidera' medication under MS indication and on
 * Diagnosis page verify that 'Other Details' value is selected as
 * 'Relapsing/remitting'
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ284Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ284Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailPA"),
				getYamlValue("physician.passwordPA"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.enterGeneralInfoAndInsuranceInfo(
				getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
		test.headerPage.verifyUserIsOnCorrectPage("Clinical Information");
		test.patientRegistrationPage.enterMedicalHistory(getYamlValue("physician.medicalHistory.knownAllergy1"));
		test.patientRegistrationPage.selectIndication(getYamlValue("physician.indication3.name"));
		test.patientRegistrationPage.selectOtherDetails(getYamlValue("physician.indication3.otherDetails"));
		test.patientRegistrationPage.clickOnCompleteRegistration();
	}

	@Test
	public void Step02_prescribePatientAndVerifyOtherDetailsValue() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication3.name"));
		test.patientCommonWorkflow.chooseMedication(getYamlValue("physician.indication3.medicines.medicine6.name"),
				patientLastNameDOBAndGender[0]);
		test.patientDetailPage.enterPatientDetails(getYamlValue("physician.medicalHistory.knownAllergy1"),
				getYamlValue("physician.indication3.medicines.medicine6.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2]);
		test.patientDetailPage.clickOnDiagnosisStep();
		test.patientDetailPage.clickOnPatientProfileModal();
		test.diagnosisPage.verifyOtherDetails(getYamlValue("physician.indication3.otherDetails"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
