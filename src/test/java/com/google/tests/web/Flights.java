package com.google.tests.web;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Flights {
    private WebDriver webDriver;
    private com.google.pages.Flights flightsPage;

    @BeforeEach
    void beforeTestsActions() {
        webDriver = new ChromeDriver();
        flightsPage = new com.google.pages.Flights(webDriver);
    }

    @ParameterizedTest
    @CsvSource({
            "ADB, IST, 1",
            "IST, ESB, 3",
            "LIS, FNC, 4",
    })
    public void testFLightIASAForNonStopDirectFLights(String departureIASA, String arrivalIASA, int daysToAdd) {
        flightsPage.open();
        flightsPage.setTextForFieldFrom(departureIASA);
        flightsPage.setDepartureAirportFromListByNumber(0);
        flightsPage.setTextForFieldTo(arrivalIASA);
        flightsPage.setArrivalAirportFromListByNumber(0);
        flightsPage.chooseNumberOfTrips(com.google.pages.Flights.NumberOfTrips.ONE_WAY);
        flightsPage.clickToDepartureDateField();
        flightsPage.chooseAvailableDepartureDateByIndex(daysToAdd);
        flightsPage.clickDoneInCalendar();
        flightsPage.clickSearch();
        flightsPage.openListOfStopsNumber();
        flightsPage.changeStopsNumber(com.google.pages.Flights.StopNumbers.NONSTOP_ONLY);
        flightsPage.closeList();
        flightsPage.verifyDepartureAirportIATAOfFlights(departureIASA, flightsPage.getListOfFlights());
        flightsPage.verifyArrivalAirportIATAOfFlights(arrivalIASA, flightsPage.getListOfFlights());
    }

    @AfterEach
    void afterAllTestsActions() {
        webDriver.close();
    }


}
