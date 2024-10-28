package com.google.web.travel.flights;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FlightTests {
    private WebDriver webDriver;
    private FlightsPage flightsPage;

    @BeforeEach
    void beforeTestsActions() {
        webDriver = new ChromeDriver();
        flightsPage = new FlightsPage(webDriver);
    }

    @Test
    public void someTest (){
        flightsPage.open();
        flightsPage.setTextForFieldFrom("izmir");
        flightsPage.getAirportFromListByNumber(1);
        flightsPage.setTextForFieldTo("Lisbon");
        flightsPage.getAirportFromListByNumber(1);
        flightsPage.setTextForFieldDepartureDate("Wed, Jan 6");
//        flightsPage.clickExplore();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @AfterEach
    void afterAllTestsActions(){
        webDriver.close();
    }




}
