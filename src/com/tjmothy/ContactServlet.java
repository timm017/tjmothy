package com.tjmothy;

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
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.tjmothy.email.Email;
import com.tjmothy.utils.PathHelper;

/**
 * Servlet implementation class PPEServlet
 */
@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
//	private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
	private static final String SMTP_HOST_NAME = "malt";
	private static final String SMTP_AUTH_USER = "mckeown.timothy@gmail.com";
	private static final String SMTP_AUTH_PWD = "**********";
	private static final int SMTP_PORT = 2525;

	/**
	 * Default constructor.
	 */
	public ContactServlet()
	{
		// TODO Auto-generated constructor stub
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
		sb.append("<message>");
		sb.append("Hell yeah it works! xslSheet: " + xslSheet);
		sb.append("</message>");
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
		String subcmd = request.getParameter("subcmd");

		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("<outertag>");
		sb.append("<message>");
		sb.append("Hell yeah it works!  xslSheet: " + xslSheet);
		sb.append("</message>");

		if (subcmd.equals("email"))
		{
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("name", request.getParameter("name"));
			hm.put("company", request.getParameter("company"));
			hm.put("phone", request.getParameter("phone"));
			hm.put("comments", request.getParameter("comments"));
//			Email e = new Email(hm);
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
