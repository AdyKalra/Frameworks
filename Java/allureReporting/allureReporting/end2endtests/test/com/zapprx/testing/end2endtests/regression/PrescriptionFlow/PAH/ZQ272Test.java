package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician and quick register new patient.
 * Step 2: Capture patient consent. Step 3: Select patient, click on Complete Rx
 * and verify that 'SSN' and 'MRN' field is 'not disabled' and then enter values
 * for SSN, MRN and patient details to complete profile ,click on diagnosis step
 * then go back and verify that values in 'SSN' and 'MRN' fields are correctly
 * saved.
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ272Test extends BaseTest {
	String[] patientLastNameDOBAndGender, ssnAndMrnNo = null;

	private ZQ272Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_loginPhysicianAndQuickRegisterPatient() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
	}

	@Test
	public void Step02_capturePatientConsent() {
		test.patientRegistrationPage.clickOnCaptureConsent();
		test.headerPage.verifyHeaderText_PatientConsent("Choose an indication");
		test.indicationPage.selectIndicationForPatientConsent(getYamlValue("physician.indication1.name"));
		test.chooseMedicationPage
				.selectMedicationForPatientConsent(getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.authorizationPage.enterSignAndSave(patientLastNameDOBAndGender[2]);
		test.rxStatusPage.verifyPatientAndStatusOnRxStatusPage(patientLastNameDOBAndGender[0],
				getYamlValue("physician.rxStatus.patientSigned"));
	}

	@Test
	public void Step03_presPatAndVerifySSNAndMRNNo() {
		test.rxStatusPage.selectPatientByName(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.clickOnCompleteRx();
		test.patientDetailPage.verifySSNAndMRNFieldIsNotDisabled();
		ssnAndMrnNo = test.patientDetailPage.enterSSNAndMRNdetails();
		test.patientDetailPage.enterPatientDetailsToCompleteProfile(getYamlValue("physician.generalInfo.state"),
				getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"),
				getYamlValue("physician.indication1.medicines.medicine2.pharmacy.pharmacy1"));
		test.patientDetailPage.clickOnDiagnosisStep();
		test.patientDetailPage.clickOnPatientProfileModal();
		test.diagnosisPage.verifyUserIsOnDiagnosisPage();
		test.diagnosisPage.clickOnPatient();
		test.patientDetailPage.verifySSNAndMRNdetails(ssnAndMrnNo[0], ssnAndMrnNo[1]);
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
