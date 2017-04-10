package com.zapprx.testing.end2endtests.regression.PhysicianProfile;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.BeforeSuite;

import com.zapprx.testing.end2endtests.automation.TestSessionInitiator;

/**
 * Steps Automated: Step 1: Login physician to save the signature for physician
 * 
 * @author vivekdua
 *
 */

public class SaveSigPhysicianProfileTest {
	TestSessionInitiator test;

	@BeforeSuite
	public void savePhysicianSignature() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication(getYamlValue("physician.baseUrl"));
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		test.headerPage.selectMyProfile();
		test.myProfilePage.submitSignature();
		test.headerPage.logOut();
		test.patientCommonWorkflow.refreshPage();
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailPA"),
				getYamlValue("physician.passwordPA"));
		test.headerPage.selectMyProfile();
		test.myProfilePage.submitSignature();
		test.headerPage.logOut();
		test.loginPage.verifyUserIsOnLoginPage(getYamlValue("physician.pageTitle"));
		test.closeBrowserSession();
	}
}
