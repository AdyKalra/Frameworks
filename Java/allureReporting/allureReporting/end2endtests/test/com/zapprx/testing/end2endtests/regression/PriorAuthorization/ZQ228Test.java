package com.zapprx.testing.end2endtests.regression.PriorAuthorization;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and
 * complete patient profile. Step 2: Physician prescribes medication to newly
 * registered patient and verifies after clicking on print prior authorization
 * form is correctly displayed.
 * 
 * @author vivekdua
 */

public class ZQ228Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ228Test(String baseUrl) {
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
	public void Step02_presPatAndPriorAuthForDiffMedMassHealthInsurance(String medicineName) {
		test.headerPage.verifyHeaderText("Choose an indication");
		test.indicationPage.selectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(medicineName,
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(medicineName);
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue, medicineName,
				getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.enterSign();
		test.priorAuthorizationPage.clickOnPrint();
		test.priorAuthorizationPage.verifyFormTitleAndSwitchWindow(getYamlValue("physician.formTitle.title1"));
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