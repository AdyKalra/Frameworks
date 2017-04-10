package com.project.pages;

import com.project.util.WebDriverWrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


/**
 * Created by User on 15.02.2017.
 */
public abstract class BasePage {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${product.url}")
    public String url;
    @Autowired
    protected WebDriverWrap driver;

    @Autowired
    public HeaderBlock header;
}
