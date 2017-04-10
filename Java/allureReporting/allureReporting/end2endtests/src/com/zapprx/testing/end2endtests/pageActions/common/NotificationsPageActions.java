package com.zapprx.testing.end2endtests.pageActions.common;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class NotificationsPageActions extends GetPage {
	WebDriver driver;
	private static int notifctnCount;

	public NotificationsPageActions(WebDriver driver) {
		super(driver, "common/NotificationPage");
		this.driver = driver;
	}

	// ****************************************Patient***********************************//

	/**
	 * This method clicks on select all option on patient end
	 */
	public void clickSelectAllOnPatientEnd() {
		wait.waitForLoaderToDisappear();
		scrollDown(element("inp_slctAllPatient"));
		executeJavascript("document.getElementById('datagrid').getElementsByTagName('input')[0].click();");
		logMessage("User clicks on Select All option on Patient End");
	}

	/**
	 * This method verifies the select all option is selected or not
	 */
	public void verifySelectAllIsEnabled() {
		Assert.assertTrue(element("inp_slctAllPatient").isSelected(),
				"Assertion Failed: Select All option is disabled");
		logMessage("Assertion Passed: Select All option is enabled");
	}

	// ***************************************Physician*********************************//
	/**
	 * This method verifies notification message
	 * 
	 * @param notificationMsg
	 */
	public void verifyNotificationMessage(String notificationMsg) {
		Assert.assertEquals(element("td_notfctnMsg").getText(), notificationMsg,
				"Assertion Failed: Notification message '" + notificationMsg + "' is not displayed");
		logMessage("Assertion Passed: Notification message '" + notificationMsg + "' is displayed");
	}

	/**
	 * This method clicks on notification to read
	 */
	public void readNotification() {
		element("td_notfctnMsg").click();
		logMessage("User clicks on notification message");
	}

	/**
	 * This method clicks on Notification toggle to get menu to check/uncheck
	 * notifications
	 */
	public void clickOnNotificationToggle() {
		element("i_notfctnToggle").click();
		logMessage("User clicks on Notification toggle to get menu to check/uncheck notifications");
	}

	/**
	 * This method clicks on Unread toggle to check unread notifications
	 */
	public void clickOnUnreadToggle() {
		element("a_toggleUnread").click();
		logMessage("User clicks on Unread toggle to check unread notifications");
	}

	/**
	 * This method clicks on 'Action' filter to get menu options
	 */
	public void clickOnActionFilter() {
		element("btn_filterAction").click();
		logMessage("User clicks on 'Action' filter to get menu options");
	}

	/**
	 * This method clicks on 'Mark as read' under 'Action' filter to mark as
	 * read the unread notifications
	 */
	public void clickOnMarkAsReadFilter() {
		element("a_markRead").click();
		logMessage("User clicks on 'Mark as read' under 'Action' filter to mark as read the unread notifications");
	}

	/**
	 * This method 'Mark as read' all unread notifications
	 */
	public void markAsReadAllUnreadNotifications() {
		if (elements("span_notfctnCount").size() == 1) {
			clickOnNotificationToggle();
			clickOnUnreadToggle();
			clickOnActionFilter();
			clickOnMarkAsReadFilter();
		}
	}

	// ***************************************Pharmacy*********************************//
	/**
	 * This method clicks on Notification message under Rx Info
	 * 
	 * @param medicationName
	 */
	public void clickNotificationMsg(String medicationName) {
		element("span_rxInfo", medicationName).click();
		logMessage("User clicks on Notification Message under Rx Info");
	}

	/**
	 * This method checks for no new notification
	 */
	public void verifyNoNewNotification() {
		if (elements("span_notfctnCount").size() == 1) {
			Assert.assertEquals(Integer.parseInt(element("span_notfctnCount").getText()), notifctnCount,
					"Assertion Failed: New notification is displayed");
			logMessage("Assertion Passed: No new notification is displayed");
		} else
			logMessage("Assertion Passed: No new notification is displayed");
	}

	/**
	 * This method checks for new notification
	 */
	public void verifyNewNotificationCount() {
		if (elements("span_notfctnCount").size() == 1) {
			Assert.assertEquals(Integer.parseInt(element("span_notfctnCount").getText()), ++notifctnCount,
					"Assertion Failed: New notification is displayed");
			logMessage("Assertion Passed: No new notification is displayed");
		} else
			logMessage("Assertion Passed: No new notification is displayed");
	}

	/**
	 * This method fetches notification count
	 */
	public void fetchNotificationCount() {
		if (elements("span_notfctnCount").size() == 1) {
			notifctnCount = Integer.parseInt(element("span_notfctnCount").getText());
		}
	}
}
