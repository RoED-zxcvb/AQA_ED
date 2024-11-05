package com.google.steps;

import com.google.pages.GoogleFlightsPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoogleFlightsSteps {

    WebDriver webDriver;

    WebDriverWait wait;

    GoogleFlightsPage googleFlightsPage;

    public GoogleFlightsSteps(WebDriver webDriver) {
        this.webDriver = webDriver;

        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        PageFactory.initElements(this.webDriver, this);

        googleFlightsPage = new GoogleFlightsPage(webDriver);
    }


    public void open() {
        webDriver.navigate().to(GoogleFlightsPage.GOOGLE_FLIGHT_URL);
        webDriver.manage().window().fullscreen();
    }

    public void waitLoadingEnds() {
        try {
            WebElement loadingBarElement = wait.until(ExpectedConditions.visibilityOfElementLocated(googleFlightsPage.getLoadingBar()));
            wait.until(ExpectedConditions.invisibilityOf(loadingBarElement));
        } catch (TimeoutException E) {
            System.out.println("Loading bar was very fast");
        }
    }

    public void setTextForFieldFrom(String textForSearch) {
        WebElement fieldFromElement = wait.until(ExpectedConditions.visibilityOfElementLocated(googleFlightsPage.getFieldFrom()));
        fieldFromElement.clear();
        fieldFromElement.sendKeys(textForSearch);
    }

    public void setTextForFieldTo(String textForSearch) {
        WebElement fieldToElement = wait.until(ExpectedConditions.visibilityOfElementLocated(googleFlightsPage.getFieldTo()));
        fieldToElement.clear();
        fieldToElement.sendKeys(textForSearch);
    }

    public void setDepartureAirportFromListByNumber(int number) {
        expandCitiesInAirportLists();

        List<WebElement> listOfAirportsElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(googleFlightsPage.getListOfDepartureAirports()));

        listOfAirportsElements.get(number).click();
    }

    public void expandCitiesInAirportLists() {
        try {
            List<WebElement> listOfCountriesTogglesElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(googleFlightsPage.getListOfCountriesToggles()));
            listOfCountriesTogglesElements.forEach(WebElement::click);
        } catch (TimeoutException e) {
            System.out.println("No expandable city lists with airports");
        }
    }

    public void setArrivalAirportFromListByNumber(int number) {
        expandCitiesInAirportLists();

        List<WebElement> listOfAirportsElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(googleFlightsPage.getListOfArrivalAirports()));

        listOfAirportsElements.get(number).click();
    }

    public void clickToDepartureDateField() {
        try {
            WebElement fieldDepartureDateElement = wait.until(ExpectedConditions.elementToBeClickable(googleFlightsPage.getFieldDepartureDate()));
            fieldDepartureDateElement.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Departure date selected but again throw exception");
        }
    }

    /// Current day index = 0
    public void chooseAvailableDepartureDateByIndex(int dayNumber) {

        clickToDepartureDateField();

        List<WebElement> webElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(googleFlightsPage.getButtonsOfCalendarDates()));

        wait.until(ExpectedConditions.elementToBeClickable(webElements.get(dayNumber)));

        webElements.get(dayNumber).click();

    }

    public void chooseNumberOfTrips(NumberOfTrips numberOfTrips) {

        WebElement tripTypeDropDownListButtonElement = wait.until(ExpectedConditions.elementToBeClickable(googleFlightsPage.getDropDownListNumberOfTripsButton()));

        tripTypeDropDownListButtonElement.click();

        WebElement numberOfTripsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(@class, 'VfPpkd-OkbHre-SfQLQb-M1Soyc-bN97Pc') and .//span[text()='" + numberOfTrips.getText() + "']]")));

        WebElement oneWayButtonElement = wait.until(ExpectedConditions.visibilityOf(numberOfTripsElement));

        oneWayButtonElement.click();

        wait.until(ExpectedConditions.invisibilityOf(numberOfTripsElement));

    }

    public enum NumberOfTrips {
        ROUND_TRIP("Round trip"),
        ONE_WAY("One way"),
        MULTI_CITY("Multi-city");

        private final String text;

        NumberOfTrips(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public void clickDoneInCalendar() {
        webDriver.findElement(googleFlightsPage.getButtonDoneForCalendar()).click();
    }

    public void clickSearch() {
        webDriver.findElement(googleFlightsPage.getButtonSearch()).click();
    }

    public void openListOfStopsNumber() {
        wait.until(ExpectedConditions.elementToBeClickable(googleFlightsPage.getButtonOfStopsNumberList())).click();
    }

    public void changeStopsNumber(StopNumbers stopNumbers) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'm76nmf') and .//*[text()='" + stopNumbers.getFilterText() + "']]"))).click();


    }

    public enum StopNumbers {
        ANU_NUMBER_OF_STOPS("Any number of stops"),
        NONSTOP_ONLY("Nonstop only"),
        ONE_STOP_OR_FEWER("1 stop or fewer"),
        TWO_STOPS_OR_FEWER("2 stops or fewer");

        private final String filterText;

        StopNumbers(String filterText) {
            this.filterText = filterText;
        }

        public String getFilterText() {
            return filterText;
        }
    }

    public void closeList() {
        wait.until(ExpectedConditions.elementToBeClickable(googleFlightsPage.getButtonCloseForStopsList())).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(googleFlightsPage.getButtonCloseForStopsList()));
        waitLoadingEnds();
    }

    public List<WebElement> getListOfFlights() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(googleFlightsPage.getListOfFlights())).stream().filter(WebElement::isDisplayed).toList();
    }

    public String getDepartureAirportIATA(WebElement flight) {
        return flight.findElement(googleFlightsPage.getDepartureAirportIATA()).getText();
    }

    public void verifyDepartureAirportIATAofFlight(String departureAirportIATA, WebElement flight) {
        assertEquals(departureAirportIATA, getDepartureAirportIATA(flight));
    }

    public void verifyDepartureAirportIATAOfFlights(String departureAirportIATA, List<WebElement> flights) {

        assertAll("Departure IATA codes should match",
                flights.stream()
                        .map(flight -> () -> verifyDepartureAirportIATAofFlight(departureAirportIATA, flight))
        );
    }

    public String getArrivalAirportIATA(WebElement flight) {
        return
                wait.until(ExpectedConditions.visibilityOfElementLocated(googleFlightsPage.getArriveAirportIATA())).getText();
    }


    public void verifyArrivalAirportIATAofFlight(String arrivalAirportIATA, WebElement flight) {
        assertEquals(arrivalAirportIATA, getArrivalAirportIATA(flight));
    }


    public void verifyArrivalAirportIATAOfFlights(String arrivalAirportIATA, List<WebElement> flights) {

        assertAll("Arrival IATA codes should match",
                flights.stream()
                        .map(flight -> () -> verifyArrivalAirportIATAofFlight(arrivalAirportIATA, flight))
        );
    }
}
