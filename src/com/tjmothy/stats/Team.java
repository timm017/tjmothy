package com.tjmothy.stats;

public class Team
{
	private int id;
	private String schoolName;
	private String teamName;
	private int leagueId;
	private boolean isHomeTeam;
	private int sportId;
	
	public Team(int id, String schoolName, String teamName, int leagueId, boolean isHomeTeam, int sportId)
	{
		this.id = id;
		this.schoolName = schoolName;
		this.teamName = teamName;
		this.leagueId = leagueId;
		this.isHomeTeam = isHomeTeam;
		this.sportId = sportId;
	}
	
	public String getSchoolName()
	{
		return schoolName;
	}

	public void setSchoolName(String schoolName)
	{
		this.schoolName = schoolName;
	}

	public String getTeamName()
	{
		return teamName;
	}

	public void setTeamName(String teamName)
	{
		this.teamName = teamName;
	}

	public int getLeagueId()
	{
		return leagueId;
	}

	public void setLeagueId(int leagueId)
	{
		this.leagueId = leagueId;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
	
	public boolean getIsHomeTeam()
	{
		return this.isHomeTeam;
	}

	public int getSportId()
	{
		return this.sportId;
	}

	public void setSportId(int sportId)
	{
		this.sportId = sportId;
	}
	
	public String toXML()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<team>");
		sb.append("<id>" + this.getId() + "</id>");
		sb.append("<school_name>" + this.getSchoolName() + "</school_name>");
		sb.append("<team_name>" + this.getTeamName() + "</team_name>");
		sb.append("<league_id>" + this.getLeagueId() + "</league_id>");
		sb.append("<is_home_team>" + this.getIsHomeTeam() + "</is_home_team>");
		sb.append("<sport_id>" + this.getSportId() + "</sport_id>");
		sb.append("</team>");
		return sb.toString();
	}

}
