package com.EaseMyTrip.businessLayer;

import java.util.Date;

import org.openqa.selenium.support.ui.WebDriverWait;

import com.EaseMyTrip.pageObjects.HomePage;
import com.EaseMyTrip.resources.TestBase;
import com.EaseMyTrip.util.TestUtil;

public class Flight extends TestBase {
	HomePage homePage = new HomePage(driver);
	String url = prop.getProperty("URL");
	WebDriverWait wait = new WebDriverWait(driver, 60);

	public boolean selectTripType(String tripType) {

		try {
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
				homePage.fromLocationInputField().sendKeys(city);
				if (TestUtil.selectFromListofElements(homePage.selectFromCity(), city)) {
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
				homePage.toLocationInputField().sendKeys(city);
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
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean selectNumberOfAdults(int number) {
		try {
			homePage.numberOfTravelersSelect().click();
			while (!homePage.numberOfTravelersDisplayText().getAttribute("value")
					.equalsIgnoreCase(String.valueOf(number))) {
				homePage.numberOfTravelersAdultSelect().click();
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
			if (homePage.classSelect().isDisplayed()) {
				homePage.classSelect().click();
				if (TestUtil.selectFromListofElements(homePage.classSelectfromList(), flightClass)) {
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
