package com.tjmothy.crons;

import com.tjmothy.email.Email;
import com.tjmothy.email.Emailer;
import com.tjmothy.email.ReminderBean;
import com.tjmothy.stats.Game;

import javax.mail.MessagingException;
import java.util.ArrayList;

/**
 * @author tmckeown
 */
public class ADEmails
{
    final static String REMINDER_SUBJECT = "Distict Scores: Reminder";
    final static String REMINDER_BODY = "Hope you are having a great day. Friendly reminder that some of your coaches haven't submitted their scores for today's games.";

    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            System.out.println("Empty args, no sport specified");
        }
        else
        {
            int sportId = 0;
            try
            {
                sportId = Integer.parseInt(args[0]);
            } catch (NumberFormatException nfe)
            {

            }
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd");
            ReminderBean rb = new ReminderBean();
            String adEmail = rb.getADEmail(sportId);
            System.out.println("sending Athletic Directory reminder email to -> " + adEmail);
            sendEmail(REMINDER_BODY, REMINDER_SUBJECT, adEmail);
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
        } catch (MessagingException me)
        {
            me.printStackTrace();
        }
    }
}
