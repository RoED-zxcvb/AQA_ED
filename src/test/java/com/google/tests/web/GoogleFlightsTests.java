package com.google.tests.web;

import com.google.steps.GoogleFlightsSteps;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GoogleFlightsTests {
    private WebDriver webDriver;
    private GoogleFlightsSteps googleFlightsSteps;

    @BeforeEach
    void beforeTestsActions() {
        webDriver = new ChromeDriver();
        googleFlightsSteps = new GoogleFlightsSteps(webDriver);
    }

    @ParameterizedTest
    @CsvSource({
            "ADB, IST, 1",
//            "IST, ESB, 3",
//            "LIS, FNC, 4",
    })
    public void testFLightIASAForNonStopDirectFLights(String departureIASA, String arrivalIASA, int daysToAdd) {
        googleFlightsSteps.open();
        googleFlightsSteps.setTextForFieldFrom(departureIASA);
        googleFlightsSteps.setDepartureAirportFromListByNumber(0);
        googleFlightsSteps.setTextForFieldTo(arrivalIASA);
        googleFlightsSteps.setArrivalAirportFromListByNumber(0);
        googleFlightsSteps.chooseNumberOfTrips(GoogleFlightsSteps.NumberOfTrips.ONE_WAY);
        googleFlightsSteps.clickToDepartureDateField();
        googleFlightsSteps.chooseAvailableDepartureDateByIndex(daysToAdd);
        googleFlightsSteps.clickDoneInCalendar();
        googleFlightsSteps.clickSearch();
        googleFlightsSteps.openListOfStopsNumber();
        googleFlightsSteps.changeStopsNumber(GoogleFlightsSteps.StopNumbers.NONSTOP_ONLY);
        googleFlightsSteps.closeList();
        googleFlightsSteps.verifyDepartureAirportIATAOfFlights(departureIASA, googleFlightsSteps.getListOfFlights());
        googleFlightsSteps.verifyArrivalAirportIATAOfFlights(arrivalIASA, googleFlightsSteps.getListOfFlights());
    }

    @AfterEach
    void afterAllTestsActions() {
        webDriver.close();
    }


}
