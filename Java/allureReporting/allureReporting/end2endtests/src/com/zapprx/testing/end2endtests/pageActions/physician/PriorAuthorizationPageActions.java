package com.zapprx.testing.end2endtests.pageActions.physician;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

/**
 * Created by fatemakapoor on 7/1/16.
 */
public class PriorAuthorizationPageActions extends GetPage {
	WebDriver driver;
	private List<WebElement> beforePrintFields;
	private List<WebElement> afterPrintFields;
	private HashMap<String, String> mapBefore;
	private HashMap<String, String> commonElements;
	private HashMap<String, String> mapAfter;

	public PriorAuthorizationPageActions(WebDriver driver) {
		super(driver, "physician/PriorAuthorizationsPage");
		this.driver = driver;
	}

	/**
	 * This method fetches ids of all elements before print
	 */
	public void getInputElements() {
		wait.resetImplicitTimeout(2);
		mapBefore = new HashMap<>();
		try {
			isElementDisplayed("inp_patLastName");
		} catch (StaleElementReferenceException e) {
			isElementDisplayed("inp_patLastName");
		}
		wait.resetImplicitTimeout(wait.timeout);
		beforePrintFields = elements("inp_txt");
		for (WebElement we : beforePrintFields) {
			if (!we.getAttribute("value").isEmpty()) {
				logMessage("INPUT BEFORE PRINT: " + we.getAttribute("id") + "   " + we.getAttribute("value"));
				mapBefore.put(we.getAttribute("id"), we.getAttribute("value"));
			}
		}
	}

	/**
	 * This method clicks on Print button
	 */
	public void clickOnPrint() {
		clickUsingXpathInJavaScriptExecutor(element("btn_print", "Print"));
		logMessage("User clicks on Print button");
	}

	/**
	 * This method validates the form title is displayed after clicking on print
	 * 
	 * @param formTitle
	 */
	public void verifyFormTitle(String formTitle) {
		changeWindow(1);
		isElementDisplayed("span_formTitle");
		wait.waitForElementTextToContain(element("span_formTitle"), formTitle);
		Assert.assertEquals(element("span_formTitle").getText(), formTitle,
				"Assertion Failed: Form name is dispayed incorrectly");
		logMessage("Assertion Passed: Form name is dispayed correctly");
	}

	/**
	 * This method validates the form title is displayed after clicking on print
	 * and switch window
	 * 
	 * @param formTitle
	 */
	public void verifyFormTitleAndSwitchWindow(String formTitle) {
		verifyFormTitle(formTitle);
		closeWindow();
		changeWindow(0);
	}

	/**
	 * This method clicks on Draw Signature
	 */
	public void clickOnDrawSign() {
		wait.waitForStableDom(250);
		scrollDown(element("li_draw"));
		isElementDisplayed("li_draw");
		element("li_draw").click();
		logMessage("User clicks on Draw Signature");
	}

	/**
	 * This method enters Signature
	 */
	public void enterSign() {
		clickOnDrawSign();
		drawSignatureOnCanvas(element("canvas_sig"));
	}

	/**
	 * This method fetches ids of all elements from after print
	 */
	public void getInputElementsForm() {
		afterPrintFields = elements("inp_txt");
		mapAfter = new HashMap<>();
		for (WebElement formInp : afterPrintFields) {
			if (!formInp.getAttribute("value").isEmpty()) {
				logMessage("INPUT AFTER PRINT: " + formInp.getAttribute("id") + "   " + formInp.getAttribute("value"));
				mapAfter.put(formInp.getAttribute("id"), formInp.getAttribute("value"));
			}
		}
	}

	/**
	 * This method compares ids of elements from before print and after print
	 * 
	 * @param medicineName
	 */
	public void compareLists(String medicineName) {
		commonElements = new HashMap<>();
		logMessage("===============COMMON FIELDS FOR *****" + medicineName + "***** ARE=============");
		for (Entry<String, String> entry : mapBefore.entrySet()) {
			if (mapAfter.containsKey(entry.getKey()) && mapAfter.containsValue(entry.getValue())) {
				commonElements.put(entry.getKey(), entry.getValue());
				logMessage("INPUT ID  " + entry.getKey() + " ====>     " + entry.getValue());
			}
		}
		driver.close();
		changeWindow(0);
	}

	/**
	 * This method draw signature on canvas and click on submit
	 * 
	 * @param buttonName
	 * @param PASubmissionMsg
	 */
	public void drawSignAndSubmit(String buttonName, String PASubmissionMsg) {
		clickOnDrawSign();
		drawSignatureOnCanvas(element("canvas_sig"));
		saveSubmitReSubmitPA(buttonName, PASubmissionMsg);
	}

	/**
	 * This method save/submit/Re-submit PA and verifies success message
	 * 
	 * @param buttonName
	 * @param PASubmissionMsg
	 */
	public void saveSubmitReSubmitPA(String buttonName, String PASubmissionMsg) {
		clickOnSaveSubmitReSubButton(buttonName);
		if (buttonName.equalsIgnoreCase("Submit")) {
			clickOnConfirmModal();
			verifySuccessMsgAndClickOkay(PASubmissionMsg);
		} else {
			clickOnOkay();
		}
	}

	/**
	 * This method clicks on Save/Submit/Re-Submit button as per buttonName
	 * 
	 * @param buttonName
	 */
	public void clickOnSaveSubmitReSubButton(String buttonName) {
		wait.waitForStableDom(250);
		scrollDown(element("btn_saveSubmitReSub", buttonName));
		wait.waitForElementToBeClickable(element("btn_saveSubmitReSub", buttonName));
		clickUsingXpathInJavaScriptExecutor(element("btn_saveSubmitReSub", buttonName));
		logMessage("User click on " + buttonName + " button after signature submission");
	}

	/**
	 * This method clicks on Yes on confirmation modal
	 */
	public void clickOnConfirmModal() {
		element("btn_confirmYes").click();
		logMessage("User click on Yes on confirmation modal");
		wait.waitForElementToBeInVisible(getLocator("btn_confirmYes"));
	}

	/**
	 * This method click on Okay on PA success modal
	 */
	public void clickOnOkay() {
		wait.waitForStableDom(250);
		executeJavascript("document.getElementById('zp-modal-success-btn').click();");
		logMessage("User click on Okay/Continue button on confirmation modal");
	}

	/**
	 * This method verifies PA submission success message and click on Okay
	 * 
	 * @param successMsg
	 */
	public void verifySuccessMsgAndClickOkay(String successMsg) {
		Assert.assertEquals(element("p_submitSuccess").getText(), successMsg,
				"Assertion Failed: Success Message " + successMsg
						+ " is not getting displayed and user is able to Send To Plan without submitting signature");
		logMessage("Assertion Passed: Success Message " + successMsg
				+ " is getting displayed and user is not able to Send To Plan without submitting signature");
		clickOnOkay();
	}

	/**
	 * This method click on View Prescription Details on PA form
	 */
	public void clickOnViewPresDetails(String medicineName) {
		isElementDisplayed("a_viewRx", medicineName);
		Assert.assertTrue(element("a_viewRx").getText().contains(medicineName),
				"Assertion Failed: Medication Name is displayed incorrect");
		wait.waitForStableDom(250);
		scrollDown(element("a_viewRx", medicineName));
		wait.waitForElementToBeClickable(element("a_viewRx", medicineName));
		executeJavascript("document.getElementsByClassName('left-divider')[0].click();");
		logMessage("User click on " + medicineName + " Rx");
	}

	/**
	 * This method verifies that PA Form title is displayed
	 */
	public void verifyPAFormIsDisplayed(String PAFormTitle) {
		wait.waitForStableDom(250);
		if (PAFormTitle.contains("Universal"))
			Assert.assertEquals(element("h1_paFormTitle").getText().split(":")[0].trim(), PAFormTitle,
					"Assertion Failed: PA form title " + PAFormTitle + " is not getting displayed");
		else
			Assert.assertEquals(element("h1_paFormTitle").getText(), PAFormTitle,
					"Assertion Failed: PA form title " + PAFormTitle + " is not getting displayed");
		logMessage("Assertion Passed: PA form title " + PAFormTitle + " is getting displayed");
	}

	/**
	 * This method expands given section of PA form
	 */
	public void expandTabs(String option) {
		scrollDown(element("h3_paTabs", option));
		if (element("i_paTabsAngle", option).getAttribute("class").contains("fa-angle-right")) {
			clickUsingXpathInJavaScriptExecutor(element("h3_paTabs", option));
			logMessage("User click on " + option + " section to expand it");
		}
	}

	/**
	 * This method verifies that Office Phone option is displayed
	 */
	public void verifyOfficePhoneIsDisplayed() {
		Assert.assertEquals(element("div_officePhone").getText(), "Office Phone",
				"Assertion Failed: Office Phone option is not getting displayed");
		logMessage("Assertion Passed: Office Phone is getting displayed");
	}

	/**
	 * This method verifies that Office Email option is displayed
	 */
	public void verifyOfficeEmailIsDisplayed() {
		Assert.assertEquals(element("div_officeEmail").getText(), "Email",
				"Assertion Failed: Office Email option is not getting displayed");
		logMessage("Assertion Passed: Office Email is getting displayed");
	}

	/**
	 * This method verifies that 'save'/'Submit' button is not displayed
	 * 
	 * @param buttonName
	 */
	public void verifySaveSubmitButtonIsNotDisplayed(String buttonName) {
		isElementNotDisplayed("btn_saveSubmitReSub", buttonName);
	}

	/**
	 * This method verifies that 'save' button is displayed
	 * 
	 * @param buttonName
	 */
	public void verifySaveButtonIsDisplayed(String buttonName) {
		wait.waitForStableDom(250);
		scrollDown(element("btn_saveSubmitReSub", buttonName));
		isElementDisplayed("btn_saveSubmitReSub", buttonName);
	}

	/**
	 * This method enters Insurance Member Id
	 * 
	 * @param memberId
	 */
	public void enterMassHealthMemberId(String memberId) {
		enterText(element("inp_insuranceMemId"), memberId);
		logMessage("This method enters " + memberId + " in Insurance Member Id field");
	}

	/**
	 * This method verifies Insurance Member Id
	 * 
	 * @param memberId
	 */
	public void verifyMassHealthMemberId(String memberId) {
		Assert.assertEquals(element("inp_insuranceMemId").getAttribute("value"), memberId,
				"Assertion Failed: Insurance Member Id is not getting displayed");
		logMessage("Assertion Passed: Insurance Member Id is getting displayed");
		driver.close();
		changeWindow(0);
	}

	/**
	 * This method verifies PA Status on form
	 * 
	 * @param PAStatus
	 */
	public void verifyPAStatus(String PAStatus) {
		wait.waitForStableDom(250);
		isElementDisplayed("span_paStatusOnForm");
		Assert.assertEquals(element("span_paStatusOnForm").getText(), PAStatus,
				"Assertion Failed: PA Status: " + PAStatus + " is not displayed on PA input screen");
		logMessage("Assertion Passed: PA Status: " + PAStatus + " is displayed on PA input screen");
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
		wait.waitForElementToBeVisible(element("btn_addPAFile"));
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
		Assert.assertEquals(element("link_phyFileName").getText(), fileName,
				"Assertion Failed: File is not uploaded successfully");
		logMessage("Assertion Passed: File is uploaded successfully");
	}

	/**
	 * This method verifies delete button is not displayed
	 */
	public void verifyDeleteButtonNotDisplayed() {
		isElementNotDisplayed("btn_delete");
	}

	/**
	 * This method verifies all the Prior and Current medications present on PA
	 * input screen
	 * 
	 * @param medications
	 */
	public void verifyPriorAndCurrentMedications(String... medications) {
		scrollDown(element("span_priorAndCurrentMed"));
		for (int i = 0; i < medications.length; i++) {
			Assert.assertTrue(elements("span_priorAndCurrentMed").get(i).getText().equalsIgnoreCase(medications[i]),
					"Assertion Failed: Medication " + medications[i]
							+ " is not dispalyed under Prior and Current Medications");
			logMessage("Assertion Passed: Medication " + medications[i]
					+ " is dispalyed under Prior and Current Medications");
		}
	}

	/**
	 * This method verifies value in 'Reason Ended' field
	 * 
	 * @param medication
	 * @param reason
	 */
	private void verifyReasonEndedField(String medication, String reason) {
		Assert.assertEquals(element("inp_reasonEnded", medication).getAttribute("value"), reason,
				"Assertion Failed: Reason Ended : " + reason + " is not displayed under Prior and Current Medications");
		logMessage("Assertion Passed: Reason Ended: " + reason + " is displayed under Prior and Current Medications");
	}

	/**
	 * This method verifies value in 'Status' field
	 * 
	 * @param medication
	 * @param status
	 */
	private void verifyStatusField(String medication, String status) {
		Assert.assertEquals(getProvidedTextFromDropDown(element("select_priorStatus", medication)), status,
				"Assertion Failed: Status: " + status + " is not displayed under Prior and Current Medications");
		logMessage("Assertion Passed: Status: " + status + " is displayed under Prior and Current Medications");
	}

	/**
	 * This method verifies that values in Reason Ended and Status fields are
	 * displayed correctly under prior and current medication
	 */
	public void verifyPriorAndCurrentMedFields(String medication, String reason, String status) {
		verifyStatusField(medication, status);
		verifyReasonEndedField(medication, reason);
	}

	/**
	 * This method clicks on Patient Name from header links to view patient
	 * profile
	 * 
	 * @param lastName
	 */
	public void clickOnPatientName() {
		scrollDown(element("a_patientName"));
		executeJavascript("document.getElementsByClassName('pa-header-links')[0].getElementsByTagName('a')[0].click()");
		logMessage("User clicks on Patient Name to view patient Profile");
	}

	/**
	 * This method enters value in EDSS score under general question
	 */
	public void enterGeneralQuestionValue(String edssScore) {
		enterText(element("inp_edssScore"), edssScore);
		logMessage("User enters EDSS Score");
	}

	/**
	 * This method validates no duplicate entry for epoprostenol question
	 * 
	 * @param epoprostenolQue
	 */
	public void verifyDuplicateEntryForEpoprostenolQuestion(String epoprostenolQue) {
		executeJavascript("document.getElementById('doctor-nav-toggle').click();");
		scrollDown(element("select_genMedQues", epoprostenolQue));
		Assert.assertTrue(elements("select_genMedQues", epoprostenolQue).size() == 1,
				"Assertion Failed: Medication question : " + epoprostenolQue + " is not appearing once");
		logMessage("Assertion Passed: Medication question: " + epoprostenolQue + " is appearing once");
	}

	/**
	 * This method enters value in medication questions
	 * 
	 * @param medicationQueAns
	 */
	public void enterGeneralOrMedicationQuestionAns(String... medicationQueAns) {
		String question, ans;
		executeJavascript("document.getElementById('doctor-nav-toggle').click();");
		scrollDown(element("select_genMedQues", medicationQueAns[0]));
		for (int queAnsCount = 0; queAnsCount < medicationQueAns.length; queAnsCount++) {
			question = medicationQueAns[queAnsCount];
			ans = medicationQueAns[++queAnsCount];
			if (question.equalsIgnoreCase(getYamlValue("physician.medicationQuestions.medicationRequest.req1"))) {
				clickUsingXpathInJavaScriptExecutor(element("inp_medRequest", question));
				logMessage("User selects " + question + " in medication request");
				--queAnsCount;
			} else {
				selectProvidedTextFromDropDown(element("select_genMedQues", question), ans);
				logMessage("User selects " + ans + " from dropdown under " + question);
			}
		}
	}

	public void selectVascularResistanceUnit(String unitQues, String unitValue) {
		selectProvidedTextFromDropDown(element("select_vascularUnit", unitQues), unitValue);
		logMessage("User selects " + unitValue + " vascular unit");
	}

	/**
	 * This method enters decimal values in fields under Right-Heart
	 * Catheterization
	 * 
	 * @param medicationQueAns
	 */
	public void enterDecimalValuesForRightHeartCatheterizationFields(String... PAMQueAns) {
		String question, ans;
		for (int queAnsCount = 0; queAnsCount < PAMQueAns.length; queAnsCount++) {
			question = PAMQueAns[queAnsCount];
			ans = PAMQueAns[++queAnsCount];
			enterText(element("inp_pulmonaryArteryFields", question), ans);
			logMessage("User enters " + ans + " under " + question);
		}
	}

	/**
	 * This method verifies answers under medication questions are saved
	 * correctly
	 * 
	 * @param medicationQueAns
	 */
	public void verifySavedMedicationQuestionAns(String... medicationQueAns) {
		String question, ans;
		scrollDown(element("select_genMedQues", medicationQueAns[0]));
		for (int queAnsCount = 0; queAnsCount < medicationQueAns.length; queAnsCount++) {
			question = medicationQueAns[queAnsCount];
			ans = medicationQueAns[++queAnsCount];
			if (question.equalsIgnoreCase(getYamlValue("physician.medicationQuestions.medicationRequest.req1"))) {
				Assert.assertTrue(element("inp_medRequest", question).isSelected(),
						"Assertion Failed: " + question + " is not selected under medication request");
				logMessage("Assertion Passed: " + question + " is selected under medication request");
				--queAnsCount;
			} else if (ans.equalsIgnoreCase("Yes")) {
				Assert.assertTrue(element("select_genMedQues", question).getAttribute("value").contains("true"),
						"Assertion Failed: " + ans + " is not displayed under " + question + "medication question");
				logMessage("Assertion Passed: " + ans + " is displayed under " + question + "medication question");
			} else if (ans.equalsIgnoreCase("No")) {
				Assert.assertTrue(element("select_genMedQues", question).getAttribute("value").contains("false"),
						"Assertion Failed: " + ans + " is not displayed under " + question + "medication question");
				logMessage("Assertion Passed: " + ans + " is displayed under " + question + "medication question");
			} else {
				Assert.assertTrue(
						element("select_genMedQues", question).getAttribute("value").contains(ans.toUpperCase()),
						"Assertion Failed: " + ans + " is not displayed under" + question + " medication question");
				logMessage("Assertion Passed: " + ans + " is displayed under " + question + "medication question");
			}
		}
	}

	/**
	 * This method verifies values of Right-Heart Catheterization fields are
	 * saved correctly
	 * 
	 * @param medicationQueAns
	 */
	public void verifySavedValuesOfRightHeartCatheterizationFields(String... PAMQueAns) {
		String question, ans;
		scrollDown(element("inp_pulmonaryArteryFields", PAMQueAns[0]));
		for (int queAnsCount = 0; queAnsCount < PAMQueAns.length; queAnsCount++) {
			question = PAMQueAns[queAnsCount];
			ans = PAMQueAns[++queAnsCount];
			Assert.assertTrue(element("inp_pulmonaryArteryFields", question).getAttribute("value").contains(ans),
					"Assertion Failed: " + ans + " is not displayed under" + question + " medication question");
			logMessage("Assertion Passed: " + ans + " is displayed under " + question + "medication question");
		}
	}

	/**
	 * This method selects type of Prescription Attachment from dropdown
	 * 
	 * @param presType
	 */
	public void selectTypeOfPresAttachment(String presType) {
		selectProvidedTextFromDropDown(element("select_presUploadType"), presType);
		logMessage("User selects type or Prescription Attachment to be uploaded");
	}

	/**
	 * This method upload the file under Prescription Attachment and verifies
	 * file is uploaded successfully
	 * 
	 * @param fileName
	 */
	public void uploadPresAttachFileAndVerify(String fileName) {
		String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "com"
				+ File.separator + "zapprx" + File.separator + "testing" + File.separator + "end2endtests"
				+ File.separator + "main" + File.separator + "resources" + File.separator + "testdata" + File.separator
				+ "file" + File.separator + fileName;
		wait.waitForElementToBeVisible(element("btn_addPresAttachFile"));
		executeJavascript("document.getElementById('rx-medical-infos-upload').parentNode.removeAttribute('style');");
		executeJavascript("document.getElementById('rx-medical-infos-upload').parentNode.style.display='block';");
		element("inp_presUpload").sendKeys(filePath);
		executeJavascript("document.getElementById('rx-medical-infos-upload').parentNode.style.display='none';");
		verifyPresAttachFileIsUploaded(fileName);
	}

	/**
	 * This method verifies Prescription Attachment file is uploaded
	 * successfully
	 * 
	 * @param fileName
	 */
	public void verifyPresAttachFileIsUploaded(String fileName) {
		Assert.assertEquals(element("a_presFileName").getText(), fileName,
				"Assertion Failed: File is not uploaded successfully");
		logMessage("Assertion Passed: File is uploaded successfully");
	}

	/**
	 * This method clicks on delete button to delete Prescription Attachment
	 * file
	 */
	private void clickOnDeletePresAttachFile() {
		element("btn_presAttachDelete").click();
		logMessage("User clicks on Delete to delete Prescription Attachment");
	}

	/**
	 * This method deletes Prescription Attachment file and validates that file
	 * is deleted successfully
	 * 
	 * @param successMsg
	 */
	public void deletePresAttachFileAndVerify(String successMsg) {
		clickOnDeletePresAttachFile();
		clickOnConfirmModal();
		verifySuccessMsgAndClickOkay(successMsg);
		isElementNotDisplayed("a_presFileName");
	}

	/**
	 * This method verifies that 'submit' button is not displayed
	 */
	public void verifySubmitButtonIsNotDisplayed(String buttonName) {
		isElementNotDisplayed("btn_saveSubmitReSub", buttonName);
	}

	/**
	 * This method verifies that 'General Questions' header is displayed
	 */
	public void verifyGeneralQuestions() {
		isElementDisplayed("h5_phygeneralques");
	}

	/**
	 * This method returns Questions under clinical section on PA form
	 * 
	 * @return
	 */
	public List<String> getTextOfQuestionsOnPAForm() {
		List<String> clinicalQuestion = new ArrayList<String>();
		scrollDown(element("div_phyMedQues"));
		for (WebElement ques : elements("div_phyMedQues")) {
			clinicalQuestion.add(ques.getText().trim());
		}
		return clinicalQuestion;
	}

	/**
	 * This method clicks on appeal button
	 */
	private void clickOnAppeal() {
		element("btn_appeal").click();
		logMessage("User clicks on Appeal button");
	}

	/**
	 * This method saves PA under 'Appeal'
	 */
	public void savePAUnderAppeal() {
		clickOnAppeal();
		clickOnConfirmModal();
		clickOnOkay();
	}

	/**
	 * This method performs PA Appeal process
	 */
	public void appealPAAndResubmit() {
		savePAUnderAppeal();
		clickOnSaveSubmitReSubButton(getYamlValue("physician.priorAuthButton.reSubmit"));
		clickOnOkay();
	}

	/**
	 * This method verifies New Diagnosis details under Daignosis tab
	 * 
	 * @param newDiagnosisValue
	 * @param newDiagnosisName
	 */
	public void verifyDiagnosisDetails(String primaryDiagnosis, String newDiagnosis, String otherDiagnosis) {
		Assert.assertEquals(element("p_primaryDiagnosis").getText(), primaryDiagnosis,
				"Assertion Failed: Primary Diagnosis value '" + primaryDiagnosis + "' is not displayed");
		logMessage("Assertion Passed: Primary Diagnosis value '" + primaryDiagnosis + "' is displayed");
		Assert.assertEquals(element("p_newDiagnosis").getText(), newDiagnosis,
				"Assertion Failed: New Diagnosis value '" + newDiagnosis + "' is not displayed");
		logMessage("Assertion Passed: New Diagnosis value '" + newDiagnosis + "' is displayed");
		Assert.assertEquals(element("p_otherDiagnosis").getText(), otherDiagnosis,
				"Assertion Failed: Other Diagnosis value '" + otherDiagnosis + "' is not displayed");
		logMessage("Assertion Passed: Other Diagnosis value '" + otherDiagnosis + "' is displayed");
	}

	/**
	 * This method validates value of EDSS score on PA form under 'General
	 * Questions'
	 * 
	 * @param edssScore
	 */
	public void verifyValueOfEDSSScore(String edssScore) {
		Assert.assertEquals(element("inp_edssScore").getAttribute("value"), edssScore,
				"Assertion Failed: EDSS score value '" + edssScore + "' is not displayed under general questions");
		logMessage("Assertion Passed: EDSS score value '" + edssScore + "' is displayed under general questions");
	}

	/**
	 * This method clicks on 'Mark PA as Urgent' checkbox
	 */
	public void clickOnMarkPAUrgentCheckbox() {
		element("inp_paUrgntCheckbox").click();
		logMessage("User clicks on 'Mrak PA as Urgent' checkbox");
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
	 * This method verifies additional information header under clinical section
	 * 
	 * @param additionalInfo
	 */
	public void verifyAdditionalInfoHeader(String additionalInfoHeader) {
		isElementDisplayed("span_additionalInfo");
		Assert.assertEquals(element("span_additionalInfo").getText(), additionalInfoHeader,
				"Assertion Failed: Additional Information is not displayed under clinical section");
		logMessage("Assertion Passed: Additional Information is displayed under clinical section");
	}

	/**
	 * This method verifies additional information section is displayed
	 * 
	 * @param patab
	 */
	public void verifyAdditionalInfoSectionIsDisplayed(String patab) {
		isElementDisplayed("h3_paTabs", patab);
	}

	/**
	 * This method enters text for additional information
	 * 
	 * @param text
	 */
	public void enterTextForAdditionalInfo(String text) {
		enterText(element("txt_additionalInfo"), text);
		logMessage("User enters text under additional information");
	}

	/**
	 * This method verifies additional information header and text on the
	 * overflow page while rendering the form to print
	 * 
	 * @param additionalInfoHeader
	 * @param additionalInfoText
	 */
	public void verifyAdditionalInfoOnOverflowPage(String additionalInfoHeader, String additionalInfoText) {
		Assert.assertEquals(element("label_additionalInfo").getText(), additionalInfoHeader,
				"Assertion Failed: Additional Information Header '" + additionalInfoHeader
						+ "'is not displayed on overflow page");
		logMessage("Assertion Passed: Additional Information Header '" + additionalInfoHeader
				+ "'is displayed on overflow page");
		Assert.assertEquals(element("txt_printAdditionalInfo").getAttribute("value"), additionalInfoText,
				"Assertion Failed: Additional Information text '" + additionalInfoText
						+ "' is not displayed on overflow page");
		logMessage("Assertion Passed: Additional Information text '" + additionalInfoText
				+ "' is displayed on overflow page");
		closeWindow();
		changeWindow(0);
	}

	/**
	 * This method verifies that selected dosage option on dosage page is
	 * getting displayed under Dosage section on PA input page
	 * 
	 * @param dosageValue
	 */
	public void verifyDosageOption(String medicineName, String... dosageValue) {
		String dosageText = element("p_dosages").getText();
		if (medicineName.equalsIgnoreCase("Adempas"))
			dosageText = element("p_dosages").getText().split("Maintenance")[1];
		System.out.println(element("p_dosages").getText());
		for (int count = 0; count < dosageValue.length; count++) {
			if (!dosageValue[count].equalsIgnoreCase(" ")) {
				Assert.assertTrue(dosageText.contains(dosageValue[count]), "Assertion Failed: Dosage Value '"
						+ dosageValue[count] + "' is not getting displayed on PA input page");
				logMessage("Assertion Passed: Dosage Value '" + dosageValue[count]
						+ "' is getting displayed on PA input page");
			}
		}
	}

	/**
	 * This method verifies that selected initial dosage option on dosage page
	 * is getting displayed under Dosage section on PA input page for Adempas
	 * medication
	 * 
	 * @param dosageValue
	 */
	public void verifyInitialDosage_Adempas(String... dosageValue) {
		for (int count = 0; count < dosageValue.length; count++) {
			Assert.assertTrue(element("p_dosages").getText().split("Maintenance")[0].contains(dosageValue[count]),
					"Assertion Failed: Dosage Value '" + dosageValue[count]
							+ "' is not getting displayed on PA input page");
			logMessage("Assertion Passed: Dosage Value '" + dosageValue[count]
					+ "' is getting displayed on PA input page");
		}
	}

	/**
	 * This method verifies that selected frequency option on dosage page is
	 * getting displayed under Dosage section on PA input page
	 * 
	 * @param dosageValue
	 */
	public void verifyDirectionOption(String frequency) {
		if (!frequency.equalsIgnoreCase(" ")) {
			Assert.assertTrue(element("p_rxInstruction").getText().contains(frequency),
					"Assertion Failed: Dosage Value '" + frequency + "' is not getting displayed on PA input page");
			logMessage("Assertion Passed: Dosage Value '" + frequency + "' is getting displayed on PA input page");
		}
	}

	/**
	 * This method verifies that physician signature is saved
	 */
	public void verifySignatureIsSaved() {
		isElementDisplayed("img_physicianSig");
	}

	/**
	 * This method verifies PA signature error message and click on Okay
	 * 
	 * @param successMsg
	 */
	public void verifySigErrorMsgAndClickOkay(String sigErrorMsg) {
		Assert.assertEquals(element("p_errorMsg").getText(), sigErrorMsg,
				"Assertion Failed: Error Message " + sigErrorMsg
						+ " is not getting displayed and user is able to Submit/Save PA without submitting signature");
		logMessage("Assertion Passed: Error Message " + sigErrorMsg
				+ " is getting displayed and user is not able to Submit/Save PA without submitting signature");
		clickOnOkayOnErrorModal();
	}

	/**
	 * This method click on Okay button on Sig error modal
	 */
	public void clickOnOkayOnErrorModal() {
		element("btn_errorOkay").click();
		logMessage("User click on Okay button on Sig error modal");
		wait.waitForElementToBeInVisible(getLocator("btn_okayContinue"));
	}

	/**
	 * This method verifies that user is not able to submit PA without signature
	 * by validating signature error message on modal
	 * 
	 * @param buttonName
	 * @param sigErrorMsg
	 */
	public void verifyUserNotAbleToSubmitWithoutSig(String buttonName, String sigErrorMsg) {
		clickOnSaveSubmitReSubButton(buttonName);
		verifySigErrorMsgAndClickOkay(sigErrorMsg);
	}

	/**
	 * This method validates the quantity value under Dosage on PA
	 * 
	 * @param quantity
	 */
	public void verifyQuantityForDosage(String quantity) {
		Assert.assertTrue(element("p_dosages").getText().contains(quantity + " capsules"),
				"Assertion Failed: Incorrect quantity value is displayed on PA page");
		logMessage("Assertion Passed: Correct quantity value is displayed on PA page");
	}

	/**
	 * This method validates that quantity value '30-day supply' under Dosage on
	 * PA is displayed once
	 * 
	 * @param quantity
	 */
	public void verifyQuantityValueAppearingOnce(String quantity) {
		String initialDosage = element("p_dosages").getText().split("Maintenance")[0];
		String maintenanceDosage = element("p_dosages").getText().split("Maintenance")[1].split("Goal")[0];

		Assert.assertTrue(initialDosage.contains(quantity),
				"Assertion Failed: Quantity value '" + quantity + "' is not displayed under Initial Dosage");
		logMessage("Assertion Passed: Quantity value '" + quantity + "' is displayed under Initial Dosage");
		Assert.assertTrue(maintenanceDosage.contains(quantity),
				"Assertion Failed: Quantity value '" + quantity + "' is not displayed under Maintenance Dosage");
		logMessage("Assertion Passed: Quantity value '" + quantity + "' is displayed under Maintenance Dosage");

		Assert.assertFalse(initialDosage.split("\\n")[1].contains(quantity), "Assertion Failed: Quantity value '"
				+ quantity + "' under Initial Dosage is appearing twice on PA page");
		logMessage("Assertion Passed: Quantity value '" + quantity
				+ "' under Initial Dosage is appearing once on PA page");
		Assert.assertFalse(maintenanceDosage.split("\\n")[1].contains(quantity), "Assertion Failed: Quantity value '"
				+ quantity + "' under Maintenance Dosage is appearing twice on PA page");
		logMessage("Assertion Passed: Quantity value '" + quantity
				+ "' under Maintenance Dosage is appearing once on PA page");
	}
}
