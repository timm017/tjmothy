package com.tjmothy.crons;

import com.tjmothy.email.Email;
import com.tjmothy.email.Emailer;
import com.tjmothy.email.ReminderBean;

import javax.mail.MessagingException;
import java.util.ArrayList;

/**
 * Athletic Director reminder email. This class will gather all emails that this particular sport is missing game.
 * Sends AD reminder email telling them to remind their coaches to report their missing games.
 *
 * @author tmckeown
 */
public class ADEmails
{
    final static String REMINDER_SUBJECT = "District Scores: Reminder";
    // TODO: Add "sport_name" after "had a" in body
    final static String REMINDER_BODY = "Your school had a game last night that was not reported to districtscores.com. Please log in and enter the score to keep the rankings up to date.<br/><br/>Thank you,<br>DistrictScores.com";

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
            System.out.println("Gathering AD emails for sportId (0 = all): " + sportId);
            ReminderBean rb = new ReminderBean();
            ArrayList<String> adEmails = rb.getADEmails(sportId);
//            adEmails.forEach((email) -> System.out.print("  Emailing Athletic Directory [" + email + "]"));
            for (String adEmail : adEmails)
            {
                sendEmail(REMINDER_BODY, REMINDER_SUBJECT, adEmail);
            }
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
