/**
 *
 */
package com.zapprx.comparatorator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import com.zapprx.comparatorator.inventories.FormField;
import com.zapprx.comparatorator.inventories.Inventory;
import com.zapprx.comparatorator.results.AbstractComparisionResult;
import com.zapprx.comparatorator.results.ErrorResult;
import com.zapprx.comparatorator.results.MismatchResult;
import com.zapprx.comparatorator.util.TrivalArgParser;

/**
 * The main class for comparing documents
 * 
 * @author ejw
 *
 */
public class Comparatorator {
	private static final String COMMAND_NAME_STRING = "comparatorator";
	private static final String USAGE_STRING = "Usage: " + COMMAND_NAME_STRING + " [-d] [-q] goodfile testfile\n"
			+ "\tgoodfile: the known good example file\n" + "\ttestfile: the file being checked for correctness\n"
			+ "\t-q: Quiet. Print no output\n" + "\t-d: Debug. Print debugging information";
	private final Inventory goodInventory;

	/**
	 * Creates a Comparatorator object that will compare test documents to the
	 * given known good document
	 *
	 * @param goodInputStream
	 * @param goodFileName
	 * @throws IOException
	 *             if there is an error processing the file
	 */
	public Comparatorator(InputStream goodInputStream, String goodFileName) throws IOException {
		this(new TestDocument(goodInputStream, goodFileName));
	}

	/**
	 * Creates a Comparatorator object that will compare test documents to the
	 * given known good document
	 *
	 * @param goodDoc
	 * @throws IOException
	 *             if there is an error processing the file
	 */
	public Comparatorator(TestDocument goodDoc) throws IOException {
		this(goodDoc.extractInventory());
	}

	/**
	 * @param goodInventory
	 */
	public Comparatorator(Inventory goodInventory) {
		this.goodInventory = goodInventory;
	}

	/**
	 * Creates a Comparatorator object that will compare test documents to the
	 * given known good document
	 *
	 * @param goodFile
	 * @throws IOException
	 *             if there is an error processing the file
	 */
	public Comparatorator(File goodFile) throws IOException {
		this(new TestDocument(goodFile));
	}

	/**
	 * @param is
	 * @param filename
	 * @return
	 */
	public ComparisonResult compare(InputStream is, String filename) {
		return compare(new TestDocument(is, filename));
	}

	/**
	 * @param testfile
	 * @return
	 */
	public ComparisonResult compare(File testfile) {
		try {
			return compare(new TestDocument(testfile));

		} catch (final IOException e) {
			return new ErrorResult(e);
		}
	}

	/**
	 * @param testDoc
	 * @return
	 */
	public ComparisonResult compare(TestDocument testDoc) {
		try {
			final Inventory testInventory = testDoc.extractInventory();
			for (final String key : goodInventory.keys()) {
				final FormField goodValue = goodInventory.get(key);
				// ********* This list is comprised of all the key (all the
				// forms under each medication of PAH Indication) for
				// whom values are not being compared while performing the
				// comparison of two pdf files i.e. the good pdf file and the
				// test pdf file **************//
				ArrayList<String> patientInfoList = new ArrayList<String>(Arrays.asList("Patient Last Name",
						"Patient Sign Date", "Patient Email", "Patient Street", "Gender", "Relationship to Patient",
						"Patient Postal Code", "Patient Alternate Contact Phone", "Prescriber Sign Date",
						"Patient Last Name 2", "Patient Primary Phone", "Patient DOB", "Patient DOB 2",
						"Patient Alternate Contact Name", "Patient City", "Female Patient Sign Date",
						"Emergency Contact Relationship", "ZIP", "Patient Caregiver/Family Member", "Patient Address",
						"Primary Insured Name", "Patient Home Phone", "Patient Name", "Weight", "Prescription Time",
						"Height", "Primary Insurer Phone", "yesyes.", "Physician Sign Date", "Male",
						"Patient Emergency Contact", "Emergency Contact Phone", "Patient female Signature Date",
						"Prescription Date", "Emergency Phone", "Signature Substitution Allowed Date", "Patient Name_2",
						"Patient Name_3", "Patient DOB_3", "Patient Weight", "Patient DOB_0", "Patient DOB_2",
						"Patient DOB_1", "Patient HIPAA Signature Date", "Concurrent Meds_1", "Concurrent Meds_2",
						"Primary Insured DOB", "Female", "Date", "Patient Representative Sign Date",
						"Doctor Allow Substitute Signature Date", "Fax Cover Sheet Date", "Emergency Name",
						"Patient Name_3_1", "Address", "Birth date", "Date_3", "Primary phone", "Last name", "Phone",
						"Patient name print", "Date 4", "DOB", "For Patient 1",
						"Patient Caregiver/Family Member Telephone", "Patient Date of Birth",
						"Primary Medical Insurance Subscriber Telephone", "For Patient",
						"Primary Medical Insurance Policy Holder/Relationship", "DOB 2", "DOB 1", "Patient Name Last",
						"Patient Email Address", "For Patient 2", "Patient Telephone", "Patient Zip",
						"Patient Home Address", "Time", "Patient Height", "Fax Cover Sheet Date Time", "Patient Name_6",
						"Patient Name_5", "Patient Name_4", "Alternate Contact", "Patient DOB_4", "Patient DOB_5",
						"Dosing Weight", "Remodulin kglb", "Patient Phone", "Policy holder name", "Ship-to Zip",
						"Policy Holder DOB", "Ship-to City", "Patient Name 1", "DOB 4", "Patient Name 3",
						"Patient Name 2", "Patient Name 5", "Ship-to Address", "Patient Name 4", "DOB 5",
						"Ship-to Attn", "Date Time", "Prescriber Date", "Fax Date", "Prescriber Signature Date",
						"Patient Signature Date", "Prescriber Initials Date", "Physician Sign Date_2",
						"Prescriber Sign Date_4", "Date_8", "Date_5", "Date_6", "Date_4", "Alternate Contact Phone",
						"Prescriber Telephone", "Prescriber Practice Phone", "Doctor Signature Date",
						"Fax Cover Sheet From_2", "Prescriber Contact Prescriber Contact Phone",
						"Patient City State ZIP", "Current Therapy", "Patient Postal Code_2", "Fax From",
						"Prescriber FAX", "Prescriber Practice Fax", "Patient HIPAA Signature Name_4",
						"Patient Address_2", "Patient Primary Phone_2", "Patient DOB MMDDYY",
						"Patient HIPAA Signature Name", "Patient City_2"));
				final FormField testValue = testInventory.get(key);
				final String good = goodValue != null ? goodValue.getValue() : null;
				final String test = testValue != null ? testValue.getValue() : null;
				if (!StringUtils.equals(good, test) && !patientInfoList.contains(key)) {
					Assert.assertEquals(test.toString(), good.toString(),
							"Assertion Failed: As the key " + key + " value is appeared incorrect");
					return new MismatchResult(key, good, test);
				}
			}
		} catch (final IOException e) {
			return new ErrorResult(e);
		}
		return AbstractComparisionResult.SUCCEEDED;
	}

	public static ComparisonResult compareTestDocuments(File goodFile, InputStream testInputStream,
			String testFileName) {
		try {
			final TestDocument gdoc = new TestDocument(goodFile);
			final TestDocument tdoc = new TestDocument(testInputStream, testFileName);
			return compareTestDocuments(gdoc, tdoc);
		} catch (final FileNotFoundException e) {
			return new ErrorResult(e);
		}
	}

	public static ComparisonResult compareTestDocuments(InputStream goodInputStream, String goodFileName,
			File testFile) {
		try {
			final TestDocument gdoc = new TestDocument(goodInputStream, goodFileName);
			final TestDocument tdoc = new TestDocument(testFile);
			return compareTestDocuments(gdoc, tdoc);
		} catch (final FileNotFoundException e) {
			return new ErrorResult(e);
		}
	}

	public static ComparisonResult compareTestDocuments(InputStream goodInputStream, String goodFileName,
			InputStream testInputStream, String testFileName) {
		final TestDocument gdoc = new TestDocument(goodInputStream, goodFileName);
		final TestDocument tdoc = new TestDocument(testInputStream, testFileName);
		return compareTestDocuments(gdoc, tdoc);
	}

	/**
	 * @param goodFile
	 * @param testFile
	 * @return
	 */
	public static ComparisonResult compareTestDocuments(File goodFile, File testFile) {
		try {
			final TestDocument gdoc = new TestDocument(goodFile);
			final TestDocument tdoc = new TestDocument(testFile);
			return compareTestDocuments(gdoc, tdoc);
		} catch (final FileNotFoundException e) {
			return new ErrorResult(e);
		}
	}

	/**
	 * @param goodDoc
	 * @param testDoc
	 * @return
	 */
	public static ComparisonResult compareTestDocuments(TestDocument goodDoc, TestDocument testDoc) {
		try {
			final Comparatorator comparatorator = new Comparatorator(goodDoc);
			return comparatorator.compare(testDoc);
		} catch (final IOException e) {
			return new ErrorResult(e);
		}
	}

	public static int doMain(String[] args) {
		final TrivalArgParser argParser = new TrivalArgParser(args);
		final String argv[] = argParser.getArgs();

		final boolean quiet = argParser.hasFlag("q");
		final boolean debug = argParser.hasFlag("d");

		if (argv.length != 2) {
			usage(quiet);
			System.exit(1);
		}

		final File goodFile = new File(argv[0]);
		final File testFile = new File(argv[1]);

		final ComparisonResult comparisonResult = compareTestDocuments(goodFile, testFile);

		if (comparisonResult.error()) {
			if (!quiet) {
				System.err.println(comparisonResult.getMessage());
			}
			if (debug) {
				final Exception e = comparisonResult.getError();
				e.printStackTrace();
			}
			return -1;
		} else if (comparisonResult.mismatch()) {
			if (!quiet) {
				System.err.println(comparisonResult.getMessage());
			}
			return 1;
		}

		if (debug) {
			System.err.println(comparisonResult.getMessage());
		}

		return 0;
	}

	/**
	 * @param args
	 *
	 *            The command line version of Compararatorator Usage:
	 *            Compararator [-d] [-q] goodfile testfile goodfile: the known
	 *            good example file testfile: the file being checked for
	 *            correctness -q: Quiet. Print no output -d: Debug. Print
	 *            debugging information
	 *
	 */
	public static void main(String[] args) {
		final int status = doMain(args);
		System.exit(status);
	}

	private static void usage(boolean quiet) {
		if (quiet) {
			return;
		}
		// result.nullTODO Auto-generated method stub
		System.err.println(USAGE_STRING);
	}
}
