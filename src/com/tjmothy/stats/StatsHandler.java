package com.tjmothy.stats;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.tjmothy.email.Email;
import com.tjmothy.email.Emailer;
import com.tjmothy.users.LogInOutBean;
import com.tjmothy.utils.PathHelper;

@WebServlet("/StatsHandler")
public class StatsHandler extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private final String EMAIL_XSL = "stats-recap.xsl";

	private enum SessionAttributes
	{
		logged_in, phonenumber
	}

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
		doPost(request, response);
	}

	/**
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(true);
		String xslSheet = getServletConfig().getInitParameter("xslSheet");
		String subcmd = request.getParameter("subcmd");
		String outputMode = request.getParameter("outputMode");
		System.out.println("POST: subcmd: " + subcmd);
		StatsBean statsBean = new StatsBean();
		StringBuilder innerSB = new StringBuilder();
		User user = null;
		Team team = null;
		Team enemyTeam = null;
		Game game = null;
		if (subcmd == null)
		{
			subcmd = "login";
			if (session.getAttribute(SessionAttributes.logged_in.name()) != null && session.getAttribute(SessionAttributes.logged_in.name()).equals("true"))
			{
				// We are logged in, redirect to stats-view
				subcmd = "stats-view";
			}
		}
		if (subcmd.equals("login"))
		{
			LogInOutBean liob = new LogInOutBean();
			String phoneNumber = request.getParameter("phonenumber");
			String password = request.getParameter("password");
			// REGULAR
			if (liob.logIn(phoneNumber, password) || liob.logInBaseball(phoneNumber, password))
			{
				user = statsBean.userInfo(phoneNumber);
				game = statsBean.gameInfo(user.getTeamId(), StatsBean.getTodayDate());
				team = statsBean.teamInfo(user.getTeamId());
				enemyTeam = statsBean.teamInfo((team.getIsHomeTeam() ? game.getAwayTeamId() : game.getHomeTeamId()));
				System.out.println("enemy team home-> " + team.getIsHomeTeam() + " enemy teamId: " + (team.getIsHomeTeam() ? game.getAwayTeamId() : game.getHomeTeamId()));
				// Create session variables, logged_in for webapp and phonenumber for authentication?
				session.setAttribute(SessionAttributes.logged_in.name(), "true");
				session.setAttribute(SessionAttributes.phonenumber.name(), phoneNumber);
				if (!game.getNoGameToday())
				{
					subcmd = "stats-view";
				}
				innerSB.append(game.toXML());
				// If game is already submitted, show them the recap
				if (statsBean.isGameSubmitted(user.getTeamId(), game.getScheduleId()))
				{
					xslSheet = "stats-recap.xsl";
				}
			}
			else
			{
				// User not logged in, show login screen again
				innerSB.append("<login success='false'>");
				innerSB.append("<username>" + phoneNumber + "</username>");
				innerSB.append("<password>" + password + "</password>");
				innerSB.append("</login>");
			}
		}

		// If phonenumber is invalid then user is not authenticated to execute anything below, log them out
		String phoneNumber = (String) session.getAttribute(SessionAttributes.phonenumber.name());
		LogInOutBean liob = new LogInOutBean();
		if (!statsBean.isValidPhoneNumber(phoneNumber) && !liob.logInBaseball(phoneNumber, "baseball"))
		{
			System.err.println("Phone number invalid, logging user out");
			subcmd = "logout";
		}
		// Log user out, clear sessions
		if (subcmd.equals("logout"))
		{
			System.out.println("logging user out");
			session.invalidate();
			innerSB.append("<login success='false'>");
			innerSB.append("<username></username>");
			innerSB.append("<password></password>");
			innerSB.append("</login>");
		}
		else if (subcmd.equals("stats-view"))
		{
			user = statsBean.userInfo(phoneNumber);
			game = statsBean.gameInfo(user.getTeamId(), StatsBean.getTodayDate());
			team = statsBean.teamInfo(user.getTeamId());
			enemyTeam = statsBean.teamInfo((team.getIsHomeTeam() ? game.getAwayTeamId() : game.getHomeTeamId()));
			if (statsBean.isGameSubmitted(user.getTeamId(), game.getScheduleId()))
			{
				xslSheet = "stats-recap.xsl";
			}
			if (!game.getNoGameToday())
			{
				innerSB.append(user.toXML());
				innerSB.append(statsBean.getPlayersForTeam(user.getTeamId(), true));
				innerSB.append(statsBean.getPlayersForTeam(enemyTeam.getId(), false));
				innerSB.append("<my_team>" + team.toXML() + statsBean.getCurrentTeamScores(team.getId(), game.getScheduleId()) + "</my_team>");
				innerSB.append("<enemy_team>" + enemyTeam.toXML() + statsBean.getCurrentTeamScores(enemyTeam.getId(), game.getScheduleId()) + "</enemy_team>");
			}
			innerSB.append(game.toXML());
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
				System.err.println("Error converting score OR playerId to integer: " + nfe.getMessage());
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
				System.err.println("Error converting score OR playerId to integer: " + nfe.getMessage());
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
				System.err.println("Error converting teamId to integer: " + nfe.getMessage());
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
				System.err.println("Error converting score OR playerId to integer: " + nfe.getMessage());
			}
			statsBean.updateHighlights(highlights, realTeamId, realScheduleId);
		}
		else if (subcmd.equals("update-total-score"))
		{
			String total = request.getParameter("total");
			String teamId = request.getParameter("teamId");
			String scheduleId = request.getParameter("scheduleId");
			int realTotal = -1;
			int realTeamId = -1;
			int realScheduleId = -1;
			try
			{
				realTeamId = Integer.parseInt(teamId);
				realScheduleId = Integer.parseInt(scheduleId);
				realTotal = Integer.parseInt(total);
			}
			catch (NumberFormatException nfe)
			{
				System.err.println("Error converting score OR playerId to integer: " + nfe.getMessage());
			}
			statsBean.updateBoxScore("q1", 0, realTeamId, realScheduleId);
			statsBean.updateTeamTotal(realTotal, realTeamId, realScheduleId);
		}
		else if (subcmd.equals("update-pitch-total"))
		{
			String playerId = request.getParameter("playerId");
			String pitches = request.getParameter("pitches");
			String scheduleId = request.getParameter("scheduleId");
			int realPlayerId = -1;
			int realPitches = -1;
			int realScheduleId = -1;
			try
			{
				realPlayerId = Integer.parseInt(playerId);
				realPitches = Integer.parseInt(pitches);
				realScheduleId = Integer.parseInt(scheduleId);
			}
			catch (NumberFormatException nfe)
			{
				System.err.println("Error converting playerId OR pitches to integer: " + nfe.getMessage());
			}
			statsBean.updatePitchTotal(realPlayerId, realPitches, realScheduleId);
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
				System.err.println("Error converting score OR playerId to integer: " + nfe.getMessage());
			}
			game = statsBean.gameInfo(realTeamId, StatsBean.getTodayDate());
			xslSheet = "stats-recap.xsl";
			Team submitMyTeam = statsBean.teamInfo(realTeamId);
			Team submitEnemyTeam = statsBean.teamInfo(realEnemyTeamId);
			int totalMy = 0;
			int totalEnemy = 0;
			if (game.getSport() == Game.BASEBALL_ID)
			{
				innerSB.append("<my_team>" + submitMyTeam.toXML() + "<total>" + statsBean.getTeamTotalScore(realTeamId, realScheduleId) + "</total></my_team>");
				innerSB.append("<enemy_team>" + submitEnemyTeam.toXML() + "<total>" + statsBean.getTeamTotalScore(realEnemyTeamId, realScheduleId) + "</total></enemy_team>");
				totalMy = statsBean.getTeamTotalScore(realTeamId, realScheduleId);
			}
			else
			{
				innerSB.append("<my_team>" + submitMyTeam.toXML() + statsBean.getCurrentTeamScores(submitMyTeam.getId(), game.getScheduleId()) + "</my_team>");
				innerSB.append("<enemy_team>" + submitEnemyTeam.toXML() + statsBean.getCurrentTeamScores(submitEnemyTeam.getId(), game.getScheduleId()) + "</enemy_team>");
				// Set the total for MY & ENEMY teams in the "schedule" table
				totalMy = statsBean.getTeamTotalScoreFromQuarters(realTeamId, realScheduleId);
				totalEnemy = statsBean.getTeamTotalScoreFromQuarters(realEnemyTeamId, realScheduleId);
				statsBean.submitTeamTotal(totalMy, realScheduleId, submitMyTeam);
				statsBean.submitTeamTotal(totalEnemy, realScheduleId, submitEnemyTeam);
			}
			// If user refreshes submit page, don't update win/loss. Should we include Email? SubmitTotals?
			if (!statsBean.isGameSubmitted(submitMyTeam.getId(), game.getScheduleId()))
			{
				// Incremement either win or losses column for each team
				statsBean.updateWinLoss(realTeamId, (totalMy > totalEnemy));
				statsBean.updateWinLoss(realEnemyTeamId, (totalEnemy > totalMy));
			}
			// Only mark as submitted for YOUR team (realTeamId)
			statsBean.submitTeamStats(realTeamId, realScheduleId);
			innerSB.append(game.toXML());
			// Get XML for final submission recap
			innerSB.append(statsBean.getPlayersForTeam(realTeamId, true));
			innerSB.append(statsBean.getPlayersForTeam(realEnemyTeamId, false));
			innerSB.append(statsBean.getCurrentTeamScores(realTeamId, realScheduleId));
			innerSB.append(submitMyTeam.toXML());
			innerSB.append(submitEnemyTeam.toXML());
			final String subjectLine = submitMyTeam.getSchoolName() + " stats";
			final String emailXml = "<outertag><subcmd>" + subcmd + "</subcmd>" + innerSB.toString() + "</outertag>";
			// Thread to loop through all teams and update their rankings
			statsBean.updateAllRanksForAllTeams(game.getSport(), 1);
			// Send email
			sendEmail(emailXml, subjectLine, (user == null) ? "" : user.getEmail());

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

	/**
	 * Send email using HTML created by stats-recap XSL sheet
	 * 
	 * @param xml
	 *            - The XML to transform with stats-recap
	 * @param subjectLine
	 *            - Subjectline for the email
	 * 
	 * @param email
	 *            - The user's email to send the stats to
	 * 
	 */
	private void sendEmail(String xml, String subjectLine, String userEmail)
	{
		try
		{
			ServletContext servletContext = getServletContext();
			String contextPath = servletContext.getRealPath(File.separator);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Source xslDoc = new StreamSource(contextPath + PathHelper.XSL_PATH + EMAIL_XSL);
			Source xmlDoc = new StreamSource(new ByteArrayInputStream(xml.getBytes("utf-8")));
			Transformer transformer = tFactory.newTransformer(xslDoc);
			transformer.setParameter("email_template", "true");
			StringWriter out = new StringWriter();
			transformer.transform(xmlDoc, new StreamResult(out));
			Email email = new Email();
			email.addRecipient(userEmail);
			email.setSubject(subjectLine);
			email.setBody(out.toString());
			try
			{
				new Emailer(email);
			}
			catch (MessagingException me)
			{
				me.printStackTrace();
			}
		}
		catch (TransformerConfigurationException tce)
		{
			tce.printStackTrace();
		}
		catch (TransformerException te)
		{
			te.printStackTrace();
		}
		catch (UnsupportedEncodingException uee)
		{
			uee.printStackTrace();
		}
	}
}
