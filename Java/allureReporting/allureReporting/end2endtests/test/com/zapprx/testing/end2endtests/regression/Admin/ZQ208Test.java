package com.zapprx.testing.end2endtests.regression.Admin;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as admin and add new user under doctor role.
 * Step 2: Verify newly added user is able to login successfully
 * 
 * @author vivekdua
 */

public class ZQ208Test extends BaseTest {
	String[] doctorFirstNameAndEmailId = null;

	private ZQ208Test(String baseUrl) {
		super("admin.baseUrl");
	}

	@Test
	public void Step01_loginAdminAndAddNewDoctor() {
		test.patientCommonWorkflow.verifyAdminIsAbleToLogin(getYamlValue("admin.emailId1"),
				getYamlValue("admin.password1"));
		test.pracPage.selectPractice(getYamlValue("admin.practice.practice1"));
		test.pracPage.clickAddUser();
		test.pracPage.selectRole(getYamlValue("admin.clinicianRole.clinicianRole3"));
		doctorFirstNameAndEmailId = test.pracPage.enterDetailsToAddUser(getYamlValue("physician.generalInfo.state"),
				getYamlValue("physician.lastName"), getYamlValue("physician.patientPassword"), "Practice");
		test.pracPage.clickOnAddNewUser();
		test.pracPage.verifyUserSuccessMsg(getYamlValue("physician.lastName"));
	}

	@Test
	public void Step02_verifyAddedUserIsAbleToLogin() {
		test.patientCommonWorkflow.switchToPhysicianPortal(doctorFirstNameAndEmailId[1],
				getYamlValue("physician.patientPassword"));
		test.headerPage.verifyUserIsOnHomepage(getYamlValue("physician.headerText"));
	}

	@AfterClass
	public void delete_patient_and_doctor() {
		test.openUrl(getYamlValue("djangoadmin.baseUrl"));
		test.djangoAdminPage.loginAndSelectActors(getYamlValue("djangoadmin.emailId"),
				getYamlValue("djangoadmin.password"));
		test.patientCommonWorkflow.deleteDoctor(doctorFirstNameAndEmailId[0]);
	}
}
