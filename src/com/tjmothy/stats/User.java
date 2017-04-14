package com.tjmothy.stats;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.tjmothy.email.ReminderBean.Column;

public class User
{
	private String firstName;
	private String lastName;
	private int teamId;
	private String phoneNumber;
	private int id;
	private String email;
	private int type;
	
	public User(int id, String firstName, String lastName, int teamId, String phoneNumber, String email, int type)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.teamId = teamId;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.type = type;
	}
	
	public User(ResultSet rs)
	{
		try
		{
			this.id = rs.getInt(Column.id.name());
			this.firstName = rs.getString(Column.first_name.name());
			this.lastName = rs.getString(Column.last_name.name());
			this.teamId = rs.getInt(Column.team_id.name());
			this.phoneNumber = rs.getString(Column.phone_number.name());
			this.email = rs.getString(Column.email.name());
			this.type = rs.getInt(Column.type.name());
		}
		catch (SQLException sqle)
		{
			System.out.println("User.user() -> " + sqle.getMessage());
		}
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public int getTeamId()
	{
		return teamId;
	}

	public void setTeamId(int teamId)
	{
		this.teamId = teamId;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public int getType()
	{
		return this.type;
	}
	
	public void setType(int type)
	{
		this.type = type;
	}
	
	public String toXML()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<user>");
		sb.append("<id>" + this.getId() + "</id>");
		sb.append("<phone_number>" + this.getPhoneNumber() + "</phone_number>");
		sb.append("<first_name>" + this.getFirstName() + "</first_name>");
		sb.append("<last_name>" + this.getLastName() + "</last_name>");
		sb.append("<team_id>" + this.getTeamId() + "</team_id>");
		sb.append("<email>" + this.getEmail() + "</email>");
		sb.append("<type>" + this.getType() + "</type>");
		sb.append("</user>");
		return sb.toString();
	}

}
