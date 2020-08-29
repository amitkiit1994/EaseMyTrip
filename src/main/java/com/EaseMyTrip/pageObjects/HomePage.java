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
	
	
	
	
	
	
	
	
	
	
	
	
}
