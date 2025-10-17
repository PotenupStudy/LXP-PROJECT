package com.lxp.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

<<<<<<< HEAD
public class QueryUtil {
=======
/**
 * 쿼리 관련 유틸 클래스
 */
public class QueryUtil {

>>>>>>> f3e14e7a8d06e4f273dab37a864c1cc3ce5f01a4
    private static final Map<String, String> queries = new HashMap<>();

    static {
        loadQueries();
    }

<<<<<<< HEAD
    private static void loadQueries() {
        try {
            InputStream inputStream = QueryUtil.class.getClassLoader().getResourceAsStream("section_queries.xml");

            if (inputStream == null)
                throw new RuntimeException("section_queries.xml not found");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("query");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element queryElement = (Element) nodeList.item(i);
                String key = queryElement.getAttribute("key");
                String sql = queryElement.getTextContent().trim();

                queries.put(key, sql);

            }
        } catch (Exception e) {
=======
    /**
     * 쿼리 로드
     */
    private static void loadQueries() {
        try {
            // 쿼리 xml 로드
            InputStream inputStream = QueryUtil.class.getClassLoader().getResourceAsStream("user_queries.xml");

            if(inputStream == null) {
                throw new RuntimeException("user_queries.xml not found");
            }

            // XML Document 관련 builder 생성
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // XML 파싱
            Document document = builder.parse(inputStream);

            document.getDocumentElement().normalize();

            // query 태그 기준 노드리스트 생성
            NodeList nodeList = document.getElementsByTagName("query");

            // key 기준 (key, query) 추출하여 HashMap에 넣음
            for(int i = 0; i < nodeList.getLength(); i++) {
                Element queryElement = (Element) nodeList.item(i);

                String key = queryElement.getAttribute("key");

                String sql = queryElement.getTextContent().trim();
                queries.put(key, sql);
            }
        } catch(Exception e) {
>>>>>>> f3e14e7a8d06e4f273dab37a864c1cc3ce5f01a4
            throw new RuntimeException("쿼리 로딩 중 오류 발생", e);
        }
    }

<<<<<<< HEAD
=======
    /**
     * Key에 해당하는 Query 얻기
     * @param key 쿼리 키
     * @return key에 해당하는 쿼리
     */
>>>>>>> f3e14e7a8d06e4f273dab37a864c1cc3ce5f01a4
    public static String getQuery(String key) {
        return queries.get(key);
    }
}
