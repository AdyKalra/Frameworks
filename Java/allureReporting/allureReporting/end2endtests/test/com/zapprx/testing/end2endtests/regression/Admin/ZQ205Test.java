package com.zapprx.testing.end2endtests.regression.Admin;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login Admin and select a already existing pharmacy
 * Step 2: Edit the pharmacy and validate the success message
 * 
 * @author vivekdua
 *
 */

public class ZQ205Test extends BaseTest{
	String pharName;

	private ZQ205Test(String baseUrl) {
		super("admin.baseUrl");
	}
	
	@Test
	public void Step01_loginAdminAndSelectPharmmacy() {
		test.patientCommonWorkflow.verifyAdminIsAbleToLogin(getYamlValue("admin.emailId1"),
				getYamlValue("admin.password1"));
		test.homePage.clickOnPharmacies();
		test.pharPage.clickOnPharmacyName(getYamlValue("admin.pharmacy.pharmacy1"));

	}

	@Test
	public void Step02_editPharmacyAndVerify() {
		test.pharPage.clickOnEditPharmacy();
		test.headerPage.verifyUserIsOnEditPage("Edit Pharmacy");
		test.pharPage.enterAddress();
		test.pharPage.enterCity();
		test.pharPage.selectState(getYamlValue("admin.state"));
		test.pharPage.enterZip();
		test.pharPage.clickOnSavePharmacyProfile();
		test.pharPage.verifyPharmacyIsUpdated(getYamlValue("admin.pharmacy.pharmacy1"));
	}

}
