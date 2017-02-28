package com.tjmothy.crons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tjmothy.stats.StatsBean;
import com.tjmothy.utils.Encryption;

public class UpdateRankings
{
	public static void main(String[] args)
	{
		System.out.println("Updating ranks: ");
		StatsBean sb = new StatsBean();
		ArrayList<Integer> teams = sb.getTeamsForRankings(1, 1);
		teams.forEach(System.out::println);
	}
}
