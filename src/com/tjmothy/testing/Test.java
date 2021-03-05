package com.tjmothy.testing;

import java.util.Arrays;
import java.util.List;

import com.tjmothy.email.ReminderBean;
import com.tjmothy.stats.Game;
import com.tjmothy.utils.Encryption;

public class Test
{

	public static void main(String[] args)
	{

		String[] passwords = { "baseball"};
		List<String> players = Arrays.asList(passwords);

		ReminderBean rb = new ReminderBean();
//		rb.getEmailsForReminder(Game.BASEBALL_ID).forEach((teamId) -> System.out.print(teamId + "\n "));
		players.forEach((teamId) -> System.out.print(teamId + "\n "));


		// Old looping
//		for (String password : passwords)
//		{
//			System.out.print(password + " : " + Encryption.md5(password));
//		}

		// Using lambda expression and functional operations
//		players.forEach((player) -> System.out.print(Encryption.md5(player) + "\n "));

		// Using double colon operator in Java 8
//		players.forEach(System.out::println);
	}
}
