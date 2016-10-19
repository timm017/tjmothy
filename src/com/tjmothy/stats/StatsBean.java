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
	public String login(String phoneNumber, String password)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder userString = new StringBuilder();
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
				int userId = rs.getInt(Column.id.name());
				String firstName = rs.getString(Column.first_name.name());
				String lastName = rs.getString(Column.last_name.name());
				String teamId = rs.getString(Column.team_id.name());
				userString.append("<user>");
				userString.append("<id>" + userId + "</id>");
				userString.append("<phone_number>" + firstName + "</phone_number>");
				userString.append("<first_name>" + firstName + "</first_name>");
				userString.append("<last_name>" + lastName + "</last_name>");
				userString.append("<team_id>" + teamId + "</team_id>");
				userString.append("</user>");
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
		StringBuilder sb = new StringBuilder("<login success='" + success + "'");
		sb.append(userString.toString());
		sb.append("</login>");
		return sb.toString();
	}
}
