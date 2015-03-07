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

	public enum Table
	{
		users
	}

	public enum Column
	{
		id, first_name, last_name, user_name, email, password
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

		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(TProperties.getConnection());
			pstmt = conn.prepareStatement("SELECT * FROM " + Table.users.name() + " WHERE " + Column.user_name.name() + "=? AND " + Column.password + "=?");
			pstmt.setString(1, username);
			pstmt.setString(2, Encryption.md5(password));
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
		return success;
	}
}
