package com.xin.lemontree.tools.jsoup;

import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

/**
 * @author creator mafh 2018/2/28 17:40
 * @author updater mafh
 * @version 1.0.0
 * @description 解析html文档抽象
 */
public abstract class DocumentAnalyzer {

    /**
     * 根据html文档对象获取List<Map>
     *
     * @param document html文档对象
     * @return 列表
     */
    public abstract List<Map<String, Object>> getMapList(Document document);

    /**
     * 根据html文档对象获取Map
     *
     * @param document html文档对象
     * @return 集合
     */
    public abstract Map<String, Object> getMap(Document document);
}
