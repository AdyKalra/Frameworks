package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register new patient and
 * complete patient profile adding Pharmacy Benefits information. Step 2:
 * Prescribe 'Adcirca' medication to newly registered patient and verify that
 * 'Group Number' field under Pharmacy Benefits on Patient detail page is not
 * displayed.
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ286Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ286Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_loginPhysicianRegPatAddPharmacyBenefit() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.headerPage.verifyHeaderOfPage("New Patient Registration");
		test.patientRegistrationPage.enterGeneralInfo(getYamlValue("physician.generalInfo.state"),
				getYamlValue("physician.patientPassword"), getYamlValue("physician.firstName"));
		test.headerPage.verifyUserIsOnCorrectPage("Insurance Information");
		test.patientRegistrationPage.enterInsuranceInfo(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
		test.patientRegistrationPage.enterDetailsForPharmacyBenefits(
				getYamlValue("physician.insuranceInfo.insuranceName2"), getYamlValue("physician.insuranceInfo.state2"),
				getYamlValue("physician.insuranceInfo.binNumber"), getYamlValue("physician.insuranceInfo.pcnNumber"));
		test.patientRegistrationPage.clickOnClinical();
		test.headerPage.verifyUserIsOnCorrectPage("Clinical Information");
		test.patientRegistrationPage.clickOnCompleteRegistration();
	}

	@Test
	public void Step02_prescribePatientAndVerifyGrpNo() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.chooseMedication(getYamlValue("physician.indication1.medicines.medicine1.name"),
				patientLastNameDOBAndGender[0]);
		test.patientDetailPage.verifyGroupNumber(getYamlValue("physician.insuranceInfo.insuranceName2"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}

}
