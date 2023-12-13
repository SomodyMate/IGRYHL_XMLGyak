package hu.domparse.IGRYHL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class DOMWriteIGRYHL {

    public static void main(String[] args) {
        try {
            // XML fajl beolvasasa
            File xmlFile = new File("XMLIGRYHL.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            // konzolra iras
            System.out.println("XML dokumentum fa struktúrában:");
            printDocument(doc.getDocumentElement(), "");

            // fajlba iras
            writeXml(doc, new File("XMLIGRYHL1.xml"));

        } catch (ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
    }

    private static void writeXml(Document doc, File output) throws TransformerException, IOException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult file = new StreamResult(output);
        transformer.transform(source, file);

        System.out.println("\nAz XML fa struktúra kiírva a " + output.getName() + " fájlba.");
    }

    private static void printDocument(org.w3c.dom.Node node, String indent) {
        if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            System.out.print(indent + "<" + node.getNodeName());

            if (node.getAttributes() != null) {
                for (int i = 0; i < node.getAttributes().getLength(); i++) {
                    System.out.print(" " + node.getAttributes().item(i).getNodeName() + "=\""
                            + node.getAttributes().item(i).getNodeValue() + "\"");
                }
            }

            System.out.println(">");
            indent += "    ";
        }

        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            printDocument(node.getChildNodes().item(i), indent);
        }

        if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            System.out.println(indent.substring(0, indent.length() - 4) + "</" + node.getNodeName() + ">");
        }
    }
}
