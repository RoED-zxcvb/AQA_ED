package com.google.web.travel.flights;

import org.complexAQA.utils.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FlightsPage {

    private WebDriver webDriver;

    WebDriverWait wait;

    final private String googleFLightURL = Properties.getPropertyValue("googleHost") + Properties.getPropertyValue("flightsURL");

    private final By fieldFrom = By.cssSelector("[aria-label='Where from?']");
    private final By fieldTo = By.cssSelector("[aria-label='Where to? ']");
    private final By fieldDepartureDate = By.cssSelector("[aria-label='Departure']");
    private final By fieldReturnDate = By.cssSelector("[aria-label='Return']");
    private final By buttonExplore = By.className("VfPpkd");

    private final By listOfCountriesToggles = By.xpath("//*[contains(@class, 'dm1EBe')]//button[contains(@class, 'VfPpkd-Bz112c-LgbsSe') and @aria-expanded='false']");

    private final By listOfDepartureAirports = By.xpath("//*[contains(@class, 'DFGgtd')]//*[contains(@class, 'n4HaVc')]");
    private final By listOfArrivalAirports = By.xpath("//*[contains(@class, 'DFGgtd')]//*[contains(@class, 'n4HaVc')]");
    private final By buttonsOfDates = By.className("KQqAEc");
    private final By tripTypeDropDownListButton = By.className("VfPpkd-aPP78e");
    private final By oneWayButton = By.xpath("//li[contains(@class, 'VfPpkd-OkbHre-SfQLQb-M1Soyc-bN97Pc') and .//span[text()='One way']]");

    public FlightsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
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


    public void chooseDepartureDateByIndex(int dayNumber) {

        WebElement fieldDepartureDateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(fieldDepartureDate));


        fieldDepartureDateElement.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<WebElement> webElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("WhDFk")));
        webElements.get(dayNumber).click();
    }


    public void ChooseOneWayTrip() {
        WebElement tripTypeDropDownListButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(tripTypeDropDownListButton));
        WebElement oneWayButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(oneWayButton));

        tripTypeDropDownListButtonElement.click();

        oneWayButtonElement.click();
    }


    public void sleep3sec() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void printElements(List<WebElement> webElements) {

        sleep3sec();


        System.out.println(webElements.size());
        webElements.stream().filter(WebElement::isDisplayed).forEach(i -> System.out.println(
                "Text:"
                        + i.getText()
                        + "\n"
                        + "HTML:"
                        + i.getAttribute("innerHTML")
        ));


        sleep3sec();


    }
}





