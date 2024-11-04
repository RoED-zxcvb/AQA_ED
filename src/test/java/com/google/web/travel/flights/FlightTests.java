package com.google.web.travel.flights;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @ParameterizedTest
    @CsvSource({
            "ADB, IST, 0",
            "IST, ESB, 3",
    })
    public void testFLightIASAForNonStopDirectFLights(String departureIASA, String arrivalIASA, int daysToAdd) {
        flightsPage.open();
        flightsPage.setTextForFieldFrom(departureIASA);
        flightsPage.setDepartureAirportFromListByNumber(0);
        flightsPage.setTextForFieldTo(arrivalIASA);
        flightsPage.setArrivalAirportFromListByNumber(0);
        flightsPage.chooseNumberOfTrips(FlightsPage.NumberOfTrips.ONE_WAY);
        flightsPage.clickToDepartureDateField();
        flightsPage.chooseAvailableDepartureDateByIndex(daysToAdd);
        flightsPage.clickDoneInCalendar();
        flightsPage.clickSearch();
        flightsPage.openListOfStopsNumber();
        flightsPage.changeStopsNumber(FlightsPage.StopNumbers.NONSTOP_ONLY);
        flightsPage.closeList();
        flightsPage.verifyDepartureAirportIATAOfFlights(departureIASA, flightsPage.getListOfFlights());
        flightsPage.verifyArrivalAirportIATAOfFlights(arrivalIASA, flightsPage.getListOfFlights());
    }

    @AfterEach
    void afterAllTestsActions() {
        webDriver.close();
    }


}
