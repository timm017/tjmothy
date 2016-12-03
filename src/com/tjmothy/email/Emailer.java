package com.tjmothy.email;

import java.net.PasswordAuthentication;
import java.util.Properties;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import com.sun.xml.internal.ws.api.message.Message;

import sun.rmi.transport.Transport;

public class Emailer
{
	public static void main(String[] args)
	{
		// Recipient's email ID needs to be mentioned.
		String to = "destinationemail@gmail.com";

		// Sender's email ID needs to be mentioned
		String from = "sportsstatstracker@gmail.com";
		final String username = "sportsstatstracker";// change accordingly
		final String password = "tmckeown1";// change accordingly

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "relay.jangosmtp.net";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "25");

		// Get the Session object.
//		Session session = Session.getInstance(props, new javax.mail.Authenticator()
//		{
//			protected PasswordAuthentication getPasswordAuthentication()
//			{
//				return new PasswordAuthentication(username, password);
//			}
//		});
//
//		try
//		{
//			// Create a default MimeMessage object.
//			Message message = new MimeMessage(session);
//
//			// Set From: header field of the header.
//			message.setFrom(new InternetAddress(from));
//
//			// Set To: header field of the header.
//			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//
//			// Set Subject: header field
//			message.setSubject("Testing Subject");
//
//			// Now set the actual message
//			message.setText("Hello, this is sample for to check send " + "email using JavaMailAPI ");
//
//			// Send message
//			Transport.send(message);
//
//			System.out.println("Sent message successfully....");
//
//		}
//		catch (MessagingException e)
//		{
//			throw new RuntimeException(e);
//		}
	}
}
