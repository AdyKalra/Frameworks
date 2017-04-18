package com.heroku.theinternet.tests.mobi;

import com.frameworkium.core.ui.tests.BaseTest;
import com.heroku.theinternet.pages.web.DynamicLoadingExamplePage;
import com.heroku.theinternet.pages.web.WelcomePage;
import org.testng.annotations.Test;

import static com.google.common.truth.Truth.assertThat;

public class DynamicLoadingMobiTest extends BaseTest {

    @Test(description = "Test element presence")
    public final void testElementPresence() {
        // Navigate to the dynamic loading element not yet rendered page
        DynamicLoadingExamplePage dynamicLoadingPage =
                WelcomePage.open().then().clickDynamicLoading().then().clickExample2();

        // Click start and wait for element to be displayed
        dynamicLoadingPage.clickStart().then().waitForElementToBeDisplayed();

        // Assert that the element is indeed present
        assertThat(dynamicLoadingPage.isElementPresent()).named("element presence").isTrue();
    }
}
