package com.EaseMyTrip.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.EaseMyTrip.pageObjects.FlightListPage;
import com.EaseMyTrip.resources.TestBase;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.util.MailSSLSocketFactory;

public class TestUtil extends TestBase {
	public static String readOTPfromMail(final String email, final String password) throws GeneralSecurityException {
		// TODO Auto-generated method stub
		Properties props = new Properties();
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		props.put("mail.imap.ssl.trust", "*");
		props.put("mail.imap.ssl.socketFactory", sf);
		props.put("mail.imap.com", "imap.gmail.com");
		props.put("mail.imap.starttls.enable", "true");
		props.put("mail.imap.auth", "true"); // If you need to authenticate
		props.put("mail.imap.socketFactory.port", 993);
		props.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.imap.socketFactory.fallback", "false");
//		System.out.println("Mail: " + email + " Password: " + password);
		Session session = Session.getDefaultInstance(props);
//		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(email, password);
//			}
//		});
		ArrayList<String> listOfOtps = new ArrayList<String>();
		String recentOtp = "";
		try {
			Object messageContent = new ArrayList<String>();
			IMAPStore store = (IMAPStore) session.getStore("imap");
			store.connect("imap.gmail.com", email, password);
			// create the folder object and open it
			Folder inbox = store.getFolder("INBOX");
			Thread.sleep(30000);
			inbox.open(Folder.READ_ONLY);
			Message[] messages = inbox.getMessages();
			for (int i = 0, n = messages.length; i < n; i++) {
				Message message = messages[i];
				if (message.getFrom()[0].toString().contains("EaseMyTrip")) {
					messageContent = message.getContent();
//					System.out.println("---------------------------------");
//					System.out.println("Email Number " + (i + 1));
//					System.out.println("Subject: " + message.getSubject());
//					System.out.println("From: " + message.getFrom()[0]);
//					System.out.println("Text: " + messageContent.toString());
					// close the store and folder objects
					Scanner sc = new Scanner(messageContent.toString());
					String otp = "";
					String line = null;
					while (sc.hasNextLine()) {
						line = sc.nextLine().trim();
						if (line.contains("height=\"60\"")) {
							otp = line.substring(171, 177);
							listOfOtps.add(otp);
						}
					}
					sc.close();
				}
			}
			inbox.close(false);
			store.close();
//			System.out.println("List of OTPs retrived: "+listOfOtps);
			if (listOfOtps.size() > 1) {
				recentOtp = listOfOtps.get(listOfOtps.size() - 1);
			} else {
				recentOtp = listOfOtps.get(0);
			}
			return recentOtp;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recentOtp;
	}

	public static void sendMail(final String email, final String password, final String toEmail) throws IOException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password);
			}
		});
		try {

			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(email));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

			// Set Subject: header field
			message.setSubject("Best Buy Test Results");

			BodyPart messageBodyPart = new MimeBodyPart();
			BodyPart messageBodyPart1 = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText("TEST RESULTS by Amit");

			// Create a multipart message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
//	         messageBodyPart = new MimeBodyPart();
//	         String filename = System.getProperty("user.dir")
//						+ "\\target\\surefire-reports\\index.html";
//	         DataSource source = new FileDataSource(filename);
//	         messageBodyPart.setDataHandler(new DataHandler(source));
//	         messageBodyPart.setFileName(filename);
//	         multipart.addBodyPart(messageBodyPart);
			messageBodyPart1 = new MimeBodyPart();
			String filename1 = System.getProperty("user.dir") + "\\target\\surefire-reports\\emailable-report.html";
			String content = Jsoup.parse(new File(filename1), "UTF-8").toString();
			messageBodyPart1.setContent(content, "text/html");

			multipart.addBodyPart(messageBodyPart1);
			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);
			System.out.println("MESSAGE SENT SUCCESSFULLY");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static String convertMonthnumbertoName(int monthNumberInt) {
		String monthNumber = String.valueOf(monthNumberInt);
		if (monthNumber.equals("1")) {
			return "JAN";
		} else if (monthNumber.equals("2")) {
			return "FEB";
		} else if (monthNumber.equals("3")) {
			return "MAR";
		} else if (monthNumber.equals("4")) {
			return "APR";
		} else if (monthNumber.equals("5")) {
			return "MAY";
		} else if (monthNumber.equals("6")) {
			return "JUN";
		} else if (monthNumber.equals("7")) {
			return "JUL";
		} else if (monthNumber.equals("8")) {
			return "AUG";
		} else if (monthNumber.equals("9")) {
			return "SEP";
		} else if (monthNumber.equals("10")) {
			return "OCT";
		} else if (monthNumber.equals("11")) {
			return "NOV";
		} else if (monthNumber.equals("12")) {
			return "DEC";
		}
		return "Invalid Input";
	}

	public static boolean selectFromListofElements(List<WebElement> listOfValues, String selection) {
		try {
			for (WebElement value : listOfValues) {
				if (value.getText().contains(selection)) {
					value.click();
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

	public static boolean setSliderRange(WebElement displayText, WebElement maxSliderObject, WebElement minSliderObject,
			String maxRange, String minRange) {
		try {
			if (displayText.isDisplayed()) {
				String value = displayText.getAttribute("value");
				Actions action = new Actions(driver);
				String[] valueRange = value.split("-");
				String actualMaxRange = valueRange[1].strip().replaceAll("[^\\d.]", "");
				String actualMinRange = valueRange[0].strip().replaceAll("[^\\d.]", "");
				if (maxRange != null) {
					maxSliderObject.click();
					while (actualMaxRange.equalsIgnoreCase(maxRange)) {
						action.sendKeys(Keys.ARROW_LEFT).build().perform();
					}
					return true;
				}
				if (minRange != null) {
					minSliderObject.click();
					while (actualMinRange.equalsIgnoreCase(minRange)) {
						action.sendKeys(Keys.ARROW_RIGHT).build().perform();
					}
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

	public static boolean sort(WebElement itemToSort, String sortingOrder) {
		try {
			FlightListPage flightListPage = new FlightListPage(driver);
			if (sortingOrder.toLowerCase().contains("asc")) {
				while (flightListPage.sortArrow().getAttribute("class").contains("up")) {
					flightListPage.priceSortLink().click();
				}
				return true;
			}
			if (sortingOrder.toLowerCase().contains("desc")) {
				while (flightListPage.sortArrow().getAttribute("class").contains("down")) {
					flightListPage.priceSortLink().click();
				}
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public static void dateSelector(WebElement monthNavigation, WebElement monthSelect, List<WebElement> dates,
			Date date) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//		System.out.println("[DEBUG] Month Value: "+localDate.getMonthValue());
//		System.out.println("[DEBUG] Month Name from Excel: " + convertMonthnumbertoName(localDate.getMonthValue()));
//		System.out.println("[DEBUG] Month Name From UI: "+monthSelect.getText());
//		if (!monthSelect.getText().contains(convertMonthnumbertoName(localDate.getMonthValue()))) {
//			Thread.sleep(2000);
//			while (!monthSelect.getText().contains(convertMonthnumbertoName(localDate.getMonthValue()))) {
//				driver.navigate().refresh();
//				Thread.sleep(2000);
//				System.out.println("[DEBUG] Month Name From UI: "+monthSelect.getText());
//				monthNavigation.click();
//			}
//		}
		for (int i = 1; i <= 12; i++) {
			if (monthSelect.getText().contains(convertMonthnumbertoName(localDate.getMonthValue()))) {
//				System.out.println("[DEBUG] Month Name From UI: " + monthSelect.getText());
				break;
			}
//			System.out.println("[DEBUG] Month Name From UI: " + monthSelect.getText());
			monthNavigation.click();
		}

//		System.out.println("[DEBUG] Date From Excel: "+localDate.getDayOfMonth());
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
//		System.out.println("[DEBUG] Date From Excel: " + sf.format(date));
		for (int i = 0; i < dates.size(); i++) {
//			System.out.println("[DEBUG] Date From UI: "+dates.get(i).getAttribute("id"));
			if (dates.get(i).getAttribute("id").contains(sf.format(date))) {
				dates.get(i).click();
				break;
			}
		}
	}

	public static ArrayList<String> getListOfUsers(String sheetName, String excelPath) throws IOException {
		FileInputStream file = new FileInputStream(excelPath);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		int sheetCount = workbook.getNumberOfSheets();
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < sheetCount; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				Iterator<Row> row = sheet.iterator();
				while (row.hasNext()) {
					Row rowObject = row.next();
					list.add(rowObject.getCell(0).getStringCellValue());
				}
			}
		}
		return list;
	}

	public static ArrayList<String> getDetailsOfUser(String sheetName, String excelPath, String email)
			throws IOException {
		FileInputStream file = new FileInputStream(excelPath);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		int sheetCount = workbook.getNumberOfSheets();
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < sheetCount; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				Iterator<Row> row = sheet.iterator();
				while (row.hasNext()) {
					Row rowObject = row.next();
					if (rowObject.getCell(0).getStringCellValue().contains(email)) {
						Iterator<Cell> cellObject = rowObject.cellIterator();
						while (cellObject.hasNext()) {
							Cell cellObject1 = cellObject.next();
							if (cellObject1.getCellType() == CellType.STRING) {
								list.add(cellObject1.getStringCellValue());
							} else if (cellObject1.getCellType() == CellType.NUMERIC) {
								if (DateUtil.isCellDateFormatted(cellObject1)) {
									SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
									list.add(dateFormat.format(cellObject1.getDateCellValue()));
								} else {
									list.add(Long.toString((long) cellObject1.getNumericCellValue()));
								}
							}
						}
					}
				}
			}
		}
		return list;
	}

	public static Object[][] readUsersFromExcel(String filename, String SheetName) throws IOException {
//		System.out.println("File path: " + filename);
		String file_location = System.getProperty("user.dir") + filename;
		FileInputStream fileInputStream = new FileInputStream(file_location); // Excel sheet file location get mentioned
																				// here
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream); // get my workbook
		XSSFSheet worksheet = workbook.getSheet(SheetName);// get my sheet from workbook
		XSSFRow Row = worksheet.getRow(0); // get my Row which start from 0

		int RowNum = worksheet.getPhysicalNumberOfRows();// count my number of Rows
		int ColNum = Row.getLastCellNum(); // get last ColNum

		Object Data[][] = new Object[RowNum - 1][ColNum]; // pass my count data in array

		for (int i = 0; i < RowNum - 1; i++) // Loop work for Rows
		{
			XSSFRow row = worksheet.getRow(i + 1);

			for (int j = 0; j < ColNum; j++) // Loop work for colNum
			{
				if (row == null)
					Data[i][j] = "";
				else {
					XSSFCell cell = row.getCell(j);
					if (cell == null)
						Data[i][j] = ""; // if it get Null value it pass no data
					else if (cell.getCellType() == CellType.STRING) {
						String value = cell.getStringCellValue();
						Data[i][j] = value; // This formatter get my all values as string i.e integer, float all type
											// data value
					} else if (cell.getCellType() == CellType.NUMERIC) {
						if (DateUtil.isCellDateFormatted(cell)) {
							Date value = cell.getDateCellValue();
							Data[i][j] = value;
						} else {
							double value = cell.getNumericCellValue();
							Data[i][j] = String.valueOf(value);
						}
						// This formatter get my all values as string i.e integer, float all type
						// data value
					}
					// This formatter get my all values as string i.e integer, float all type
					// data value
				}
			}
		}
		workbook.close();

		return Data;
	}

	public static void writeFlightDataToExcel(String filename, List<String> airlines, List<String> depTm,
			List<String> arrTm, List<String> duration, List<String> price) throws IOException {
		String file_location = System.getProperty("user.dir") + filename;
		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet("Flight Results");

		// Create row object
		XSSFRow row;
		// This data needs to be written (Object[])
		Map<String, Object[]> fltinfo = new TreeMap<String, Object[]>();
		fltinfo.put("1", new Object[] { "AIRLINES", "DEPARTURE TIME", "ARRIVAL TIME", "DURATION", "PRICE" });
		int c = 2;
		for (int i = 0; i < airlines.size(); i++) {
			fltinfo.put(String.valueOf(c),
					new Object[] { airlines.get(i), depTm.get(i), arrTm.get(i), duration.get(i), price.get(i) });
			c = c + 1;
		}

		// Iterate over data and write to sheet
		Set<String> keyid = fltinfo.keySet();
		int rowid = 0;

		for (String key : keyid) {
			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = fltinfo.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}
		// Write the workbook in file system
		FileOutputStream out = new FileOutputStream(new File(file_location));
		workbook.write(out);
		out.close();
		workbook.close();
	}
}
