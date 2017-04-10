package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

/**
 * Steps Automated:
 * Step 1: Login physician to register new patient
 * Step 2: Search the newly registered patient and prescribe medication
 * to save as a draft
 * Step 3: Verify Rx status and validate user is able to authorize
 */
import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

public class ZQ105Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ105Test(String baseUrl) {
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
	public void Step02_prescribePatientAndSavePresAsDraft() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.prescribePatient(getYamlValue("physician.indication1.medicines.medicine1.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.commonDosagePage.clickOnSaveDraft();
		test.commonDosagePage.clickOnCancelIcon();
	}

	@Test
	public void Step03_verifyRxStatusAndAttemptToAuthorize() {
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.headerPage.verifyUserIsOnCorrectPage("Rx Status");
		test.rxStatusPage.verifyProgressStatus(patientLastNameDOBAndGender[0],
				getYamlValue("physician.rxStatus.draft"));
		test.rxStatusPage.clickOnPatientName(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.clickOnViewRxDetails();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.verifyUserIsAbleToAuthorize();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
