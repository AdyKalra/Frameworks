package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician and register new patient Step 2:
 * Physician prescribes medication to newly registered patient,selecting Accredo
 * as pharmacy Step 3: Click on Mark As Sent button displayed corresponding to
 * Patient Name under Sent To Pharmacy tab on Home page and submit the current
 * date. Step 4: Navigate to the prescription details page and verify the refill
 * value is gone down by once the prescription is mark as completed.
 * 
 * @author vivekdua
 */

@Test(groups = "TestExclude")
public class ZQ192Test extends BaseTest {
	String date;
	String[] patientLastNameDOBAndGender = null;

	private ZQ192Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_loginPhysicianToRegisterPatient() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step02_prescribePatient() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine1.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine1.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
	}

	@Test
	public void Step03_selectDateAndMarkAsSent() {
		test.leftnavigationPage.clickOnHome();
		test.homePage.clickOnReadyForPharmacy();
		test.homePage.clickOnMarkAsSent(patientLastNameDOBAndGender[0]);
		test.homePage.selectCurrentDate();
		test.presDetailsPage.clickOnSubmit();
		test.homePage.clickOnClose();
	}

	@Test
	public void Step04_completePresAndVerifyRefillValue() {
		test.homePage.selectPatient(patientLastNameDOBAndGender[0]);
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.expandPresDetails();
		int refillValue = test.presDetailsPage.getRefillValue();
		test.presDetailsPage.clickOnMarkAsComplete();
		test.presDetailsPage.selectReason();
		test.presDetailsPage.selectDate();
		test.presDetailsPage.clickOnSubmit();
		test.presDetailsPage.clickContinueOnConfirmationBox();
		test.presDetailsPage.expandPresDetails();
		test.presDetailsPage.verifyUpdatedRefillValue(refillValue);

	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
