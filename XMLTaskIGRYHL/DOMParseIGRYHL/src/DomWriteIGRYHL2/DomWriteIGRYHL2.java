package DomWriteIGRYHL2;

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

public class DomWriteIGRYHL2 {

    public static void main(String[] args) {
        try {
            // xml fajl beolvasasa
            File xmlFile = new File("XMLIGRYHL.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            // konzolra iras
            System.out.println("XML dokumentum fa struktúrában:");
            printDocument(doc.getDocumentElement(), "", System.out);

            // fajlba iras
            writeXml(doc, new File("XMLIGRYHL2.xml"));

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

        try (PrintStream out = new PrintStream(new FileOutputStream(output))) {
            printDocument(doc.getDocumentElement(), "", out);
        }
    }

    private static void printDocument(org.w3c.dom.Node node, String indent, PrintStream out) {
        if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            out.print(indent + node.getNodeName());

            if (node.getAttributes() != null) {
                for (int i = 0; i < node.getAttributes().getLength(); i++) {
                    out.print(" " + node.getAttributes().item(i).getNodeName() + "=\""
                            + node.getAttributes().item(i).getNodeValue() + "\"");
                }
            }

            out.println();
            indent += "|   ";
        }

        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            printDocument(node.getChildNodes().item(i), indent, out);
        }
    }
}
