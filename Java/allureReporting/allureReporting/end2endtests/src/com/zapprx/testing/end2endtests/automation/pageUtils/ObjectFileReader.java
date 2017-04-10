package com.zapprx.testing.end2endtests.automation.pageUtils;

import static com.zapprx.testing.end2endtests.automation.utils.ConfigPropertyReader.getProperty;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class reads the PageObjectRepository text files. Uses buffer reader.
 *
 * @author prashantshukla
 *
 */
public class ObjectFileReader {
	static String tier;
	static String filepath = "src/com/zapprx/testing/end2endtests/main/resources/PageObjects/";

	public static String[] getElementFromFile(String pageName, String elementName) {
		try {
			FileReader specFile = new FileReader(filepath + pageName + ".spec");
			return getElement(specFile, elementName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String[] getElement(FileReader specFile, String elementName) throws Exception {
		ArrayList<String> elementLines = getSpecSection(specFile);
		for (String elementLine : elementLines) {
			if (elementLine.startsWith(elementName)) {
				return elementLine.split(" ", 3);
			}
		}
		throw new Exception();
	}

	private static ArrayList<String> getSpecSection(FileReader specfile) {
		String readBuff = null;
		ArrayList<String> elementLines = new ArrayList<String>();

		try {
			BufferedReader buff = new BufferedReader(specfile);
			try {
				boolean flag = false;
				readBuff = buff.readLine();
				while ((readBuff = buff.readLine()) != null) {
					if (readBuff.startsWith("========")) {
						flag = !flag;
					}
					if (flag) {
						elementLines.add(readBuff.trim().replaceAll("[ \t]+", " "));
					}
					if (!elementLines.isEmpty() && !flag) {
						break;
					}
				}
			} finally {
				buff.close();
				if (elementLines.get(0).startsWith("========")) {
					elementLines.remove(0);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Spec File not found at location :- " + filepath);
		} catch (IOException e) {
			System.out.println("exceptional case");
		}
		return elementLines;
	}

	private static void setTier() {
		switch (Tiers.valueOf(getProperty("Config.properties", "tier"))) {
		case local:
		case LOCAL:
		case Local:
			tier = "Local/";
			break;

		case test:
		case TEST:
		case Test:
			tier = "Test/";
			break;
		}
	}
}
