package com.EaseMyTrip.test;

import static org.junit.Assert.assertTrue;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.EaseMyTrip.businessLayer.Account;
import com.EaseMyTrip.businessLayer.Flight;
import com.EaseMyTrip.resources.TestBase;

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

	@Test(dataProvider = "readFlightSearchInfoExcel", dataProviderClass = DataProvider.class)
	public void searchFlightTest(String tripType, String departureCity, String arrivalCity, Date departureDate,
			Date arrivalDate, String numberOfTravellers, String flightClass) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
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
			if (flight.searchFlight(tripType, departureCity, arrivalCity, departureDate, arrivalDate, numberOfTravellers, flightClass)) {
				logger.info("Flight Search test case executed successfully");
			}
			logger.info("---------------------------------------------------------");
			driver.navigate().refresh();
		} catch (Exception e) {
			logger.error("Exception Occured while Searching Flight");
			e.printStackTrace();
			assertTrue(false);
		}
	}

	

	@AfterTest
	public void closeBrowser() {
		driver.close();
		driver.quit();
		logger.info("Browser closed");
	}
}
