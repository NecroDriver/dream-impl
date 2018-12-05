package com.xin.lemontree.controller.novel.service.impl;

import com.xin.lemontree.common.consts.SysConfig;
import com.xin.lemontree.controller.novel.service.ArticleService;
import com.xin.lemontree.dao.ArticleDao;
import com.xin.lemontree.entity.ArticleEntity;
import com.xin.lemontree.tools.jsoup.JsoupUtils;
import com.xin.lemontree.tools.jsoup.impl.CSDNWeeklyDocumentAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author creator mafh 2018/2/28 18:23
 * @author updater mafh
 * @version 1.0.0
 * @description 文章service实现
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    /**
     * 解析器注入
     */
    @Autowired
    private CSDNWeeklyDocumentAnalyzer csdnWeeklyDocumentAnalyzer;

    /**
     * 文章dao
     */
    @Autowired
    private ArticleDao articleDao;

    /**
     * 根据期号获取文章列表
     *
     * @param stage 期号
     * @return 文章列表
     */
    @Override
    public List<ArticleEntity> getListForWeek(Integer stage) throws Exception {
        List<ArticleEntity> articleEntityList = JsoupUtils.getEntityList(SysConfig.CSDN_WEEKLY_PREURL + stage, csdnWeeklyDocumentAnalyzer, ArticleEntity.class);
        articleEntityList.forEach(articleEntity -> {
            articleEntity.setStage(stage);
            articleEntity.setCreator("init");
            articleEntity.setCreateTime(new Date());
            articleEntity.setCreatorIP("127.0.0.1");
            articleEntity.setModifier("init");
            articleEntity.setModifyTime(new Date());
            articleEntity.setModifierIP("127.0.0.1");
        });
        articleDao.saveAll(articleEntityList);
        return articleEntityList;
    }
}
