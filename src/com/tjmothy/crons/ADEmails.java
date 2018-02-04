package com.tjmothy.crons;

import com.tjmothy.email.Email;
import com.tjmothy.email.Emailer;
import com.tjmothy.email.ReminderBean;

import javax.mail.MessagingException;
import java.util.ArrayList;

/**
 * @author tmckeown
 */
public class ADEmails
{
    final static String REMINDER_SUBJECT = "Distict Scores: Reminder";
    final static String REMINDER_BODY = "Hope you are having a great day. Friendly reminder that one or more of your coaches haven't submitted their scores for today's game.";

    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            System.out.println("Empty args, no sport specified");
            return;
        }
        else
        {
            int sportId = 0;
            try
            {
                sportId = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException nfe)
            {
                System.out.println("ADEmail.main() - " + nfe.getMessage());
            }
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd");
            ReminderBean rb = new ReminderBean();
            ArrayList<String> adEmails = rb.getADEmail(sportId);
            System.out.println("Gathering AD emails for sportId: " + sportId);
            adEmails.forEach((email) -> System.out.print("  Emailing Athletic Directory [" + email + "]"));
//            sendEmail(REMINDER_BODY, REMINDER_SUBJECT, adEmail);
        }
    }

    /**
     * Send emails to Athletic Directors that some of their coahces haven't submitted scores;
     *
     * @param body
     * @param subjectLine
     * @param userEmail
     */
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
