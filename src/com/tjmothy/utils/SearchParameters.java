package com.tjmothy.utils;

import java.net.URL;

public class SearchParameters
{
	private String search = "";
	private String website = "";

	public SearchParameters(String search, String website)
	{
		this.search = search;
		this.website = (!website.startsWith("http://")) ? "http://" + website : website;
		// this.search = propsLoader.getProperty(PropertyName.search.name());
		// url = new URL(propsLoader.getProperty(PropertyName.url.name()));
	}

	public String getSearch()
	{
		return search;
	}

	public void setSearch(String search)
	{
		this.search = search;
	}

	public String getWebsite()
	{
		return website;
	}

	public void setWebsite(String website)
	{
		this.website = website;
	}

}
