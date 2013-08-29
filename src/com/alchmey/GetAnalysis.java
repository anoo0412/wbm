package com.alchmey;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;
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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.alchemyapi.api.AlchemyAPI;
import com.google.extract.GoogleResultReader;
import com.minemyrep.io.Input;
import com.minemyrep.io.Result;
import com.pipl.api.data.containers.Record;
import com.pipl.api.search.SearchAPIError;
import com.pipl.api.search.SearchAPIRequest;
import com.pipl.api.search.SearchAPIResponse;
import com.trendmenot.extractdata.ExtractLinkedinData;
import com.trendmenot.extractdata.ExtractTwitterData;
import com.trendmenot.extractdata.URLReader;

public class GetAnalysis {

	/**
	 * @param args
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws SearchAPIError
	 */
	public static List<Result> MineRep(Input input) throws SearchAPIError,
			IOException, URISyntaxException {
		AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("../standalone/deployments/api_key.txt");
		List<Result> output = new ArrayList<Result>();
		double resumeCorrelation = 0;

		List<String> linkedInKeywords = TextSimilarity
				.getLinkedinKeywords(ExtractLinkedinData.extractData(input
						.getlURL()));
		try {
			if (linkedInKeywords.size() < 20) {
				Document doc = alchemyObj.URLGetRankedKeywords(input.getlURL());
				linkedInKeywords
						.addAll(getLinkedinKeywords(getStringFromDocument(doc)));
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(linkedInKeywords);

		try {
			// linkedin api
			String linkedinURL = input.getlURL();
			System.out.println("linkedin: " + " sentiment: "
					+ getURLSentiment(alchemyObj, linkedinURL) + " category: "
					+ getCategory(alchemyObj, linkedinURL) + "\n");
			Result linkedinResult = new Result();
			linkedinResult.setCategory(getCategory(alchemyObj, linkedinURL));
			linkedinResult.setSentiment(getURLSentiment(alchemyObj,
					linkedinURL));
			linkedinResult.setSentiment_weightage(getURLSentimentScore(alchemyObj,linkedinURL));
			
			linkedinResult.setURL(linkedinURL);

			// Text similarity
			double similarities = 0;
			try {
				for (String keyword : linkedInKeywords) {
					if (input.getResume().toLowerCase()
							.contains(keyword.toLowerCase().trim())) {
						similarities++;
						System.out.println(keyword);
					}
				}
				System.out.println("Resume: " + similarities
						/ new Double(linkedInKeywords.size()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			resumeCorrelation = new Double(similarities
					/ new Double(linkedInKeywords.size()));
			linkedinResult.setResumeCorrelation(new Double(similarities
					/ new Double(linkedInKeywords.size())).toString());
			linkedinResult.setKeywords(linkedInKeywords);
			output.add(linkedinResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		 try {
//			//pipl search
//			 SearchAPIRequest request = new SearchAPIRequest.Builder()
//			 .firstName(input.getfName())
//			 .lastName(input.getlName())
//			 .middleName(input.getmName())
//			 .username(input.getUserName())
//			 .email(input.getEmailID())
//			 .country(input.getCountry())
//			 .city(input.getCity())
//			 .apiKey("pnhc4pjc257wq68jsrjpdp6y")
//			 .build();
//			 SearchAPIResponse response = request.send();
//			 for (Record j : response.getRecords())
//			 {
//			 System.out.println(j.getSource().getUrl()+", weightage:" +
//			 j.getQueryPersonMatch()
//			 +" sentiment: "+ getURLSentiment(alchemyObj, j.getSource().getUrl())
//			 +" category: "+ getCategory(alchemyObj,j.getSource().getUrl()));
//			 Result r = new Result();
//			 r.setCategory(getCategory(alchemyObj,j.getSource().getUrl()));
//			 r.setSentiment(getURLSentiment(alchemyObj,
//			 j.getSource().getUrl()));
//			 r.setSentiment_weightage(getURLSentimentScore(alchemyObj,j.getSource().getUrl()));
//			 r.setURL(j.getSource().getUrl());
//			 r.setResumeCorrelation(new Double(resumeCorrelation).toString());
//			 r.setKeywords(linkedInKeywords);
//			 output.add(r);
//			 }
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
		// google api
		try {
			List<String> googleUrls = GoogleResultReader
					.extractSearchResults((input.getfName() + " " + input
							.getlName()).replaceAll(" ", "%20"));
			for (String url : googleUrls) {
				System.out.println(url + " sentiment: "
						+ getURLSentiment(alchemyObj, url) + " category: "
						+ getCategory(alchemyObj, url) + "\n");
				Result r = new Result();
				r.setCategory(getCategory(alchemyObj, url));
				r.setSentiment(getURLSentiment(alchemyObj, url));
				r.setSentiment_weightage(getURLSentimentScore(alchemyObj,url));
				r.setURL(url);
				r.setResumeCorrelation(new Double(resumeCorrelation).toString());
				r.setKeywords(linkedInKeywords);
				output.add(r);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// twitter api
		try {
			String twitterData = ExtractTwitterData.extractData(
					input.getTwitterRT()).toString();
			System.out.println("www.twitter.com/" + " sentiment: "
					+ getTextSentiment(alchemyObj, twitterData) + "\n");
			Result twitterResult = new Result();
			twitterResult.setSentiment(getTextSentiment(alchemyObj,
					twitterData));
			twitterResult.setSentiment_weightage(getTextSentimentScore(alchemyObj,
					twitterData));
			twitterResult.setURL("www.twitter.com/" + input.getTwitterRT());
			twitterResult.setResumeCorrelation(new Double(resumeCorrelation)
					.toString());
			twitterResult.setKeywords(linkedInKeywords);
			output.add(twitterResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return output;
	}

	public static String getCorrelation(List<String> linkedInKeywords,
			String url) {
		double similarities = 0;
		try {
			for (String keyword : linkedInKeywords) {
				if (URLReader.readUrl(url).contains(
						keyword.toLowerCase().trim())) {
					similarities++;
					System.out.println(keyword);
				}
			}
			System.out.println(url + ":" + similarities
					/ new Double(linkedInKeywords.size()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Double(similarities / new Double(linkedInKeywords.size()))
				.toString();
	}

	private static String getCategory(AlchemyAPI alchemyObj, String url) {

		try {
			// Categorize a web URL by topic.
			Document doc = alchemyObj.URLGetCategory(url);
			return doc.getElementsByTagName("category").item(0)
					.getTextContent();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
							if (!keywords
									.contains(new String(ch, start, length)))
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

	private static String getURLSentimentScore(AlchemyAPI alchemyObj, String url) {
		try {

			// Extract sentiment for a web URL.
			Document doc = alchemyObj.URLGetTextSentiment(url);
			NodeList nList = doc.getElementsByTagName("docSentiment");
			Node nNode = nList.item(0);
			Element eElement = (Element) nNode;
			// System.out.println("First Name : " +
			// eElement.getElementsByTagName("type").item(0).getTextContent());
			return eElement.getElementsByTagName("score").item(0)
					.getTextContent();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String getTextSentimentScore(AlchemyAPI alchemyObj, String text) {
		try {

			// Extract sentiment for a web URL.
			Document doc = alchemyObj.TextGetTextSentiment(text);
			// System.out.println(getStringFromDocument(doc));
			NodeList nList = doc.getElementsByTagName("docSentiment");
			Node nNode = nList.item(0);
			Element eElement = (Element) nNode;
			// System.out.println("First Name : " +
			// eElement.getElementsByTagName("type").item(0).getTextContent());
			return eElement.getElementsByTagName("score").item(0)
					.getTextContent();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String getURLSentiment(AlchemyAPI alchemyObj, String url) {
		try {

			// Extract sentiment for a web URL.
			Document doc = alchemyObj.URLGetTextSentiment(url);
			NodeList nList = doc.getElementsByTagName("docSentiment");
			Node nNode = nList.item(0);
			Element eElement = (Element) nNode;
			// System.out.println("First Name : " +
			// eElement.getElementsByTagName("type").item(0).getTextContent());
			return eElement.getElementsByTagName("type").item(0)
					.getTextContent();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String getTextSentiment(AlchemyAPI alchemyObj, String text) {
		try {

			// Extract sentiment for a web URL.
			Document doc = alchemyObj.TextGetTextSentiment(text);
			// System.out.println(getStringFromDocument(doc));
			NodeList nList = doc.getElementsByTagName("docSentiment");
			Node nNode = nList.item(0);
			Element eElement = (Element) nNode;
			// System.out.println("First Name : " +
			// eElement.getElementsByTagName("type").item(0).getTextContent());
			return eElement.getElementsByTagName("type").item(0)
					.getTextContent();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

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