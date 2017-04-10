package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import com.zapprx.testing.end2endtests.basetests.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and
 * complete patient profile. Step 2: Physician prescribes medication to newly
 * registered patient. Step 3: Search patient and verify specialty pharmacy is
 * displayed as selected on earlier prescription and also verify user is able to
 * select any other specialty pharmacy and prescribe medication.
 * 
 * @author vivekdua
 */

public class ZQ229Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ229Test(String baseUrl) {
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
	public void Step02_prescribePatientAndVerifyConfModal() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine1.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine1.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
	}

	@Test
	public void Step03_presPatForDiffPharAndVerifySpecialtyPharmacy() {
		test.patientCommonWorkflow.searchPatientAndSelectIndication(patientLastNameDOBAndGender[0],
				getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.chooseMedication(
				getYamlValue("physician.indication1.medicines.medicine1.name"), patientLastNameDOBAndGender[0]);
		test.patientDetailPage.verifySpecialtyPharmacy(getYamlValue("physician.pharmacy"),
				getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.patientDetailPage
				.selectPharmacy(getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy2"));
		test.patientDetailPage.clickOnDiagnosisStep();
		test.diagnosisPage.enterDiagnosisDetailsAndClickDosage(
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.diagnosis.newDiagnosis"), getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine1.name"), getYamlValue("physician.password1"));
		test.authorizationPage.verifyAuthorizationConfirmationModal();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}

}
