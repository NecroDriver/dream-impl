package com.xin.lemontree.tools.jsoup.impl;

import com.xin.lemontree.tools.jsoup.DocumentAnalyzer;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author creator mafh 2018/2/28 17:45
 * @author updater mafh
 * @version 1.0.0
 * @description 解析CSDN每周知识干货html文档具体实现
 */
@Component
public class CSDNWeeklyDocumentAnalyzer extends DocumentAnalyzer {

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
        document.body().getElementsByClass("pclist").get(0).children().forEach(element -> {
            Map<String, Object> result = new HashMap<>();
            result.put("type", element.getElementsByTag("span").get(0).getElementsByTag("a").get(0).attr("href"));
            result.put("img", element.getElementsByTag("span").get(0).getElementsByTag("a").get(0).getElementsByTag("img").get(0).attr("src"));
            result.put("url", element.getElementsByTag("span").get(1).getElementsByTag("a").get(0).attr("href"));
            result.put("name", element.getElementsByTag("span").get(1).getElementsByTag("a").get(0).text());
            result.put("views", Integer.valueOf(element.getElementsByTag("span").get(1).getElementsByTag("span").get(0).getElementsByTag("em").get(0).text().replaceAll("\\D+", "")));
            result.put("collections", Integer.valueOf(element.getElementsByTag("span").get(1).getElementsByTag("span").get(1).getElementsByTag("em").get(0).text().replaceAll("\\D+", "")));
            results.add(result);
        });
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
        return null;
    }
}
