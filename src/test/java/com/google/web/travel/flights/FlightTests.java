package com.google.web.travel.flights;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightTests {
    private WebDriver webDriver;
    private FlightsPage flightsPage;

    @BeforeEach
    void beforeTestsActions() {
        webDriver = new ChromeDriver();
        flightsPage = new FlightsPage(webDriver);
    }

    @Test
    public void someTest() {
        flightsPage.open();
        flightsPage.setTextForFieldFrom("ADB");
        flightsPage.setDepartureAirportFromListByNumber(0);
        flightsPage.setTextForFieldTo("IST");
        flightsPage.setArrivalAirportFromListByNumber(0);
        flightsPage.chooseNumberOfTrips(FlightsPage.NumberOfTrips.ONE_WAY);
        flightsPage.clickToDepartureDateField();
        flightsPage.chooseAvailableDepartureDateByIndex(0);
        flightsPage.clickDoneInCalendar();
        flightsPage.clickSearch();
        flightsPage.openListOfStopsNumber();
        flightsPage.changeStopsNumber(FlightsPage.StopNumbers.NONSTOP_ONLY);
        flightsPage.closeList();

        flightsPage.verifyDepartureAirportIATAOfFlights("ADB", flightsPage.getListOfFlights());

        flightsPage.verifyArrivalAirportIATAOfFlights("IST", flightsPage.getListOfFlights());





    }

    @AfterEach
    void afterAllTestsActions() {
        webDriver.close();
    }


}
