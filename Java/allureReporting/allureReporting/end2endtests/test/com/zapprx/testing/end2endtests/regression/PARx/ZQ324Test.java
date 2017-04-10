package com.zapprx.testing.end2endtests.regression.PARx;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register a new patient Step 2:
 * Now prescribe 'Esbriet' medication under IPF indication to newly registered
 * patient and on 'Dosage' page select 'Initial Titration Order', 'Maintenance
 * Order' and 'Esbriet Start Now' and then authorize the prescription. Step 3:
 * On prior authorization page, verify that quantity value is appearing once
 * under 'Dosage' tab for 'Initial Dosage' and 'Maintenance Dosage'.
 * 
 * @author QAIT\priyankasingh
 *
 */

public class ZQ324Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ324Test(String baseUrl) {
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
	public void Step02_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step03_prescribeAndCheckTitOrderMainOrderStartNow() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication4.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication4.medicines.medicine1.name"),
				getYamlValue("physician.indication4.diagnosis.diagnosis1"),
				getYamlValue("physician.indication4.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.IPFDosagePage.selectInitialTitrationOrder();
		test.dosageCommonWorkflow.clickOnDifferentDosageOptions(
				getYamlValue("physician.indication4.medicines.medicine1.name"),
				getYamlValue("physician.indication4.medicines.medicine1.maintenanceOrder.maintenanceOrder1"),
				"Maintenance Order");
		test.IPFDosagePage.selectEsbrietStartNow();
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication4.medicines.medicine1.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
	}

	@Test
	public void Step04_verifyQuantityAppearingOnceOnPA() {
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.expandTabs(getYamlValue("physician.paTabs.dosage"));
		test.priorAuthorizationPage.verifyQuantityValueAppearingOnce(getYamlValue("physician.Quantity.quantity2"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
