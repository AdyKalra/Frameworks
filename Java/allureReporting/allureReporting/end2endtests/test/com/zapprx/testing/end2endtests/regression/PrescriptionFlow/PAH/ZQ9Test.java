package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and search
 * the newly created patient Step 2: Physician prescribe a medication to a newly
 * created patient and the save it as a draft Step 3: Now physician authorize
 * the prescription and verify Rx Status Step 4: Patient login and verify the
 * notification to share authorization is displayed
 * 
 * @author vivekdua
 *
 */
public class ZQ9Test extends BaseTest {
	String[] loginEmailAndMRN;
	boolean remsValue;
	String[] patientLastNameDOBAndGender = null;

	private ZQ9Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		loginEmailAndMRN = test.patientCommonWorkflow.completeProfile(
				getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
	}

	@Test
	public void Step02_prescribeMedicationAndSave() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine3.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine3.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(getYamlValue("physician.indication1.medicines.medicine3.name"));
		test.commonDosagePage.clickOnSaveDraft();
		test.commonDosagePage.verifySavedPrescriptionMsg(getYamlValue("physician.savePrescriptionMsg"));
		test.commonDosagePage.clickOnFinishAndReview();
	}

	@Test
	public void Step03_authorizePatientAndVerifyRxStatus() {
		test.headerPage.verifyUserIsOnCorrectPage("Prescription Details");
		test.presDetailsPage.enterPrescriberAuth(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine3.name"));
		test.presDetailsPage.enterPhysicianSavedSig(getYamlValue("physician.password1"));
		test.presDetailsPage.clickOnAuthorize();
		test.presDetailsPage.verifyAuthSuccessMsg(getYamlValue("physician.authSuccessMsg"));
		test.presDetailsPage.clickOnContinue();
		test.presDetailsPage.verifyRxStatus(getYamlValue("physician.rxStatus.withPatient"));
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

	@Test
	public void Step04_patientVerifyRemsNotification() {
		test.patientCommonWorkflow.switchToPatientPortal(loginEmailAndMRN[0],
				getYamlValue("physician.patientPassword"));
		test.homePage.verifyShareAuthMessage(getYamlValue("physician.indication1.medicines.medicine3.name"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
