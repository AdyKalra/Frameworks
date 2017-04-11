package com.github.tests.web;

import com.frameworkium.core.ui.tests.BaseTest;
import com.github.pages.web.ExplorePage;
import com.github.pages.web.HomePage;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Issue;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class ComponentExampleTest extends BaseTest {

    @Issue("CET-1")
    @Test(description = "Simple test showing the use of components")
    public final void componentExampleTest() {

        // Navigate to homepage then use the nav bar to go to the explore page
        ExplorePage explorePage = HomePage.open().then().with().theHeader().clickExplore();

        // not a good assertion, improving this is an exercise for the reader
        assertThat(explorePage.getTitle()).isEqualTo("Explore · GitHub");

        // Search for "Selenium" and check SeleniumHQ/selenium is one of the returned repos.
        List<String> searchResults = explorePage.with().theHeader()
                .search("Selenium")
                .getRepoNames();
        assertThat(searchResults).contains("SeleniumHQ/selenium");
    }
}
