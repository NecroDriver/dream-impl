package com.xin.lemontree.dao.novel;

import com.xin.lemontree.entity.novel.NovelEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author creator 11934 2018/3/13 0013 16:38
 * @author updater 11934
 * @version 1.0.0
 * @description 小说dao
 */
public interface NovelDao extends PagingAndSortingRepository<NovelEntity, Integer>, JpaSpecificationExecutor<NovelEntity> {

    /**
     * 根据小说编号获取小说
     *
     * @param novelCode 小说编号
     * @return 信息
     */
    NovelEntity findTopByNovelCodeEquals(String novelCode);

    /**
     * 更新flagUpdate标志
     *
     * @param novelCode  小说编号
     * @param flagUpdate 是否更新
     * @return 数量
     */
    @Modifying
    @Query("update NovelEntity set flagUpdate = :flagUpdate where novelCode = :novelCode")
    int updateFlagUpdate(@Param("novelCode") String novelCode, @Param("flagUpdate") Integer flagUpdate);
}
