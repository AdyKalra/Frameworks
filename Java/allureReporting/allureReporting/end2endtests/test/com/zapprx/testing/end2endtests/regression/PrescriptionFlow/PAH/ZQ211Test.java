package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register a new patient and
 * validate newly created patient is displayed in Patient's list. Step 2: Now
 * prescribe medication to newly registered patient, and update the calcium
 * blocker details on dosage page. Step 3: Navigate to prescription details page
 * and validate updated values are displayed
 * 
 * @author vivekdua
 *
 */
public class ZQ211Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ211Test(String baseUrl) {
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
	public void Step02_prescribePatientAndUpdateCalBlockerDetails() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine10.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine10.name"));
		test.commonDosagePage.clickOnViewEnrollmentQue();
		test.PAHDosagePage.selectCalBlockerOption(
				getYamlValue("physician.indication1.medicines.medicine10.calChannelOptions.option1"),
				getYamlValue("physician.indication1.medicines.medicine10.ccbStatement"));
		test.PAHDosagePage.enterCCB(getYamlValue("physician.indication1.medicines.medicine10.CCB"));
		test.PAHDosagePage.clickOnResponse(
				getYamlValue("physician.indication1.medicines.medicine10.responses.response1"),
				getYamlValue("physician.indication1.medicines.medicine10.responses.response4"),
				getYamlValue("physician.indication1.medicines.medicine10.responses.response6"));
		test.PAHDosagePage.enterResponses(
				getYamlValue("physician.indication1.medicines.medicine10.responsesTxt.responseTxt1"),
				getYamlValue("physician.indication1.medicines.medicine10.responsesTxt.responseTxt2"),
				getYamlValue("physician.indication1.medicines.medicine10.responsesTxt.responseTxt3"));
		test.commonDosagePage.clickOnAuthorize();
		test.authorizationPage.enterPrescriberAuth(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine10.name"));
		test.authorizationPage.enterPhysicianSavedSig(getYamlValue("physician.password1"));
		test.authorizationPage.clickOnSubmitButton();
	}

	@Test
	public void Step03_verifyCalBlockerDetails() {
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.presDetailsPage.expandPresDetails();
		test.presDetailsPage
				.verifyDosageTyvasoTitle(getYamlValue("physician.indication1.medicines.medicine10.dosageTitle"));
		test.presDetailsPage.verifyDosageTyvasoDetails(
				getYamlValue("physician.indication1.medicines.medicine10.calChannelOptions.option1"));
		test.presDetailsPage.compareCCBValue(getYamlValue("physician.indication1.medicines.medicine10.CCB"));
		test.presDetailsPage.verifyCheckedResponses(
				getYamlValue("physician.indication1.medicines.medicine10.responses.response1"),
				getYamlValue("physician.indication1.medicines.medicine10.responses.response4"),
				getYamlValue("physician.indication1.medicines.medicine10.responses.response6"));
		test.presDetailsPage.compareResponsesValue(
				getYamlValue("physician.indication1.medicines.medicine10.responsesTxt.responseTxt1"),
				getYamlValue("physician.indication1.medicines.medicine10.responsesTxt.responseTxt2"),
				getYamlValue("physician.indication1.medicines.medicine10.responsesTxt.responseTxt3"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
