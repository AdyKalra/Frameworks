package com.zapprx.testing.end2endtests.regression.PARx;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login admin and select PA Workflow mode as 'Manual'
 * for 'ZappRx Practice'. Step 2: Switch to physician portal, login as physician
 * to register new patient and complete profile. Step 3: Complete Rx and submit
 * PA to PARx. Step 4: Now switch to pharmacy portal, access PA, 'Request More
 * Info' and then 'Cancel More Info Request' and verify PA status is updated to
 * 'Submitted to PARx' from 'More Info Requested'. Step 5: Now update PA status
 * to 'Submitted to Payer', 'Request More Info' and then 'Cancel More Info
 * Request' and verify that PA status is updated to 'Submitted to Payer' from
 * 'More Info Requested'.
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ308Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ308Test(String baseUrl) {
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
	public void Step02_loginPhysicianRegPatAndCompleteProfile() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
	}

	@Test
	public void Step03_completeRxSubmitPAToPARx() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine3.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine3.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine3.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.patientCommonWorkflow.createPASubmitAndVerifyPAStatus(
				getYamlValue("physician.indication1.medicines.medicine3.name"),
				getYamlValue("physician.PASubmissionMsg"), getYamlValue("physician.paStatus.PAStatus1"),
				getYamlValue("physician.priorAuthButton.submit"));
		test.headerPage.logOut();
	}

	@Test
	public void Step04_onPharmacyPortalVerifyCancelMoreInfoReqForSubmittedToPARx() {
		test.patientCommonWorkflow.switchToPharmacyPortal(getYamlValue("pharmacy.emailPA"),
				getYamlValue("pharmacy.password"));
		test.priorAuthPage.searchAndSelectPatient(patientLastNameDOBAndGender[0]);
		test.priorAuthPage.verifyPAStatusOnPriorAuthPage(getYamlValue("pharmacy.paStatus.PAStatus1"),
				patientLastNameDOBAndGender[0]);
		test.priorAuthPage.clickOnViewPADetails();
		test.priorAuthPage.requestMoreInfo();
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus2"));
		test.priorAuthPage.verifyCancelMoreInfoReqIsDisplayed();
		test.priorAuthPage.cancelMoreInfoRequest(getYamlValue("pharmacy.cancelMoreInfo.confirmText"),
				getYamlValue("pharmacy.cancelMoreInfo.successText"));
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus1"));
		test.priorAuthPage.verifyCancelMoreInfoReqNotDisplayed();
	}

	@Test
	public void Step05_onPharmacyPortalVerifyCancelMoreInfoReqForSubmittedToPayer() {
		test.priorAuthPage.updatePAStatus(getYamlValue("pharmacy.paStatus.PAStatus3"));
		test.priorAuthPage.requestMoreInfo();
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus2"));
		test.priorAuthPage.verifyCancelMoreInfoReqIsDisplayed();
		test.priorAuthPage.cancelMoreInfoRequest(getYamlValue("pharmacy.cancelMoreInfo.confirmText"),
				getYamlValue("pharmacy.cancelMoreInfo.successText"));
		test.priorAuthPage.verifyPAStatus(getYamlValue("pharmacy.paStatus.PAStatus3"));
		test.priorAuthPage.verifyCancelMoreInfoReqNotDisplayed();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
