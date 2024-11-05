package com.google.pages;

import org.complexAQA.utils.Properties;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

public class GoogleFlightsPage {

    public GoogleFlightsPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public static final String GOOGLE_FLIGHT_URL = Properties.getPropertyValue("googleHost") + Properties.getPropertyValue("flightsURL");

    private final  By fieldFrom = By.cssSelector("[aria-label='Where from?']");

    private final By fieldTo = By.cssSelector("[aria-label='Where to? ']");

    private final By fieldDepartureDate = By.cssSelector("[aria-label='Departure']");

    private final By listOfCountriesToggles = By.xpath("//*[contains(@class, 'dm1EBe')]//button[contains(@class, 'VfPpkd-Bz112c-LgbsSe') and @aria-expanded='false']");

    private final By listOfDepartureAirports = By.cssSelector(".DFGgtd .n4HaVc");

    private final By listOfArrivalAirports = By.cssSelector(".DFGgtd .n4HaVc");

    private final By buttonsOfCalendarDates = By.cssSelector(".WhDFk[aria-hidden='false'] [role='button']");

    private final By dropDownListNumberOfTripsButton = By.className("VfPpkd-aPP78e");

    private final By buttonDoneForCalendar = By.xpath("//button[contains(@class, 'VfPpkd-LgbsSe-OWXEXe-dgl2Hf') and .//span[text()='Done']]");

    private final By buttonSearch = By.xpath("//button[contains(@class, 'VfPpkd-LgbsSe-OWXEXe-k8QpJ') and .//span[text()='Search']]");

    private final By buttonOfStopsNumberList = By.xpath("//div[contains(@jscontroller, 'aDULAf')][1]");


    private final By buttonCloseForStopsList = By.xpath("//button[contains(@aria-label, 'Close dialog')]");

    private final By listOfFlights = By.className("pIav2d");

    private final By departureAirportIATA = By.xpath("(//*[contains(@class, 'PTuQse')]//span[contains(@jscontroller, 'cNtv4b')])[1]");

    private final By arriveAirportIATA = By.xpath("(//*[contains(@class, 'PTuQse')]//span[contains(@jscontroller, 'cNtv4b')])[2]");

    private final By loadingBar = By.xpath("//button[contains(@aria-label, 'Close dialog')]");


    public By getFieldFrom() {
        return fieldFrom;
    }

    public By getFieldTo() {
        return fieldTo;
    }

    public By getFieldDepartureDate() {
        return fieldDepartureDate;
    }

    public By getListOfCountriesToggles() {
        return listOfCountriesToggles;
    }

    public By getListOfDepartureAirports() {
        return listOfDepartureAirports;
    }

    public By getListOfArrivalAirports() {
        return listOfArrivalAirports;
    }

    public By getButtonsOfCalendarDates() {
        return buttonsOfCalendarDates;
    }

    public By getDropDownListNumberOfTripsButton() {
        return dropDownListNumberOfTripsButton;
    }

    public By getButtonDoneForCalendar() {
        return buttonDoneForCalendar;
    }

    public By getButtonSearch() {
        return buttonSearch;
    }

    public By getButtonOfStopsNumberList() {
        return buttonOfStopsNumberList;
    }

    public By getButtonCloseForStopsList() {
        return buttonCloseForStopsList;
    }

    public By getListOfFlights() {
        return listOfFlights;
    }

    public By getDepartureAirportIATA() {
        return departureAirportIATA;
    }

    public By getArriveAirportIATA() {
        return arriveAirportIATA;
    }

    public By getLoadingBar() {
        return loadingBar;
    }
}






