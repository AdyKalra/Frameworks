/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapprx.testing.end2endtests.automation.utils;

import static com.zapprx.testing.end2endtests.automation.utils.ConfigPropertyReader.getProperty;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

import org.testng.Reporter;
import org.yaml.snakeyaml.Yaml;

@SuppressWarnings("unchecked")
public class YamlReader {
	public static String yamlFilePath = null;
	static String tier = System.getProperty("env");
	static String filePath = "src/com/zapprx/testing/end2endtests/main/resources/testdata/";
	static String commonFilePath = filePath + "Common_TestData.yml";
	static String pahFilePath = filePath + "PAH_TestData.yml";
	static String msFilePath = filePath + "MS_TestData.yml";
	static String raFilePath = filePath + "RA_TestData.yml";
	static String ipfFilePath = filePath + "IPF_TestData.yml";
	static Map<String, Object> propCache = null;

	public static String setYamlFilePath() {
		if (tier == null || tier.isEmpty() || tier.equals(""))
			tier = getProperty("Config.properties", "tier").trim();

		System.out.println("Tier ::" + tier);

		if (tier.equalsIgnoreCase("local")) {
			yamlFilePath = filePath + "LocalEnv_TestData.yml";
		} else if (tier.equalsIgnoreCase("test")) {
			yamlFilePath = filePath + "TestEnv_TestData.yml";
		} else {
			Reporter.log("YOU HAVE PROVIDED WRONG TIER IN CONFIG!!! using dev test data", true);
		}

		System.out.println("yaml file path ::" + yamlFilePath);
		try {
			loadYaml(commonFilePath);
			loadYaml(pahFilePath);
			loadYaml(raFilePath);
			loadYaml(msFilePath);
			loadYaml(ipfFilePath);
		} catch (FileNotFoundException e) {
			Reporter.log("Defaults not found.", true);
		}

		try {
			loadYaml(yamlFilePath);
		} catch (FileNotFoundException e) {
			Reporter.log("Wrong Tier.", true);
			System.exit(0);
		}

		String customTestData = System.getProperty("customTestData");
		if (customTestData != null) {
			try {
				loadYaml(customTestData);
			} catch (FileNotFoundException e) {
				Reporter.log("Override specified but missing.", true);
				System.exit(0);
			}
		}

		return yamlFilePath;
	}

	public static String getYamlValue(String token) {
		return getValue(token);
	}

	public static Map<String, Object> getYamlValues(String token) {
		return parseMap(propCache, token + ".");
	}

	private static String getValue(String token) {
		return getMapValue(propCache, token);
	}

	public static String getMapValue(Map<String, Object> object, String token) {
		// TODO: check for proper yaml token string based on presence of '.'
		String[] st = token.split("\\.");
		return parseMap(object, token).get(st[st.length - 1]).toString();
	}

	private static Map<String, Object> parseMap(Map<String, Object> object, String token) {
		if (token.contains(".")) {
			String[] st = token.split("\\.");
			object = parseMap((Map<String, Object>) object.get(st[0]), token.replace(st[0] + ".", ""));
		}
		return object;
	}

	private static void loadYaml(String filePath) throws FileNotFoundException {
		Reader doc = new FileReader(filePath);
		Yaml yaml = new Yaml();
		Map<String, Object> yamlMap = (Map<String, Object>) yaml.load(doc);
		if (propCache == null) {
			propCache = yamlMap;
		} else {
			mergeMaps(propCache, yamlMap);
		}
	}

	private static void mergeMaps(Map<String, Object> target, Map<String, Object> src) {
		for (String k : src.keySet()) {
			if (target.containsKey(k)) {
				boolean targetIsMap = target.get(k) instanceof Map;
				boolean srcIsMap = src.get(k) instanceof Map;
				if (srcIsMap && targetIsMap) {
					mergeMaps((Map<String, Object>) target.get(k), (Map<String, Object>) src.get(k));
				} else {
					target.put(k, src.get(k));
				}
			} else {
				target.put(k, src.get(k));
			}
		}
	}

	public static int generateRandomNumber(int MinRange, int MaxRange) {
		int randomNumber = MinRange + (int) (Math.random() * ((MaxRange - MinRange) + 1));
		return randomNumber;
	}
}
