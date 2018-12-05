package com.xin.lemontree.controller.novel.service;

import com.xin.lemontree.entity.ArticleEntity;

import java.util.List;

/**
 * @author creator mafh 2018/2/28 18:21
 * @author updater mafh
 * @version 1.0.0
 * @description 文章数据service
 */
public interface ArticleService {

    /**
     * 根据期号获取文章列表
     *
     * @param stage 期号
     * @return 文章列表
     */
    List<ArticleEntity> getListForWeek(Integer stage) throws Exception;
}
