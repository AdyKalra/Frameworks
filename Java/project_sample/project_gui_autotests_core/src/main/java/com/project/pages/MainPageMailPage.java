package com.project.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by User on 15.02.2017.
 */
@Component
public class MainPageMailPage extends BasePage {

    public String pageUrl = "/mail";
    public String pageTitle = "Yandex";

    @FindBy(xpath = "//input[@name='login']")
    public WebElement loginField;

    @FindBy(xpath = "//input[@type='password']")
    public WebElement passwordField;

    @FindBy(xpath = "//input[@type='submit']")
    public WebElement loginButton;

    @PostConstruct
    public void init() {
        pageUrl = url + pageUrl;
        PageFactory.initElements(driver, this);
    }

    public void login(String login, String password) {
        loginField.sendKeys(login);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    public void open(){
        driver.get(pageUrl);
    }
}
