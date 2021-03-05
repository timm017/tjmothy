package com.tjmothy.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class PageParser
{
	private String body = "";
	private URL url;
	private SearchParameters searchParameters;

	public PageParser(SearchParameters searchParameters)
	{
		this.searchParameters = searchParameters;
		try
		{
			url = new URL(searchParameters.getWebsite());
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();
			encoding = encoding == null ? "UTF-8" : encoding;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[8192];
			int len = 0;
			while ((len = in.read(buf)) != -1)
			{
				baos.write(buf, 0, len);
			}
			body = new String(baos.toByteArray(), encoding);
		}
		catch (MalformedURLException murle)
		{
			murle.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public boolean foundIt()
	{
		if (body.contains(this.searchParameters.getSearch()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
