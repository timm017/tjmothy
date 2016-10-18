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
		leagues, teams, players
	}

	public enum Column
	{
		id, first_name, last_name, team_name, league_name, league_id, team_id
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
}
