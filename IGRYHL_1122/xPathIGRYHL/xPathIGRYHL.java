package xPathIGRYHL;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import org.xml.sax.SAXException; 

public class xPathIGRYHL {

    public static void main(String[] args) {
        try {
            InputStream inputStream = new FileInputStream("/Users/somodymate/Downloads/University/Adatkezelés XML-ben/IGRYHL_XMLGyak/IGRYHL_XMLGyak/IGRYHL_1122/kurzusfelvetelIGRYHL.xml");

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            XPathExpression expression = xPath.compile("/class/student");
            //XPathExpression expr = xPath.compile("/class/student[@id='02']");
            //XPathExpression expr = xPath.compile("//student");
            //XPathExpression expr = xPath.compile("/class/student[position() = 2]");
            //XPathExpression expr = xPath.compile("/class/student[last()]");
            //XPathExpression expr = xPath.compile("/class/student[position() = last() - 1]");
            //XPathExpression expr = xPath.compile("/class/student[position() <= 2]");
            //XPathExpression expr = xPath.compile("/class/*");
            //XPathExpression expr = xPath.compile("//student[@*]");
            //XPathExpression expr = xPath.compile("//*");
            //XPathExpression expr = xPath.compile("/class//*[kor > 20]");
            //XPathExpression expr = xPath.compile("//student/(keresztnev | vezeteknev)");

            NodeList nodeList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element studentElement = (Element) node;

                    System.out.println("ID: " + studentElement.getAttribute("id"));
                    System.out.println("Keresztnév: " + studentElement.getElementsByTagName("keresztnev").item(0).getTextContent());
                    System.out.println("Vezetéknév: " + studentElement.getElementsByTagName("vezeteknev").item(0).getTextContent());
                    System.out.println("Becenév: " + studentElement.getElementsByTagName("becenev").item(0).getTextContent());
                    System.out.println("Kor: " + studentElement.getElementsByTagName("kor").item(0).getTextContent());
                    System.out.println();
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
        }
    }
}
