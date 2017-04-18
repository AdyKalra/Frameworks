package com.heroku.theinternet.pages.web;

import com.frameworkium.core.ui.annotations.Visible;
import com.frameworkium.core.ui.pages.BasePage;
import com.frameworkium.core.ui.pages.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Link;

public class JQueryUIMenuPage extends BasePage<JQueryUIMenuPage> {

    @Visible
    @Name("Enabled menu item")
    @FindBy(linkText = "Enabled")
    private WebElement enabledMenuItem;

    @Name("Back to JQuery UI menu item")
    @FindBy(linkText = "Back to JQuery UI")
    private Link backToJQueryUIMenuItem;

    @Name("downloads menu item")
    @FindBy(linkText = "Downloads")
    private WebElement downloadsMenuItem;

    @Name("excel file menu item")
    @FindBy(linkText = "Excel")
    private Link excelFileMenuItem;

    @Step("click back to UI")
    public JQueryUIPage clickBackToUI() {

        // Move mouse over the first figure to make caption visible
        (new Actions(driver)).moveToElement(enabledMenuItem).perform();

        // Click the now-visible link
        backToJQueryUIMenuItem.click();

        // returns us a new page
        return PageFactory.newInstance(JQueryUIPage.class);
    }

}
