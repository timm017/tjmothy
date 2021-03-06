package com.tjmothy.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.tjmothy.utils.TProperties;

public class Emailer
{
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	private Email email;
	private static TProperties tprops = (TProperties) new TProperties();

	public static void main(String args[]) throws AddressException, MessagingException
	{
		Email email = new Email(true);
		Emailer e = new Emailer(email);
		System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
	}

	public Emailer(Email email) throws AddressException, MessagingException
	{
		this.email = email;
		generateAndSendEmail(this.email);
	}

	public static void generateAndSendEmail(Email email) throws AddressException, MessagingException
	{
		// Step1
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("Mail Server Properties have been setup successfully..");

		// Step2
		System.out.println("\n 2nd ===> get Mail Session..");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		int i = 1;
		// Loop through all the email addresses you would like to send the stats to
		for (String emailAddress : email.getRecipients())
		{
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
			System.out.println("\n (" + i + ") 2nd ===> Adding \"" + emailAddress + "\" to list...");
			i++;
		}
		generateMailMessage.setSubject(email.getSubject());
		String emailBody = email.getBody();
//		System.out.println(email.getBody());
		generateMailMessage.setContent(emailBody, "text/html; charset=utf-8");
		System.out.println("Mail Session has been created successfully..");

		// Step3
		System.out.println("\n\n 3rd ===> Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtp");

//		System.out.println("gmail_password: " + tprops.getProperty(TProperties.PropertyName.gmail_password));
//		System.out.println("password: " + tprops.getProperty(TProperties.PropertyName.password));
		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		transport.connect("smtp.gmail.com", "sportsstatstracker@gmail.com", tprops.getProperty(TProperties.PropertyName.gmail_password));
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}
}
