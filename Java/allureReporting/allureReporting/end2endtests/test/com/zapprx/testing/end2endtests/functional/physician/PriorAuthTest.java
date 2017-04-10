package com.zapprx.testing.end2endtests.functional.physician;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and search
 * the newly created patient Step 2: Prescribe patient for all or any particular
 * medication and request prior authorization
 *
 * @author vivekdua
 */

/*
 * Excluding this test as Prior Authorization functionality is not working fine.
 * Error message is appearing instead of Prior Authorization form
 */
@Test(groups = "TestExclude")
public class PriorAuthTest extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private PriorAuthTest(String baseUrl) {
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

	@Test(dataProvider = "medicineProvider")
	public void Step02_prescribePatientAndPriorAuthForDiffMedication(String medicineName) {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine1.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine1.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthForPatientPage.userSelectFormAndSave();
		test.priorAuthForPatientPage.clickOnReviewPrescription();
		test.leftnavigationPage.clickOnHome();
		test.homePage.clickOnPrescribe();
		test.patientPage.searchPatient(patientLastNameDOBAndGender[0]);
		test.patientPage.selectPatientByName(patientLastNameDOBAndGender[0]);
		test.patientPage.clickPrescribeOnPatientModal();
		test.indicationPage.selectIndication(getYamlValue("physician.indication1.name"));
	}

	@DataProvider(name = "medicineProvider")
	public Object[][] getMedications() {
		return test.chooseMedicationPage.getAllMedicines(getYamlValue("physician.chooseMedication"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
