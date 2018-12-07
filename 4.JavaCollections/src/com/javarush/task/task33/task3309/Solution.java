package com.javarush.task.task33.task3309;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;

/*
Комментарий внутри xml
*/
public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment) throws JAXBException, ParserConfigurationException, TransformerException {
        StringWriter stringWriter = new StringWriter();
        String result;
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        marshaller.marshal(obj, document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        //transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        //transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        //transformer.setOutputProperty(OutputKeys.STANDALONE, "no");

        Node root = document.getDocumentElement();
        NodeList tags = document.getElementsByTagName(tagName);
        NodeList alltags = root.getChildNodes();
        checkCDATA(alltags, document);
        if (tags.getLength()>0){
            for (int i = 0; i < tags.getLength(); i++) {
                Node tag = tags.item(i);
                tag.getParentNode().insertBefore(document.createComment(comment),tag);
            }
        }

        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new ByteArrayOutputStream());
        transformer.transform(domSource, streamResult);
        result = streamResult.getOutputStream().toString();
        return result;
    }

    public static void checkCDATA(NodeList listtag, Document document){
        for (int i = 0; i < listtag.getLength(); i++) {
            Node tag = listtag.item(i);
            if (tag.getFirstChild().getNodeType() == Node.TEXT_NODE) {
                Node currentNode = tag.getFirstChild();
                if (currentNode.getTextContent().matches(".*[<>&'\"].*")) {
                    String content = currentNode.getTextContent();
                    CDATASection cdataSection = document.createCDATASection(content);
                    tag.replaceChild(cdataSection, currentNode);
                }
            }else{
                if (tag.hasChildNodes()){
                    NodeList subtags = tag.getChildNodes();
                    checkCDATA(subtags,document);
                }
            }
        }

    }

    public static void main(String[] args) throws TransformerException, JAXBException, ParserConfigurationException {
        System.out.println(
                Solution.toXmlWithComment(new First(), "second", "it's a comment"));
    }
}

@XmlRootElement(name = "first")
class First {
    @XmlElement(name = "second")
    public String item1 = "some string";
    @XmlElement(name = "second")
    public String item2 = "need CDATA because of < and >";
    @XmlElement(name = "second")
    public String item3 = "";
    @XmlElement(name = "third")
    public String item4;
    @XmlElement(name = "forth")
    public Second[] third = new Second[]{new Second()};
    @XmlElement(name = "fifth")
    public String item5 = "need CDATA because of < and >";
}

class Second {
    @XmlElement(name = "second")
    public String item1 = "some string";
    @XmlElement(name = "second")
    public String item2 = "need CDATA because of <second>";
}