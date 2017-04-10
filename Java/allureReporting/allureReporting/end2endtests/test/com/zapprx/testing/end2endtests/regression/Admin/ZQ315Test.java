package com.zapprx.testing.end2endtests.regression.Admin;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as admin, select 'ZappRx Practice' and add new
 * user having all the permissions. Step 2: Click on 'Back' button and verify
 * user is displayed under 'Physicians' tab, select user and verify all the
 * permission are checked. Step 3: Edit user profile and check only 'Show All
 * Patients', now click on 'Back' button and verify user is displayed under
 * 'Nurses' tab, select user and verify only 'Show All Patients' is checked
 * 
 * @author QAIT\priyankasingh
 */

public class ZQ315Test extends BaseTest {
	String[] doctorFirstNameAndEmailId = null;

	private ZQ315Test(String baseUrl) {
		super("admin.baseUrl");
	}

	@Test
	public void Step01_loginAdminAddNewDoctorWithAllPermissions() {
		test.patientCommonWorkflow.verifyAdminIsAbleToLogin(getYamlValue("admin.emailId1"),
				getYamlValue("admin.password1"));
		test.pracPage.selectPractice(getYamlValue("admin.practice.practice3"));
		test.pracPage.clickAddUser();
		test.pracPage.selectRole(getYamlValue("admin.clinicianRole.clinicianRole3"));
		doctorFirstNameAndEmailId = test.pracPage.enterDetailsToAddUser(
				getYamlValue("physician.generalInfo.state"), getYamlValue("physician.lastName"),
				getYamlValue("physician.patientPassword"),"Practice");
		test.pracPage.assignAllPermissions();
		test.pracPage.verifyAddUserButtonText();
		test.pracPage.clickOnAddNewUser();
		test.pracPage.verifyUserSuccessMsg(getYamlValue("physician.lastName"));
		test.pracPage.verifyAdminAuthorizeShowAllPatientsAreChecked();
	}

	@Test
	public void Step02_verifyUserUnderPhysiciansTab() {
		test.headerPage.verifyUserIsOnCorrectPage("Edit Doctor");
		test.pracPage.clickOnBackButton();
		test.pracPage.verifyUserUnderPhysicians(doctorFirstNameAndEmailId[0]);
		test.pracPage.selectUserByName(doctorFirstNameAndEmailId[0]);
		test.pracPage.verifyAdminAuthorizeShowAllPatientsAreChecked();
	}

	@Test
	public void Step03_editProfileSelectShowAllPatientsVerifyUser() {
		test.pracPage.uncheckAllPermissions();
		test.pracPage.clickOnShowAllPatients();
		test.pracPage.clickOnAddNewUser();
		test.pracPage.verifyUserUpdateProfileSuccess(getYamlValue("physician.lastName"));
		test.pracPage.verifyAdminAuthorizeShowAllPatients();
		test.headerPage.verifyUserIsOnCorrectPage("Edit Doctor");
		test.pracPage.clickOnBackButton();
		test.pracPage.verifyUserUnderNurses(doctorFirstNameAndEmailId[0]);
		test.pracPage.selectUserByName(doctorFirstNameAndEmailId[0]);
		test.pracPage.verifyAdminAuthorizeShowAllPatients();
	}

	@AfterClass
	public void deleteDoctor() {
		test.openUrl(getYamlValue("djangoadmin.baseUrl"));
		test.djangoAdminPage.loginAndSelectActors(getYamlValue("djangoadmin.emailId"),
				getYamlValue("djangoadmin.password"));
		test.patientCommonWorkflow.deleteDoctor(doctorFirstNameAndEmailId[0]);
	}
}
