package com.EaseMyTrip.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AccountPage {
	public WebDriver driver;

	public AccountPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebElement emailDisplayText() {
		return driver.findElement(By.xpath("//span[text()='Hi there!']/following-sibling::span"));
	}
	public WebElement logoutButton() {
		return driver.findElement(By.xpath("//button[text()='Log out']"));
	}
	public WebElement logoutSureButton() {
		return driver.findElement(By.xpath("(//button[text()='Log out'])[2]"));
	}
	
	
	
}
