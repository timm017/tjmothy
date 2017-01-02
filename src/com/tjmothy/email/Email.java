package com.tjmothy.email;

import java.util.ArrayList;

public class Email
{
	private String subject = "Basketball stats";

	private String body = "";

	private ArrayList<String> recipients = new ArrayList<>();

	public Email()
	{
		recipients.add("mckeown.timothy@gmail.com");
		recipients.add("rwhiggins2@gmail.com");
//		recipients.add("sports@delcotimes.com");
//		recipients.add("highschoolscores@philly.com");
		// recipients.add("spiff10@verizon.net");
		// recipients.add("kevindank@gmail.com");
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
