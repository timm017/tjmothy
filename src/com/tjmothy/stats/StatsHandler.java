package com.tjmothy.stats;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.tjmothy.utils.PathHelper;

@WebServlet("/StatsHandler")
public class StatsHandler extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public StatsHandler()
	{
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String subcmd = request.getParameter("subcmd");
		System.out.println("GET: subcmd: " + subcmd);
		if (subcmd == null)
			subcmd = "login";

		StringBuffer sb = new StringBuffer("<outertag>");
		String xslSheet = getServletConfig().getInitParameter("xslSheet");
		PrintWriter out = response.getWriter();

		if (subcmd.equals("default"))
		{
			StatsBean statsBean = new StatsBean();
			sb.append(statsBean.getPlayersForTeam(1));
		}
		else if (subcmd.equals("login"))
		{
		}
		sb.append("<subcmd>" + subcmd + "</subcmd>");
		sb.append("</outertag>");
		StringReader xml = new StringReader(sb.toString());
		ServletContext servletContext = getServletContext();
		String contextPath = servletContext.getRealPath(File.separator);

		try
		{
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Source xslDoc = new StreamSource(contextPath + PathHelper.XSL_PATH + xslSheet);
			Source xmlDoc = new StreamSource(xml);
			Transformer transformer = tFactory.newTransformer(xslDoc);
			transformer.transform(xmlDoc, new StreamResult(out));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String xslSheet = getServletConfig().getInitParameter("xslSheet");
		String subcmd = request.getParameter("subcmd");
		System.out.println("POST: subcmd: " + subcmd);
		StatsBean statsBean = new StatsBean();
		StringBuilder innerSB = new StringBuilder();
		User user = null;
		Team team = null;
		Team enemyTeam = null;
		Game game = null;
		if (subcmd == null)
			subcmd = "login";
		if (subcmd.equals("login"))
		{
			String phoneNumber = request.getParameter("phonenumber");
			String password = request.getParameter("password");
			if (statsBean.login(phoneNumber, password))
			{
				user = statsBean.userInfo(phoneNumber);
				game = statsBean.gameInfo(user.getTeamId(), StatsBean.getTodayDate());
				team = statsBean.teamInfo(user.getTeamId());
				enemyTeam = statsBean.teamInfo((team.getIsHomeTeam() ? game.getAwayTeamId() : game.getHomeTeamId()));
				innerSB.append(statsBean.getPlayersForTeam(user.getTeamId()));
				innerSB.append("<my_team>" + team.toXML() + statsBean.getCurrentTeamScores(team.getId(), game.getScheduleId()) + "</my_team>");
				innerSB.append("<enemy_team>" + enemyTeam.toXML() + statsBean.getCurrentTeamScores(enemyTeam.getId(), game.getScheduleId()) + "</enemy_team>");
				innerSB.append(game.toXML());
				subcmd = "stats-view";
				if (statsBean.isGameSubmitted(user.getTeamId(), game.getScheduleId()))
				{
					xslSheet = "stats-recap.xsl";
				}
			}
			else
			{
				// Show login screen again
				innerSB.append("<login success='false'>");
				innerSB.append("<username>" + phoneNumber + "</username>");
				innerSB.append("<password>" + password + "</password>");
				innerSB.append("</login>");
			}
		}
		else if (subcmd.equals("update-player-score"))
		{
			String playerId = request.getParameter("player_id");
			String score = request.getParameter("score");
			String change = request.getParameter("change");
			int realPlayerId = 0;
			try
			{
				realPlayerId = Integer.parseInt(playerId);
			}
			catch (NumberFormatException nfe)
			{
				System.out.println("Error converting score OR playerId to integer: " + nfe.getMessage());
				;
			}
			statsBean.updatePlayerScore(realPlayerId, score, change);
		}
		else if (subcmd.equals("update-box-score"))
		{
			String quarter = request.getParameter("quarter");
			String teamId = request.getParameter("teamId");
			String scheduleId = request.getParameter("scheduleId");
			String score = request.getParameter("score");
			int realTeamId = -1;
			int realScheduleId = -1;
			int realScore = 0;
			try
			{
				realTeamId = Integer.parseInt(teamId);
				realScheduleId = Integer.parseInt(scheduleId);
				realScore = Integer.parseInt(score);
			}
			catch (NumberFormatException nfe)
			{
				System.out.println("Error converting score OR playerId to integer: " + nfe.getMessage());
				;
			}
			statsBean.updateBoxScore(quarter, realScore, realTeamId, realScheduleId);
		}
		else if (subcmd.equals("update-team-score"))
		{
			String teamId = request.getParameter("team_id");
			String score = request.getParameter("score");
			String quarter = request.getParameter("quarter");
			int realTeamId = 0;
			try
			{
				realTeamId = Integer.parseInt(teamId);
			}
			catch (NumberFormatException nfe)
			{
				System.out.println("Error converting teamId to integer: " + nfe.getMessage());
				;
			}
			statsBean.updatePlayerScore(realTeamId, score, quarter);
		}
		else if (subcmd.equals("update-highlights"))
		{
			String highlights = request.getParameter("highlights");
			String teamId = request.getParameter("teamId");
			String scheduleId = request.getParameter("scheduleId");
			int realTeamId = -1;
			int realScheduleId = -1;
			try
			{
				realTeamId = Integer.parseInt(teamId);
				realScheduleId = Integer.parseInt(scheduleId);
			}
			catch (NumberFormatException nfe)
			{
				System.out.println("Error converting score OR playerId to integer: " + nfe.getMessage());
				;
			}
			statsBean.updateHighlights(highlights, realTeamId, realScheduleId);
		}
		else if (subcmd.equals("final-submit"))
		{
			String teamId = request.getParameter("teamId");
			String enemyTeamId = request.getParameter("enemyTeamId");
			String scheduleId = request.getParameter("scheduleId");
			int realTeamId = -1;
			int realEnemyTeamId = -1;
			int realScheduleId = -1;
			try
			{
				realTeamId = Integer.parseInt(teamId);
				realEnemyTeamId = Integer.parseInt(enemyTeamId);
				realScheduleId = Integer.parseInt(scheduleId);
			}
			catch (NumberFormatException nfe)
			{
				System.out.println("Error converting score OR playerId to integer: " + nfe.getMessage());
				;
			}
			// Only mark as submitted for YOUR team (realTeamId)
			statsBean.submitTeamStats(realTeamId, realScheduleId);
			xslSheet = "stats-recap.xsl";
			Team submitMyTeam = statsBean.teamInfo(realTeamId);
			Team submitEnemyTeam = statsBean.teamInfo(realEnemyTeamId);
			innerSB.append(statsBean.getPlayersForTeam(realTeamId));
			innerSB.append(submitMyTeam.toXML());
			innerSB.append(statsBean.getCurrentTeamScores(realTeamId, realScheduleId));
			// Set the total for MY & ENEMY teams in the "schedule" table
			int totalMy = statsBean.getTeamTotalScore(realTeamId, realScheduleId);
			statsBean.submitTeamTotal(totalMy, realScheduleId, submitMyTeam);
			int totalEnemy = statsBean.getTeamTotalScore(realEnemyTeamId, realScheduleId);
			statsBean.submitTeamTotal(totalEnemy, realScheduleId, submitEnemyTeam);
			// TODO: Send emails
			// innerSB.append(statsBean.gameInfo(realScheduleId));

		}
		ServletContext servletContext = getServletContext();
		String contextPath = servletContext.getRealPath(File.separator);

		PrintWriter out = response.getWriter();

		StringBuffer sb = new StringBuffer("<outertag>");
		sb.append("<subcmd>" + subcmd + "</subcmd>");
		sb.append(innerSB.toString());
		sb.append("</outertag>");
		System.out.println(sb.toString());
		StringReader xml = new StringReader(sb.toString());

		try
		{
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Source xslDoc = new StreamSource(contextPath + PathHelper.XSL_PATH + xslSheet);
			Source xmlDoc = new StreamSource(xml);
			Transformer transformer = tFactory.newTransformer(xslDoc);
			transformer.transform(xmlDoc, new StreamResult(out));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
