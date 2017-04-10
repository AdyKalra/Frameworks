package com.zapprx.testing.end2endtests.automation.utils;

import org.openqa.selenium.WebDriver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CustomFunctions {
	static WebDriver driver;
	public String patientLastName, lastName, date, userName, password;
	public static String gender;
	public String medicationName;
	public String doctorFirstName;
	public String doctorEmailId;
	private static final Random random = new Random();
	private static DateFormat dateFormat;

	public CustomFunctions(WebDriver driver) {
		CustomFunctions.driver = driver;
	}

	public static String getStringWithTimestamp(String name) {
		return name + System.currentTimeMillis();
	}

	public static int getRandomIntegerValue() {
		final int integerValue = random.nextInt(62) + 18;
		return integerValue;
	}

	public static String getDate() {
		dateFormat = new SimpleDateFormat("MMddyyyy");
		final Date birthDate = new Date(
				-946771200000L + (Math.abs(random.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000)));
		final String dob = dateFormat.format(birthDate);
		return dob;
	}

	public static String getDateWithSeparator() {
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		final Date birthDate = new Date(
				-946771200000L + (Math.abs(random.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000)));
		final String dob = dateFormat.format(birthDate);
		return dob;
	}

	public static String getCurrentDateWithSep() {
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date currentDate = new Date();
		String date = dateFormat.format(currentDate);
		return date;
	}

	public static void setGender() {
		gender = random.nextBoolean() ? "Male" : "Female";
	}

	public String getGender() {
		return gender;
	}

	public void setPatientLastName() {
		patientLastName = getStringWithTimestamp("Last");
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setMedication() {
		medicationName = getStringWithTimestamp("medication");
	}

	public String getMedication() {
		return medicationName;
	}

	public void setDoctorFirstName() {
		doctorFirstName = getStringWithTimestamp("Doctor");
	}

	public String getDoctorFirstName() {
		return doctorFirstName;
	}

	public void setDoctorEmailId() {
		doctorEmailId = getDoctorFirstName()
				.concat(YamlReader.getYamlValue("physician.lastName").concat("@zapprx.com"));
	}

	public String getDoctorEmailId() {
		return doctorEmailId;
	}

	public String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("[yyyy-MM-dd] [HH:mm:ss:SSS]");
		Date dt = new Date();
		return dateFormat.format(dt);
	}

	public static String getNextDate() {
		dateFormat = new SimpleDateFormat("MM/dd/YYYY");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		Date tommorrow = calendar.getTime();
		return dateFormat.format(tommorrow);
	}
}
