package com.tjmothy.users;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashMap;

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

import com.tjmothy.email.Email;
import com.tjmothy.utils.PathHelper;

@WebServlet("/RegistrationHandler")
public class LogInOutHandler extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LogInOutHandler()
	{
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String subcmd = request.getParameter("subcmd");
		StringBuffer sb = new StringBuffer("<outertag>");

		if (subcmd.equalsIgnoreCase("logout"))
		{
			HttpSession session = request.getSession();
			session.invalidate();
		}

		String xslSheet = getServletConfig().getInitParameter("xslSheet");
		PrintWriter out = response.getWriter();
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
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
		LogInOutBean liob = new LogInOutBean();
		
		if (subcmd.equalsIgnoreCase("login"))
		{
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			sb.append("<username>");
			if (liob.logIn(username, password))
			{
				sb.append(username);
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				// setting session to expire in 30 mins
				session.setMaxInactiveInterval(30 * 60);
				response.sendRedirect("/tjmothy/home");
			}
			sb.append("</username>");
		}

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
