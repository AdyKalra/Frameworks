package com.zapprx.testing.end2endtests.pageActions.patient;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;

public class DocumentsPageActions extends GetPage {
	WebDriver driver;

	public DocumentsPageActions(WebDriver driver) {
		super(driver, "patient/DocumentsPage");
		this.driver = driver;
	}

	/**
	 * This method enter the signature and clicks on submit button
	 * 
	 * @param signature
	 */
	public void enterSignatureAndSubmit() {
		isElementDisplayed("span_draw");
		executeJavascript("document.getElementsByClassName('drawIt')[0].getElementsByTagName('a')[0].click();");
		logMessage("Patient enter signature to share authorization");
		wait.waitForElementToBeClickable(element("canvas_drwSig"));
		drawSignatureOnCanvas(element("canvas_drwSig"));
		wait.waitForLoaderToDisappear();
		element("btn_submit").click();
		logMessage("Patient clicks on Submit button");
	}

	/**
	 * This method verify the message displayed after submission of signature
	 * 
	 * @param submitMsg
	 */
	public void verifySignIsSubmitted(String submitMsg) {
		Assert.assertEquals(element("hdr_signMsg").getText(), submitMsg,
				"Assertion Failed: Signature submission message " + submitMsg + " is not displayed");
		logMessage("Assertion Passed: Signature submission message " + submitMsg + " is displayed");
	}
}
