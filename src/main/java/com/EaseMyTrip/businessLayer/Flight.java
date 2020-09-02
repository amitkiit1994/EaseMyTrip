package com.EaseMyTrip.businessLayer;

import java.util.Date;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.EaseMyTrip.pageObjects.HomePage;
import com.EaseMyTrip.resources.TestBase;
import com.EaseMyTrip.util.TestUtil;

public class Flight extends TestBase {
	HomePage homePage = new HomePage(driver);
	String url = prop.getProperty("URL");
	WebDriverWait wait = new WebDriverWait(driver, 10);

	public boolean selectTripType(String tripType) {

		try {
			wait.until(ExpectedConditions.visibilityOfAllElements(homePage.tripTypeSwitch()));
			if (TestUtil.selectFromListofElements(homePage.tripTypeSwitch(), tripType)) {
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean selectDepartureCity(String city) {
		try {
			if (homePage.fromLocationInputField().isDisplayed()) {
				homePage.fromLocationInputField().clear();
				homePage.fromLocationInputField().click();
				homePage.fromLocationInputField().sendKeys(city);
				wait.until(ExpectedConditions.visibilityOfAllElements(homePage.selectToCity()));
				if (TestUtil.selectFromListofElements(homePage.selectToCity(), city)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean selectArrivalCity(String city) {
		try {
			if (homePage.toLocationInputField().isDisplayed()) {
				homePage.toLocationInputField().clear();
				homePage.toLocationInputField().click();
				homePage.toLocationInputField().sendKeys(city);
				wait.until(ExpectedConditions.visibilityOfAllElements(homePage.selectToCity()));
				if (TestUtil.selectFromListofElements(homePage.selectToCity(), city)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean selectDepartureDate(Date date) {
		try {
			if (homePage.departureDatePicker().isDisplayed()) {
				homePage.departureDatePicker().click();
				TestUtil.dateSelector(homePage.monthSelectRightArrow(), homePage.monthList(), homePage.dateList(),
						date);
				return true;
			}
			return false;
		} catch (StaleElementReferenceException e) {
			// TODO: handle exception
			try {
				TestUtil.dateSelector(homePage.monthSelectRightArrow(), homePage.monthList(), homePage.dateList(),
						date);
				return true;
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean selectNumberOfAdults(String number) {
		try {

			homePage.numberOfTravelersSelect().click();
			Double actualValue = Double.parseDouble(homePage.numberOfTravelersDisplayText().getAttribute("value"));
//			System.out.println("[DEBUG] Number of Travellers from Excel: " + number);
//			System.out.println("[DEBUG] Number of Travellers from UI: "+actualValue);
			if (actualValue != Double.parseDouble(number)) {
				while (actualValue != Double.parseDouble(number)) {
					homePage.numberOfTravelersAdultSelect().click();
				}
			}
			if (homePage.numberOfTravelersDoneButton().isDisplayed()) {
				homePage.numberOfTravelersDoneButton().click();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean selectClass(String flightClass) {
		try {
			wait.until(ExpectedConditions.visibilityOf(homePage.classSelect()));
			if (homePage.classSelect().isDisplayed()) {
				homePage.classSelect().click();
				if (TestUtil.selectFromListofElements(homePage.classSelectfromList(), flightClass)) {
					wait.until(ExpectedConditions.visibilityOf(homePage.classSelectDoneButton()));
					homePage.classSelectDoneButton().click();
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean submitSelection() {
		try {
			if (homePage.searchButton().isDisplayed()) {
				homePage.searchButton().click();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
}
