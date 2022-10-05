package vlad.skiba.javacore.consoleshop.parser;

import vlad.skiba.javacore.consoleshop.exception.ApplicationGenericException;
import org.xml.sax.SAXException;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.util.LinkedHashMap;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class SortingCriteriaXMLParser {

    public static LinkedHashMap<String, Order> parse(Path pathToXMLFile) {
        LinkedHashMap<String, Order> sortingCriteriaMap = new LinkedHashMap<>();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(pathToXMLFile.toFile());
            NodeList nodes = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String attributeName = element.getElementsByTagName("title").item(0).getChildNodes().
                            item(0).getNodeValue();
                    String orderAsString = element.getElementsByTagName("value").item(0).getChildNodes().
                            item(0).getNodeValue();
                    Order order = Order.valueOf(orderAsString);
                    sortingCriteriaMap.put(attributeName, order);
                }
            }
        } catch (IOException | SAXException | ParserConfigurationException ex) {
            throw new ApplicationGenericException("Troubles with XML file parsing! ");
        }
        return sortingCriteriaMap;
    }

}