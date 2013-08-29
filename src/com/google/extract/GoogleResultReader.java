package com.google.extract;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.trendmenot.extractdata.URLReader;
 
public class GoogleResultReader {
     public static void main(String[] args)  {
 
 
    	 extractSearchResults("sumatheja dasararaju".replaceAll(" ", "%20"));
 
     }

	public static List<String> extractSearchResults(String filters) {
		Set<String> uniqueURLs = new HashSet<String>();
		List<String> wholeURLs = new ArrayList<String>();
		for (int i = 0; i < 3; i++) {
	try {
			BufferedWriter out = null;
			int startIndex = 8*i;
			URL url = new URL(
					"https://ajax.googleapis.com/ajax/services/search/web?v=1.0&rsz=8&start="+startIndex+"&q="+filters);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String inputLine = "";
			String temp = "";
			while ((temp = in.readLine()) != null) {
				inputLine = inputLine + temp;

			}
			SearchOutput data = new Gson().fromJson(inputLine,
					SearchOutput.class);
			int j = 0;
			for (Results r : data.getResponseData().getResults()) {
				try {
					if(!uniqueURLs.contains(r.getVisibleUrl()))
						{	
						  URLReader.readUrl(r.getUrl());
						  uniqueURLs.add(r.getVisibleUrl());
						  wholeURLs.add(r.getUrl());
						}
					} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				j++;
			}
			
			return wholeURLs;
 
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
		return wholeURLs;
	}
 
}