package com.zapprx.testing.end2endtests.regression.PriorAuthorization;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and enters
 * MassHealth as Primary insurance Step 2: Physician prescribe under MS
 * indication Step 3: Patient clicks on Print button Step 4: Fetch ids from HTML
 * Form and Prior Authorization Request Form to check common values in both the
 * forms
 */
@Test(groups = "TestExclude")
public class MassHealth_MS_PATest extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private MassHealth_MS_PATest(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
		test.patientPage.clickOnPrescribe();
	}

	@Test(dataProvider = "medicineProvider")
	public void Step02_prescribePatientAndPriorAuthForDiffMedication_MassHealth_Insurance(String medicineName) {
		test.headerPage.verifyHeaderText("Choose an indication");
		test.indicationPage.selectIndication(getYamlValue("physician.indication3.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(medicineName,
				getYamlValue("physician.indication3.diagnosis.diagnosis1"),
				getYamlValue("physician.indication3.medicines.medicine5.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0],
				getYamlValue("physician.indication3.medicines.medicine1.otherDetails"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue, medicineName,
				getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.getInputElements();
		test.priorAuthorizationPage.enterSign();
		test.priorAuthorizationPage.clickOnPrint();
		test.priorAuthorizationPage.verifyFormTitle(getYamlValue("physician.formTitle.title8"));
		test.priorAuthorizationPage.getInputElementsForm();
		test.priorAuthorizationPage.compareLists(medicineName);
		test.leftnavigationPage.clickOnPatients();
		test.patientPage.searchPatient(patientLastNameDOBAndGender[0]);
		test.patientPage.selectPatientByName(patientLastNameDOBAndGender[0]);
		test.patientPage.clickPrescribeOnPatientModal();
	}

	@DataProvider(name = "medicineProvider")
	public Object[][] getMedications() {
		return test.chooseMedicationPage.getMedicinesForMassHealth_MS();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}

}
