package com.project.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by User on 15.02.2017.
 */
@Component
public class LetterViewBlock extends BasePage {

    @FindBy(xpath = "//div[@class='mail-Message-Toolbar-Subject mail-Message-Toolbar-Subject_message js-toolbar-subject js-subject-content js-invalid-drag-target']")
    public WebElement letterTitle;

    @FindBy(xpath = "//div[@class='ns-view-message-body ns-view-id-114 mail-Message-Body js-message-body']")
    public WebElement letterBody;

    @FindBy(xpath = "//span[@class='mail-Message-Sender-Email mail-ui-HoverLink-Content']")
    public WebElement mailSender;

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, this);
    }
}
