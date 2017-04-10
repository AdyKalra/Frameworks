package com.zapprx.testing.end2endtests.regression.Admin;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login Admin and create a new practice Step 2: Verify
 * the newly created practice is displayed in the list
 * 
 * @author vivekdua
 *
 */

public class ZQ202Test extends BaseTest{
	String pracName;
    
	private ZQ202Test(){
		super("admin.baseUrl");
	}
	
	@Test
	public void Step01_loginAdminAndAddNewPractice() {
		test.patientCommonWorkflow
				.verifyAdminIsAbleToLogin(getYamlValue("admin.emailId1"),
						getYamlValue("admin.password1"));
		pracName = test.patientCommonWorkflow.addNewPracOrPhar("Practice");
		test.patientCommonWorkflow
				.enterDetails(getYamlValue("admin.state"),
						getYamlValue("admin.firstName"),
						getYamlValue("admin.password"));
		test.pracPage.verifySuccessMsg(getYamlValue("admin.pracSuccessMsg"));
		test.pracPage.clickOnContinue();
	}

	@Test
	public void Step02_verifyNewlyCreatedPracIsDisplayed() {
		test.leftnavigationPage.clickHomeIconOnAdmin();
		test.pracPage.verifyNewlyCreatedPrac(pracName);
	}
}
