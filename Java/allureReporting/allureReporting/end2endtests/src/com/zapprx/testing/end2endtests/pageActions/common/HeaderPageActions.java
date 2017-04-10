package com.zapprx.testing.end2endtests.pageActions.common;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class HeaderPageActions extends GetPage {
	WebDriver driver;

	public HeaderPageActions(WebDriver driver) {
		super(driver, "common/HeaderPage");
		this.driver = driver;
	}

	/**
	 * This method verifies header on Home Page
	 * 
	 * @param headerText
	 */
	public void verifyUserIsOnHomepage(String headerText) {
		wait.waitForLoaderToDisappear();
		wait.waitForStableDom(250);
		isElementDisplayed("headerTxt");
		String header = element("headerTxt").getText().replace("\n", " ");
		Assert.assertTrue(header.equals(headerText),
				"Assertion Failed : Header" + headerText + " is not getting displayed on Home Page");
		logMessage("Assertion Passed : Header " + headerText + " is getting displayed on Home Page");
	}

	/**
	 * This method verifies header on Admin Home Page
	 * 
	 * @param headerText
	 */
	public void verifyUserIsOnAdminHomepage() {
		isElementDisplayed("hdrtxt_admin");
	}

	/**
	 * This method clicks on LogOut
	 */
	public void logOut() {
		wait.waitForStableDom(250);
		executeJavascript("document.getElementById('advancedSearchDD').style.display='block'");
		executeJavascript("document.getElementById('logout_link').click();");
		logMessage("User clicks on LogOut");
		if (getEnv().equalsIgnoreCase("test")) {
			wait.waitForLoaderToDisappear();
		}
	}

	/**
	 * This method clicks on user's name at top right corner
	 */
	private void clickOnUserName() {
		wait.waitForLoaderToDisappear();
		wait.waitForStableDom(250);
		element("span_username").click();
		logMessage("User clicks on user's name");
	}

	/**
	 * This method verifies username is displayed once logged in
	 */
	public void verifyAddNewPracIsDisplayed() {
		isElementDisplayed("btn_addNewPrac");
	}

	/**
	 * This method verifies user is on new patient registration
	 */
	public void verifyHeaderOfPage(String headerText) {
		wait.waitForLoaderToDisappear();
		wait.waitForElementTextToContain(element("hdr_breadcrumb"), headerText);
		Assert.assertEquals(element("hdr_breadcrumb").getText().trim(), headerText,
				"Assertion Failed: User is not on " + headerText + " Page");
		logMessage("Assertion Passed: User is on " + headerText + " Page");
	}

	/**
	 * This method verifies user is on correct page
	 * 
	 * @param pageName
	 */
	public void verifyUserIsOnCorrectPage(String pageName) {
		wait.waitForLoaderToDisappear();
		wait.waitForElementTextToContain(element("txt_breadcrumb"), pageName);
		isElementDisplayed("txt_breadcrumb");
		Assert.assertEquals(element("txt_breadcrumb").getText().trim(), pageName,
				"Assertion Failed: User is not on " + pageName + " Page");
		logMessage("Assertion Passed: User is on " + pageName + " Page");
	}

	/**
	 * This method clicks on Practice Profile
	 */
	public void selectPracticeProfile() {
		clickOnUserName();
		element("link_pracProfile").click();
		logMessage("User clicks on Practice Profile");
	}

	/**
	 * This method clicks on My Profile
	 */
	public void selectMyProfile() {
		clickOnUserName();
		element("link_myProfile").click();
		logMessage("User clicks on My Profile");
	}

	/**
	 * This method verifies that settings link is not displayed
	 */
	public void verifySettingsLinkIsNotDisplayed() {
		clickOnUserName();
		isElementNotDisplayed("link_settings");
	}

	/**
	 * This method clicks on LogOut icon at Patient End
	 */
	public void clickOnLogOutAtPatientEnd() {
		isElementDisplayed("span_drpdwnProfile");
		executeJavascript("document.getElementById('zp-navbar-dropdown-logout').click();");
		logMessage("User clicks on LogOut at Patient End");
	}

	/**
	 * This method clicks on LogOut icon at Admin End
	 */
	public void logOutFromAdmin() {
		wait.waitForStableDom(250);
		executeJavascript("document.getElementById('zp-navbar-dropdown-profile-ul').style.display='block'");
		executeJavascript("document.getElementById('logout_link').click();");
		logMessage("User clicks on LogOut");
		if (getEnv().equalsIgnoreCase("test")) {
			wait.waitForLoaderToDisappear();
		}
	}

	/**
	 * This method verifies the header text
	 * 
	 * @param headerTxt
	 */
	public void verifyHeaderText(String headerTxt) {
		isElementDisplayed("span_hdrTxt", headerTxt);
		wait.waitForElementTextToContain(element("span_hdrTxt"), headerTxt);
		Assert.assertEquals(element("span_hdrTxt").getText(), headerTxt,
				"Assertion Failed: Header text " + headerTxt + "is displayed incorrectly");
		logMessage("Assertion Passed: Header text " + headerTxt + " is displayed correctly");
	}

	/**
	 * This method verifies the header text for patient consent
	 * 
	 * @param headerTxt
	 */
	public void verifyHeaderText_PatientConsent(String headerTxt) {
		wait.waitForElementTextToContain(element("hdrTxt_patConsent"), headerTxt);
		Assert.assertTrue(element("hdrTxt_patConsent").getText().trim().contains(headerTxt));
		logMessage("Assertion Passed: Header text " + headerTxt + " is displayed correctly");
	}

	/**
	 * This method verifies user is on edit page for practice or pharmacy
	 */
	public void verifyUserIsOnEditPage(String hdrText) {
		isElementDisplayed("li_editHdr");
		wait.waitForElementTextToContain(element("li_editHdr"), hdrText);
		logMessage("User is on" + hdrText + "page");
	}
}
