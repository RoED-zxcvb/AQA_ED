package com.google.steps;

import com.google.pages.GoogleFlightsPage;
import com.google.tests.web.GoogleFlightsTests;
import org.openqa.selenium.WebDriver;

public class GoogleFlightsSteps {
    GoogleFlightsPage googleFlightsPage;

    public void GoogleFlightsTests(WebDriver webDriver) {
        googleFlightsPage = new GoogleFlightsPage(webDriver);
    }
}
