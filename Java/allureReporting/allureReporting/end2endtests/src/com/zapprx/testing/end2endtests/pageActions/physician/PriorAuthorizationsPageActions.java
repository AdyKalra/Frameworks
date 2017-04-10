package com.zapprx.testing.end2endtests.pageActions.physician;

import static com.zapprx.testing.end2endtests.automation.utils.CustomFunctions.getCurrentDateWithSep;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;
import com.zapprx.testing.end2endtests.automation.utils.ReadFromPDF;

public class PriorAuthorizationsPageActions extends GetPage {
	WebDriver driver;
	private static int fileCount = 1;

	public PriorAuthorizationsPageActions(WebDriver driver) {
		super(driver, "physician/PriorAuthorizationsPage");
		this.driver = driver;
	}

	/**
	 * This method clicks on Saved tab
	 */
	public void clickOnSavedTab() {
		element("div_tabState", "Saved").click();
		logMessage("User clicks on Saved tab");
	}

	/**
	 * This method verifies diagnosis value
	 * 
	 * @param firstName
	 * @param diagnosis
	 */
	public void verifyDiagnosis(String firstName, String diagnosis) {
		Assert.assertEquals(element("span_diagnosis", firstName).getText(), diagnosis.split(" [(]")[0],
				"Assertion Failed: Diagnosis value " + diagnosis + " is not displayed on Prior Authorizations");
		logMessage("Assertion Passed: Diagnosis value " + diagnosis + " is displayed on Prior Authorizations");
	}

	/**
	 * This method verifies insurer value
	 * 
	 * @param firstName
	 * @param insurer
	 */
	public void verifyInsurer(String firstName, String insurer) {
		Assert.assertEquals(element("span_insurer", firstName).getText(), insurer,
				"Assertion Failed: Insurer value " + insurer + " is not displayed on Prior Authorizations");
		logMessage("Assertion Passed: Insurer value " + insurer + " is displayed on Prior Authorizations");
	}

	// ******************************************Pharmacy*****************************************//
	/**
	 * This method verifies no rx is displayed
	 * 
	 * @param patientFirstName
	 */
	public void verifyNoRxIsDisplayed(String patientFirstName) {
		isElementNotDisplayed("span_patientName", patientFirstName);
	}

	/**
	 * This method verifies patient's PA status on PA form
	 * 
	 * @param PAStatus
	 */
	public void verifyPAStatusOnPriorAuthPage(String PAStatus, String patientName) {
		isElementDisplayed("td_paStatus", patientName);
		Assert.assertEquals(element("td_paStatus", patientName).getText(), PAStatus,
				"Assertion Failed: PA Status: " + PAStatus + " is not displayed on prescription details page");
		logMessage("Assertion Passed: PA Status: " + PAStatus + " is displayed on prescription details page");
	}

	/**
	 * This method verifies PA Status on form
	 * 
	 * @param PAStatus
	 */
	public void verifyPAStatus(String PAStatus) {
		isElementDisplayed("span_paStatusOnForm");
		Assert.assertEquals(element("span_paStatusOnForm").getText(), PAStatus,
				"Assertion Failed: PA Status: " + PAStatus + " is not displayed on PA input screen");
		logMessage("Assertion Passed: PA Status: " + PAStatus + " is displayed on PA input screen");
	}

	/**
	 * This method verifies patient's PA status on Prescription details page
	 * 
	 * @param PAStatus
	 */
	public void verifyPAStatusOnPresDetailsPage(String PAStatus) {
		wait.waitForStableDom(250);
		scrollDown(element("span_pharPAStatus"));
		isElementDisplayed("span_pharPAStatus");
		Assert.assertEquals(element("span_pharPAStatus").getText(), PAStatus,
				"Assertion Failed: PA Status: " + PAStatus + " is not displayed on prescription details page");
		logMessage("Assertion Passed: PA Status: " + PAStatus + " is displayed on prescription details page");
	}

	/**
	 * This method verifies additional instruction after clicking on review
	 * 
	 * @param PAStatus
	 */
	public void verifyAdditionalInstOnPAFormReview(String additionalInst) {
		isElementDisplayed("td_additionalInst");
		Assert.assertEquals(element("td_additionalInst").getText(), additionalInst,
				"Assertion Failed: Additional instruction: " + additionalInst + " is not displayed on PA review");
		logMessage("Assertion Passed: Additional instruction: " + additionalInst + " is displayed on PA review");
	}

	/**
	 * This method clicks on View PA Details
	 */
	public void clickOnViewPADetails() {
		element("btn_viewPADetails").click();
		logMessage("User clicks on View PA Details");
	}

	/**
	 * This method clicks on View Rx Details
	 */
	public void clickOnViewRxDetails() {
		element("btn_viewRxDetails").click();
		logMessage("User clicks on View PA Details");
	}

	/**
	 * This method clicks on Request More Info
	 */
	private void clickOnRequestMoreInfo() {
		wait.waitForStableDom(250);
		wait.waitForElementToBeClickable(element("btn_requestMoreInfo"));
		element("btn_requestMoreInfo").click();
		logMessage("User clicks on Request More Info");
	}

	/**
	 * This method clicks on Review
	 */
	public void clickOnReview() {
		scrollDown(element("btn_review"));
		element("btn_review").click();
		logMessage("User clicks on Review");
	}

	/**
	 * This method verifies 'Review' button is displayed
	 */
	public void verifyReviewButtonIsDisplayed() {
		isElementDisplayed("btn_review");
	}

	/**
	 * This method clicks on Submit More Info Request
	 */
	private void clickOnSubmitMoreInfoRequest() {
		scrollDown(element("btn_submitMoreInfo"));
		element("btn_submitMoreInfo").click();
		logMessage("User clicks on Submit More Info Request");
	}

	/**
	 * This method clicks on Continue
	 */
	private void clickOnContinue() {
		element("btn_continue").click();
		logMessage("User clicks on continue from modal");
	}

	/**
	 * This method requests more info
	 */
	public void requestMoreInfo() {
		clickOnRequestMoreInfo();
		enterTextForRequestMoreInfo();
		clickOnSubmitMoreInfoRequest();
		clickOnContinue();
	}

	/**
	 * This method clicks on View Prescription Details on PA form
	 */
	public void clickOnViewPresDetails(String medicineName) {
		wait.waitForStableDom(250);
		isElementDisplayed("a_pharViewRx");
		scrollDown(element("a_pharViewRx"));
		Assert.assertTrue(element("a_pharViewRx").getText().contains(medicineName),
				"Assertion Failed: Medication Name is displayed incorrect");
		logMessage("Assertion Passed: Medication Name is displayed correct " + medicineName);
		executeJavascript("document.getElementsByClassName('pa-header-links')[0].getElementsByTagName('a')[0].click()");
		logMessage("User click on " + medicineName + " Rx");
	}

	/**
	 * This method enters text in textarea for 'Request More Info'
	 */
	private void enterTextForRequestMoreInfo() {
		isElementDisplayed("txt_textarea");
		enterText(element("txt_textarea"), "Please provide more info");
		logMessage("User enter the text for more info requested");
	}

	/**
	 * This method search and selects Patient
	 * 
	 * @param searchType
	 */
	public void searchAndSelectPatient(String patientName) {
		enterPatientDetail(patientName);
		clickOnSearchIcon();
		verifyPatientIsDisplayed(patientName);
		selectPatientByName(patientName);
	}

	/**
	 * This method search and selects patient by name and medicine prescribed
	 * 
	 * @param patientName
	 * @param medicineName
	 */
	public void searchAndSelectPatient(String patientName, String medicineName) {
		enterPatientDetail(patientName);
		clickOnSearchIcon();
		verifyPatientIsDisplayed(patientName, medicineName);
		selectPatientByNameAndMed(patientName, medicineName);
	}

	/**
	 * This method enters Patient Name in search box along filter
	 * 
	 * @param patientName
	 */
	private void enterPatientDetail(String patientName) {
		isElementDisplayed("inp_search");
		enterText(element("inp_search"), patientName);
		logMessage("User enter the " + patientName + " in the search field");
	}

	/**
	 * This method clicks on Search icon
	 */
	private void clickOnSearchIcon() {
		isElementDisplayed("btn_searchIcon");
		executeJavascript(
				"document.getElementsByClassName('zp-searchbar-input-wrapper')[0].getElementsByTagName('button')[0].click()");
		logMessage("User click on Search Icon");
	}

	/**
	 * This method verifies Patient Name is Displayed
	 * 
	 * @param firstName
	 */
	private void verifyPatientIsDisplayed(String firstName) {
		wait.resetImplicitTimeout(5);
		try {
			isElementDisplayed("txt_patientName", firstName);
		} catch (StaleElementReferenceException e) {
			isElementDisplayed("txt_patientName", firstName);
		}
		wait.resetImplicitTimeout(wait.timeout);
	}

	/**
	 * This method verifies Patient Name with prescribed medication is Displayed
	 * 
	 * @param firstName
	 */
	private void verifyPatientIsDisplayed(String patientName, String medicineName) {
		wait.resetImplicitTimeout(5);
		try {
			isElementDisplayed("txt_patNameMed", patientName, medicineName);
		} catch (StaleElementReferenceException e) {
			isElementDisplayed("txt_patNameMed", patientName, medicineName);
		}
		wait.resetImplicitTimeout(wait.timeout);

	}

	/**
	 * This method select the patient
	 * 
	 * @param patientName
	 */
	private void selectPatientByName(String patientName) {
		wait.resetImplicitTimeout(1);
		if (element("td_patList").getText().contains(patientName)) {
			try {
				isElementDisplayed("txt_patientName", patientName);
				wait.waitForElementToBeClickable(element("txt_patientName", patientName));
				executeJavascript("document.getElementById('datagrid').getElementsByTagName('td')[0].click()");
			} catch (StaleElementReferenceException e) {
				isElementDisplayed("txt_patientName", patientName);
				wait.waitForElementToBeClickable(element("txt_patientName", patientName));
				executeJavascript("document.getElementById('datagrid').getElementsByTagName('td')[0].click()");
			}
			logMessage("User clicks on Patient Name");
			wait.resetImplicitTimeout(wait.timeout);
		}
	}

	/**
	 * This method selects patient by name and medicine
	 * 
	 * @param patientName
	 * @param medicineName
	 */
	public void selectPatientByNameAndMed(String patientName, String medicineName) {
		wait.resetImplicitTimeout(1);
		if (elements("td_patList").get(0).getText().contains(patientName)) {
			try {
				wait.waitForStableDom(250);
				element("txt_patNameMed", patientName, medicineName).click();
			} catch (StaleElementReferenceException e) {
				element("txt_patNameMed", patientName, medicineName).click();
			}
			logMessage("User clicks on Patient Name");
			wait.resetImplicitTimeout(wait.timeout);
		}
	}

	/**
	 * This method expands Medical Records section
	 */
	public void expandMedicalRecords() {
		wait.waitForElementToBeClickable(element("h3_medicalRecords", "Medical Records"));
		element("h3_medicalRecords", "Medical Records").click();
		logMessage("User clicks on Medical Records to expand it");
	}

	/**
	 * This method validates 'Add New File' and 'Update PA Status' is displayed
	 */
	public void verifyAddNewFileUpdatePAStatusIsDisplayed() {
		isElementDisplayed("btn_updatePA");
		isElementDisplayed("btn_addNewFile");
	}

	/**
	 * This method verifies 'Update PA Status' is not displayed
	 */
	public void verifyUpdatePAStatusIsNotDisplayed() {
		isElementNotDisplayed("btn_updatePA");
	}

	/**
	 * This method verifies 'Add New File' is not displayed
	 */
	public void verifyAddNewFileIsNotDisplayed() {
		isElementNotDisplayed("btn_addNewFile");
	}

	/**
	 * This method validates 'Add New File' and 'Update PA Status' is not
	 * displayed
	 */
	public void verifyAddNewFileUpdatePAStatusIsNotDisplayed() {
		verifyUpdatePAStatusIsNotDisplayed();
		verifyAddNewFileIsNotDisplayed();
	}

	/**
	 * This method Updates PA Status i.e Submitted to Payer, Approved or Denied
	 * 
	 * @param status
	 */
	public void updatePAStatus(String status) {
		clickOnUpdatePA();
		selectStatusFromDropdowm(status);
		if (status.equalsIgnoreCase("Submitted to Payer"))
			enterSubmitDate();
		else if (status.equalsIgnoreCase("Approved")) {
			enterCompleteDate();
		} else if (status.equalsIgnoreCase("Denied")) {
			enterCompleteDate();
			enterReasonForDenial();
		}
		clickOnUpdatePAFromModal();
		clickOnOkay();
	}

	/**
	 * This method click on Update PA Status
	 */
	public void clickOnUpdatePA() {
		wait.waitForStableDom(250);
		scrollDown(element("btn_updatePA"));
		isElementDisplayed("btn_updatePA");
		wait.waitForElementToBeClickable(element("btn_updatePA"));
		clickUsingXpathInJavaScriptExecutor(element("btn_updatePA"));
		logMessage("User clicks on Update PA Status");
	}

	/**
	 * This method selects status from dropdown
	 */
	private void selectStatusFromDropdowm(String status) {
		selectProvidedTextFromDropDown(element("select_status"), status);
		logMessage("User selects status " + status + " from dropdown");
	}

	/**
	 * This method enters submitted date
	 */
	private void enterSubmitDate() {
		wait.waitForElementToBeClickable(element("inp_subDate"));
		enterText(element("inp_subDate"), getCurrentDateWithSep());
		logMessage("User enters submitted date");
	}

	/**
	 * This method enters completed date
	 */
	private void enterCompleteDate() {
		isElementDisplayed("inp_compDate");
		wait.waitForElementToBeClickable(element("inp_compDate"));
		enterText(element("inp_compDate"), getCurrentDateWithSep());
		logMessage("User enters completed date");
	}

	/**
	 * This method clicks on Update from modal
	 */
	private void clickOnUpdatePAFromModal() {
		element("btn_updateModal", "Update").click();
		logMessage("User clicks on Update from modal");
	}

	/**
	 * This method click on Okay on PA success modal
	 */
	private void clickOnOkay() {
		element("btn_okayContinue").click();
		logMessage("User click on Okay button on confirmation modal");
	}

	/**
	 * This method upload the file and verifies file is uploaded successfully
	 * 
	 * @param fileName
	 */
	public void uploadFileAndVerify(String fileName) {
		String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "com"
				+ File.separator + "zapprx" + File.separator + "testing" + File.separator + "end2endtests"
				+ File.separator + "main" + File.separator + "resources" + File.separator + "testdata" + File.separator
				+ "file" + File.separator + fileName;
		wait.waitForElementToBeVisible(element("btn_addNewFile"));
		executeJavascript("document.getElementById('pa-medical-infos-upload').parentNode.removeAttribute('style');");
		executeJavascript("document.getElementById('pa-medical-infos-upload').parentNode.style.display='block';");
		element("inp_paUpload").sendKeys(filePath);
		executeJavascript("document.getElementById('pa-medical-infos-upload').parentNode.style.display='none';");
		verifyFileIsUploaded(fileName);
		verifyDeleteButtonNotDisplayed();
	}

	/**
	 * This method verifies file is uploaded successfully
	 * 
	 * @param fileName
	 */
	public void verifyFileIsUploaded(String fileName) {
		wait.waitForStableDom(250);
		wait.waitForElementsToBeVisible(elements("link_filename"));
		Assert.assertTrue(elements("link_filename").get(fileCount).getText().contains(fileName),
				"Assertion Failed: File is not uploaded successfully");
		logMessage("Assertion Passed: File is uploaded successfully");
		fileCount = fileCount + 1;
	}

	/**
	 * This method verifies delete button is not displayed
	 */
	public void verifyDeleteButtonNotDisplayed() {
		isElementNotDisplayed("btn_delete");
	}

	/**
	 * This method returns values of different question under clinical section
	 * on PA form
	 * 
	 * @return
	 */
	public List<String> getAnswersOfQuestionsOnPAForm() {
		List<String> clinicalValue = new ArrayList<String>();
		scrollDown(element("div_pharPAClinicalQue"));
		clinicalValue.add(element("inp_pharPAEDSSScore").getAttribute("value"));
		for (int queCount = 1; queCount < elements("div_pharPAClinicalQue").size(); queCount++) {
			if (queCount == 3)
				clinicalValue.add(element("inp_pharPAMedRequest").getText().trim());
			else {
				clinicalValue.add(
						element("select_pharPAMedQuestions", elements("div_pharPAClinicalQue").get(queCount).getText())
								.getText());
			}
		}
		return clinicalValue;
	}

	/**
	 * This method returns Questions under clinical section on PA form
	 * 
	 * @return
	 */
	public List<String> getTextOfQuestionsOnPAForm() {
		List<String> clinicalQuestion = new ArrayList<String>();
		scrollDown(element("div_pharPAClinicalQue"));
		for (WebElement value : elements("div_pharPAClinicalQue")) {
			clinicalQuestion.add(value.getText());
		}
		return clinicalQuestion;
	}

	/**
	 * This method verifies that questions and corresponding values under
	 * clinical section on PA review are same as the corresponding values on PA
	 * form
	 * 
	 * @param clinicalQuestion
	 * @param clinicalValue
	 */
	public void verifyClinicalSectionOnPAReview(List<String> clinicalQuestion, List<String> clinicalValue) {
		scrollDown(element("td_pharReviewQue"));
		for (int count = 0; count < elements("td_pharReviewValue").size() - 2; count++) {
			Assert.assertEquals(elements("td_pharReviewValue").get(count).getText(), clinicalValue.get(count),
					"Assertion Failed: Value " + clinicalValue.get(count) + " is displayed incorrectly");
			logMessage("Assertion Passed: Value " + clinicalValue.get(count) + " is displayed correctly");

			Assert.assertEquals(elements("td_pharReviewQue").get(count).getText(), clinicalQuestion.get(count),
					"Assertion Failed: Value " + clinicalQuestion.get(count) + " is displayed incorrectly");
			logMessage("Assertion Passed: Value " + clinicalQuestion.get(count) + " is displayed correctly");
		}
	}

	/**
	 * This method enters reason for Denial
	 */
	private void enterReasonForDenial() {
		enterText(element("txt_denialReason"), "Denied for appeal");
		logMessage("User enters reason for Denial");
	}

	/**
	 * This method verifies that PA Form title is displayed
	 */
	public void verifyPAFormIsDisplayed(String PAFormTitle) {
		Assert.assertEquals(element("h1_pharPAFormTitle").getText().split(":")[0].trim(), PAFormTitle,
				"Assertion Failed: PA form title " + PAFormTitle + " is not getting displayed");
		logMessage("Assertion Passed: PA form title " + PAFormTitle + " is getting displayed");
	}

	/**
	 * This method click on Print on PA review form
	 */
	public void clickOnPrintOnPAReviewForm() {
		wait.waitForStableDom(250);
		isElementDisplayed("btn_reviewPrint");
		wait.waitForElementToBeClickable(element("btn_reviewPrint"));
		element("btn_reviewPrint").click();
		logMessage("User clicks on Print button");
	}

	/**
	 * This method verifies PA form title and also validates that form can be
	 * downloaded as a pdf
	 * 
	 * @throws IOException
	 */
	public void verifyReviewViewTitleAndContentType() {
		wait.waitForStableDom(250);
		File file = ReadFromPDF.getDownloadedFile();
		Assert.assertTrue(ReadFromPDF.getPdfFileTitle(file).contains("Prior Authorization"),
				"Assertion Failed: Prior Authorization form is not rendered");
		logMessage("Assertion Passed: Prior Authorization form is rendered");
		Assert.assertTrue(ReadFromPDF.checkIfFileIsPDFType(file),
				"Assertion Failed: Prior Authorization form can't be downloaded as PDF");
		logMessage("Assertion Passed: Prior Authorization form can be downloaded as PDF");
	}

	/**
	 * This method verifies PA Alert icon on PA listings page is displayed
	 * 
	 * @param patientName
	 */
	public void verifyPAUrgentAlertIconIsDisplayed(String patientName) {
		isElementDisplayed("i_paUrgntAlertIcon", patientName);
	}

	/**
	 * This method verifies PA Alert icon on PA listings page is not displayed
	 * 
	 * @param patientName
	 */
	public void verifyPAUrgentAlertIconNotDisplayed(String patientName) {
		isElementNotDisplayed("i_paUrgntAlertIcon", patientName);
	}

	/**
	 * This method verifies PA marked as urgent text is displayed on PA input
	 * view
	 * 
	 * @param paMarkedUrgent
	 */
	public void verifyPAIsMarkedAsUrgentTextIsDisplayed(String paMarkedUrgent) {
		isElementDisplayed("span_paUrgentMrked");
		Assert.assertEquals(element("span_paUrgentMrked").getText(), paMarkedUrgent,
				"Assertion Failed: Text '" + paMarkedUrgent + "' is not displayed on PA form");
		logMessage("Assertion Passed: Text '" + paMarkedUrgent + "' is displayed on PA form");
	}

	/**
	 * This method verifies PA marked as urgent text is not displayed on PA
	 * input view
	 */
	public void verifyPAIsMarkedAsUrgentTextNotDisplayed() {
		isElementDisplayed("span_paUrgentMrked");
		Assert.assertEquals(element("span_paUrgentMrked").getText(), "", "Assertion Failed: PA is marked as urgent");
		logMessage("Assertion Passed: PA is not marked as urgent");
	}

	/**
	 * This method verifies additional information header and text on PA form
	 * 
	 * @param additionalInfoHeader
	 * @param additionalInfoText
	 */
	public void verifyAdditionalInfoOnPAForm(String additionalInfoHeader, String additionalInfoText) {
		isElementDisplayed("span_additionalInfo");
		Assert.assertEquals(element("span_additionalInfo").getText(), additionalInfoHeader,
				"Assertion Failed: Additional Information Header '" + additionalInfoHeader
						+ "'is not displayed under clinical section");
		logMessage("Assertion Passed: Additional Information Header '" + additionalInfoHeader
				+ "'is displayed under clinical section");
		Assert.assertEquals(element("txt_additionalInfo").getAttribute("value"), additionalInfoText,
				"Assertion Failed: Additional Information text '" + additionalInfoText
						+ "' is not displayed under clinical section on PA form");
		logMessage("Assertion Passed: Additional Information text '" + additionalInfoText
				+ "' is displayed under clinical section on PA form");
	}

	/**
	 * This method verifies additional information header and text on PA form
	 * review
	 * 
	 * @param additionalInfoHeader
	 * @param additionalInfoText
	 */
	public void verifyAdditionalInfoOnPAFormReview(String additionalInfoHeader, String additionalInfoText) {
		isElementDisplayed("td_additionalInfoHeader", additionalInfoHeader);
		Assert.assertEquals(element("td_additionalInfoText", additionalInfoHeader).getText(), additionalInfoText,
				"Assertion Failed: Additional Information text '" + additionalInfoText
						+ "' is not displayed under clinical section on PA review");
		logMessage("Assertion Passed: Additional Information text '" + additionalInfoText
				+ "' is displayed under clinical section on PA review");
	}

	/**
	 * This method verifies additional information on PA review PDf form
	 * 
	 * @param pdfText
	 * @param additionalInfoHeader
	 * @param additionalInfoText
	 */
	public void verifyAdditionalInfoOnPAReviewPDF(String additionalInfoHeader, String additionalInfoText) {
		File file = ReadFromPDF.getDownloadedFile();
		String pdfText = ReadFromPDF.getTextFromPdfFile(file);
		Assert.assertTrue(pdfText.contains(additionalInfoHeader), "Assertion Failed: Additional Information Header '"
				+ additionalInfoHeader + "'is not displayed under clinical section on PA review PDf");
		logMessage("Assertion Passed: Additional Information Header '" + additionalInfoHeader
				+ "'is displayed under clinical section on PA review PDf");
		Assert.assertTrue(pdfText.contains(additionalInfoText), "Assertion Failed: Additional Information text '"
				+ additionalInfoText + "' is not displayed under clinical section on PA review PDF");
		logMessage("Assertion Passed: Additional Information text '" + additionalInfoText
				+ "' is displayed under clinical section on PA review PDF");
	}

	/**
	 * This method verifies 'Cancel More Info Request' button on PA form
	 */
	public void verifyCancelMoreInfoReqIsDisplayed() {
		isElementDisplayed("btn_cancelMoreInfo");
		Assert.assertEquals(element("btn_cancelMoreInfo").getText(), "Cancel More Info Request",
				"Assertion Failed: Button with text '" + element("btn_cancelMoreInfo").getText()
						+ "' is not displayed on PA form");
		logMessage("Assertion Failed: Button with text '" + element("btn_cancelMoreInfo").getText()
				+ "' is displayed on PA form");
	}

	/**
	 * This method clicks on 'Cancel More Info Request' button on PA form
	 */
	public void clickOnCancelMoreInfoRequest() {
		element("btn_cancelMoreInfo").click();
		logMessage("User clicks on 'Cancel More Info Request' button");
	}

	/**
	 * This method verifies 'Cancel More Info Request' confirmation text on
	 * modal
	 * 
	 * @param confirmText
	 */
	public void verifyCancelMoreInfoRequestConfirmText(String confirmText) {
		Assert.assertEquals(element("p_cancelMoreInfoCnfrmTxt").getText(), confirmText,
				"Assertion Failed: Confirmation modal with text '" + confirmText + "' is not displayed on PA form");
		logMessage("Assertion Failed: Confirmation with text '" + confirmText + "' is displayed on PA form");
	}

	/**
	 * This method clicks on Yes on confirmation modal
	 */
	public void clickOnConfirmModal() {
		element("btn_confirmYes").click();
		logMessage("User click on Yes on confirmation modal");
	}

	/**
	 * This method verifies Cancel More Info Request success message and click
	 * on Okay
	 * 
	 * @param successMsg
	 */
	public void verifySuccessMsgAndClickOkay(String successMsg) {
		Assert.assertEquals(element("p_submitSuccess").getText(), successMsg,
				"Assertion Failed: Success Message " + successMsg
						+ " is not getting displayed and user is able to Send To Plan without submitting signature");
		logMessage("Assertion Passed: Success Message " + successMsg
				+ " is getting displayed and user is not able to Send To Plan without submitting signature");
	}

	/**
	 * This method cancel's More Info Request
	 * 
	 * @param confirmText
	 */
	public void cancelMoreInfoRequest(String confirmText, String successMsg) {
		clickOnCancelMoreInfoRequest();
		verifyCancelMoreInfoRequestConfirmText(confirmText);
		clickOnConfirmModal();
		verifySuccessMsgAndClickOkay(successMsg);
		clickOnOkay();
	}

	/**
	 * This method verifies that 'Cancel More Info Request' button is not
	 * displayed
	 */
	public void verifyCancelMoreInfoReqNotDisplayed() {
		isElementNotDisplayed("btn_cancelMoreInfo");
	}

	/**
	 * This method clicks on 'Prior Authorization'
	 */
	public void clickOnPriorAuthorization() {
		element("btn_priorAuth").click();
		logMessage("User clicks on Prior Authorization button");
	}

	/**
	 * This method verifies that 'Update' button on PA list page is disabled for
	 * a cancelled PA
	 */
	public void verifyUpdateIsDisabled() {
		Assert.assertTrue(element("btn_PAListUpdate").getAttribute("disabled").equalsIgnoreCase("true"),
				"Assertion Failed: Update Rx button is not disabled");
		logMessage("Assertion Passed: Update Rx button is disabled");
	}
}