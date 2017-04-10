package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step1: Login as physician to register a new patient Step 2:
 * Save Draft of patient's Rx and verify that medication is displayed correctly
 * under 'Prior And Current Medications' section on prescription details page
 * and then click on 'Edit' and verify that medication is displayed correctly
 * under 'Prior And Current Medications' section on diagnosis page. Step 3: Now
 * again prescribe patient and verify that on diagnosis page previously
 * prescribed medication is displayed under 'Prior And Current Medications'.
 * 
 * @author vivekdua
 *
 */
public class ZQ266Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ266Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
	}

	@Test
	public void Step02_saveRxDraftAndVerifyPriorAndCurrentMed() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.prescribePatient(getYamlValue("physician.indication1.medicines.medicine1.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.commonDosagePage.clickOnSaveDraft();
		test.commonDosagePage.clickOnFinishAndReview();
		test.presDetailsPage.expandPresDetails();
		test.presDetailsPage.verifyPriorAndCurrentMed(getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.presDetailsPage.clickOnEdit();
		test.patientDetailPage.clickOnDiagnosisStep();
		test.diagnosisPage.verifyPriorAndCurrentMed(getYamlValue("physician.indication1.medicines.medicine1.name"));
	}

	@Test
	public void Step03_againPrescribeAndVerifyPriorAndCurrentMed() {
		test.patientCommonWorkflow.clickPatientsSearchAndSelect(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow
				.clickPresOnPatientModalAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.chooseMedication(getYamlValue("physician.indication1.medicines.medicine2.name"),
				patientLastNameDOBAndGender[0]);
		test.patientDetailPage.enterPatientDetails(getYamlValue("physician.medicalHistory.knownAllergy1"),
				getYamlValue("physician.indication1.medicines.medicine2.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2]);
		test.patientDetailPage.clickOnDiagnosisStep();
		test.patientDetailPage.clickOnPatientProfileModal();
		test.diagnosisPage.verifyPriorAndCurrentMed(getYamlValue("physician.indication1.medicines.medicine1.name"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}