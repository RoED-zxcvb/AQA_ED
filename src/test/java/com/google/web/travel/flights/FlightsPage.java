package com.google.web.travel.flights;

import org.complexAQA.utils.Properties;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FlightsPage {

    private WebDriver webDriver;

    WebDriverWait wait;

    final private String googleFLightURL = Properties.getPropertyValue("googleHost") + Properties.getPropertyValue("flightsURL");

    private final By fieldFrom = By.cssSelector("[aria-label='Where from?']");

    private final By fieldTo = By.cssSelector("[aria-label='Where to? ']");

    private final By fieldDepartureDate = By.cssSelector("[aria-label='Departure']");

    private final By listOfCountriesToggles = By.xpath("//*[contains(@class, 'dm1EBe')]//button[contains(@class, 'VfPpkd-Bz112c-LgbsSe') and @aria-expanded='false']");

    private final By listOfDepartureAirports = By.xpath("//*[contains(@class, 'DFGgtd')]//*[contains(@class, 'n4HaVc')]");
    private final By listOfArrivalAirports = By.xpath("//*[contains(@class, 'DFGgtd')]//*[contains(@class, 'n4HaVc')]");

    private final By buttonsOfCalendarDates = By.xpath("//*[contains(@class, 'WhDFk') and .//*[contains(@role, 'button')]]");

    private final By tripTypeDropDownListButton = By.className("VfPpkd-aPP78e");

    private final By oneWayButton = By.xpath("//li[contains(@class, 'VfPpkd-OkbHre-SfQLQb-M1Soyc-bN97Pc') and .//span[text()='One way']]");

    private final By buttonDoneForCalendar = By.xpath("//button[contains(@class, 'VfPpkd-LgbsSe-OWXEXe-dgl2Hf') and .//span[text()='Done']]");

    private final By buttonSearch = By.xpath("//button[contains(@class, 'VfPpkd-LgbsSe-OWXEXe-k8QpJ') and .//span[text()='Search']]");

    private final By buttonOfStopsNumberList = By.xpath("//div[contains(@jscontroller, 'aDULAf')][1]");


    private final By buttonCloseForStopsList = By.xpath("//button[contains(@aria-label, 'Close dialog')]");

    private final By listOfFlights = By.xpath("//*[@class='pIav2d']");

    private final By departureAirportIATA = By.xpath("(//*[contains(@class, 'PTuQse')]//span[contains(@jscontroller, 'cNtv4b')])[1]");

    private final By arriveAirportIATA = By.xpath("(//*[contains(@class, 'PTuQse')]//span[contains(@jscontroller, 'cNtv4b')])[2]");

    private final By flightNumberOfStops = By.xpath("//*[contains(@class, 'BbR8Ec')]");


    public FlightsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        PageFactory.initElements(this.webDriver, this);

    }

    public void open() {
        webDriver.navigate().to(googleFLightURL);
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
            listOfCountriesTogglesElements.forEach(i -> i.click());
        } catch (TimeoutException e) {
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
        }
    }

    public void clickToReturnDateField() {
        WebElement fieldDepartureDateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(fieldDepartureDate));
        fieldDepartureDateElement.click();
    }

    //    TODO Подумать как выбирать дату
    public void chooseDepartureAvailableDateByIndex(int dayNumber) {


        clickToDepartureDateField();

        List<WebElement> webElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(buttonsOfCalendarDates));


        wait.until(ExpectedConditions.elementToBeClickable(webElements.get(dayNumber)));
        webElements.get(dayNumber).click();


    }

    // TODO Сделать через Enum
    public void chooseOneWayTrip() {

        WebElement tripTypeDropDownListButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(tripTypeDropDownListButton));

        tripTypeDropDownListButtonElement.click();

        WebElement oneWayButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(oneWayButton));

        oneWayButtonElement.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(oneWayButton));

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
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'm76nmf') and .//*[text()='" + stopNumbers.text + "']]"))).click();
    }

    public enum StopNumbers {
        ANU_NUMBER_OF_STOPS("Any number of stops"),
        NONSTOP_ONLY("Nonstop only"),
        ONE_STOP_OR_FEWER("1 stop or fewer"),
        TWO_STOPS_OR_FEWER("2 stops or fewer");

        private String text;

        StopNumbers(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public void closeList() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(buttonCloseForStopsList)).stream().filter(WebElement::isDisplayed).toList().get(0).click();
    }

    public List<WebElement> getListOfFlights() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(listOfFlights)).stream().filter(WebElement::isDisplayed).toList();
    }

    public String getDepartureAirportIATA(WebElement flight) {
        return flight.findElement(departureAirportIATA).getText();
    }

    public String getArrivalAirportIATA(WebElement flight) {
        return flight.findElement(arriveAirportIATA).getText();
    }

    public String getFlightNumberOfStops(WebElement flight) {
        return flight.findElement(flightNumberOfStops).getText();
    }


    public void sleep3sec() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void printElementsWithBy(By by) {

        sleep3sec();

        List<WebElement> webElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));

        printElementsWithWebElemetsList(wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by)));


    }

    public void printElementsWithWebElemetsList(List<WebElement> webElements) {

        System.out.println("Total elements: " + webElements.size());
        System.out.println("Displayed elements: " + webElements.stream().filter(WebElement::isDisplayed).toList().size());

        webElements.stream().filter(WebElement::isDisplayed).forEach(i -> System.out.println(
                "Text:"
                        + i.getText()
                        + "\n"
                        + "Tag:"
                        + i.getTagName()
                        + "\n"
                        + "Displayed:"
                        + i.isDisplayed()
                        + "\n"
                        + "HTML:"
                        + i.getAttribute("innerHTML")
                        + "\n"
        ));


        sleep3sec();
    }
}






