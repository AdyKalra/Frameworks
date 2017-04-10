package com.zapprx.testing.end2endtests.pageActions.common;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class LeftNavigationPageActions extends GetPage {
	WebDriver driver;

	public LeftNavigationPageActions(WebDriver driver) {
		super(driver, "common/LeftNavigationPage");
		this.driver = driver;
	}

	// ******************************************PhysicianEnd**********************************************//
	/**
	 * This method clicks on Home icon
	 */
	public void clickOnHome() {
		wait.waitForStableDom(250);
		isElementDisplayed("link_home");
		wait.waitForElementToBeClickable(element("link_home"));
		executeJavascript("document.getElementById('doctor-home').getElementsByTagName('i')[0].click();");
//		 element("link_home").click();
		logMessage("user clicks on Home");
	}

	/**
	 * This method clicks on Patients icon
	 */
	public void clickOnPatients() {
		wait.waitForStableDom(250);
		isElementDisplayed("link_patients");
		wait.waitForElementToBeClickable(element("link_patients"));
		wait.waitForStableDom(250);
		executeJavascript("document.getElementById('doctor-patients').click();");
		logMessage("user clicks on Patients");
	}

	/**
	 * This method clicks on Prescription Status on left navigation bar at
	 * Physician End
	 */
	public void clickOnPrescriptionStatus() {
		refreshPage();
		wait.waitForElementToBeVisible(element("link_presStatus"));
		executeJavascript("document.getElementById('doctor-prescriptions').click();");
		logMessage("User clicks on Rx Status on left navigation bar at Physician End");
	}

	/**
	 * This method clicks on Prior Authorizations on left navigation bar at
	 * Physician End
	 */
	public void clickOnPriorAuth() {
		scrollDown(element("link_priorAuth"));
		element("link_priorAuth").click();
		logMessage("User clicks on Prior Authorizations on left navigation bar at Physician End");
	}

	/**
	 * This method clicks on Medications on left navigation bar at Physician End
	 */
	public void clickOnMedication() {
		scrollDown(element("link_medications"));
		element("link_medications").click();
		logMessage("User clicks on Medications on left navigation bar at Physician End");
	}

	/**
	 * This method clicks on Notifications on left navigation bar End
	 */
	public void clickOnNotifications() {
		element("link_notifications").click();
		logMessage("User clicks on Notifications on left navigation bar");
	}

	// *******************************PatientEnd************************************************//
	/**
	 * This method clicks on Rx Status on left navigation bar at Patient End
	 */
	public void clickOnRxStatusOnPatient() {
		isElementDisplayed("link_patientRxStatus");
		executeJavascript("document.getElementById('patient-prescriptions').click();");
		logMessage("User clicks on Rx Status on left navigation bar at Patient End");
	}

	/**
	 * This method verifies that notification count is displayed
	 */
	public void verifyNotificationCountIsDisplayed() {
		navigateBack();
		Assert.assertTrue(element("span_notfctnCount").isDisplayed(),
				"Assertion Failed: Notification count is not getting displayed");
		logMessage("Assertion Passed: Notification count is getting displayed");
	}

	/**
	 * This method clicks on Notification at Patient End
	 */
	public void clickOnNotificationAtPatient() {
		element("link_patientNotifications").click();
		logMessage("User clicks on Notifications at Patient End");
	}

	/**
	 * This method clicks on Profile on left navigation bar
	 */
	public void clickOnProfile() {
		element("link_profile").click();
		logMessage("User clicks on Profile on left navigation bar");
	}

	/**
	 * This checks if the element is visible on the page
	 * 
	 * @return isPresent
	 */
	public Boolean notificationPresent() {
		Boolean isPresent = false;
		wait.resetImplicitTimeout(2);
		try {
			if (elements("li_notificationCount").size() == 1) {
				isPresent = true;
			}
		} catch (NoSuchElementException e) {
			isPresent = false;
		}
		wait.resetImplicitTimeout(wait.timeout);
		return isPresent;
	}

	/**
	 * This method returns the notification count
	 * 
	 * @return
	 */
	public String getNotificationCount() {
		return element("li_notificationCount").getText();
	}

	/**
	 * This method verifies notification count
	 * 
	 * @param notificationCount
	 */
	public void verifyNotificationCount(String notificationCount) {
		wait.waitForLoaderToDisappear();
		Assert.assertEquals(element("li_notificationCount").getText(), (Integer.parseInt(notificationCount) + 1) + "",
				"Assertion Failed: Notification Count " + notificationCount + " is not getting displayed");
		logMessage("Assertion Passed: Notification Count " + notificationCount + " is getting displayed");
	}

	/**
	 * This method verifies no pending notification is present on side bar
	 * 
	 * @param notificationCount
	 */
	public void verifyNoPendingNotification() {
		wait.waitForLoaderToDisappear();
		Assert.assertFalse(notificationPresent(), "Assertion Failed: There are pending notifications on the side bar");
		logMessage("Assertion Passed: There are no pending notifications on the side bar");
	}

	/**
	 * This method clicks Home page on Admin portal
	 */
	public void clickHomeIconOnAdmin() {
		executeJavascript("document.getElementById('nav-home').getElementsByTagName('i')[1].click()");
		wait.waitForStableDom(250);
		executeJavascript("document.getElementById('nav-home').getElementsByTagName('i')[0].click()");
		logMessage("User clicks on Home page icon on admin portal");
	}

	// *******************************PharmacyEnd************************************************//
	/**
	 * This method clicks on PA List on left navigation bar
	 */
	public void clickOnPAList() {
		wait.waitForElementToBeClickable(element("link_paListing"));
		executeJavascript("document.getElementById('pa-list').click()");
		logMessage("User clicks on PA List on left navigation bar");
	}
}