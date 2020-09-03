package com.EaseMyTrip.businessLayer;

import java.util.Date;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.EaseMyTrip.pageObjects.FlightListPage;
import com.EaseMyTrip.pageObjects.HomePage;
import com.EaseMyTrip.resources.TestBase;
import com.EaseMyTrip.util.TestUtil;

public class Flight extends TestBase {
	HomePage homePage = new HomePage(driver);
	FlightListPage flightListPage = new FlightListPage(driver);
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
				wait.until(ExpectedConditions.visibilityOfAllElements(homePage.selectFromCity()));
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
				wait.until(ExpectedConditions.invisibilityOf(flightListPage.searchLoader()));
				wait.until(ExpectedConditions.visibilityOf(flightListPage.bookNowButton()));
				if (flightListPage.bookNowButton().isDisplayed()) {
					return true;
				}
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean verifySearchResults(String fromCity, String toCity, String dDate, String travellers, String fClass) {
		try {
			wait.until(ExpectedConditions.invisibilityOf(flightListPage.searchLoader()));
			while (price.equalsIgnoreCase("Rs. 0 - Rs.0")) {
				Thread.sleep(1000);
			}
			if (flightListPage.flightListResultDepartureCity().getAttribute("value").equalsIgnoreCase(fromCity)
					&& flightListPage.flightListResultArrivalCity().getAttribute("value").equalsIgnoreCase(toCity)
					&& flightListPage.flightListResultDate().getAttribute("value").equalsIgnoreCase(dDate)
					&& flightListPage.flightListResultNumberOfTravellers().getText().equalsIgnoreCase(travellers)
					&& flightListPage.flightListResultClass().getText().equalsIgnoreCase(fClass)) {
				flightListPage.homePageLink().click();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public static String price = "";

	public boolean setPriceRange(String maxPrice, String minPrice) {
		try {
			wait.until(ExpectedConditions.invisibilityOf(flightListPage.searchLoader()));
			if (flightListPage.priceDisplayText().isDisplayed()) {
				wait.until(ExpectedConditions.invisibilityOf(flightListPage.searchLoader()));
				wait.until(ExpectedConditions.visibilityOf(flightListPage.priceDisplayText()));
				price = flightListPage.priceDisplayText().getAttribute("value");
				while (price.equalsIgnoreCase("Rs. 0 - Rs.0")) {
					Thread.sleep(1000);
				}
				if (TestUtil.setSliderRange(flightListPage.priceDisplayText(), flightListPage.priceSliderMax(),
						flightListPage.priceSliderMin(), maxPrice, minPrice)) {
					return true;
				}
			} else if (flightListPage.oopsMessageText().isDisplayed()) {
				wait.until(ExpectedConditions.invisibilityOf(flightListPage.searchLoader()));
				return false;
			}

			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public static String departureTimeHour = "";

	public boolean setDepartureTimeRange(String maxHour, String minHour) {
		try {
			if (flightListPage.hourDisplayText().isDisplayed()) {
				wait.until(ExpectedConditions.invisibilityOf(flightListPage.searchLoader()));
				wait.until(ExpectedConditions.visibilityOf(flightListPage.hourDisplayText()));
				departureTimeHour = flightListPage.hourDisplayText().getText();
				while (departureTimeHour.equalsIgnoreCase(" Hr 0 - Hr 0")) {
					Thread.sleep(1000);
				}
				if (TestUtil.setSliderRange(flightListPage.hourDisplayText(), flightListPage.hourSliderMax(),
						flightListPage.hourSliderMin(), maxHour, minHour)) {
					return true;
				}
			} else if (flightListPage.oopsMessageText().isDisplayed()) {
				wait.until(ExpectedConditions.invisibilityOf(flightListPage.searchLoader()));
				return false;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public static String arrivalTimeHour = "";

	public boolean setArrivalTimeRange(String maxHour, String minHour) {
		try {
			if (flightListPage.hourDisplayText().isDisplayed()) {
				wait.until(ExpectedConditions.invisibilityOf(flightListPage.searchLoader()));
				wait.until(ExpectedConditions.visibilityOf(flightListPage.arrivalHourDisplayText()));
				arrivalTimeHour = flightListPage.arrivalHourDisplayText().getText();
				while (arrivalTimeHour.equalsIgnoreCase(" Hr 0 - Hr 0")) {
					Thread.sleep(1000);
				}
				if (TestUtil.setSliderRange(flightListPage.arrivalHourDisplayText(),
						flightListPage.arrivalHourSliderMax(), flightListPage.arrivalHourSliderMin(), maxHour,
						minHour)) {
					return true;
				}
			} else if (flightListPage.oopsMessageText().isDisplayed()) {
				wait.until(ExpectedConditions.invisibilityOf(flightListPage.searchLoader()));
				return false;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean setStops(String stop) {
		try {
			if (TestUtil.selectFromListofElements(flightListPage.stopsList(), stop)) {
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean setAirline(String airline) {
		try {
			for (int i = 0; i < flightListPage.airlinesCheckboxList().size(); i++) {
				if (flightListPage.airlinesNameList().get(i).getText().equalsIgnoreCase(airline)) {
					flightListPage.airlinesCheckboxList().get(i).click();
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

	public boolean getPriceSortedFlightDetails(String sortingOrder) {
		try {

			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean getArrivalTimeSortedFlightDetails(String sortingOrder) {
		try {

			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean getDepartureTimeSortedFlightDetails(String sortingOrder) {
		try {

			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean getDurationSortedFlightDetails(String sortingOrder) {
		try {

			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean getAirlinesSortedFlightDetails(String sortingOrder) {
		try {

			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
}
