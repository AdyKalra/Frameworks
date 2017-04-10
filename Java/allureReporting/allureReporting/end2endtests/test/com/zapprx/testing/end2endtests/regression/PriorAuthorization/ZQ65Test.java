package com.zapprx.testing.end2endtests.regression.PriorAuthorization;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Add a new doctor not having submit privileges Step
 * 2: Login with the newly created doctor to register a new patient and then
 * search for the newly created patient Step 3: Physician prescribe and save
 * prescription as draft Step 4: Physician verify Rx Status under Quick Info and
 * availability of Request PA button
 */
public class ZQ65Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;
	String[] doctorFirstNameAndEmailId = null;

	private ZQ65Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_createDoctorWithNoSubmitPrivileges() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.headerPage.selectPracticeProfile();
		test.headerPage.verifyUserIsOnCorrectPage("ZappRx Practice Profile");
		test.practiceProfilePage.clickOnAddNewDoctor();
		doctorFirstNameAndEmailId = test.doctorProfilePage.enterDetailsToAddNewDoctor(
				getYamlValue("physician.generalInfo.state"), getYamlValue("physician.lastName"),
				getYamlValue("physician.patientPassword"), getYamlValue("physician.faxNo"));
		test.doctorProfilePage.saveVerifyAndClose(getYamlValue("physician.addDoctorMsg"),
				getYamlValue("physician.lastName"));
		test.headerPage.logOut();
	}

	@Test
	public void Step02_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyNewlyAddedDoctorLogin(doctorFirstNameAndEmailId[1],
				getYamlValue("physician.patientPassword"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step03_prescribePatientAndSavePres() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.prescribePatient(getYamlValue("physician.indication1.medicines.medicine1.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.commonDosagePage.savePresAndReview();
	}

	@Test
	public void Step04_verifyRxStatusAndRequestPriorAuth() {
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.verifyRxStatus(getYamlValue("physician.rxStatus.draft"));
		test.presDetailsPage.verifyUserIsAbleToRequestPriorAuth();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
