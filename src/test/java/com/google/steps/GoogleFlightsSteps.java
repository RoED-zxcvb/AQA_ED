package com.google.steps;

import com.google.config.WebDriverManager;
import com.google.pages.GoogleFlightsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoogleFlightsSteps {

    private final WebDriver webDriver;
    private final WebDriverWait wait;
    private final GoogleFlightsPage googleFlightsPage;

    public GoogleFlightsSteps() {
        this.webDriver = WebDriverManager.getDriver();
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        googleFlightsPage = new GoogleFlightsPage(webDriver);
    }

    @Given("Google flight page open")
    public void open() {
        webDriver.navigate().to(GoogleFlightsPage.GOOGLE_FLIGHT_URL);
        webDriver.manage().window().fullscreen();
    }

    @When("I waiting for the loading bar to disappear")
    public void waitLoadingEnds() {
        try {
            WebElement loadingBarElement = wait.until(ExpectedConditions.visibilityOfElementLocated(googleFlightsPage.getLoadingBar()));
            wait.until(ExpectedConditions.invisibilityOf(loadingBarElement));
        } catch (TimeoutException E) {
            System.out.println("Loading bar was very fast");
        }
    }

    @When("I enter departure airport {string}")
    public void enterDepartureAirport(String textForSearch) {
        WebElement fieldFromElement = wait.until(ExpectedConditions.visibilityOfElementLocated(googleFlightsPage.getFieldFrom()));
        fieldFromElement.clear();
        fieldFromElement.sendKeys(textForSearch);
    }

    @When("I enter arrival airport {string}")
    public void enterArrivalAirport(String textForSearch) {
        WebElement fieldToElement = wait.until(ExpectedConditions.visibilityOfElementLocated(googleFlightsPage.getFieldTo()));
        fieldToElement.clear();
        fieldToElement.sendKeys(textForSearch);
    }

    @When("I expand airports lists of cities")
    public void expandCitiesInAirportLists() {
        try {
            List<WebElement> listOfCountriesTogglesElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(googleFlightsPage.getListOfCountriesToggles()));
            listOfCountriesTogglesElements.forEach(WebElement::click);
        } catch (TimeoutException e) {
            System.out.println("No expandable city lists with airports");
        }
    }

    @When("I select departure airport by index {int}")
    public void selectDepartureAirportByIndex(int number) {
        expandCitiesInAirportLists();

        List<WebElement> listOfAirportsElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(googleFlightsPage.getListOfDepartureAirports()));

        listOfAirportsElements.get(number).click();
    }


    @When("I select arrival airport by index {int}")
    public void selectArrivalAirportByIndex(int number) {
        expandCitiesInAirportLists();

        List<WebElement> listOfAirportsElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(googleFlightsPage.getListOfArrivalAirports()));

        listOfAirportsElements.get(number).click();
    }

    @When("I open departure calendar")
    public void clickToDepartureDateField() {
        try {
            WebElement fieldDepartureDateElement = wait.until(ExpectedConditions.elementToBeClickable(googleFlightsPage.getFieldDepartureDate()));
            fieldDepartureDateElement.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Departure date selected but again throw exception");
        }
    }

    /// Current day index = 0
    @When("I select departure date in calendar by number of available day {int}")
    public void chooseAvailableDepartureDateByIndex(int dayNumber) {

        clickToDepartureDateField();

        List<WebElement> webElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(googleFlightsPage.getButtonsOfCalendarDates()));

        wait.until(ExpectedConditions.elementToBeClickable(webElements.get(dayNumber)));

        webElements.get(dayNumber).click();

    }


    public enum NumberOfTrips {
        ROUND_TRIP("Round trip"),
        ONE_WAY("One way"),
        MULTI_CITY("Multi-city");

        private final String text;

        NumberOfTrips(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

        public static NumberOfTrips fromString(String text) {
            for (NumberOfTrips trip : NumberOfTrips.values()) {
                if (trip.text.equalsIgnoreCase(text)) {
                    return trip;
                }
            }
            throw new IllegalArgumentException("No enum constant for value: " + text);
        }
    }

    @When("I select the trip type {string}")
    public void selectTripType(String tripTypeText) {
        NumberOfTrips tripType = NumberOfTrips.fromString(tripTypeText);
        changeNumberOfTrips(tripType);
    }

    public void changeNumberOfTrips(NumberOfTrips numberOfTrips) {
        WebElement tripTypeDropDownListButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(googleFlightsPage.getDropDownListNumberOfTripsButton()));


        moveToElement(tripTypeDropDownListButtonElement);

        tripTypeDropDownListButtonElement.click();

        WebElement numberOfTripsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(@class, 'VfPpkd-OkbHre-SfQLQb-M1Soyc-bN97Pc') and .//span[text()='" + numberOfTrips.toString() + "']]")));

        WebElement oneWayButtonElement = wait.until(ExpectedConditions.visibilityOf(numberOfTripsElement));

        oneWayButtonElement.click();

        wait.until(ExpectedConditions.invisibilityOf(numberOfTripsElement));

    }

    public void moveToElement(WebElement element) {
        Actions actions = new Actions(webDriver);
        actions.moveToElement(element).perform();
    }

    @When("I click calendar button Done")
    public void clickDoneInCalendar() {
        webDriver.findElement(googleFlightsPage.getButtonDoneForCalendar()).click();
    }

    @When("I click button Search")
    public void clickButtonSearch() {
        webDriver.findElement(googleFlightsPage.getButtonSearch()).click();
    }

    @When("I open stops filter")
    public void openStopsFilterList() {
        wait.until(ExpectedConditions.elementToBeClickable(googleFlightsPage.getButtonOfStopsNumberList())).click();
    }

    public enum FiltersOfStops {
        ANU_NUMBER_OF_STOPS("Any number of stops"),
        NONSTOP_ONLY("Nonstop only"),
        ONE_STOP_OR_FEWER("1 stop or fewer"),
        TWO_STOPS_OR_FEWER("2 stops or fewer");

        private final String filterText;

        FiltersOfStops(String filterText) {
            this.filterText = filterText;
        }

        @Override
        public String toString() {
            return filterText;
        }

        public static FiltersOfStops fromString(String text) {
            for (FiltersOfStops numberOfStops : FiltersOfStops.values()) {
                if (numberOfStops.filterText.equalsIgnoreCase(text)) {
                    return numberOfStops;
                }
            }
            throw new IllegalArgumentException("No enum constant for value: " + text);
        }
    }

    @When("I select the stops filter {string}")
    public void selectFilterOfStops(String filterText) {
        FiltersOfStops filtersOfStops = FiltersOfStops.fromString(filterText);
        changeStopsFilter(filtersOfStops);
    }

    public void changeStopsFilter(FiltersOfStops filtersOfStops) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'm76nmf') and .//*[text()='" + filtersOfStops.toString() + "']]"))).click();
    }

    @When("I close filter list")
    public void closeFilter() {
        wait.until(ExpectedConditions.elementToBeClickable(googleFlightsPage.getButtonCloseForStopsList())).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(googleFlightsPage.getButtonCloseForStopsList()));
        waitLoadingEnds();
    }

    @When("I get list of flights")
    public List<WebElement> getListOfFlights() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(googleFlightsPage.getListOfFlights())).stream().filter(WebElement::isDisplayed).toList();
    }

    public String getDepartureAirportIATA(WebElement flight) {
        return flight.findElement(googleFlightsPage.getDepartureAirportIATA()).getText();
    }

    public void assertDepartureAirportIATAofFlight(String departureAirportIATA, WebElement flight) {
        assertEquals(departureAirportIATA, getDepartureAirportIATA(flight));
    }

    public void assertDepartureAirportIATAOfFlights(String departureAirportIATA, List<WebElement> flights) {
        assertAll("Departure IATA codes should match",
                flights.stream()
                        .map(flight -> () -> assertDepartureAirportIATAofFlight(departureAirportIATA, flight))
        );
    }

    @Then("I check all departure airports IATA of flights equals {string}")
    public void assertDepartureAirportIATAOfFlightsEquals (String departureAirportIATA){
        assertDepartureAirportIATAOfFlights(departureAirportIATA,getListOfFlights());
    }


    public String getArrivalAirportIATA(WebElement flight) {
        return flight.findElement(googleFlightsPage.getArriveAirportIATA()).getText();
    }


    public void assertArrivalAirportIATAofFlight(String arrivalAirportIATA, WebElement flight) {
        assertEquals(arrivalAirportIATA, getArrivalAirportIATA(flight));
    }


    public void assertArrivalAirportIATAOfFlights(String arrivalAirportIATA, List<WebElement> flights) {
        assertAll("Arrival IATA codes should match",
                flights.stream()
                        .map(flight -> () -> assertArrivalAirportIATAofFlight(arrivalAirportIATA, flight))
        );
    }

    @Then("I check all arrival airports IATA of flights equals {string}")
    public void assertArrivalAirportIATAOfFlightsEquals (String arrivalAirportIATA){
        assertArrivalAirportIATAOfFlights(arrivalAirportIATA,getListOfFlights());
    }
}
