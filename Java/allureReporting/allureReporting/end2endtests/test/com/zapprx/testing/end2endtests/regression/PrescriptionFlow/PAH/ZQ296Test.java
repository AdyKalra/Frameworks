package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register a new patient Step 2:
 * Now prescribe 'Remodulin IV' medication under PAH indication to newly
 * registered patient and on 'Dosage' page select multiple 'Dose' options and
 * then authorize the prescription. Step 3: Verify that selected 'Dose' options
 * on 'Dosage' page while prescribing, is appeared selected on prescription
 * details page
 * 
 * @author QAIT\priyankasingh
 *
 */

public class ZQ296Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ296Test(String baseUrl) {
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
	public void Step02_prescribePatientAndSelectMultipleDoseOptions() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine13.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine13.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine13.name"));
		test.dosageCommonWorkflow.clickOnDifferentDosageOptions(
				getYamlValue("physician.indication1.medicines.medicine13.name"),
				getYamlValue("physician.indication1.medicines.medicine13.Dosage.Dosage2"), "Dosage");
		test.dosageCommonWorkflow.clickOnDifferentDosageOptions(
				getYamlValue("physician.indication1.medicines.medicine13.name"),
				getYamlValue("physician.indication1.medicines.medicine13.Dosage.Dosage3"), "Dosage");
		test.dosageCommonWorkflow.clickOnDifferentDosageOptions(
				getYamlValue("physician.indication1.medicines.medicine13.name"),
				getYamlValue("physician.indication1.medicines.medicine13.Dosage.Dosage4"), "Dosage");
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine13.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
	}

	@Test
	public void Step03_verifyDoseOptionIsChecked() {
		test.presDetailsPage.expandPresDetails();
		test.presDetailsPage
				.verifyDosageOptionIsChecked(getYamlValue("physician.indication1.medicines.medicine13.Dosage.Dosage1"));
		test.presDetailsPage
				.verifyDosageOptionIsChecked(getYamlValue("physician.indication1.medicines.medicine13.Dosage.Dosage2"));
		test.presDetailsPage
				.verifyDosageOptionIsChecked(getYamlValue("physician.indication1.medicines.medicine13.Dosage.Dosage3"));
		test.presDetailsPage
				.verifyDosageOptionIsChecked(getYamlValue("physician.indication1.medicines.medicine13.Dosage.Dosage4"));

	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}

}
