package com.tjmothy;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

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

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public HomeServlet()
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

		try
		{
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Source xslDoc = new StreamSource(xslSheet);
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
		// TODO Auto-generated method stub
	}

}
