package com.project.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * Created by User on 15.02.2017.
 */
public class WebDriverWrap implements WebDriver {

    private final Logger logger = LoggerFactory.getLogger(WebDriverWrap.class);

    private WebDriver driver;

    public WebDriverWrap(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getWrappedDriver() {
        return driver;
    }

    @Override
    public void get(String s) {
        logger.info("Open URL: " + s);
        driver.get(s);
    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        logger.info("Finding list of elements by: " + by);
        return driver.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        logger.info("Finding element by: " + by);
        return driver.findElement(by);
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        logger.info("Closing page: " + driver.getCurrentUrl());
        driver.close();
    }

    @Override
    public void quit() {
        logger.info("Closing driver...");
        driver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    @Override
    public Navigation navigate() {
        return driver.navigate();
    }

    @Override
    public Options manage() {
        return driver.manage();
    }
}
