package com.zapprx.testing.end2endtests.regression.Admin;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zapprx.testing.end2endtests.basetests.BaseTest;

/**
 * Steps Automated: Step 1: Login Admin, add new user under practice and verify
 * header 'ZappRx Practice/Add New Doctor'. Step 2: Verify header 'ZappRx
 * Practice/Edit Doctor' after adding new user. Step 3: Add new user under
 * pharmacy and verify header 'ZappRx Pharmacy/Add New Pharmacist'. Step 4:
 * Verify header 'ZappRx Pharmacy/Edit Pharmacist' after adding new user.
 * 
 * @author vivekdua
 *
 */

public class ZQ326Test extends BaseTest {
	String[] doctorFirstNameAndEmailId = null;
	String[] pharmacistFirstNameAndEmailId = null;

	private ZQ326Test(String baseUrl) {
		super("admin.baseUrl");
	}

	@Test
	public void Step01_verifyHeaderAndAddNewUserInPractice() {
		test.patientCommonWorkflow.verifyAdminIsAbleToLogin(getYamlValue("admin.emailId1"),
				getYamlValue("admin.password1"));
		test.pracPage.selectPractice(getYamlValue("admin.practice.practice3"));
		test.pracPage.clickAddUser();
		test.headerPage.verifyHeaderOfPage("ZappRx Practice");
		test.headerPage.verifyUserIsOnCorrectPage("Add New Doctor");
		test.pracPage.selectRole(getYamlValue("admin.clinicianRole.clinicianRole3"));
		doctorFirstNameAndEmailId = test.pracPage.enterDetailsToAddUser(getYamlValue("physician.generalInfo.state"),
				getYamlValue("physician.lastName"), getYamlValue("physician.patientPassword"), "Practice");
		test.pracPage.assignAllPermissions();
		test.pracPage.verifyAddUserButtonText();
		test.pracPage.clickOnAddNewUser();
		test.pracPage.verifyUserSuccessMsg(getYamlValue("physician.lastName"));
	}

	@Test
	public void Step02_verifyHeaderForEditUserForPractice() {
		test.headerPage.verifyHeaderOfPage("ZappRx Practice");
		test.headerPage.verifyUserIsOnCorrectPage("Edit Doctor");
	}

	@Test
	public void Step03_verifyHeaderAndAddNewUserInPharmacy() {
		test.leftnavigationPage.clickHomeIconOnAdmin();
		test.homePage.clickOnPharmacies();
		test.pracPage.selectPractice(getYamlValue("admin.pharmacy.pharmacy2"));
		test.pharPage.addUser();
		test.headerPage.verifyHeaderOfPage("ZappRx Pharmacy");
		test.headerPage.verifyUserIsOnCorrectPage("Add New Pharmacist");
		test.pharPage.selectRole(getYamlValue("admin.pharmacy.role.role1"));
		pharmacistFirstNameAndEmailId = test.pracPage.enterDetailsToAddUser(getYamlValue("physician.generalInfo.state"),
				getYamlValue("physician.lastName"), getYamlValue("physician.patientPassword"), "Pharmacy");
		test.pharPage.clickOnAddPharmacist();
		test.pracPage.verifyUserSuccessMsg(getYamlValue("physician.lastName"));
	}

	@Test
	public void Step04_verifyHeaderForEditUserForPharmacy() {
		test.headerPage.verifyHeaderOfPage("ZappRx Pharmacy");
		test.headerPage.verifyUserIsOnCorrectPage("Edit Pharmacist");
	}

	@AfterClass
	public void deleteDoctor() {
		test.openUrl(getYamlValue("djangoadmin.baseUrl"));
		test.djangoAdminPage.loginAndSelectActors(getYamlValue("djangoadmin.emailId"),
				getYamlValue("djangoadmin.password"));
		test.patientCommonWorkflow.deleteDoctor(doctorFirstNameAndEmailId[0]);
		test.patientCommonWorkflow.deleteDoctor(pharmacistFirstNameAndEmailId[0]);
	}

}
