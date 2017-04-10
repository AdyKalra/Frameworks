package com.zapprx.testing.end2endtests.regression.Search;

import com.zapprx.testing.end2endtests.basetests.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

/**
 * Steps Automated: Step 1: Login physician to register a new patient,fill in
 * all the required fields on General Info and click on search patients to
 * validate search result
 * 
 * @author vivekdua
 *
 */

@Test (groups = "TestExclude")
//Exclude this test for now as this feature is not used
//in our platform.
public class ZQ30Test extends BaseTest {
	String[] patientLastNameDOBAndGender;

	private ZQ30Test(String baseUrl) {
		super("physician.baseUrl");
	}

	@Test
	public void Step01_loginPhysicianAndVerifySearchResult() {
		test.patientCommonWorkflow.verifyPhysicianIsAbleToLogin(getYamlValue("physician.emailId1"),
				getYamlValue("physician.password1"));
		patientLastNameDOBAndGender = test.patientCommonWorkflow.registerPatient();
		test.patientRegistrationPage.clickOnCompleteProfile();
		test.patientRegistrationPage.enterLoginEmailId(getYamlValue("physician.firstName"));
		test.patientRegistrationPage.enterPasswordForPatientIdentification(getYamlValue("physician.patientPassword"),
				patientLastNameDOBAndGender[0]);
		test.patientRegistrationPage.enterInfoForPatientStatistics();
		test.patientRegistrationPage.enterInfoForHomeAddress(getYamlValue("physician.generalInfo.state"));
		test.patientRegistrationPage.enterInfoForEmergencyContact();
		test.patientRegistrationPage.clickOnSearchPatients();
		test.patientRegistrationPage.verifySearchResultAndClose(getYamlValue("physician.generalInfo.searchTitle"));
	}
	
	@AfterClass
	public void delete_Patient() {
		test.patientCommonWorkflow.loginAdminAndDeletePatient(patientLastNameDOBAndGender[0]);
	}
}
