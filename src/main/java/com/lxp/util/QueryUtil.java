package com.lxp.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class QueryUtil {
    private static final Map<String, String> queries = new HashMap<>();

    static {
        List<String> xmlFiles = List.of(
                "category_queries.xml",
                "course_queries.xml",
                "user_queries.xml",
                "section_queries.xml"
        );

        for (String xmlFile : xmlFiles) {
            loadQueries(xmlFile);
        }
    }

    /**
     * XML 파일을 로드하여 쿼리 맵에 추가한다.
     */
    private static void loadQueries(String xmlFile) {
        try (InputStream is = QueryUtil.class.getClassLoader().getResourceAsStream(xmlFile)) {

            if (is == null) {
                throw new RuntimeException("쿼리 파일을 찾을 수 없습니다: " + xmlFile);
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
            throw new RuntimeException("쿼리 로딩 중 오류 발생 (" + xmlFile + "): " + e.getMessage(), e);
        }
    }

    /**
     * key로 SQL 쿼리문을 가져온다.
     */
    public static String getQuery(String key) {
        String query = queries.get(key);
        if (query == null) {
            throw new RuntimeException("등록되지 않은 쿼리 키입니다: " + key);
        }
        return query;
    }
}
