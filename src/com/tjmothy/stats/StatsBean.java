package com.tjmothy.stats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tjmothy.utils.Encryption;
import com.tjmothy.utils.TProperties;

public class StatsBean
{
	public enum Table
	{
		leagues, teams, players, users
	}

	public enum Column
	{
		id, first_name, last_name, team_name, league_name, league_id, team_id, school_name, username, password, phone_number
	}

	public StatsBean()
	{

	}

	public String getPlayersForTeam(int teamId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder("<team id='" + teamId + "'>");

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
		sb.append("</team>");
		return sb.toString();
	}
	
	/**
	 * 	phone_number VARCHAR(10) NOT NULL,
	 *  first_name VARCHAR(255) NOT NULL,
	 *  last_name VARCHAR(255) NOT NULL,
	 *  password VARCHAR(255) NOT NULL,
	 *  team_id INT(10) NOT NULL,
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
			System.out.println("StatsBean.login(): " + e.getMessage());
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
}
