package DomModifyIGRYHL;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomModifyIGRYHL {

    public static void main(String argv[]) throws ParserConfigurationException, SAXException, IOException, TransformerException {

        File inputFile = new File("XMLIGRYHL.xml");

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document doc = documentBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

        // masodik szemelygepjarmu attributumanak modositasa
        Node jarmu = doc.getElementsByTagName("szemelygepjarmu").item(1);

        NamedNodeMap attr = jarmu.getAttributes();
        Node nodeAttr = attr.getNamedItem("rendszam");
        nodeAttr.setTextContent("MM-AB-123");
        System.out.println("szemelygepjarmu attributum modositva");

        // elso tulajdonos attributumanak modositasa
        Node tulaj = doc.getElementsByTagName("tulajdonos").item(0);

        attr = tulaj.getAttributes();
        nodeAttr = attr.getNamedItem("jogositvany_szama");
        nodeAttr.setTextContent("FR346544");

        System.out.println("tulajdonos attributum modositva");

        // osszes matrica beallitasa van-ra

        NodeList nodes = doc.getElementsByTagName("karosszeria");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                NodeList childNodes = node.getChildNodes();

                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);
                    if (childNode.getNodeName().equals("matrica")) {
                        childNode.setTextContent("van");
                    }
                }
            }
        }
        System.out.println("matrica modositasok kesz");

        // osszes benzines motor atallitasa hibridre

        nodes = doc.getElementsByTagName("motor");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                NodeList childNodes = node.getChildNodes();

                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);
                    if (childNode.getNodeName().equals("uzemanyag") && childNode.getTextContent().equals("benzin")) {
                        childNode.setTextContent("hibrid");
                    }
                }
            }
        }
        System.out.println("benzin motorok atallitva hibridre");

        // gyartasi_ev element atnevezese evjaratra

        nodes = doc.getElementsByTagName("gyartasi_ev");
        for (int i = 0; i < nodes.getLength(); i++) {
            doc.renameNode(nodes.item(i), null, "evjarat");
        }
        System.out.println("gyartasi_ev atnevezeve evjaratra");

        // modositott xml dokumentum elmentese
        writeXml(doc, new File("XMLIGRYHLModify.xml"));

    }

    private static void writeXml(Document doc, File output) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(doc);

        StreamResult file = new StreamResult(output);

        transformer.transform(source, file);
    }

}
