package com.tjmothy.users;

import javax.servlet.http.HttpServletRequest;

public class User
{
	private String firstName = "";
	private String lastName = "";
	private String userName = "";
	private String email = "";
	private String password = "";
	private String passwordRepeat = "";

	public User(HttpServletRequest request)
	{
		populateUser(request);
	}

	private void populateUser(HttpServletRequest request)
	{
		setFirstName((request.getParameter("firstName") == null) ? "" : request.getParameter("firstName"));
		setLastName((request.getParameter("lastName") == null) ? "" : request.getParameter("lastName"));
		setUserName((request.getParameter("userName") == null) ? "" : request.getParameter("userName"));
		setEmail((request.getParameter("email") == null) ? "" : request.getParameter("email"));
		setPassword((request.getParameter("password") == null) ? "" : request.getParameter("password"));
		setPasswordRepeat((request.getParameter("passwordRepeat") == null) ? "" : request.getParameter("passwordRepeat"));
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

	public String getPassword()
	{
		return this.password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setPasswordRepeat(String passwordRepeat)
	{
		this.passwordRepeat = passwordRepeat;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String toString()
	{
		return "first: " + this.firstName + "\n last: " + this.lastName + "\n user: " + this.userName + "\n emaill: " + this.email + "\n password1: " + this.password + "\n password2: " + this.passwordRepeat;
	}
}
