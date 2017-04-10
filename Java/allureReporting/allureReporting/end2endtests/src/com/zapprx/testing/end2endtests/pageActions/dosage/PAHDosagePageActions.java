package com.zapprx.testing.end2endtests.pageActions.dosage;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;
import com.zapprx.testing.end2endtests.automation.utils.YamlReader;

public class PAHDosagePageActions extends GetPage {
	WebDriver driver;
	private int medicationCount = 1;

	public PAHDosagePageActions(WebDriver driver) {
		super(driver, "physician/DosagePage");
		this.driver = driver;
	}

	/**
	 * This method selects WHO group
	 * 
	 * @param group
	 */
	public void selectWHOGroup(String group) {
		isElementDisplayed("select_whoGrp");
		selectProvidedTextFromDropDown(element("select_whoGrp"), group);
		logMessage("User selects " + group + " from WHO Group dropdown");
	}

	/**
	 * This method selects NHYA Functional class
	 * 
	 * @param nhyaClass
	 */
	public void selectNYHAFunctionalClass(String nhyaClass) {
		selectProvidedTextFromDropDown(element("select_nyhaClass"), nhyaClass);
		logMessage("User selects " + nhyaClass + " from NHYA Functional class dropdown");
	}

	/**
	 * This method validates that refills field is empty after entering
	 * non-integer value
	 * 
	 * @param refillValue
	 */
	public void verifyUserIsUnableToEnterNonIntegerValue(String refillValue) {
		enterRefillValue(refillValue);
		verifyRefillFieldIsEmpty();
	}

	/**
	 * This method verifies refill field is empty
	 */
	public void verifyRefillFieldIsEmpty() {
		Assert.assertTrue(element("inp_refills", "Refills").getAttribute("value").isEmpty(),
				"Assertion Failed : User is able to enter non-integer value in Refills field");
		logMessage("Assertion Passed : User is not able to enter non-integer value in Refills field");
	}

	/**
	 * This method enters non-integer value in refills field
	 * 
	 * @param refillValue
	 */
	public void enterRefillValue(String refillValue) {
		enterText(element("inp_refills", "Refills"), refillValue);
		logMessage("User enters value in Refills field");
	}

	/**
	 * This method enters the updated value for titration refill
	 * 
	 * @param refill
	 */
	public void editTitrationRefill(String refill) {
		enterText(element("inp_titRefill"), refill);
		logMessage("User edit the Titration refills value and enters " + refill);
	}

	/**
	 * This method clears the value for Initial Dosage
	 */
	public void clearInitialDosage() {
		element("inp_iniDos").clear();
		logMessage("User clear the field value for Initial Dosage");
	}

	/**
	 * This method validates Initiation Dosage field is present
	 */
	public void verifyDosageFieldIsDisplayed() {
		wait.waitForElementToBeVisible(element("inp_iniDos"));
		Assert.assertTrue(element("inp_iniDos").isDisplayed(),
				"Assertion False: Initiation Dosage is not displayed after entering empty value");
		logMessage("Assertion Passed: Initiation Dosage is displayed after entering empty value");
	}

	/**
	 * This method clicks on Date Picker for Date of Walk Test
	 */
	public void clickOnDateForWalkTest() {
		wait.waitForElementToBeClickable(element("btn_date"));
		scrollDown(element("btn_date"));
		element("btn_date").click();
		logMessage("User clicks on Date Picker");
	}

	/**
	 * This method validates previous and next button is displayed on calendar
	 */
	public void verifyPrevAndNextOnCal() {
		isElementDisplayed("link_prv");
		Assert.assertTrue(element("link_prv").isDisplayed(),
				"Assertion Failed: Previous button is not displayed on calendar");
		logMessage("Assertion Passed: Previous button is displayed on calendar");
		isElementDisplayed("link_nxt");
		Assert.assertTrue(element("link_nxt").isDisplayed(),
				"Assertion Failed: Next button is not displayed on calendar");
		logMessage("Assertion Passed: Next button is displayed on calendar");
	}

	/**
	 * This field selects dose value for Remodulin medications
	 * 
	 * @param dose
	 */
	public void selectDose(String text, String dose) {
		if (!element("inp_RemodulinDosage", text, dose).isSelected()) {
			clickUsingXpathInJavaScriptExecutor(element("inp_RemodulinDosage", text, dose));
			logMessage("User selects " + dose + " for Dose field");
		}
	}

	/**
	 * This field selects dose value for Adempas on marty branch
	 * 
	 * @param dose
	 */
	public void selectInitialDose(String dose) {
		if (!dose.equalsIgnoreCase(" ")) {
			if (!element("inp_testInitialDose", dose).isSelected()) {
				clickUsingXpathInJavaScriptExecutor(element("inp_testInitialDose", dose));
				logMessage("User selects " + dose + " for Dose field");
			}
			selectInitialDoseQuantity(getYamlValue("physician.Quantity.quantity2"));
		}
	}

	/**
	 * This method selects quantity under Initial Dose on marty branch
	 * 
	 * @param quantity
	 */
	public void selectInitialDoseQuantity(String quantity) {
		selectProvidedTextFromDropDown(element("select_initialDoseQuantity"), quantity);
		logMessage("User selects value " + quantity + " for Quantity field");
	}

	/**
	 * This method selects Strength for orenitram medication
	 * 
	 * @param strength
	 */
	public void selectStrengthTitration(String strength) {
		if (!element("inp_testStrength", strength).isSelected()) {
			clickUsingXpathInJavaScriptExecutor(element("inp_testStrength", strength));
			logMessage("User selects " + strength + " for Titration field");
		}
	}

	/**
	 * This method selects the yes option in Calcium Channel Blocker Statement
	 * 
	 * @param yesOption
	 * @param statement
	 */
	public void selectCalBlockerOption(String option, String statement) {
		scrollDown(element("div_ccbStatement", statement));
		isElementDisplayed("inp_calBlockerOption", option);
		executeJavascript(
				"document.getElementsByClassName('radio_question')[0].getElementsByTagName('input')[0].click()");
		logMessage("User selects " + option + " for Calcium Channel Blocker Statement field");
	}

	/**
	 * This method enters the Calcium Channel Blocker
	 * 
	 * @param text
	 */
	public void enterCCB(String text) {
		enterText(element("txt_hypersens"), text);
		logMessage("User enters " + text + " for Calcium Channel Blocker text box field");
	}

	/**
	 * This method clicks on responses checkboxes of the Calcium Channel Blocker
	 * 
	 * @param hyperSensResp
	 */
	public void clickOnHyperSensOption(String hyperSensResp) {
		isElementDisplayed("inp_calResponses", hyperSensResp);
		element("inp_calResponses", hyperSensResp).click();
		logMessage("User clicks on " + hyperSensResp + " for Calcium Channel Blocker field");
	}

	/**
	 * This method clicks on responses checkboxes of the Calcium Channel Blocker
	 * 
	 * @param patietSympResp
	 */

	public void clickOnPatientSympOption(String patietSympResp) {
		isElementDisplayed("inp_calResponses", patietSympResp);
		element("inp_calResponses", patietSympResp).click();
		logMessage("User clicks on " + patietSympResp + " for Calcium Channel Blocker field");
	}

	/**
	 * This method clicks on responses checkboxes of the Calcium Channel Blocker
	 * 
	 * @param otherResp
	 */
	public void clickOnOthersOption(String otherResp) {
		isElementDisplayed("inp_calResponses", otherResp);
		element("inp_calResponses", otherResp).click();
		logMessage("User clicks on " + otherResp + " for Calcium Channel Blocker field");

	}

	/**
	 * This method enters values on responses textboxes of the Calcium Channel
	 * Blocker
	 * 
	 * @param hyperSensTxt
	 */
	public void enterHyperSensitivity(String hyperSensTxt) {
		enterText(element("txt_responsesValue", hyperSensTxt), hyperSensTxt);
	}

	/**
	 * This method enters values on responses textboxes of the Calcium Channel
	 * Blocker
	 * 
	 * @param hyperSensTxt
	 */

	public void enterPatientSymp(String patietSympTxt) {
		enterText(element("txt_responsesValue", patietSympTxt), patietSympTxt);
	}

	/**
	 * This method enters values on responses textboxes of the Calcium Channel
	 * Blocker
	 * 
	 * @param hyperSensTxt
	 */
	public void enterOthersTxt(String otherTxt) {
		enterText(element("txt_responsesValue", otherTxt), otherTxt);
	}

	/**
	 * This method verifies that Compassionate Use Header as well as checkbox is
	 * displayed
	 */
	public void verifyOpsumitCompassionateUse() {
		isElementDisplayed("h2_compassionateHeader");
		isElementDisplayed("inp_compassionateCheckbox");
	}

	/**
	 * This method selects diluent value for Flolan, Veletri and epoprostenol
	 * medication
	 * 
	 * @param diluent
	 */
	public void selectDiluent(String diluent) {
		int index = 0;
		if (!diluent.equalsIgnoreCase(" ")) {
			for (WebElement diluentOption : elements("select_testDiluentOptions")) {
				if (diluentOption.getText().contains(diluent)) {
					selectTextFromDropDownByIndex(element("select_DiluentDropdown"), index);
					logMessage("User selects " + diluent + " under 'Diluent' field");
					break;
				} else
					++index;
			}
		}
	}

	/**
	 * This method selects pump value for Flolan, Veletri and epoprostenol
	 * medication
	 * 
	 * @param medication
	 * @param pump
	 */
	public void selectPump(String pump) {
		if (!pump.equalsIgnoreCase(" ")) {
			selectProvidedTextFromDropDown(element("select_testPump"), pump);
			logMessage("User selects " + pump + " under 'Pump' field");
		}
	}

	/**
	 * This method slects dose value on test env for epoprostenol medication
	 * under PAH
	 */
	public void selectDose_epoprostenolVeletri(String dose) {
		if (!dose.equalsIgnoreCase(" ")) {
			selectProvidedTextFromDropDown(element("select_testDose"), dose);
			logMessage("User selects " + dose + " under Dose drop down for epoprostenol medication");
		}
	}

	/**
	 * This method selects Titration checkbox of Adempas medication
	 */
	public void selectTitrationScheduleAdempas() {
		element("inp_titrationSchedule").click();
		logMessage("User clicks on Titration Schedule option");
	}

	/**
	 * This method verifies initial dose default refill value of Adempas
	 * medication
	 */
	public void verifyInitialDoseDefualtRefillValue() {
		Assert.assertEquals(element("inp_initialDoseRefill").getAttribute("value"), "1",
				"Assertion Failed: Initial dose refill value dosen't defualt to "
						+ element("inp_initialDoseRefill").getAttribute("value"));
		logMessage("Assertion Passed: Initial dose refill value defualts to "
				+ element("inp_initialDoseRefill").getAttribute("value"));
	}

	/**
	 * This method verifies titration default refill value of Adempas medication
	 */
	public void verifyTitrationDefaultRefillValue() {
		Assert.assertEquals(element("inp_strengthRefill").getAttribute("value"), "11",
				"Assertion Failed: Titration refill value dosen't defualt to "
						+ element("inp_strengthRefill").getAttribute("value"));
		logMessage("Assertion Passed: Titration refill value defualts to "
				+ element("inp_strengthRefill").getAttribute("value"));
	}

	/**
	 * This method clicks on checkboxes in Calcium channel blocker details on
	 * dosage page
	 * 
	 * @param hyperSensResp
	 * @param patietSympResp
	 * @param password
	 */
	public void clickOnResponse(String hyperSensResp, String patietSympResp, String otherResp) {
		executeJavascript("document.getElementById('doctor-nav-toggle').click();");
		clickOnHyperSensOption(hyperSensResp);
		clickOnPatientSympOption(patietSympResp);
		clickOnOthersOption(otherResp);
	}

	/**
	 * This method enters on textboxes in Calcium channel blocker details on
	 * dosage page
	 * 
	 * @param hyperSensTxt
	 * @param patietSympTxt
	 * @param otherTxt
	 */
	public void enterResponses(String hyperSensTxt, String patietSympTxt, String otherTxt) {
		enterHyperSensitivity(hyperSensTxt);
		enterPatientSymp(patietSympTxt);
		enterOthersTxt(otherTxt);
	}

	/**
	 * This method selects quantity, strength and dose values for adempas
	 * medication
	 */
	public void selectDoseQuantityTitration_Adempas() {
		medicationCount = 2;
		for (int doseCount = 0; doseCount < YamlReader
				.getYamlValues("physician.indication1.medicines.medicine" + String.valueOf(medicationCount) + ".doses")
				.size(); doseCount++) {
			List<Object> doses = new ArrayList<Object>(YamlReader
					.getYamlValues(
							"physician.indication1.medicines.medicine" + String.valueOf(medicationCount) + ".doses")
					.values());
			selectInitialDose(doses.get(doseCount).toString());
		}
	}

	/**
	 * This method selects dosage values for Remodulin IV and Remodulin SC
	 * medication
	 * 
	 * @param medicationName
	 */
	public void selectDosageValue_Remodulin(String medicationName, String fieldName) {
		if (medicationName.equalsIgnoreCase("Remodulin IV")) {
			medicationCount = 13;
		} else {
			medicationCount = 14;
		}
		for (int doseCount = 0; doseCount < YamlReader
				.getYamlValues("physician.indication1.medicines.medicine" + String.valueOf(medicationCount) + ".Dosage")
				.size(); doseCount++) {
			List<Object> doses = new ArrayList<Object>(YamlReader
					.getYamlValues(
							"physician.indication1.medicines.medicine" + String.valueOf(medicationCount) + ".Dosage")
					.values());
			selectDose(fieldName, doses.get(doseCount).toString());
		}
	}

	/**
	 * This method verifies the dosage options for Remodulin IV medication
	 */
	public void verifyDosageOptionsForRemodulinIV() {
		List<Object> dosages = new ArrayList<Object>(
				YamlReader.getYamlValues("physician.indication1.medicines.medicine13.Dosage").values());
		for (int dosageCount = 0; dosageCount < dosages.size(); dosageCount++) {
			Assert.assertTrue(
					element("label_RemodulinDosage", "Dosage", dosages.get(dosageCount).toString().trim())
							.isDisplayed(),
					"Assertion Failed: Dosage Value " + dosages.get(dosageCount).toString().trim()
							+ " is not displayed correctly");
			logMessage("Assertion Passed: Dosage Value " + dosages.get(dosageCount).toString().trim()
					+ " is displayed correctly");
		}
	}

	/**
	 * This method verifies Sig text of letairis medication
	 */
	public void verifyLetairisSigText() {
		Assert.assertEquals(element("p_sigInstruction").getText(),
				"Take 1 tablet orally " + element("inp_dosageFrequency").getAttribute("value") + ".",
				"Assertion Failed: Sig instruction is not displayed correctly");
		logMessage("Assertion Passed: Sig instruction is displayed correctly");
	}

	/**
	 * This method validates Shipment1 exists only once
	 */
	public void verifyShipment1IsDisplayedOnce(String shipmentTxt) {
		wait.hardWait(1);
		String strengthText = executeJavascriptReturnValue(
				"return document.getElementsByClassName('col-md-12')[0].innerText");
		String shipmentText[] = strengthText.split("\\r?\\n");
		Assert.assertTrue(shipmentText[1].contains(shipmentTxt),
				"Assertion Failed : " + shipmentTxt + " text exists once");
		Assert.assertTrue(!shipmentText[2].contains(shipmentTxt),
				"Assertion Failed : " + shipmentTxt + " text exists more than once");
		Assert.assertTrue(!shipmentText[3].contains(shipmentTxt),
				"Assertion Failed : " + shipmentTxt + " text exists more than once");
		logMessage("Assertion Passed : " + shipmentTxt + " text exists only once");
	}
	
	/**
	 * This method selects frequency for orenitram medication
	 * 
	 * @param frequency
	 */
	public void selectFrequency(String frequency) {
		if (!frequency.equalsIgnoreCase(" ")) {
			wait.waitForElementToBeClickable(element("select_testFrqncy"));
			selectProvidedTextFromDropDown(element("select_testFrqncy"), frequency);
			logMessage("User selected Frequency for Orenitram");
		}
	}
}
