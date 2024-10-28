package com.google.web.travel.flights;

import org.complexAQA.utils.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

public class FlightsPage {

    final private WebDriver webDriver;

    final private String googleFLightURL = Properties.getPropertyValue("googleHost") + Properties.getPropertyValue("flightsURL");

    @FindBy(css = "[aria-label='Where from?']")
    private WebElement fieldFrom;

    @FindBy(css = "[aria-label='Where to? ']")
    private WebElement fieldTo;

    @FindBy(css = "[aria-label='Departure']")
    private WebElement fieldDepartureDate;

    @FindBy(css = "[aria-label='Return']")
    private WebElement fieldReturnDate;

    @FindBy(className = "VfPpkd")
    private WebElement buttonExplore;

    @FindBy(className = "n4HaVc")
    private List<WebElement> airports;

    public FlightsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        PageFactory.initElements(this.webDriver, this);
    }

    public void open() {
        webDriver.navigate().to(googleFLightURL);
    }

    public void setTextForFieldFrom(String textForSearch) {
        fieldFrom.clear();
        fieldFrom.sendKeys(textForSearch);
    }

    public void setTextForFieldTo(String textForSearch) {
        fieldTo.clear();
        fieldTo.sendKeys(textForSearch);
    }

    public void getAirportFromListByNumber(int number) {
        airports.get(number).click();
    }

    public void setTextForFieldDepartureDate(String textForSearch) {
        fieldDepartureDate.clear();
        fieldDepartureDate.sendKeys(textForSearch);
    }

    public void clickExplore() {
        buttonExplore.click();
    }
}





