package com.project.pages;

import com.project.util.WebDriverWrap;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by User on 15.02.2017.
 */
@Component
public class HeaderBlock {

    @Autowired
    private WebDriverWrap driver;

    @FindBy(xpath = "//div[@class='mail-User-Name']")
    public WebElement username;

    @FindBy(xpath = "//a[text()='Выход']")
    public WebElement exitButton;

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, this);
    }
}
