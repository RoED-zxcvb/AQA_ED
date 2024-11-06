package com.google.runner;

import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/google/features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,
        value = "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
public class GoogleFlightRunner {
//
//    @BeforeAll
//    public static void before(){
//        WebDriverManager.getDriver().manage().window().fullscreen();
//    }
//
//    @AfterAll
//    public static void after(){
//        WebDriverManager.getDriver().quit();
//    }
}

