package com.lxp.utill;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class QueryUtil {
    private static final Map<String, String> queries = new HashMap<String, String>();

    static {
        loadQueries();
    }

    private static void loadQueries() {
        try {
            InputStream is = QueryUtil.class.getClassLoader().getResourceAsStream("category_queries.xml");

            if (is == null) {
                throw new RuntimeException("queries.xml not found");
            }

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document document = builder.parse(is);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("query");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element queryElement = (Element) nodeList.item(i);
                String key = queryElement.getAttribute("key");
                String sql = queryElement.getTextContent().trim();
                queries.put(key, sql);
            }
        } catch (Exception e) {
            throw new RuntimeException("쿼리 로딩중 오류 발생", e);
        }
    }

    public static String getQuery(String key) {
        return queries.get(key);
    }
}
