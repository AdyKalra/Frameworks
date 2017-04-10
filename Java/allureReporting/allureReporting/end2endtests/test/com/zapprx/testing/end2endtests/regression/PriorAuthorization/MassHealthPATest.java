package com.zapprx.testing.end2endtests.regression.PriorAuthorization;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and enters
 * Mass Health as Primary insurance Step 2: Physician prescribe under PAH
 * indication Step 3: Patient clicks on Print button Step 4: Fetch ids from HTML
 * Form and Prior Authorization Request Form to check common values in both the
 * forms
 */
@Test(groups = "TestExclude")
public class MassHealthPATest extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private MassHealthPATest(String baseUrl) {
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
		test.indicationPage.selectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(medicineName,
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue, medicineName,
				getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.patientCommonWorkflow.fetchListAndCompare(medicineName, getYamlValue("physician.formTitle.title1"));
		test.leftnavigationPage.clickOnPatients();
		test.patientPage.searchPatient(patientLastNameDOBAndGender[0]);
		test.patientPage.selectPatientByName(patientLastNameDOBAndGender[0]);
		test.patientPage.clickPrescribeOnPatientModal();
	}

	@DataProvider(name = "medicineProvider")
	public Object[][] getMedications() {
		return test.chooseMedicationPage.getMedicines(
				getYamlValue("physician.indication1.medicines.medicine1.name"),
				getYamlValue("physician.indication1.medicines.medicine2.name"),
				getYamlValue("physician.indication1.medicines.medicine3.name"),
				getYamlValue("physician.indication1.medicines.medicine4.name"),
				getYamlValue("physician.indication1.medicines.medicine5.name"),
				getYamlValue("physician.indication1.medicines.medicine6.name"),
				getYamlValue("physician.indication1.medicines.medicine7.name"),
				getYamlValue("physician.indication1.medicines.medicine10.name"),
				getYamlValue("physician.indication1.medicines.medicine15.name"),
				getYamlValue("physician.indication1.medicines.medicine9.name"),
				getYamlValue("physician.indication1.medicines.medicine11.name"),
				getYamlValue("physician.indication1.medicines.medicine13.name"),
				getYamlValue("physician.indication1.medicines.medicine14.name"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
