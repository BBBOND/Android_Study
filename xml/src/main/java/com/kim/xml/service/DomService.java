package com.kim.xml.service;


import com.kim.xml.model.Person;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by KIM on 2015/8/6.
 */
public class DomService {

    public DomService() {
    }

    public static List<Person> getPersons(InputStream inputStream) throws Exception {
        List<Person> list = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputStream);
        Element element = document.getDocumentElement();
        NodeList personNodes = element.getElementsByTagName("person");
        for (int i = 0; i < personNodes.getLength(); i++) {
            Element personElement = (Element) personNodes.item(i);
            Person person = new Person();
            person.setId(Integer.parseInt(personElement.getAttribute("id")));
            NodeList childNodes = personElement.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    if ("name".equals(childNodes.item(j).getNodeName())) {
                        person.setName(childNodes.item(j).getFirstChild().getNodeValue());
                    } else if ("age".equals(childNodes.item(j).getNodeName())) {
                        person.setAge(Integer.parseInt(childNodes.item(j).getFirstChild().getNodeValue()));
                    }
                }
            }
            list.add(person);
        }
        return list;
    }
}