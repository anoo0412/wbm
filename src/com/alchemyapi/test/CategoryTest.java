package com.alchemyapi.test;

import com.alchemyapi.api.*;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import java.io.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

class CategoryTest {
    public static void main(String[] args)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        // Create an AlchemyAPI object.
        AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");

        // Categorize a web URL by topic.
        Document doc = alchemyObj.URLGetCategory("http://www.techcrunch.com/");
        System.out.println(getStringFromDocument(doc));
        
        System.out.println("First Name : " + doc.getElementsByTagName("category").item(0).getTextContent());

        // Categorize some text.
        doc = alchemyObj.TextGetCategory("Latest on the War in Iraq.");
        System.out.println(getStringFromDocument(doc));

        // Load a HTML document to analyze.
        String htmlDoc = getFileContents("data/example.html");

        // Categorize a HTML document by topic.
        doc = alchemyObj.HTMLGetCategory(htmlDoc, "http://www.test.com/");
        System.out.println(getStringFromDocument(doc));
        
        AlchemyAPI_CategoryParams categoryParams = new AlchemyAPI_CategoryParams();
        categoryParams.setOutputMode(AlchemyAPI_Params.OUTPUT_RDF);
        doc = alchemyObj.HTMLGetCategory(htmlDoc, "http://www.test.com/", categoryParams);
        System.out.println(getStringFromDocument(doc));
    }

    // utility function
    private static String getFileContents(String filename)
        throws IOException, FileNotFoundException
    {
        File file = new File(filename);
        StringBuilder contents = new StringBuilder();

        BufferedReader input = new BufferedReader(new FileReader(file));

        try {
            String line = null;

            while ((line = input.readLine()) != null) {
                contents.append(line);
                contents.append(System.getProperty("line.separator"));
            }
        } finally {
            input.close();
        }

        return contents.toString();
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
