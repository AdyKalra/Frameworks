package com.zapprx.testing.end2endtests.basetests;

import static com.zapprx.testing.end2endtests.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.zapprx.testing.end2endtests.automation.TestSessionInitiator;

public class BaseTest {
	protected TestSessionInitiator test;
	protected String baseUrl;

	protected BaseTest(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication(getYamlValue(baseUrl));
	}

	@BeforeMethod
	public void testMethodName(Method method) {
		test.stepStartMessage(method.getName());
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

	@AfterClass
	public void close_Test_Session() {
		test.closeBrowserSession();
	}
}
