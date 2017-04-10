package com.zapprx.testing.end2endtests.automation;

import static com.zapprx.testing.end2endtests.automation.utils.ConfigPropertyReader.getProperty;
import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;
import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.setYamlFilePath;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import com.zapprx.testing.end2endtests.automation.utils.TakeScreenshot;
import com.zapprx.testing.end2endtests.pageActions.admin.PharmaciesPageActions;
import com.zapprx.testing.end2endtests.pageActions.admin.PracticePageActions;
import com.zapprx.testing.end2endtests.pageActions.common.*;
import com.zapprx.testing.end2endtests.pageActions.dosage.CommonDosagePageActions;
import com.zapprx.testing.end2endtests.pageActions.dosage.DosageCommonWorkflow;
import com.zapprx.testing.end2endtests.pageActions.dosage.IPFDosagePageActions;
import com.zapprx.testing.end2endtests.pageActions.dosage.MSDosagePageActions;
import com.zapprx.testing.end2endtests.pageActions.dosage.PAHDosagePageActions;
import com.zapprx.testing.end2endtests.pageActions.dosage.RADosagePageActions;
import com.zapprx.testing.end2endtests.pageActions.patient.DocumentsPageActions;
import com.zapprx.testing.end2endtests.pageActions.physician.*;
import com.zapprx.testing.end2endtests.pageActions.services.DjangoAdminPortalPageActions;
import com.zapprx.testing.end2endtests.pageActions.services.PresAdminPortalPageActions;

public class TestSessionInitiator {
	protected WebDriver driver;
	private final WebDriverFactory wdfactory;
	String browser = System.getProperty("browser");
	String seleniumserver;
	String seleniumserverhost;
	String appbaseurl;
	String applicationpath;
	String chromedriverpath;
	String datafileloc = "";
	static int timeout;
	Map<String, Object> chromeOptions = null;
	DesiredCapabilities capabilities;

	/**
	 * Initiating the page objects
	 *
	 */
	public LoginPageActions loginPage;
	public HeaderPageActions headerPage;
	public LeftNavigationPageActions leftnavigationPage;
	public HomePageActions homePage;
	public PatientRegistrationPageActions patientRegistrationPage;
	public PatientPageActions patientPage;
	public IndicationPageActions indicationPage;
	public ChooseMedicationPageActions chooseMedicationPage;
	public PatientDetailsPageActions patientDetailPage;
	public ProviderDetailsPageActions providerDetailsPage;
	public DiagnosisPageActions diagnosisPage;
	public AuthorizationPageActions authorizationPage;
	public PrescriptionDetailsPageActions presDetailsPage;
	public PriorAuthForPatientPageActions priorAuthForPatientPage;
	public PatientProfilePageActions patientProfilePage;
	public NotificationsPageActions notificationsPage;
	public RxStatusPageActions rxStatusPage;
	public DocumentsPageActions documentPage;
	public DjangoAdminPortalPageActions djangoAdminPage;
	public PatientCommonWorkflow patientCommonWorkflow;
	public PracticeProfilePageActions practiceProfilePage;
	public MyProfilePageActions myProfilePage;
	public DoctorProfilePageActions doctorProfilePage;
	public PriorAuthorizationsPageActions priorAuthPage;
	public MedicationsPageActions medicationPage;
	public ProfilePageActions profilePage;
	public PresAdminPortalPageActions presAdminPortal;
	public PDFReadPageActions pdfPage;
	public PracticePageActions pracPage;
	public PharmaciesPageActions pharPage;
	public CommonDosagePageActions commonDosagePage;
	public DosageCommonWorkflow dosageCommonWorkflow;
	public PAHDosagePageActions PAHDosagePage;
	public RADosagePageActions RADosagePage;
	public MSDosagePageActions MSDosagePage;
	public IPFDosagePageActions IPFDosagePage;
	public TakeScreenshot takescreenshot;
	public PriorAuthorizationPageActions priorAuthorizationPage;

	public WebDriver getDriver() {
		return this.driver;
	}

	private void _initPage() {
		priorAuthorizationPage = new PriorAuthorizationPageActions(driver);
		loginPage = new LoginPageActions(driver);
		headerPage = new HeaderPageActions(driver);
		leftnavigationPage = new LeftNavigationPageActions(driver);
		homePage = new HomePageActions(driver);
		patientRegistrationPage = new PatientRegistrationPageActions(driver);
		patientPage = new PatientPageActions(driver);
		indicationPage = new IndicationPageActions(driver);
		chooseMedicationPage = new ChooseMedicationPageActions(driver);
		patientDetailPage = new PatientDetailsPageActions(driver);
		providerDetailsPage = new ProviderDetailsPageActions(driver);
		diagnosisPage = new DiagnosisPageActions(driver);
		authorizationPage = new AuthorizationPageActions(driver);
		presDetailsPage = new PrescriptionDetailsPageActions(driver);
		priorAuthForPatientPage = new PriorAuthForPatientPageActions(driver);
		patientProfilePage = new PatientProfilePageActions(driver);
		notificationsPage = new NotificationsPageActions(driver);
		rxStatusPage = new RxStatusPageActions(driver);
		documentPage = new DocumentsPageActions(driver);
		djangoAdminPage = new DjangoAdminPortalPageActions(driver);
		patientCommonWorkflow = new PatientCommonWorkflow(this, driver);
		practiceProfilePage = new PracticeProfilePageActions(driver);
		myProfilePage = new MyProfilePageActions(driver);
		doctorProfilePage = new DoctorProfilePageActions(driver);
		priorAuthPage = new PriorAuthorizationsPageActions(driver);
		medicationPage = new MedicationsPageActions(driver);
		profilePage = new ProfilePageActions(driver);
		presAdminPortal = new PresAdminPortalPageActions(driver);
		pdfPage = new PDFReadPageActions(driver);
		pracPage = new PracticePageActions(driver);
		pharPage = new PharmaciesPageActions(driver);
		commonDosagePage = new CommonDosagePageActions(driver);
		dosageCommonWorkflow = new DosageCommonWorkflow(this, driver);
		PAHDosagePage = new PAHDosagePageActions(driver);
		RADosagePage = new RADosagePageActions(driver);
		MSDosagePage = new MSDosagePageActions(driver);
		IPFDosagePage = new IPFDosagePageActions(driver);
	}

	/**
	 * Page object Initiation done
	 *
	 */
	public TestSessionInitiator(String testname) {
		wdfactory = new WebDriverFactory();
		wdfactory.setTempPath();
		testInitiator(testname);
	}

	private void testInitiator(String testname) {
		setYamlFilePath();
		_configureBrowser();
		_initPage();
		Reporter.log("***** STARTING TEST CLASS:- " + testname + " *****", true);
		takescreenshot = new TakeScreenshot(testname, this.driver);
	}

	private void _configureBrowser() {
		driver = wdfactory.getDriver(_getSessionConfig());
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(getProperty("timeout")), TimeUnit.SECONDS);
	}

	private Map<String, String> _getSessionConfig() {
		String[] configKeys = { "tier", "browser", "seleniumserver", "seleniumserverhost", "timeout", "driverpath" };
		Map<String, String> config = new HashMap<String, String>();
		for (String string : configKeys) {
			config.put(string, getProperty("./Config.properties", string));
		}
		return config;
	}

	public void launchApplication() {
		launchApplication(getYamlValue("app_url"));
	}

	public void launchApplication(String baseurl) {
		Reporter.log("\nThe application url is :- " + baseurl, true);
		if (browser == null || browser.isEmpty() || browser.equals(""))
			browser = _getSessionConfig().get("browser");
		Reporter.log("The test browser is :- " + browser + "\n", true);
		driver.manage().deleteAllCookies();
		driver.get(baseurl);
	}

	public void stepStartMessage(String testStepName) {
		Reporter.log(" ", true);
		Reporter.log("***** STARTING TEST STEP:- " + testStepName + " *****", true);
		Reporter.log(" ", true);
	}

	public void openUrl(String url) {
		driver.get(url);
	}

	public void closeBrowserSession() {
		Reporter.log("\n", true);
		driver.quit();
	}
}
