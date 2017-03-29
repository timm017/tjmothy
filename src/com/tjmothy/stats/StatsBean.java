package com.tjmothy.stats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.tjmothy.users.LogInOutBean;
import com.tjmothy.utils.TProperties;

public class StatsBean
{

	public enum Table
	{
		leagues, teams, players, users, player_points, schedule, team_stats
	}

	public enum Column
	{
		sport, season, team_wins, team_loses, rank, email, type, home_score, road_score, submitted, home_team, road_team, home_id, road_id, first_quarter, second_quarter, third_quarter, fourth_quarter, overtime, highlights, id, first_name, last_name, team_name, league_name, league_id, team_id, school_name, username, password, phone_number, schedule_id, game_day, player_id, one_points, one_points_attempted, two_points, three_points, rebounds, fouls, pitches;
	}

	private TProperties tProps;

	public StatsBean()
	{
		this.tProps = new TProperties();
	}

	public String getPlayersForTeam(int teamId, boolean myTeam)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String elementName = (myTeam) ? "team_players" : "enemy_team_players";
		StringBuilder sb = new StringBuilder("<" + elementName + " id='" + teamId + "'>");

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
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
		sb.append("</" + elementName + ">");
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
			conn = DriverManager.getConnection(tProps.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.users.name() + " WHERE " + Column.phone_number.name() + "=? OR " + Column.email.name() + "=?");
			pstmt.setString(1, phoneNumber);
			pstmt.setString(2, phoneNumber);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				int userId = rs.getInt(Column.id.name());
				String firstName = rs.getString(Column.first_name.name());
				String lastName = rs.getString(Column.last_name.name());
				int teamId = rs.getInt(Column.team_id.name());
				String email = rs.getString(Column.email.name());
				int type = rs.getInt(Column.type.name());
				user = new User(userId, firstName, lastName, teamId, phoneNumber, email, type);
			}
			if (user == null)
			{
				user = new User(0, "", "", getBaseballTeamId(phoneNumber), phoneNumber, "", 1);
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
	 * Gets the info for a specific game.
	 * 
	 * @param teamId
	 * @param date
	 * @return
	 */
	public Game gameInfo(int teamId, String date)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Game game = null;
		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			String query = "SELECT * FROM " + Table.schedule.name() + " WHERE " + Column.game_day.name() + "=? AND (" + Column.home_id.name() + "=? OR " + Column.road_id.name() + "=?)";
			System.out.println("SELECT * FROM " + Table.schedule.name() + " WHERE " + Column.game_day.name() + "='" + date + "' AND (" + Column.home_id.name() + "=" + teamId + " OR " + Column.road_id.name() + "=" + teamId + ")");
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, date);
			pstmt.setInt(2, teamId);
			pstmt.setInt(3, teamId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				int scheduleId = rs.getInt(Column.id.name());
				int homeId = rs.getInt(Column.home_id.name());
				String homeSchool = rs.getString(Column.home_team.name());
				int roadId = rs.getInt(Column.road_id.name());
				String roadSchool = rs.getString(Column.road_team.name());
				int sport = rs.getInt(Column.sport.name());
				game = new Game(scheduleId, homeId, roadId, homeSchool, roadSchool, date, sport);
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
		// If game is NULL then we couldn't find a game
		if (game == null)
		{
			game = new Game();
			game.setNoGameToday(true);
		}
		return game;
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
			conn = DriverManager.getConnection(tProps.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.teams.name() + " WHERE " + Column.id.name() + "=?");
			pstmt.setInt(1, teamId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				String schoolName = rs.getString(Column.school_name.name());
				String teamName = rs.getString(Column.team_name.name());
				int leagueId = rs.getInt(Column.league_id.name());
				team = new Team(teamId, schoolName, teamName, leagueId, isHomeTeam(teamId, getTodayDate()));
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
			conn = DriverManager.getConnection(this.tProps.getConnection());
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

	public void updatePlayerScore(int playerId, String score, String change)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query;
		int scheduleId = getScheduleIdByDate(getTodayDate());
		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			query = "INSERT INTO " + Table.player_points.name() + " (" + Column.schedule_id.name() + ", " + Column.player_id.name() + ", " + getPlayerColumn(score) + ") VALUES (?, ?, 1) ON DUPLICATE KEY UPDATE " + getPlayerColumn(score) + "=" + getPlayerColumn(score) + " " + getOperator(change) + " 1";
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

	public void updateHighlights(String highlights, int teamId, int scheduleId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query;
		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			query = "INSERT INTO " + Table.team_stats.name() + " (" + Column.schedule_id.name() + ", " + Column.team_id.name() + ", " + Column.highlights.name() + ") VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE " + Column.highlights.name() + "=?";
			// query = "REPLACE INTO " + Table.player_points.name() + " SET " + getColumn(score) + "=" + getColumn(score) + " " + getOperator(change) + " 1" + " WHERE " + Column.schedule_id.name() + "=?" + " AND " + Column.player_id.name() + "=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, scheduleId);
			pstmt.setInt(2, teamId);
			pstmt.setString(3, highlights);
			pstmt.setString(4, highlights);
			pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.updateHighlights(): " + e.getMessage());
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

	/**
	 * 
	 * mysql> describe team_stats; +----------------+--------------+------+-----+---------+-------+ | Field | Type | Null | Key | Default | Extra | +----------------+--------------+------+-----+---------+-------+ | schedule_id | int(10) | NO | PRI | 0 | | | team_id | int(10) | NO | PRI | 0 | | | first_quarter | int(2) | NO | | 0 | | | second_quarter | int(2) | NO | | 0 | | | third_quarter | int(2) | NO | | 0 | | | fourth_quarter | int(2) | NO | | 0 | | | overtime | int(2) | NO | | 0 | | | highlights | varchar(255) | NO | | NULL | | +----------------+--------------+------+-----+---------+-------+
	 * 
	 * @param quarter
	 * @param score
	 * @param teamId
	 * @param scheduleId
	 */
	public void updateBoxScore(String quarter, int score, int teamId, int scheduleId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query;
		System.out.println("q: " + quarter);
		System.out.println("sco: " + score);
		System.out.println("t: " + teamId);
		System.out.println("sch: " + scheduleId);
		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			query = "INSERT INTO " + Table.team_stats.name() + " (" + Column.schedule_id.name() + ", " + Column.team_id.name() + ", " + getTeamColumn(quarter) + ") VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE " + getTeamColumn(quarter) + "=?";
			// query = "REPLACE INTO " + Table.player_points.name() + " SET " + getColumn(score) + "=" + getColumn(score) + " " + getOperator(change) + " 1" + " WHERE " + Column.schedule_id.name() + "=?" + " AND " + Column.player_id.name() + "=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, scheduleId);
			pstmt.setInt(2, teamId);
			pstmt.setInt(3, score);
			pstmt.setInt(4, score);
			pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.updateBoxScore(): " + e.getMessage());
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
			conn = DriverManager.getConnection(tProps.getConnection());
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
			conn = DriverManager.getConnection(tProps.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.player_points.name() + " WHERE " + Column.player_id.name() + "=? AND " + Column.schedule_id.name() + "=?");
			pstmt.setInt(1, playerId);
			pstmt.setInt(2, scheduleId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				int ones = rs.getInt(Column.one_points.name());
				int onesAttempted = rs.getInt(Column.one_points_attempted.name());
				int twos = rs.getInt(Column.two_points.name());
				int threes = rs.getInt(Column.three_points.name());
				int fouls = rs.getInt(Column.fouls.name());
				int rebounds = rs.getInt(Column.rebounds.name());
				sb.append("<one_points>" + ones + "</one_points>");
				sb.append("<one_points_attempted>" + onesAttempted + "</one_points_attempted>");
				sb.append("<two_points>" + twos + "</two_points>");
				sb.append("<three_points>" + threes + "</three_points>");
				sb.append("<fouls>" + fouls + "</fouls>");
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

	public String getCurrentTeamScores(int teamId, int scheduleId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder("<current_team_scores>");

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.team_stats.name() + " WHERE " + Column.team_id.name() + "=? AND " + Column.schedule_id.name() + "=?");
			pstmt.setInt(1, teamId);
			pstmt.setInt(2, scheduleId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				int firstQuarter = rs.getInt(Column.first_quarter.name());
				int secondQuarter = rs.getInt(Column.second_quarter.name());
				int thirdQuarter = rs.getInt(Column.third_quarter.name());
				int fourthQuarter = rs.getInt(Column.fourth_quarter.name());
				int overtime = rs.getInt(Column.overtime.name());
				String highlights = rs.getString(Column.highlights.name());
				sb.append("<first_quarter>" + firstQuarter + "</first_quarter>");
				sb.append("<second_quarter>" + secondQuarter + "</second_quarter>");
				sb.append("<third_quarter>" + thirdQuarter + "</third_quarter>");
				sb.append("<fourth_quarter>" + fourthQuarter + "</fourth_quarter>");
				sb.append("<overtime>" + overtime + "</overtime>");
				sb.append("<highlights>" + highlights + "</highlights>");
			}
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.getCurrentTeamScores(): " + e.getMessage());
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
		sb.append("</current_team_scores>");
		return sb.toString();
	}

	public void updateTeamScore(int teamId, String score, String quarter)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query;
		int scheduleId = getScheduleIdByDate(getTodayDate());
		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			query = "UPDATE " + Table.team_stats.name() + " SET(" + Column.schedule_id.name() + ", " + Column.player_id.name();
			// query = "REPLACE INTO " + Table.player_points.name() + " SET " + getColumn(score) + "=" + getColumn(score) + " " + getOperator(change) + " 1" + " WHERE " + Column.schedule_id.name() + "=?" + " AND " + Column.player_id.name() + "=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, scheduleId);
			pstmt.setInt(2, teamId);
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

	public int getTeamTotalScoreFromQuarters(int teamId, int scheduleId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int total = 0;

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.team_stats.name() + " WHERE " + Column.team_id.name() + "=? AND " + Column.schedule_id.name() + "=?");
			pstmt.setInt(1, teamId);
			pstmt.setInt(2, scheduleId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				total += rs.getInt(Column.first_quarter.name());
				total += rs.getInt(Column.second_quarter.name());
				total += rs.getInt(Column.third_quarter.name());
				total += rs.getInt(Column.fourth_quarter.name());
				total += rs.getInt(Column.overtime.name());
			}
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.getCurrentTeamScores(): " + e.getMessage());
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
		return total;
	}

	public int getTeamTotalScore(int teamId, int scheduleId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int total = 0;
		String awayHomeColumn;
		String awayHomeId;
		if (isHomeTeamByScheduleId(teamId, scheduleId))
		{
			awayHomeColumn = Column.home_score.name();
			awayHomeId = Column.home_id.name();
		}
		else
		{
			awayHomeColumn = Column.road_score.name();
			awayHomeId = Column.road_id.name();
		}

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			pstmt = conn.prepareStatement("SELECT " + awayHomeColumn + " FROM " + Table.schedule.name() + " WHERE " + awayHomeId + "=? AND " + Column.id.name() + "=?");
			String query = "SELECT " + awayHomeColumn + " FROM " + Table.schedule.name() + " WHERE " + awayHomeId + "=" + teamId + " AND " + Column.id.name() + "=" + scheduleId;
			System.out.println(query);
			pstmt.setInt(1, teamId);
			pstmt.setInt(2, scheduleId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				total = rs.getInt(awayHomeColumn);
				System.out.println("total: " + total);
			}
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.getTeamTotalScore(): " + e.getMessage());
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
		return total;
	}

	/**
	 * Sets the submitted field to 1
	 * 
	 * @param teamId
	 * @param scheduleId
	 */
	public void submitTeamStats(int teamId, int scheduleId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query;
		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			query = "UPDATE " + Table.team_stats.name() + " SET " + Column.submitted.name() + "=1 WHERE " + Column.team_id.name() + "=? AND + " + Column.schedule_id.name() + "=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, teamId);
			pstmt.setInt(2, scheduleId);
			pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			System.err.println("StatsBean.updatePlayerScore(): " + e.getMessage());
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

	/**
	 * 
	 * @param teamId
	 * @param scheduleId
	 * @return
	 */
	public boolean isGameSubmitted(int teamId, int scheduleId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean submitted = false;

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.team_stats.name() + " WHERE " + Column.team_id.name() + "=? AND " + Column.schedule_id.name() + "=? AND " + Column.submitted.name() + "=1");
			pstmt.setInt(1, teamId);
			pstmt.setInt(2, scheduleId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				submitted = true;
			}
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.getCurrentTeamScores(): " + e.getMessage());
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
		System.out.println("isGameSubmitted: " + submitted + " team: " + teamId + " sch: " + scheduleId);
		return submitted;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	private boolean isHomeTeam(int teamId, String gameDay)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean isHomeTeam = false;

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.schedule.name() + " WHERE " + Column.game_day.name() + "=? AND " + Column.home_id.name() + "=?");
			pstmt.setString(1, gameDay);
			pstmt.setInt(2, teamId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				isHomeTeam = true;
			}
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.isHomeTeam(): " + e.getMessage());
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
		return isHomeTeam;
	}

	private boolean isHomeTeamByScheduleId(int teamId, int scheduleId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean isHomeTeam = false;

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.schedule.name() + " WHERE " + Column.id.name() + "=? AND " + Column.home_id.name() + "=?");
			pstmt.setInt(1, scheduleId);
			pstmt.setInt(2, teamId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				isHomeTeam = true;
			}
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.isHomeTeamByScheduleId(): " + e.getMessage());
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
		return isHomeTeam;
	}

	/**
	 * 
	 * @param total
	 * @param scheduleId
	 * @param team
	 */
	public void submitTeamTotal(int total, int scheduleId, Team team)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query;

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			query = "UPDATE " + Table.schedule.name() + " SET " + (team.getIsHomeTeam() ? Column.home_score.name() : Column.road_score.name()) + "=? WHERE " + Column.id.name() + "=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, total);
			pstmt.setInt(2, scheduleId);
			pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.submitTeamTotal(): " + e.getMessage());
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

	private String getPlayerColumn(String score)
	{
		switch (score)
		{
		case "1":
			return Column.one_points.name();
		case "1a":
			return Column.one_points_attempted.name();
		case "2":
			return Column.two_points.name();
		case "3":
			return Column.three_points.name();
		case "f":
			return Column.fouls.name();
		case "r":
			return Column.rebounds.name();
		default:
			return Column.one_points.name();
		}
	}

	private String getTeamColumn(String quarter)
	{
		switch (quarter)
		{
		case "q1":
			return Column.first_quarter.name();
		case "q2":
			return Column.second_quarter.name();
		case "q3":
			return Column.third_quarter.name();
		case "q4":
			return Column.fourth_quarter.name();
		case "ot":
			return Column.overtime.name();
		default:
			return Column.first_quarter.name();
		}
	}

	public static String getTodayDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());

		// String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	}

	public boolean isValidPhoneNumber(String phoneNumber)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean isValid = false;

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.users.name() + " WHERE " + Column.phone_number.name() + "=? OR " + Column.email.name() + "=?");
			pstmt.setString(1, phoneNumber);
			pstmt.setString(2, phoneNumber);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				isValid = true;
			}
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.isHomeTeam(): " + e.getMessage());
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
		return isValid;
	}

	/**
	 * Update either the win or loss column for each playing team.
	 * 
	 * @param teamId
	 * @param won
	 */
	public void updateWinLoss(int teamId, boolean win)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query;
		String column = (win) ? Column.team_wins.name() : Column.team_loses.name();
		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			query = "UPDATE " + Table.teams.name() + " SET " + column + "=" + column + " + 1 WHERE " + Column.id.name() + "=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, teamId);
			pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.updateWinLoss(): " + e.getMessage());
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

	/**
	 * 
	 */
	public void updateAllRanksForAllTeams(int sport, int season)
	{
		System.out.println("Updating ALL team ranks ");
		// getTeams (sport, season)
		ArrayList<Integer> teamIds = getTeamsForRankings(sport, season);
		Thread thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				for (Integer teamId : teamIds)
				{
					updateAllRanksForTeam(teamId, sport);
				}
			}

		});
		thread.start();
	}

	/**
	 * Updates: Schedule Points Bonus Points Rank value
	 * 
	 * @param teamId
	 * @param season
	 */
	public void updateAllRanksForTeam(int teamId, int sport)
	{
		if (sport == LogInOutBean.BASEBALL_ID)
		{

		}
		else
		{
			updateTeamSchedulePoints(teamId);
			updateTeamBonusPoints(teamId);
			updateTeamRank(teamId);
		}
	}

	/**
	 * 
	 * @param teamId
	 */
	private void updateTeamSchedulePoints(int teamId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query;
		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			query = "UPDATE teams SET sch_pts = " + "(SELECT " + "IFNULL(SUM(IF(home_id = ? AND home_score <> 0, 5 * team_wins/(team_wins + team_loses),0) " + "+ IF(road_id = ? AND road_score <> 0, 5 * team_wins/(team_wins + team_loses),0)), 0) " + "FROM schedule INNER JOIN (SELECT * FROM teams) AS temp " + "ON schedule.home_id = ? AND temp.id = schedule.road_id "
					+ "OR schedule.road_id = ? AND temp.id = schedule.home_id) " + "WHERE id = ?;";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, teamId);
			pstmt.setInt(2, teamId);
			pstmt.setInt(3, teamId);
			pstmt.setInt(4, teamId);
			pstmt.setInt(5, teamId);
			pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.updateTeamSchedulePoints(): " + e.getMessage());
		}
		finally
		{
			try
			{
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception e)
			{
				;
			}
		}
	}

	/**
	 * 
	 * @param teamId
	 */
	private void updateTeamBonusPoints(int teamId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query;
		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			query = "update teams " + "set bns_pts = " + "(select " + "IFNULL(sum(if(home_id = ? AND home_score>road_score, " + "12 * team_wins/(team_wins+team_loses), " + "if(road_id = ? AND road_score > home_score, " + "12 * team_wins/(team_wins + team_loses), 0))), 0) " + "from schedule inner join (select * from teams) as temp "
					+ "on schedule.home_id = ? and temp.id = schedule.road_id " + "or schedule.road_id = ? and temp.id = schedule.home_id) " + "WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, teamId);
			pstmt.setInt(2, teamId);
			pstmt.setInt(3, teamId);
			pstmt.setInt(4, teamId);
			pstmt.setInt(5, teamId);
			pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			System.err.println("StatsBean.updateTeamBonusPoints(): " + e.getMessage());
		}
		finally
		{
			try
			{
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception e)
			{
				;
			}
		}
	}

	/**
	 * 
	 * @param teamId
	 */
	private void updateTeamRank(int teamId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query;
		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			query = "update teams " + "set rank = " + "(SELECT IFNULL((team_wins * 5 + sch_pts + bns_pts)/(team_wins + team_loses), 0)) " + "where id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, teamId);
			pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			System.err.println("StatsBean.updateTeamRank(): " + e.getMessage());
		}
		finally
		{
			try
			{
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception e)
			{
				;
			}
		}
	}

	/**
	 * Gets all the team IDS for that are going to have their power ranking updated
	 * 
	 * @param sport
	 * @param season
	 * @return
	 */
	public ArrayList<Integer> getTeamsForRankings(int sport, int season)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Integer> teams = new ArrayList<>();
		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			pstmt = conn.prepareStatement("SELECT " + Column.id.name() + " FROM " + Table.teams.name() + " WHERE " + Column.sport.name() + "=? AND " + Column.season.name() + "=?");
			pstmt.setInt(1, sport);
			pstmt.setInt(2, season);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				teams.add(rs.getInt(Column.id.name()));
			}
		}
		catch (SQLException sqle)
		{
			System.err.println("1 StatsBean.getTeamsForRankings(): " + sqle.getMessage());
		}
		catch (Exception e)
		{
			System.err.println("2 StatsBean.getTeamsForRankings(): " + e.getMessage());
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
		return teams;
	}

	/***********************************************
	 * BASEBALL
	 ***********************************************/
	public int getBaseballTeamId(String username)
	{
		int teamId = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			pstmt = conn.prepareStatement("SELECT id FROM " + Table.teams.name() + " WHERE " + Column.school_name.name() + "=? AND " + Column.sport.name() + "=?");
			pstmt.setString(1, username);
			pstmt.setInt(2, LogInOutBean.BASEBALL_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				teamId = rs.getInt(Column.id.name());
			}
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.getBaseballTeamId(): " + e.getMessage());
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
		return teamId;
	}

	public void updateTeamTotal(int total, int teamId, int scheduleId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query;
		String homeAwayColumn = (isHomeTeamByScheduleId(teamId, scheduleId)) ? Column.home_score.name() : Column.road_score.name();
		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			query = "UPDATE " + Table.schedule.name() + " SET " + homeAwayColumn + "=? WHERE " + Column.id.name() + "=?";
			System.out.println("UPDATE " + Table.schedule.name() + " SET " + homeAwayColumn + "=" + total + " WHERE " + Column.id.name() + "=" + scheduleId);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, total);
			pstmt.setInt(2, scheduleId);
			pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.updateTeamTotal(): " + e.getMessage());
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
	
	public void updatePitchTotal(int playerId, int pitches, int scheduleId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query;
		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			query = "INSERT INTO " + Table.player_points.name() + " (" + Column.schedule_id.name() + ", " + Column.player_id.name() + ", " + Column.pitches.name() + ") VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE " + Column.pitches.name() + "=?";
			// query = "REPLACE INTO " + Table.player_points.name() + " SET " + getColumn(score) + "=" + getColumn(score) + " " + getOperator(change) + " 1" + " WHERE " + Column.schedule_id.name() + "=?" + " AND " + Column.player_id.name() + "=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, scheduleId);
			pstmt.setInt(2, playerId);
			pstmt.setInt(3, pitches);
			pstmt.setInt(4, pitches);
			pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			System.out.println("StatsBean.updatePitchTotal(): " + e.getMessage());
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
}
