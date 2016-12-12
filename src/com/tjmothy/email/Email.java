package com.tjmothy.email;

import java.util.ArrayList;

public class Email
{
	private String subject = "Penncrest stats";
	
	private String body = "<body><p>Tim McKeown</p><p>7 points</p><p>Bob Higgins</p><p>100 points</p></body>";
	
	private ArrayList<String> recipients = new ArrayList<>(); 

	public Email()
	{
		recipients.add("mckeown.timothy@gmail.com");
		recipients.add("tmckeown@unboundmedicine.com");
	}

	// Setter methods = comments
	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getSubject()
	{
		return this.subject;
	}
	
	public void setBody(String body)
	{
		this.body = body;
	}
	
	public String getBody()
	{
		return this.body;
	}
	
	public ArrayList<String> getRecipients()
	{
		return this.recipients;
	}
}
