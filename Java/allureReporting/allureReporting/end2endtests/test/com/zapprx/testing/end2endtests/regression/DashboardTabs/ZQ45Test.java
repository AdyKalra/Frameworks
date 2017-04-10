package com.zapprx.testing.end2endtests.regression.DashboardTabs;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login physician and verify Manufacturer page on
 * clicking drug under Medications page
 * 
 * @author vivekdua
 *
 */
public class ZQ45Test extends BaseTest{

	private ZQ45Test(String baseUrl) {
		super("physician.baseUrl");
	}
	
	@Test
	public void Step01_loginPhysicianAndVerifyManufacturerPage() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(
				getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.leftnavigationPage.clickOnMedication();
		test.medicationPage
				.clickOnMedication(getYamlValue("physician.indication1.medicines.medicine1.name"));
		test.medicationPage.clickOnManufacturerLink();
		test.medicationPage
				.verifyManufacturerPage(getYamlValue("physician.manufacturerName"));
	}
}
