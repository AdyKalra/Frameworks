package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician and register new patient. Step 2:
 * Now prescribe 'Opsumit' medication and verify that 'Compassionate Use'
 * checkbox is not displayed on 'Patient' page while it is displayed on 'Dosage'
 * page.
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ271Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ271Test(String baseUrl) {
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
	public void Step02_prescribePatientAndVerifyCompassionateUse() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.chooseMedication(getYamlValue("physician.indication1.medicines.medicine4.name"),
				patientLastNameDOBAndGender[0]);
		test.patientDetailPage.enterPatientDetails(getYamlValue("physician.medicalHistory.knownAllergy1"),
				getYamlValue("physician.indication1.medicines.medicine4.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2]);
		test.patientDetailPage.verifyOpsumitCompassionateUse();
		test.patientDetailPage.clickOnDiagnosisStep();
		test.patientDetailPage.clickOnPatientProfileModal();
		test.diagnosisPage.enterDiagnosisDetailsAndClickDosage(
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.diagnosis.newDiagnosis"), getYamlValue("physician.otherDetails"));
		test.PAHDosagePage.verifyOpsumitCompassionateUse();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
