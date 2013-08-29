package com.alchmey;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.alchemyapi.api.AlchemyAPI;
import com.trendmenot.extractdata.ExtractLinkedinData;

public class TextSimilarity {
   
//	public static void main(String[] args) throws Exception
//	{
//		int similarities = 0;
//		String resume = "";
//		try (BufferedReader br = new BufferedReader(new FileReader("/Users/pinchu/Documents/workspace/BFITDataCollection/src/dates")))
//		{
//			String sCurrentLine;
//			while ((sCurrentLine = br.readLine()) != null) {
//				resume += sCurrentLine;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
//		
//		List<String> linkedInKeywords = getLinkedinKeywords(ExtractLinkedinData.extractData("www.linkedin.com%2Fin%2Fsumatheja"));
////		String urlContentString = getURLKeywords("http://www.linkedin.com/pub/anusha-sunkenapally/57/944/757").toString();
//		
//		resume = getURLContentString("http://www.linkedin.com/in/ckadur");
//		
//		
//		for(String keyword: linkedInKeywords)
//		{
//			if(resume.toLowerCase().contains(keyword.toLowerCase().trim()))
//			{
//				similarities++;
//				System.out.println(keyword);
//			}
//		}
//		System.out.println("total : "+linkedInKeywords.size()+ " similar : "+ similarities);
//		System.out.println(linkedInKeywords);
//	}

	public static String getURLContentString(String webURL) throws MalformedURLException,
			IOException {
//		URL url = new URL("http://www.linkedin.com/in/sumatheja");
		URL url = new URL(webURL);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		String inputLine = "";
		String temp = "";
		while ((temp = in.readLine()) != null)
		{
			if(!"".equals(temp.trim()))
			inputLine = inputLine + temp.replaceAll("\\<.*?\\>", "")//code to remove xml tags
					.replaceAll("[^a-zA-Z ]+", "").trim()+"\n";
		}
		return inputLine;
	}
	
	public static List<String> getResumeKeywords(String resume) throws Exception {
		
		AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");
		final List<String> keywords = new ArrayList<String>();
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
							keywords.add(new String(ch, start, length));
							bfname = false;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			};
			
			Document doc = alchemyObj.TextGetRankedKeywords(resume);
			InputSource is = new InputSource(new StringReader(getStringFromDocument(doc))); 
			saxParser.parse(is, handler);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return keywords;
	}
	
	public static List<String> getLinkedinKeywords(String linkedinXML) {
		
		final List<String> keywords = new ArrayList<String>();
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler handler = new DefaultHandler() {

				boolean bfname = false;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					if (qName.equalsIgnoreCase("title")) {
						bfname = true;
					}
					if (qName.equalsIgnoreCase("industry")) {
						bfname = true;
					}
					if (qName.equalsIgnoreCase("name")) {
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
							if(!keywords.contains(new String(ch, start, length)))
								keywords.add(new String(ch, start, length));
							bfname = false;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			};
			
			InputSource is = new InputSource(new StringReader(linkedinXML)); 
			saxParser.parse(is, handler);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keywords;
	}
	
	 // utility method
    private static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    }
