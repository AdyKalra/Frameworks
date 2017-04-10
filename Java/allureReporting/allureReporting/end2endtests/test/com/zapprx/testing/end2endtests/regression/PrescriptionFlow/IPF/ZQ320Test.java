package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.IPF;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register a new patient Step 2:
 * Now prescribe 'Esbriet' medication under IPF indication to newly registered
 * patient and on 'Dosage' page select 'Other directions' under Maintenance
 * Order and edit dosage quanity value and then authorize the prescription. Step
 * 3: On prescription details page, verify that provided 'Dosage Quantity' value
 * on 'Dosage' page while prescribing, is appearing under 'Dosage' tab.
 * 
 * @author QAIT\priyankasingh
 *
 */

public class ZQ320Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ320Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step02_prescribePatientAndEditDosageQuantity() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication4.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication4.medicines.medicine1.name"),
				getYamlValue("physician.indication4.diagnosis.diagnosis1"),
				getYamlValue("physician.indication4.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.clickOnDifferentDosageOptions(
				getYamlValue("physician.indication4.medicines.medicine1.name"),
				getYamlValue("physician.indication4.medicines.medicine1.maintenanceOrder.maintenanceOrder2"),
				"Maintenance Order");
		test.IPFDosagePage.editEsbrietDosageQuantity(getYamlValue("physician.dosageQuantity"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine13.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
	}

	@Test
	public void Step03_verifyEsbrietDosageQuantityValue() {
		test.presDetailsPage.expandPresDetails();
		test.presDetailsPage.verifyEsbrietDosageQuantityValue(getYamlValue("physician.dosageQuantity"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}

}
