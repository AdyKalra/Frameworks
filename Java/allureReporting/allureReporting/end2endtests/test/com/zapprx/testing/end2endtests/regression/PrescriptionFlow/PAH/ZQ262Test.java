package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register a new patient and
 * verify 'Add Additional Medications' on clinical profile section. Step 2: Now
 * prescribe 'Adcirca' medication to newly registered patient and verify 'Add
 * Additional Medications' on Diagnosis step and then authorize the
 * prescription. Step 3: View patient profile and verify 'Add Additional
 * Medications' under clinical tab.
 * 
 * @author QAIT\priyankasingh
 */
public class ZQ262Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ262Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_verifyAddAdditionalMedOnCompletingPatientProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.enterGeneralInfoAndInsuranceInfo(
				getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
		test.headerPage.verifyUserIsOnCorrectPage("Clinical Information");
		test.patientRegistrationPage.verifyAdditionAndRemovalOfAdditionalMed(getYamlValue("physician.removeMedMsg"));
		test.patientRegistrationPage.clickOnCompleteRegistration();

	}

	@Test
	public void Step02_presPatAndVerifyAddAdditionalMedOnDiagnosisPage() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.chooseMedication(
				getYamlValue("physician.indication1.medicines.medicine1.name"), patientLastNameDOBAndGender[0]);
		test.patientDetailPage.enterPatientDetails(getYamlValue("physician.medicalHistory.knownAllergy1"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2]);
		test.patientDetailPage.clickOnDiagnosisStep();
		test.patientDetailPage.clickOnPatientProfileModal();
		test.diagnosisPage.enterDiagnosisDetails(getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.diagnosis.newDiagnosis"), getYamlValue("physician.otherDetails"));
		test.diagnosisPage.verifyAdditionAndRemovalOfAdditionalMed(getYamlValue("physician.removeMedMsg"));
		test.diagnosisPage.clickOnDosage();
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine1.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
	}

	@Test
	public void Step03_verifyAddAdditionalMedOnPatientProfile() {
		test.patientCommonWorkflow.clickPatientsSearchAndSelect(patientLastNameDOBAndGender[0]);
		test.patientPage.clickOnViewProfile();
		test.patientProfilePage.clickOnClinicalTab();
		test.patientProfilePage.clickOnClinicalProfile();
		test.patientProfilePage.clickOnEdit();
		test.patientProfilePage.verifyAdditionAndRemovalOfAdditionalMed(getYamlValue("physician.removeMedMsg"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
