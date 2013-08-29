package com.trendmenot.extractdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
public class JSONReader {
     public static void main(String[] args) {
 
	JSONParser parser = new JSONParser();
 
	try {
 
		BufferedWriter out = null;
		out = new BufferedWriter(new FileWriter("test.json"));
        URL url = new URL("https://ajax.googleapis.com/ajax/services/search/web?v=1.0&rsz=1&start=2&q=sumatheja%20dasararaju");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(url.openStream()));

        String inputLine = "";
        String temp = "";
        while ((temp = in.readLine()) != null)
        {
        	inputLine = inputLine+temp;
        	System.out.println(inputLine);
        	
        }
        out.write(inputLine);
		Object obj = parser.parse(new FileReader("test.json"));
 
		JSONObject jsonObject = (JSONObject) obj;
 
//		String name = (String) jsonObject.get("name");
//		System.out.println(name);
// 
//		long age = (Long) jsonObject.get("age");
//		System.out.println(age);
 
		// loop array
		JSONArray msg = (JSONArray) jsonObject.get("url");
		Iterator<String> iterator = msg.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
 
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} 
	catch (ParseException e) {
		e.printStackTrace();
	}
 
     }
 
}