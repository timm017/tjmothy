package com.tjmothy.stats;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

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

import com.tjmothy.bcrypt.BCrypt;
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
		if(subcmd == null)
			subcmd = "login";
		
		StringBuffer sb = new StringBuffer("<outertag>");
		String xslSheet = getServletConfig().getInitParameter("xslSheet");
		PrintWriter out = response.getWriter();
		
		if(subcmd.equals("default"))
		{
			StatsBean statsBean = new StatsBean();
			sb.append(statsBean.getPlayersForTeam(1));
		}
		else if(subcmd.equals("login"))
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String subcmd = request.getParameter("subcmd");
		System.out.println("POST: subcmd: " + subcmd);
		StatsBean statsBean = new StatsBean(); 
		StringBuilder innerSB = new StringBuilder();
		User user = null;
		if(subcmd == null)
			subcmd = "login";
		if(subcmd.equals("login"))
		{
			String phoneNumber = request.getParameter("phonenumber");
			String password = request.getParameter("password");
			if(statsBean.login(phoneNumber, password))
			{
				user = statsBean.userInfo(phoneNumber);
				innerSB.append(statsBean.getPlayersForTeam(user.getTeamId()));
				subcmd = "stats-view";
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
		String xslSheet = getServletConfig().getInitParameter("xslSheet");
		ServletContext servletContext = getServletContext();
		String contextPath = servletContext.getRealPath(File.separator);

		PrintWriter out = response.getWriter();
		
		StringBuffer sb = new StringBuffer("<outertag>");
		sb.append("<subcmd>" + subcmd + "</subcmd>");
		sb.append(innerSB.toString());
		sb.append("</outertag>");
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
