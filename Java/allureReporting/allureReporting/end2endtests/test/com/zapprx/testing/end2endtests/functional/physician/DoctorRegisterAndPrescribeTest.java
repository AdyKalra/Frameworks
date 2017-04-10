package com.zapprx.testing.end2endtests.functional.physician;

import com.zapprx.testing.end2endtests.basetests.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and search
 * the newly created patient Step 2: Prescribe medication to patient
 * 
 * @author vivekdua
 *
 */

public class DoctorRegisterAndPrescribeTest extends BaseTest {
	String successMessage;
	String[] patientLastNameDOBAndGender = null;
   
	private DoctorRegisterAndPrescribeTest(String baseUrl) {
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
	public void Step02_prescribePatient() {
		test.patientCommonWorkflow
				.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow
				.prescribePatient(
						getYamlValue("physician.indication1.medicines.medicine1.name"),
						getYamlValue("physician.indication1.diagnosis.diagnosis1"),
						getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
						patientLastNameDOBAndGender[2],
						patientLastNameDOBAndGender[0],
						getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions_PAH(getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.patientCommonWorkflow.authorizePres(
				patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine1.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow
				.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}

}
