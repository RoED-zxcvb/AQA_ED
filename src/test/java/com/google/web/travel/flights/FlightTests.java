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
        flightsPage.setTextForFieldFrom("Lisbon");
        flightsPage.setDepartureAirportFromListByNumber(0);
        flightsPage.setTextForFieldTo("Izmir");
        flightsPage.setArrivalAirportFromListByNumber(0);

    }

    @AfterEach
    void afterAllTestsActions(){
        webDriver.close();
    }




}
