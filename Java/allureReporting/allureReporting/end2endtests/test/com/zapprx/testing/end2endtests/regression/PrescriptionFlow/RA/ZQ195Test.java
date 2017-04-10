package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.RA;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register a new patient and
 * validate newly created patient is displayed in Patient's list. Step 2: Now
 * prescribe Actrema IV medication to newly registered patient and verify
 * directions is a required field and user is able to authorize
 * 
 * @author vivekdua
 *
 */

@Test(groups = "TestExclude")
public class ZQ195Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;
	String medication;
	boolean remsValue;

	private ZQ195Test(String baseUrl) {
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
	public void Step02_presMedVerifyDirIsReqFieldAndUserIsAbleToAuthorize() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication2.name"));
		remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication2.medicines.medicine5.name"),
				getYamlValue("physician.indication2.diagnosis.diagnosis1"),
				getYamlValue("physician.indication2.medicines.medicine5.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.RADosagePage.verifyDirectionIsRequiredField();
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication2.medicines.medicine5.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication2.medicines.medicine5.name"), getYamlValue("physician.password1"));
		test.authorizationPage.verifyAuthorizationConfirmationModal();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}