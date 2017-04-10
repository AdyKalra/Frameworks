package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register a new patient Step 2:
 * Now prescribe 'Adempas' medication to newly registered patient and on
 * 'Dosage' page select both the 'Initial dose' options and then authorize the
 * prescription. Step 3. Again prescribe 'Letairis' medication to patient and on
 * 'Dosage' page select both the 'Dosage' options and then authorize the
 * prescription
 * 
 * @author QAIT\priyankasingh
 *
 */
public class ZQ289Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ289Test(String baseUrl) {
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
	}

	@Test
	public void Step02_prescribeAdempasAndSelectBothInitialDoses() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine2.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.medicines.medicine2.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine2.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine2.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.expandPresDetails();
		test.presDetailsPage
				.verifyDosageOptionIsChecked(getYamlValue("physician.indication1.medicines.medicine2.doses.dose1"));
		test.presDetailsPage
				.verifyDosageOptionIsChecked(getYamlValue("physician.indication1.medicines.medicine2.doses.dose2"));
	}

	@Test
	public void Step03_prescribeLetairisAndSelectBothDosageValue() {
		test.patientCommonWorkflow.clickPatientsSearchAndSelect(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow
				.clickPresOnPatientModalAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine3.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.medicines.medicine3.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine3.name"));
		test.dosageCommonWorkflow.clickOnDifferentDosageOptions(
				getYamlValue("physician.indication1.medicines.medicine3.name"),
				getYamlValue("physician.indication1.medicines.medicine3.Dosage.Dosage2"), "Dosage");
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine3.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.expandPresDetails();
		test.presDetailsPage
				.verifyDosageOptionIsChecked(getYamlValue("physician.indication1.medicines.medicine3.Dosage.Dosage1"));
		test.presDetailsPage
				.verifyDosageOptionIsChecked(getYamlValue("physician.indication1.medicines.medicine3.Dosage.Dosage2"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
