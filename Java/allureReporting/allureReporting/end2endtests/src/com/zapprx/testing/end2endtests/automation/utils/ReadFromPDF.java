package com.zapprx.testing.end2endtests.automation.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import com.zapprx.comparatorator.Comparatorator;
import com.zapprx.comparatorator.ComparisonResult;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

/**
 * Created by fatemakapoor on 2/4/16.
 */

/**
 * Reads a pdf file and then outputs the name value to a file
 *
 */
public class ReadFromPDF {

	private static final int BUFFER_SIZE = 4096;
	static String path = "src/com/zapprx/testing/end2endtests/main/resources/testdata/downloadPDF/";
	static String pathForPDFToJSON = "src/com/zapprx/testing/end2endtests/main/resources/testdata/json/";
	static String pdfText = "", title;

	public static void readFromPDFAndParseJSON(String medicationName, String pharmacyName, int count) {
		waitForFileToBeDownloaded();
		String fileName = getNameForDownloadedFile();
		deleteFile(pathForPDFToJSON + medicationName + "_" + pharmacyName + "_" + "form" + count + ".json");
		File file = new File(path + fileName);
		File outputFile = new File(
				pathForPDFToJSON + medicationName + "_" + pharmacyName + "_" + "form" + count + ".json");
		FileWriter fw = null;
		try {
			fw = new FileWriter(outputFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			PDDocument pdDoc = null;
			pdDoc = PDDocument.load(file);
			PDDocumentCatalog pdCatalog = pdDoc.getDocumentCatalog();
			PDAcroForm pdAcroForm = pdCatalog.getAcroForm();
			JSONObject obj = new JSONObject();
			for (PDField pdField : pdAcroForm.getFields()) {
				obj.put(pdField.getPartialName(), pdField.getValueAsString());
			}
			bw.write(obj.toJSONString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method returns downloaded file
	 * 
	 * @return
	 */
	public static File getDownloadedFile() {
		waitForFileToBeDownloaded();
		String fileName = getNameForDownloadedFile();
		File file = new File(path + fileName);
		file.renameTo(new File(file + ".pdf"));
		return file;
	}

	/**
	 * This method returns PDF file title
	 * 
	 * @param file
	 * @return
	 */
	public static String getPdfFileTitle(File file) {
		try {
			PdfReader reader = new PdfReader(file + ".pdf");
			HashMap<String, String> map = reader.getInfo();
			title = map.get("Title");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return title;
	}

	/**
	 * This method returns text from PDF file
	 * 
	 * @param file
	 * @return
	 */
	public static String getTextFromPdfFile(File file) {
		try {
			PdfReader reader = new PdfReader(file + ".pdf");
			for (int pageCount = 1; pageCount <= reader.getNumberOfPages(); pageCount++) {
				pdfText = pdfText.concat(PdfTextExtractor.getTextFromPage(reader, pageCount));
				pdfText = pdfText.concat("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pdfText;
	}

	/**
	 * This method checks if file is of PDF type or not
	 * 
	 * @param file
	 * @return
	 */
	public static boolean checkIfFileIsPDFType(File file) {
		try {
			Scanner input = new Scanner(new FileReader(new File(file + ".pdf")));
			while (input.hasNextLine()) {
				final String checkline = input.nextLine();
				if (checkline.contains("%PDF-")) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getNameForDownloadedFile() {
		File file = new File(path);
		File[] files = file.listFiles();
		if (files.length > 1) {
			Assert.fail("More Than one file is downloaded");
		} else {
			for (File f : files) {
				return f.getName();
			}
		}
		return null;
	}

	public static void waitForFileToBeDownloaded() {
		int i = 0;
		File file = new File(path);
		File[] files = file.listFiles();
		while (!(files.length > 0) && i < 30) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				files = file.listFiles();
				i++;
			}
		}
	}

	public static void createNewDir(String folderName) {
		File dir = new File("src/com/zapprx/testing/end2endtests/main/resources/testdata/" + folderName);
		System.out.println("dir= " + dir);
		if (!dir.exists()) {
			System.out.println("Befor mkdir= " + dir.mkdir());
			dir.mkdir();
			System.out.println("After mkdir= " + dir.mkdir());
		}
	}

	public static void comparePDFFiles(String goodPDFfileName, String testPDF) {
		ComparisonResult result = Comparatorator.compareTestDocuments(new File(goodPDFfileName), new File(testPDF));
		if (result.succeded()) {
			Reporter.log(result.getMessage(), true);
		} else if (result.error()) {
			Reporter.log(result.getMessage(), true);
		} else {
			Reporter.log(result.getMessage(), true);
		}
	}

	/**
	 * This method deletes the files from specific directory
	 * 
	 * @param dir
	 */
	public static void deleteFile(String dir) {
		File folder = new File(dir);
		for (File file : folder.listFiles()) {
			file.delete();
		}
	}

	/**
	 * This method moves the pdf file to a specific directory generated in temp
	 * 
	 * @return
	 */
	public static String moveFileFromTemp(String dirPath, String formName, String medicineName) {
		String tempDir = System.getProperty("java.io.tmpdir");
		File folder = new File(tempDir);
		for (File file : folder.listFiles()) {
			if (file.getName().endsWith(".pdf")) {
				Reporter.log("Name of File=" + file.getName(), true);
				File source = new File(tempDir + "/" + file.getName());
				File dest = new File(dirPath + file.getName());
				source.renameTo(dest);
				Reporter.log("File is moved successful!", true);
				createNewDir("/pdf/" + medicineName);
				Assert.assertTrue(dest.exists(), "Assertion Failed: File is not found in directory");
				if (dest.exists()) {
					dest.renameTo(new File(dirPath + medicineName + "/" + formName + ".pdf"));
					Reporter.log("File is renamed successful!", true);
				}
			}
		}
		return medicineName + "/" + formName + ".pdf";
	}
}
