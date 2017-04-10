package com.zapprx.testing.end2endtests.automation.reporting;

import static com.zapprx.testing.end2endtests.automation.utils.ConfigPropertyReader.getProperty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.zapprx.testing.end2endtests.automation.utils.YamlReader;

public class ResultsIT {
	String today = new Date().toString();
	String resultOfRun = null;
	String host = "smtp.gmail.com";
	String from = "automation.resultsqait@gmail.com";
	String password = "QaitAutomation";
	int port = 25;
	String failureResults = "";
	String skippedResults = "";
	String passedResult = "";
	boolean sendResults = false;
	YamlReader util = new YamlReader();
	final String projectName = "ZappRx";
	@SuppressWarnings("unused")
	private String totaltest;
	@SuppressWarnings("unused")
	private String passedResults;
	public static int count = 0;

	@BeforeClass
	void setupMailConfig() {
	}

	@Test(enabled = false)
	public void sendResultsMail() throws MessagingException, IOException {
		if (true) { // send email is true *************************
			Message message = new MimeMessage(getSession());
			message.addFrom(new InternetAddress[] { (new InternetAddress(from)) });
			setMailRecipient(message);
			message.setContent(setAttachment());
			message.setSubject(setMailSubject());
			Session session = getSession();
			Transport transport = session.getTransport("smtps");
			transport.connect(host, from, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		}
	}

	private Session getSession() {
		Authenticator authenticator = new Authenticator(from, password);
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtps");
		properties.put("mail.smtps.auth", "true");
		properties.setProperty("mail.smtp.submitter", authenticator
				.getPasswordAuthentication().getUserName());
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", String.valueOf(port));
		return Session.getInstance(properties, authenticator);
	}

	private String setBodyText() throws IOException {
		List<String> failedResultsList = printFailedTestInformation();
		String[] failedResultArray = new String[failedResultsList.size()];
		for (int i = 0; i < failedResultArray.length; i++) {
			failedResultArray[i] = failedResultsList.get(i);
		}
		String mailtext = "";

		mailtext = "Hi All,<br>";
		mailtext = mailtext + "</br><b>" + projectName
				+ " Test Automation Result:: </b></br><br>";
		mailtext = mailtext
				+ "<br><b><font style = Courier, color = green>Test Name: </font></b>"
				+ getTestName();

		mailtext = mailtext + "<br><b><font color = green>Browser: </font></b>"
				+ getProperty("./Config.properties", "browser").toUpperCase();
		mailtext = mailtext
				+ "<br><b><font color = green>Test Case Executed By: </font></b>"
				+ projectName + " Automation Team";
		mailtext = mailtext
				+ "<br><b><font color = green>Test Date: </font></b>" + today;
		mailtext = mailtext + "<b>" + testSetResult() + "</b>";

		mailtext = mailtext + "<br><br>";

		mailtext = mailtext
				+ "<br><br>Note: This is a system generated mail. Please do not reply."
				+ "</br></br>";
		mailtext = mailtext
				+ "<br>If you have any queries mail to <a href=mailto:" + from
				+ "?subject=Reply-of-Automation-Status"
				+ today.replaceAll(" ", "_") + ">ZappRx Automation </a></br>";
		mailtext = mailtext
				+ "<br><br>The detailed test results are given in the attached <i>emailable-report.html</i> </br></br>";
		mailtext = mailtext + "<br><br>Best Regards" + "</br></br>";
		mailtext = mailtext + "<br>" + projectName + " Automation Team"
				+ "</br>";

		return mailtext;
	}

	private String setMailSubject() {
		return (projectName + " Automated Test Results: " + failureResults
				+ " Failures | " + today);
	}

	private void setMailRecipient(Message message) throws AddressException,
			MessagingException, IOException {
		YamlReader.setYamlFilePath();
		Map<String, Object> emailMap = YamlReader
				.getYamlValues("email.recepients");
		for (Object val : emailMap.values()) {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					val.toString()));
		}
	}

	private Multipart setAttachment() throws MessagingException, IOException {
		// Create the message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();

		// Fill the message
		messageBodyPart.setContent(setBodyText(), "text/html");

		MimeMultipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// Part two is attachment
		messageBodyPart = new MimeBodyPart();
		addAttachment(multipart, messageBodyPart,
				"./target/surefire-reports/emailable-report.html");

		return multipart;
	}

	private static void addAttachment(Multipart multipart,
			MimeBodyPart messageBodyPart, String filename)
			throws MessagingException {
		messageBodyPart = new MimeBodyPart();
		File f = new File(filename);
		DataSource source = new FileDataSource(f);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(f.getName());
		multipart.addBodyPart(messageBodyPart);
	}

	private String getTestName() {
		String test = System.getProperty("test", "null");
		String testsuite = System.getProperty("testsuite", "null");
		String testName;
		if (test != "null") {
			testName = test + " was executed";
			return testName;
		} else if (testsuite != "null") {
			testName = testsuite + "were executed";
			return testName;
		} else {
			testName = "Complete Automated SMOKE Test Suite Executed";
			return testName;
		}
	}

	@SuppressWarnings("unused")
	private String getTextFile() {
		String textFile = "";
		File folder = new File("./target/surefire-reports/");
		String[] fileNames = folder.list();
		int total = 0;
		for (int i = 0; i < fileNames.length; i++) {
			if (fileNames[i].contains(".txt")) {
				total++;
				assert total == 1;
				textFile = fileNames[i];
				return textFile;
			}
		}
		return textFile;
	}

	private String testSetResult() throws IOException {
		String messageToBeSent = "";
		String overallRes = "";

		String filepath = "./target/surefire-reports/testng-results.xml";
		overallRes = parseTestNgXmlFile(filepath);
		messageToBeSent = "<br>" + overallRes;
		return messageToBeSent;
	}

	private String parseTestNgXmlFile(String filepath) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document dom = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			dom = dBuilder.parse(filepath);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		NodeList nodes = dom.getElementsByTagName("testng-results");
		Element ele = (Element) nodes.item(0);
		String msgOutput = "Tests run: ";
		failureResults = ele.getAttribute("failed");
		skippedResults = ele.getAttribute("skipped");
		passedResult = ele.getAttribute("passed");
		NodeList nodes1 = dom.getElementsByTagName("suite");
		Element ele1 = (Element) nodes1.item(0);
		String totalTime = ele1.getAttribute("duration-ms");
		if (Math.round(Double.parseDouble(totalTime) / 1000) > 60) {
			totalTime = String.valueOf(Math.round((Double
					.parseDouble(totalTime) / 1000) / 60)) + " minutes";
		} else {
			totalTime = String
					.valueOf(Math.round(Double.parseDouble(totalTime) / 1000))
					+ " seconds";
		}
		msgOutput = msgOutput + ele.getAttribute("total") + " ,Passed: "
				+ passedResult + " ,Failures: " + ele.getAttribute("failed")
				+ " ,Skipped: " + ele.getAttribute("skipped")
				+ " ,Total Execution Time: " + totalTime;
		System.out.println("Message is " + msgOutput);
		return msgOutput;
	}

	@SuppressWarnings("unused")
	private String checkFilePresent() {
		File folder = new File("./target/surefire-reports");
		String[] fileNames = folder.list();
		for (int i = 0; i < fileNames.length; i++) {
			if (fileNames[i].contains("TEST-TestSuite")) {
				return "./target/surefire-reports/" + fileNames[i];
			} else if (fileNames[i].contains("TEST-com")) {
				return "./target/surefire-reports/" + fileNames[i];
			}
		}
		return "";
	}

	@SuppressWarnings("unused")
	private void parseTestNgXmlFile1(String filepath) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document dom = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			dom = dBuilder.parse(filepath);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		NodeList nodes = dom.getElementsByTagName("testng-results");
		Element ele = (Element) nodes.item(0);
		totaltest = ele.getAttribute("total");
		passedResults = ele.getAttribute("passed");
		failureResults = ele.getAttribute("failed");
		skippedResults = ele.getAttribute("skipped");
	}

	private List<String> printFailedTestInformation() {
		String filepath = "./target/surefire-reports/testng-results.xml";
		File file = new File(filepath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document dom = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			dom = dBuilder.parse(file);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> list = identifyTagsAndTraverseThroguhElements(dom);
		return list;
	}

	private List<String> identifyTagsAndTraverseThroguhElements(Document dom) {
		List<String> list = new ArrayList<String>();

		NodeList nodes = dom.getElementsByTagName("test-method");
		try {
			NodeList nodesMessage = dom.getElementsByTagName("full-stacktrace");
			for (int i = 0, j = 0; i < nodes.getLength()
					&& j < nodesMessage.getLength(); i++) {
				Element ele1 = (Element) nodes.item(i);
				Element ele2 = (Element) nodesMessage.item(j);

				if (ele1.getAttribute("status").equalsIgnoreCase("FAIL")) {
					count++;
					String[] testMethodResonOfFailure = getNameTestReason(ele1,
							ele2);
					list.add(testMethodResonOfFailure[0]);
					list.add(testMethodResonOfFailure[1]);
					list.add(testMethodResonOfFailure[2]);

					j++;
				}
			}
		} catch (Exception e) {
			Reporter.log("No Failures!!");
		}
		return list;
	}

	private String[] getNameTestReason(Element el1, Element el2) {
		String[] returnNameTestReason = new String[3];
		NamedNodeMap name = el1.getParentNode().getParentNode().getAttributes();

		returnNameTestReason[0] = name.getNamedItem("name").toString()
				.replaceAll("name=", "");
		returnNameTestReason[1] = el1.getAttribute("name");
		returnNameTestReason[2] = el2.getTextContent();
		return returnNameTestReason;
	}

	@SuppressWarnings("unused")
	private String giveTable(String[] failedResults) {
		String table = "";
		table = table
				+ "<table border='3'><tbody><tr style='background:red'><th><b>Test Case</b></th>"
				+ "<th><b>Test Method</b></th></tr>";

		for (int k = 0; k < failedResults.length; k += 3) {
			table = table + "<tr valign='top'><b>" + failedResults[k] + "</b>"
					+ "<b><td>" + failedResults[k + 1] + "</b></tr>";
		}
		table = table + "</tbody></table>";
		return table;
	}
}
