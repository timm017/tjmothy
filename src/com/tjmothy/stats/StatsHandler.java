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
		if(subcmd == null)
			subcmd = "default";
		
		StringBuffer sb = new StringBuffer("<outertag>");
		String xslSheet = getServletConfig().getInitParameter("xslSheet");
		PrintWriter out = response.getWriter();
		
		if(subcmd.equals("default"))
		{
			StatsBean statsBean = new StatsBean();
			sb.append(statsBean.getPlayersForTeam(1));
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
		String xslSheet = getServletConfig().getInitParameter("xslSheet");
		ServletContext servletContext = getServletContext();
		String contextPath = servletContext.getRealPath(File.separator);
		// String subcmd = getRequestParam("subcmd", "");
		String subcmd = (request.getParameter("subcmd") == null) ? "" : request.getParameter("subcmd");

		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("<outertag>");
		sb.append("<stats-handler>");
		sb.append("<message>Get stats from DB.</message>");
		sb.append("<stats-handler>");

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
