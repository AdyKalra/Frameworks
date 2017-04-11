package com.heroku.theinternet.tests.web;

import com.frameworkium.core.common.retry.RetryFlakyTest;
import com.frameworkium.core.ui.tests.BaseTest;
import com.heroku.theinternet.pages.web.*;
import org.testng.SkipException;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Issue;
import ru.yandex.qatools.allure.annotations.Stories;

import java.net.URL;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@Features("The Internet")
public class TheInternetExampleTests extends BaseTest {

    @Issue("HEROKU-1")
    @Stories("Basic Auth Login")
    @Test(description = "Basic Auth")
    public void basicAuth() {

        String pageSource = WelcomePage.open().then()
                // Navigate to the basic auth page
                .clickBasicAuth("admin", "admin")
                .getSource();

        // Assert that the returned page has the text present
        assertThat(pageSource).contains(
                "Congratulations! You must have the proper credentials.");
    }

    @Issue("HEROKU-2")
    @Stories("Check Checkboxes")
    @Test(description = "Checkboxes")
    public void checkBoxes() {

        // Navigate to the checkboxes page
        CheckboxesPage checkboxesPage = WelcomePage.open().then().clickCheckboxesLink();

        // Set all checkboxes to checked via alternative method
        checkboxesPage.checkAllCheckboxes();

        // Assert that all checkboxes are checked
        assertThat(checkboxesPage.getAllCheckboxCheckedStatus())
                .named("check status of checkboxes")
                .doesNotContain(false);
    }

    @Issue("HEROKU-3")
    @Test(description = "Drag and Drop")
    public void dragAndDrop() {

        // Navigate to the checkboxes page
        List<String> headings = WelcomePage.open().then()
                .clickDragAndDropLink()
                // Drag A onto B
                .dragAontoB()
                .getListOfHeadings();

        // Assert on the order of the headings
        assertThat(headings)
                .named("Order of headings")
                .containsExactly("B", "A")
                .inOrder();
    }

    @Issue("HEROKU-4")
    @Test(description = "Dropdown")
    public void dropdown() {

        // Navigate to the checkboxes page
        DropdownPage dropdownPage = WelcomePage.open().then().clickDropdownLink();

        // Drag A onto B
        dropdownPage.selectFromDropdown("Option 1");

        // Assert selected
        assertThat(dropdownPage.getSelectedOptionText())
                .named("selected option in dropdown")
                .isEqualTo("Option 1");
    }

    @Issue("HEROKU-5")
    @Test(description = "Dynamic loading")
    public void dynamicLoading() {

        // Navigate to the dynamic loading hidden element page
        DynamicLoadingExamplePage dynamicLoadingExamplePage =
                WelcomePage.open().then().clickDynamicLoading().then().clickExample1();

        // Click start and wait for element to be displayed
        dynamicLoadingExamplePage.clickStart().then().waitForElementToBeDisplayed();

        // Assert that the element is indeed displayed
        assertThat(dynamicLoadingExamplePage.isElementDisplayed())
                .named("element visibility")
                .isTrue();

        // Navigate to the dynamic loading element not yet rendered page
        DynamicLoadingExamplePage dynamicLoadingPage =
                WelcomePage.open().then().clickDynamicLoading().then().clickExample2();

        // Click start and wait for element to be displayed
        dynamicLoadingPage.clickStart().then().waitForElementToBeDisplayed();

        // Assert that the element is indeed present
        assertThat(dynamicLoadingPage.isElementDisplayed()).named("element presence").isTrue();
    }

    @Issue("HEROKU-6")
    @Test(description = "File Download")
    public void fileDownload() {

        // Navigate to the download page
        FileDownloadPage downloadPage = WelcomePage.open().then().clickFileDownloadLink();

        // If you have the file
        // File testFile = new File("/Users/robgates55/avatar.jpg");
        // FileInputStream f = new FileInputStream(testFile.getAbsolutePath());
        // int size = IOUtils.toByteArray(f).length;
        // IOUtils.closeQuietly(f);

        // Confirm that the some-file.txt file in the list (as other people might be using it!)
        assertThat(downloadPage.getDownloadableFileLinkNames())
                .contains("some-file.txt");

        // If you know the size to expect
        // int size = 307;

        // Commenting out the below as this fails due to the uncontrolled
        // nature of this test, but leaving as a useful example

        // Confirm size of the downloaded file is as expected
        // assertThat(downloadPage.getSizeOfFile("some-file.txt")).isEqualTo(size);
    }

    @Issue("HEROKU-7")
    @Test(description = "File Upload")
    public void fileUpload() {

        // Navigate to the upload page
        FileUploadPage fileUploadPage = WelcomePage.open().then().clickFileUploadLink();

        // Pick a local file we're going to upload
        String fileName = "FirefoxGrid.yaml";
        String fileToUpload = this.getClass().getClassLoader().getResource(fileName).getFile();

        // Upload the file and confirm we land on the success page
        FileUploadSuccessPage successPage = fileUploadPage.uploadFile(fileToUpload);

        // Confirm that the uploaded files list contains our filename
        assertThat(successPage.getUploadedFiles()).contains(fileName);
    }

    @Issue("HEROKU-8")
    @Test(description = "Form Authentication")
    public void formAuthentication() {

        // Navigate to the form authentication page
        final String username = "tomsmith";
        FormAuthenticationPage formAuthenticationPage = WelcomePage
                .open().then()
                .clickFormAuthenticationLink()
                // Log in with the bad password and expect to land where we are
                .login(username, "BadBadPassword", FormAuthenticationPage.class)
                .expectErrorMessage();

        // Log in with the username password provided
        FormAuthenticationSuccessPage successPage = formAuthenticationPage
                .login(username, "SuperSecretPassword!", FormAuthenticationSuccessPage.class);

        // Confirm that we're on the success page
        assertThat(successPage.getSource()).contains("Welcome to the Secure Area");
    }

    @Issue("HEROKU-15")
    @Test(description = "iFrames test")
    public void iframes() {

        //Navigate to the frames page
        FramesPage framesPage = WelcomePage.open().then().clickFramesLink();

        //Browse to iframes page
        IFramePage iframePage = framesPage.clickIFrameLink();

        //Clear text
        iframePage.clearTextInEditor();

        //Enter some text in the editor
        String text = "hello";
        iframePage.enterTextInEditor(text);

        //Assert that it entered it correctly
        assertThat(iframePage.getTextInEditor()).isEqualTo(text);

        //Enter some bold text in the editor
        iframePage.enterBoldTextInEditor(" some more text");
    }

    @Issue("HEROKU-9")
    @Test(description = "Hovers")
    public void hovers() {

        throw new SkipException("Doesn't yet work with Selenium 3/Marionette/Geckodriver");

        /*// Navigate to the hovers page
        HoversPage hoversPage = WelcomePage.open().then().clickHoversLink();

        // Confirm that the caption under the first figure contains expected text
        assertThat(hoversPage.getFirstFigureCaption()).contains("name: user1");*/
    }

    @Issue("HEROKU-10")
    @Test(description = "JQuery UI",
            retryAnalyzer = RetryFlakyTest.class)
    public void jQuery_UI() {

        throw new SkipException("Doesn't yet work with Firefox/Marionette/Geckodriver");

        /*WelcomePage.open().then()
                // Navigate to the jQuery UI page
                .clickJQueryUILink()
                // Browse to the UI page
                .clickBackToUI()
                // Click the menu link to return to the menu page
                .clickMenuLink();*/
    }

    @Issue("HEROKU-11")
    @Test(description = "Javascript Alerts")
    public void javascript_alerts() {

        // Navigate to the javascript alerts page
        JavaScriptAlertsPage javascriptAlerts =
                WelcomePage.open().then().clickJavascriptAlertsLink();

        javascriptAlerts.clickAlertButtonAndAccept();
        assertThat(javascriptAlerts.getResultText())
                .isEqualTo("You successfuly clicked an alert");

        javascriptAlerts.clickAlertButtonAndDismiss();
        assertThat(javascriptAlerts.getResultText())
                .isEqualTo("You successfuly clicked an alert");

        javascriptAlerts.clickConfirmButtonAndAccept();
        assertThat(javascriptAlerts.getResultText())
                .isEqualTo("You clicked: Ok");

        javascriptAlerts.clickConfirmButtonAndDismiss();
        assertThat(javascriptAlerts.getResultText())
                .isEqualTo("You clicked: Cancel");

        String textToEnter = "Blah blah blah";
        javascriptAlerts.clickPromptButtonAndEnterPrompt(textToEnter);
        assertThat(javascriptAlerts.getResultText())
                .isEqualTo("You entered: " + textToEnter);
    }

    @Issue("HEROKU-12")
    @Test(description = "Key Presses")
    public void key_presses() {

        throw new SkipException("Doesn't yet work with Firefox/Marionette/Geckodriver");

        /* //Navigate to the key presses page
        KeyPressesPage keyPressesPage = WelcomePage
                .open()
                .clickKeyPressesLink()
                .enterKeyPress(Keys.ENTER);

        assertThat(keyPressesPage.getResultText())
                .isEqualTo("You entered: " + Keys.ENTER.name());*/
    }

    @Issue("HEROKU-13")
    @Test(description = "Secure file Download")
    public void secureFileDownload() {

        // Navigate to the secure file downloads page
        String headingText = WelcomePage
                .open()
                .clickSecureFileDownloadsLink("admin", "admin")
                .getHeadingText();

        // Assert that the page contains the text
        assertThat(headingText)
                .isEqualTo("Secure File Downloader");
    }

    @Issue("HEROKU-14")
    @Test(description = "Table Manipulation & Validation")
    public void sortDataTable() {

        // Navigate to the sortable data tables page
        SortableDataTablesPage sortableDataTablesPage =
                WelcomePage.open().then().clickSortableDataTablesLink();

        // Assert that Table 1 contains "http://www.jdoe.com" in the web site column
        assertThat(sortableDataTablesPage.getTable1ColumnContents("Web Site"))
                .contains("http://www.jdoe.com");

        List<String> lastNameColumn = sortableDataTablesPage
                // Sort Table 2 by last name column
                .sortTable2ByColumnName("Last Name")
                .getTable2ColumnContents("Last Name");

        // Confirm that "Bach" is then the first surname in table 2
        assertThat(lastNameColumn.get(0)).isEqualTo("Bach");

        // Confirm that the column is then ordered by the last name
        assertThat(lastNameColumn).isOrdered();
    }
}
