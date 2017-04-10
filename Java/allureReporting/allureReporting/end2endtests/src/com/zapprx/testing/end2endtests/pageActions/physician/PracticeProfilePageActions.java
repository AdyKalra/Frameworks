package com.zapprx.testing.end2endtests.pageActions.physician;

import org.openqa.selenium.WebDriver;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class PracticeProfilePageActions extends GetPage {
	WebDriver driver;

	public PracticeProfilePageActions(WebDriver driver) {
		super(driver, "physician/PracticeProfilePage");
		this.driver = driver;
	}

	/**
	 * This method clicks on Add New Doctor
	 */
	public void clickOnAddNewDoctor() {
		wait.waitForElementToBeClickable(element("btn_addDoctor"));
		element("btn_addDoctor").click();
		logMessage("User clicks on Add New Doctor");
	}
}
