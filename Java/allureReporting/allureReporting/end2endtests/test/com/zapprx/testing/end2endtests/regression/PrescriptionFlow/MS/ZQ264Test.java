package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.MS;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as physician to register a new patient and
 * verify 'General Questions' on clinical profile section and complete profile.
 * Step 2: Now prescribe MS medication to newly registered patient and verify
 * 'General Questions' and 'Medication-specific Questions' on PA form and
 * patient profile.
 * 
 * @author vivekdua
 */
@Test(groups = "TestExclude")
public class ZQ264Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;
	List<String> paMedQues = null;

	private ZQ264Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_loginPhysicianAndVerifyGeneralQuestions() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailPA"),
				getYamlValue("physician.passwordPA"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.enterGeneralInfoAndInsuranceInfo(
				getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
		test.headerPage.verifyUserIsOnCorrectPage("Clinical Information");
		test.patientRegistrationPage.enterMedicalHistory(getYamlValue("physician.medicalHistory.knownAllergy1"));
		test.patientRegistrationPage.enterClinicalProfileDetails(getYamlValue("physician.indication3.name"),
				getYamlValue("physician.indication3.diagnosis.diagnosis1"),
				getYamlValue("physician.indication3.otherDetails"));
		test.patientRegistrationPage.verifyGeneralQuestions();
		test.patientRegistrationPage.clickOnCompleteRegistration();
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication3.name"));
	}

	@Test(dataProvider = "medicineProvider")
	public void Step02_presPatAndVerifyGeneralAndMedQuesOnPAAndPatProfile(String medicineName) {
		boolean remsValue = test.patientCommonWorkflow.chooseMedication(medicineName, patientLastNameDOBAndGender[0]);
		test.patientDetailPage.selectPharmacyForMSMedQues(medicineName);
		test.patientDetailPage.clickOnDiagnosisStep();
		test.diagnosisPage.enterDiagnosisDetails(getYamlValue("physician.indication3.diagnosis.diagnosis1"),
				getYamlValue("physician.indication3.diagnosis.newDiagnosis"),
				getYamlValue("physician.indication3.otherDetails"));
		test.diagnosisPage.verifyGeneralQuestions();
		test.diagnosisPage.clickOnDosage();
		test.dosageCommonWorkflow.selectDosageOptions(medicineName);
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue, medicineName,
				getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.presDetailsPage.clickOnPriorAuthorization();
		test.priorAuthorizationPage.verifyGeneralQuestions();
		paMedQues = test.priorAuthorizationPage.getTextOfQuestionsOnPAForm();
		test.priorAuthorizationPage.clickOnPatientName();
		test.patientProfilePage.clickOnClinicalTab();
		test.patientProfilePage.clickOnClinicalProfile();
		test.patientProfilePage.verifyGeneralQuestions();
		test.patientProfilePage.verifyMedUnderPriorAndCurrentMedication(medicineName);
		test.patientProfilePage.verifyMedSpecificQuestions(paMedQues, medicineName);
		test.patientCommonWorkflow.searchPatientAndSelectIndication(patientLastNameDOBAndGender[0],
				getYamlValue("physician.indication3.name"));
	}

	@DataProvider(name = "medicineProvider")
	public Object[][] getMedications() {
		return test.chooseMedicationPage.getMSMedicinesForMedQuestions();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
