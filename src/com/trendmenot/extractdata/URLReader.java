package com.trendmenot.extractdata;
import java.net.*;
import java.io.*;

public class URLReader {
	
    public static String readUrl(String webUrl) throws Exception {

//    	BufferedWriter out = null;
    	BufferedReader in = null;
		String inputLine = "";
		try {
//			System.out.println(webUrl);
//			out = new BufferedWriter(new FileWriter("/Users/pinchu/Desktop/webdata/"+fileName+".txt"));
			URL url = new URL(webUrl);
			in = new BufferedReader(
			new InputStreamReader(url.openStream()));

			inputLine = "";
			String temp = "";
			while ((temp = in.readLine()) != null)
			{
				if(!"".equals(temp.trim()))
				inputLine = inputLine + temp.replaceAll("\\<.*?\\>", "")//code to remove xml tags
						.replaceAll("[^a-zA-Z ]+", "").trim()+"\n";
			}
//			out.write(inputLine+"\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
//			out.close();
			in.close();
		}
		return inputLine;
    }
}
