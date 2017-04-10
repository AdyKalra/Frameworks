package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician and quick register new patient.
 * Step 2: Capture patient consent and then click on Complete Rx and verify that
 * 'Primary Insurance' field is empty and red border appears.
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ270Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ270Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_loginPhysicianAndQuickRegisterPatient() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
	}

	@Test
	public void Step02_captPatConPresPatientAndVerifyPrimaryInsurance() {
		test.patientRegistrationPage.clickOnCaptureConsent();
		test.headerPage.verifyHeaderText_PatientConsent("Choose an indication");
		test.indicationPage.selectIndicationForPatientConsent(getYamlValue("physician.indication1.name"));
		test.chooseMedicationPage
				.selectMedicationForPatientConsent(getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.authorizationPage.enterSignAndSave(patientLastNameDOBAndGender[2]);
		test.rxStatusPage.verifyPatientAndStatusOnRxStatusPage(patientLastNameDOBAndGender[0],
				getYamlValue("physician.rxStatus.patientSigned"));
		test.rxStatusPage.selectPatientByName(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.clickOnCompleteRx();
		test.patientDetailPage.verifyPrimaryInsurance();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
