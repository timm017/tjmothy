package com.tjmothy.stats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.tjmothy.utils.Encryption;
import com.tjmothy.utils.TProperties;

public class StatsBean
{
	public enum Table
	{
		leagues, teams, players, users, player_points, schedule
	}

	public enum Column
	{
		id, first_name, last_name, team_name, league_name, league_id, team_id, school_name, username, password, phone_number, schedule_id, game_day, player_id, one_points, one_points_attempted, two_points, three_points, rebounds;
	}

	public StatsBean()
	{

	}

	public String getPlayersForTeam(int teamId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder("<team_players id='" + teamId + "'>");

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(TProperties.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.players.name() + " WHERE " + Column.team_id.name() + "=?");
			pstmt.setInt(1, teamId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				sb.append("<player>");
				int playerId = rs.getInt(Column.id.name());
				String firstName = rs.getString(Column.first_name.name());
				String lastName = rs.getString(Column.last_name.name());
				sb.append("<id>" + playerId + "</id>");
				sb.append("<first_name>" + firstName + "</first_name>");
				sb.append("<last_name>" + lastName + "</last_name>");
				System.out.println("scheduleID: " + getScheduleIdByDate(getTodayDate()));
				sb.append(getCurrentPlayerScores(playerId, getScheduleIdByDate(getTodayDate())));
				sb.append("</player>");
			}
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.getPlayersForTeam(): " + e.getMessage());
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
		sb.append("</team_players>");
		return sb.toString();
	}

	/**
	 * phone_number VARCHAR(10) NOT NULL, first_name VARCHAR(255) NOT NULL, last_name VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, team_id INT(10) NOT NULL,
	 * 
	 * @param phoneNumber
	 * @param password
	 * @return
	 */
	public User userInfo(String phoneNumber)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(TProperties.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.users.name() + " WHERE " + Column.phone_number.name() + "=?");
			pstmt.setString(1, phoneNumber);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				int userId = rs.getInt(Column.id.name());
				String firstName = rs.getString(Column.first_name.name());
				String lastName = rs.getString(Column.last_name.name());
				int teamId = rs.getInt(Column.team_id.name());
				user = new User(userId, firstName, lastName, teamId, phoneNumber);
			}
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.userInfo(): " + e.getMessage());
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
	 * 
	 * @param teamId
	 * @return
	 */
	public Team teamInfo(int teamId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Team team = null;

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(TProperties.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.teams.name() + " WHERE " + Column.id.name() + "=?");
			pstmt.setInt(1, teamId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				String schoolName = rs.getString(Column.school_name.name());
				String teamName = rs.getString(Column.team_name.name());
				int leagueId = rs.getInt(Column.league_id.name());
				team = new Team(teamId, schoolName, teamName, leagueId);
			}
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.teamInfo(): " + e.getMessage());
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
		return team;
	}

	/**
	 * 
	 * @param phoneNumber
	 * @param password
	 * @return
	 */
	public boolean login(String phoneNumber, String password)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean success = false;

		try
		{
			Class.forName(TProperties.DRIVERS);
		}
		catch (Exception e)
		{
			System.out.println("1 Error loading drivers: " + e.getMessage());
		}
		try
		{
			conn = DriverManager.getConnection(TProperties.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.users.name() + " WHERE " + Column.phone_number.name() + "=? AND " + Column.password.name() + "=?");
			pstmt.setString(1, phoneNumber);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				success = true;
			}
		}
		catch (Exception e)
		{
			System.out.println("2 StatsBean.login(): " + e.getMessage());
			e.getStackTrace();
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
		return success;
	}

	public void updatePlayerScore(int playerId, int score, String change)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query;
		int scheduleId = getScheduleIdByDate(getTodayDate());
		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(TProperties.getConnection());
			query = "INSERT INTO " + Table.player_points.name() + " (" + Column.schedule_id.name() + ", " + Column.player_id.name() + ", " + getColumn(score) + ") VALUES (?, ?, 1) ON DUPLICATE KEY UPDATE " + getColumn(score) + "=" + getColumn(score) + " " + getOperator(change) + " 1";
			// query = "REPLACE INTO " + Table.player_points.name() + " SET " + getColumn(score) + "=" + getColumn(score) + " " + getOperator(change) + " 1" + " WHERE " + Column.schedule_id.name() + "=?" + " AND " + Column.player_id.name() + "=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, scheduleId);
			pstmt.setInt(2, playerId);
			pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.updatePlayerScore(): " + e.getMessage());
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
	}

	public int getScheduleIdByDate(String date)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int scheduleId = 0;

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(TProperties.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.schedule.name() + " WHERE " + Column.game_day.name() + "=?");
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				scheduleId = rs.getInt(Column.id.name());
			}
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.teamInfo(): " + e.getMessage());
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
		return scheduleId;
	}

	public String getCurrentPlayerScores(int playerId, int scheduleId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder("<current_scores>");

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(TProperties.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.player_points.name() + " WHERE " + Column.player_id.name() + "=? AND " + Column.schedule_id.name() + "=?");
			pstmt.setInt(1, playerId);
			pstmt.setInt(2, scheduleId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				int ones = rs.getInt(Column.one_points.name());
				int twos = rs.getInt(Column.two_points.name());
				int threes = rs.getInt(Column.three_points.name());
				int rebounds = rs.getInt(Column.rebounds.name());
				sb.append("<one_points>" + ones + "</one_points>");
				sb.append("<two_points>" + twos + "</two_points>");
				sb.append("<three_points>" + threes + "</three_points>");
				sb.append("<rebounds>" + rebounds + "</rebounds>");
			}
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.getPlayersForTeam(): " + e.getMessage());
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
		sb.append("</current_scores>");
		return sb.toString();
	}

	private String getOperator(String change)
	{
		switch (change)
		{
		case "increase":
			return "+";
		case "decrease":
			return "-";
		default:
			return "+";
		}
	}

	private String getColumn(int score)
	{
		switch (score)
		{
		case 1:
			return Column.one_points.name();
		case 2:
			return Column.two_points.name();
		case 3:
			return Column.three_points.name();
		default:
			return Column.one_points.name();
		}
	}

	private String getTodayDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());

		// String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	}
}
