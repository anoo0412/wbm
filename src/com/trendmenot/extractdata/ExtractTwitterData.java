package com.trendmenot.extractdata;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ExtractTwitterData {
	public static void main(String argv[]) throws IOException {

		extractData("699dirtygirl");
	}

	public static StringBuffer extractData(String rtName)
			throws MalformedURLException, IOException {
		final StringBuffer tweetsTxt = new StringBuffer();
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler handler = new DefaultHandler() {

				boolean bfname = false;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					if (qName.equalsIgnoreCase("text")) {
						bfname = true;
					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

					try {
						if (bfname) {
								tweetsTxt.append(new String(ch, start, length)+"\n");
								// System.out.println( new String(ch, start,
							// length)+"\n");
							bfname = false;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			};
			URL game = new URL(
					"http://api.twitter.com/1/statuses/user_timeline.xml?id="
							+ rtName + "&count=200&page=1");
			URLConnection connection = game.openConnection();
			InputStream is = connection.getInputStream();
			saxParser.parse(is, handler);
			System.out.println(tweetsTxt);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tweetsTxt;
	}
}
