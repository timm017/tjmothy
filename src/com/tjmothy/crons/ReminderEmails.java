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
	final static int MAX_EMAIL_SIZE = 50;
	final static String REMINDER_SUBJECT = "Distict Scores: Reminder";
	final static String REMINDER_BODY = "Hope you are having a great day. Friendly reminder to please submit your scores for today using this link: <a href=\"http://stats.tjmothy.com/index.php/add-stats/\">Add Stats</a><br/><br/>Thank you,<br>District Scores";

	public static void main(String[] args)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		ReminderBean rb = new ReminderBean();
		ArrayList<String> emails = rb.getEmailsForReminder(Game.BASEBALL_ID);

		if (emails.size() > MAX_EMAIL_SIZE)
		{
			System.out.println("Hit max size (" + MAX_EMAIL_SIZE + ") for reminder emails -> " + emails.size());
			return;
		}
		for (String email : emails)
		{
			System.out.println("sendimg reminder email to -> " + email);
			sendEmail(REMINDER_BODY, REMINDER_SUBJECT, email);
		}
	}

	private static void sendEmail(String body, String subjectLine, String userEmail)
	{
		Email email = new Email(false);
		email.addRecipient(userEmail);
		email.setSubject(subjectLine);
		email.setBody(body);
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
