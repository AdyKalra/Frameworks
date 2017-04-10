package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.MS;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register new patient and
 * complete patient's profile. Step 2: Prescribe patient and verify diagnosis
 * value is selected by default
 * 
 * @author vivekdua
 */

public class ZQ273Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ273Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailPA"),
				getYamlValue("physician.passwordPA"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
	}

	@Test
	public void Step02_presribePatAndVerifyDiagnosisValue() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication3.name"));
		test.patientCommonWorkflow.chooseMedication(getYamlValue("physician.indication3.medicines.medicine6.name"),
				patientLastNameDOBAndGender[0]);
		test.patientDetailPage.enterPatientDetails(getYamlValue("physician.medicalHistory.knownAllergy1"),
				getYamlValue("physician.indication3.medicines.medicine6.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2]);
		test.patientDetailPage.clickOnDiagnosisStep();
		test.patientDetailPage.clickOnPatientProfileModal();
		test.diagnosisPage.verifyPrimaryDiagnosis(getYamlValue("physician.indication3.medicines.medicine1.diagnosis"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
