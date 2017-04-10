package com.zapprx.testing.end2endtests.functional.pharmacy;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Verify pharmacy is on login page after launching url
 * and login into the application Step 2: Verify pharmacy is successfully able
 * to login and redirects to Home page Step 3: Pharmacy logout and verify user
 * redirects to Login page
 * 
 * @author vivekdua
 *
 */
public class PharmacyLoginTest extends BaseTest{

	private PharmacyLoginTest(String baseUrl) {
		super("pharmacy.baseUrl");
	}

	@Test
	public void Step01_Login_As_Pharmacy() {
		test.loginPage
		.verifyUserIsOnLoginPage(getYamlValue("pharmacy.pageTitle"));
		test.loginPage.loginUser(getYamlValue("pharmacy.emailId"),
				getYamlValue("pharmacy.password"));
	}

	@Test
	public void Step02_User_Is_On_HomePage() {
		test.headerPage
		.verifyUserIsOnHomepage(getYamlValue("pharmacy.headerText"));
	}

	@Test
	public void Step03_User_LogOut() {
		test.headerPage.logOut();
		test.loginPage
		.verifyUserIsOnLoginPage(getYamlValue("pharmacy.pageTitle"));
	}

}
