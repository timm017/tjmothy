package com.tjmothy.crons;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.tjmothy.stats.BaseballExportBean;

/**
 * <32 character Stat Supplier ID>
 * Jersey|NumberOfPitches
 * 1|50
 * 2|44
 * 
 * @author tmckeown
 *
 */
public class BaseballExport
{
	public static void main(String[] args)
	{
		final String MAX_PREPS_BASEBALL_FOLDER = "max_preps_baseball";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		LocalDate localDate = LocalDate.now();
		System.out.println("Creating baseball text export for Max Preps -> " + dtf.format(localDate));
		BaseballExportBean beb = new BaseballExportBean();
		ArrayList<String> lines = beb.getPitchersList();
		//baseball_pitchers_export_2016_11_16.txt
		Path file = Paths.get("../" + MAX_PREPS_BASEBALL_FOLDER + "/baseball_pitchers_export_" + dtf.format(localDate) + ".txt");
		System.out.println("path: " + file.toString());
		try
		{
			Files.write(file, lines, Charset.forName("UTF-8"));
		}
		catch (IOException ioe)
		{
			System.out.println("BaseballExport.Main()-> " + ioe.getMessage());
		}
		//Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
		System.out.println("Done baseball export");
	}
}
