package com.tjmothy.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Handles all encryption methods.
 * 
 * @author tmckeown
 * 
 */
public class Encryption
{
	public Encryption()
	{

	}

	public static String md5(String input)
	{
		String md5 = null;

		if (null == input)
			return null;

		try
		{
			// Create MessageDigest object for MD5
			MessageDigest digest = MessageDigest.getInstance("MD5");
			// Update input string in message digest
			digest.update(input.getBytes(), 0, input.length());
			// Converts message digest value in base 16 (hex)
			md5 = new BigInteger(1, digest.digest()).toString(16);
			// Pad front with zeros b/c md5 sometimes cuts off before 32 chars
			while (md5.length() < 32)
			{
				md5 = "0" + md5;
			}
		}
		catch (NoSuchAlgorithmException e)
		{
			System.err.println("RegistrationBean.md5(): " + e.getMessage());
		}
		return md5;
	}
}
