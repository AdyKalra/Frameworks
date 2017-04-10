package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to add a new doctor under practice
 * profile Step 2: Login with the newly added doctor to register a patient and
 * prescribe medication Step 3: Validate that user has an option to save
 * prescription as draft on Dosage page
 * 
 * @author vivekdua
 *
 */
public class ZQ129Test extends BaseTest {
	String[] doctorFirstNameAndEmailId = null;
	String[] patientLastNameDOBAndGender = null;

	private ZQ129Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_addNewDoctorUnderPracProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.headerPage.selectPracticeProfile();
		test.headerPage.verifyUserIsOnCorrectPage("ZappRx Practice Profile");
		test.practiceProfilePage.clickOnAddNewDoctor();
		doctorFirstNameAndEmailId = test.doctorProfilePage.enterDetailsToAddNewDoctor(
				getYamlValue("physician.generalInfo.state"), getYamlValue("physician.lastName"),
				getYamlValue("physician.patientPassword"), getYamlValue("physician.faxNo"));
		test.doctorProfilePage.saveVerifyAndClose(getYamlValue("physician.addDoctorMsg"),
				getYamlValue("physician.lastName"));
		test.headerPage.logOut();
	}

	@Test
	public void Step02_loginNewlyAddedDoctorAndRegisterPatient() {
		test.patientCommonWorkflow.verifyNewlyAddedDoctorLogin(doctorFirstNameAndEmailId[1],
				getYamlValue("physician.patientPassword"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step03_prescribeMedicationAndVerifySaveDraft() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.prescribePatientAndAssignPhysician(
				getYamlValue("physician.indication1.medicines.medicine1.name"), getYamlValue("physician.name1"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], getYamlValue("physician.otherDetails"));
		test.commonDosagePage.verifySaveDraftForPresIsDisplayed();
	}

	@AfterClass
	public void delete_patient_and_doctor() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow.deleteDoctor(doctorFirstNameAndEmailId[0]);
	}
}
