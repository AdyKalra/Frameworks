package com.zapprx.testing.end2endtests.regression.PatientPortal;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as patient and verify user is successfully
 * navigated to Home page Step 2: Now switch to notifications page and verify
 * select all check box is functional
 * 
 * @author vivekdua
 *
 */
public class ZQ37Test extends BaseTest {

	private ZQ37Test(String baseUrl) {
		super("patient.baseUrl");
	}

	@Test
	public void Step01_patientLogin() {
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("patient.pageTitle"));
		test.loginPage.loginUser(getYamlValue("patient.emailId"), getYamlValue("patient.password"));
		test.headerPage.verifyUserIsOnHomepage(getYamlValue("patient.headerText"));
	}

	@Test
	public void Step02_verifySelectAllOnNotification() {
		test.leftnavigationPage.clickOnNotificationAtPatient();
		test.notificationsPage.clickSelectAllOnPatientEnd();
		test.notificationsPage.verifySelectAllIsEnabled();
	}
}
