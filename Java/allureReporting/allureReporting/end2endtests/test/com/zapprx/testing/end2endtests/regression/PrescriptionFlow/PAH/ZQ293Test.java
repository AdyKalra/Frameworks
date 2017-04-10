package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and search
 * the newly created patient Step 2: Physician choose medication to prescribe
 * and click on previous button to validate user navigates back to the
 * medication page
 */
public class ZQ293Test extends BaseTest {
	String[] loginEmailAndMRN;
	String[] patientLastNameDOBAndGender = null;

	private ZQ293Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		loginEmailAndMRN = test.patientCommonWorkflow.completeProfile(
				getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step02_selectMedicationAndVerifyPageOnClickingPreviousButton() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.chooseMedication(getYamlValue("physician.indication1.medicines.medicine1.name"),
				patientLastNameDOBAndGender[0]);
		test.headerPage.verifyHeaderText("Complete " + getYamlValue("physician.indication1.medicines.medicine1.name")
				+ " script for " + getYamlValue("physician.firstName") + " " + patientLastNameDOBAndGender[0]);
		test.patientDetailPage.clickOnPreviousButton();
		test.headerPage.verifyHeaderText("Choose a medication");
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
