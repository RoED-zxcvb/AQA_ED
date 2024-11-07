package com.google.runner;

import com.google.config.WebDriverManager;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/google/features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,
        value = "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
public class GoogleFlightRunner {

    @BeforeAll
    public static void before(){
    }

    @AfterAll
    public static void after(){
        WebDriverManager.getDriver().quit();
    }
}

