package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register new patient and
 * validate it is getting displayed in patient's list Step 2: Prescribe
 * medication to newly registered patient and validate success message is
 * displayed after performing authorization
 * 
 * @author vivekdua
 */
public class ZQ74Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ74Test(String baseUrl) {
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
	public void Step02_prescribePatientToVerifyAuthSuccessMsgIsDisplayed() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine4.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.commonDosagePage.clickOnViewEnrollmentQue();
		test.PAHDosagePage.selectWHOGroup(getYamlValue("physician.whoGroup"));
		test.PAHDosagePage.selectNYHAFunctionalClass(getYamlValue("physician.nhyaClass"));
		test.commonDosagePage.clickOnSaveDraft();
		test.commonDosagePage.clickOnFinishAndReview();
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.expandPresDetails();
		test.presDetailsPage.enterPrescriberAuth(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine4.name"));
		test.presDetailsPage.enterPhysicianSavedSig(getYamlValue("physician.password1"));
		test.presDetailsPage.clickOnAuthorize();
		test.presDetailsPage.verifyAuthSuccessMsg(getYamlValue("physician.authSuccessMsg"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
