package DomQueryIGRYHL;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import org.xml.sax.SAXException;

public class DomQueryIGRYHL {

  public static void main(String[] args) {
    try {
      // xml fajl beolvasasa es dom objektum letrehozasa
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      Document document = documentBuilder.parse("XMLIGRYHL.xml");

      // dom objektum normalizalasa
      document.getDocumentElement().normalize();

      // xpath inicializalasa
      XPath xPath = XPathFactory.newInstance().newXPath();

      // lekerdezesek
      String expression = "";

      // kulonbozo xpath lekerdezesek es kifejezesek

      //expression = "/gepjarmu/tulajdonos";
      //expression = "/gepjarmu/gyartas[@gyartasID='10B']";
      //expression = "/gepjarmu/motor[last()]";
      //expression = "/gepjarmu/karosszeria[szin='feher']";
      //expression = "/gepjarmu/gyartas[gyartasi_ev>2008]";
      expression = "/gepjarmu/birtoklas";
      NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);

      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        System.out.println("\nAktuális elem: " + node.getNodeName());

        // gepjarmu elem kiirasa
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("szemelygepjarmu")) {
          Element element = (Element) node;

          System.out.println("Rendszam: " + element.getAttribute("rendszam"));
          System.out.println("Tulaj jogsi: " + element.getElementsByTagName("tulaj_jogsi").item(0).getTextContent());
          System.out.println("Alvaz szama: " + element.getElementsByTagName("alvaz_szama").item(0).getTextContent());
          System.out.println("Motor szama: " + element.getElementsByTagName("motor_szama").item(0).getTextContent());
          System.out.println("Gyartasi szam: " + element.getElementsByTagName("gyartas_sorszam").item(0).getTextContent());
        }

        // tulajdonos elem kiirasa
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("tulajdonos")) {
          Element element = (Element) node;

          System.out.println("Jogositvany szama: " + element.getAttribute("jogositvany_szama"));
          System.out.println("Keresztnev: " + element.getElementsByTagName("keresztnev").item(0).getTextContent());
          System.out.println("Vezeteknev: " + element.getElementsByTagName("vezeteknev").item(0).getTextContent());
        }

        // motor elem kiirasa
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("motor")) {
          Element element = (Element) node;

          System.out.println("Motor szama: " + element.getAttribute("motorszam"));
          System.out.println("Uzemanyag: " + element.getElementsByTagName("uzemanyag").item(0).getTextContent());
          System.out.println("Loero: " + element.getElementsByTagName("loero").item(0).getTextContent());
          System.out.println("Nyomatek: " + element.getElementsByTagName("nyomatek").item(0).getTextContent());
        }

        // karosszeria elem kiirasa
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("karosszeria")) {
          Element element = (Element) node;

          System.out.println("Alvazszam: " + element.getAttribute("alvazszam"));
          System.out.println("Felulet: " + element.getElementsByTagName("felulet").item(0).getTextContent());
          System.out.println("Matrica: " + element.getElementsByTagName("matrica").item(0).getTextContent());
          System.out.println("Elsodleges szin: " + element.getElementsByTagName("szin").item(0).getTextContent());
        }

        // gyartas elem kiirasa
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("gyartas")) {
          Element element = (Element) node;

          System.out.println("ID: " + element.getAttribute("gyartasID"));
          System.out.println("Marka: " + element.getElementsByTagName("marka").item(0).getTextContent());
          System.out.println("Modell: " + element.getElementsByTagName("modell").item(0).getTextContent());
          System.out.println("Gyartasi ev: " + element.getElementsByTagName("gyartasi_ev").item(0).getTextContent());
        }
        // birtoklasok kiirasa

        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("birtoklas")){
          Element element = (Element) node;

          System.out.println("Rendszam: " + element.getAttribute("rendszam"));
          System.out.println("Jogositvany szama: " + element.getAttribute("jogositvany_szama"));
          System.out.println("Birtoklas kezdete: " + element.getElementsByTagName("birtoklas_kezdete").item(0).getTextContent());
        }
      }

    } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
      e.printStackTrace();
    }
  }
}
