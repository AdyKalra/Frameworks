package com.zapprx.testing.end2endtests.functional.admin;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login Admin and verify user is on home page
 * @author vivekdua
 *
 */

public class ZQ201Test extends BaseTest {

	private ZQ201Test(String baseUrl) {
		super("admin.baseUrl");
	}

	@Test
	public void Step01_loginAdminAndVerifyHomePage() {
		test.patientCommonWorkflow.verifyAdminIsAbleToLogin(getYamlValue("admin.emailId1"),
				getYamlValue("admin.password1"));
		test.headerPage.verifyAddNewPracIsDisplayed();
	}
}
