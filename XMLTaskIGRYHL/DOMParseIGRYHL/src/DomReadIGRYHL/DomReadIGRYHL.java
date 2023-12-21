package DomReadIGRYHL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DomReadIGRYHL {
    public static void main(String[] args) {
        try {
            // kimeneti fajl inicializalasa
            File newXMLFile = new File("XML_read_output.txt");
            StreamResult newXmlStream = new StreamResult(newXMLFile);

            // dom parser factory inicializalasa
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // dom parser letrehozasa
            DocumentBuilder builder = factory.newDocumentBuilder();
            // xml fajl beolvasasa a dom-ba
            Document doc = builder.parse(new File("XMLIGRYHL.xml"));

            // ures szovegek torlese a dom-bol
            cutEmptyStrings(doc.getDocumentElement());

            // xml fajlba iras
            writeDoc(doc, newXmlStream);

            // konzolra kiiras
            System.out.println(makeToXMLFormat(doc));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ures szovegek torlese a dom-bol
    private static void cutEmptyStrings(Node root) {
        NodeList nodeList = root.getChildNodes();
        List<Node> deleteEmptyLists = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() == Node.TEXT_NODE
                    && nodeList.item(i).getTextContent().isEmpty()) {
                deleteEmptyLists.add(nodeList.item(i));
            } else {
                cutEmptyStrings(nodeList.item(i));
            }
        }
        for (Node node : deleteEmptyLists) {
            root.removeChild(node);
        }
    }

    // dom irasa xml fajlba
    public static void writeDoc(Document document, StreamResult output) {
        try {
            // transformer factory inicializalasa
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            // transformer letrehozasa
            Transformer transformer = transformerFactory.newTransformer();

            // kimeneti formazas beallitasa
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            // dom forras inicializalasa
            DOMSource source = new DOMSource(document);
            // dom irasa kimeneti stream-re
            transformer.transform(source, output);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // xml formazasa
    public static String makeToXMLFormat(Document document) {
        return "<?xml version=\"" + document.getXmlVersion() + "\" encoding=\"" + document.getXmlEncoding() + "\" ?>"
                + elementsToXMLFormat(document.getDocumentElement(), 0);
    }

    // xml elemek formazasa
    public static String elementsToXMLFormat(Node node, int indent) {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            return "";
        }
        StringBuilder output = new StringBuilder();
        output.append(getIndent(indent)).append("<").append(((Element) node).getTagName());
        if (node.hasAttributes()) {
            for (int i = 0; i < node.getAttributes().getLength(); i++) {
                Node attribute = node.getAttributes().item(i);
                output.append(" ").append(attribute.getNodeName()).append("=\"").append(attribute.getNodeValue())
                        .append("\"");
            }
        }
        NodeList children = node.getChildNodes();
        if (children.getLength() == 1 && children.item(0).getNodeType() == Node.TEXT_NODE) {
            output.append(">").append(children.item(0).getTextContent().trim()).append("</")
                    .append(((Element) node).getTagName()).append(">\n");
        } else {
            output.append(">\n");
            for (int i = 0; i < children.getLength(); i++) {
                output.append(elementsToXMLFormat(children.item(i), indent + 1));
            }
            output.append(getIndent(indent)).append("</").append(((Element) node).getTagName()).append(">\n");
        }
        return output.toString();
    }

    // ures helyek szamanak lekerdezese
    private static String getIndent(int indent) {
        StringBuilder indentation = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            indentation.append("    ");
        }
        return indentation.toString();
    }
}
