package com.zapprx.testing.end2endtests.regression.PatientConsent;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and search
 * Step 2: Physician prescribe medication keeping assigning to itself Step 3:
 * Verify user is able to capture patient consent(i.e Capture patient consent
 * link is available on Prescription details page) then authorize the
 * prescription Step 4: Once authorized, capture the patient consent Step 5:
 * Physician again prescribe medication to patient and choose another physician
 * as the assigned physician Step 6: Verify the Rx status as draft and capture
 * the patient consent
 * 
 * @author vivekdua
 *
 */
public class ZQ134Test extends BaseTest {
	boolean remsValue;
	String[] patientLastNameDOBAndGender = null;

	private ZQ134Test(String baseUrl) {
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
	public void Step02_presWithSamePhysicianAndSaveAsDraft() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine2.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.commonDosagePage.clickOnSaveDraft();
		test.commonDosagePage.clickOnFinishAndReview();
	}

	@Test
	public void Step03_verifyPatientConsentAndAuthorize() {
		test.presDetailsPage.verifyRxStatus(getYamlValue("physician.rxStatus.draft"));
		test.presDetailsPage.verifyPatientConsentBeforeSign();
		test.presDetailsPage.enterPrescriberAuth(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.presDetailsPage.enterSignAndAuthorizePres();
		test.presDetailsPage.clickOnPatientConsent();
		test.presDetailsPage.capturePatientConsent(patientLastNameDOBAndGender[2],
				getYamlValue("physician.indication1.medicines.medicine2.name"));
	}

	@Test
	public void Step04_presWithDiffPhysicianAndSaveAsDraft() {
		test.leftnavigationPage.clickOnPatients();
		test.patientPage.searchPatient(patientLastNameDOBAndGender[0]);
		test.patientPage.selectPatientByName(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow
				.clickPresOnPatientModalAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.prescribePatientAndAssignPhysician(
				getYamlValue("physician.indication1.medicines.medicine2.name"), getYamlValue("physician.name2"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.commonDosagePage.clickOnSaveDraftForPres();
		test.commonDosagePage.clickOnFinishAndReview();
	}

	@Test
	public void Step05_verifyStatusAndCapturePatientConsent() {
		test.presDetailsPage.verifyRxStatus(getYamlValue("physician.rxStatus.draft"));
		test.presDetailsPage.clickOnPatientConsent();
		test.presDetailsPage.capturePatientConsent(patientLastNameDOBAndGender[2],
				getYamlValue("physician.indication1.medicines.medicine2.name"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}