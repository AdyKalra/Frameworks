package com.zapprx.testing.end2endtests.regression.PARx;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician and create a Nurse Practioner. Step
 * 2: Login as Nurse Practioner to register a new patient and complete profile.
 * Step 3: Save draft of prescription and verify that Nurse is not able to
 * create PA. Step 4: Now Login as physician to register a new patient and
 * complete profile. Step 5: Save draft of prescription and verify that
 * Physician is not able to create PA.
 * 
 * @author vivekdua
 */
public class RX2212Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;
	String[] doctorFirstNameAndEmailId = null;

	private RX2212Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_createNursePractioner() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailPA"),
				getYamlValue("physician.passwordPA"));
		test.headerPage.selectPracticeProfile();
		test.headerPage.verifyUserIsOnCorrectPage("Neuromuscular Clinic Profile");
		test.practiceProfilePage.clickOnAddNewDoctor();
		doctorFirstNameAndEmailId = test.doctorProfilePage.enterDetailsToAddNewDoctor(
				getYamlValue("physician.generalInfo.state"), getYamlValue("physician.lastName"),
				getYamlValue("physician.patientPassword"), getYamlValue("physician.faxNo"));
		test.doctorProfilePage.enterClinicianRole(getYamlValue("physician.clinicianRole.clinicianRole2"));
		test.doctorProfilePage.saveVerifyAndClose(getYamlValue("physician.addDoctorMsg"),
				getYamlValue("physician.lastName"));
		test.headerPage.logOut();
	}

	@Test
	public void Step02_loginAsNurseAndregisterPatient() {
		test.patientCommonWorkflow.verifyNewlyAddedDoctorLogin(doctorFirstNameAndEmailId[1],
				getYamlValue("physician.patientPassword"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientCommonWorkflow.savePatientLastname(patientLastNameDOBAndGender[0]);
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step03_saveDraftOfPresAndVerifyNurseIsNotAbleToCreatePA() {
		test.patientCommonWorkflow.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
		test.patientPage.clickOnPatientConsent();
		test.patientCommonWorkflow.clickPatConsentAndSelectIndication_Medication(
				getYamlValue("physician.indication3.name"),
				getYamlValue("physician.indication3.medicines.medicine3.name"));
		test.patientCommonWorkflow.verifyUserIsOnPatientConsentPageToAuthorize();
		test.authorizationPage.enterSignAndSave(patientLastNameDOBAndGender[2]);
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.rxStatusPage.clickOnCompleteRx();
		test.patientDetailPage.selectPrescribingProvider(getYamlValue("physician.namePA"));
		test.patientCommonWorkflow.presPatAfterSigningPatCon(getYamlValue("physician.indication3.diagnosis.diagnosis1"),
				getYamlValue("physician.indication3.medicines.medicine3.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], getYamlValue("physician.indication3.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication3.medicines.medicine3.name"));
		test.commonDosagePage.clickOnSaveDraftForPres();
		test.commonDosagePage.clickOnFinishAndReview();
		test.presDetailsPage.verifyPriorAuthorizationLinkIsNotDisplayed();
		test.headerPage.logOut();
	}

	@Test
	public void Step04_loginAsPhysicianAndRegisterPatient() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailPA"),
				getYamlValue("physician.passwordPA"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientCommonWorkflow.savePatientLastname(patientLastNameDOBAndGender[0]);
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
		test.patientCommonWorkflow.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
	}

	@Test
	public void Step05_saveDraftOfPresAndVerifyPhysicianIsNotAbleToCreatePA() {
		test.patientCommonWorkflow.capturePatConAndPrescribePatient(getYamlValue("physician.indication3.name"),
				getYamlValue("physician.indication3.medicines.medicine3.name"),
				getYamlValue("physician.indication3.diagnosis.diagnosis1"),
				getYamlValue("physician.indication3.medicines.medicine3.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0],
				getYamlValue("physician.indication3.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication3.medicines.medicine3.name"));
		test.commonDosagePage.clickOnSaveDraft();
		test.commonDosagePage.clickOnFinishAndReview();
		test.presDetailsPage.verifyPriorAuthorizationLinkIsNotDisplayed();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.deletePatients();
		test.patientCommonWorkflow.deleteDoctor(doctorFirstNameAndEmailId[0]);
	}
}
