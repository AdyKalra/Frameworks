package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.MS;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and search
 * the newly created patient Step 2: Prescribe patient under MS indication and
 * select Amprya medicine Step 3: On dosage page verify that View additional
 * enrollment questions contains "Where will this drug be administered?" option
 * 
 * @author vivekdua
 *
 */

@Test
public class RX1875Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private RX1875Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
		test.patientPage.clickOnPrescribe();
	}

	@Test
	public void Step02_prescribePatientViewAdditionalQuestion() {
		test.headerPage.verifyHeaderText("Choose an indication");
		test.indicationPage.selectIndication(getYamlValue("physician.indication3.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication3.medicines.medicine3.name"),
				getYamlValue("physician.indication3.diagnosis.diagnosis1"),
				getYamlValue("physician.indication3.medicines.medicine3.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0],
				getYamlValue("physician.indication3.medicines.medicine1.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication3.medicines.medicine3.name"));
		test.commonDosagePage.clickOnViewEnrollmentQue();
		test.MSDosagePage.verifyDrugAdministeredOptionIsDisplayed(
				getYamlValue("physician.indication3.medicines.medicine3.viewAddDetails"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication3.medicines.medicine3.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}

}
