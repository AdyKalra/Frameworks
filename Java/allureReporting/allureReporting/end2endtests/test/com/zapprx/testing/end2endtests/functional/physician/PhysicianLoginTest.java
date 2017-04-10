package com.zapprx.testing.end2endtests.functional.physician;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Verify physician is on login page after launching
 * the url and login into the application Step 2: Verify physician is
 * successfully able to login and redirects to Home page Step 3: Physician
 * logout and verify user redirects to login page
 * 
 * @author vivekdua
 *
 */

public class PhysicianLoginTest extends BaseTest{

	private PhysicianLoginTest(String baseUrl) {
		super("physician.baseUrl");
	}
	
	@Test
	public void Step01_Login_As_Physician() {
		test.loginPage
				.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
		test.loginPage.loginUser(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
	}

	@Test
	public void Step02_User_Is_On_HomePage() {
		test.headerPage
				.verifyUserIsOnHomepage(getYamlValue("physician.headerText"));
	}

	@Test
	public void Step03_User_LogOut() {
		test.headerPage.logOut();
		test.loginPage
				.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
	}

}
