package com.EaseMyTrip.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FlightListPage {

	public WebDriver driver;
	public FlightListPage(WebDriver driver) {
		this.driver = driver;
	}
	public WebElement priceDisplayText() {
		return driver.findElement(By.xpath("//input[@id='amount']"));
	}
	public WebElement flightLink() {
		return driver.findElement(By.xpath("//a[text()='Flights']"));
	}
	public WebElement searchLoader() {
		return driver.findElement(By.xpath("//div[text()='Checking best flight deals...']/parent::div/div/img"));
	}
	public WebElement flightListResultDate() {
		return driver.findElement(By.xpath("//input[@id='ddate']"));
	}
	public WebElement flightListResultNumberOfTravellers() {
		return driver.findElement(By.xpath("//span[contains(text(),' Travellers')]/parent::div/div/span[@ng-bind='TtlTrvModSec']"));
	}
	public WebElement flightListResultClass() {
		return driver.findElement(By.xpath("//span[contains(text(),'Class')]/parent::div/select/option[@selected='selected']"));
	}
	public WebElement flightListResultDepartureCity() {
		return driver.findElement(By.xpath("//input[@id='FromSector_show']"));
	}
	public WebElement flightListResultArrivalCity() {
		return driver.findElement(By.xpath("//input[@id='Editbox13_show']"));
	}
	public WebElement homePageLink() {
		return driver.findElement(By.xpath("//a[@title='easemytrip.com']"));
	}
	public WebElement oopsMessageText() {
		return driver.findElement(By.xpath("//span[@id='OopMsg']"));
	}
	public WebElement priceSliderMax() {
		return driver.findElement(By.xpath("//div[@id='slider-range']/span[2]"));
	}
	public WebElement priceSliderMin() {
		return driver.findElement(By.xpath("//div[@id='slider-range']/span[1]"));
	}
	public WebElement bookNowButton() {
		return driver.findElement(By.xpath("(//button[text()='Book Now'])[1]"));
	}	
	public WebElement hourSliderMax() {
		return driver.findElement(By.xpath("//div[@id='slider-range3']/span[2]"));
	}
	public WebElement hourSliderMin() {
		return driver.findElement(By.xpath("//div[@id='slider-range3']/span[1]"));
	}
	public WebElement hourDisplayText() {
		return driver.findElement(By.xpath("//span[@id='time']"));
	}
	public WebElement arrivalHourSliderMax() {
		return driver.findElement(By.xpath("//div[@id='slider-range31']/span[2]"));
	}
	public WebElement arrivalHourSliderMin() {
		return driver.findElement(By.xpath("//div[@id='slider-range31']/span[1]"));
	}
	public WebElement arrivalHourDisplayText() {
		return driver.findElement(By.xpath("//span[@id='timeat']"));
	}
	public List<WebElement> stopsList() {
		return driver.findElements(By.xpath("//div[contains(text(),'Stops')]/parent::div/div/div/label[@class='ctr_cbox']"));
	}
	public WebElement moreAirlinesLink() {
		return driver.findElement(By.xpath("//div[@id='ShowMore']/a[contains(text(),'More')]"));
	}
	public List<WebElement> airlinesCheckboxList() {
		return driver.findElements(By.xpath("//div[contains(text(),'Airlines')]/parent::div/div/div/div/label/input[@class='airline' and @type='checkbox']/following-sibling::span"));
	}
	public List<WebElement> airlinesNameList() {
		return driver.findElements(By.xpath("//div[contains(text(),'Airlines')]/parent::div/div/div/div/label/input[@class='airline' and @type='checkbox']"));
	}
	public List<WebElement> flightResultList() {
		return driver.findElements(By.xpath("//div[contains(@class,'fltResult')]"));
	}
	public List<WebElement> flightResultStopsList() {
		return driver.findElements(By.xpath("//div[contains(@class,'fltResult')]/div/div/div/span[contains(@ng-if,'STP')]"));
	}
	public List<WebElement> flightResultAirlinesList() {
		return driver.findElements(By.xpath("//div[contains(@class,'fltResult')]/div/div/div/div/div/span[contains(@ng-bind,'AC')]"));
	}
	public WebElement airlinesSortLink() {
		return driver.findElement(By.xpath("//a[contains(text(),'Airlines') and contains(@ng-click,'sort')]"));
	}
	public WebElement departSortLink() {
		return driver.findElement(By.xpath("//a[contains(text(),'DEPART') and contains(@ng-click,'sort')]"));
	}
	public WebElement durationSortLink() {
		return driver.findElement(By.xpath("//a[contains(text(),'Duration') and contains(@ng-click,'sort')]"));
	}
	public WebElement arriveSortLink() {
		return driver.findElement(By.xpath("//a[contains(text(),'ARRIVE') and contains(@ng-click,'sort')]"));
	}
	public WebElement priceSortLink() {
		return driver.findElement(By.xpath("//a[contains(text(),'Price') and contains(@ng-click,'sort')]"));
	}
	
	
	
	
	
}
