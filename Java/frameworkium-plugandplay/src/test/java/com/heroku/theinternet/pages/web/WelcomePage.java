package com.heroku.theinternet.pages.web;

import com.frameworkium.core.ui.annotations.Visible;
import com.frameworkium.core.ui.pages.BasePage;
import com.frameworkium.core.ui.pages.PageFactory;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Link;

public class WelcomePage extends BasePage<WelcomePage> {

    @Visible
    @Name("Basic auth link")
    @FindBy(linkText = "Basic Auth")
    private Link basicAuthLink;

    @Visible
    @Name("Checkboxes link")
    @FindBy(linkText = "Checkboxes")
    private Link checkboxesLink;

    @Name("Drag and Drop link")
    @FindBy(linkText = "Drag and Drop")
    private Link dragAndDropLink;

    @Name("Dropdown Link")
    @FindBy(linkText = "Dropdown")
    private Link dropdownLink;

    @Name("Dynamic Loading link")
    @FindBy(linkText = "Dynamic Loading")
    private Link dynamicLoadingLink;

    @Name("File Download Link")
    @FindBy(linkText = "File Download")
    private Link fileDownloadLink;

    @Name("File Upload Link")
    @FindBy(linkText = "File Upload")
    private Link fileUploadLink;

    @Name("Form Authentication Link")
    @FindBy(linkText = "Form Authentication")
    private Link formAuthenticationLink;

    @Name("Hovers Link")
    @FindBy(linkText = "Hovers")
    private Link hoversLink;

    @Name("Frames Link")
    @FindBy(linkText = "Frames")
    private Link framesLink;

    @Name("JQuery UI Link")
    @FindBy(linkText = "JQuery UI Menus")
    private Link jqueryUILink;

    @Name("JavaScript Alerts Link")
    @FindBy(linkText = "JavaScript Alerts")
    private Link javascriptAlertsLink;

    @Name("Key Presses Link")
    @FindBy(linkText = "Key Presses")
    private Link keyPressesLink;

    @Name("Secure File Download Link")
    @FindBy(linkText = "Secure File Download")
    private Link secureFileLink;

    @Name("Sortable Data Tables Link")
    @FindBy(linkText = "Sortable Data Tables")
    private Link sortableDataTablesLink;

    @Step("Navigate to http://the-internet.herokuapp.com")
    public static WelcomePage open() {
        return PageFactory.newInstance(
                WelcomePage.class, "http://the-internet.herokuapp.com");
    }

    @Step("Click the Dynamic Loading link - user: {0}, password {1}")
    public BasicAuthSuccessPage clickBasicAuth(String username, String password) {
        // For this sort of authentication, Selenium cannot handle the dialog
        // box that appears if you click the link.
        // Instead, we can provide the username and password in the URL:
        String url = formatBasicAuthURL(username, password, basicAuthLink.getReference());
        return PageFactory.newInstance(BasicAuthSuccessPage.class, url);
    }

    @Step("Click the Checkboxes link")
    public CheckboxesPage clickCheckboxesLink() {
        checkboxesLink.click();
        return PageFactory.newInstance(CheckboxesPage.class);
    }

    @Step("Click the Drag And Drop link")
    public DragAndDropPage clickDragAndDropLink() {
        dragAndDropLink.click();
        return PageFactory.newInstance(DragAndDropPage.class);
    }

    @Step("Click the Dropdown link")
    public DropdownPage clickDropdownLink() {
        dropdownLink.click();
        return PageFactory.newInstance(DropdownPage.class);
    }

    @Step("Click the Dynamic Loading link")
    public DynamicLoadingPage clickDynamicLoading() {
        dynamicLoadingLink.click();
        return PageFactory.newInstance(DynamicLoadingPage.class);
    }

    @Step("Click the File Download link")
    public FileDownloadPage clickFileDownloadLink() {
        fileDownloadLink.click();
        return PageFactory.newInstance(FileDownloadPage.class);
    }

    @Step("Click the File Upload link")
    public FileUploadPage clickFileUploadLink() {
        fileUploadLink.click();
        return PageFactory.newInstance(FileUploadPage.class);
    }

    @Step("Click the Form authentication link")
    public FormAuthenticationPage clickFormAuthenticationLink() {
        formAuthenticationLink.click();
        return PageFactory.newInstance(FormAuthenticationPage.class);
    }

    @Step("Click the Frames link")
    public FramesPage clickFramesLink() {
        framesLink.click();
        return PageFactory.newInstance(FramesPage.class);
    }

    @Step("Click the Hovers link")
    public HoversPage clickHoversLink() {
        hoversLink.click();
        return PageFactory.newInstance(HoversPage.class);
    }

    @Step("Click the Jquery UI link")
    public JQueryUIMenuPage clickJQueryUILink() {
        jqueryUILink.click();
        return PageFactory.newInstance(JQueryUIMenuPage.class);
    }

    @Step("Click the JavaScript Alerts link")
    public JavaScriptAlertsPage clickJavascriptAlertsLink() {
        javascriptAlertsLink.click();
        return PageFactory.newInstance(JavaScriptAlertsPage.class);
    }

    @Step("Click the Key Presses link")
    public KeyPressesPage clickKeyPressesLink() {
        keyPressesLink.click();
        return PageFactory.newInstance(KeyPressesPage.class);
    }

    @Step("Click the Secure File download link")
    public SecureFileDownloadPage clickSecureFileDownloadsLink(String username, String password) {
        // For this sort of authentication, Selenium cannot handle the dialog
        // box that appears if you click the link.
        // Instead, we can provide the username and password in the URL:
        String url = formatBasicAuthURL(username, password, secureFileLink.getReference());
        return PageFactory.newInstance(SecureFileDownloadPage.class, url);
    }

    private String formatBasicAuthURL(String username, String password, String originalURL) {
        return String.format("http://%s:%s@%s",
                username, password, originalURL.replace("http://", ""));
    }

    @Step("Click the Sortable Data Table link")
    public SortableDataTablesPage clickSortableDataTablesLink() {
        sortableDataTablesLink.click();
        return PageFactory.newInstance(SortableDataTablesPage.class);
    }
}
