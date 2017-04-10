package com.zapprx.testing.end2endtests.pageActions.common;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class LoginPageActions extends GetPage {
	WebDriver driver;

	public LoginPageActions(WebDriver driver) {
		super(driver, "common/LoginPage");
		this.driver = driver;
	}

	/**
	 * This method verifies user is on Login Page
	 * 
	 * @param pageTitle
	 */
	public void verifyUserIsOnLoginPage(String pageTitle) {
		wait.resetImplicitTimeout(1);
		try {
			wait.waitForElementToBeVisible(element("inp_userName"));
		} catch (StaleElementReferenceException e) {
			wait.waitForElementToBeVisible(element("inp_userName"));
		}
		wait.resetImplicitTimeout(wait.getTimeout());
		verifyPageTitleContains(pageTitle);
	}

	/**
	 * This method enters user name, password and clicks on SignIn
	 * 
	 * @param userName
	 * @param password
	 */
	public void loginUser(String userName, String password) {
		enterText(element("inp_userName"), userName);
		logMessage("User enters " + userName + " on Login Page");
		enterText(element("inp_password"), password);
		logMessage("User enters password on Login Page");
		element("button_signIn").click();
		logMessage("User clicks on Sign in button");
	}

	/**
	 * This method clicks on Forgot Password link
	 */
	public void clickOnForgotPassword() {
		element("link_forgotPassword").click();
		logMessage("User clicks on Forgot your Password");
	}

	/**
	 * This method verifies message displayed on clicking Forgot Password
	 * 
	 * @param resetPasswordMsg
	 */
	public void verifyResestPasswordMessage(String resetPasswordMsg) {
		Assert.assertEquals(element("p_passwrdMsg").getText(), resetPasswordMsg, "Assertion Failed: Message "
				+ resetPasswordMsg + " is not getting displayed on clicking Forgot Password");
		logMessage(
				"Assertion Passed: Message " + resetPasswordMsg + " is getting displayed on clicking Forgot Password");
	}

	/**
	 * This method validate error message is displayed on entering incorrect
	 * value
	 * 
	 * @param errorMsg
	 */
	public void verifyErrorMsgForIncorrectPassword(String errorMsg) {
		Assert.assertEquals(element("div_alertMsg").getText().trim(), errorMsg,
				"Assertion Failed: Error message is not getting displayed on entering incorrect password value");
		logMessage("Assertion Passed: Error message is getting displayed on entering incorrect password value");

	}
}
