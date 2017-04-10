package com.project.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by User on 15.02.2017.
 */
@Component
public class MailPage extends BasePage {

    public String pageUrl = "/mail";
    public String pageTitle;

    @Autowired
    public LetterViewBlock letterViewBlock;

    @FindBy(xpath = "//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']")
    public List<WebElement> lettersTitles;

    @FindBy(xpath = "//h1[@data-key='view=logo']")
    public WebElement mailLogo;


    @PostConstruct
    public void init() {
        pageUrl = url + pageUrl;
        PageFactory.initElements(driver, this);
    }

    public void openLetter(String title) {
        for (WebElement we: lettersTitles) {
            if (we.getText().equals(title)) {
                we.click();
                break;
            }
        }
    }


}
