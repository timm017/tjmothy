package com.tjmothy.email;

import java.sql.*;
import java.util.ArrayList;

import com.tjmothy.stats.StatsBean;
import com.tjmothy.stats.User;
import com.tjmothy.utils.TProperties;

public class ReminderBean
{

    private TProperties tProps;

    public ReminderBean()
    {
        this.tProps = new TProperties();
    }

    /**
     * Retrieves a list of all teamIds that haven't submitted their games for the current day.
     * Then collects the email associated with that teamId.
     *
     * @param sportId
     * @return
     */
    public ArrayList<String> getEmailsForReminder(int sportId)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<String> reminderEmails = new ArrayList<>();

        try
        {
            Class.forName(TProperties.DRIVERS);
            conn = DriverManager.getConnection(tProps.getConnection());
//			pstmt = conn.prepareStatement("SELECT s.id, s.home_id FROM schedule s WHERE s.home_id NOT IN(SELECT ts.team_id FROM team_stats ts WHERE ts.schedule_id = s.id AND ts.submitted = 0) AND s.game_day = CURDATE() AND s.sport=?");
            pstmt = conn.prepareStatement("SELECT s.id, s.home_id FROM schedule s WHERE s.id NOT IN (SELECT ts.schedule_id FROM team_stats ts) AND s.game_day = CURDATE() AND s.sport=?");
            pstmt.setInt(1, sportId);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                int teamId = rs.getInt(StatsBean.Column.home_id.name());
                User user = getUserFromTeamId(teamId);
                if (user != null)
                {
                    reminderEmails.add(getUserFromTeamId(teamId).getEmail());
                }
                else
                {
                    System.out.println("ReminderBean.getEmailsForReminder() -> user null for teamId = " + teamId);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("ReminderBean.getEmailsForReminder(): " + e.getMessage());
        }
        finally
        {
            try
            {
                if (conn != null)
                    conn.close();
                if (pstmt != null)
                    pstmt.close();
                if (rs != null)
                    rs.close();
            }
            catch (Exception e)
            {
                ;
            }
        }
        return reminderEmails;
    }

    /**
     * Creates a new user object from the teamId
     *
     * @param teamId
     * @return
     */
    private User getUserFromTeamId(int teamId)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try
        {
            Class.forName(TProperties.DRIVERS);
            conn = DriverManager.getConnection(tProps.getConnection());
            pstmt = conn.prepareStatement("SELECT * FROM users WHERE team_id=? LIMIT 1");
            pstmt.setInt(1, teamId);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                user = new User(rs);
            }
        }
        catch (Exception e)
        {
            System.out.println("ReminderBean.getUserFromTeamId(): " + e.getMessage());
        }
        finally
        {
            try
            {
                if (conn != null)
                    conn.close();
                if (pstmt != null)
                    pstmt.close();
                if (rs != null)
                    rs.close();
            }
            catch (Exception e)
            {
                ;
            }
        }
        return user;
    }

    /**
     * Gets the Athletic Director's email for the specific sport
     *
     * @param sportId
     * @return - Athletic Director's email
     */
    public ArrayList<String> getADEmail(int sportId)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<String> emails = new ArrayList<>();
        try
        {
            Class.forName(TProperties.DRIVERS);
            conn = DriverManager.getConnection(tProps.getConnection());
            pstmt = conn.prepareStatement("SELECT DISTINCT user_email FROM wordpress.wp_users " +
                    "JOIN teams ON teams.wp_ADID = wp_users.id WHERE teams.wp_ADID IN " +
                    "(SELECT wp_ADID FROM teams JOIN schedule ON (home_id = teams.id or road_id = teams.id) " +
                    "WHERE game_day < NOW() AND (home_score = 0 AND road_score = 0) AND schedule.sport = ?);");
            pstmt.setInt(1, sportId);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                System.out.println("email: " + rs.getString("user_email"));
                emails.add(rs.getString("user_email"));
            }
        }
        catch (Exception e)
        {
            System.out.println("ReminderBean.getADEmail(): " + e.getMessage());
        }
        finally
        {
            try
            {
                if (conn != null)
                    conn.close();
                if (pstmt != null)
                    pstmt.close();
                if (rs != null)
                    rs.close();
            }
            catch (Exception e)
            {
                System.out.println("ReminderBean.getADEmail() - SQL connection error: " + e.getMessage());
            }
        }
        return emails;
    }
}
