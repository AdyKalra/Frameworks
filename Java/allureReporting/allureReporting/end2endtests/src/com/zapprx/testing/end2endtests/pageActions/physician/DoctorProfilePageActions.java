package com.zapprx.testing.end2endtests.pageActions.physician;

import static com.zapprx.testing.end2endtests.automation.utils.CustomFunctions.getStringWithTimestamp;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;
import com.zapprx.testing.end2endtests.automation.utils.YamlReader;

public class DoctorProfilePageActions extends GetPage {
	WebDriver driver;
	private String firstName, emailId;

	public DoctorProfilePageActions(WebDriver driver) {
		super(driver, "physician/DoctorProfilePage");
		this.driver = driver;
		custom.setDoctorFirstName();
		custom.setDoctorEmailId();
	}

	/**
	 * This method enter value in all required fields to add a new doctor
	 * 
	 * @param state
	 */
	public String[] enterDetailsToAddNewDoctor(String state, String lastName, String password, String faxNo) {
		String[] arrayForFirstNameEmailId = new String[2];
		firstName = enterFirstName();
		enterLastName(lastName);
		enterStateLicenseNo();
		enterDEANumber();
		enterNPINumber();
		enterAddress();
		enterCity();
		selectState(state);
		enterZip();
		emailId = enterEmail(firstName);
		enterFaxNo(faxNo);
		enterDegree();
		enterSpecialty();
		enterPassword(password);
		enterConfirmPassword(password);
		enterDoctorSignature();
		arrayForFirstNameEmailId[0] = firstName;
		arrayForFirstNameEmailId[1] = emailId;
		return arrayForFirstNameEmailId;
	}

	/**
	 * This method enters value in First Name field
	 */
	private String enterFirstName() {
		custom.setDoctorFirstName();
		String firstName = custom.getDoctorFirstName();
		wait.waitForLoaderToDisappear();
		enterText(element("inp_firstName"), firstName);
		logMessage("User enters text in FirstName field");
		return firstName;
	}

	/**
	 * This method enters value in Last Name field
	 */
	private void enterLastName(String lastName) {
		enterText(element("inp_lastName"), lastName);
		logMessage("User enters text in LastName field");
	}

	/**
	 * This method enters value in State License Number
	 */
	private void enterStateLicenseNo() {
		enterText(element("inp_licNo"), getStringWithTimestamp("").concat(getStringWithTimestamp("")));
		logMessage("User enters text in License Number field");
	}

	/**
	 * This method enters value in DEA Number field
	 */
	private void enterDEANumber() {
		enterText(element("inp_deaNo"), getStringWithTimestamp("").concat(getStringWithTimestamp("")));
		logMessage("User enters text in DEA Number field");
	}

	/**
	 * This method enters value in NPI Number field
	 */
	private void enterNPINumber() {
		enterText(element("inp_npiNo"), getStringWithTimestamp("").concat(getStringWithTimestamp("")));
		logMessage("User enters text in NPI Number field");
	}

	/**
	 * This method enters value in Address field
	 */
	private void enterAddress() {
		enterText(element("inp_address"), getStringWithTimestamp(""));
		logMessage("User enters text in Address field");
	}

	/**
	 * This method enters value in City field
	 */
	private void enterCity() {
		enterText(element("inp_city"), getStringWithTimestamp(""));
		logMessage("User enters text in City field");
	}

	/**
	 * This method enters value in State field
	 */
	private void selectState(String state) {
		selectProvidedTextFromDropDown(element("select_state"), state);
		logMessage("User selects value from dropdown in State field");
	}

	/**
	 * This method enters value in Zip field
	 */
	private void enterZip() {
		enterText(element("inp_zip"), getStringWithTimestamp(""));
		logMessage("User enters text in Zip field");
	}

	/**
	 * This method enters value in Email field
	 */
	private String enterEmail(String firstname) {
		String emailId = firstName.concat(YamlReader.getYamlValue("physician.lastName").concat("@zapprx.com"));
		// String emailId = custom.getDoctorEmailId();
		enterText(element("inp_email"), emailId);
		logMessage("User enters " + custom.getDoctorEmailId() + " in Email field");
		return emailId;
	}

	/**
	 * This method enters value in Fax No field
	 * 
	 * @param faxNo
	 */
	private void enterFaxNo(String faxNo) {
		enterText(element("inp_faxNo"), faxNo);
		logMessage("User enters " + faxNo + " in Fax field");
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
	 * This method enters value in Password field
	 */
	private void enterPassword(String password) {
		enterText(element("inp_psswrd"), password);
		logMessage("User enters text in Password field");
	}

	/**
	 * This method enters value in Confirm Password field
	 */
	private void enterConfirmPassword(String confirmPassword) {
		enterText(element("inp_cnfrmPasswd"), confirmPassword);
		logMessage("User enters text in Confirm Password field");
	}

	/**
	 * This method enters doctor signature
	 * 
	 * @param signature
	 */
	private void enterDoctorSignature() {
		element("link_drwSig").click();
		logMessage("User clicks on draw signature");
		drawSignatureOnCanvas(element("canvas_sig"));
	}

	/**
	 * This method save a new doctor, verify the success message and close the
	 * dialog box
	 * 
	 * @param successMsg
	 */
	public void saveVerifyAndClose(String successMsg, String lastName) {
		clickOnSaveNewDoctor();
		verifyNewDoctorIsCreated(successMsg, lastName);
		clickOnClose();
	}

	/**
	 * This method clicks on Save New Doctor
	 */
	public void clickOnSaveNewDoctor() {
		element("btn_save").click();
		logMessage("User clicks on Save New Doctor");
	}

	/**
	 * This method verifies success message on creation of a doctor
	 * 
	 * @param successMsg
	 */
	public void verifyNewDoctorIsCreated(String successMsg, String lastName) {
		Assert.assertTrue(
				element("p_successMsg").getText()
						.contains(successMsg + " " + custom.getDoctorFirstName() + " " + lastName),
				"Assertion Failed: Success Message for successful creation of doctor does not display");
		logMessage("Assertion Passed: Success Message for successful creation of doctor is displayed");
	}

	/**
	 * This method clicks on Close
	 */
	private void clickOnClose() {
		wait.waitForElementToBeClickable(element("div_close"));
		element("div_close").click();
		logMessage("User clicks on Close");
		wait.waitForElementToBeInVisible(getLocator("div_close"));
	}

	/**
	 * This method verifies that administrator and show all patients is not
	 * selected
	 */
	public void verifyAdminAndShowAllPatients() {
		verifyAdministratorIsUnchecked();
		verifyShowAllPatientsIsUnchecked();
	}

	/**
	 * This method verifies that administrator is not selected
	 */
	private void verifyAdministratorIsUnchecked() {
		wait.waitForStableDom(250);
		Assert.assertTrue(element("inp_isAdmin").getAttribute("class").contains("ng-empty"),
				"Assertion Failed: Administrator checkbox is selected");
		logMessage("Assertion Passed: Administrator checkbox is not selected");
	}

	/**
	 * This method verifies that show all patients is not selected
	 */
	private void verifyShowAllPatientsIsUnchecked() {
		Assert.assertTrue(element("inp_showAll").getAttribute("class").contains("ng-empty"),
				"Assertion Failed: Show All Patients checkbox is selected");
		logMessage("Assertion Passed: Show All Patients checkbox is not selected");
	}

	/**
	 * This method verifies value for fax number after saving the profile
	 * 
	 * @param faxNo
	 */
	public void verifyFaxNoIsSavedCorrectly(String faxNo) {
		wait.waitForStableDom(250);
		try {
			isElementDisplayed(("inp_faxNo"));
		} catch (StaleElementReferenceException e) {
			isElementDisplayed(("inp_faxNo"));
		}
		Assert.assertEquals(element("inp_faxNo").getAttribute("value"), faxNo, "Assertion Failed: Fax Number value "
				+ element("inp_faxNo").getAttribute("value") + " is displayed incorrect");
		logMessage("Assertion Passed: Fax Number value " + element("inp_faxNo").getAttribute("value")
				+ " is displayed correctly");
	}

	/**
	 * This method selects clinician role
	 */
	public void enterClinicianRole(String role) {
		selectProvidedTextFromDropDown(element("select_clinicianRole"), role);
		logMessage("User selects clinician role '" + role + "' from dropdown");
	}

	/**
	 * This method validates maximum number of character count in License, DEA
	 * and NPI numder field
	 */
	public void verifyNumderOfCharactersInLicenceDEAAndNPI() {
		verifyNumberOfCharactersInLicenseField();
		verifyNumberOfCharactersInDEAField();
		verifyNumberOfCharactersInNPIField();
	}

	/**
	 * This method validates maximum number of character count in License numder
	 * field
	 */
	private void verifyNumberOfCharactersInLicenseField() {
		wait.waitForStableDom(250);
		isElementDisplayed("inp_licNo");
		Assert.assertTrue(element("inp_licNo").getAttribute("value").length() == 20,
				"Assertion Failed: More than 20 characters can be entered in License Number field");
		logMessage("Assertion Passed: Not more than 20 characters can be entered in License Number field");
	}

	/**
	 * This method validates maximum number of character count in DEA numder
	 * field
	 */
	private void verifyNumberOfCharactersInDEAField() {
		Assert.assertTrue(element("inp_deaNo").getAttribute("value").length() == 9,
				"Assertion Failed: More than 9 characters can be entered in DEA Number field");
		logMessage("Assertion Passed: Not more than 9 characters can be entered in DEA Number field");
	}

	/**
	 * This method validates maximum number of character count in NPI numder
	 * field
	 */
	private void verifyNumberOfCharactersInNPIField() {
		Assert.assertTrue(element("inp_npiNo").getAttribute("value").length() == 10,
				"Assertion Failed: More than 10 characters can be entered in DEA Number field");
		logMessage("Assertion Passed: Not more than 10 characters can be entered in DEA Number field");
	}

	/**
	 * This method clicks on 'Administrator' checkbox
	 */
	public void clickOnAdministrator() {
		element("inp_isAdmin").click();
		logMessage("User clicks on Administrator checkbox");
	}

	/**
	 * This method clicks on 'Authorize Prescription' checkbox
	 */
	public void clickOnAuthorizePrescription() {
		element("inp_canSubmit").click();
		logMessage("User clicks on Can Authorize checkbox");
	}

	/**
	 * This method clicks on 'Show all Patients' checkbox
	 */
	public void clickOnShowAllPatients() {
		element("inp_showAll").click();
		logMessage("User clicks on Show all Patients checkbox");
	}

	/**
	 * This method assigns permissions to the user
	 */
	public void assignPermissions() {
		clickOnAdministrator();
		clickOnAuthorizePrescription();
		clickOnShowAllPatients();
	}

	/**
	 * This method validates users Prescription Notification Preferences
	 * defaults to 'No alerts'
	 */
	public void verifyDefaultPresNotificationPref() {
		Assert.assertEquals(element("option_presNotificationPrefs").getText(), "No alerts",
				"Assertion Failed: Prescription Notification Preferences does not defaults to 'No alerts'");
		logMessage("Assertion Passed: Prescription Notification Preferences defaults to 'No alerts'");
	}

	/**
	 * This method validates users PA Notification Preferences defaults to 'No
	 * alerts'
	 */
	public void verifyDefaultPANotificationPrefs() {
		Assert.assertEquals(element("option_paNotificationPrefs").getText(), "No alerts",
				"Assertion Failed: PA Notification Preferences does not defaults to 'No alerts'");
		logMessage("Assertion Passed: PA Notification Preferences defaults to 'No alerts'");
	}
}
