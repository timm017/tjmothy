package com.tjmothy.stats;

public class Game
{
	private int scheduleId;
	private int homeTeamId;
	private String homeTeamSchool;
	private int awayTeamId;
	private String awayTeamSchool;
	private String gameDay;
	private boolean noGameToday = false;

	public Game(int scheduleId, int homeTeamId, int awayTeamId, String homeTeamSchool, String awayTeamSchool, String gameDay)
	{
		this.scheduleId = scheduleId;
		this.homeTeamId = homeTeamId;
		this.homeTeamSchool = homeTeamSchool;
		this.awayTeamId = awayTeamId;
		this.awayTeamSchool = awayTeamSchool;
		this.gameDay = gameDay;
	}
	
	public Game()
	{
		
	}

	public boolean getNoGameToday()
	{
		return this.noGameToday;
	}
	
	public void setNoGameToday(boolean noGameToday)
	{
		this.noGameToday = noGameToday;
	}
	
	public void setGameDay(String gameDay)
	{
		this.gameDay = gameDay;
	}
	
	public String getGameDay()
	{
		return this.gameDay;
	}
	
	public int getScheduleId()
	{
		return scheduleId;
	}

	public void setScheduleId(int scheduleId)
	{
		this.scheduleId = scheduleId;
	}

	public int getHomeTeamId()
	{
		return homeTeamId;
	}

	public void setHomeTeamId(int homeTeamId)
	{
		this.homeTeamId = homeTeamId;
	}

	public String getHomeTeamSchool()
	{
		return homeTeamSchool;
	}

	public void setHomeTeamSchool(String homeTeamSchool)
	{
		this.homeTeamSchool = homeTeamSchool;
	}

	public int getAwayTeamId()
	{
		return awayTeamId;
	}

	public void setAwayTeamId(int awayTeamId)
	{
		this.awayTeamId = awayTeamId;
	}

	public String getAwayTeamSchool()
	{
		return awayTeamSchool;
	}

	public void setAwayTeamSchool(String awayTeamSchool)
	{
		this.awayTeamSchool = awayTeamSchool;
	}

	public String toXML()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<game>");
		sb.append("<schedule_id>" + this.getScheduleId() + "</schedule_id>");
		sb.append("<home_team_id>" + this.getHomeTeamId() + "</home_team_id>");
		sb.append("<home_team_school>" + this.getHomeTeamSchool() + "</home_team_school>");
		sb.append("<away_team_id>" + this.getAwayTeamId() + "</away_team_id>");
		sb.append("<away_team_school>" + this.getAwayTeamSchool() + "</away_team_school>");
		sb.append("<no_game_today>" + this.getNoGameToday() + "</no_game_today>");
		sb.append("</game>");
		return sb.toString();
	}

}
