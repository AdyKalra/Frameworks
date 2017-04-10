package com.zapprx.testing.end2endtests.pageActions.dosage;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;

import com.zapprx.testing.end2endtests.automation.TestSessionInitiator;
import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;
import com.zapprx.testing.end2endtests.automation.utils.YamlReader;

public class DosageCommonWorkflow extends GetPage {
	TestSessionInitiator test;
	private int medicationCount = 1, indicationCount;
	String[] medicineDetails;
	String[] quantity, strength, doses, dosage, frequency, dose, diluent, pump, doseStrength, ampuleTablet;
	String med, selectedQuantity, selectedStrength, selectedDosage, refillValue;

	public DosageCommonWorkflow(TestSessionInitiator test, WebDriver driver) {
		super(driver, "");
		this.test = test;
	}

	/**
	 * This method returns indication count for the given medication
	 * 
	 * @param medicineName
	 * @return
	 */
	public int getIndicationCount(String medicineName) {
		Map<String, Object> indication = YamlReader.getYamlValues("physician");
		for (Map.Entry<String, Object> m : indication.entrySet()) {
			if (m.getValue().toString().contains(medicineName)) {
				med = m.getKey();
			}
		}
		return Integer.parseInt(med.substring(Math.max(0, med.length() - 1)));
	}

	/**
	 * This method selects dosage options while prescribing based on the
	 * medication value
	 * 
	 * @param medicineName
	 */

	public void selectDosageOptions(String medicineName) {
		indicationCount = getIndicationCount(medicineName);
		Map<String, Object> map = YamlReader
				.getYamlValues("physician.indication" + String.valueOf(indicationCount) + ".medicines");
		for (Map.Entry<String, Object> m : map.entrySet()) {
			if (m.getValue().toString().contains(medicineName)) {
				med = m.getKey();
			}
		}
		if (med.length() == 10)
			medicationCount = Integer.parseInt(med.substring(Math.max(0, med.length() - 2)));
		else
			medicationCount = Integer.parseInt(med.substring(Math.max(0, med.length() - 1)));
		Map<String, Object> medDetails = YamlReader.getYamlValues("physician.indication"
				+ String.valueOf(indicationCount) + ".medicines.medicine" + String.valueOf(medicationCount));
		ArrayList<String> medDosageOptions = new ArrayList<String>(
				Arrays.asList("Quantity", "Dosage", "Strengths", "Frequency", "Directions", "Initial dose",
						"starterPack", "Dose", "Titration Pack", "Maintenance Dosage", "Directions (Initial Dose)",
						"Directions (Maintenance Dose)", "Maintenance Rx", "Dose/Strength", "Starter Pack Rx"));
		for (Map.Entry<String, Object> m : medDetails.entrySet()) {
			String key = m.getKey().toString();
			if (medDosageOptions.contains(key)) {
				selectOptionsOnDosagePage(key, medicineName);
			}
		}
	}

	public void selectDosageOptions_PAH(String medication) {
		ArrayList<String> medList = new ArrayList<String>(Arrays.asList("Lemtrada", "Ampyra", "Gilenya", "Aubagio"));
		if (medList.contains(medication)) {
			test.commonDosagePage.selectQuantity(getYamlValue("physician.Quantity.quantity4"));
		} else if (medication.equalsIgnoreCase("Adcirca") || medication.equalsIgnoreCase("sildenafil")
				|| medication.equalsIgnoreCase("Revatio")) {
			test.commonDosagePage.selectQuantity(getYamlValue("physician.Quantity.quantity2"));
		} else if (medication.equalsIgnoreCase("Adempas")) {
			test.PAHDosagePage.selectDoseQuantityTitration_Adempas();
		} else if (medication.equalsIgnoreCase("Orenitram")) {
			selectDosageValue_Orenitram(medication);
		} else if (medication.equalsIgnoreCase("Letairis") || medication.equalsIgnoreCase("Tyvaso")) {
			selectDosageValue_Letairis_Tyvaso(medication);
		} else if (medication.equalsIgnoreCase("Remodulin IV") || medication.equalsIgnoreCase("Remodulin SC")) {
			test.PAHDosagePage.selectDosageValue_Remodulin(medication, "Dosage");
		} else if (medication.equalsIgnoreCase("Flolan") || medication.equalsIgnoreCase("epoprostenol")
				|| medication.equalsIgnoreCase("Veletri")) {
			selectDiluentAndPump_Flolan_epoprostenol_Veletri(medication);
		}
	}

	/**
	 * This method selects options on dosage page as per given parameters
	 * 
	 * @param key
	 * @param medicineName
	 */
	public void selectOptionsOnDosagePage(String key, String medicineName) {
		ArrayList<String> medDosageOptions = new ArrayList<String>(Arrays.asList("Quantity", "Frequency", "starterPack",
				"Titration Pack", "Maintenance Dosage", "Dose/Strength", "Dose"));
		String keyValue = getYamlValue("physician.indication" + String.valueOf(indicationCount) + ".medicines.medicine"
				+ String.valueOf(medicationCount) + "." + key + "." + key + "1");
		if (medDosageOptions.contains(key)) {
			selectDiffrentDosageOption(keyValue, key, medicineName);
		} else if (medicineName.equalsIgnoreCase("Adempas") && key.equalsIgnoreCase("Strengths"))
			test.PAHDosagePage.selectDoseQuantityTitration_Adempas();
		else {
			if (medicineName.equalsIgnoreCase("Remicade"))
				test.RADosagePage.enterMaintainDosage(getYamlValue("physician.maintainDosage"));
			clickOnDifferentDosageOptions(medicineName, keyValue, key);
		}
	}

	/**
	 * This method clicks on different direction options as per keyValue
	 * 
	 * @param medicineName
	 * @param keyValue
	 */
	public void clickOnDifferentDosageOptions(String medicineName, String dosageValue, String fieldName) {
		test.commonDosagePage.clickOnDosageOptions(dosageValue, fieldName);
		if (dosageValue.equalsIgnoreCase("Other")) {
			if (medicineName.equalsIgnoreCase("Actemra IV"))
				test.RADosagePage.enterOtherInstructions(getYamlValue("physician.otherInstruction"));
			else
				test.RADosagePage.enterSigOrInst(getYamlValue("physician.otherInstruction"));
		}
	}

	/**
	 * This method selects different dosage options as per keyValue
	 * 
	 * @param keyValue
	 * @param keyName
	 */
	public void selectDiffrentDosageOption(String keyValue, String keyName, String medicineName) {
		wait.waitForStableDom(250);
		if (medicineName.equalsIgnoreCase("Adempas")) {
			test.PAHDosagePage.selectTitrationScheduleAdempas();
			test.commonDosagePage.selectQuantity(keyValue);
		} else
			test.commonDosagePage.selectDosageOptions(keyValue, keyName);
		if (keyValue.equalsIgnoreCase("Other"))
			test.commonDosagePage.enterDaysSupply(getYamlValue("physician.daysSupply"));
	}

	/**
	 * This method selects all the dosage values for letairis medication
	 */
	public void selectDosageValue_Letairis_Tyvaso(String medicationName) {
		if (medicationName.equalsIgnoreCase("Letairis")) {
			medicationCount = 3;
		} else {
			medicationCount = 10;
		}
		for (int doseCount = 0; doseCount < YamlReader
				.getYamlValues("physician.indication1.medicines.medicine" + String.valueOf(medicationCount) + ".Dosage")
				.size(); doseCount++) {
			List<Object> doses = new ArrayList<Object>(YamlReader
					.getYamlValues(
							"physician.indication1.medicines.medicine" + String.valueOf(medicationCount) + ".Dosage")
					.values());
			test.commonDosagePage.selectDosage(doses.get(doseCount).toString());
		}
	}

	/**
	 * This method selects dosage values for Orenitram medication
	 * 
	 * @param medicationName
	 * @param fieldName
	 */
	public void selectDosageValue_Orenitram(String medicationName) {
		medicationCount = 5;
		test.PAHDosagePage
				.selectFrequency(getYamlValue("physician.indication1.medicines.medicine5.Frequency.Frequency1"));
		for (int doseCount = 0; doseCount < YamlReader
				.getYamlValues(
						"physician.indication1.medicines.medicine" + String.valueOf(medicationCount) + ".Strength")
				.size(); doseCount++) {
			List<Object> doses = new ArrayList<Object>(YamlReader
					.getYamlValues(
							"physician.indication1.medicines.medicine" + String.valueOf(medicationCount) + ".Strength")
					.values());
			test.PAHDosagePage.selectStrengthTitration(doses.get(doseCount).toString());
		}
	}

	/**
	 * This method selects diluent and pump values for flolan, epoprostenol and
	 * veletri medication
	 * 
	 * @param medication
	 */
	public void selectDiluentAndPump_Flolan_epoprostenol_Veletri(String medication) {
		if (medication.equalsIgnoreCase("Flolan")) {
			medicationCount = 12;
		} else if (medication.equalsIgnoreCase("epoprostenol")) {
			medicationCount = 16;
			test.commonDosagePage.selectQuantity(getYamlValue("physician.Quantity.quantity2"));
		} else {
			medicationCount = 15;
		}
		test.PAHDosagePage.selectDose_epoprostenolVeletri(getYamlValue(
				"physician.indication1.medicines.medicine" + String.valueOf(medicationCount) + ".Dose.Dose1"));
		for (int diluentCount = 0; diluentCount < YamlReader
				.getYamlValues(
						"physician.indication1.medicines.medicine" + String.valueOf(medicationCount) + ".Diluent")
				.size(); diluentCount++) {
			List<Object> diluents = new ArrayList<Object>(YamlReader
					.getYamlValues(
							"physician.indication1.medicines.medicine" + String.valueOf(medicationCount) + ".Diluent")
					.values());
			test.PAHDosagePage.selectDiluent(diluents.get(diluentCount).toString());
		}
		for (int pumpCount = 0; pumpCount < YamlReader
				.getYamlValues("physician.indication1.medicines.medicine" + String.valueOf(medicationCount) + ".Pump")
				.size(); pumpCount++) {
			List<Object> diluents = new ArrayList<Object>(YamlReader
					.getYamlValues(
							"physician.indication1.medicines.medicine" + String.valueOf(medicationCount) + ".Pump")
					.values());
			test.PAHDosagePage.selectPump(diluents.get(pumpCount).toString());
		}
	}

	/**
	 * This method selects dosage options for all the required medications
	 * 
	 * @param medication
	 */
	public void selectDosageAndAuthorize(String medication, String lastName, String path, boolean remsValue) {
		selectDosageOptions_PAH(medication);
		test.patientCommonWorkflow.authorizePres(lastName, remsValue, medication, getYamlValue("physician.password1"));
		test.authorizationPage.clickReviewOnConfirmationModal();
		test.presDetailsPage.clickOnSend();
		test.patientCommonWorkflow.downloadAndComparePDF(path, medication);
	}

	/********** Multiple dosage combination selection randomly **************/

	/**
	 * This method generates random integer for given range and returns dosage
	 * values for given medicineDetails
	 * 
	 * @param min
	 * @param max
	 * @return
	 */

	public String[] getRandomDosageValue(int min, int max, String[] medicineDetails) {
		int num1, num2;
		Random random = new Random();
		num1 = random.nextInt(max - min + 1) + min;
		num2 = random.nextInt(max - min + 1) + min;
		if (medicineDetails[min].equalsIgnoreCase(" "))
			return new String[] { " ", " " };
		else if (min == max)
			return new String[] { medicineDetails[min], medicineDetails[max] };
		else if (medicineDetails[min + 1].equalsIgnoreCase(" "))
			return new String[] { medicineDetails[min], medicineDetails[min] };
		else if (min + 1 == max)
			return new String[] { medicineDetails[min], medicineDetails[max] };
		else {
			while (medicineDetails[num1].equalsIgnoreCase(" ") || medicineDetails[num2].equalsIgnoreCase(" ")
					|| medicineDetails[num1].equalsIgnoreCase(medicineDetails[num2])) {
				num1 = random.nextInt(max - min + 1) + min;
				num2 = random.nextInt(max - min + 1) + min;
				if (!medicineDetails[num1].equalsIgnoreCase(" ") && !medicineDetails[num2].equalsIgnoreCase(" ")
						&& !medicineDetails[num1].equalsIgnoreCase(medicineDetails[num2])) {
					return new String[] { medicineDetails[num1], medicineDetails[num2] };
				}
			}
		}
		return new String[] { medicineDetails[min], medicineDetails[max] };
	}

	/**
	 * This method stores dosage values randomly
	 * 
	 * @param medicineDetails
	 */
	public void storeDosageValues(String[] medicineDetails) {
		doses = getRandomDosageValue(1, 2, medicineDetails);
		quantity = getRandomDosageValue(3, 5, medicineDetails);
		strength = getRandomDosageValue(6, 10, medicineDetails);
		dosage = getRandomDosageValue(11, 14, medicineDetails);
		frequency = getRandomDosageValue(15, 16, medicineDetails);
		dose = getRandomDosageValue(17, 18, medicineDetails);
		diluent = getRandomDosageValue(19, 21, medicineDetails);
		pump = getRandomDosageValue(22, 23, medicineDetails);
		doseStrength = getRandomDosageValue(24, 24, medicineDetails);
		ampuleTablet = getRandomDosageValue(25, 26, medicineDetails);
	}

	/**
	 * This method stores dosage values for given medicine
	 * 
	 * @param medicineName
	 */
	public void getDosageValuesFromCSV(String medicineName) {
		quantity = strength = doses = dosage = frequency = dose = diluent = pump = doseStrength = ampuleTablet = new String[] {
				"", "" };
		String csvFile = "src/com/zapprx/testing/end2endtests/main/resources/testdata/dosage.csv", line = "",
				cvsSplitBy = ",";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				medicineDetails = line.split(cvsSplitBy);
				if (medicineDetails[0].equalsIgnoreCase(medicineName)) {
					storeDosageValues(medicineDetails);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method selects dosage option on dosage page randomly
	 * 
	 * @param medicineName
	 * @param medicineDetails
	 */
	public String selectsDosageOptions(String medicineName, String dosage) {
		if (!dosage.equalsIgnoreCase(" ")) {
			if (medicineName.equalsIgnoreCase("Letairis")) {
				medicationCount = 3;
			} else if (medicineName.equalsIgnoreCase("Tyvaso")) {
				medicationCount = 10;
			} else
				medicationCount = 13;
			for (int doseCount = 0; doseCount < YamlReader
					.getYamlValues(
							"physician.indication1.medicines.medicine" + String.valueOf(medicationCount) + ".Dosage")
					.size(); doseCount++) {
				List<Object> doses = new ArrayList<Object>(YamlReader.getYamlValues(
						"physician.indication1.medicines.medicine" + String.valueOf(medicationCount) + ".Dosage")
						.values());
				test.commonDosagePage.uncheckCheckedDosageValues(doses.get(doseCount).toString());
			}
			test.commonDosagePage.selectDosage(dosage);
		}
		return dosage;
	}

	/**
	 * This method selects quantity option on dosage page randomly
	 * 
	 * @param medicineName
	 * @param medicineDetails
	 */
	public String selectQuantityOptions(String medicineName, String quantity) {
		if (!quantity.equalsIgnoreCase(" ")) {
			test.commonDosagePage.selectQuantity(quantity);
		} else if (!medicineName.equalsIgnoreCase("Tyvaso")) {
			quantity = test.commonDosagePage.getStaticQuantityValue(medicineName);
		}
		return quantity;
	}

	/**
	 * This method selects strength option on dosage page randomly
	 * 
	 * @param medicineName
	 * @param medicineDetails
	 */
	public String selectStrengthOptions(String medicineName, String strength) {
		if (!strength.equalsIgnoreCase(" ")) {
			if (medicineName.equalsIgnoreCase("Orenitram")) {
				medicationCount = 5;
				for (int doseCount = 0; doseCount < YamlReader.getYamlValues(
						"physician.indication1.medicines.medicine" + String.valueOf(medicationCount) + ".Strength")
						.size(); doseCount++) {
					List<Object> doses = new ArrayList<Object>(YamlReader.getYamlValues(
							"physician.indication1.medicines.medicine" + String.valueOf(medicationCount) + ".Strength")
							.values());
					test.commonDosagePage.uncheckCheckedDosageValues(doses.get(doseCount).toString());
				}
			} else if (medicineName.equalsIgnoreCase("Adempas"))
				test.PAHDosagePage.selectTitrationScheduleAdempas();
		}
		return strength;
	}

	/**
	 * This method selects dosage options for different combinations
	 * 
	 * @param count
	 */
	public void selectDosageCombinations(String medicineName, int count) {
		test.PAHDosagePage.selectInitialDose(doses[count]);
		selectedStrength = selectStrengthOptions(medicineName, strength[count]);
		selectedQuantity = selectQuantityOptions(medicineName, quantity[count]);
		selectedDosage = selectsDosageOptions(medicineName, dosage[count]);
		test.PAHDosagePage.selectFrequency(frequency[count]);
		test.PAHDosagePage.selectDose_epoprostenolVeletri(dose[count]);
		test.PAHDosagePage.selectDiluent(diluent[count]);
		test.PAHDosagePage.selectPump(pump[count]);
		if (!dosage[count].equalsIgnoreCase("Treprostinil inhalation system starter kit (6 mcg/breath)"))
			refillValue = test.commonDosagePage.getRefillValue(medicineName).concat(" refills");
	}

	public void getQuantityAndRefillValue(String medicineName) {
		if (medicineName.equalsIgnoreCase("Ventavis")) {
			selectedQuantity = test.commonDosagePage.getStaticQuantityValue(medicineDetails[0]);
			refillValue = test.commonDosagePage.getRefillValue(medicineName).concat(" refills");
		} else if (medicineName.equalsIgnoreCase("Uptravi"))
			refillValue = test.commonDosagePage.getRefillValue(medicineName).concat(" refills");
	}

	/**
	 * This method verifies dosage value on PA input page
	 * 
	 * @param count
	 * @param medicineName
	 */
	public void verifyDosageOptionOnPAPage(int count, String medicineName) {
		String daysSupply = quantity[count];
		if (quantity[count].equalsIgnoreCase("Other")) {
			daysSupply = getYamlValue("physician.daysSupply").concat("-day supply");
		} else if (quantity[count].equalsIgnoreCase(" "))
			daysSupply = selectedQuantity;
		if (medicineName.equalsIgnoreCase("Adempas")) {
			test.priorAuthorizationPage.verifyInitialDosage_Adempas(doses[count],
					getYamlValue("physician.Quantity.quantity2"), "1 refills");
			test.priorAuthorizationPage.verifyDosageOption(medicineName, "0.5 mg", "1 mg", "1.5 mg", "2 mg", "2.5 mg",
					daysSupply, refillValue, selectedDosage, dose[count]);
		} else
			test.priorAuthorizationPage.verifyDosageOption(doses[count], selectedStrength, daysSupply, refillValue,
					selectedDosage, dose[count], doseStrength[count], ampuleTablet[0], ampuleTablet[1]);
		test.priorAuthorizationPage.verifyDirectionOption(frequency[count]);
	}

	/**
	 * This method prescribes medication for different dosage combination
	 * 
	 * @param medicineName
	 * @param patientLastNameDOBAndGender
	 */
	public void precribeMedAndAuthorize(String medicineName, String[] patientLastNameDOBAndGender) {
		for (int count = 0; count < 2; count++) {
			selectedQuantity = selectedStrength = selectedDosage = refillValue = " ";
			test.leftnavigationPage.clickOnPatients();
			test.patientPage.searchPatient(patientLastNameDOBAndGender[0]);
			test.patientPage.selectPatientByName(patientLastNameDOBAndGender[0]);
			test.patientPage.clickPrescribeOnPatientModal();
			test.headerPage.verifyHeaderText("Choose an indication");
			test.indicationPage.selectIndication(getYamlValue("physician.indication1.name"));
			boolean remsValue = test.patientCommonWorkflow.prescribePatient(medicineName,
					getYamlValue("physician.indication1.diagnosis.diagnosis4"),
					getYamlValue("physician.indication1.medicines.medicine2.pharmacy.pharmacy1"),
					patientLastNameDOBAndGender[2], patientLastNameDOBAndGender[0],
					getYamlValue("physician.otherDetails"));
			ArrayList<String> medList = new ArrayList<String>(
					Arrays.asList("Tracleer", "Uptravi", "Ventavis", "Opsumit"));
			if (medList.contains(medicineName)) {
				count = count + 1;
				getQuantityAndRefillValue(medicineName);
			} else
				selectDosageCombinations(medicineName, count);
			test.patientCommonWorkflow.authorizePres(patientLastNameDOBAndGender[2], remsValue, medicineName,
					getYamlValue("physician.password1"));
			test.authorizationPage.clickReviewOnConfirmationModal();
			test.presDetailsPage.clickOnPriorAuthorization();
			test.priorAuthorizationPage.expandTabs(getYamlValue("physician.paTabs.dosage"));
			verifyDosageOptionOnPAPage(count, medicineName);
		}
	}
}
