package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register a new patient and
 * verify height and weight fields while completing patient profile. Step 2:
 * Complete patient profile without filling height and weight fields, now
 * prescribe medication and on patient detail page(Rx step 1) verify height and
 * weight fields. Step 3: Now view patient profile and on medical section under
 * clinical tab verify height and weight fields.
 * 
 * @author QAIT\priyankasingh
 *
 */

public class ZQ328Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ328Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndVerifyHeightWeightFields() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientRegistrationPage.verifyHeightWeightReqFields();
		test.patientRegistrationPage.refreshPage();
	}

	@Test
	public void Step02_prescribePatientAndVerifyHeightWeightFields() {
		test.patientCommonWorkflow.clickPatientsSearchAndSelect(patientLastNameDOBAndGender[0]);
		test.patientPage.clickOnCompleteProfile();
		test.headerPage.verifyHeaderOfPage("New Patient Registration");
		test.patientRegistrationPage.enterGeneralInfoWithoutPatientStatistics(
				getYamlValue("physician.generalInfo.state"), getYamlValue("physician.patientPassword"),
				getYamlValue("physician.firstName"));
		test.headerPage.verifyUserIsOnCorrectPage("Insurance Information");
		test.patientRegistrationPage.enterInsuranceInfo(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
		test.patientRegistrationPage.clickOnClinical();
		test.patientRegistrationPage.clickOnCompleteRegistration();
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.chooseMedication(getYamlValue("physician.indication1.medicines.medicine1.name"),
				patientLastNameDOBAndGender[0]);
		test.patientDetailPage.verifyHeightWeightReqFields();
		test.patientDetailPage.refreshPage();
	}

	@Test
	public void Step03_viewPatientProfileAndVerifyHeightWeightFields() {
		test.patientCommonWorkflow.clickPatientsSearchAndSelect(patientLastNameDOBAndGender[0]);
		test.patientPage.clickOnViewProfile();
		test.patientProfilePage.clickOnClinicalTab();
		test.patientProfilePage.clickOnEdit();
		test.patientProfilePage.clickOnMedical();
		test.patientProfilePage.verifyHeightWeightReqFields();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}

}