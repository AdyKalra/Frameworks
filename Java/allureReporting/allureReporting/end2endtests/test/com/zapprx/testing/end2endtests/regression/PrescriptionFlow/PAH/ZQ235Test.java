package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import com.zapprx.testing.end2endtests.basetests.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

/**
 * Steps Automated: Step 1: Login physician and create a Registered Nurse. Step
 * 2: Login as Registered Nurse to register a new patient and complete profile.
 * Step 3: Search newly created patient and capture patient consent. Step 4:
 * Select patient from 'Patient-signed but Incomplete tab' and prescribe patient
 * and verify that user is able to save draft of prescription.
 * 
 * @author vivekdua
 */

@Test
public class ZQ235Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;
	String[] doctorFirstNameAndEmailId = null;

	private ZQ235Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_createRegisteredNurse() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.headerPage.selectPracticeProfile();
		test.headerPage.verifyUserIsOnCorrectPage("ZappRx Practice Profile");
		test.practiceProfilePage.clickOnAddNewDoctor();
		doctorFirstNameAndEmailId = test.doctorProfilePage.enterDetailsToAddNewDoctor(
				getYamlValue("physician.generalInfo.state"), getYamlValue("physician.lastName"),
				getYamlValue("physician.patientPassword"), getYamlValue("physician.faxNo"));
		test.doctorProfilePage.enterClinicianRole(getYamlValue("physician.clinicianRole.clinicianRole1"));
		test.doctorProfilePage.saveVerifyAndClose(getYamlValue("physician.addDoctorMsg"),
				getYamlValue("physician.lastName"));
		test.headerPage.logOut();
	}

	@Test
	public void Step02_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyNewlyAddedDoctorLogin(doctorFirstNameAndEmailId[1],
				getYamlValue("physician.patientPassword"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step03_capturePatientConsent() {
		test.patientCommonWorkflow.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
		test.patientPage.clickOnPatientConsent();
		test.patientCommonWorkflow.clickPatConsentAndSelectIndication_Medication(
				getYamlValue("physician.indication1.name"),
				getYamlValue("physician.indication1.medicines.medicine5.name"));
		test.authorizationPage.enterSignAndSave(patientLastNameDOBAndGender[2]);
	}

	@Test
	public void Step04_prescribePatientAndVerifyUserIsAbleToSaveDraftOFPres() {
		test.leftnavigationPage.clickOnHome();
		test.homePage.clickOnPatientSigned();
		test.homePage.selectPatientFromPatientSigned(patientLastNameDOBAndGender[0]);
		test.patientDetailPage.selectPrescribingProvider(getYamlValue("physician.name1"));
		test.patientCommonWorkflow.presPatAfterSigningPatCon(getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine5.name"));
		test.commonDosagePage.clickOnSaveDraftForPres();
		test.commonDosagePage.clickOnFinishAndReview();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow.deleteDoctor(doctorFirstNameAndEmailId[0]);
	}
}
