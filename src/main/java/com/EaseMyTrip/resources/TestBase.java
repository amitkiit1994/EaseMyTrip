package com.EaseMyTrip.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class TestBase {
	public static WebDriver driver;
	public static Properties prop;

	public WebDriver initializeDriver() throws IOException {

		prop = new Properties();
		String propertiesFilePath = System.getProperty("user.dir")
				+ "/src/main/java/com/EaseMyTrip/resources/config.properties";
		FileInputStream fis = new FileInputStream(propertiesFilePath);

		prop.load(fis);
		String browserName = prop.getProperty("BROWSER");
		String chromeDriverPath = System.getProperty("user.dir")
				+ "/src/main/java/com/EaseMyTrip/resources/chromedriver.exe";
		// System.out.println(browserName);

		ChromeOptions options = new ChromeOptions();
		options.addArguments(RandomUserAgent.getRandomUserAgent(),"--incognito");
		System.out.println("User Agent: "+RandomUserAgent.getRandomUserAgent());
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			driver = new ChromeDriver(options);

		} else if (browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();

		} else if (browserName.equalsIgnoreCase("internetexplorer")) {
			driver = new InternetExplorerDriver();
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}


	public void getScreenshot(String result) throws IOException {
	}
}
