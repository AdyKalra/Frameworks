package com.heroku.theinternet.pages.web;

import com.frameworkium.core.ui.annotations.Visible;
import com.frameworkium.core.ui.pages.BasePage;
import com.frameworkium.core.ui.pages.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.annotations.Name;

import java.io.File;

public class FileUploadPage extends BasePage<FileUploadPage> {

    @Visible
    @Name("Choose Files button")
    @FindBy(css = "input#file-upload")
    private WebElement chooseFilesButton;

    @Visible
    @Name("Upload button")
    @FindBy(css = "input#file-submit")
    private WebElement uploadButton;

    @Step("Upload a file by choosing file and then clicking upload")
    public FileUploadSuccessPage uploadFile(String filePath) {
        chooseFilesButton.sendKeys(filePath);
        uploadButton.click();
        return PageFactory.newInstance(FileUploadSuccessPage.class);
    }

}
