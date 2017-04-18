package com.tfl.web.tests;

import com.frameworkium.core.common.retry.RetryFlakyTest;
import com.frameworkium.core.ui.tests.BaseTest;
import com.tfl.web.pages.*;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Issue;

import static com.google.common.truth.Truth.assertThat;

public class PlanJourneyTest extends BaseTest {

    @Issue("TFL-1")
    @Test(description = "Plan a journey test",
            retryAnalyzer = RetryFlakyTest.class)
    public final void planJourneyTest() {

        // Navigate to homepage
        HomePage homePage = HomePage.open();

        // Click the the plan journey link
        PlanJourneyPage planJourneyPage = homePage.clickPlanJourneyLink();

        // Plan a journey between two locations
        JourneyPlannerResultsPage resultsPage = planJourneyPage
                .planJourney("Clapham Junction", "Oxford Circus Underground Station");

        // Check that the title displayed on the page is "JOURNEY RESULTS"
        assertThat(resultsPage.getTitleText()).isEqualTo("JOURNEY RESULTS");
    }
}
