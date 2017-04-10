package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register new patient and
 * complete patient profile adding Primary, Secondary and Tertiary information.
 * Step 2: Prescribe 'Adcirca' medication to newly registered patient and verify
 * Primary Insurance is a required field by verifying the Insurance Error
 * Message on the modal.
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ288Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ288Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_loginPhysicianRegPatAddSecondaryTertiaryIns() {
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
		test.patientRegistrationPage.addSecondaryInsurancePlan(getYamlValue("physician.insuranceInfo.insuranceName3"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber3"),
				getYamlValue("physician.insuranceInfo.state1"));
		test.patientRegistrationPage.addTertiaryInsurancePlan(getYamlValue("physician.insuranceInfo.insuranceName4"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber4"),
				getYamlValue("physician.insuranceInfo.state1"));
		test.patientRegistrationPage.clickOnClinical();
		test.headerPage.verifyUserIsOnCorrectPage("Clinical Information");
		test.patientRegistrationPage.enterMedicalHistory(getYamlValue("physician.medicalHistory.knownAllergy1"));
		test.patientRegistrationPage.clickOnCompleteRegistration();
	}

	@Test
	public void Step02_prescribePatAndVerifyInsuranceMsg() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.chooseMedication(getYamlValue("physician.indication1.medicines.medicine1.name"),
				patientLastNameDOBAndGender[0]);
		test.patientDetailPage
				.selectPharmacy(getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"));
		test.patientDetailPage.clickOnSecondaryRadioBtn(getYamlValue("physician.insuranceInfo.insuranceName1"));
		test.patientDetailPage.clickOnDiagnosisStep();
		test.patientDetailPage.verifyInsuranceMessage(getYamlValue("physician.insuranceMsg"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}

}
