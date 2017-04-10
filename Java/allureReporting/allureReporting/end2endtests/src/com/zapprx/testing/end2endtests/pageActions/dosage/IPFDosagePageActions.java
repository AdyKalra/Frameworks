package com.zapprx.testing.end2endtests.pageActions.dosage;

import org.openqa.selenium.WebDriver;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class IPFDosagePageActions extends GetPage {
	WebDriver driver;

	public IPFDosagePageActions(WebDriver driver) {
		super(driver, "physician/DosagePage");
		this.driver = driver;
	}

	/**
	 * This method enters the updated value for Dosage Quantity for Esbriet
	 * medication
	 * 
	 * @param quantityValue
	 */
	public void editEsbrietDosageQuantity(String quantityValue) {
		enterText(element("inp_esbrietDosageQuantity"), quantityValue);
		logMessage("User edit Dosage Quantity value and enters " + quantityValue);
	}
	
	/**
	 * This method selects the default option for Initial Titration Order
	 */
	public void selectInitialTitrationOrder() {
		element("inp_defIniTitOrder").click();
		logMessage("User clicks on '207 capsules, 30 day supply' option Initial Titration Order");
	}

	/**
	 * This method selects quantity for esbriet medication
	 * 
	 * @param quantity
	 */
	public void editEsbrietQuantity(String quantity) {
		enterText(element("inp_quantity"), quantity);
		logMessage("User enetrs quantity for esbriet medicatio");
	}

	/**
	 * This method selects esbriet start now option
	 */
	public void selectEsbrietStartNow() {
		clickUsingXpathInJavaScriptExecutor(element("inp_esbrietStartNow"));
		logMessage("User selects option for esbriert start now");
	}
}
