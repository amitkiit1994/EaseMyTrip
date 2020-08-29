package com.EaseMyTrip.businessLayer;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.EaseMyTrip.pageObjects.AccountPage;
import com.EaseMyTrip.pageObjects.HomePage;
import com.EaseMyTrip.resources.TestBase;

public class Account extends TestBase {

	HomePage homePage = new HomePage(driver);
	AccountPage accountPage = new AccountPage(driver);
	String url = prop.getProperty("URL");
	WebDriverWait wait = new WebDriverWait(driver, 60);

	public boolean openWebsite() {

		try {
			if (url != null) {
				driver.get(prop.getProperty("URL"));
				driver.manage().window().maximize();
				wait.until(ExpectedConditions.visibilityOf(homePage.myAccountDropTray()));
				if (homePage.myAccountDropTray().isDisplayed()) {
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

	public boolean checkAndCloseInformationAlert() {
		try {
			if (homePage.importantInformationDialog().size() != 0) {
				homePage.importantInformationDialogCloseButton().click();
				return true;
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return true;

		}
	}

	public boolean clickMyAccount() {
		try {
			wait.until(ExpectedConditions.visibilityOf(homePage.myAccountDropTray()));
			homePage.myAccountDropTray().click();
			wait.until(ExpectedConditions.visibilityOf(homePage.registerButton()));
			if (homePage.registerButton().isDisplayed()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;

		}
	}

	public boolean registerWithEmail(String email) {
		try {
			homePage.registerButton().click();
			wait.until(ExpectedConditions.visibilityOf(homePage.emailInputField()));
			if (homePage.emailInputField().isDisplayed()) {
				homePage.emailInputField().clear();
				homePage.emailInputField().sendKeys(email);
				homePage.registerButtonForEmail().click();
				wait.until(ExpectedConditions.visibilityOf(homePage.otpTextInput()));
				if (homePage.otpTextInput().isDisplayed()) {
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

	public boolean setOtpandPassword(String otp, String password) {
		try {
			homePage.otpTextInput().clear();
			homePage.otpTextInput().sendKeys(otp);
			//wait.until(ExpectedConditions.visibilityOf(homePage.passwordInputField()));
			if (homePage.passwordInputField().isDisplayed()) {
				homePage.passwordInputField().clear();
				homePage.passwordInputField().sendKeys(password);
				homePage.confirmPasswordInputField().clear();
				homePage.confirmPasswordInputField().sendKeys(password);
				homePage.submitButton().click();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean clickClose() {
		try {
			homePage.closeButton().click();
			//wait.until(ExpectedConditions.visibilityOf(homePage.myAccountDropTray()));
			if (homePage.myAccountDropTray().isDisplayed()) {
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
