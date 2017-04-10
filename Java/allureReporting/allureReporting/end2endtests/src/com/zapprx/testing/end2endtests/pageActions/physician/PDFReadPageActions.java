package com.zapprx.testing.end2endtests.pageActions.physician;

import java.io.File;

import org.openqa.selenium.WebDriver;

import com.zapprx.testing.end2endtests.automation.pageUtils.GetPage;
import com.zapprx.testing.end2endtests.automation.utils.ReadFromPDF;

public class PDFReadPageActions extends GetPage {
	WebDriver driver;
	static String path = "src/com/zapprx/testing/end2endtests/main/resources/testdata/pdf/";
	static String goodPDFPath = "src/com/zapprx/testing/end2endtests/main/resources/testdata/goodPDF/";

	public PDFReadPageActions(WebDriver driver) {
		super(driver, "physician/PDFReadPage");
		this.driver = driver;
	}

	public void comparePDF(String goodPDFName, String testPDFName) {
		ReadFromPDF.comparePDFFiles(goodPDFName, testPDFName);
	}

	public String getStoredPDF(String dirPath, String formName, String medicineName) {
		return ReadFromPDF.moveFileFromTemp(dirPath, formName, medicineName);
	}

	public void createDirectory() {
		ReadFromPDF.createNewDir("goodPDF");
		ReadFromPDF.createNewDir("pdf");
	}

	public boolean deleteDirectory(File dir) {
		if (dir.isDirectory() && dir.exists()) {
			File[] children = dir.listFiles();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirectory(children[i]);
				if (!success) {
					return false;
				}
			}
		}
		System.out.println("removing file or directory : " + dir.getName());
		return dir.delete();

	}

	public void deleteFiles() {
		ReadFromPDF.deleteFile(System.getProperty("java.io.tmpdir"));
		deleteDirectory(new File(path));
	}

	public void deleteFiles(String fileName) {
		ReadFromPDF.deleteFile(fileName);
	}

	public void getValueFromPDF(String medicationName, String pharmacyName, int count) {
		ReadFromPDF.readFromPDFAndParseJSON(medicationName, pharmacyName, count);
	}

}
