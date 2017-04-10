package com.zapprx.testing.end2endtests.regression.PhysicianProfile;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Add a new Doctor under practice profile Step 2:
 * Login with the newly created doctor and enter incorrect password to validate
 * error message is being displayed
 */
public class ZQ131Test extends BaseTest{
	String[] doctorFirstNameAndEmailId = null;

	private ZQ131Test(String baseUrl) {
		super("physician.baseUrl");
	}
	
	@Test
	public void Step01_physicianLoginToAddNewDoctor() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(
				getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.headerPage.selectPracticeProfile();
		test.headerPage.verifyUserIsOnCorrectPage("ZappRx Practice Profile");
		test.practiceProfilePage.clickOnAddNewDoctor();
		doctorFirstNameAndEmailId = test.doctorProfilePage
				.enterDetailsToAddNewDoctor(
						getYamlValue("physician.generalInfo.state"),
						getYamlValue("physician.lastName"),
						getYamlValue("physician.patientPassword"),
						getYamlValue("physician.faxNo"));
		test.doctorProfilePage.saveVerifyAndClose(
				getYamlValue("physician.addDoctorMsg"),
				getYamlValue("physician.lastName"));
		test.headerPage.logOut();
	}

	@Test()
	public void Step02_verifyErrorMsgWithIncorrectPassowrd() {
		test.loginPage
				.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
		test.loginPage.loginUser(doctorFirstNameAndEmailId[1],
				getYamlValue("physician.incorrectPassword"));
		test.loginPage
				.verifyErrorMsgForIncorrectPassword(getYamlValue("physician.loginErrorMsg"));
	}

	@AfterClass
	public void delete_patient_and_doctor() {
		test.openUrl(getYamlValue("djangoadmin.baseUrl"));
		test.djangoAdminPage.loginAndSelectActors(getYamlValue("djangoadmin.emailId"),
				getYamlValue("djangoadmin.password"));
		test.patientCommonWorkflow.deleteDoctor(doctorFirstNameAndEmailId[0]);
	}
}