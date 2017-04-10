package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and
 * complete profile. Step 2: Verify patient name appears on Rx summary header on
 * indication page and medication page.
 * 
 * 
 * @author QAIT\priyankasingh
 */
public class RX2810Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private RX2810Test(String baseUrl) {
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
	public void Step02_verifyPatientNameOnRxSummaryHeader() {
		test.patientPage.clickOnPrescribe();
		test.headerPage.verifyHeaderText("Choose an indication");
		test.indicationPage.verifyPatientNameOnRxSummaryHeader(patientLastNameDOBAndGender[0]);
		test.indicationPage.selectIndication(getYamlValue("physician.indication1.name"));
		test.headerPage.verifyHeaderText("Choose a medication");
		test.chooseMedicationPage.verifyPatientNameOnRxSummaryHeader(patientLastNameDOBAndGender[0]);
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}