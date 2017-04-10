package com.project.reporting;

import com.project.util.WebDriverWrap;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.qatools.allure.annotations.Attachment;

/**
 * Created by User on 15.02.2017.
 */
@Component
public class AllureAttachments {

    @Autowired
    private WebDriverWrap driver;

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] attachScreenshot() {
        return ((TakesScreenshot) driver.getWrappedDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value="Current URL", type = "text/html")
    public String attachCurrentUrl() {
        return driver.getCurrentUrl();
    }

}
