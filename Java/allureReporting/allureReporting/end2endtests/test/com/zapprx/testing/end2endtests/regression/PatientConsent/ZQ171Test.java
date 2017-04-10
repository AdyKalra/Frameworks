package com.zapprx.testing.end2endtests.regression.PatientConsent;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and
 * complete its profile Step 2: Physician prescribe letairis medication and capture
 * patient consent to validate REMS is not displayed
 * 
 * @author vivekdua
 *
 */
public class ZQ171Test extends BaseTest{
	String gender = "Male";
	boolean remsValue;
	String[] patientLastNameDOBAndGender = null;
	
	private ZQ171Test(String baseUrl) {
		super("physician.baseUrl");
	}

	
	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(
				getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow
				.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(
				getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"),
				getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step02_presMedToCapPatConsentAndVerifyREMSIsNotDisplayed() {
		test.patientCommonWorkflow
				.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.headerPage.verifyHeaderText("Choose a medication");
		test.chooseMedicationPage
				.selectMedication(getYamlValue("physician.indication1.medicines.medicine3.name"));
		test.chooseMedicationPage.clickOnSkipStep();
		test.headerPage
				.verifyHeaderText("Complete "
						+ getYamlValue("physician.indication1.medicines.medicine3.name")
						+ " script for " + getYamlValue("physician.firstName")
						+ " " + patientLastNameDOBAndGender[0]);
		test.patientDetailPage.enterPatientDetails(
				getYamlValue("physician.medicalHistory.knownAllergy1"),
				getYamlValue("physician.indication1.medicines.medicine3.pharmacy.pharmacy1"), gender);
		test.patientDetailPage.clickOnCaptPatientConsent();
		test.patientDetailPage.verifyREMSIsNotDisplayed();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow
				.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
