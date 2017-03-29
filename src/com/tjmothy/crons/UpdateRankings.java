package com.tjmothy.crons;

import java.util.ArrayList;

import com.tjmothy.stats.StatsBean;

public class UpdateRankings
{
	public static void main(String[] args)
	{
		System.out.println("Updating ranks: ");
		StatsBean statsBean = new StatsBean();
		// teamId, sportId
		ArrayList<Integer> teamIds = statsBean.getTeamsForRankings(1, 1);
		Thread thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				for (Integer teamId : teamIds)
				{
					// teamId, sportId
					statsBean.updateAllRanksForTeam(teamId, 1);
				}
			}

		});
		thread.start();
		// teams.forEach(statsBean.updateAllRanksForTeam());
	}
}
