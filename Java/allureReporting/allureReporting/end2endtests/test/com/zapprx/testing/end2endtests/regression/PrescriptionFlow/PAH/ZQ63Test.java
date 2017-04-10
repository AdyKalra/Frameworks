package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import com.zapprx.testing.end2endtests.basetests.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and search
 * the newly created patient Step 2: Physician prescribe medication to upload
 * file under supporting documents and delete the file to verify that no error
 * message is displayed
 * 
 * @author vivekdua
 *
 */

@Test(groups = "TestExclude")
// Exclude this test as this test is superseded
// by RX-3124 where the delete button has been
// removed from Rx process

public class ZQ63Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ63Test(String baseUrl) {
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
	public void Step02_prescribeToDeleteFileAndVerifyMsg() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.prescribePatient(getYamlValue("physician.indication1.medicines.medicine1.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.commonDosagePage.selectMedicalRecord(getYamlValue("physician.medicalType"));
		test.commonDosagePage.uploadFileAndVerify(getYamlValue("physician.fileName"));
		test.commonDosagePage.deleteFileAndVerifyMsg(getYamlValue("physician.deleteFileMsg"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
