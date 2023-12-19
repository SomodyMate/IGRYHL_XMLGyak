package DomWriteIGRYHL;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DomWriteIGRYHL {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();

            Document doc = dBuilder.newDocument();

            Element root = doc.createElementNS("2023/24", "kurzusfelvetel1");
            doc.appendChild(root);

            root.appendChild(createHallgato(doc, "IGRYHL", "Somody Máté", "2001.05.19", "programtervező informatikus"));
            root.appendChild(createKurzus(doc, "GEIAL332-B", "Adatkezelés XML-ben", "5", "A1 32.előadó", "Kedd 12-14", "Dr. Bednarik László"));
            root.appendChild(createKurzus(doc, "GEIAL315-B", "Vállalati Információs Rendszerek", "5", "In 103", "Szerda 14-16", "Dr. Sasvári Péter"));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            File myFile = new File("kurzusfelvetel1IGRYHL.xml");

            StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(myFile);

            transformer.transform(source, console);
            transformer.transform(source, file);

        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private static Element createHallgato(Document doc, String haid, String hanev, String szulev, String szak) {
        Element hallgato = doc.createElement("hallgato");

        hallgato.setAttribute("id", haid);
        hallgato.appendChild(createElement(doc, "Neve", hanev));
        hallgato.appendChild(createElement(doc, "Szulev", szulev));
        hallgato.appendChild(createElement(doc, "Szak", szak));

        return hallgato;
    }

    private static Element createKurzus(Document doc, String kuid, String kunev, String kredit, String hely, String idopont, String oktato) {
        Element kurzus = doc.createElement("kurzus");

        kurzus.setAttribute("id", kuid);
        kurzus.appendChild(createElement(doc, "kurzusnev", kunev));
        kurzus.appendChild(createElement(doc, "Kredit", kredit));
        kurzus.appendChild(createElement(doc, "Hely", hely));
        kurzus.appendChild(createElement(doc, "Idopont", idopont));
        kurzus.appendChild(createElement(doc, "Oktato", oktato));

        return kurzus;
    }

    private static Element createElement(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));

        return node;
    }
}
