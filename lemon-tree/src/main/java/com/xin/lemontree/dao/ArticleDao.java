package com.xin.lemontree.dao;

import com.xin.lemontree.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author creator mafh 2018/3/1 11:05
 * @author updater mafh
 * @version 1.0.0
 * @description 文章数据访问层
 */
public interface ArticleDao extends PagingAndSortingRepository<ArticleEntity, Integer>, JpaSpecificationExecutor<ArticleEntity> {
}
