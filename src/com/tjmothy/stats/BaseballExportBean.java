package com.tjmothy.stats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tjmothy.utils.TProperties;

public class BaseballExportBean
{
	// 32 character id
	private final String STAT_SUPPLIER_ID;
	private static final int SPORT_ID = 13;
	
	private enum Values
	{
		Jersey, NumberOfPitches
	}
	
	public enum Table
	{
		leagues, teams, players, users, player_points, schedule, team_stats
	}

	public enum Column
	{
		sport, season, team_wins, team_loses, rank, email, type, home_score, road_score, submitted, home_team, road_team, home_id, road_id, first_quarter, second_quarter, third_quarter, fourth_quarter, overtime, highlights, id, first_name, last_name, team_name, league_name, league_id, team_id, school_name, username, password, phone_number, schedule_id, game_day, player_id, one_points, one_points_attempted, two_points, three_points, rebounds, fouls, pitches, number
	}

	private TProperties tProps;

	public BaseballExportBean()
	{
		this.tProps = new TProperties();
		this.STAT_SUPPLIER_ID = tProps.getProperty(TProperties.PropertyName.stat_supplier_id.name());
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getPitchersList()
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> list = new ArrayList<>();

		try
		{
			list.add(STAT_SUPPLIER_ID);
			list.add(Values.Jersey.name() + "|" + Values.NumberOfPitches.name());
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tProps.getConnection());
			pstmt = conn.prepareStatement("SELECT p.number, pp.pitches FROM players p" +
			" LEFT JOIN player_points pp ON pp.player_id = p.id"+
	        " LEFT JOIN schedule s ON s.id = pp.schedule_id" +
	        " WHERE s.game_day = CURDATE();");
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				int number = rs.getInt(Column.number.name());
				int pitches = rs.getInt(Column.pitches.name());
				list.add(number + "|" + pitches);
			}
		}
		catch (Exception e)
		{
			System.out.println("BaseballExportBean.getPitchersList(): " + e.getMessage());
			e.printStackTrace();
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
		return list;
	}
}
