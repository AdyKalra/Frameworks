package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.automation.utils.ReadFromPDF;
import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and
 * complete patient profile. Step 2: Physician prescribes 'Adcirca' medication
 * to newly registered patient. Step 3: Click on 'Letter of Medical Necessity'
 * and then print form and verify 'Letter of Medical Necessity' form title.
 * 
 * @author vivekdua
 */

public class ZQ239Test extends BaseTest {
	protected ZQ239Test(String baseUrl) {
		super("physician.baseUrl");
	}

	String[] patientLastNameDOBAndGender = null;
	String path = "src/com/zapprx/testing/end2endtests/main/resources/testdata/downloadPDF/";

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		ReadFromPDF.createNewDir("downloadPDF");
		ReadFromPDF.deleteFile(path);
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step02_prescribePatientAndVerifyConfModal() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine1.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine1.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
	}

	@Test
	public void Step03_verifyLetterOfMedicalNecessity() {
		test.presDetailsPage.clickOnLetterOfMedicalNecessity();
		test.presDetailsPage.clickOnPrintToPrintLetterOfMedical();
		test.presDetailsPage.verifyMedicalNecessityFormTitle();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}

}
