package com.tjmothy.email;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.tjmothy.stats.User;
import com.tjmothy.utils.TProperties;

public class ReminderBean
{

	public enum Table
	{
		leagues, teams, players, users, player_points, schedule, team_stats
	}

	public enum Column
	{
		sport, season, team_wins, team_loses, rank, email, type, home_score, road_score, submitted, home_team, road_team, home_id, road_id, first_quarter, second_quarter, third_quarter, fourth_quarter, overtime, highlights, id, first_name, last_name, team_name, league_name, league_id, team_id, school_name, username, password, phone_number, schedule_id, game_day, player_id, one_points, one_points_attempted, two_points, three_points, rebounds, fouls, pitches, number
	}

	private TProperties tProps;

	public ReminderBean()
	{
		this.tProps = new TProperties();
	}

	/**
	 * Retrieves a list of all teamIds that haven't submitted their games for the current day.
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
			pstmt = conn.prepareStatement("SELECT s.id, s.home_id FROM schedule s WHERE s.home_id NOT IN(SELECT ts.team_id FROM team_stats ts WHERE ts.schedule_id = s.id) AND s.game_day = CURDATE() AND s.sport=?");
			pstmt.setInt(1, sportId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				int teamId = rs.getInt(Column.home_id.name());
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
}
