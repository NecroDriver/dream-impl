package com.xin.lemontree.dao.note.specification;

import com.xin.lemontree.common.consts.CommonConsts;
import com.xin.lemontree.entity.note.NoteEntity;
import com.xin.lemontree.tools.type.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author creator mafh 2018/4/25 17:09
 * @author updater mafh
 * @version 1.0.0
 * @description
 */
public class NoteSpecification {

    public static Specification<NoteEntity> selectListByAccount(String account, String keyword) {
        return new Specification<NoteEntity>() {
            @Override
            public Predicate toPredicate(Root<NoteEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                Path<String> accountPath = root.get("creator");
                predicates.add(criteriaBuilder.equal(accountPath, account));
                Path<Integer> flagDeletePath = root.get("flagDelete");
                predicates.add(criteriaBuilder.equal(flagDeletePath, CommonConsts.FLAG_DELETE_NO));
                if (StringUtils.isNotEmpty(keyword)) {
                    Path<String> titlePath = root.get("title");
                    predicates.add(criteriaBuilder.like(titlePath, "%" + keyword + "%"));
                }
                Predicate[] predicateArr = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(predicateArr)).getRestriction();
            }
        };
    }
}
