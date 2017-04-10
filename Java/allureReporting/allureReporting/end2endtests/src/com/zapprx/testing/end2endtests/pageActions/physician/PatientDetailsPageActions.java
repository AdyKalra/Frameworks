package com.zapprx.testing.end2endtests.pageActions.physician;

import static com.zapprx.testing.end2endtests.automation.utils.CustomFunctions.getRandomIntegerValue;
import static com.zapprx.testing.end2endtests.automation.utils.CustomFunctions.getStringWithTimestamp;
import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class PatientDetailsPageActions extends GetPage {
	WebDriver driver;
	ChooseMedicationPageActions chooseMedicationPage;

	public PatientDetailsPageActions(WebDriver driver) {
		super(driver, "physician/PatientDetailsPage");
		this.driver = driver;
		chooseMedicationPage = new ChooseMedicationPageActions(driver);
	}

	/**
	 * This method verify all mandatory fields on Patient Details Page
	 */
	public void verifyMandatoryFieldOnPatientDetails() {
		verifyMandatoryFieldsPatientIdentification();
		verifyMandatoryFieldsPatientStatistics();
		verifyMandatoryFieldsContactInfo();
		verifyMandatoryFieldsInsuranceInfo();
		verifyMandatoryFieldsSpecialityPharmacy();
		verifyMandatoryFieldsShippingInfo();
	}

	/**
	 * This method verify all mandatory fields under Patient Identification
	 */
	private void verifyMandatoryFieldsPatientIdentification() {
		Assert.assertTrue(element("inp_requiredField", element("inp_firstName").getAttribute("id"))
				.getAttribute("class").contains("required"), "Assertion Failed: 'FirstName' is not a mandatory field");
		logMessage("Assertion Passed: 'FirstName' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_lastName").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'LastName' is not a mandatory field");
		logMessage("Assertion Passed: 'LastName' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_dob").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Date of Birth' is not a mandatory field");

		logMessage("Assertion Passed: 'Date of Birth' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_userName").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Username' is not a mandatory field");
		logMessage("Assertion Passed: 'Username' is a mandatory field");
	}

	/**
	 * This method verify all mandatory fields under Patient Statistics
	 */
	private void verifyMandatoryFieldsPatientStatistics() {
		Assert.assertTrue(element("select_requiredField", element("select_gender").getAttribute("id"))
				.getAttribute("class").contains("required"), "Assertion Failed: 'Gender' is not a mandatory field");
		logMessage("Assertion Passed: 'Gender' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_height").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Height' is not a mandatory field");
		logMessage("Assertion Passed: 'Height' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_weight").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Weight' is not a mandatory field");
		logMessage("Assertion Passed: 'Weight' is a mandatory field");
		Assert.assertTrue(
				element("select_requiredField", element("select_inPatient").getAttribute("id")).getAttribute("class")
						.contains("required"),
				"Assertion Failed: 'Is the Patient an in patient?' is not a mandatory field");
		logMessage("Assertion Passed: 'Is the Patient an in patient?' is a mandatory field");
		Assert.assertTrue(element("select_requiredField", element("select_diabetic").getAttribute("id"))
				.getAttribute("class").contains("required"),
				"Assertion Failed: 'Is the Patient diabetic?' is not a mandatory field");
		logMessage("Assertion Passed: 'Is the Patient diabetic?' is a mandatory field");
		Assert.assertTrue(
				element("select_requiredField", element("select_bloodPressure").getAttribute("id"))
						.getAttribute("class").contains("required"),
				"Assertion Failed: 'Does this patient have high blood pressure?' is not a mandatory field");
		logMessage("Assertion Passed: 'Does this patient have high blood pressure??' is a mandatory field");
		Assert.assertTrue(
				element("select_requiredField", element("select_latexAllergy").getAttribute("id")).getAttribute("class")
						.contains("required"),
				"Assertion Failed: 'Does this patient have latex allergy?' is not a mandatory field");
		logMessage("Assertion Passed: 'Does this patient have latex allergy??' is a mandatory field");
	}

	/**
	 * This method verify all mandatory fields under Contact Information
	 */
	private void verifyMandatoryFieldsContactInfo() {
		Assert.assertTrue(element("inp_requiredField", element("inp_address").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Address 1' is not a mandatory field");
		logMessage("Assertion Passed: 'Address 1' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_city").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'City' is not a mandatory field");
		logMessage("Assertion Passed: 'City' is a mandatory field");
		Assert.assertTrue(element("select_requiredField", element("select_state").getAttribute("id"))
				.getAttribute("class").contains("required"), "Assertion Failed: 'State' is not a mandatory field");
		logMessage("Assertion Passed: 'State' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_zip").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Zip' is not a mandatory field");
		logMessage("Assertion Passed: 'Zip' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_email").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Email' is not a mandatory field");
		logMessage("Assertion Passed: 'Email' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_phone").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Phone' is not a mandatory field");
		logMessage("Assertion Passed: 'Phone' is a mandatory field");
	}

	/**
	 * This method clicks on 'Add New Address'
	 */
	private void clickOnAddNewAddress() {
		scrollDown(element("btn_addNewAdd"));
		wait.waitForStableDom(250);
		isElementDisplayed("btn_addNewAdd");
		wait.waitForElementToBeClickable(element("btn_addNewAdd"));
		element("btn_addNewAdd").click();
		logMessage("User clicks on Add new address");
	}

	/**
	 * This method clicks on 'Save'
	 */
	private void clickOnContactSave() {
		element("btn_contactSave").click();
		logMessage("User clicks on Save");
	}

	/**
	 * This method enters value for all the required fields under contact
	 * information
	 * 
	 * @param address
	 * @param city
	 * @param state
	 */
	public void enterDetailsForContactInfo(String state) {
		clickOnAddNewAddress();
		selectProvidedTextFromDropDown(element("select_type"), "Home Address");
		logMessage("This method selects contact type");
		enterText(element("inp_address"), getStringWithTimestamp("address"));
		logMessage("User enters value in address field");
		enterText(element("inp_city"), getStringWithTimestamp("city"));
		logMessage("User enters value in city field");
		selectProvidedTextFromDropDown(element("select_state"), state);
		logMessage("User selects " + state + " from state field");
		enterText(element("inp_zip"), getStringWithTimestamp(""));
		logMessage("User enters value in zip field");
		enterText(element("inp_phone"), getStringWithTimestamp(""));
		logMessage("User enters value in home phone field");
		clickOnContactSave();
		clickContinueOkay();
	}

	/**
	 * This method verifies that shipping information is autofilled i.e. same as
	 * Patient's Home Address
	 */
	public void verifyShippingInfoIsSameAsHomeAddress(String[] homeAddress) {
		Assert.assertEquals(element("select_selectedShipOption").getText(), "Patient",
				"Assertion Failed: Shipping address is not same as home address");
		logMessage("Assertion Passed: Shipping address is same as home address");
		Assert.assertTrue(element("inp_shipDetails").getText().contains(homeAddress[0]),
				"Assertion Failed: Shipping address is not same as home address");
		logMessage("Assertion Passed: Shipping address is same as home address");
		Assert.assertTrue(element("inp_shipDetails").getText().contains(homeAddress[1]),
				"Assertion Failed: Shipping city is not same as home city");
		logMessage("Assertion Passed: Shipping city is same as home city");
		Assert.assertTrue(element("inp_shipDetails").getText().contains(homeAddress[2]),
				"Assertion Failed: Shipping state is not same as home state");
		logMessage("Assertion Passed: Shipping state is same as home state");
		Assert.assertEquals(element("inp_shipEmail").getText(), homeAddress[4],
				"Assertion Failed: Shipping email is not same as home email");
		logMessage("Assertion Passed: Shipping email is same as home email");
		Assert.assertEquals(element("inp_shipPhone").getText().replaceAll("[^0-9]", ""), homeAddress[5].substring(1),
				"Assertion Failed: Shipping phone is not same as home phone");
		logMessage("Assertion Passed: Shipping phone is same as home phone");
	}

	/**
	 * This method verify all mandatory fields under Insurance Information
	 */
	private void verifyMandatoryFieldsInsuranceInfo() {
		Assert.assertTrue(element("inp_requiredField", element("inp_insuranceName").getAttribute("id"))
				.getAttribute("class").contains("required"),
				"Assertion Failed: 'Insurance Company Name' is not a mandatory field");
		logMessage("Assertion Passed: 'Insurance Company Name' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_insuranceState").getAttribute("id"))
				.getAttribute("class").contains("required"), "Assertion Failed: 'State' is not a mandatory field");
		logMessage("Assertion Passed: 'State' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_insurancePhone").getAttribute("id"))
				.getAttribute("class").contains("required"), "Assertion Failed: 'Phone' is not a mandatory field");
		logMessage("Assertion Passed: 'Phone' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_insuranceMemberId").getAttribute("id"))
				.getAttribute("class").contains("required"), "Assertion Failed: 'Policy ID' is not a mandatory field");
		logMessage("Assertion Passed: 'Policy ID' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_insuranceGroupNo").getAttribute("id"))
				.getAttribute("class").contains("required"),
				"Assertion Failed: 'Group Number' is not a mandatory field");
		logMessage("Assertion Passed: 'Group Number' is a mandatory field");
		Assert.assertTrue(element("div_rxBin").getAttribute("class").contains("required"),
				"Assertion Failed: 'Rx Bin' is a the mandatory field");
		logMessage("Assertion Passed: 'Rx Bin' is a mandatory field");
		Assert.assertTrue(element("div_rxPCN").getAttribute("class").contains("required"),
				"Assertion Failed: 'Rx PCN' is not a mandatory field");
		logMessage("Assertion Passed: 'Rx PCN' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_insuredName").getAttribute("id"))
				.getAttribute("class").contains("required"),
				"Assertion Failed: 'Insured Name' is not a mandatory field");
		logMessage("Assertion Passed: 'Insured Name' is a mandatory field");
		Assert.assertTrue(
				element("select_requiredField", element("select_insuredRelation").getAttribute("id"))
						.getAttribute("class").contains("required"),
				"Assertion Failed: 'Insured Relationship' is not a mandatory field");
		logMessage("Assertion Passed: 'Insured Relationship' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_insuranceEmployer").getAttribute("id"))
				.getAttribute("class").contains("required"),
				"Assertion Failed: 'Employer Name' is not a mandatory field");
		logMessage("Assertion Passed: 'Employer Name' is the mandatory field");
	}

	/**
	 * This method enters details for required fields under Emergency Contact
	 * Information
	 * 
	 * @param emerName
	 * @param emerRel
	 */
	public void enterDetailsForEmerContactInfo() {
		enterText(element("inp_emrgncyName"), getStringWithTimestamp("emergencyName"));
		logMessage("User enters value in Emergency Name field");
		enterText(element("inp_emrgncyRel"), getStringWithTimestamp("emergencyrel"));
		logMessage("User enters value in Emergency Realtionship field");
		enterText(element("inp_emrgncyPhn"), getStringWithTimestamp(""));
		logMessage("User enters value Emergency Phone Number field");
	}

	/**
	 * This method verify mandatory field under Preferred Speciality Pharmacy
	 */
	private void verifyMandatoryFieldsSpecialityPharmacy() {
		Assert.assertTrue(element("select_requiredField", element("select_pharmacy").getAttribute("id"))
				.getAttribute("class").contains("required"), "Assertion Failed: 'Pharmacy' is a the mandatory field");
		logMessage("Assertion Passed: 'Pharmacy' is a mandatory field");
	}

	/**
	 * This method verify mandatory fields under Shipping Information
	 */
	private void verifyMandatoryFieldsShippingInfo() {
		Assert.assertTrue(element("select_requiredField", element("select_ship").getAttribute("id"))
				.getAttribute("class").contains("required"),
				"Assertion Failed: 'Ship To' is  not a the mandatory field");
		logMessage("Assertion Passed: 'Ship To' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_shipAddress").getAttribute("id"))
				.getAttribute("class").contains("required"), "Assertion Failed: 'Address 1' is not a mandatory field");
		logMessage("Assertion Passed: 'Address 1' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_shipCity").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'City' is not a mandatory field");
		logMessage("Assertion Passed: 'City is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_shipState").getAttribute("id"))
				.getAttribute("class").contains("required"), "Assertion Failed: 'State' is not a mandatory field");
		logMessage("Assertion Passed: 'State' is a mandatory field");
		Assert.assertTrue(element("inp_requiredField", element("inp_shipZip").getAttribute("id")).getAttribute("class")
				.contains("required"), "Assertion Failed: 'Zip' is not a mandatory field");
		logMessage("Assertion Passed: 'Zip' is a mandatory field");
	}

	/**
	 * This method enter details on Patient Page
	 * 
	 * @param allergy
	 * @param pharmacy
	 */
	public void enterPatientDetails(String allergy, String pharmacy, String gender) {
		isElementDisplayed("select_gender");
		scrollDown(element("select_gender"));
		selectProvidedTextFromDropDown(element("select_gender"), gender);
		enterKnowDrugAllergy(allergy);
		selectPharmacy(pharmacy);
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
	 * This method verifies if specialty pharmacy is already selected
	 * 
	 * @param pharmacy
	 */
	public void verifySpecialtyPharmacy(String pharmacy, String medication) {
		Assert.assertEquals(element("option_pharmacy", pharmacy).getAttribute("selected"), "true",
				"Assertion Failed: " + pharmacy + " is  not displayed under specialty pharmacy");
		logMessage("Assertion Passed: " + pharmacy + " is displayed under specialty pharmacy");
	}

	/**
	 * This method clicks on capture patient consent
	 */
	public void clickOnCaptPatientConsent() {
		scrollDown(element("btn_capPatCon"));
		element("btn_capPatCon").click();
		logMessage("User clicks on Capture Patient Consent");
	}

	/**
	 * This method clicks on Step2: Diagnosis Step
	 */
	public void clickOnDiagnosisStep() {
		wait.waitForLoaderToDisappear();
		scrollDown(element("btn_diagnosis"));
		executeJavascript("document.getElementById('next_button').click();");
		logMessage("User clicks on Step2: Diagnosis");
	}

	/**
	 * This method enters allergy name
	 */
	private void enterKnowDrugAllergy(String allergy) {
		enterText(element("inp_drugAllergy"), allergy);
		logMessage("User enter " + allergy + " in Known Drug Allergies field");
	}

	/**
	 * This method selects Pharmacy on Patient Details
	 * 
	 * @param pharmacy
	 */
	public void selectPharmacy(String pharmacy) {
		selectProvidedTextFromDropDown(element("select_pharmacy"), pharmacy);
		logMessage("User selects " + pharmacy + " from pharmacy dropdown");
	}

	/**
	 * This method selects doctor from Prescribing Provider dropdown
	 * 
	 * @param doctorName
	 */
	public void selectPrescribingProvider(String doctorName) {
		isElementDisplayed("select_presProvider");
		selectProvidedTextFromDropDown(element("select_presProvider"), doctorName);
		logMessage("User has selected " + doctorName + " from Prescribing Provider dropdown");
	}

	/**
	 * This method click on update for patient
	 */
	public void clickOnPatientProfileModal() {
		isElementDisplayed("update_confirmation");
		executeJavascript("document.getElementById('update-confirmation-yes').click();");
		logMessage("User clicks on Update patient details");
	}

	/**
	 * This method verifies rems is not displayed for Letairis
	 */
	public void verifyREMSIsNotDisplayed() {
		isElementNotDisplayed("div_rems");
	}

	/**
	 * This method clicks on send to hub
	 */
	public void sendToHub() {
		element("inp_patientConsentYes").click();
	}

	/**
	 * This method capture patient consent
	 * 
	 * @param gender
	 * @param medicationName
	 */
	public void capturePatientConsent(String authType, String gender, String medicationName) {
		enterSignOfPatConForPatServiceAndShareAuth(authType);
		if (gender.equalsIgnoreCase("female") || medicationName.equalsIgnoreCase("Tysabri")) {
			enterSignOfPatientConsentForRems();
		}
		savePatientConsent();
		clickContinueOkay();
	}

	/**
	 * This method enters signature on patient consent for HIPAA
	 * 
	 * @param signature
	 */
	private void enterSignOfPatientConsentForHIPAA() {
		element("li_drwHIPAA").click();
		logMessage("User clicks on draw signature");
		drawSignatureOnCanvas(element("canvas_signHipaa"));
	}

	/**
	 * This method enters signature on patient consent for Share Authorization
	 * 
	 * @param signature
	 */
	private void enterSignOfPatConForPatServiceAndShareAuth(String authType) {
		scrollDown(element("li_drwPatService&Share", authType));
		element("li_drwPatService&Share", authType).click();
		logMessage("User clicks on draw signature");
		drawSignatureOnCanvas(element("canvas_signPatService&Share", authType));
	}

	/**
	 * This method enters signature on patient consent for REMS
	 * 
	 * @param signature
	 */
	private void enterSignOfPatientConsentForRems() {
		scrollDown(element("li_drwRems"));
		element("li_drwRems").click();
		logMessage("User clicks on draw signature");
		drawSignatureOnCanvas(element("canvas_signRems"));
	}

	/**
	 * This method save the Patient Consent
	 */
	private void savePatientConsent() {
		element("btn_savePatientConsent").click();
		logMessage("User save the patient consent");
	}

	/**
	 * This method clicks continue
	 */
	private void clickContinueOkay() {
		isElementDisplayed("div_continue&Okay");
		element("div_continue&Okay").click();
		logMessage("User clicks Continue/Okay on confirmation modal");
		wait.waitForLoaderToDisappear();
	}

	/**
	 * This method verifies that user is not able to save Patient Consent only
	 * signing HIPAA Authorization
	 */
	private void verifyUserCantSavePatConOnlySigningHIPAAAuth(String errorMsg) {
		Assert.assertEquals(element("p_errorMsgShare").getText(), errorMsg,
				"Assertion Failed: Error message " + errorMsg + " is not displayed");
		logMessage("Assertion Passed: Error message " + errorMsg + " is displayed");
	}

	/**
	 * This method verifies that user is not able to save Patient Consent only
	 * signing Share Authorization
	 */
	private void verifyUserCantSavePatConOnlySigningShareAuth(String errorMsg) {
		Assert.assertEquals(element("p_errorMsgRems").getText(), errorMsg,
				"Assertion Failed: Error message " + errorMsg + " is not displayed");
		logMessage("Assertion Passed: Error message " + errorMsg + " is displayed");
	}

	/**
	 * This method verifies that user is on patient consent success modal
	 */
	private void verifyUserIsPatConsentSuccessModal(String successMessage, String firstName, String lastName) {
		Assert.assertEquals(element("p_successMsg").getText().trim(),
				firstName + " " + lastName + " " + successMessage + ".",
				"Assertion Failed: Patient Consent Success Message " + successMessage + " is not displayed");
		logMessage("Assertion Passed: Patient Consent Success Message " + successMessage + " is  displayed");
	}

	/**
	 * This method first tries to save patient consent only signing HIPAA and
	 * then verifies the error message and then saves the patient consent by
	 * signing Patient Service Authorization also
	 * 
	 * @param errorMsg
	 * @param successMessage
	 * @param firstName
	 * @param lastName
	 */
	public void captPatConAndVerifyErrorMsgOnSkipSigForTechfidera(String authType, String errorMsg,
			String successMessage, String firstName, String lastName) {
		clickOnCaptPatientConsent();
		enterSignOfPatientConsentForHIPAA();
		savePatientConsent();
		verifyUserCantSavePatConOnlySigningHIPAAAuth(errorMsg);
		enterSignOfPatConForPatServiceAndShareAuth(authType);
		savePatientConsent();
		verifyUserIsPatConsentSuccessModal(successMessage, firstName, lastName);
		clickContinueOkay();
	}

	/**
	 * This method first tries to save patient consent only signing Share
	 * Authorization and then verifies the error message and then saves the
	 * patient consent by signing REMS also
	 * 
	 * @param errorMsg
	 * @param successMessage
	 * @param firstName
	 * @param lastName
	 */
	public void captPatConAndVerifyErrorMsgOnSkipSigForAdempas(String authType, String errorMsg, String successMessage,
			String firstName, String lastName) {
		clickOnCaptPatientConsent();
		enterSignOfPatConForPatServiceAndShareAuth(authType);
		savePatientConsent();
		verifyUserCantSavePatConOnlySigningShareAuth(errorMsg);
		enterSignOfPatientConsentForRems();
		savePatientConsent();
		verifyUserIsPatConsentSuccessModal(successMessage, firstName, lastName);
		clickContinueOkay();
	}

	/**
	 * This method verifies that Compassionate Use Header as well as checkbox is
	 * not displayed
	 */
	public void verifyOpsumitCompassionateUse() {
		isElementNotDisplayed("h2_compassionateHeader");
		isElementNotDisplayed("inp_compassionateCheckbox");
	}

	/**
	 * This method verifies that Primary Insurance field is a required field or
	 * not
	 */
	public void verifyPrimaryInsurance() {
		clickOnAddNewInsurance();
		Assert.assertTrue(element("inp_insuranceName").getAttribute("required") != null,
				"Assertion Failed: Insurance Name is not a required field");
		logMessage("Assertion Passed: Insurance Name is a required field");
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
	 * This method enters value in Social Security Number
	 */
	private String enterSocialSecurityNumber() {
		String ssn = getStringWithTimestamp("").substring(0, 9);
		enterText(element("inp_ssn"), ssn);
		logMessage("User enter value in Phone field");
		return ssn;
	}

	/**
	 * This method enters value in 'SSN' and 'MRN' field
	 * 
	 * @return
	 */
	public String[] enterSSNAndMRNdetails() {
		String ssn = enterSocialSecurityNumber();
		String mrnNo = enterMedicalRecordNo();
		return new String[] { ssn, mrnNo };
	}

	/**
	 * This method verifies that 'SSN' and 'MRN' field is 'not disabled'
	 */
	public void verifySSNAndMRNFieldIsNotDisabled() {
		Assert.assertTrue(element("inp_ssn").getAttribute("disabled") == null,
				"Assertion Failed: SSN field is disabled");
		logMessage("Assertion Passed: SSN field is not disabled");
		Assert.assertTrue(element("inp_mrn").getAttribute("disabled") == null,
				"Assertion Failed: MRN field is disabled");
		logMessage("Assertion Passed: MRN field is not disabled");
	}

	/**
	 * This method clicks on 'Add New Insurance'
	 */
	private void clickOnAddNewInsurance() {
		scrollDown(element("select_pharmacy"));
		isElementDisplayed("btn_addNewIns");
		element("btn_addNewIns").click();
		logMessage("User clicks on Add New Insurance");
	}

	/**
	 * This method clicks on 'Save'
	 */
	private void clickOnInsuranceSave() {
		element("btn_insuranceSave").click();
		logMessage("User clicks on Save");
	}

	/**
	 * This method enters Primary Insurance info
	 * 
	 * @param insuranceName
	 */
	public void enterPrimaryInsurance(String insuranceName, String policyId, String groupNo, String state) {
		clickOnAddNewInsurance();
		enterText(element("inp_insuranceName"), insuranceName);
		logMessage("User enter value " + insuranceName + " in Insurance Company Name field");
		selectProvidedTextFromDropDown(element("select_insurerState"), state);
		logMessage("User selects value" + state + " in State field");
		enterText(element("inp_insurancePhone"), getStringWithTimestamp(""));
		logMessage("User enter value Phone field");
		enterText(element("inp_insuranceMemberId"), policyId);
		logMessage("User enter value in Policy Id field");
		enterText(element("inp_insuranceGroupNo"), groupNo);
		logMessage("User enter value " + groupNo + " in Group Number field");
		selectProvidedTextFromDropDown(element("select_insuredRelation"),
				getYamlValue("physician.insuranceInfo.relationship"));
		logMessage("User selects " + getYamlValue("physician.insuranceInfo.relationship")
				+ " from 'Insured Relationship' dropdown");
		enterText(element("inp_insuranceEmployer"), getStringWithTimestamp("policyEmployer"));
		logMessage("User enter value in Employer Name field");
		clickOnInsuranceSave();
		clickContinueOkay();
	}

	/**
	 * This method verifies that SSN and MRN field are correctly saved
	 * 
	 * @param ssn
	 * @param mrnNo
	 */
	public void verifySSNAndMRNdetails(String ssn, String mrnNo) {
		Assert.assertEquals(element("inp_ssn").getAttribute("value").replaceAll("[^0-9]", ""), ssn,
				"Assertion Failed: SSN field is not filled corectly");
		logMessage("Assertion Passed: SSN field is filled corectly");
		Assert.assertEquals(element("inp_mrn").getAttribute("value"), mrnNo,
				"Assertion Failed: MRN no is not filled correctly");
		logMessage("Assertion Passed: MRN no is filled correctly");
	}

	/**
	 * This method enters patient details to complete patient profile
	 * 
	 * @param genState
	 * @param insuranceName
	 * @param policyId
	 * @param groupNo
	 * @param insState
	 * @param medication
	 */
	public void enterPatientDetailsToCompleteProfile(String genState, String insuranceName, String policyId,
			String groupNo, String insState, String medication) {
		enterDetailsForEmerContactInfo();
		selectPharmacy(medication);
		enterPrimaryInsurance(insuranceName, policyId, groupNo, insState);
		enterDetailsForContactInfo(genState);
	}

	/**
	 * This method selects pharmacy for different MS medication for which
	 * 'Medication Specific Questions' need to be verified
	 * 
	 * @param medicineName
	 */
	public void selectPharmacyForMSMedQues(String medicineName) {
		if (medicineName.equalsIgnoreCase("Plegridy"))
			selectPharmacy(getYamlValue("physician.indication3.medicines.medicine14.pharmacy.pharmacy1"));
		else
			selectPharmacy(getYamlValue("physician.indication3.medicines.medicine2.pharmacy.pharmacy1"));
	}

	/**
	 * This method verifies that 'Group Number' field under 'Pharmacy Benefits'
	 * is not displayed
	 * 
	 * @param pharmacyBenefits
	 */
	public void verifyGroupNumber(String pharmacyBenefits) {
		scrollDown(element("select_pharmacy"));
		isElementNotDisplayed("span_groupNo", pharmacyBenefits, "Group Number");
	}

	/**
	 * This method clicks on 'Secondary' Radio btn
	 * 
	 * @param insuranceName
	 */
	public void clickOnSecondaryRadioBtn(String insuranceName) {
		element("radioBtn_secondry", insuranceName).click();
		logMessage("This method clicks on secondary radio button of '" + insuranceName + "' insurance");
	}

	/**
	 * This method verifies insurance message on error modal
	 * 
	 * @param insuranceMsg
	 */
	public void verifyInsuranceMessage(String insuranceMsg) {
		Assert.assertEquals(element("p_insuranceErrMsg").getText().trim(), insuranceMsg,
				"Assertion Failed: Insurance error message is not displayed");
		logMessage("Assertion Passed: Insurance error message is displayed");
	}

	public void clickOnPreviousButton() {
		element("btn_previous").click();
		logMessage("User clicks on Previous button");
	}

}
