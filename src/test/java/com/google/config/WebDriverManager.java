package com.google.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverManager {

    private static WebDriver webDriver;

    public static WebDriver getDriver() {
        if (webDriver == null) {
            webDriver = new ChromeDriver();
            webDriver.manage().window().maximize();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (webDriver != null) {
                    webDriver.quit();
                }
            }));
        }
        return webDriver;
    }

    public static void closeDriver() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
    }
}
