package com.zapprx.testing.end2endtests.regression.PhysicianProfile;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Add a new Doctor under practice profile and provide
 * fax No while adding Step 2: Verify fax no is displayed correctly after saving
 * the profile
 */
public class ZQ103Test extends BaseTest{
	String[] doctorFirstNameAndEmailId = null;

	private ZQ103Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_addNewDoctorWithFaxNo() {
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
	}

	@Test
	public void Step02_verifyFaxNoAfterSavingProfile() {
		test.doctorProfilePage
				.verifyFaxNoIsSavedCorrectly(getYamlValue("physician.faxNo"));
	}

	@AfterClass
	public void delete_patient_and_doctor() {
		test.openUrl(getYamlValue("djangoadmin.baseUrl"));
		test.djangoAdminPage.loginAndSelectActors(getYamlValue("djangoadmin.emailId"),
				getYamlValue("djangoadmin.password"));
		test.patientCommonWorkflow.deleteDoctor(doctorFirstNameAndEmailId[0]);
	}
}
