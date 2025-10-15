package com.lxp.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class QueryUtil {
    private static final Map<String, String> quries = new HashMap<>();

    static{
        loadQueries();
    }

    private static void loadQueries() {
        try{
            InputStream inputStream = QueryUtil.class.getClassLoader().getResourceAsStream("courseQueries.xml");

            if(inputStream == null)
                throw new RuntimeException("courseQueries.xml not found");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("query");

            for(int i=0; i<nodeList.getLength(); i++){
                Element queryElement = (Element) nodeList.item(i);
                String key = queryElement.getAttribute("key");
                String sql = queryElement.getTextContent().trim();

                quries.put(key, sql);

            }
        } catch (Exception e){
            throw new RuntimeException("쿼리 로딩 중 오류 발생", e);
        }
    }

    public static String getQuery(String key) {
        return quries.get(key);
    }
}
