package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login with Dr. Lindsay Goldman physician, create a
 * registered nurse and delegate this newly created nurse as a signatory on
 * behalf of Dr. Lindsay Goldman. Step 2: Login with nurse and register a new
 * patient. Step 3: Prescribe patient and on authorization page verify master
 * doctoe signature(Dr. Lindsay Goldman) is displayed and authorize
 * prescription. Step 4: Login with Dr. Lindsay Goldman physician, create a
 * Doctor with all the permissions and delegate this newly created dostor as a
 * signatory on behalf of Dr. Lindsay Goldman. Step 5: Prescribe patient and on
 * authorization page verify master doctoe signature(Dr. Lindsay Goldman) is
 * displayed and authorize prescription.
 * 
 * @author QAIT\priyankasingh
 *
 */

public class ZQ314Test extends BaseTest {
	String[] patientLastNameDOBAndGender, nurseFirstNameAndEmailId,
			doctorFirstNameAndEmailId = patientLastNameDOBAndGender = nurseFirstNameAndEmailId = null;

	private ZQ314Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_createRegisteredNurseAndAssignSignatory() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.headerPage.selectPracticeProfile();
		test.headerPage.verifyUserIsOnCorrectPage("ZappRx Practice Profile");
		test.practiceProfilePage.clickOnAddNewDoctor();
		nurseFirstNameAndEmailId = test.doctorProfilePage.enterDetailsToAddNewDoctor(
				getYamlValue("physician.generalInfo.state"), getYamlValue("physician.lastName"),
				getYamlValue("physician.patientPassword"), getYamlValue("physician.faxNo"));
		test.doctorProfilePage.enterClinicianRole(getYamlValue("physician.clinicianRole.clinicianRole1"));
		test.doctorProfilePage.saveVerifyAndClose(getYamlValue("physician.addDoctorMsg"),
				getYamlValue("physician.lastName"));
		test.headerPage.selectMyProfile();
		test.myProfilePage.searchAndSelectUser(nurseFirstNameAndEmailId[0]);
		test.myProfilePage.markUserAsSignatory(nurseFirstNameAndEmailId[0], getYamlValue("physician.lastName"));
		test.headerPage.logOut();
	}

	@Test
	public void Step02_loginAsNurseAndregisterPatient() {
		test.patientCommonWorkflow.verifyNewlyAddedDoctorLogin(nurseFirstNameAndEmailId[1],
				getYamlValue("physician.patientPassword"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step03_prescribePatientAndVerifyMasterDoctorSig() {
		test.patientCommonWorkflow.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow
				.clickPresOnPatientModalAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.prescribePatientAndAssignPhysician(
				getYamlValue("physician.indication1.medicines.medicine1.name"), getYamlValue("physician.name1"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.commonDosagePage.clickOnAuthorize();
		test.authorizationPage.verifyMasterDoctorSignature(getYamlValue("physician.name1"));
		test.authorizationPage.clickOnSubmitButton();
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.verifyRxStatus(getYamlValue("physician.rxStatus.submitted"));
		test.headerPage.logOut();
	}

	@Test
	public void Step04_createDoctorAndAssignSignatory() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.headerPage.selectPracticeProfile();
		test.headerPage.verifyUserIsOnCorrectPage("ZappRx Practice Profile");
		test.practiceProfilePage.clickOnAddNewDoctor();
		doctorFirstNameAndEmailId = test.doctorProfilePage.enterDetailsToAddNewDoctor(
				getYamlValue("physician.generalInfo.state"), getYamlValue("physician.lastName"),
				getYamlValue("physician.patientPassword"), getYamlValue("physician.faxNo"));
		test.doctorProfilePage.enterClinicianRole(getYamlValue("physician.clinicianRole.clinicianRole3"));
		test.doctorProfilePage.assignPermissions();
		test.doctorProfilePage.saveVerifyAndClose(getYamlValue("physician.addDoctorMsg"),
				getYamlValue("physician.lastName"));
		test.headerPage.selectMyProfile();
		test.myProfilePage.searchAndSelectUser(doctorFirstNameAndEmailId[0]);
		test.myProfilePage.markUserAsSignatory(doctorFirstNameAndEmailId[0], getYamlValue("physician.lastName"));
		test.headerPage.logOut();
	}

	@Test
	public void Step05_loginAsNewDocPrescribePatAndVerifyMasterDoctorSig() {
		test.patientCommonWorkflow.verifyNewlyAddedDoctorLogin(doctorFirstNameAndEmailId[1],
				getYamlValue("physician.patientPassword"));
		test.patientCommonWorkflow.clickPatientsSearchAndSelect(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow
				.clickPresOnPatientModalAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.prescribePatientAndAssignPhysician(
				getYamlValue("physician.indication1.medicines.medicine1.name"), getYamlValue("physician.name1"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.commonDosagePage.clickOnAuthorize();
		test.authorizationPage.verifyMasterDoctorSignature(getYamlValue("physician.name1"));
		test.authorizationPage.clickOnSubmitButton();
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.verifyRxStatus(getYamlValue("physician.rxStatus.submitted"));
		test.headerPage.logOut();
	}

	@AfterClass
	public void delete_patient_and_doctor() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow.deleteDoctor(doctorFirstNameAndEmailId[0]);
		test.patientCommonWorkflow.deleteDoctor(nurseFirstNameAndEmailId[0]);
	}
}
