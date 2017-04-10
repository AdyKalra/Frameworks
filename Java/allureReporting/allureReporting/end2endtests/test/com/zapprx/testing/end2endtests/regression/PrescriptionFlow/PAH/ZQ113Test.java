package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new male patient and
 * search the newly created patient Step 2: Physician prescribe Tracleer
 * medication Step 3: Login as patient and verify sign health share is displayed
 * 
 * @author vivekdua
 *
 */

public class ZQ113Test extends BaseTest {
	String[] loginEmailAndMRN;
	String gender = "Male";
	String[] patientLastNameDOBAndGender = null;

	private ZQ113Test(String baseUrl) {
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
	public void Step02_prescribeMedication_Tracleer() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine7.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine7.pharmacy.pharmacy1"), gender,
				patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.patientCommonWorkflow.authorizePres(gender, remsValue,
				getYamlValue("physician.indication1.medicines.medicine7.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.verifyDrugAndEmail(getYamlValue("physician.indication1.medicines.medicine7.name"),
				loginEmailAndMRN[0]);
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step03_loginPatientAndVerifySignHealthShareForTracleer() {
		test.patientCommonWorkflow.switchToPatientPortal(loginEmailAndMRN[0],
				getYamlValue("physician.patientPassword"));
		test.patientCommonWorkflow.verifyREMSSignOffIsDisplayed(
				getYamlValue("physician.indication1.medicines.medicine7.name"), patientLastNameDOBAndGender[2]);
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
