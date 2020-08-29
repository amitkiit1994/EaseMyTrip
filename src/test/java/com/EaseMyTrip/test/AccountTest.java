package com.EaseMyTrip.test;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.EaseMyTrip.businessLayer.Account;
import com.EaseMyTrip.resources.TestBase;
import com.EaseMyTrip.util.TestUtil;

public class AccountTest extends TestBase {
	private static final Logger logger = LogManager.getLogger(AccountTest.class);
	WebDriver driver;
	Account account;

	@BeforeTest
	public void openSkyScanner() {

		try {
			driver = initializeDriver();
			logger.info("Driver is Initialized");
		} catch (Exception e) {
			assertTrue(false);
			logger.error("Exception Occured while Initializing driver");
			e.printStackTrace();
		}
		account = new Account();
		if (account.openWebsite()) {
			logger.info("Website opened successfully");
		} else {
			assertTrue(false);
			logger.error("Website could not be opened");
		}
	}

	@Test(dataProvider = "readValueFromExcel")
	public void createAccount(String email, String password) throws GeneralSecurityException {
		account = new Account();
		String otp="";
		try {
			Thread.sleep(2000);
			logger.info("Creating Account for Email id: " + email);
			if (account.checkAndCloseInformationAlert()) {
				logger.info("Covid Alert Dialog Box closed");
			}
			if (account.clickMyAccount()) {
				logger.info("My Account tray clicked successfully");
			} else {
				logger.error("My account could not be clicked");
				assertTrue(false);
			}
			if (account.registerWithEmail((email))) {
				logger.info("Email Entered with email id: " + email);
				otp = TestUtil.readOTPfromMail(email, password);
			} else {
				logger.error("Email could not be processed with email id: " + email);
				assertTrue(false);
			}
			if (otp != "") {
				logger.info("OTP successfully retrieved from mail: " + otp);
			} else {
				logger.error("OTP is null for email: " + email);
				assertTrue(false);
			}
			if (account.setOtpandPassword(otp, password)) {
				logger.info("Password Entered with: " + password);
			} else {
				logger.error("Password could not be processed with: " + password);
				assertTrue(false);
			}
			if (account.clickSubmit()) {
				logger.info("Email Id and Password submitted successfully");
			} else {
				logger.error("Account creation failed for email: " + email);
				assertTrue(false);
			}
			if (account.login(email, password)) {
				logger.info("Account Verified Successfully for email: "+email);
			}
			else {
				logger.error("Account verification failed for email: " + email);
				assertTrue(false);
			}
		} catch (Exception e) {
			assertTrue(false);
			logger.error("Exception Occured while Creating Account for email: " + email);
			e.printStackTrace();
		}

	}

	@DataProvider
	public static Object[][] readValueFromExcel() {
		Object[][] data = null;
		try {
			data = TestUtil.readUsersFromExcel("Sheet1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Exception occured in Data Provider");
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
