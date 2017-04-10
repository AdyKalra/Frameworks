package com.zapprx.testing.end2endtests.regression.RxForms;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.automation.TestSessionInitiator;
import com.zapprx.testing.end2endtests.automation.utils.ReadFromPDF;

/**
 * Steps Automated: Step 1: Login physician to register a new patient and
 * complete profile. Step 2: Prescribe medication to patient and authorize
 * prescription. Step 3: On prescription details page verify eFax and Print
 * options is also verify print rxform pdf title. Step 4: Verify send eFax to
 * rxForms and fax Status under quick info and again click on send and verify
 * eFax button is hidden.
 * 
 * @author QAIT\priyankasingh
 *
 */
@Test(groups = "TestExclude")
public class ZQ300Test {
	TestSessionInitiator test;
	String[] patientLastNameDOBAndGender = null;
	String path = "src/com/zapprx/testing/end2endtests/main/resources/testdata/pdf/";

	@BeforeClass
	public void Start_Test_Session() {
		ReadFromPDF.createNewDir("pdf");
		ReadFromPDF.deleteFile(path);
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication(getYamlValue("physician.baseUrl"));
	}

	@BeforeMethod
	public void testMethodName(Method method) {
		test.stepStartMessage(method.getName());
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
	public void Step02_prescribePatientAndPrint() {
		test.patientCommonWorkflow.clickPresAndSelectIndication(getYamlValue("physician.indication1.name"));
		boolean remsValue = test.patientCommonWorkflow.prescribePatient(
				getYamlValue("physician.indication1.medicines.medicine1.name"),
				getYamlValue("physician.indication1.diagnosis.diagnosis1"),
				getYamlValue("physician.indication1.medicines.medicine1.pharmacy.pharmacy2"),
				patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0], getYamlValue("physician.otherDetails"));
		test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue,
				getYamlValue("physician.indication1.medicines.medicine1.name"), getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
	}

	@Test
	public void Step03_verifyEFaxPrintFieldsAndRxFormPDFTitle() {
		test.presDetailsPage.verifySendButtonText();
		test.presDetailsPage.clickOnSend();
		test.presDetailsPage.verifyEFaxAndPrintButtonIsDisabled();
		test.presDetailsPage.clickOnRxFormRadioButton();
		test.presDetailsPage.verifyEFaxAndPrintButtonIsNotDisabled();
		test.presDetailsPage.clickOnPrintRxFormButton();
		test.presDetailsPage.verifyRxFormFormTitleAndContentType();
	}

	@Test
	public void Step04_verifySendEFaxToRxForm() {
		test.presDetailsPage.eFaxRxFormToGetConfirmationModal();
		test.presDetailsPage.verifyEFaxConfirmationModal(getYamlValue("physician.eFaxInfo.header"),
				getYamlValue("physician.eFaxInfo.faxSub"), getYamlValue("physician.eFaxInfo.senderInfo"),
				getYamlValue("physician.eFaxInfo.destinationInfo"));
		test.presDetailsPage.clickOnSendEFax();
		test.presDetailsPage.verifySendSuccessEFaxConfirmationModal(getYamlValue("physician.sendEFaxConfirmMsg"),
				getYamlValue("physician.faxStatus.sending"));
		test.presDetailsPage.refreshPage();
		test.presDetailsPage.verifyFaxStatus(getYamlValue("physician.faxStatus.sending"));
		test.presDetailsPage.clickOnSend();
		test.presDetailsPage.verifyEFaxButtonIsNotDisplayed();
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

	@AfterClass
	public void close_Test_Session() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
		test.closeBrowserSession();
	}

}
