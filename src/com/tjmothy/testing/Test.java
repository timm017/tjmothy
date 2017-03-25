package com.tjmothy.testing;

import java.util.Arrays;
import java.util.List;

import com.tjmothy.utils.Encryption;

public class Test
{

	public static void main(String[] args)
	{

		String[] passwords = { "player1", "player2", "player3"};
		List<String> players = Arrays.asList(passwords);

		// Old looping
		for (String password : passwords)
		{
			System.out.print(password + " : " + Encryption.md5(password));
		}

		// Using lambda expression and functional operations
//		players.forEach((player) -> System.out.print(Encryption.md5(player) + "\n "));

		// Using double colon operator in Java 8
//		players.forEach(System.out::println);
	}
}
