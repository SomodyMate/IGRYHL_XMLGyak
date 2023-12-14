package DomQueryIGRYHL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DomQueryIGRYHL {

    public static void main(String[] args) {
        try {
            // XML fájl beolvasása
            File xmlFile = new File("kurzusfelvetelIGRYHL.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Lekérdezések és kiíratások
            List<String> kurzusNevek = getKurzusNevek(doc);
            Element hallgatoElement = getHallgatoElement(doc);
            List<String> oktatokNevei = getOktatokNevei(doc);

            System.out.println("Kurzusnév: " + kurzusNevek);

            System.out.println("Hallgató adatai:");
            printElement(hallgatoElement, 0);
            writeElementToFile(hallgatoElement, "hallgato_adatok.txt");

            System.out.println("Oktatók nevei: " + oktatokNevei);

            System.out.println("Hallgató neve: " + hallgatoElement.getElementsByTagName("hnev").item(0).getTextContent());
            System.out.println("Felvett kurzusok:");
            printFelvettKurzusok(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> getKurzusNevek(Document doc) {
        List<String> kurzusNevek = new ArrayList<>();
        NodeList kurzusNodeList = doc.getElementsByTagName("kurzusnev");

        for (int i = 0; i < kurzusNodeList.getLength(); i++) {
            Node node = kurzusNodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                kurzusNevek.add(element.getTextContent());
            }
        }

        return kurzusNevek;
    }

    private static Element getHallgatoElement(Document doc) {
        NodeList hallgatoNodeList = doc.getElementsByTagName("hallgato");
        return (Element) hallgatoNodeList.item(0);
    }

    private static void printElement(Element element, int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("  ");
        }
        System.out.println(element.getTagName() + ": " + element.getTextContent());

        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                printElement((Element) node, indent + 1);
            }
        }
    }

    private static void writeElementToFile(Element element, String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writeElementToFile(element, writer, 0);
        writer.close();
    }

    private static void writeElementToFile(Element element, FileWriter writer, int indent) throws IOException {
        for (int i = 0; i < indent; i++) {
            writer.append("  ");
        }
        writer.append(element.getTagName()).append(": ").append(element.getTextContent()).append("\n");

        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                writeElementToFile((Element) node, writer, indent + 1);
            }
        }
    }

    private static List<String> getOktatokNevei(Document doc) {
        List<String> oktatokNevei = new ArrayList<>();
        NodeList kurzusokNodeList = doc.getElementsByTagName("kurzus");

        for (int i = 0; i < kurzusokNodeList.getLength(); i++) {
            Node kurzusNode = kurzusokNodeList.item(i);

            if (kurzusNode.getNodeType() == Node.ELEMENT_NODE) {
                Element kurzusElement = (Element) kurzusNode;
                String oktatoNeve = kurzusElement.getElementsByTagName("oktato").item(0).getTextContent();
                oktatokNevei.add(oktatoNeve);
            }
        }

        return oktatokNevei;
    }

    private static void printFelvettKurzusok(Document doc) {
        NodeList kurzusokNodeList = doc.getElementsByTagName("kurzus");
        for (int i = 0; i < kurzusokNodeList.getLength(); i++) {
            Element kurzusElement = (Element) kurzusokNodeList.item(i);
            String kurzusNeve = kurzusElement.getElementsByTagName("kurzusnev").item(0).getTextContent();
            String kredit = kurzusElement.getElementsByTagName("kredit").item(0).getTextContent();
            String oktato = kurzusElement.getElementsByTagName("oktato").item(0).getTextContent();

            System.out.println("  Kurzusnév: " + kurzusNeve + ", Kredit: " + kredit + ", Oktató: " + oktato);
        }
    }
}
