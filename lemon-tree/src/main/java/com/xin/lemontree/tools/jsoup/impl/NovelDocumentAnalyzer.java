package com.xin.lemontree.tools.jsoup.impl;

import com.xin.lemontree.tools.jsoup.DocumentAnalyzer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author creator 11934 2018/3/2 0002 14:13
 * @author updater 11934
 * @version 1.0.0
 * @description 小说网页解析
 */
@Component
public class NovelDocumentAnalyzer extends DocumentAnalyzer {
    /**
     * 根据html文档对象获取List<Map>
     *
     * @param document html文档对象
     * @return 列表
     */
    @Override
    public List<Map<String, Object>> getMapList(Document document) {
        List<Map<String, Object>> results = new ArrayList<>();
        if (ObjectUtils.isEmpty(document)) {
            return results;
        }
        Elements elements = document.getElementsByTag("dd");
        for (int i = 9; i < elements.size(); i++) {
            Element element = elements.get(i);
            Map<String, Object> result = new HashMap<>();
            result.put("chapterName", element.getElementsByTag("a").get(0).text());
            String url = element.getElementsByTag("a").get(0).attr("href");
            result.put("url", url);
            results.add(result);
        }
        return results;
    }

    /**
     * 根据html文档对象获取Map
     *
     * @param document html文档对象
     * @return 集合
     */
    @Override
    public Map<String, Object> getMap(Document document) {
        Map<String, Object> result = new HashMap<>();
        if (ObjectUtils.isEmpty(document)) {
            return result;
        }
        result.put("content", document.body().getElementById("content").text());
        return result;
    }
}
