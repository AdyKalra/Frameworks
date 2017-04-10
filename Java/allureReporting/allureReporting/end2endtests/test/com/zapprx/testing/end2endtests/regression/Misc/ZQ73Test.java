package com.zapprx.testing.end2endtests.regression.Misc;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Launch the application and click on forgot password
 * on login page to verify Reset Password message
 * 
 * @author vivekdua
 *
 */
@Test
public class ZQ73Test extends BaseTest{

	private ZQ73Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_verifyResetPasswordMessage() {
		test.loginPage
				.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
		test.loginPage.clickOnForgotPassword();
		test.loginPage
				.verifyResestPasswordMessage(getYamlValue("physician.resetPasswordMsg"));
	}
}
