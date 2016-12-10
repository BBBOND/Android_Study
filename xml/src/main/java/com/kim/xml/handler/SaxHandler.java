package com.kim.xml.handler;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by KIM on 2015/8/6.
 */
public class SaxHandler extends DefaultHandler {

    private HashMap<String, String> map = null;//存储单个解析的完整对象
    private List<HashMap<String, String>> list = null;//处理所有对象
    private String currentTag = null;//正在解析的元素的标签
    private String currentValue;//元素值
    private String nodeName = null;//节点名称

    public SaxHandler(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<HashMap<String, String>> getList() {
        return list;
    }

    /**
     * 读到第一个开始标签时触发
     *
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        list = new ArrayList<>();
    }

    /**
     * 当遇到文档开头时触发
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals(nodeName)) {
            map = new HashMap<>();
        }
        if (attributes != null && map != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                map.put(attributes.getQName(i), attributes.getValue(i));
            }
        }
        currentTag = qName;
    }

    /**
     * 处理xml文件所读取到的内容
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentTag != null && map != null && !currentTag.equals(nodeName)) {
            currentValue = new String(ch, start, length);
            if (currentValue != null && !currentValue.trim().equals("") && !currentValue.trim().equals("\n")) {
                map.put(currentTag, currentValue);
            }
        }
        currentTag = null;
        currentValue = null;
    }

    /**
     * 遇到结束标记触发
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals(nodeName)) {
            list.add(map);
            map = null;
        }
    }
}
