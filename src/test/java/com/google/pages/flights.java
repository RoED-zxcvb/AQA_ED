package com.google.pages;

import org.complexAQA.utils.Properties;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class flights {

    private final WebDriver webDriver;

    WebDriverWait wait;

    final private String googleFlightURL = Properties.getPropertyValue("googleHost") + Properties.getPropertyValue("flightsURL");

    private final By fieldFrom = By.cssSelector("[aria-label='Where from?']");

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

    public flights(WebDriver webDriver) {
        this.webDriver = webDriver;
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        PageFactory.initElements(this.webDriver, this);
    }

    public void open() {
        webDriver.navigate().to(googleFlightURL);
        webDriver.manage().window().fullscreen();
    }

    public void waitLoadingEnds(){
        try{
            WebElement loadingBarElement = wait.until(ExpectedConditions.visibilityOfElementLocated(loadingBar));
            wait.until(ExpectedConditions.invisibilityOf(loadingBarElement));
        }
        catch (TimeoutException E){
            System.out.println("Loading bar was very fast");
        }
    }

    public void setTextForFieldFrom(String textForSearch) {
        WebElement fieldFromElement = wait.until(ExpectedConditions.visibilityOfElementLocated(fieldFrom));
        fieldFromElement.clear();
        fieldFromElement.sendKeys(textForSearch);
    }

    public void setTextForFieldTo(String textForSearch) {
        WebElement fieldToElement = wait.until(ExpectedConditions.visibilityOfElementLocated(fieldTo));
        fieldToElement.clear();
        fieldToElement.sendKeys(textForSearch);
    }

    public void setDepartureAirportFromListByNumber(int number) {
        expandCitiesInAirportLists();

        List<WebElement> listOfAirportsElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(listOfDepartureAirports));

        listOfAirportsElements.get(number).click();
    }

    public void expandCitiesInAirportLists() {
        try {
            List<WebElement> listOfCountriesTogglesElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(listOfCountriesToggles));
            listOfCountriesTogglesElements.forEach(WebElement::click);
        } catch (TimeoutException e) {
            System.out.println("No expandable city lists with airports");
        }
    }

    public void setArrivalAirportFromListByNumber(int number) {
        expandCitiesInAirportLists();

        List<WebElement> listOfAirportsElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(listOfArrivalAirports));

        listOfAirportsElements.get(number).click();
    }

    public void clickToDepartureDateField() {
        try {
            WebElement fieldDepartureDateElement = wait.until(ExpectedConditions.elementToBeClickable(fieldDepartureDate));
            fieldDepartureDateElement.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Departure date selected but again throw exception");
        }
    }

    /// Current day index = 0
    public void chooseAvailableDepartureDateByIndex(int dayNumber) {

        clickToDepartureDateField();

        List<WebElement> webElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(buttonsOfCalendarDates));

        wait.until(ExpectedConditions.elementToBeClickable(webElements.get(dayNumber)));

        webElements.get(dayNumber).click();

    }

    public void chooseNumberOfTrips(NumberOfTrips numberOfTrips) {

        WebElement tripTypeDropDownListButtonElement = wait.until(ExpectedConditions.elementToBeClickable(dropDownListNumberOfTripsButton));

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
        webDriver.findElement(buttonDoneForCalendar).click();
    }

    public void clickSearch() {
        webDriver.findElement(buttonSearch).click();
    }

    public void openListOfStopsNumber() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonOfStopsNumberList)).click();
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
      wait.until(ExpectedConditions.elementToBeClickable(buttonCloseForStopsList)).click();
      wait.until(ExpectedConditions.invisibilityOfElementLocated(buttonCloseForStopsList));
      waitLoadingEnds();
    }

    public List<WebElement> getListOfFlights() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(listOfFlights)).stream().filter(WebElement::isDisplayed).toList();
    }

    public String getDepartureAirportIATA(WebElement flight) {
        return flight.findElement(departureAirportIATA).getText();
    }

    public String getArrivalAirportIATA(WebElement flight) {
        return
//                flight.findElement(arriveAirportIATA).getText();
        wait.until(ExpectedConditions.visibilityOfElementLocated(arriveAirportIATA)).getText();
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

    public void verifyArrivalAirportIATAofFlight(String arrivalAirportIATA, WebElement flight) {
        assertEquals(arrivalAirportIATA, getArrivalAirportIATA(flight));
    }

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3);
        assertAll("Check if numbers are positive",
                numbers.stream()
                        .map(number -> () -> assertTrue(number > 0))
        );

    }

    public void verifyArrivalAirportIATAOfFlights(String arrivalAirportIATA, List<WebElement> flights) {

        assertAll("Arrival IATA codes should match",
                flights.stream()
                        .map(flight -> () -> verifyArrivalAirportIATAofFlight(arrivalAirportIATA, flight))
        );
    }


}






