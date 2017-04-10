package com.zapprx.testing.end2endtests.regression.PatientProfile;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician and register new patient and add
 * pharmacy benefits and primary insurance while registering and logout. Step 2:
 * Login as patient and switch to profile page and verify patient get option to
 * add 'Secondary Insurance'
 * 
 * @author vivekdua
 */

public class RX1792Test extends BaseTest {
	String[] loginEmailAndMRN;
	String[] patientLastNameDOBAndGender = null;

	private RX1792Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.headerPage.verifyHeaderOfPage("New Patient Registration");
		loginEmailAndMRN = test.patientRegistrationPage.enterGeneralInfo(getYamlValue("physician.generalInfo.state"),
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
		test.patientRegistrationPage.clickHomeOnRegistrationSuccess();
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step02_loginPatientAndVerifySecondaryInsuranceOption() {
		test.patientCommonWorkflow.switchToPatientPortal(loginEmailAndMRN[0],
				getYamlValue("physician.patientPassword"));
		test.headerPage.verifyUserIsOnHomepage(getYamlValue("patient.headerText"));
		test.leftnavigationPage.clickOnProfile();
		test.profilePage.clickOnInsurance();
		test.profilePage.verifySecondaryInsuranceOption();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
