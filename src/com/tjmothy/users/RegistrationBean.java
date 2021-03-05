package com.tjmothy.users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.tjmothy.utils.Encryption;
import com.tjmothy.utils.TProperties;

public class RegistrationBean
{
	private User user;
	
	private TProperties tprops = new TProperties();

	public enum Column
	{
		id, first_name, last_name, user_name, email, password
	}

	public RegistrationBean(User user)
	{
		this.user = user;
	}

	public boolean addUser()
	{
		boolean success = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		// TODO: Get this fucking properties file shit to work!
		// It can find the file but it every property seems to be NULL
		// private TProperties props = TProperties.getInstance();
		// String url = props.getProperty(TProperties.PropertyName.url.name());
		// String user =
		// props.getProperty(TProperties.PropertyName.user.name());
		// String password =
		// props.getProperty(TProperties.PropertyName.password.name());
		// String drivers = TProperties.PropertyName.drivers.name();
		
		System.out.println("drivers: " + tprops.getProperty("drivers"));
		try
		{
			Class.forName(TProperties.DRIVERS);
			conn = DriverManager.getConnection(tprops.getConnection());
			pstmt = conn.prepareStatement("INSERT INTO users (" + Column.first_name.name() + ", " + Column.last_name.name() + ", " + Column.user_name.name() + ", " + Column.email.name() + ", " + Column.password.name() + ") VALUES (?, ?, ?, ?, ?)");
			pstmt.setString(1, this.user.getFirstName());
			pstmt.setString(2, this.user.getLastName());
			pstmt.setString(3, this.user.getUserName());
			pstmt.setString(4, this.user.getEmail());
			pstmt.setString(5, Encryption.md5(this.user.getPassword()));
			pstmt.executeUpdate();
			success = true;
		}
		catch (Exception e)
		{
			System.out.println("RegistrationBean.addUser(): " + e.getMessage());
		}
		finally
		{
			try
			{
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception e)
			{
				;
			}
		}
		return success;
	}
}
