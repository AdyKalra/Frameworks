package com.zapprx.testing.end2endtests.regression.DashboardTabs;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician and verify settings link is not
 * getting displayed
 * 
 * @author vivekdua
 *
 */
public class ZQ41Test extends BaseTest{
	
	private ZQ41Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_loginPhysicianAndVerifySettings() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(
				getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.headerPage.verifySettingsLinkIsNotDisplayed();
	}
}
