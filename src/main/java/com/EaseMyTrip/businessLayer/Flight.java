package com.EaseMyTrip.businessLayer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.EaseMyTrip.pageObjects.FlightListPage;
import com.EaseMyTrip.pageObjects.HomePage;
import com.EaseMyTrip.resources.TestBase;
import com.EaseMyTrip.util.TestUtil;

public class Flight extends TestBase {
	private static final Logger logger = LogManager.getLogger(Flight.class);
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

	public boolean searchFlight(String tripType, String departureCity, String arrivalCity, Date departureDate,
			Date arrivalDate, String numberOfTravellers, String flightClass) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			if (tripType != null) {
				if (selectTripType(tripType)) {
					logger.info("Trip Type selected");
				} else {
					logger.error("Trip type selection failed due to invalid inputs or click exception");
					return false;
				}
			} else {
				logger.error("Null input");
				return false;
			}
			if (!departureCity.equalsIgnoreCase(arrivalCity)) {
				if (departureCity != null) {
					if (selectDepartureCity(departureCity)) {
						logger.info("Departure City selected");
					} else {
						logger.error("Departure City selection failed");
						return false;
					}
				}
				if (arrivalCity != null) {
					if (selectArrivalCity(arrivalCity)) {
						logger.info("Arrival city selected");
					} else {
						logger.error("Arrival city selection failed");
						return false;
					}
				}
			} else {
				logger.error("Departure city and Arrival city are same, Please give valid inputs");
				return false;
			}
			Date dateobj = new Date();
			if (departureDate.after(dateobj)) {
				if (departureDate != null) {
					if (selectDepartureDate(departureDate)) {
						logger.info("Departure date selected");
					} else {
						logger.error("Departure date selection failed");
						return false;
					}
				}
			} else {
				logger.error("Departure date: " + format.format(departureDate) + " is less than current date: "
						+ format.format(dateobj));
				return false;
			}

			if (Double.parseDouble(numberOfTravellers) > 0) {
				if (selectNumberOfAdults(numberOfTravellers)) {
					logger.info("Number of travellers selected");
				} else {
					logger.error("Number of travellers selection failed");
					return false;
				}
			} else {
				logger.error("Invalid input for number of Travellers, cannot be less than or equal to 0");
				return false;
			}
			if (flightClass != null) {
				if (selectClass(flightClass)) {
					logger.info("Flight Class selected");
				} else {
					logger.error("Flight Class selection failed");
					return false;
				}
			} else {
				logger.error("Invalid input for Flight Class");
				return false;
			}

			if (submitSelection()) {
				logger.info("Flight Details submitted for search criteria");
			} else {
				logger.error("Flight Details submission failed");
				return false;
			}

			if (verifySearchResults(departureCity, arrivalCity, format.format(departureDate), numberOfTravellers,
					flightClass)) {
				logger.info("Flight Submissions Details verified successfully");
			} else {
				logger.error("Flight Submissions verification failed");
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("Exception Occured while Searching Flight");
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
					if (flightListPage.flightResultList().size() != 0) {
						for (WebElement price : flightListPage.flightResultList()) {
							if ((Integer.parseInt(minPrice)) <= (Integer.parseInt(price.getAttribute("price")))
									&& (Integer.parseInt(maxPrice)) <= (Integer
											.parseInt(price.getAttribute("price")))) {
								continue;
							} else {
								return false;
							}
						}
					} else {
						logger.error("No Flights found with given filter for Price Range");
					}
				}
			} else if (flightListPage.oopsMessageText().isDisplayed()) {
				wait.until(ExpectedConditions.invisibilityOf(flightListPage.searchLoader()));
				return false;
			}

			return true;
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
					if (flightListPage.flightResultList().size() != 0) {
						for (WebElement hour : flightListPage.flightResultList()) {
							String deptm = hour.getAttribute("deptm").replace(":", ".");
							if ((Double.parseDouble(minHour)) <= (Double.parseDouble(deptm))
									&& (Double.parseDouble(maxHour)) <= (Double.parseDouble(deptm))) {
								continue;
							} else {
								return false;
							}
						}
					} else {
						logger.error("No Flights found with given filter for Departure Time Range");
					}
				}
			} else if (flightListPage.oopsMessageText().isDisplayed()) {
				wait.until(ExpectedConditions.invisibilityOf(flightListPage.searchLoader()));
				return false;
			}
			return true;
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
					if (flightListPage.flightResultList().size() != 0) {
						for (WebElement hour : flightListPage.flightResultList()) {

							String arrtm = hour.getAttribute("arrtm").replace(":", ".");
							if ((Double.parseDouble(minHour)) <= (Double.parseDouble(arrtm))
									&& (Double.parseDouble(maxHour)) <= (Double.parseDouble(arrtm))) {
								continue;
							} else {
								return false;
							}
						}
					} else {
						logger.error("No Flights found with given filter for Arrival Time Range");
					}
				}
			} else if (flightListPage.oopsMessageText().isDisplayed()) {
				wait.until(ExpectedConditions.invisibilityOf(flightListPage.searchLoader()));
				return false;
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean setStops(String stop) {
		try {
			if (TestUtil.selectFromListofElements(flightListPage.stopsList(), stop)) {
				if (flightListPage.flightResultStopsList().size() != 0) {
					for (WebElement stops : flightListPage.flightResultStopsList()) {
						if (stops.getText().replace("-", "").contains(stop)) {
							continue;
						} else {
							return false;
						}
					}
				} else {
					logger.error("No Flights found with given filter for Stops");
				}
			}
			return true;
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
					if (flightListPage.flightResultAirlinesList().size() != 0) {
						for (WebElement airlines : flightListPage.flightResultAirlinesList()) {
							if (airlines.getText().contains(airline)) {
								continue;
							} else {
								return false;
							}
						}
					} else {
						logger.error("No Flights found with given filter for Airlines");
					}
				}
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean setFilter(String maxPrice, String minPrice, String maxDTime, String minDTime, String maxATime,
			String minATime, String stops, String airlines) {
		try {
			if (setPriceRange(maxPrice, minPrice)) {
				logger.info("Price range set successfully");
			} else {
				logger.error("Price range settings failed");
				return false;
			}
			if (setDepartureTimeRange(maxDTime, minDTime)) {
				logger.info("Departure time range set successfully");
			} else {
				logger.error("Departure time range range settings failed");
				return false;
			}
			if (setArrivalTimeRange(maxATime, minATime)) {
				logger.info("Arrival time range set successfully");
			} else {
				logger.error("Arrival time range range settings failed");
				return false;
			}
			if (setStops(stops)) {
				logger.info("Number of stops selected successfully");
			} else {
				logger.error("Stops selection failed");
				return false;
			}
			if (setAirline(airlines)) {
				logger.info("Airlines selected successfully");
			} else {
				logger.error("Airlines selection failed");
				return false;
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean getPriceSortedFlightDetails(String sortingOrder) {
		try {
			ArrayList<String> listOfPrices = new ArrayList<String>();
			if (TestUtil.sort(flightListPage.priceSortLink(), sortingOrder)) {
				for (int i = 0; i < flightListPage.flightResultList().size(); i++) {
					listOfPrices.add(flightListPage.flightResultList().get(i).getAttribute("price"));
				}
				Collections.sort(listOfPrices);
				if (sortingOrder.toLowerCase().contains("asc")) {
					if (listOfPrices.get(0)
							.equalsIgnoreCase(flightListPage.flightResultList().get(0).getAttribute("price"))) {
						return true;
					}
				}
				if (sortingOrder.toLowerCase().contains("desc")) {
					if (listOfPrices.get(listOfPrices.size() - 1).equalsIgnoreCase(
							flightListPage.flightResultList().get(listOfPrices.size() - 1).getAttribute("price"))) {
						return true;
					}
				}
				return false;
			} else {
				logger.error("Sorting with Price failed for " + sortingOrder);
			}

			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean getArrivalTimeSortedFlightDetails(String sortingOrder) {
		try {
			ArrayList<String> listOfTime = new ArrayList<String>();
			if (TestUtil.sort(flightListPage.arriveSortLink(), sortingOrder)) {
				for (int i = 0; i < flightListPage.flightResultList().size(); i++) {
					listOfTime.add(flightListPage.flightResultList().get(i).getAttribute("arrtm"));
				}
				Collections.sort(listOfTime);
				if (sortingOrder.toLowerCase().contains("asc")) {
					if (listOfTime.get(0)
							.equalsIgnoreCase(flightListPage.flightResultList().get(0).getAttribute("price"))) {
						return true;
					}
				}
				if (sortingOrder.toLowerCase().contains("desc")) {
					if (listOfTime.get(listOfTime.size() - 1).equalsIgnoreCase(
							flightListPage.flightResultList().get(listOfTime.size() - 1).getAttribute("price"))) {
						return true;
					}
				}
				return false;
			} else {
				logger.error("Sorting with Arrival Time failed for " + sortingOrder);
			}

			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean getDepartureTimeSortedFlightDetails(String sortingOrder) {
		try {
			ArrayList<String> listOfTime = new ArrayList<String>();
			if (TestUtil.sort(flightListPage.departSortLink(), sortingOrder)) {
				for (int i = 0; i < flightListPage.flightResultList().size(); i++) {
					listOfTime.add(flightListPage.flightResultList().get(i).getAttribute("deptm"));
				}
				Collections.sort(listOfTime);
				if (sortingOrder.toLowerCase().contains("asc")) {
					if (listOfTime.get(0)
							.equalsIgnoreCase(flightListPage.flightResultList().get(0).getAttribute("price"))) {
						return true;
					}
				}
				if (sortingOrder.toLowerCase().contains("desc")) {
					if (listOfTime.get(listOfTime.size() - 1).equalsIgnoreCase(
							flightListPage.flightResultList().get(listOfTime.size() - 1).getAttribute("price"))) {
						return true;
					}
				}
				return false;
			} else {
				logger.error("Sorting with departure time failed for " + sortingOrder);
			}

			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean getDurationSortedFlightDetails(String sortingOrder) {
		try {
//			ArrayList<String> listOfTime = new ArrayList<String>();
			if (TestUtil.sort(flightListPage.durationSortLink(), sortingOrder)) {
				return true;
			} else {
				logger.error("Sorting with duration failed for " + sortingOrder);
			}

			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean getAirlinesSortedFlightDetails(String sortingOrder) {
		try {
			ArrayList<String> listOfTime = new ArrayList<String>();
			if (TestUtil.sort(flightListPage.airlinesSortLink(), sortingOrder)) {
				for (int i = 0; i < flightListPage.flightResultAirlinesList().size(); i++) {
					listOfTime.add(flightListPage.flightResultAirlinesList().get(i).getText());
				}
				Collections.sort(listOfTime);
				if (sortingOrder.toLowerCase().contains("asc")) {
					if (listOfTime.get(0)
							.equalsIgnoreCase(flightListPage.flightResultAirlinesList().get(0).getText())) {
						return true;
					}
				}
				if (sortingOrder.toLowerCase().contains("desc")) {
					if (listOfTime.get(listOfTime.size() - 1).equalsIgnoreCase(
							flightListPage.flightResultAirlinesList().get(listOfTime.size() - 1).getText())) {
						return true;
					}
				}
				return false;
			} else {
				logger.error("Sorting with Airlines failed for " + sortingOrder);
			}

			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean sortFlightResults(String priceOrder, String arrivalTmOrder, String depTmOrder, String durationOrder,
			String airlinesOrder) {
		try {
			if (getPriceSortedFlightDetails(priceOrder)) {
				logger.info("Flight results sorted successfully with price in " + priceOrder);
			} else {
				return false;
			}
			if (getArrivalTimeSortedFlightDetails(arrivalTmOrder)) {
				logger.info("Flight results sorted successfully with arrival time in " + arrivalTmOrder);
			} else {
				return false;
			}
			if (getDepartureTimeSortedFlightDetails(depTmOrder)) {
				logger.info("Flight results sorted successfully with departure time in " + depTmOrder);
			} else {
				return false;
			}
			if (getDurationSortedFlightDetails(durationOrder)) {
				logger.info("Flight results sorted successfully with duration in " + durationOrder);
			} else {
				return false;
			}
			if (getAirlinesSortedFlightDetails(airlinesOrder)) {
				logger.info("Flight results sorted successfully with airlines in " + airlinesOrder);
			} else {
				return false;
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getFilghtResultsList(String filename) {
		List<String> airlines = new ArrayList<String>();
		List<String> deptm = new ArrayList<String>();
		List<String> arrtm = new ArrayList<String>();
		List<String> dur = new ArrayList<String>();
		List<String> price = new ArrayList<String>();
		for(int i=0;i<flightListPage.flightResultList().size();i++) {
			price.add(flightListPage.flightResultList().get(i).getAttribute("price"));
			deptm.add(flightListPage.flightResultList().get(i).getAttribute("deptm"));
			arrtm.add(flightListPage.flightResultList().get(i).getAttribute("arrtm"));
			airlines.add(flightListPage.flightResultAirlinesList().get(i).getText());
			dur.add(flightListPage.flightResultDurationList().get(i).getText());
		}
		try {
			TestUtil.writeFlightDataToExcel(filename, airlines, deptm, arrtm, dur, price);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
