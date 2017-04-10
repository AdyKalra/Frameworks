package com.zapprx.testing.end2endtests.regression.PrescriptionFlow.PAH;

import com.zapprx.testing.end2endtests.basetests.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

/**
 * Steps Automated: Step 1: Login physician to register a new patient adding
 * clinical information and verify patient clinical profile. Step 2: Verify
 * Clinical Tab links and corresponding link information of patient profile is
 * correctly displayed. Step 3: Verify in prescription process 'New Diagnosis?'
 * box is prefilled as 'No' and on selecting additional diagnosis section and
 * clicking on dosage button user is on same page.
 * 
 * @author vivekdua
 */

public class ZQ230Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ230Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndVerifyClinicalInfo() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.enterGeneralInfoAndInsuranceInfo(
				getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
		test.patientCommonWorkflow.enterClinicalInfoAndCompleteReg(getYamlValue("physician.indication1.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"), getYamlValue("physician.otherDetails"));
		test.patientCommonWorkflow.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
		test.patientCommonWorkflow.viewClinicalProfile();
		test.patientProfilePage.verifyClinicalInfo(getYamlValue("physician.indication1.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"), getYamlValue("physician.otherDetails"));
	}

	@Test
	public void Step02_verifyClinicalTabLinksAndInfo() {
		test.patientProfilePage.clickOnClinicalProfile();
		test.patientProfilePage.verifyClinicalTabLinksAreDisplayed();
		test.patientProfilePage.verifyMedicalInfoIsDisplayed("Gender", "Weight", "Height");
		test.patientProfilePage.verifyPresHistoryInfoIsDisplayed("ZappRx Prescriptions");
		test.patientProfilePage.verifyLabsInfoIsDisplayed("Attached Medical Info");
	}

	@Test
	public void Step03_verifyPrescriptionProcessDiagnosisStep() {
		test.patientCommonWorkflow.searchPatientAndSelectIndication(patientLastNameDOBAndGender[0],
				getYamlValue("physician.indication1.name"));
		test.patientCommonWorkflow.chooseMedication(getYamlValue("physician.indication1.medicines.medicine1.name"),
				patientLastNameDOBAndGender[0]);
		test.patientDetailPage
				.selectPharmacy(getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"));
		test.patientDetailPage.clickOnDiagnosisStep();
		test.diagnosisPage.verifyNewDiagnosisBoxText(getYamlValue("physician.newDiagnosis"));
		test.diagnosisPage.clickAdditionalDiagnosisCheckbox(getYamlValue("physician.indication1.diagnosis.diagnosis3"));
		test.diagnosisPage.clickOnDosage();
		test.diagnosisPage.verifyUserIsOnDiagnosisPage();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}

}