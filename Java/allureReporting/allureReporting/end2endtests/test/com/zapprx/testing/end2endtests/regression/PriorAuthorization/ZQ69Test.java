package com.zapprx.testing.end2endtests.regression.PriorAuthorization;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1:Login as physician to register a new patient and
 * search the newly created patient Step 2:Physician prescribe medication to
 * patient and request Prior Authorization Step 3:User send to plan and verify
 * that no error message is displayed for non-required field
 * 
 * @author vivekdua
 *
 */
public class ZQ69Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ69Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step02_prescribePatientAndRequestPriorAuth() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine1.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indications.indication1.medications.medication2"),
				getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
	}

	@Test
	public void Step03_verifyNoErrorMsgForNonReqField() {
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthForPatientPage.userSelectForm();
		test.priorAuthForPatientPage.clickOnSaveButton();
		test.priorAuthForPatientPage.clickOnClose();
		test.priorAuthForPatientPage.clickOnSendToPlan();
		test.priorAuthForPatientPage.verifyNoErrorMsgForNonReqField();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
