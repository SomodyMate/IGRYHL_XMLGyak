package hu.domparse.IGRYHL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomReadIGRYHL {

    public static void main(String argv[]) throws ParserConfigurationException, SAXException, IOException {

        // Adatok kiirasa console-ra

        Feldolgozas();

        // Adatok mentese TXT-be

        PrintStream out = new PrintStream(new FileOutputStream("XML_Parse_output.txt"));
        System.setOut(out);

        Feldolgozas();

    }

    public static void Feldolgozas() throws ParserConfigurationException, SAXException, IOException {
        File xmlFile = new File("XMLIGRYHL.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();

        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

        // szemelygepjarmuvek kiirasa

        NodeList nList = doc.getElementsByTagName("szemelygepjarmu");

        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);
            System.out.println("\nCurrent element: " + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elem = (Element) nNode;
                String rendszam = elem.getAttribute("rendszam");

                Node node1 = elem.getElementsByTagName("tulaj_jogsi").item(0);
                String tj = node1.getTextContent();

                Node node2 = elem.getElementsByTagName("motor_szama").item(0);
                String msz = node2.getTextContent();

                Node node3 = elem.getElementsByTagName("alvaz_szama").item(0);
                String asz = node3.getTextContent();

                Node node4 = elem.getElementsByTagName("gyartas_sorszam").item(0);
                String gys = node4.getTextContent();

                String ca = "nincs";
                Node node5 = elem.getElementsByTagName("casco_azon").item(0);
                if (node5 != null) {
                    ca = node5.getTextContent();
                }

                System.out.println("Rendszam: " + rendszam);
                System.out.println("Tulaj jogsi: " + tj);
                System.out.println("Motor szama: " + msz);
                System.out.println("Alvaz szama: " + asz);
                System.out.println("Gyartasi sorszam: " + gys);
                System.out.println("Casco azonosito: " + ca);

            }
        }

        // tulajdonosok kiirasa

        nList = doc.getElementsByTagName("tulajdonos");

        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);
            System.out.println("\nCurrent element: " + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elem = (Element) nNode;
                String jogsi = elem.getAttribute("jogositvany_szama");

                Node node1 = elem.getElementsByTagName("vezeteknev").item(0);
                String vn = node1.getTextContent();

                Node node2 = elem.getElementsByTagName("keresztnev").item(0);
                String kn = node2.getTextContent();

                System.out.println("Jogositvany szama: " + jogsi);
                System.out.println("Vezeteknev: " + vn);
                System.out.println("Keresztnev: " + kn);

            }
        }

        // motorok kiirasa

        nList = doc.getElementsByTagName("motor");

        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);
            System.out.println("\nCurrent element: " + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elem = (Element) nNode;
                String msz = elem.getAttribute("motorszam");

                Node node1 = elem.getElementsByTagName("uzemanyag").item(0);
                String ua = node1.getTextContent();

                Node node2 = elem.getElementsByTagName("loero").item(0);
                String le = node2.getTextContent();

                Node node3 = elem.getElementsByTagName("nyomatek").item(0);
                String ny = node3.getTextContent();

                System.out.println("Motorszam: " + msz);
                System.out.println("Uzemamyag: " + ua);
                System.out.println("Loero: " + le);
                System.out.println("Nyomatek: " + ny);

            }
        }

        // karosszeriak kiirasa

        nList = doc.getElementsByTagName("karosszeria");

        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);
            System.out.println("\nCurrent element: " + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elem = (Element) nNode;
                String asz = elem.getAttribute("alvazszam");

                Node node1 = elem.getElementsByTagName("felulet").item(0);
                String ft = node1.getTextContent();

                Node node2 = elem.getElementsByTagName("matrica").item(0);
                String ma = node2.getTextContent();

                System.out.println("Alvazszam: " + asz);
                System.out.println("Felulet: " + ft);
                System.out.println("Matrica: " + ma);


                for (int a = 0; a < elem.getElementsByTagName("szin").getLength(); a++) {
                    Node node3 = elem.getElementsByTagName("szin").item(a);
                    String sz = node3.getTextContent();
                    System.out.println("Szin #" + (a + 1) + ": " + sz);
                }

            }
        }

        // gyártási infók kiirasa

        nList = doc.getElementsByTagName("gyartas");

        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);
            System.out.println("\nCurrent element: " + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elem = (Element) nNode;
                String gyi = elem.getAttribute("gyartasID");

                Node node1 = elem.getElementsByTagName("marka").item(0);
                String mk = node1.getTextContent();

                Node node2 = elem.getElementsByTagName("modell").item(0);
                String me = node2.getTextContent();

                Node node3 = elem.getElementsByTagName("gyartasi_ev").item(0);
                String gye = node3.getTextContent();

                System.out.println("GyartasID: " + gyi);
                System.out.println("Marka: " + mk);
                System.out.println("Modell: " + me);
                System.out.println("Gyartasi ev: " + gye);

            }
        }

        return;
    }
}
