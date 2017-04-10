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
public class MainPage extends BasePage {

    public String pageUrl = "";
    public String pageTitle = "Yandex";

    @FindBy(xpath = "//div[@class='home-logo__default']")
    public WebElement homeLogo;

    @FindBy(xpath = "//span[@class = 'username']")
    public WebElement username;

    @FindBy(xpath = "//span[contains(@class,'input auth__username')]//input")
    public WebElement loginUserName;

    @FindBy(xpath = "//span[contains(@class,'input auth__password')]//input")
    public WebElement loginPassword;

    @FindBy(xpath = "//a[@data-statlog='mail.login.usermenu.exit']")
    public WebElement logoutButton;

    @FindBy(xpath = "//button[contains(@class,'button auth__button')]")
    public WebElement loginButton;


    @PostConstruct
    public void init() {
        pageUrl = url + pageUrl;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(pageUrl);
    }

    public void logout() {
        username.click();
        logoutButton.click();
    }

    public void login(String user, String pass) {
        loginUserName.sendKeys(user);
        loginPassword.sendKeys(pass);
        loginButton.click();
    }
}
