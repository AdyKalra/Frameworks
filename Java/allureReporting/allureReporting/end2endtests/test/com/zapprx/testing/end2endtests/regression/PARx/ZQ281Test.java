package com.zapprx.testing.end2endtests.regression.PARx;

import com.zapprx.testing.end2endtests.basetests.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

/**
 * Steps Automated: Step 1: Login admin and select PA Workflow mode as 'Manual'
 * for 'Neuromuscular Clinic'. Step 2: Switch to physician portal, login as
 * physician and register new patient. Step 3: Prescribe 'Aubagio' medication,
 * on diagnosis page 'Add Additional Medication' i.e 'Betaseron' and enter text
 * in 'Reason Ended' field, authorize the prescription. Step 4: Click on 'Expand
 * All' from prescription details page and verify values in 'Status' and 'Reason
 * Ended' fields under 'Prior and Current Medications', now click on 'Prior
 * Authorization' and verify values in 'Status' and 'Reason Ended' fields under
 * 'Prior Medications' on PA input screen
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ281Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ281Test(String baseUrl) {
		super("admin.baseUrl");
	}

	@Test
	public void Step01_loginAdminSelectManualWorkflowMode() {
		test.patientCommonWorkflow.verifyAdminIsAbleToLogin(getYamlValue("admin.emailId1"),
				getYamlValue("admin.password1"));
		test.pracPage.selectPractice(getYamlValue("admin.practice.practice2"));
		test.pracPage.clickOnEditPractice();
		test.pracPage.selectPAWorkflowMode(getYamlValue("admin.workflowMode.mode1"));
		test.patientCommonWorkflow.savePracProfileAndLogout();
	}

	@Test
	public void Step02_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailPA"),
				getYamlValue("physician.passwordPA"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
	}

	@Test
	public void Step03_completeRxAddingAdditionalMedication() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication3.name"));
		boolean remsValue = test.patientCommonWorkflow.chooseMedication(
				getYamlValue("physician.indication3.medicines.medicine4.name"), patientLastNameDOBAndGender[0]);
		test.patientDetailPage.enterPatientDetails(getYamlValue("physician.medicalHistory.knownAllergy1"),
				getYamlValue("physician.indication3.medicines.medicine4.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2]);
		test.patientDetailPage.clickOnDiagnosisStep();
		test.patientDetailPage.clickOnPatientProfileModal();
		test.diagnosisPage.enterDiagnosisDetails(getYamlValue("physician.indication3.diagnosis.diagnosis1"),
				getYamlValue("physician.indication3.diagnosis.newDiagnosis"),
				getYamlValue("physician.indication3.otherDetails"));
		test.diagnosisPage.addAdditionalMedication(getYamlValue("physician.indication3.medicines.medicine9.name"),
				getYamlValue("physician.reasonEnded"));
		test.diagnosisPage.clickOnDosage();
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication3.medicines.medicine4.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication3.medicines.medicine4.name"), getYamlValue("physician.passwordPA"));
		test.authorizationPage.clickReviewOnConfirmationModal();
	}

	@Test
	public void Step04_verifyReasonEndedAndStatusField() {
		test.presDetailsPage.expandPresDetails();
		test.presDetailsPage.verifyPriorAndCurrentMedFields(
				getYamlValue("physician.indication3.medicines.medicine9.name"), getYamlValue("physician.reasonEnded"),
				getYamlValue("physician.priorAndCurrentMedStatus.none"));
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.expandTabs(getYamlValue("physician.paTabs.priorAndCurrentMed"));
		test.priorAuthorizationPage.verifyPriorAndCurrentMedications(
				getYamlValue("physician.indication3.medicines.medicine4.name"),
				getYamlValue("physician.indication3.medicines.medicine9.name"));
		test.priorAuthorizationPage.verifyPriorAndCurrentMedFields(
				getYamlValue("physician.indication3.medicines.medicine9.name"), getYamlValue("physician.reasonEnded"),
				getYamlValue("physician.priorAndCurrentMedStatus.none"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
