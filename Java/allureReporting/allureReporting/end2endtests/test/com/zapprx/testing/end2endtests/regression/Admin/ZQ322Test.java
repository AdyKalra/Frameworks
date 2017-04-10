package com.zapprx.testing.end2endtests.regression.Admin;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login to physician portal, select practice profile,
 * click on 'Add New Doctor' and verify Prescription Notification Preferences
 * and PA Notification Preferences defaults to 'No alerts'. Step 2: Login to
 * admin portal, select practice, click on 'Add User' and verify Prescription
 * Notification Preferences and PA Notification Preferences defaults to 'No
 * alerts'.
 * 
 * @author vivekdua
 */
public class ZQ322Test extends BaseTest {
	private ZQ322Test(String baseUrl) {
		super("admin.baseUrl");
	}

	@Test
	public void Step01_loginAdminSelectManualWorkflowMode() {
		test.patientCommonWorkflow.verifyAdminIsAbleToLogin(getYamlValue("admin.emailId1"),
				getYamlValue("admin.password1"));
		test.pracPage.selectPractice(getYamlValue("admin.practice.practice3"));
		test.pracPage.clickOnEditPractice();
		test.pracPage.selectPAWorkflowMode(getYamlValue("admin.workflowMode.mode1"));
		test.patientCommonWorkflow.savePracProfileAndLogout();
	}

	@Test
	public void Step02_loginPhysicianVerifyPresPANotification() {
		test.patientCommonWorkflow.switchToPhysicianPortal(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.headerPage.selectPracticeProfile();
		test.headerPage.verifyUserIsOnCorrectPage("ZappRx Practice Profile");
		test.practiceProfilePage.clickOnAddNewDoctor();
		test.doctorProfilePage.verifyDefaultPresNotificationPref();
		test.doctorProfilePage.verifyDefaultPANotificationPrefs();
		test.headerPage.logOut();
	}

	@Test
	public void Step03_loginAdminVerifyPresPANotftnAndSelectZappRxWorkflowMode() {
		test.patientCommonWorkflow.switchToAdminPortal(getYamlValue("admin.emailId1"), getYamlValue("admin.password1"));
		test.pracPage.selectPractice(getYamlValue("admin.practice.practice3"));
		test.pracPage.clickAddUser();
		test.pracPage.verifyDefaultPresNotificationPref();
		test.pracPage.verifyDefaultPANotificationPrefs();
		test.headerPage.verifyUserIsOnCorrectPage("Add New Doctor");
		test.pracPage.clickOnBackButton();
		test.pracPage.clickOnEditPractice();
		test.pracPage.selectPAWorkflowMode(getYamlValue("admin.workflowMode.mode2"));
		test.patientCommonWorkflow.savePracProfileAndLogout();
	}
}