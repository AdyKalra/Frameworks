package com.zapprx.testing.end2endtests.regression.PARx;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step1: Login admin and select PA Workflow mode as 'Manual'
 * for 'ZappRx Practice'. Step 2: Login with Dr. Lindsay Goldman physician,
 * create a registered nurse and delegate this newly created nurse as a
 * signatory on behalf of Dr. Lindsay Goldman. Step 3: Login with nurse and
 * register a new patient. Step 4: Prescribe patient 'Flolan' medication and on
 * authorization page verify master doctor signature(Dr. Lindsay Goldman) is
 * displayed and authorize prescription, submit PA to PARx. Step 5: Again search
 * patient, prescribe 'epoprostenol' medication, verify master doctor
 * signature(Dr. Lindsay Goldman) is displayed, authorize prescription, submit
 * PA to PARx. Step 6: Now switch to pharmacy portal, access PA one by one and
 * update PA status to 'Approved'. Step 7: Login to ZappRx pharmacy with
 * credential lindsay.goldman@zapprx.com/zapprx and verify that user is able to
 * fulfill both prescription.
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ311Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;
	String[] nurseFirstNameAndEmailId = null;

	private ZQ311Test(String baseUrl) {
		super("admin.baseUrl");
	}

	@Test
	public void Step01_loginAdminSelectManualWorkflowMode() {
		test.patientCommonWorkflow.verifyAdminIsAbleToLogin(getYamlValue("admin.emailId1"),
				getYamlValue("admin.password1"));
		test.pracPage.selectPractice(getYamlValue("admin.practice.practice3"));
		test.pracPage.clickOnEditPractice();
		test.pracPage.selectPAWorkflowMode(getYamlValue("admin.workflowMode.mode1"));
		test.patientCommonWorkflow.savePracProfileAndLogout();
	}

	@Test
	public void Step02_createRegisteredNurseAndAssignSignatory() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
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
	public void Step03_loginAsNurseAndregisterPatient() {
		test.patientCommonWorkflow.verifyNewlyAddedDoctorLogin(nurseFirstNameAndEmailId[1],
				getYamlValue("physician.patientPassword"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step04_prescribePatientFlolanAndSubmitPAToPARx() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.prescribePatientAndAssignPhysician(
				getYamlValue("physician.indication1.medicines.medicine12.name"), getYamlValue("physician.name1"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy5"),
				patientLastNameDOBAndGender[2], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine12.name"));
		test.commonDosagePage.clickOnAuthorize();
		test.authorizationPage.verifyMasterDoctorSignature(getYamlValue("physician.name1"));
		test.authorizationPage.clickOnSubmitButton();
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.saveSubmitReSubmitPA(getYamlValue("physician.priorAuthButton.submit"),
				getYamlValue("physician.PASubmissionMsg"));
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus1"));
	}

	@Test
	public void Step05_prescribePatientEpoprostenolAndSubmitPAToPARx() {
		test.patientCommonWorkflow.clickPatientsSearchAndSelect(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow
				.clickPresOnPatientModalAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.prescribePatientAndAssignPhysician(
				getYamlValue("physician.indication1.medicines.medicine16.name"), getYamlValue("physician.name1"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy5"),
				patientLastNameDOBAndGender[2], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine16.name"));
		test.commonDosagePage.clickOnAuthorize();
		test.authorizationPage.verifyMasterDoctorSignature(getYamlValue("physician.name1"));
		test.authorizationPage.clickOnSubmitButton();
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.saveSubmitReSubmitPA(getYamlValue("physician.priorAuthButton.submit"),
				getYamlValue("physician.PASubmissionMsg"));
		test.priorAuthorizationPage.verifyPAStatus(getYamlValue("physician.paStatus.PAStatus1"));
	}

	@Test
	public void Step06_onPharmacyPortalUpdateBothPAToApproved() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailPA"),
				getYamlValue("pharmacy.password"));
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0],
				getYamlValue("physician.indication1.medicines.medicine12.name"));
		test.priorAuthPage.verifyPAStatusOnPriorAuthPage(getYamlValue("pharmacy.paStatus.PAStatus1"),
				patientLastNameDOBAndGender[0]);
		test.priorAuthPage.clickOnViewPADetails();
		test.priorAuthPage.updatePAStatus(getYamlValue("pharmacy.paStatus.PAStatus3"));
		test.priorAuthPage.updatePAStatus(getYamlValue("pharmacy.paStatus.PAStatus4"));
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus4"));
		test.leftnavigationPage.clickOnPAList();
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0],
				getYamlValue("physician.indication1.medicines.medicine16.name"));
		test.priorAuthPage.verifyPAStatusOnPriorAuthPage(getYamlValue("pharmacy.paStatus.PAStatus1"),
				patientLastNameDOBAndGender[0]);
		test.priorAuthPage.clickOnViewPADetails();
		test.priorAuthPage.updatePAStatus(getYamlValue("pharmacy.paStatus.PAStatus3"));
		test.priorAuthPage.updatePAStatus(getYamlValue("pharmacy.paStatus.PAStatus4"));
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus4"));
		test.headerPage.logOut();
	}

	@Test
	public void Step07_loginZappRxPharmacyAndFulfillBothPrescription() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailId"),
				getYamlValue("pharmacy.password"));
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0],
				getYamlValue("physician.indication1.medicines.medicine12.name"));
		test.rxStatusPage.clickOnViewRxDetails();
		test.presDetailsPage.fulfillPrescription(getYamlValue("pharmacy.fulfillSuccess"));
		test.presDetailsPage.verifyRxStatus(getYamlValue("physician.rxStatus.filled"));
		test.presDetailsPage.verifyFulfilledDate();
		test.leftnavigationPage.clickOnPrescriptionStatus();
		test.rxStatusPage.searchAndSelectPatient(patientLastNameDOBAndGender[0],
				getYamlValue("physician.indication1.medicines.medicine16.name"));
		test.rxStatusPage.clickOnViewRxDetails();
		test.presDetailsPage.fulfillPrescription(getYamlValue("pharmacy.fulfillSuccess"));
		test.presDetailsPage.verifyRxStatus(getYamlValue("physician.rxStatus.filled"));
		test.presDetailsPage.verifyFulfilledDate();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow.deleteDoctor(nurseFirstNameAndEmailId[0]);
	}
}