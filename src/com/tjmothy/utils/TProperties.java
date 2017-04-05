package com.tjmothy.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TProperties extends Properties
{
	private static final long serialVersionUID = 1L;

	private final static String PROP_FILE_NAME = "tjmdb.properties";

	public final static String DRIVERS = "com.mysql.jdbc.Driver";

	public enum PropertyName
	{
		url, password, user, drivers, gmail_password, stat_supplier_id
	}
	
	public TProperties()
	{
		InputStream inputStream = null;
		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			inputStream = classLoader.getResourceAsStream(PROP_FILE_NAME);
			if (inputStream != null)
			{
				load(inputStream);
				inputStream.close();
			}
		}
		catch (FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		if (inputStream == null)
		{
			System.out.println("inputStream == null");
		}
	}

	public String getProperty(PropertyName propertyName)
	{
		String propertyValue = "";
		propertyValue = this.getProperty(propertyName.name());
		return propertyValue;
	}

	/**
	 * Gets the connection URL for the MySQL database
	 * 
	 * @return
	 */
	public String getConnection()
	{
		return getProperty(PropertyName.url) + "?" + "user=" + getProperty(PropertyName.user) + "&password=" + getProperty(PropertyName.password);
	}

}
