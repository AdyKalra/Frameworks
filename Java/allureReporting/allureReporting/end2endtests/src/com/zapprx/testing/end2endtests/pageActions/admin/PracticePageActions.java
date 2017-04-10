package com.zapprx.testing.end2endtests.pageActions.admin;

import static com.zapprx.testing.end2endtests.automation.utils.CustomFunctions.getDate;
import static com.zapprx.testing.end2endtests.automation.utils.CustomFunctions.getStringWithTimestamp;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;
import com.zapprx.testing.end2endtests.automation.utils.YamlReader;

public class PracticePageActions extends GetPage {
	WebDriver driver;
	private String firstName, emailId;

	public PracticePageActions(WebDriver driver) {
		super(driver, "admin/PracticePage");
		this.driver = driver;
	}

	/**
	 * This method validates user is on New Practice Page
	 */
	public void verifyUserIsNewPracPage() {
		Assert.assertTrue(element("hdr_newPrac").isDisplayed(), "Assertion Failed: User is not on New Practice page");
		logMessage("Assertion Passed: User is on New Practice page");
	}

	/**
	 * This method enters practice name
	 */
	public String enterPracName() {
		String practiceName = getStringWithTimestamp("PracticeName");
		enterText(element("inp_name"), practiceName);
		logMessage("User enters " + practiceName + " in Practice Name field");
		return practiceName;
	}

	/**
	 * This method enters address
	 */
	public void enterAddress() {
		String address = getStringWithTimestamp("address");
		enterText(element("inp_street"), address);
		logMessage("User enters " + address + " in Address field");
	}

	/**
	 * This method enters city
	 */
	public void enterCity() {
		String city = getStringWithTimestamp("city");
		enterText(element("inp_city"), city);
		logMessage("User enters " + city + " in City field");
	}

	/**
	 * This method selects state
	 */
	public void selectState(String state) {
		selectProvidedTextFromDropDown(element("select_state"), state);
		logMessage("User selects " + state + " in State field");
	}

	/**
	 * This method enters value in zip field
	 */
	public void enterZip() {
		String zip = getStringWithTimestamp("city");
		enterText(element("inp_zip"), zip);
		logMessage("User enters " + zip + " in Zip field");
	}

	/**
	 * This method enters value in Administrator First Name field
	 */
	public void enterFirstName(String firstName) {
		enterText(element("inp_firstName"), firstName);
		logMessage("User enters " + firstName + " in Administrator First Name field");
	}

	/**
	 * This method enters value in Administrator Last Name field
	 */
	public void enterLastName() {
		String lastName = getStringWithTimestamp("lastName");
		enterText(element("inp_lastName"), lastName);
		logMessage("User enters " + lastName + " in Administrator Last Name field");
	}

	/**
	 * This method enters value in Password field
	 */
	public void enterPassword(String password) {
		enterText(element("inp_password"), password);
		logMessage("User enters " + password + " in Password field");
	}

	/**
	 * This method enters value in Confirm Password field
	 */
	public void enterConfirmPassword(String password) {
		enterText(element("inp_cnfrmPasswrd"), password);
		logMessage("User enters " + password + " in Confirm Password field");
	}

	/**
	 * This method enters value in Administrator email field
	 */
	public void enterAdminEmail() {
		String emailId = YamlReader.getYamlValue("admin.firstName") + getStringWithTimestamp("") + "@zapprx.com";
		enterText(element("inp_email"), emailId);
		logMessage("User enters " + emailId + " Administrator email field");
	}

	/**
	 * This method clicks on Save Practice Profile
	 */
	public void clickOnSavePracProfile() {
		scrollDown(element("btn_savePracProfile"));
		wait.waitForStableDom(250);
		isElementDisplayed("btn_savePracProfile");
		wait.waitForElementToBeClickable(element("btn_savePracProfile"));
		executeJavascript("document.getElementById('practice-profile-edit-save').click();");
		logMessage("User clicks on Save Practice Profile");
	}

	/**
	 * This method verifies the success message after a new pratice is created
	 */
	public void verifySuccessMsg(String successMsg) {
		isElementDisplayed("p_successMsg");
		wait.waitForElementTextToContain(element("p_successMsg"), successMsg);
		Assert.assertEquals(element("p_successMsg").getText(), successMsg,
				"Assertion Failed: Success message is not getting displayed");
		logMessage("Assertion Passed: Success message is getting displayed");
	}

	/**
	 * This method clicks on continue button
	 */
	public void clickOnContinue() {
		element("btn_continue").click();
		logMessage("User clicks on Continue button");
		wait.waitForElementToBeInVisible(getLocator("btn_continue"));
	}

	/**
	 * This method validates the newly created practice name is displayed in the
	 * list
	 * 
	 * @param pracName
	 */
	public void verifyNewlyCreatedPrac(String pracName) {
		List<WebElement> pracNames = elements("td_pracName");
		for (WebElement element : pracNames) {
			if (element.getText().contains(pracName)) {
				logMessage("Assertion Passed: Newly created Practice Name " + pracName + " is displayed");
			}
		}
	}

	// *******************************EditPractice*********************************//

	/**
	 * This method enters NPI Number
	 */
	public void enterNPINumber() {
		isElementDisplayed("inp_npiNo");
		String npi = getStringWithTimestamp("npi");
		enterText(element("inp_npiNo"), npi);
		logMessage("User enters " + npi + "in NPI Number field");
	}

	/**
	 * This method validates the success message after updating practice
	 * 
	 * @param pracName
	 */
	public void verifyPracIsUpdated(String pracName) {
		Assert.assertEquals(element("p_updatedMsg").getText().trim(), pracName + " has been updated successfully.",
				"Assertion Failed: Practice " + pracName + " is not updated successfullyÏ");
		logMessage("Assertion Passed: Practice " + pracName + " is updated successfully");
	}

	/**
	 * This methods clicks on Edit Practice
	 */
	public void clickOnEditPractice() {
		wait.waitForStableDom(250);
		isElementDisplayed("btn_editPractice");
		executeJavascript("document.getElementById('practice-btn-edit').click();");
		logMessage("User clicks on 'Edit Practice' button");
	}

	/**
	 * This methods selects PA Workfole mode from dropdown
	 */
	public void selectPAWorkflowMode(String workflowMode) {
		selectProvidedTextFromDropDown(element("select_workflowMode"), workflowMode);
		logMessage("User selects " + workflowMode + " from dropdown");
	}

	// ***************Select Practice and Add New Doctor*****************//

	/**
	 * This method clicks on Add User button
	 */
	public void clickAddUser() {
		isElementDisplayed("btn_addUser");
		executeJavascript("document.getElementById('practice-btn-add-doctor').click();");
		logMessage("User clicks on add user button");
	}

	/**
	 * This method enter value in all required fields to add a new doctor
	 * 
	 * @param state
	 */
	public String[] enterDetailsToAddUser(String state, String lastName, String password, String type) {
		String[] arrayForFirstNameEmailId = new String[2];
		firstName = enterFirstName();
		enterLastName(lastName);
		if (type.equalsIgnoreCase("Practice")) {
			enterStateLicenseNo();
			enterDEANumber();
			enterDegree();
			enterSpecialty();
		}
		enterNPINumberForDoc();
		enterAddressForDoc();
		enterCity();
		selectState(state);
		enterZip();
		emailId = enterEmail(type);
		enterPassword(password);
		enterConfirmPasswordForDoc(password);
		arrayForFirstNameEmailId[0] = firstName;
		arrayForFirstNameEmailId[1] = emailId;
		return arrayForFirstNameEmailId;
	}

	/**
	 * This method enters value in First Name field
	 */
	private String enterFirstName() {
		String firstName = custom.getDoctorFirstName();
		wait.waitForLoaderToDisappear();
		enterText(element("inp_firstname"), firstName);
		logMessage("User enters text in FirstName field");
		return firstName;
	}

	/**
	 * This method enters value in Last Name field
	 */
	private void enterLastName(String lastName) {
		enterText(element("inp_lastname"), lastName);
		logMessage("User enters text in LastName field");
	}

	/**
	 * This method enters value in State License Number
	 */
	private void enterStateLicenseNo() {
		enterText(element("inp_licNo"), getStringWithTimestamp(""));
		logMessage("User enters text in License Number field");
	}

	/**
	 * This method enters value in DEA Number field
	 */
	private void enterDEANumber() {
		enterText(element("inp_deaNo"), getDate());
		logMessage("User enters text in DEA Number field");
	}

	/**
	 * This method enters value in NPI Number field
	 */
	private void enterNPINumberForDoc() {
		enterText(element("inp_npiNoDoc"), getDate());
		logMessage("User enters text in NPI Number field");
	}

	/**
	 * This method enters value in Address field
	 */
	private void enterAddressForDoc() {
		enterText(element("inp_street"), getStringWithTimestamp(""));
		logMessage("User enters text in Address field");
	}

	/**
	 * This method enters value in Email field
	 */
	private String enterEmail(String type) {
		String emailId = custom.getDoctorEmailId();
		enterText(element("inp_emailDoc"), type + emailId);
		logMessage("User enters " + custom.getDoctorEmailId() + " in Email field");
		return type + emailId;
	}

	/**
	 * This method enters value in Degree field
	 */
	private void enterDegree() {
		enterText(element("inp_degree"), getStringWithTimestamp(""));
		logMessage("User enters text in Degree field");
	}

	/**
	 * This method enters value in Specialty field
	 */
	private void enterSpecialty() {
		enterText(element("inp_specialty"), getStringWithTimestamp(""));
		logMessage("User enters text in Specialty field");
	}

	/**
	 * This method enters value in Confirm Password field
	 */
	private void enterConfirmPasswordForDoc(String password) {
		enterText(element("inp_cnfrmPasswd"), password);
		logMessage("User enters " + password + " in Confirm Password field");
	}

	/**
	 * This method selects practice from available practices on Practice page
	 */
	public void selectPractice(String practice) {
		scrollDown(element("td_practice", practice));
		isElementDisplayed("td_practice", practice);
		element("td_practice", practice).click();
		logMessage("User clicks on " + practice + " practice");
	}

	/**
	 * This method selects role for new user
	 */

	public void selectRole(String role) {
		selectProvidedTextFromDropDown(element("select_role"), role);
		logMessage("User selects role as " + role);
	}

	/**
	 * This method verifies Add User button text
	 */
	public void verifyAddUserButtonText() {
		Assert.assertEquals(element("btn_updateProfileAddUser").getText().trim(), "Add User",
				"Assertion Failed: Button is not displayed with text 'Add User'");
		logMessage("Assertion Passed: Button is displayed with text 'Add User'");
	}

	/**
	 * This method clicks on Add Doctor button
	 */
	public void clickOnAddNewUser() {
		scrollDown(element("btn_updateProfileAddUser"));
		executeJavascript("document.getElementById('doctor-btn-save').click();");
		logMessage("User clicks on add doctor button");
	}

	/**
	 * This method verifies success message after clicking on "Add User" button
	 * 
	 * @param lastName
	 */
	public void verifyUserSuccessMsg(String lastName) {
		Assert.assertEquals(element("p_userSuccessMsg").getText(),
				"A profile for " + firstName + " " + lastName + " has been created.",
				"Assertion Failed: Success Message for successful creation of doctor does not display");
		logMessage("Assertion Passed: Success Message for successful creation of doctor is displayed");
		clickOnContinue();
	}

	/**
	 * This method verifies success message after clicking on "Update Profile"
	 * button
	 * 
	 * @param lastName
	 */
	public void verifyUserUpdateProfileSuccess(String lastName) {
		Assert.assertEquals(element("p_userSuccessMsg").getText(),
				"The profile for " + firstName + " " + lastName + " has been updated.",
				"Assertion Failed: Success Message for successful updation of user profile does not displayed");
		logMessage("Assertion Passed: Success Message for successful updation of user profile is displayed");
		clickOnContinue();
	}

	/**
	 * This method clicks on 'Show all Patients' checkbox
	 */
	public void clickOnShowAllPatients() {
		element("inp_showAll").click();
		logMessage("User clicks on Show all Patients checkbox");
	}

	/**
	 * This method clicks on 'Authorize Prescription' checkbox
	 */
	public void clickOnAuthorizePrescription() {
		element("inp_canSubmit").click();
		logMessage("User clicks on Authorize prescription checkbox");
	}

	/**
	 * This method clicks on 'Administrator' checkbox
	 */
	public void clickOnAdministrator() {
		element("inp_isAdmin").click();
		logMessage("User clicks on Administrator checkbox");
	}

	/**
	 * This method unchecks 'Administrator' checkbox
	 */
	public void uncheckAdministrator() {
		if (element("inp_isAdmin").isSelected())
			element("inp_isAdmin").click();
		logMessage("User unchecks Administrator checkbox");
	}

	/**
	 * This method unchecks 'Authorize Prescription' checkbox
	 */
	public void uncheckAuthorizePrescription() {
		if (element("inp_canSubmit").isSelected())
			element("inp_canSubmit").click();
		logMessage("User unchecks Authorize prescription checkbox");
	}

	/**
	 * This method unchecks 'Show all Patients' checkbox
	 */
	public void uncheckShowAllPatients() {
		if (element("inp_showAll").isSelected())
			element("inp_showAll").click();
		logMessage("User unchecks Show all Patients checkbox");
	}

	/**
	 * This method assigns all the permissions to the user
	 */
	public void assignAllPermissions() {
		clickOnAdministrator();
		clickOnAuthorizePrescription();
		clickOnShowAllPatients();
	}

	/**
	 * This method assigns all the permissions to the user
	 */
	public void uncheckAllPermissions() {
		uncheckAdministrator();
		uncheckAuthorizePrescription();
		uncheckShowAllPatients();
	}

	/**
	 * This method clicks on 'Back' button to view Practice
	 */
	public void clickOnBackButton() {
		clickUsingXpathInJavaScriptExecutor(element("btn_back"));
		logMessage("User clicks on 'Back' button");
	}

	/**
	 * This method clicks on Nurses tab
	 */
	public void clickOnNursesTab() {
		wait.waitForStableDom(250);
		element("li_nursesTab").click();
		logMessage("User clicks on Nurses tab");
	}

	/**
	 * This method clicks on Physicians tab
	 */
	public void clickOnPhysiciansTab() {
		wait.waitForStableDom(250);
		element("li_physicianTab").click();
		logMessage("User clicks on Physicians tab");
	}

	/**
	 * This method verifies that user is displayed under given tabName
	 * 
	 * @param tabName
	 * @param firstName
	 */
	public void verifyUserIsDisplayed(String tabName, String firstName) {
		boolean found = false;
		if (tabName.equalsIgnoreCase("Nurses"))
			clickOnNursesTab();
		else
			clickOnPhysiciansTab();
		for (WebElement userName : elements("td_userList")) {
			if (userName.getText().contains(firstName)) {
				found = true;
				break;
			}
		}
		Assert.assertTrue(found, "Assertion Failed: User '" + firstName + "' is not displayed under Nurses tab");
		logMessage("Assertion Passed: User '" + firstName + "' is displayed under Nurses tab");
	}

	/**
	 * This method verifies that user is not displayed under given tabName
	 * 
	 * @param tabName
	 * @param firstName
	 */
	public void verifyUserIsNotDisplayed(String tabName, String firstName) {
		boolean found = true;
		if (tabName.equalsIgnoreCase("Nurses"))
			clickOnNursesTab();
		else
			clickOnPhysiciansTab();
		for (WebElement userName : elements("td_userList")) {
			if (userName.getText().contains(firstName)) {
				found = false;
				break;
			}
		}
		Assert.assertTrue(found, "Assertion Failed: User '" + firstName + "' is displayed under Nurses tab");
		logMessage("Assertion Passed: User '" + firstName + "' is not displayed under Nurses tab");
	}

	/**
	 * This method validates user is displayed under Nurses while user is not
	 * displayed under Physicians
	 * 
	 * @param firstName
	 */
	public void verifyUserUnderNurses(String firstName) {
		verifyUserIsNotDisplayed("Physicians", firstName);
		verifyUserIsDisplayed("Nurses", firstName);
	}

	/**
	 * This method validates that user is displayed under Physicians while user
	 * is not displayed under Nurses
	 * 
	 * @param firstName
	 */
	public void verifyUserUnderPhysicians(String firstName) {
		verifyUserIsNotDisplayed("Nurses", firstName);
		verifyUserIsDisplayed("Physicians", firstName);
	}

	/**
	 * This method selects the user
	 * 
	 * @param patientName
	 */
	public void selectUserByName(String firstName) {
		for (WebElement userName : elements("td_userList")) {
			if (userName.getText().contains(firstName)) {
				userName.click();
				logMessage("User clicks on username");
				break;
			}
		}
	}

	/**
	 * This method verifies that administrator, Authorize Prescription, Show All
	 * Patients is selected
	 */
	public void verifyAdminAuthorizeShowAllPatientsAreChecked() {
		verifyAdministratorIsChecked();
		verifyAuthorizePrescriptionsChecked();
		verifyShowAllPatientsIsChecked();
	}

	/**
	 * This method verifies that administrator and Authorize Prescription is not
	 * selected while Show All Patients is selected
	 */
	public void verifyAdminAuthorizeShowAllPatients() {
		verifyAdministratorIsUnchecked();
		verifyAuthorizePrescriptionsIsUnchecked();
		verifyShowAllPatientsIsChecked();
	}

	/**
	 * This method verifies that administrator is not selected
	 */
	private void verifyAdministratorIsUnchecked() {
		wait.waitForStableDom(250);
		Assert.assertFalse(element("inp_isAdmin").isSelected(), "Assertion Failed: Administrator checkbox is selected");
		logMessage("Assertion Passed: Administrator checkbox is not selected");
	}

	/**
	 * This method verifies that Authorize Prescription is not selected
	 */
	private void verifyAuthorizePrescriptionsIsUnchecked() {
		Assert.assertFalse(element("inp_canSubmit").isSelected(),
				"Assertion Failed: Authorize Prescription checkbox is selected");
		logMessage("Assertion Passed: Authorize Prescription checkbox is not selected");
	}

	/**
	 * This method verifies that Administrator is selected
	 */
	private void verifyAdministratorIsChecked() {
		wait.waitForStableDom(250);
		Assert.assertTrue(element("inp_isAdmin").isSelected(),
				"Assertion Failed: Administrator checkbox is not selected");
		logMessage("Assertion Passed: Administrator checkbox is selected");
	}

	/**
	 * This method verifies that Authorize Prescription is selected
	 */
	private void verifyAuthorizePrescriptionsChecked() {
		Assert.assertTrue(element("inp_canSubmit").isSelected(),
				"Assertion Failed: Authorize Prescription checkbox is not selected");
		logMessage("Assertion Passed: Authorize Prescription checkbox is selected");
	}

	/**
	 * This method verifies that show all Patients is selected
	 */
	private void verifyShowAllPatientsIsChecked() {
		Assert.assertTrue(element("inp_showAll").isSelected(),
				"Assertion Failed: Show All Patients checkbox is not selected");
		logMessage("Assertion Passed: Show All Patients checkbox is selected");
	}

	/**
	 * This method validates users Prescription Notification Preferences
	 * defaults to 'No alerts'
	 */
	public void verifyDefaultPresNotificationPref() {
		Assert.assertEquals(getProvidedTextFromDropDown(element("select_presNotificationPrefs")), "No alerts",
				"Assertion Failed: Prescription Notification Preferences does not defaults to 'No alerts'");
		logMessage("Assertion Passed: Prescription Notification Preferences defaults to 'No alerts'");
	}

	/**
	 * This method validates users PA Notification Preferences defaults to 'No
	 * alerts'
	 */
	public void verifyDefaultPANotificationPrefs() {
		Assert.assertEquals(getProvidedTextFromDropDown(element("select_paNotificationPrefs")), "No alerts",
				"Assertion Failed: PA Notification Preferences does not defaults to 'No alerts'");
		logMessage("Assertion Passed: PA Notification Preferences defaults to 'No alerts'");
	}
}
