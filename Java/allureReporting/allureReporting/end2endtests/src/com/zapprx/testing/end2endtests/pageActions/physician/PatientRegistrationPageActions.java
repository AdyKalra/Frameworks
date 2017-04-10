package com.zapprx.testing.end2endtests.pageActions.physician;

import static com.zapprx.testing.end2endtests.automation.utils.CustomFunctions.getCurrentDateWithSep;
import static com.zapprx.testing.end2endtests.automation.utils.CustomFunctions.getDateWithSeparator;
import static com.zapprx.testing.end2endtests.automation.utils.CustomFunctions.getRandomIntegerValue;
import static com.zapprx.testing.end2endtests.automation.utils.CustomFunctions.getStringWithTimestamp;
import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class PatientRegistrationPageActions extends GetPage {
	WebDriver driver;
	private String lastName, loginEmail, dob;

	public PatientRegistrationPageActions(WebDriver driver) {
		super(driver, "physician/PatientRegistrationPage");
		this.driver = driver;
	}

	// *****************************************GeneralInformation**********************************************//
	/**
	 * This method verifies all mandatory fields on General Page
	 */
	public void verifyMandatoryFieldsOnGenPage() {
		verifyMandatoryFieldsForPatientIdentification();
		verifyMandatoryFieldsForPatientStatistics();
		verifyMandatoryFieldsForHomeAddress();
		verifyMandatoryFieldsForEmergencyContactInfo();
	}

	/**
	 * This method verify all the mandatory fields under Patient Identification
	 * on General Page
	 */
	private void verifyMandatoryFieldsForPatientIdentification() {
		Assert.assertTrue(element("inp_requiredField", element("inp_firstName").getAttribute("id"))
				.getAttribute("class").contains("required"), "Assertion Failed: 'FirstName' is not a mandatory field");
		logMessage("Assertion Passed: 'FirstName' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_lastName").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'LastName' is not a mandatory field");
		logMessage("Assertion Passed: 'LastName' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_dob").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Date of Birth' is not a mandatory field");
		logMessage("Assertion Passed: 'Date of Birth' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_loginEmail").getAttribute("id"))
				.getAttribute("class").contains("required"), "Assertion Failed: 'loginEmail' is not a mandatory field");
		logMessage("Assertion Passed: 'loginEmail' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_password").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Password' is not a mandatory field");
		logMessage("Assertion Passed: 'Password' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_confirmpassword").getAttribute("id"))
				.getAttribute("class").contains("required"),
				"Assertion Failed: 'Confirm Password' is not a mandatory field");
		logMessage("Assertion Passed: 'Confirm Password' is a mandatory field");
	}

	/**
	 * This method verify all the mandatory fields under Patient Statistics on
	 * General Page
	 */
	public void verifyMandatoryFieldsForPatientStatistics() {
		Assert.assertTrue(element("select_requiredField", element("select_gender").getAttribute("id"))
				.getAttribute("class").contains("required"), "Assertion Failed: 'Gender' is not a mandatory field");
		logMessage("Assertion Passed: 'Gender' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_height").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Height' is not a mandatory field");
		logMessage("Assertion Passed: 'Height' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_weight").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Weight' is not a mandatory field");
		logMessage("Assertion Passed: 'Weight' is a mandatory field");
	}

	/**
	 * This method verify all the mandatory fields under Home Address on General
	 * Page
	 */
	private void verifyMandatoryFieldsForHomeAddress() {
		Assert.assertTrue(element("inp_requiredField", element("inp_address").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Address 1' is not a mandatory field");
		logMessage("Assertion Passed: 'Address 1' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_city").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'City' is not a mandatory field");
		logMessage("Assertion Passed: 'City' is a mandatory field");
		Assert.assertTrue(element("select_requiredField", element("select_state").getAttribute("id"))
				.getAttribute("class").contains("required"), "Assertion Failed: 'State' is not a mandatory field");
		logMessage("Assertion Passed: 'State' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_email").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Email' is not a mandatory field");
		logMessage("Assertion Passed: 'Email' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_mainPhone").getAttribute("id"))
				.getAttribute("class").contains("required"), "Assertion Failed: 'Main Phone' is not a mandatory field");
		logMessage("Assertion Passed: 'Main Phone' is a mandatory field");
	}

	/**
	 * This method verify all the mandatory fields under Emergency Contact
	 * Information on General Page
	 */
	private void verifyMandatoryFieldsForEmergencyContactInfo() {
		Assert.assertTrue(element("inp_requiredField", element("inp_contactName").getAttribute("id"))
				.getAttribute("class").contains("required"),
				"Assertion Failed: 'Contact Name' is not a mandatory field");
		logMessage("Assertion Passed: 'Contact Name' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_relationship").getAttribute("id"))
				.getAttribute("class").contains("required"),
				"Assertion Failed: 'Relationship' is not a mandatory field");
		logMessage("Assertion Passed: 'Relationship' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_phone").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Phone' is not a mandatory field");
		logMessage("Assertion Passed: 'Phone' is a mandatory field");
	}

	/**
	 * This method enters general info without entering details for patient
	 * statistics
	 */
	public void enterGeneralInfoWithoutPatientStatistics(String state, String password, String firstName) {
		enterMedicalRecordNo();
		enterLoginEmailId(firstName);
		enterPasswordForPatientIdentification(password, lastName);
		enterInfoForHomeAddress(state);
		enterInfoForEmergencyContact();
		clickOnAddInsurance();
	}

	/**
	 * This method enter info in all the required fields on General Page
	 * 
	 * @param state
	 */
	public String[] enterGeneralInfo(String state, String password, String firstName) {
		String mrnNo = enterMedicalRecordNo();
		String loginEmail = enterLoginEmailId(firstName);
		enterPasswordForPatientIdentification(password, lastName);
		enterInfoForPatientStatistics();
		enterInfoForHomeAddress(state);
		enterInfoForEmergencyContact();
		clickOnAddInsurance();
		return new String[] { loginEmail, mrnNo };
	}

	/**
	 * This method enters general information for mandatory fields
	 * 
	 * @param state
	 * @param password
	 * @param firstName
	 * @return
	 */
	public String enterMinimumGeneralInfo(String state, String password, String firstName) {
		String loginEmail = enterLoginEmailId(firstName);
		enterPasswordForPatientIdentification(password, lastName);
		enterInfoForHomeAddWithoutContactEmail(state);
		enterInfoForEmergencyContact();
		clickOnAddInsurance();
		return loginEmail;
	}

	/**
	 * This method clicks on Next button for Patient Info
	 */
	public void clickOnNextButtonForPatientInfo() {
		element("btn_next").click();
		logMessage("User clicks on next button");
	}

	/**
	 * This method enter details for all required fields on General Information
	 * Page under Patient Identification
	 */
	public String[] registerNewPatient(String firstName) {
		enterFirstNameLastNameDob(firstName);
		String gender = custom.getGender();
		selectGender(gender);
		clickOnSavePatient();
		return new String[] { lastName, dob, gender };
	}

	/**
	 * This method enter details for all required fields on General Information
	 * Page under Patient Identification
	 */
	public String[] registerNewMalePatient(String firstName) {
		enterFirstNameLastNameDob(firstName);
		String gender = "Male";
		selectGender(gender);
		clickOnSavePatient();
		return new String[] { lastName, dob, gender };
	}

	/**
	 * This method enters firstName lastName and Dob
	 * 
	 * @param firstName
	 * @return
	 */
	public String[] enterFirstNameLastNameDob(String firstName) {
		custom.setPatientLastName();
		lastName = custom.getPatientLastName();
		dob = getDateWithSeparator();
		enterText(element("inp_firstName"), firstName);
		logMessage("User enter " + firstName + " in First Name field");
		enterText(element("inp_lastName"), lastName);
		logMessage("User enter " + lastName + " in Last Name field");
		wait.waitForElementToBeClickable(element("inp_dob", dob));
		enterText(element("inp_dob"), dob);
		logMessage("User enter date " + dob + " in Date of Birth field");
		return new String[] { lastName, dob };
	}

	/**
	 * This method enters gender
	 * 
	 * @param gender
	 */
	public void selectGender(String gender) {
		selectProvidedTextFromDropDown(element("select_gender"), gender);
		logMessage("User selects " + gender + " from Gender dropdown");
	}

	/**
	 * This method enters login email id for new patient
	 * 
	 * @param firstName
	 */
	public String enterLoginEmailId(String firstName) {
		loginEmail = firstName + lastName + "@zapprx.com";
		enterText(element("inp_loginEmail"), loginEmail);
		logMessage("User enter " + loginEmail + " in login Email field");
		return loginEmail;
	}

	/**
	 * This method enters login email id for new patient
	 * 
	 * @param firstName
	 */
	public void enterLoginEmailId(String firstName, String emailId) {
		enterText(element("inp_loginEmail"), loginEmail);
		logMessage("User enter " + loginEmail + " in login Email field");
	}

	/**
	 * This method enters password for patient identification
	 * 
	 * @param password
	 * @param lastName
	 */
	public void enterPasswordForPatientIdentification(String password, String lastName) {
		enterText(element("inp_password"), password);
		logMessage("User enter value in Password field");
		enterText(element("inp_confirmpassword"), password);
		logMessage("User enter value in Confirm Password field");
	}

	/**
	 * This method clicks on save patient
	 */
	public void clickOnSavePatient() {
		wait.waitForElementToBeVisible(element("btn_savePatient"));
		executeJavascript("document.getElementById('quick-register-save').click();");
		logMessage("User clicks on Save Patient");
	}

	/**
	 * This method clicks on checkbox if patient does not have email address
	 */
	public void hideEmailPasswrdField() {
		element("span_chckPatNoEmail").click();
		logMessage("User clicks on checkbox if patient does not have email address");
	}

	/**
	 * This method verifies height/weight will be required if weight_units
	 * selected, and vice versa
	 */
	public void verifyHeightWeightReqFields() {
		enterHeightAndWeight();
		verifyHeightWeightUnitsIsRequiredField();
		clearHeightAndWeight();
		selectHeightAndWeightUnits();
		verifyHeightWeightIsRequiredField();
	}

	/**
	 * This method enters values in height and weight fields
	 */
	public void enterHeightAndWeight() {
		enterText(element("inp_height"), "" + getRandomIntegerValue());
		logMessage("User enter value in Height field");
		enterText(element("inp_weight"), "" + getRandomIntegerValue());
		logMessage("User enter value in Weight field");
	}

	/**
	 * This method clears values of height and weight fields
	 */
	public void clearHeightAndWeight() {
		element("inp_height").clear();
		element("inp_weight").clear();
	}

	/**
	 * This method selects height and weight units
	 */
	public void selectHeightAndWeightUnits() {
		selectProvidedTextFromDropDown(element("select_heightUnits"),
				getYamlValue("physician.generalInfo.heightUnits"));
		logMessage("User selects " + getYamlValue("physician.generalInfo.heightUnits") + " from Height Units dropdown");
		selectProvidedTextFromDropDown(element("select_weightUnits"),
				getYamlValue("physician.generalInfo.weightUnits"));
		logMessage("User selects " + getYamlValue("physician.generalInfo.weightUnits") + " from Weight Units dropdown");
	}

	/**
	 * This method enter details for all required fields on General Information
	 * Page under Patient Statistics
	 */
	public void enterInfoForPatientStatistics() {
		enterHeightAndWeight();
		selectHeightAndWeightUnits();
	}

	/**
	 * This method verifies 'Height Units' and 'Weight Units' is required field
	 * after entering values in height and weight fields
	 */
	public void verifyHeightWeightUnitsIsRequiredField() {
		Assert.assertTrue(element("select_heightUnits").getAttribute("required").equalsIgnoreCase("true"),
				"Assertion Failed: 'Height Units' is not a mandatory field");
		logMessage("Assertion Passed: 'Height Units' is a mandatory field");
		Assert.assertTrue(element("select_weightUnits").getAttribute("required").equalsIgnoreCase("true"),
				"Assertion Failed: 'Weight Units' is not a mandatory field");
		logMessage("Assertion Passed: 'Weight Units' is a mandatory field");
	}

	/**
	 * This method verifies 'Height' and 'Weight' is required field after
	 * selecting units for 'Height Units' and 'Weight Units' fields
	 */
	public void verifyHeightWeightIsRequiredField() {
		Assert.assertTrue(element("inp_height").getAttribute("required").equalsIgnoreCase("true"),
				"Assertion Failed: 'Height' is not a mandatory field");
		logMessage("Assertion Passed: 'Height' is a mandatory field");
		Assert.assertTrue(element("inp_weight").getAttribute("required").equalsIgnoreCase("true"),
				"Assertion Failed: 'Weight' is not a mandatory field");
		logMessage("Assertion Passed: 'Weight' is a mandatory field");
	}

	/**
	 * This method enters value in medical record number
	 */
	private String enterMedicalRecordNo() {
		isElementDisplayed("inp_mrn");
		String mrnNo = getStringWithTimestamp("MedicalNo");
		enterText(element("inp_mrn"), mrnNo);
		logMessage("Usr enters value in medical record number");
		return mrnNo;
	}

	/**
	 * This method enter details for all required fields on General Information
	 * Page under Home Address
	 */
	public String[] enterInfoForHomeAddress(String state) {
		logMessage("User enters name in Contact Name field");
		String address = getStringWithTimestamp("address");
		enterText(element("inp_address"), address);
		logMessage("User enter text in Address1 field");
		String city = getStringWithTimestamp("city");
		enterText(element("inp_city"), city);
		logMessage("User enter text in City field");
		selectProvidedTextFromDropDown(element("select_state"), state);
		logMessage("User selects " + state + " from State dropdown");
		String zip = getStringWithTimestamp("").substring(0, 5);
		enterText(element("inp_zip"), zip);
		logMessage("User enter value in Zip field");
		enterText(element("inp_email"), loginEmail);
		logMessage("User enter value in" + loginEmail + "Email field");
		String phone = getStringWithTimestamp("").substring(0, 11);
		enterText(element("inp_mainPhone"), phone);
		logMessage("User enter value in Main Phone field");
		return new String[] { address, city, state, zip, loginEmail, phone };
	}

	/**
	 * This method verifies that 'Contact Name' is prefilled with patient name
	 * 
	 * @param firstname
	 * @param lastname
	 */
	public void verifyContactName(String firstname, String lastname) {
		Assert.assertEquals(element("inp_patContactName").getAttribute("value"), firstname + " " + lastname,
				"Assertion Failed: 'Contact Name' is not prefilled with patient name");
		logMessage("Assertion Passed: 'Contact Name' is prefilled with patient name");
	}

	/**
	 * This method enters details for all required field under Home Address
	 * 
	 * @param state
	 */
	private void enterInfoForHomeAddWithoutContactEmail(String state) {
		enterText(element("inp_address"), getStringWithTimestamp("address"));
		logMessage("User enter text in Address1 field");
		enterText(element("inp_city"), getStringWithTimestamp("city"));
		logMessage("User enter text in City field");
		selectProvidedTextFromDropDown(element("select_state"), state);
		logMessage("User selects " + state + " from State dropdown");
		enterText(element("inp_zip"), getStringWithTimestamp(""));
		logMessage("User enter value in Zip field");
		enterText(element("inp_mainPhone"), getStringWithTimestamp(""));
		logMessage("User enter value in Main Phone field");
	}

	/**
	 * This method enter details for all required fields on General Information
	 * Page under Emergency Contact Information
	 */
	public void enterInfoForEmergencyContact() {
		enterText(element("inp_contactName"), getStringWithTimestamp("contactName"));
		logMessage("User enter value in Contact Name field");
		enterText(element("inp_relationship"), getStringWithTimestamp(""));
		logMessage("User enter value in Relationship field");
		enterText(element("inp_phone"), getStringWithTimestamp(""));
		logMessage("User enter value in Phone field");
	}

	/**
	 * This method clicks on Create Patient & Add Insurance
	 */
	public void clickOnAddInsurance() {
		element("btn_next", "Add Insurance").click();
		logMessage("User clicks on Add Insurance button");
	}

	/**
	 * This method validates that email id already exists
	 */
	public void verifyExistingEmailMsg(String existingEmailMsg) {
		isElementDisplayed("p_invalidEmailMsg");
		wait.waitForElementTextToContain(element("p_invalidEmailMsg"), existingEmailMsg);
		Assert.assertEquals(element("p_invalidEmailMsg").getText(), existingEmailMsg,
				"Assertion Failed: Message for exsiting EmailId is not displayed");
		logMessage("Assertion Passed: Message for exsiting EmailId is displayed");
	}

	// *********************************************InsuranceInformation***********************************************//
	/**
	 * This method verify all mandatory fields on Insurance Information
	 */
	public void verifyMandatoryFieldsOnInsuranceInfo() {
		Assert.assertTrue(
				element("inp_requiredField", element("inp_insField", "Primary Insurance", "Carrier").getAttribute("id"))
						.getAttribute("class").contains("required"),
				"Assertion Failed: 'Insurance Company Name' is not the mandatory field");
		logMessage("Assertion Passed: 'Insurance Company Name' is the mandatory field");
		Assert.assertTrue(
				element("inp_requiredField", element("inp_insField", "Primary Insurance", "State").getAttribute("id"))
						.getAttribute("class").contains("required"),
				"Assertion Failed: 'State' is not the mandatory field");
		logMessage("Assertion Passed: 'State' is the mandatory field");
		Assert.assertTrue(
				element("inp_requiredField", element("inp_insField", "Primary Insurance", "Phone").getAttribute("id"))
						.getAttribute("class").contains("required"),
				"Assertion Failed: 'Phone' is not the mandatory field");
		logMessage("Assertion Passed: 'Phone' is the mandatory field");
		Assert.assertTrue(
				element("inp_requiredField",
						element("inp_insField", "Primary Insurance", "Policy ID").getAttribute("id"))
								.getAttribute("class").contains("required"),
				"Assertion Failed: 'Policy ID' is not the mandatory field");
		logMessage("Assertion Passed: 'Policy ID' is the mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_insuranceGroupNo").getAttribute("id"))
				.getAttribute("class").contains("required"),
				"Assertion Failed: 'Group Number' is not the mandatory field");
		logMessage("Assertion Passed: 'Group Number' is the mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_rxBin").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Rx Bin' is not the mandatory field");
		logMessage("Assertion Passed: 'Rx Bin' is the mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_rxPCN").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Rx PCN' is not the mandatory field");
		logMessage("Assertion Passed: 'Rx PCN' is the mandatory field");
		Assert.assertTrue(
				element("select_requiredField", element("select_relationship").getAttribute("id")).getAttribute("class")
						.contains("required"),
				"Assertion Failed: 'Policy Holder Relationship' is not the mandatory field");
		logMessage("Assertion Passed: 'Policy Holder Relationship' is the mandatory field");
		Assert.assertTrue(
				element("inp_requiredField", element("inp_insuranceEmployer").getAttribute("id")).getAttribute("class")
						.contains("required"),
				"Assertion Failed: 'Policy Holder Employer Name' is not the mandatory field");
		logMessage("Assertion Passed: 'Policy Holder Employer Name' is the mandatory field");
	}

	/**
	 * This method enter details for all the required fields on Insurance
	 * Information
	 */
	public void enterInsuranceInfo(String insuranceName, String policyId, String groupNo, String state) {
		enterText(element("inp_insrncName", "Primary Insurance", "Carrier"), insuranceName);
		logMessage("User enter value " + insuranceName + " in Insurance Company Name field");
		selectProvidedTextFromDropDown(element("select_insField", "Primary Insurance", "State"), state);
		logMessage("User selects value" + state + " in State field");
		enterText(element("inp_insField", "Primary Insurance", "Phone"), "1111111111");
		logMessage("User enter value Phone field");
		enterText(element("inp_insField", "Primary Insurance", "Policy ID"), policyId);
		logMessage("User enter value in Policy Id field");
		enterText(element("inp_insField", "Primary Insurance", "Group Number"), groupNo);
		logMessage("User enter value " + groupNo + " in Group Number field");
		selectProvidedTextFromDropDown(element("select_insField", "Primary Insurance", "Policy Holder Relationship"),
				getYamlValue("physician.insuranceInfo.relationship"));
		logMessage("User selects " + getYamlValue("physician.insuranceInfo.relationship")
				+ " from 'Policy Holder Relationship' dropdown");
		enterText(element("inp_insField", "Primary Insurance", "Policy Holder Employer Name"),
				"Policy Holder Employer Name");
		logMessage("User enter value in Policy Holder Employer Name field");
	}

	/**
	 * This method enters Insurance Name
	 * 
	 * @param insuranceName
	 */
	public void enterInsuranceName(String insuranceName) {
		enterText(element("inp_insrncName", "Primary Insurance", "Carrier"), insuranceName);
		logMessage("User enter value " + insuranceName + " in Insurance Company Name field");

	}

	/**
	 * This method verifies state field on Insurance Information page
	 */
	public void verifyStateFieldOnInsuranceInfo() {
		Assert.assertTrue(element("select_insField", "Primary Insurance", "State").isDisplayed(),
				"Assertion Failed: State field is not getting displayed on Insurance Information page");
		logMessage("Assertion Passed: State field is getting displayed on Insurance Information page");
	}

	/**
	 * This method clicks on Clinical
	 */
	public void clickOnClinical() {
		isElementDisplayed("btn_next", "Clinical");
		executeJavascript("document.getElementById('next_button').click()");
		logMessage("User clicks on Step 3: Clinical button");
	}

	/**
	 * This method verifies that Insurance Form is displayed
	 */
	public void verifyInsurFormHdrIsDisplayed(String insurance) {
		isElementDisplayed("hdr_txt", insurance);
	}

	/**
	 * This method verifies that Insurance Form is not displayed
	 */
	public void verifyInsurFormHdrIsNotDisplayed(String insurance) {
		isElementNotDisplayed("hdr_txt", insurance);
	}

	/**
	 * This method clicks on Clinical
	 */
	public void clickOnRemoveInsurancePBM(String insurance) {
		element("btn_removeInsurancePBM", insurance).click();
		logMessage("User clicks on Remove " + insurance);
	}

	// *************************************************MedicalHistory*********************************************//
	/**
	 * This method verifies mandatory fields on Medical History
	 */
	public void verifyMandatoryFieldsOnMedicalHistory() {
		Assert.assertTrue(
				element("select_requiredField", element("select_patient").getAttribute("id")).getAttribute("class")
						.contains("required"),
				"Assertion Failed: 'Is the Patient an in patient?' is not the mandatory field");
		logMessage("Assertion Passed: 'Is the Patient an in patient?' is the mandatory field");
		Assert.assertTrue(
				element("select_requiredField", element("select_diabetic").getAttribute("id")).getAttribute("class")
						.contains("required"),
				"Assertion Failed: 'Is the Patient diabetic?' is not the mandatory field");
		logMessage("Assertion Passed: 'Is the Patient diabetic?' is the mandatory field");
		Assert.assertTrue(
				element("select_requiredField", element("select_bloodPressure").getAttribute("id"))
						.getAttribute("class").contains("required"),
				"Assertion Failed: 'Does this patient have high blood pressure?' is not the mandatory field");
		logMessage("Assertion Passed: 'Does this patient have high blood pressure??' is the mandatory field");
		Assert.assertTrue(
				element("select_requiredField", element("select_latexAllergy").getAttribute("id")).getAttribute("class")
						.contains("required"),
				"Assertion Failed: 'Does this patient have latex allergy?' is not the mandatory field");
		logMessage("Assertion Passed: 'Does this patient have latex allergy??' is the mandatory field");
	}

	/**
	 * This method selects options for the required dropdown on Medical History
	 * Page
	 */
	public void enterMedicalHistory(String knownAllergy) {
		enterText(element("inp_knownDrugAllergy"), knownAllergy);
		logMessage("User enter value " + knownAllergy + " in Known Drug Allergies");
		// selectProvidedTextFromDropDown(element("select_patient"),
		// getYamlValue("physician.medicalHistory.in-patient"));
		// logMessage("User selects " +
		// getYamlValue("physician.medicalHistory.in-patient")
		// + " from 'Is the patient an in-patient?' dropdown");
		selectProvidedTextFromDropDown(element("select_diabetic"), getYamlValue("physician.medicalHistory.diabetic"));
		logMessage("User selects " + getYamlValue("physician.medicalHistory.diabetic")
				+ " from 'Is the patient diabetic?'  dropdown");
		selectProvidedTextFromDropDown(element("select_bloodPressure"),
				getYamlValue("physician.medicalHistory.bloodPressure"));
		logMessage("User selects " + getYamlValue("physician.medicalHistory.bloodPressure")
				+ " from 'Does the patient have high blood pressure?' dropdown");
		selectProvidedTextFromDropDown(element("select_latexAllergy"),
				getYamlValue("physician.medicalHistory.latexAllergy"));
		logMessage("User selects " + getYamlValue("physician.medicalHistory.latexAllergy")
				+ " from 'Does the patient have latex allergy?' dropdown");
	}

	/**
	 * This method selects indication
	 * 
	 * @param indication
	 */
	public void selectIndication(String indication) {
		selectProvidedTextFromDropDown(element("select_indication"), indication);
		logMessage("User selects indication from dropdown");
	}

	/**
	 * This method selects diagnosis
	 * 
	 * @param diagnosis
	 */
	private void selectDiagnosis(String diagnosis) {
		selectProvidedTextFromDropDown(element("select_diagnosis"), diagnosis);
		logMessage("User selects diagnosis from dropdown");
	}

	/**
	 * This method selects otherDetails
	 * 
	 * @param otherDetails
	 */
	public void selectOtherDetails(String otherDetails) {
		selectProvidedTextFromDropDown(element("select_otherDetails"), otherDetails);
		logMessage("User selects diagnosis from dropdown");
	}

	/**
	 * This method enters indication details for patients clinical profile
	 * 
	 * @param medication
	 * @param diagnosis
	 * @param otherDetails
	 */
	public void enterClinicalProfileDetails(String indication, String diagnosis, String otherDetails) {
		selectIndication(indication);
		selectDiagnosis(diagnosis);
		selectOtherDetails(otherDetails);
	}

	/**
	 * This method clicks on Complete Registration
	 */
	public void clickOnCompleteRegistration() {
		executeJavascript("document.getElementById('submit_button').click();");
		logMessage("User clicks on Complete Registration");
	}

	/**
	 * This method clicks on Patient List
	 */
	public void clickOnPatientList() {
		element("div_patntList").click();
		logMessage("User clicks on Patient List");
	}

	/**
	 * Verified that correct success message is displayed on Registration
	 * success modal
	 * 
	 * @param successMessage
	 */
	public void verifyUserIsOnRegSuccessModal(String successMessage, String firstName) {
		Assert.assertEquals(element("p_successMsg").getText().trim(),
				successMessage + " " + firstName + " " + custom.getPatientLastName() + ".",
				"Assertion Failed: Registration Success Message " + successMessage + " is not displayed");
		logMessage("Assertion Passed: Registration Success Message " + successMessage + " is  displayed");
	}

	/**
	 * This method clicks Home on Registration Success Modal window
	 */
	public void clickHomeOnRegistrationSuccess() {
		element("div_home").click();
		logMessage("User clicks on Home button");
	}

	/**
	 * This method clicks on Search Patients
	 */
	public void clickOnSearchPatients() {
		executeJavascript("document.getElementsByClassName('button')[9].click();");
		logMessage("User clicks on Search Patients");
	}

	/**
	 * This method verifies header text of patient's search result list
	 * 
	 * @param searchHdrTxt
	 */
	public void verifySearchResultAndClose(String searchHdrTxt) {
		isElementDisplayed("hdr_searchResults");
		Assert.assertEquals(element("hdr_searchResults").getText(), searchHdrTxt,
				"Assertion Failed : " + searchHdrTxt + " header is not getting displayed in clicking search Patients");
		logMessage("Assertion Passed : " + searchHdrTxt + " header is getting displayed in clicking search Patients");
		clickOnCloseIcon();
	}

	/**
	 * This method clicks on close icon
	 */
	private void clickOnCloseIcon() {
		element("icon_close").click();
		logMessage("User clicks on Close Icon");
	}

	/**
	 * This method click on Add Another Insurance Plan
	 */
	public void clickOnAddAnotherInsurancePlan() {
		element("btn_addInsurance").click();
		logMessage("User clicks on Add Another Insurance Plan");
	}

	/**
	 * This method enter details to add secondary insurance plan
	 * 
	 * @param insuranceName
	 * @param policyId
	 * @param state
	 */
	public void addSecondaryInsurancePlan(String insuranceName, String policyId, String groupNo, String state) {
		clickOnAddAnotherInsurancePlan();
		enterDetailsForSecondaryInsurance(insuranceName, policyId, groupNo, state);
	}

	/**
	 * This method enter details to add secondary insurance
	 * 
	 * @param insuranceName
	 * @param policyId
	 * @param groupNo
	 * @param state
	 */
	private void enterDetailsForSecondaryInsurance(String insuranceName, String policyId, String groupNo,
			String state) {
		enterText(element("inp_insrncName", "Secondary Insurance", "Carrier"), insuranceName);
		logMessage("User enter value " + insuranceName + " in Insurance Company Name field");
		selectProvidedTextFromDropDown(element("select_insField", "Secondary Insurance", "State"), state);
		logMessage("User selects value" + state + " in State field");
		enterText(element("inp_insField", "Secondary Insurance", "Phone"), getStringWithTimestamp(""));
		logMessage("User enter value Phone field");
		enterText(element("inp_insField", "Secondary Insurance", "Policy ID"), policyId);
		logMessage("User enter value in Policy ID field");
		enterText(element("inp_insField", "Secondary Insurance", "Group Number"), groupNo);
		logMessage("User enter value " + groupNo + " in Group Number field");
		selectProvidedTextFromDropDown(element("select_insField", "Secondary Insurance", "Policy Holder Relationship"),
				getYamlValue("physician.insuranceInfo.relationship"));
		logMessage("User selects " + getYamlValue("physician.insuranceInfo.relationship")
				+ " from 'Policy Holder Relationship' dropdown");
		enterText(element("inp_insField", "Secondary Insurance", "Policy Holder Employer Name"),
				getStringWithTimestamp("policyEmployer"));
		logMessage("User enter value in Policy Holder Employer Name field");
	}

	/**
	 * This method enter details to add tertiary insurance plan
	 * 
	 * @param insuranceName
	 * @param policyId
	 * @param state
	 */
	public void addTertiaryInsurancePlan(String insuranceName, String policyId, String groupNo, String state) {
		clickOnAddAnotherInsurancePlan();
		enterDetailsForTertiaryInsurance(insuranceName, policyId, groupNo, state);
	}

	/**
	 * This method enter details to add tertiary insurance
	 * 
	 * @param insuranceName
	 * @param policyId
	 * @param groupNo
	 * @param state
	 */
	private void enterDetailsForTertiaryInsurance(String insuranceName, String policyId, String groupNo, String state) {
		enterText(element("inp_insrncName", "Tertiary Insurance", "Carrier"), insuranceName);
		logMessage("User enter value " + insuranceName + " in Insurance Company Name field");
		selectProvidedTextFromDropDown(element("select_insField", "Tertiary Insurance", "State"), state);
		logMessage("User selects value" + state + " in State field");
		enterText(element("inp_insField", "Tertiary Insurance", "Phone"), getStringWithTimestamp(""));
		logMessage("User enter value Phone field");
		enterText(element("inp_insField", "Tertiary Insurance", "Policy ID"), policyId);
		logMessage("User enter value in Policy ID field");
		enterText(element("inp_insField", "Tertiary Insurance", "Group Number"), groupNo);
		logMessage("User enter value " + groupNo + " in Group Number field");
		selectProvidedTextFromDropDown(element("select_insField", "Tertiary Insurance", "Policy Holder Relationship"),
				getYamlValue("physician.insuranceInfo.relationship"));
		logMessage("User selects " + getYamlValue("physician.insuranceInfo.relationship")
				+ " from 'Policy Holder Relationship' dropdown");
		enterText(element("inp_insField", "Tertiary Insurance", "Policy Holder Employer Name"),
				getStringWithTimestamp("policyEmployer"));
		logMessage("User enter value in Policy Holder Employer Name field");
	}

	/**
	 * This method clicks on complete profile
	 */
	public void clickOnCompleteProfile() {
		element("div_completeProfile").click();
		logMessage("User clicks on Complete Profile");
	}

	/**
	 * This method clicks on STEP 1: GENERAL
	 */
	public void clickOnStep1General() {
		element("link_step1").click();
		logMessage("User clicks on STEP 1:GENERAL");
	}

	/**
	 * This method validates calue in retained on navigating back
	 * 
	 * @param loginEmail
	 */
	public void verifyValuesAreRetained(String loginEmail) {
		Assert.assertEquals(element("inp_loginEmail").getAttribute("value"), loginEmail,
				"Assertion Failed: Value for login email " + loginEmail + " is retained navigating back");
		logMessage("Assertion Failed: Value for login email " + loginEmail + " does not retain on navigating back");
	}

	// ***********************************PharmacyBenefits*******************************//
	/**
	 * This method enter details under Pharmacy Benefits
	 * 
	 * @param insName
	 * @param state
	 * @param rxBin
	 * @param rxPCN
	 */
	public void enterDetailsForPharmacyBenefits(String insName, String state, String rxBin, String rxPCN) {
		clicKOnAddPharmacyBenefits();
		enterPBInsuranceName(insName);
		selectPBState(state);
		enterPBRxBin(rxBin);
		// enterPBRxPCN(rxPCN);
	}

	/**
	 * This method clicks on add Pharmacy Benefits button
	 */
	public void clicKOnAddPharmacyBenefits() {
		element("btn_addPharmacyBenefit").click();
		logMessage("User clicks on add Pharmacy Benefits button");
	}

	/**
	 * This method enters insurance name value under Pharmacy Benefits
	 * 
	 * @param insuranceName
	 */
	private void enterPBInsuranceName(String insuranceName) {
		enterText(element("inp_insField", "Pharmacy Benefits", "PBM"), insuranceName);
		logMessage("User enters " + insuranceName + " under Insurance Name for Pharmacy Benefits");
	}

	/**
	 * This method selects state value under Pharmacy Benefits
	 * 
	 * @param state
	 */
	private void selectPBState(String state) {
		selectProvidedTextFromDropDown(element("select_insField", "Pharmacy Benefits", "State"), state);
		logMessage("User selects " + state + " under State for Pharmacy Benefits");
	}

	/**
	 * This method enters Rx Bin under Pharmacy Benefits
	 *
	 * @param rxBin
	 */
	private void enterPBRxBin(String rxBin) {
		enterText(element("inp_insField", "Pharmacy Benefits", "Rx Bin"), "3858.0");
		logMessage("User enters " + rxBin + " under Rx Bin for Pharmacy Benefits");
	}

	/**
	 * This method enters Rx PCN under Pharmacy Benefits
	 * 
	 * @param rxPCN
	 */
	// private void enterPBRxPCN(String rxPCN) {
	// enterText(element("inp_insField", "Pharmacy Benefits", "Rx PCN"), rxPCN);
	// logMessage("User enters " + rxPCN + " under Rx PCN for Pharmacy
	// Benefits");
	// }

	/**
	 * This method clicks on capture consent
	 */
	public void clickOnCaptureConsent() {
		element("div_patConsent").click();
		logMessage("User clicks on capture consent");
	}

	/**
	 * This method clicks on Add Additional Medication
	 */
	private void clickOnAddAdditionalMedication() {
		element("span_addAdditionalMed").click();
		logMessage("User clicks on Remove Medication Button");
	}

	/**
	 * This method clicks on 'cross' to remove medication
	 */
	private void clickOnCross() {
		executeJavascript("document.getElementsByClassName('btn-med-remove')[0].getElementsByTagName('i')[0].click();");
		logMessage("User clicks on Remove Medication Button");
	}

	/**
	 * This method verifies remove medication confirmation message after
	 * removing additional medication
	 */
	public void verifyRemoveConfirmationMessage(String removeMsg) {
		Assert.assertEquals(element("p_removeMedConfirm").getText().trim(), removeMsg,
				"Assertion Failed: Remove medication confirmation message : " + removeMsg + " is not displayed");
		logMessage("Assertion Passed: Remove medication confirmation message : " + removeMsg + " is displayed");
	}

	/**
	 * This method clicks on 'Yes' from med removal confirmation modal
	 */
	private void clickOnYesFromModal() {
		element("btn_modalYes").click();
		logMessage("User clicks on Yes from modal");
	}

	/**
	 * This method verifies addition and removal of 'Add Additional Medications'
	 * 
	 * @param removeMsg
	 */
	public void verifyAdditionAndRemovalOfAdditionalMed(String removeMsg) {
		clickOnAddAdditionalMedication();
		isElementDisplayed("select_medication");
		isElementDisplayed("i_medRemove");
		clickOnCross();
		verifyRemoveConfirmationMessage(removeMsg);
		clickOnYesFromModal();
		isElementNotDisplayed("select_medication");
	}

	/**
	 * This method verifies that 'General Questions' is displayed
	 */
	public void verifyGeneralQuestions() {
		isElementDisplayed("h5_generalques");
	}

	/**
	 * This method clicks on 'Add additional symptom'
	 */
	public void clickOnAddAdditionalSymptom() {
		element("btn_addAddtnlSymptom").click();
		logMessage("User clicks on 'Add Additional Symptom' button");
	}

	/**
	 * This method selects symptom name from dropdown
	 * 
	 * @param symptomName
	 */
	private void selectSymptomName(String symptomName) {
		selectProvidedTextFromDropDown(element("select_symptomName"), symptomName);
		logMessage("User selects Symptom Name from dropdown");
	}

	/**
	 * This method enters value for start date under 'Add New Symptom'
	 */
	private void enterSymptomStartDate() {
		enterText(element("inp_symStartDate"), getDateWithSeparator());
		logMessage("User enters Start Date");
	}

	/**
	 * This method enters value for end date under 'Add New Symptom'
	 */
	private void enterSymptomEndDate() {
		enterText(element("inp_symEndDate"), getCurrentDateWithSep());
		logMessage("User enters Start Date");
	}

	/**
	 * This method clicks on 'Save' button on Symptom modal
	 */
	private void clickOnSaveOnSymptomModal() {
		element("btn_symSave").click();
		logMessage("User clicks on 'Save' on Symtom modal");
	}

	/**
	 * This method verifies Symptom Name
	 * 
	 * @param symptomName
	 */
	public void verifySymptomName(String symptomName) {
		Assert.assertEquals(element("td_symptomName", symptomName).getText(), symptomName,
				"Assertion Failed: Symptom name : " + symptomName + " is not displayed");
		logMessage("Assertion Passed: Symptom name : " + symptomName + " is displayed");
	}

	/**
	 * This method clicks on Symptom Name
	 * 
	 * @param symptomName
	 */
	private void clickOnSymptomName(String symptomName) {
		wait.waitForStableDom(250);
		element("td_symptomName", symptomName).click();
		logMessage("User clicks on Symptom Name");
	}

	/**
	 * This method clicks on 'Delete' button on Symptom modal
	 */
	private void clickOnDelete() {
		element("btn_symDelete").click();
		logMessage("User clicks on 'Delete' from symptom modal");
	}

	/**
	 * This method clicks on 'Yes' on Symptom modal
	 */
	private void clickOnYes() {
		element("btn_symYes").click();
		logMessage("User clicks on 'Yes' from symptom modal");
	}

	/**
	 * This method clicks on Okay
	 */
	private void clickOnOkay() {
		element("btn_okay").click();
		logMessage("User clicks on 'Okay' from symptom modal");
	}

	/**
	 * This method adds new symptom
	 * 
	 * @param symptomName
	 */
	public void addNewSymptom(String symptomName) {
		clickOnAddAdditionalSymptom();
		selectSymptomName(symptomName);
		enterSymptomStartDate();
		enterSymptomEndDate();
		clickOnSaveOnSymptomModal();
		clickOnOkay();
	}

	/**
	 * This method deletes symptom
	 * 
	 * @param symptomName
	 */
	public void deleteSymptom(String symptomName) {
		clickOnSymptomName(symptomName);
		clickOnDelete();
		clickOnYes();
		clickOnOkay();
	}

	/**
	 * This method validates addition and deletion of 'Additional Symptoms'
	 * 
	 * @param symptomName
	 */
	public void verifyAdditionAndRemovalOfAdditionalSymtoms(String symptomName) {
		addNewSymptom(symptomName);
		verifySymptomName(symptomName);
		deleteSymptom(symptomName);
		isElementNotDisplayed("td_symptomName", symptomName);
	}

	/**
	 * This method expands 'General Questions' section
	 */
	public void expandGeneralQuestions() {
		isElementDisplayed("h5_generalques");
		element("h5_generalques").click();
		logMessage("User clicks on 'General Questions' to expand it");
	}

	/**
	 * This method enters value for EDSS score under general questions
	 * 
	 * @param edssScore
	 */
	public void enterValueForEDSSScore(String edssScore) {
		enterText(element("inp_edssScore"), edssScore);
		logMessage("User enters value for EDSS score under general questions");
	}
}
