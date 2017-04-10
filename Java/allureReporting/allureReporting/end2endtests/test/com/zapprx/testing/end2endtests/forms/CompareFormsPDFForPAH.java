package com.zapprx.testing.end2endtests.forms;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.zapprx.testing.end2endtests.basetests.BaseTest;

public class CompareFormsPDFForPAH extends BaseTest {
	String[] patientLastNameDOBAndGender = null;
	String path = "src/com/zapprx/testing/end2endtests/main/resources/testdata/";

	private CompareFormsPDFForPAH(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_registerPatientToCompleteProfileAndPresPatient() {
		test.pdfPage.deleteFiles();
		test.pdfPage.createDirectory();
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerMalePatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.headerPage.verifyHeaderOfPage("New Patient Registration");
		test.patientRegistrationPage.enterGeneralInfo(getYamlValue("physician.generalInfo.state"),
				getYamlValue("physician.patientPassword"), getYamlValue("physician.firstName"));
		test.headerPage.verifyUserIsOnCorrectPage("Insurance Information");
		test.patientRegistrationPage.enterInsuranceInfo(getYamlValue("physician.insuranceInfo.insuranceName1"),
				getYamlValue("physician.insuranceInfo.policyId1"), getYamlValue("physician.insuranceInfo.groupNumber1"),
				getYamlValue("physician.insuranceInfo.state1"));
		test.patientRegistrationPage.enterDetailsForPharmacyBenefits("Pharmacy Benefits",
				getYamlValue("physician.insuranceInfo.state2"), getYamlValue("physician.insuranceInfo.binNumber"),
				getYamlValue("physician.insuranceInfo.pcnNumber"));
		test.patientRegistrationPage.clickOnClinical();
		test.headerPage.verifyUserIsOnCorrectPage("Clinical Information");
		test.patientRegistrationPage.clickOnCompleteRegistration();
		test.patientPage.clickOnHome();
	}

	@Test(dataProvider = "medicineProvider")
	public void Step02_precribeMedicationAndGeneratePDFToCompare(String medicineName) {
		test.leftnavigationPage.clickOnPatients();
		test.patientPage.searchPatient(patientLastNameDOBAndGender[0]);
		test.patientPage.selectPatientByName(patientLastNameDOBAndGender[0]);
		test.patientPage.clickPrescribeOnPatientModal();
		test.headerPage.verifyHeaderText("Choose an indication");
		test.indicationPage.selectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(medicineName,
				getYamlValue("physician.indication1.diagnosis.diagnosis4"),
				getYamlValue("physician.indication1.medicines.medicine2.pharmacy.pharmacy1"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.dosageCommonWorkflow.selectDosageAndAuthorize(medicineName, patientLastNameDOBAndGender[0], path,
				remsValue);

	}

	@DataProvider(name = "medicineProvider")
	public Object[][] getMedications() {
		return test.chooseMedicationPage.getAllMedicines_PAHFormsTest();
	}

	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
