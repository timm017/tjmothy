package com.tjmothy.crons;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.mail.MessagingException;

import com.tjmothy.email.Email;
import com.tjmothy.email.Emailer;
import com.tjmothy.email.ReminderBean;
import com.tjmothy.stats.Game;

/**
 * 
 * @author tmckeown
 *
 */
public class ReminderEmails
{
	final static String REMINDER_SUBJECT = "Distict Scores Reminder";
	final static String REMINDER_BODY = "Hope you are having a great day. Friendly reminder to please submit your scores for today using this link: <a href=\"http://stats.tjmothy.com/index.php/add-stats/\">Add Stats</a><br/><br/>Thank you,<br>District Scores";

	public static void main(String[] args)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		ReminderBean rb = new ReminderBean();
		ArrayList<String> emails = rb.getEmailsForReminder(Game.BASEBALL_ID);

		String body = "";
		for (String email : emails)
		{
			body += email + "\n";
		}
		sendEmail("", REMINDER_SUBJECT, "mckeown.timothy@gmail.com");

	}

	private static void sendEmail(String xml, String subjectLine, String userEmail)
	{
		Email email = new Email();
		email.addRecipient(userEmail);
		email.setSubject(subjectLine);
		email.setBody(REMINDER_BODY);
		try
		{
			new Emailer(email);
		}
		catch (MessagingException me)
		{
			me.printStackTrace();
		}
	}
}
