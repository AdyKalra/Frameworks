package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician and register new patient and add
 * pharmacy benefits and secondary insurance while registering Step 2: Physician
 * prescribes medication to newly registered patient,and validate group number
 * for pharmacy benefits
 * 
 * @author vivekdua
 */
public class ZQ148Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ148Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientToAddPharmacyBenefitsAndSecondaryInsurance() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.headerPage.verifyHeaderOfPage("New Patient Registration");
		test.patientRegistrationPage.enterGeneralInfo(getYamlValue("physician.generalInfo.state"),
				getYamlValue("physician.patientPassword"), getYamlValue("physician.firstName"));
		test.headerPage.verifyUserIsOnCorrectPage("Insurance Information");
		test.patientRegistrationPage.enterInsuranceInfo(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
		test.patientRegistrationPage.addSecondaryInsurancePlan(getYamlValue("physician.insuranceInfo.insuranceName3"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber3"),
				getYamlValue("physician.insuranceInfo.state1"));
		// Always add PBM details after adding secondary insurance. This ensures
		// that we have the correct IDs for the
		// elements.
		test.patientRegistrationPage.enterDetailsForPharmacyBenefits(
				getYamlValue("physician.insuranceInfo.insuranceName2"), getYamlValue("physician.insuranceInfo.state2"),
				getYamlValue("physician.insuranceInfo.binNumber"), getYamlValue("physician.insuranceInfo.pcnNumber"));
		test.patientRegistrationPage.clickOnClinical();
		test.headerPage.verifyUserIsOnCorrectPage("Clinical Information");
		test.patientRegistrationPage.enterMedicalHistory(getYamlValue("physician.medicalHistory.knownAllergy1"));
		test.patientRegistrationPage.clickOnCompleteRegistration();
	}

	@Test
	public void Step02_prescribePatientAndVerifyGroupNoForPharmacyBenefits() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine1.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine1.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.expandPresDetails();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
