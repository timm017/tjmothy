package com.tjmothy.users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tjmothy.utils.Encryption;
import com.tjmothy.utils.TProperties;

public class LogInOutBean
{
	private User user;

	private TProperties tprops = new TProperties();

	public static final int BASEBALL_ID = 13;

	private final String BASEBALL_PASSWORD = "baseball";

	public enum Table
	{
		users, teams
	}

	public enum Column
	{
		id, first_name, last_name, user_name, email, password, phone_number, sport, school_name
	}

	public LogInOutBean()
	{

	}

	public LogInOutBean(User user)
	{
		this.user = user;
	}

	public boolean logIn(String username, String password)
	{
		boolean success = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println("regular login: " + username + " : " + password);

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tprops.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.users.name() + " WHERE (" + Column.phone_number.name() + "=? OR "  + Column.email.name() + "=?) AND " + Column.password + "=?");
			pstmt.setString(1, username);
			pstmt.setString(2, username);
			pstmt.setString(3, Encryption.md5(password));
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				success = true;
			}
		}
		catch (Exception e)
		{
			System.out.println("LogInOutBean.logIn(): " + e.getMessage());
		}
		finally
		{
			try
			{
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
			}
			catch (Exception e)
			{
				;
			}
		}
		System.out.println("reg login: " + success);
		return success;
	}

	/**
	 * Currently there are no users made for baseball coaches so we will just hack some logins together. In this login method we use username as school_name and password is 'baseball'.
	 * 
	 * @param username
	 *            - school name
	 * @param password
	 *            - baseball
	 * @return
	 */
	public boolean logInBaseball(String username, String password)
	{
		boolean success = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println("trying to login: " + username + " p: " + password);

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tprops.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.teams.name() + " WHERE " + Column.school_name.name() + "=? AND " + Column.sport.name() + "=?");
			pstmt.setString(1, username);
			pstmt.setInt(2, BASEBALL_ID);
			rs = pstmt.executeQuery();
			if (password.equalsIgnoreCase(BASEBALL_PASSWORD))
			{
				while (rs.next())
				{
					success = true;
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("LogInOutBean.logInBaseball(): " + e.getMessage());
		}
		finally
		{
			try
			{
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
			}
			catch (Exception e)
			{
				;
			}
		}
		return success;
	}
}
