package com.EaseMyTrip.test;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;

import com.sun.mail.imap.IMAPStore;
import com.sun.mail.util.MailSSLSocketFactory;

public class Test {

	public static void main(String[] args) throws GeneralSecurityException {
		String email = "resultsautomationtest@gmail.com";
		String password = "Amrita1959@";
		System.out.println(readOTPfromMail(email, password));
	}
	
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
		ArrayList<String> listOfOtps=new ArrayList<String>();
		String recentOtp="";
		try {
			Object messageContent = new ArrayList<String>();
			IMAPStore store = (IMAPStore) session.getStore("imap");
			store.connect("imap.gmail.com", email, password);
			// create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			// retrieve the messages from the folder in an array and print it
			Message[] messages = emailFolder.getMessages();
//			System.out.println("messages.length---" + messages.length);

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
					String otp="";
			        String line = null;
			        while (sc.hasNextLine()) {
			            line = sc.nextLine().trim();
			            if(line.contains("height=\"60\"")) {
			            	otp=line.substring(171, 177);
			            	listOfOtps.add(otp);
			            }
			        }			        
			        sc.close();					
				}
			}
			emailFolder.close(false);
			store.close();
			if(listOfOtps.size()>1) {
			recentOtp=listOfOtps.get(listOfOtps.size()-1);
			}
			else {
				recentOtp=listOfOtps.get(0);
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
	

}

