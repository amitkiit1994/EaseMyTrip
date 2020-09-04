package com.EaseMyTrip.test;

import java.io.IOException;
import com.EaseMyTrip.resources.TestBase;
import com.EaseMyTrip.util.TestUtil;

public class DataProvider extends TestBase {
	
	
	@org.testng.annotations.DataProvider(name="readFlightSearchInfoExcel")
	public static Object[][] readFlightSearchInfoExcel() {
		Object[][] data = null;
		try {
			data = TestUtil.readUsersFromExcel(prop.getProperty("FLIGHT_SEARCH_INFO"), "Sheet1");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	@org.testng.annotations.DataProvider(name="readFlightSearchInfoExcel")
	public static Object[][] readFlightSearchInfoExcelPrerequisite() {
		Object[][] data = null;
		try {
			data = TestUtil.readUsersFromExcel(prop.getProperty("FLIGHT_SEARCH_INFO"), "Sheet2");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
