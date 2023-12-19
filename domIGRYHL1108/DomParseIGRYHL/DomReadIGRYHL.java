 package DomParseIGRYHL;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomReadIGRYHL {

	public static void main(String argv[]) throws SAXException, IOException, ParserConfigurationException{

		File xmlFile = new File("kurzusfelvetelIGRYHL.xml");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();

		Document doc = dBuilder.parse(xmlFile);

		doc.getDocumentElement().normalize();

		System.out.println("\nDokumentum: " + doc.getDocumentElement().getNodeName());

		/* Kurzus felvétel (Tanév, Egytem) */
		NodeList tList = doc.getElementsByTagName("IGRYHL_kurzusfelvetel");

		for (int i = 0; i < tList.getLength(); i++) {
			Node nNode = tList.item(i);
			System.out.println("\n -- " + nNode.getNodeName() + " -- ");

			if(nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element elem1 = (Element) nNode;
				String tanev = elem1.getAttribute("tanev");

				Element elem2 = (Element) nNode;
				String egyetem = elem2.getAttribute("egyetem");


				System.out.println("Tanév: " + tanev);
				System.out.println("Egyetem: " + egyetem);
			}

		}

		NodeList hList = doc.getElementsByTagName("hallgato");

		for (int i = 0; i < hList.getLength(); i++) {
			Node nNode = hList.item(i);
			System.out.println("\n -- " + nNode.getNodeName() + " -- ");

			if(nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element elem = (Element) nNode;
				String hid = elem.getAttribute("id");

				Node node1 = elem.getElementsByTagName("hnev").item(0);
				String nev = node1.getTextContent();

				Node node2 = elem.getElementsByTagName("szulev").item(0);
				String szulev = node2.getTextContent();

				Node node3 = elem.getElementsByTagName("szak").item(0);
				String szak = node3.getTextContent();


				System.out.println("Neptun kód: " + hid);
				System.out.println("Név: " + nev);
				System.out.println("Születési év: " + szulev);
				System.out.println("Szak: " + szak);
			}

		}

		NodeList kList = doc.getElementsByTagName("kurzus");
		int k = 1;

		for (int i = 0; i < kList.getLength(); i++) {
			Node nNode = kList.item(i);
			System.out.println("\n -- " + nNode.getNodeName() + "  " + k + " -- ");
			k++;

			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element) nNode;
				String kid = elem.getAttribute("id");

				Node node1 = elem.getElementsByTagName("kurzusnev").item(0);
				String knev = node1.getTextContent();

				Node node2 = elem.getElementsByTagName("kredit").item(0);
				String kredit = node2.getTextContent();

				Node node3 = elem.getElementsByTagName("hely").item(0);
				String hely = node3.getTextContent();

				Node node4 = elem.getElementsByTagName("idopont").item(0);
				String ido = node4.getTextContent();

				Node node5 = elem.getElementsByTagName("oktato").item(0);
				String okt = node5.getTextContent();


				System.out.println("Kurzus id: " + kid);
				System.out.println("Kurzus neve: " + knev);
				System.out.println("Kredit: " + kredit);
				System.out.println("Terem: " + hely);
				System.out.println("Időpont: " + ido);
				System.out.println("Oktato: " + okt);
			}

		}

	}

}
