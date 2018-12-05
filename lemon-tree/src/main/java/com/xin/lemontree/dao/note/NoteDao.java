package com.xin.lemontree.dao.note;

import com.xin.lemontree.entity.note.NoteEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * @author creator mafh 2018/4/24 16:10
 * @author updater mafh
 * @version 1.0.0
 * @description 笔记dao
 */
public interface NoteDao extends PagingAndSortingRepository<NoteEntity, Integer>, JpaSpecificationExecutor<NoteEntity> {

    /**
     * 根据id查询笔记信息
     *
     * @param id 笔记id
     * @return 数据
     */
    NoteEntity findByIdEquals(Integer id);

    /**
     * 获取下一篇
     *
     * @param creator    创建人
     * @param id         主键id
     * @param flagDelete 是否删除
     * @return 数据
     */
    NoteEntity findTopByCreatorEqualsAndIdLessThanAndFlagDeleteEqualsOrderByIdDesc(String creator, Integer id, Integer flagDelete);

    /**
     * 获取上一篇
     *
     * @param creator    创建人
     * @param id         主键id
     * @param flagDelete 是否删除
     * @return 数据
     */
    NoteEntity findTopByCreatorEqualsAndIdGreaterThanAndFlagDeleteEqualsOrderByIdAsc(String creator, Integer id, Integer flagDelete);

    /**
     * 更新flagUpdate标志
     *
     * @param id         笔记id
     * @param flagDelete 是否删除
     * @return 结果
     */
    @Modifying
    @Query("update NoteEntity set flagDelete = :flagDelete, modifyTime = :modifyTime, modifier = :modifier, modifierIP = :modifierIP where id = :id")
    int updateFlagDelete(@Param("id") Integer id, @Param("flagDelete") Integer flagDelete, @Param("modifyTime") Date modifyTime, @Param("modifier") String modifier, @Param("modifierIP") String modifierIP);

    /**
     * 更新笔记内容
     *
     * @param id         笔记id
     * @param title      标题
     * @param content    内容
     * @param labelId    标签id
     * @param modifier   修改人
     * @param modifyTime 修改时间
     * @param modifierIP 修改人ip
     * @return 结果
     */
    @Modifying
    @Query("update NoteEntity set title= :title, content = :content, labelId = :labelId, modifier = :modifier,modifyTime = :modifyTime, modifierIP = :modifierIP where id = :id")
    int updateInfo(@Param("id") Integer id, @Param("title") String title, @Param("content") String content, @Param("labelId") Integer labelId, @Param("modifier") String modifier, @Param("modifyTime") Date modifyTime, @Param("modifierIP") String modifierIP);
}
