package com.zapprx.testing.end2endtests.regression.Registration;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login the physician and verify state field is
 * present on Insurance Information page while registering a new patient
 * 
 * @author vivekdua
 *
 */
public class ZQ85Test extends BaseTest {
   
	private ZQ85Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_loginAndVerifyStateFieldOnInsuranceInfo() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(
				getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.headerPage.verifyHeaderOfPage("New Patient Registration"); 
		test.patientRegistrationPage.enterGeneralInfo(
				getYamlValue("physician.generalInfo.state"),
				getYamlValue("physician.patientPassword"),
				getYamlValue("physician.lastName"));
		test.headerPage.verifyUserIsOnCorrectPage("Insurance Information");
		test.patientRegistrationPage.verifyStateFieldOnInsuranceInfo();
	}

}
