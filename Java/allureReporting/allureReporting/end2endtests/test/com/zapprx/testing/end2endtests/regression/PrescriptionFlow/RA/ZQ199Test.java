package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.RA;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register a new patient and
 * validate newly created patient is displayed in Patient's list. Step 2: Now
 * prescribe Enbrel medication to newly registered patient and select other
 * option under directions on dosage page and validate user is able to
 * authorize.
 * 
 * @author vivekdua
 *
 */
public class ZQ199Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;
	String medication, diagnosis;
	boolean remsValue;

	private ZQ199Test(String baseUrl) {
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
	public void Step02_prescribeMedicationSelectOtherAndVerifyUserAbleToAuthorize() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication2.name"));
		remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication2.medicines.medicine4.name"),
				getYamlValue("physician.indication2.diagnosis.diagnosis1"),
				getYamlValue("physician.indication2.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication2.medicines.medicine4.name"));
		test.RADosagePage.editRefill(getYamlValue("physician.refillInt"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication2.medicines.medicine4.name"), getYamlValue("physician.password1"));
		test.authorizationPage.verifyAuthorizationConfirmationModal();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
