package com.zapprx.testing.end2endtests.functional.patient;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Verify user is on login page after launching the
 * application and url and login into the application Step 2: Verify patient is
 * successfully able to login and redirects to Home page Step 3: Patient logout
 * and verify user redirects to Login page
 * 
 * @author vivekdua
 *
 */
public class PatientLoginTest extends BaseTest {

	private PatientLoginTest(String baseUrl) {
		super("patient.baseUrl");
	}

	@Test
	public void Step01_Login_As_Patient() {
		test.loginPage
				.verifyUserIsOnLoginPage(getYamlValue("patient.pageTitle"));
		test.loginPage.loginUser(getYamlValue("patient.emailId"),
				getYamlValue("patient.password"));
	}

	@Test
	public void Step02_User_Is_On_HomePage() {
		test.headerPage
				.verifyUserIsOnHomepage(getYamlValue("patient.headerText"));
	}

	@Test
	public void Step3_User_LogOut() {
		test.headerPage.clickOnLogOutAtPatientEnd();
		test.loginPage
				.verifyUserIsOnLoginPage(getYamlValue("patient.pageTitle"));
	}

}
