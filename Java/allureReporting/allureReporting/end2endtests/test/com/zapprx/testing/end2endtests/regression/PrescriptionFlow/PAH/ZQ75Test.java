package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register new patient and
 * validate it is getting displayed in patient's list Step 2: Prescribe
 * medication to newly registered patient and validate that user is unable to
 * enter non-integer value in Refills field on Dosage page
 * 
 * @author vivekdua
 */
public class ZQ75Test extends BaseTest{
	String[] patientLastNameDOBAndGender = null;
	   
	private ZQ75Test(String baseUrl) {
		super("physician.baseUrl");
	}
	
	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"),
				getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step02_prescribePatientAndVerifyRefillIsEmpty() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.prescribePatient(getYamlValue("physician.indication1.medicines.medicine4.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.PAHDosagePage.verifyUserIsUnableToEnterNonIntegerValue(getYamlValue("physician.refillValue"));
	}
	
	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow
				.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
