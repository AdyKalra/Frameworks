package com.zapprx.testing.end2endtests.regression.PARx;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step1: Login as physician with PAH specific clinical profile
 * to register a new patient. Step 2: Complete patient's Rx and on authorization
 * page verify that 'Prior Auth' link is displayed on modal view after clicking
 * on 'Authorize Prescription' and then click on 'Prior Auth' and verfify that
 * PA form is displayed.
 * 
 * @author vivekdua
 *
 */
public class ZQ242Test extends BaseTest {
	String[] patientLastNameDOBAndGender = null;

	private ZQ242Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientAndCompleteProfile() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId4"),
				getYamlValue("physician.password4"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientCommonWorkflow.completeProfile(getYamlValue("physician.insuranceInfo.insuranceName5"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state5"));
		test.patientCommonWorkflow.searchPatientAndSelect(patientLastNameDOBAndGender[0]);
	}

	@Test(dataProvider = "medicineProvider")
	public void Step02_completeRxAndVerifyPriorAuthLink(String medicineName) {
		boolean remsValue = test.patientCommonWorkflow.capturePatConAndPrescribePatient(
				getYamlValue("physician.indication1.name"), medicineName,
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageOptions(medicineName);
		test.commonDosagePage.clickOnAuthorize();
		test.authorizationPage.enterPrescriberAuth(patientLastNameDOBAndGender[2], remsValue, medicineName);
		test.authorizationPage.verifyUserIsAbleToDrawSig();
		test.authorizationPage.verifyPriorAuthLinkAndClickPA();
		test.priorAuthorizationPage.verifyPAFormIsDisplayed(getYamlValue("physician.formTitle.title1"));
		test.priorAuthorizationPage.clickOnViewPresDetails(medicineName);
		test.leftnavigationPage.clickOnPatients();
		test.patientPage.searchPatient(patientLastNameDOBAndGender[0]);
		test.patientPage.selectPatientByName(patientLastNameDOBAndGender[0]);
	}

	@DataProvider(name = "medicineProvider")
	public Object[][] getMedications() {
		return test.chooseMedicationPage.getMedicines(getYamlValue("physician.indication1.medicines.medicine1.name"),
				getYamlValue("physician.indication1.medicines.medicine2.name"),
				getYamlValue("physician.indication1.medicines.medicine4.name"),
				getYamlValue("physician.indication1.medicines.medicine6.name"),
				getYamlValue("physician.indication1.medicines.medicine10.name"));
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}