package com.tjmothy;

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
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.tjmothy.utils.PageParser;
import com.tjmothy.utils.PathHelper;
import com.tjmothy.utils.SearchParameters;

/**
 * Servlet implementation class PPEServlet
 */
@WebServlet("/WebsiteCheckerServlet")
public class WebsiteCheckerServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public enum Result
	{
		found, not_found, malformed_url
	}

	/**
	 * Default constructor.
	 */
	public WebsiteCheckerServlet()
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
		if (subcmd.equals("check"))
		{
			String website = request.getParameter("website");
			String search = request.getParameter("search");
			SearchParameters sp = new SearchParameters(search, website);
			sb.append("<websitechecker_tool><result>");
			if (website.equals(""))
			{
				sb.append(Result.malformed_url.name());
			}
			else
			{
				PageParser pp = new PageParser(sp);
				if (pp.foundIt())
				{
					sb.append(Result.found.name());
				}
				else
				{
					sb.append(Result.not_found.name());
				}
			}
			sb.append("</result></websitechecker_tool>");
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
