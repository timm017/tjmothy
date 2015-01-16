package com.tjmothy.users;

import javax.servlet.http.HttpServletRequest;

public class User
{
	private String firstName = "";
	private String lastName = "";
	private String userName = "";
	private String email = "";

	public User(HttpServletRequest request)
	{
		populate(request);
	}
	
	private void populate(HttpServletRequest request)
	{
		setFirstName(request.getParameter("firstName"));
		setLastName(request.getParameter("lasttName"));
		setUserName(request.getParameter("userName"));
		setEmail(request.getParameter("email"));
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

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
}
