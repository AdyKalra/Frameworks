package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated:
 * Step 1: Verify physician is on login page after launching the url and login into the application
 * Step 2: Register a new patient and verify all the mandatory fields
 * Step 3: Prescribe patient and verify all mandatory fields
 *
 * @author vivekdua
 */

/**
 * Disabling this test as the fix for this is implemented in the new unified
 * portal. Once we move over to the unified portal , this test will be enabled.
 */
@Test(groups = "TestExclude")
public class ZQ11Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ11Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_verifyPhysicianIsAbleToLogin() {
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
		test.loginPage.loginUser(getYamlValue("physician.emailId1"), getYamlValue("physician.password1"));
		test.headerPage.verifyUserIsOnHomepage(getYamlValue("physician.headerText"));
	}

	@Test
	public void Step02_verifyMandatoryFieldsOnRegisterPatient() {
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.verifyMandatoryFieldsOnGenPage();
		test.patientRegistrationPage.enterGeneralInfo(getYamlValue("physician.generalInfo.state"),
				getYamlValue("physician.patientPassword"), getYamlValue("physician.firstName"));
		test.patientRegistrationPage.verifyMandatoryFieldsOnInsuranceInfo();
		test.patientRegistrationPage.enterInsuranceInfo(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state"));
		test.patientRegistrationPage.verifyMandatoryFieldsOnMedicalHistory();
		test.patientRegistrationPage.enterMedicalHistory(getYamlValue("physician.medicalHistory.knownAllergy1"));
		test.patientRegistrationPage.clickOnCompleteRegistration();
	}

	@Test
	public void Step03_verifyMandatoryFieldsForPrescribePatient() {
		test.patientRegistrationPage.clickHomeOnRegistrationSuccess();
		test.homePage.clickOnPrescribe();
		test.patientPage.searchPatient(patientLastNameDOBAndGender[0]);
		test.patientPage.selectPatientByName(patientLastNameDOBAndGender[0]);
		test.patientPage.clickOnPrescribe();
		test.headerPage.verifyUserIsOnCorrectPage("Choose Indication");
		test.indicationPage.selectIndication(getYamlValue("physician.indications.indication1"));
		test.headerPage.verifyUserIsOnCorrectPage("Choose Medication");
		test.chooseMedicationPage.selectMedication(getYamlValue("physician.medications.medication1"));
		test.chooseMedicationPage.clickOnSkipStep();
		test.headerPage.verifyUserIsOnCorrectPage("Patient Details");
		test.patientDetailPage.verifyMandatoryFieldOnPatientDetails();
		test.patientDetailPage.enterPatientDetails(getYamlValue("physician.medicalHistory.knownAllergy"),
				getYamlValue("physician.pharmacyName"), patientLastNameDOBAndGender[3]);
		test.headerPage.verifyUserIsOnCorrectPage("Provider Details");
		test.providerDetailsPage.verifyMandatoryFieldProviderDetails();
		test.patientDetailPage.clickOnDiagnosisStep();
		test.headerPage.verifyUserIsOnCorrectPage("Diagnosis");
		test.diagnosisPage.verifyMandatoryFieldOnDiagnosis();
		test.diagnosisPage.enterDiagnosisDetailsAndClickDosage(
				getYamlValue("physician.primaryDiagnosis.Rheumatology.diagnosis1"),
				getYamlValue("physician.newDiagnosis"), getYamlValue("physician.otherDetails"));
		test.headerPage.verifyUserIsOnCorrectPage("Dosage");
		test.commonDosagePage.verifyMandatoryFieldsOnDosage();
		test.authorizationPage.enterPrescriberAuth(patientLastNameDOBAndGender[3], false,
				getYamlValue("physician.medications.medication1"));
		test.authorizationPage.enterPhysicianSavedSig(getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
