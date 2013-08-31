package com.tjmothy.email;

import java.util.HashMap;

public class Email
{
	private String name = "";
	private String company = "";
	private String phone = "";
	private String comments = "";

	public Email()
	{
	}

	public Email(HashMap<String, String> hm)
	{
		init(hm);
	}

	private void init(HashMap<String, String> hm)
	{
		setName(hm.get("name"));
		setCompany(hm.get("name"));
		setPhone(hm.get("name"));
		setComments(hm.get("name"));
	}

	// Setter methods = comments
	public void setName(String name)
	{
		this.name = name;
	}

	public void setCompany(String company)
	{
		this.company = company;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	// Getter methods
	public String getName()
	{
		return this.name;
	}

	public String getCompany()
	{
		return this.company;
	}

	public String getPhone()
	{
		return this.phone;
	}

	public String getComments()
	{
		return this.comments;
	}
}
