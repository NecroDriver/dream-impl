package com.xin.lemontree.dao.novel.specification;

import com.xin.lemontree.entity.novel.NovelChapterEntity;
import com.xin.lemontree.tools.type.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;

/**
 * @author creator 11934 2018/3/29 0029 10:15
 * @author updater 11934
 * @version 1.0.0
 * @description
 */
public class NovelChapterSpecification {

    public static Specification<NovelChapterEntity> selectByNovleCode(String novelCode, String keyword) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Path<String> novelCodePath = root.get("novelCode");
            if (StringUtils.isNotEmpty(keyword)){
                Path<String> keywordPath = root.get("chapterName");
                return criteriaBuilder.and(criteriaBuilder.equal(novelCodePath, novelCode), criteriaBuilder.like(keywordPath, "%" + keyword + "%"));
            }else{
                return criteriaBuilder.equal(novelCodePath, novelCode);
            }
        };
    }
}
