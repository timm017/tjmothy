package com.tjmothy.testing;

import java.util.Arrays;
import java.util.List;

import com.tjmothy.utils.Encryption;

public class Test
{

	public static void main(String[] args)
	{

		String[] atp = { "tmckeown", "penncrest"};
		List<String> players = Arrays.asList(atp);

		// Old looping
		for (String player : players)
		{
			System.out.print(player + "; ");
		}

		// Using lambda expression and functional operations
		players.forEach((player) -> System.out.print(Encryption.md5(player) + "\n "));

		// Using double colon operator in Java 8
		players.forEach(System.out::println);
	}
}
