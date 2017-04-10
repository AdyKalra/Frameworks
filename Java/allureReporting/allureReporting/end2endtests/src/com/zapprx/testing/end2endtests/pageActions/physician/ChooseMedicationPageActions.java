package com.zapprx.testing.end2endtests.pageActions.physician;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class ChooseMedicationPageActions extends GetPage {
	WebDriver driver;
	private boolean flag;
	private ArrayList<String> medications;
	private ArrayList<String> medicationList_PAH, aetnaMedicationList_PAH, excellusMedicationList_PAH,
			harvardMedicationList_PAH, healthPlanMedicationList_PAH, univeraMedicationList_PAH,
			massHealthMedicationList_MS, medQuesMedicinesList_MS;

	public ChooseMedicationPageActions(WebDriver driver) {
		super(driver, "physician/ChooseMedicationPage");
		this.driver = driver;
	}

	/**
	 * This method selects Medication
	 * 
	 * @param medicationName
	 */
	public boolean selectMedication(String medicationName) {
		medications = new ArrayList<String>(Arrays.asList("Adempas", "Letairis", "Opsumit", "Tracleer"));
		if (medications.contains(medicationName)) {
			flag = true;
		} else {
			flag = false;
		}
		wait.waitForStableDom(250);
		clickOnMedication(medicationName);
		wait.resetImplicitTimeout(5);
		try {
			wait.waitForElementToDisappear(element("span_medicationName", medicationName));
		} catch (StaleElementReferenceException e) {
			wait.waitForElementToDisappear(element("span_medicationName", medicationName));
		}
		wait.resetImplicitTimeout(wait.timeout);
		return flag;
	}

	/**
	 * This method clicks on medication name
	 * 
	 * @param medicationName
	 */
	private void clickOnMedication(String medicationName) {
		@SuppressWarnings("unchecked")
		List<WebElement> medicationNames = (List<WebElement>) ((JavascriptExecutor) driver)
				.executeScript(("return document.getElementsByClassName('ng-binding')"));
		isElementDisplayed("span_medicationName", medicationName);
		wait.waitForElementToBeClickable(element("span_medicationName", medicationName));
		for (WebElement webElement : medicationNames) {
			if (webElement.getText().equals(medicationName)) {
				if (medicationName.equalsIgnoreCase("sildenafil")) {
					scrollDown(element("span_medicationName", medicationName));
				}
				if (medicationName.equalsIgnoreCase("Rebif")) {
					scrollDown(element("span_medicationName", medicationName));
				}
				isElementDisplayed("span_medicationName", medicationName);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
				break;
			}
		}
	}

	/**
	 * This method selects medication for patient consent
	 * 
	 * @param medication
	 */
	public boolean selectMedicationForPatientConsent(String medicationName) {
		medications = new ArrayList<String>(Arrays.asList("Adempas", "Letairis", "Opsumit", "Tracleer"));
		if (medications.contains(medicationName)) {
			flag = true;
		} else {
			flag = false;
		}
		isElementDisplayed("patConsent_medication");
		element("patConsent_medication", medicationName).click();
		logMessage("User clicks on " + medicationName + " Medication");
		return flag;
	}

	/**
	 * This method return the medications under specific condition
	 * 
	 * @param chooseMedication
	 * @return
	 */
	public Object[][] getAllMedicines(String chooseMedication) {
		if (chooseMedication.equalsIgnoreCase("all")) {
			List<WebElement> medicationList = elements("list_medication");
			Object[][] medicineNames = new Object[medicationList.size()][1];
			for (int i = 0; i < medicationList.size(); i++) {
				medicineNames[i][0] = medicationList.get(i).getText().split("\n")[0];
			}
			return medicineNames;

		} else {
			Object[][] medicineNames = new Object[1][1];
			medicineNames[0][0] = chooseMedication;
			return medicineNames;
		}
	}

	/**
	 * This method stores all the medicines of Aetna Insurance for PAH
	 * indication
	 * 
	 * @return
	 */
	public Object[][] getAllMedicines() {
		aetnaMedicationList_PAH = new ArrayList<String>(Arrays.asList("Adcirca", "Adempas", "Letairis", "Opsumit",
				"Orenitram", "Revatio", "Tracleer", "Uptravi", "sildenafil", "Tyvaso", "Ventavis", "Flolan",
				"Remodulin IV", "Remodulin SC", "Veletri", "epoprostenol"));
		Object[][] medicineNames = new Object[aetnaMedicationList_PAH.size()][1];
		for (int i = 0; i < aetnaMedicationList_PAH.size(); i++) {
			medicineNames[i][0] = aetnaMedicationList_PAH.get(i);
		}
		return medicineNames;
	}

	/**
	 * This method provides the list of all the medications under PAH indication
	 * for which the forms will be generated
	 * 
	 * @return
	 */

	public Object[][] getAllMedicines_PAHFormsTest() {
		// medicationList_PAH = new ArrayList<String>(Arrays.asList("Tyvaso",
		// "Remodulin IV", "Remodulin SC", "Adcirca",
		// "Adempas", "Letairis", "Opsumit", "Orenitram", "Revatio", "Tracleer",
		// "Ventavis", "Flolan", "Veletri",
		// "epoprostenol", "Uptravi", "sildenafil"));
		medicationList_PAH = new ArrayList<String>(Arrays.asList("Remodulin IV"));
		Object[][] medicineNames = new Object[medicationList_PAH.size()][1];
		for (int i = 0; i < medicationList_PAH.size(); i++) {
			medicineNames[i][0] = medicationList_PAH.get(i);
		}
		return medicineNames;
	}

	/**
	 * This method clicks on Skip this Step
	 */
	public void clickOnSkipStep() {
		wait.waitForLoaderToDisappear();
		wait.resetImplicitTimeout(1);
		try {
			element("link_skipStep").click();
			logMessage("User clicks on Skip this Step");
		} catch (AssertionError e) {
			logMessage("Skip this Step is not displayed");
		}
		wait.resetImplicitTimeout(wait.getTimeout());
	}

	/**
	 * This method stores all the medicines
	 */
	public Object[][] getMedicines(String... medications) {
		Object[][] medicineNames = new Object[medications.length][1];
		for (int i = 0; i < medications.length; i++) {
			medicineNames[i][0] = medications[i];
		}
		return medicineNames;
	}

	/**
	 * This method stores all the medicines of Excellus Insurance for PAH
	 * indication
	 */
	public Object[][] getMSMedicinesForMedQuestions() {
		medQuesMedicinesList_MS = new ArrayList<String>(Arrays.asList("Copaxone", "Plegridy", "Betaseron", "Extavia",
				"Avonex", "Rebif", "Tysabri", "Gilenya", "Tecfidera"));
		Object[][] medicineNames = new Object[medQuesMedicinesList_MS.size()][1];
		for (int i = 0; i < medQuesMedicinesList_MS.size(); i++) {
			medicineNames[i][0] = medQuesMedicinesList_MS.get(i);
		}
		return medicineNames;
	}

	/**
	 * This method stores all the medicines of Excellus Insurance for PAH
	 * indication
	 */
	public Object[][] getMedicinesForExcellus() {
		excellusMedicationList_PAH = new ArrayList<String>(
				Arrays.asList("Adcirca", "Adempas", "Letairis", "Opsumit", "Revatio", "Tracleer", "Tyvaso", "Uptravi"));
		Object[][] medicineNames = new Object[excellusMedicationList_PAH.size()][1];
		for (int i = 0; i < excellusMedicationList_PAH.size(); i++) {
			medicineNames[i][0] = excellusMedicationList_PAH.get(i);
		}
		return medicineNames;
	}

	/**
	 * This method stores all the medicines of Harvard Pilgrim Insurance for PAH
	 * indication
	 */
	public Object[][] getMedicinesForHarvardPilgrim() {
		harvardMedicationList_PAH = new ArrayList<String>(Arrays.asList("Adcirca", "Revatio", "sildenafil"));
		Object[][] medicineNames = new Object[harvardMedicationList_PAH.size()][1];
		for (int i = 0; i < harvardMedicationList_PAH.size(); i++) {
			medicineNames[i][0] = harvardMedicationList_PAH.get(i);
		}
		return medicineNames;

	}

	/**
	 * This method stores all the medicines of Neighborhood Health Plan
	 * Insurance for PAH indication
	 */
	public Object[][] getMedicinesForNHealthPlan() {
		healthPlanMedicationList_PAH = new ArrayList<String>(Arrays.asList("Adcirca", "Adempas", "Letairis", "Opsumit",
				"Orenitram", "Revatio", "Tracleer", "Tyvaso", "Ventavis"));
		Object[][] medicineNames = new Object[healthPlanMedicationList_PAH.size()][1];
		for (int i = 0; i < healthPlanMedicationList_PAH.size(); i++) {
			medicineNames[i][0] = healthPlanMedicationList_PAH.get(i);
		}
		return medicineNames;

	}

	/**
	 * This method stores all the medicines of Univera Insurance for PAH
	 * indication
	 */
	public Object[][] getMedicinesForUnivera() {
		univeraMedicationList_PAH = new ArrayList<String>(
				Arrays.asList("epoprostenol", "Flolan", "Veletri", "Ventavis", "Remodulin IV", "Remodulin SC"));
		Object[][] medicineNames = new Object[univeraMedicationList_PAH.size()][1];
		for (int i = 0; i < univeraMedicationList_PAH.size(); i++) {
			medicineNames[i][0] = univeraMedicationList_PAH.get(i);
		}
		return medicineNames;

	}

	/**
	 * This method stores all the medicines of MassHealth Insurance for MS
	 * indication
	 */
	public Object[][] getMedicinesForMassHealth_MS() {
		massHealthMedicationList_MS = new ArrayList<String>(
				Arrays.asList("Ampyra", "Aubagio", "Tecfidera", "Lemtrada", "Gilenya"));
		Object[][] medicineNames = new Object[massHealthMedicationList_MS.size()][1];
		for (int i = 0; i < massHealthMedicationList_MS.size(); i++) {
			medicineNames[i][0] = massHealthMedicationList_MS.get(i);
		}
		return medicineNames;
	}

	public void verifyOrderOfDrugs(String... drug) {
		List<WebElement> drugs = elements("list_medType");
		for (int drugCount = 0; drugCount < drugs.size(); drugCount++) {
			Assert.assertEquals(drugs.get(drugCount).getText(), drug[drugCount],
					"Assertion Failed: Order of drugs is not displayed incorrectly");
			logMessage("Assertion Passed: Order of drugs is displayed incorrectly");
		}
	}

	/**
	 * This method verifies Patient name on Rx summary header
	 * 
	 * @param lastName
	 */
	public void verifyPatientNameOnRxSummaryHeader(String lastName) {
		Assert.assertTrue(
				element("h4_rxSummaryHeader").getText().contains(getYamlValue("physician.firstName") + " " + lastName),
				"Assertion Failed: Patient name is not present under Prescribing Summary Header");
		logMessage("Assertion Passed: Patient name is present under Prescribing Summary Header");
	}
}
