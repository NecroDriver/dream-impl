package com.xin.lemontree.dao.note;

import com.xin.lemontree.entity.note.LabelEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @author creator mafh 2018/4/24 16:13
 * @author updater mafh
 * @version 1.0.0
 * @description 标签dao
 */
public interface LabelDao extends PagingAndSortingRepository<LabelEntity, Integer>, JpaSpecificationExecutor<LabelEntity> {

    /**
     * 根据id查询
     *
     * @param id id
     * @return 实体
     */
    LabelEntity findByIdEquals(Integer id);

    /**
     * 根据标签名称查询
     *
     * @param labelName  标签名称
     * @param flagDelete 是否删除
     * @return 实体
     */
    LabelEntity findByLabelNameEqualsAndFlagDeleteEquals(String labelName, Integer flagDelete);

    /**
     * 根据删除状态获取所有标签
     *
     * @param flagDelete 是否删除
     * @return 列表
     */
    List<LabelEntity> findAllByFlagDeleteEqualsOrderByIdDesc(Integer flagDelete);

    /**
     * 更新flagUpdate标志
     *
     * @param id         记录id
     * @param flagDelete 是否删除
     * @return 结果
     */
    @Modifying
    @Query("update LabelEntity set flagDelete = :flagDelete,modifyTime = :modifyTime,modifier = :modifier,modifierIP= :modifierIP where id = :id")
    int updateFlagDelete(@Param("id") Integer id, @Param("flagDelete") Integer flagDelete, @Param("modifyTime") Date modifyTime, @Param("modifier") String modifier, @Param("modifierIP") String modifierIP);

}
