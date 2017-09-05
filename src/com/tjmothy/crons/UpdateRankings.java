package com.tjmothy.crons;

import java.util.ArrayList;

import com.tjmothy.stats.StatsBean;

/**
 * Loops through all teams for that sport and season then will update all team ranks (schedule, bonus, power)
 * args:
 *  0 - The sport id
 *  1 - the season
 *  
 * @author tmckeown
 *
 */
class UpdateRankings
{
	public static void main(String[] args)
	{
		int sportId = 0;
		int seasonId = 0;
		if (args.length > 0)
		{
			try
			{
				sportId = Integer.parseInt(args[0]);
				seasonId = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException e)
			{
				System.err.println("Argument" + args[0] + " OR " + args[1] + " must be an integer.");
				System.exit(1);
			}
		}

		System.out.println("Updating ranks: season:" + seasonId + " sport:" + sportId);
		final StatsBean statsBean = new StatsBean();
		// sportId, seasonId
		final ArrayList<Integer> teamIds = statsBean.getTeamsForRankings(sportId, seasonId);
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
