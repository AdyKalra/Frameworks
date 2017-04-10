package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register a new patient and
 * complete profile. Step 2: Now prescribe patient and verify on patient page
 * that Shipping Information is prefilled as Home Address.
 * 
 * @author QAIT\priyankasingh
 */
public class ZQ263Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;
	String[] homeAddress = null;

	private ZQ263Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.headerPage.verifyHeaderOfPage("New Patient Registration");
		test.patientRegistrationPage.enterLoginEmailId(getYamlValue("physician.firstName"));
		test.patientRegistrationPage.enterPasswordForPatientIdentification(getYamlValue("physician.patientPassword"),
				patientLastNameDOBAndGender[0]);
		homeAddress = test.patientRegistrationPage
				.enterInfoForHomeAddress(getYamlValue("physician.insuranceInfo.state5"));
		test.patientRegistrationPage.enterInfoForEmergencyContact();
		test.patientRegistrationPage.clickOnAddInsurance();
		test.headerPage.verifyUserIsOnCorrectPage("Insurance Information");
		test.patientRegistrationPage.enterInsuranceInfo(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
		test.patientRegistrationPage.clickOnClinical();
		test.headerPage.verifyUserIsOnCorrectPage("Clinical Information");
		test.patientRegistrationPage.clickOnCompleteRegistration();
	}

	@Test
	public void Step02_verifyShippingInfoIsSameAsHomeAddOnPatientPage() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.chooseMedication(getYamlValue("physician.indication1.medicines.medicine1.name"),
				patientLastNameDOBAndGender[0]);
		test.patientDetailPage.verifyShippingInfoIsSameAsHomeAddress(homeAddress);
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
