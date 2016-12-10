package com.kim.xml.service;

import com.kim.xml.handler.SaxHandler;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by KIM on 2015/8/6.
 */
public class SaxService {

    public SaxService() {
    }

    public static List<HashMap<String, String>> readXML(InputStream inputStream, String nodeName) {
        try {
            //创建工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            SaxHandler saxHandler = new SaxHandler(nodeName);
            parser.parse(inputStream, saxHandler);
            inputStream.close();
            return saxHandler.getList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
