package com.EaseMyTrip.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.EaseMyTrip.businessLayer.Account;
import com.EaseMyTrip.businessLayer.Flight;
import com.EaseMyTrip.resources.TestBase;
import com.EaseMyTrip.util.TestUtil;

public class FlightTest extends TestBase {
	private static final Logger logger = LogManager.getLogger(FlightTest.class);
	WebDriver driver;
	Account account;
	Flight flight;

	@BeforeTest
	public void openSkyScanner() {

		try {
			driver = initializeDriver();
			logger.info("Driver is Initialized");
		} catch (Exception e) {
			logger.error("Exception Occured while Initializing driver");
			e.printStackTrace();
			assertTrue(false);	
		}
		account = new Account();
		if (account.openWebsite()) {
			logger.info("Website opened successfully");
		} else {
			assertTrue(false);
			logger.error("Website could not be opened");
		}
	}

	@Test(dataProvider = "readFlightSearchInfoExcel")
	public void searchFlightTest(String tripType, String departureCity, String arrivalCity, Date departureDate,
			Date arrivalDate, String numberOfTravellers, String flightClass) throws ParseException {
		SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
		flight = new Flight();
		try {
			logger.info("-------------------Flight Search Details-----------------");
			logger.info("Trip Type: " + tripType);
			logger.info("Departure City: " + departureCity);
			logger.info("Arrival City: " + arrivalCity);
			logger.info("Departure Date: " + format.format(departureDate));
			logger.info("Arrival Date: " + format.format(arrivalDate));
			logger.info("Number of Travellers: " + numberOfTravellers);
			logger.info("Flight Class: " + flightClass);
			
			if (tripType != null) {
				if (flight.selectTripType(tripType)) {
					logger.info("Trip Type selected");
				} else {
					logger.error("Trip type selection failed due to invalid inputs or click exception");
					assertTrue(false);
				}
			} else {
				logger.error("Null input");
				assertTrue(false);
			}
			if (!departureCity.equalsIgnoreCase(arrivalCity)) {
				if (departureCity != null) {
					if (flight.selectDepartureCity(departureCity)) {
						logger.info("Departure City selected");
					} else {
						logger.error("Departure City selection failed");
						assertTrue(false);
					}
				}
				if (arrivalCity != null) {
					if (flight.selectArrivalCity(arrivalCity)) {
						logger.info("Arrival city selected");
					} else {
						logger.error("Arrival city selection failed");
						assertTrue(false);
					}
				}
			} else {
				logger.error("Departure city and Arrival city are same, Please give valid inputs");
				assertTrue(false);
			}
			
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			Date dateobj = new Date();
			if(departureDate.after(dateobj)) {
				if (departureDate != null) {
					if (flight.selectDepartureDate(departureDate)) {
						logger.info("Departure date selected");
					} else {
						logger.error("Departure date selection failed");
						assertTrue(false);
					}
				}	
			} else {
				logger.error("Departure date: "+df.format(departureDate)+" is less than current date: "+df.format(dateobj));
				assertTrue(false);
			}
	
			if (Double.parseDouble(numberOfTravellers) > 0) {
				if (flight.selectNumberOfAdults(numberOfTravellers)) {
					logger.info("Number of travellers selected");
				} else {
					logger.error("Number of travellers selection failed");
					assertTrue(false);
				}
			} else {
				logger.error("Invalid input for number of Travellers, cannot be less than or equal to 0");
				assertTrue(false);
			}
			if (flightClass != null) {
				if (flight.selectClass(flightClass)) {
					logger.info("Flight Class selected");
				} else {
					logger.error("Flight Class selection failed");
					assertTrue(false);
				}
			} else {
				logger.error("Invalid input for Flight Class");
				assertTrue(false);
			}	
			logger.info("---------------------------------------------------------");
			driver.navigate().refresh();
		} catch (Exception e) {
			logger.error("Exception Occured while Searching Flight");
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@DataProvider
	public static Object[][] readFlightSearchInfoExcel() {
		Object[][] data = null;
		try {
			data = TestUtil.readUsersFromExcel(prop.getProperty("FLIGHT_SEARCH_INFO"),"Sheet1");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Exception occured in Data Provider for Flight search info list");
		}
		return data;

	}

	@AfterTest
	public void closeBrowser() {
		driver.close();
		driver.quit();
		logger.info("Browser closed");
	}
}
