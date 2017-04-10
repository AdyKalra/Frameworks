package com.zapprx.testing.end2endtests.regression.PatientConsent;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient. Step 2:
 * Now capture consent for the registered patient and complete its profile(add
 * MRN). Step 3: Now on Patients page, validate the Medical Record Number is
 * displayed for the required patient.
 * 
 * @author vivekdua
 *
 */
public class ZQ174Test extends BaseTest{
	String[] loginEmailAndMRN;
	String[] patientLastNameDOBAndGender = null;
	   
	private ZQ174Test(String baseUrl) {
		super("physician.baseUrl");
	}
	
	@Test
	public void Step01_registerPatient() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(
				getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow
				.registerPatient();
	}

	@Test
	public void Step02_captureConsentAndCompleteProfile() {
		test.patientRegistrationPage.clickOnCaptureConsent();
		test.headerPage.verifyHeaderText_PatientConsent("Choose an indication");
		test.indicationPage
				.selectIndicationForPatientConsent(getYamlValue("physician.indication1.name"));
		test.chooseMedicationPage
				.selectMedicationForPatientConsent(getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.authorizationPage.enterSignAndSave(patientLastNameDOBAndGender[2]);
		test.rxStatusPage.verifyPatientAndStatusOnRxStatusPage(
				patientLastNameDOBAndGender[0],
				getYamlValue("physician.rxStatus.patientSigned"));
		test.leftnavigationPage.clickOnPatients();
		test.patientPage.searchPatient(patientLastNameDOBAndGender[0]);
		test.patientPage.clickOnSearchIcon();
		test.patientPage.selectPatientByName(patientLastNameDOBAndGender[0]);
		test.patientPage.clickOnCompleteProfile();
		loginEmailAndMRN = test.patientCommonWorkflow.completeProfile(
				getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"),
				getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
		test.patientPage.clickOnHome();
	}

	@Test
	public void Step03_verifyMedicalRecordNumber() {
		test.leftnavigationPage.clickOnPatients();
		test.patientPage.searchPatient(patientLastNameDOBAndGender[0]);
		test.patientPage.verifyMRNNoIsDisplayed(loginEmailAndMRN[1]);
	}
	
	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow
				.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
