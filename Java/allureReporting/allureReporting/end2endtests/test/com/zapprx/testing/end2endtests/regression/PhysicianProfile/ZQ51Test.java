package com.zapprx.testing.end2endtests.regression.PhysicianProfile;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login to add a new doctor under practice profile
 * without selecting administrator and show all patients option. Now verify
 * options remain unselected after saving the details.
 * 
 * @author vivekdua
 */
public class ZQ51Test extends BaseTest{
	String[] doctorFirstNameAndEmailId = null;
	   
	private ZQ51Test(String baseUrl) {
		super("physician.baseUrl");
	}
	
	@Test
	public void Step01_createDoctorAndVerifyOptionsAreUnchecked() {
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
		test.doctorProfilePage.verifyAdminAndShowAllPatients();
	}
	
	@AfterClass
	public void delete_patient_and_doctor() {
		test.openUrl(getYamlValue("djangoadmin.baseUrl"));
		test.djangoAdminPage.loginAndSelectActors(getYamlValue("djangoadmin.emailId"),
				getYamlValue("djangoadmin.password"));
		test.patientCommonWorkflow.deleteDoctor(doctorFirstNameAndEmailId[0]);
	}
}
