package com.EaseMyTrip.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
	public WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement myAccountDropTray() {
		return driver.findElement(By.xpath("//span[text()='My Account']"));
	}

	public WebElement registerButton() {
		return driver.findElement(By.xpath("//div[@id='divSignInPnl']/a[text()='Register']"));
	}

	public WebElement emailInputField() {
		return driver.findElement(By.xpath("//input[@id='txtEmail']"));
	}

	public WebElement registerButtonForEmail() {
		return driver.findElement(By.xpath("//input[@value='Register' and @onclick='CreateAccountMobEmail();']"));
	}

	public WebElement otpTextInput() {
		return driver.findElement(By.xpath("//input[@id='otptxt']"));
	}

	public WebElement passwordInputField() {
		return driver.findElement(By.xpath("//input[@id='pass']"));
	}

	public WebElement confirmPasswordInputField() {
		return driver.findElement(By.xpath("//input[@id='confirmpass']"));
	}

	public WebElement submitButton() {
		return driver.findElement(By.xpath("//input[@value='Submit' and @onclick='RegConfirmOTP();']"));
	}

	public WebElement closeButton() {
		return driver.findElement(By.xpath("//div[text()='Create New Account']/following-sibling::div/i"));
	}

	public List<WebElement> importantInformationDialog() {
		return driver.findElements(By.xpath("//h3[text()='Important Information']"));
	}

	public WebElement importantInformationDialogCloseButton() {
		return driver.findElement(By.xpath("//h3[text()='Important Information']/preceding-sibling::a[text()='×']"));
	}

	public WebElement loginUserNameText() {
		return driver.findElement(By.xpath("//input[@id='txtusername']"));
	}

	public WebElement loginPasswordText() {
		return driver.findElement(By.xpath("//input[@id='Password1']"));
	}

	public WebElement loginButton() {
		return driver.findElement(By.xpath("//input[@value='Login' and @onClick='UserAuthentication()']"));
	}

	public WebElement accountNameDisplayText() {
		return driver.findElement(By.xpath("//span[text()='Hi ']/following-sibling::span"));
	}
	public WebElement logoutButton() {
		return driver.findElement(By.xpath("//a[@onClick='LogOut()']"));
	}
	public WebElement myProfileButton() {
		return driver.findElement(By.xpath("//span[@id='spnLogoutPnl']/a[text()='My Profile']"));
	}
	public List<WebElement> tripTypeSwitch() {
		return driver.findElements(By.xpath("//li[contains(@onclick,'setType')]"));
	}
	public WebElement fromLocationInputField() {
		return driver.findElement(By.xpath("//input[@id='FromSector_show']"));
	}
	public List<WebElement> selectFromCity() {
		return driver.findElements(By.xpath("//li[contains(@onclick,'FromSector')]/div/span[@class='ct']"));
	}
	public WebElement toLocationInputField() {
		return driver.findElement(By.xpath("//input[@placeholder='To' and @id='Editbox13_show']"));
	}
	public List<WebElement> selectToCity() {
		return driver.findElements(By.xpath("//li[@class='ui-menu-item']/div[contains(@class,'active')]/span[@class='ct']"));
	}
	public WebElement departureDatePicker() {
		return driver.findElement(By.xpath("//input[@id='ddate']"));
	}
	public WebElement monthList(){
		return driver.findElement(By.xpath("(//div[contains(@class,'month') and contains(text(),'2020')])[1]"));
	}
	public WebElement monthSelectRightArrow() {
		return driver.findElement(By.xpath("//img[@alt='Arrow' and contains(@onclick,'nxt')]"));
	}
	public List<WebElement> dateList(){
		return driver.findElements(By.xpath("//div[@class='box']/div[@class='days']/ul/li[contains(@id,'/2020') and not(contains(@class,'old-dt'))]"));
	}	
	public WebElement numberOfTravelersSelect() {
		return driver.findElement(By.xpath("//div[@class='dropdown_n']/a[@onclick='myFunction4()']/span[text()='1 Traveller(s)' and@class='drpNoTrv']"));
	}
	public WebElement numberOfTravelersDisplayText() {
		return driver.findElement(By.xpath("(//input[@name='quantity'])[1]"));
	}
	public WebElement numberOfTravelersAdultSelect() {
		return driver.findElement(By.xpath("(//input[@field='quantity' and @value='+'])[1]"));
	}
	public WebElement numberOfTravelersDoneButton() {
		return driver.findElement(By.xpath("(//a[text()='Done'])[1]"));
	}
	public WebElement classSelect() {
		return driver.findElement(By.xpath("//div[@class='dropdown_n']/a[@onclick='myFunction9()']/span[text()=' Economy' and@class='optclass-name']"));
	}
	public List<WebElement> classSelectfromList() {
		return driver.findElements(By.xpath("//input[contains(@type,'radio') and not(contains(@onclick,'Mul')) and @name='optClass']/parent::label"));
	}
	public WebElement classSelectDoneButton() {
		return driver.findElement(By.xpath("(//a[text()='Done'])[2]"));
	}
	public WebElement searchButton() {
		return driver.findElement(By.xpath("(//input[@value='Search'])[1]"));
	}
	
	
	
	
}
