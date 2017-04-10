package com.zapprx.testing.end2endtests.regression.PARx;

/**
 * Steps Automated: 
 * Step 1: Login physician to register new patient and complete patient profile. 
 * Step 2: Sign patient consent for Ampyra medication
 * Step 3: Now complete Rx for Ampyra medication and verify that user is able to authorize
 * 
 * @author vivekdua
 */

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

public class RX2203Test extends BaseTest {
	boolean remsValue;
	String[] patientLastNameDOBAndGender = null;

	private RX2203Test(String baseUrl) {
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
		test.patientCommonWorkflow.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
	}

	@Test
	public void Step02_signPatientConsentForAmpyra() {
		test.patientPage.clickOnPatientConsent();
		remsValue = test.patientCommonWorkflow.clickPatConsentAndSelectIndication_Medication(
				getYamlValue("physician.indication3.name"),
				getYamlValue("physician.indication3.medicines.medicine3.name"));
		test.patientCommonWorkflow.verifyUserIsOnPatientConsentPageToAuthorize();
		test.authorizationPage.enterSignAndSave(patientLastNameDOBAndGender[2]);
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.clickOnCompleteRx();
	}

	@Test
	public void Step03_prescribePatientAndVerifyUserIsAbleToAuthorize() {
		test.patientCommonWorkflow.presPatAfterSigningPatCon(getYamlValue("physician.indication3.diagnosis.diagnosis1"),
				getYamlValue("physician.indication3.medicines.medicine3.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], getYamlValue("physician.indication3.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication3.medicines.medicine3.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication3.medicines.medicine3.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}

}
