package com.project.configuration;

import com.project.util.WebDriverWrap;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 15.02.2017.
 */
@Configuration
public class DriverConfig {

    @Value("${driver.timeout.default:30000}")
    private int defaultTimeout;

    @Value("${driver.name: chrome}")
    private String driverName;

    @Bean(destroyMethod = "quit")
    public WebDriverWrap  webDriverWrap() {
        WebDriver driver;
        switch (driverName.toLowerCase()) {
            case "chrome":
                ChromeDriverManager.getInstance().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                FirefoxDriverManager.getInstance().setup();
                driver = new FirefoxDriver();
                break;
            case "IE":
                InternetExplorerDriverManager.getInstance().setup();
                driver = new InternetExplorerDriver();
                break;
                default: throw new IllegalArgumentException("Invalid driver type: " + driverName);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(defaultTimeout, TimeUnit.MILLISECONDS);
        return new WebDriverWrap(driver);
    }
}
