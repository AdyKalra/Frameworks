package com.zapprx.testing.end2endtests.regression.Admin;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login Admin and create a new practice Step 2: Verify
 * the newly created pharmacy is displayed in the list
 * 
 * @author vivekdua
 *
 */

public class ZQ203Test extends BaseTest {
	String pharName;

	private ZQ203Test(String baseUrl) {
		super("admin.baseUrl");
	}

	@Test
	public void Step01_loginAdminAndAddNewPharmacy() {
		test.patientCommonWorkflow.verifyAdminIsAbleToLogin(getYamlValue("admin.emailId1"),
				getYamlValue("admin.password1"));
		test.homePage.clickOnPharmacies();
		pharName = test.patientCommonWorkflow.addNewPracOrPhar("Pharmacy");
		test.patientCommonWorkflow.enterDetails(getYamlValue("admin.state"), getYamlValue("admin.firstName"),
				getYamlValue("admin.password"));
		test.pracPage.verifySuccessMsg(getYamlValue("admin.pharSuccessMsg"));
		test.pracPage.clickOnContinue();
	}

	@Test
	public void Step02_verifyNewlyCreatedPharIsDisplayed() {
		test.leftnavigationPage.clickHomeIconOnAdmin();
		test.homePage.clickOnPharmacies();
		test.pharPage.verifyNewlyCreatedPharmacy(pharName);
	}

}
