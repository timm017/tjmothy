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

/**
 * Servlet implementation class PPEServlet *
 * +------------+--------------+------+-----+---------+----------------+ | Field
 * | Type | Null | Key | Default | Extra |
 * +------------+--------------+------+-----+---------+----------------+ | id |
 * int(9) | NO | PRI | NULL | auto_increment | | first_name | varchar(255) | NO
 * | | NULL | | | last_name | varchar(255) | NO | | NULL | | | user_name |
 * varchar(255) | NO | | NULL | | | email | varchar(255) | NO | | NULL | | |
 * password | varchar(255) | NO | | NULL | |
 * +------------+--------------+------+-----+---------+----------------+
 */
@WebServlet("/RegistrationHandler")
public class RegistrationHandler extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public RegistrationHandler()
	{
		System.out.println("CALLED");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String xslSheet = getServletConfig().getInitParameter("xslSheet");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("<outertag>");
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
		sb.append("</outertag>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		User user = new User(request);
		String xslSheet = getServletConfig().getInitParameter("xslSheet");
		ServletContext servletContext = getServletContext();
		String contextPath = servletContext.getRealPath(File.separator);
		// String subcmd = getRequestParam("subcmd", "");
		String subcmd = request.getParameter("subcmd");

		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("<outertag>");

		if (subcmd.equals("email"))
		{
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("name", request.getParameter("name"));
			hm.put("company", request.getParameter("company"));
			hm.put("phone", request.getParameter("phone"));
			hm.put("comments", request.getParameter("comments"));
//			Email e = new Email(hm);
		}
		else if (subcmd.equalsIgnoreCase("register"))
		{
			RegistrationBean rb = new RegistrationBean(user);
			sb.append("<logged_in>");
			if (rb.addUser())
			{
				sb.append("true");
				HttpSession session = request.getSession();
				session.setAttribute("username", user.getUserName());
				// setting session to expire in 30 mins
				session.setMaxInactiveInterval(30 * 60);
				response.sendRedirect("/tjmothy/home");
			}
			else
			{
				sb.append("false");
			}
			sb.append("</logged_in>");
		}

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
