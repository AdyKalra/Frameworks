package com.zapprx.testing.end2endtests.regression.PatientProfile;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login as patient and verify user is successfully
 * navigated to Home page Step 2: Navigate to Profile page and validate that
 * user is unable to save details on leaving mandatory fields empty
 * 
 * @author vivekdua
 *
 */
public class ZQ29Test extends BaseTest{

	private ZQ29Test(String baseUrl) {
		super("patient.baseUrl");
	}
	
	@Test
	public void Step01_patientLoginAndVerifyHomePage() {
		test.loginPage
				.verifyUserIsOnLoginPage(getYamlValue("patient.pageTitle"));
		test.loginPage.loginUser(getYamlValue("patient.emailId"),
				getYamlValue("patient.password"));
		test.headerPage
				.verifyUserIsOnHomepage(getYamlValue("patient.headerText"));
	}

	@Test
	public void Step02_verifyUserIsUnableToSaveLeavingMandatoryFieldsEmpty() {
		test.leftnavigationPage.clickOnProfile();
		test.profilePage.clickOnEditProfile();
		test.profilePage.clearDOBfield();
		test.profilePage.clickOnSave();
		test.profilePage.verifyUserisUnableToSaveDetails();
	}
}
