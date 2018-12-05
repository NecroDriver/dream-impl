package com.xin.lemontree.dao.novel.specification;

import com.xin.lemontree.common.consts.CommonConsts;
import com.xin.lemontree.entity.novel.NovelEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;

/**
 * @author creator 11934 2018/3/24 0024 19:48
 * @author updater 11934
 * @version 1.0.0
 * @description
 */
public class NovelSpecification {

    public static Specification<NovelEntity> selectList() {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Path<Integer> flagDeletePath = root.get("flagDelete");
            return criteriaBuilder.equal(flagDeletePath, CommonConsts.FLAG_DELETE_NO);
        };
    }
}
