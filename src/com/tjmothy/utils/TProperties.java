package com.tjmothy.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.util.Properties;

public class TProperties extends Properties
{
	private static final long serialVersionUID = 1L;

	private static Properties instance = null;

	private final static String PROP_FILE_NAME = "tjmdb.properties";
	// Database variables
	public final static String URL = "jdbc:mysql://localhost/stats";
	public final static String USER = "root";
	public final static String PASSWORD = "";
	public final static String DRIVERS = "com.mysql.jdbc.Driver";

	public enum PropertyName
	{
		url, password, user, drivers
	}

	public static Properties getInstance()
	{
		if (instance == null)
		{
			instance = new TProperties();
			init();
		}
		return instance;
	}

	public TProperties()
	{
		InputStream inputStream = null;
		try
		{
			// inputStream = new FileInputStream("/" + PROP_FILE_NAME);
			// inputStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME);
			inputStream = getClass().getResourceAsStream("tjmdb.properties");
			//System.out.println("IS: " + getStringFromInputStream(inputStream));
			if (inputStream != null)
			{
				instance.load(inputStream);
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
			System.err.println("inputStream == null");
		}
	}

	private static void init()
	{
		InputStream inputStream = null;
		try
		{
			inputStream = new FileInputStream(PROP_FILE_NAME);
			if (inputStream != null)
			{
				instance.load(inputStream);
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
			System.err.println("inputStream == null");
		}
	}

	// convert InputStream to String
	private static String getStringFromInputStream(InputStream is)
	{

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try
		{

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null)
			{
				sb.append(line);
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}
	
	/**
	 * Gets the connection URL for the MySQL database.
	 * @return
	 */
	public static String getConnection()
	{
		return URL + "?" + "user=" + USER + "&password=" + PASSWORD;
	}

}
