package com.gk.test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = "src/test/resources", tags = {"@gui"}, monochrome = true, plugin = {
        "pretty", "html:target/cucumber-report/runwebat",
        "json:target/cucumber-report/runwebat/cucumber.json",
        "rerun:target/cucumber-report/runwebat/rerun.txt"},
        glue = "com.gk.test")
public class RunWebATSuite extends AbstractTestNGCucumberTests {
}
