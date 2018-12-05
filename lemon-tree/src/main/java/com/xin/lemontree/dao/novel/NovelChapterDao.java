package com.xin.lemontree.dao.novel;

import com.xin.lemontree.entity.novel.NovelChapterEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author creator 11934 2018/3/2 0002 18:54
 * @author updater 11934
 * @version 1.0.0
 * @description 小说章节dao
 */
public interface NovelChapterDao extends PagingAndSortingRepository<NovelChapterEntity, Integer>, JpaSpecificationExecutor<NovelChapterEntity> {

    /**
     * 查找章节内容为空的章节列表
     *
     * @param novelCode 小说编号
     * @return 列表
     */
    List<NovelChapterEntity> findByNovelCodeEqualsAndContentIsNull(String novelCode);

    /**
     * 查询小说最新的章节
     *
     * @param novelCode 小说编号
     * @return 列表
     */
    NovelChapterEntity findTopByNovelCodeEqualsOrderByIdDesc(String novelCode);

    /**
     * 查询上一章节
     *
     * @param id        id
     * @param novelCode 小说编号
     * @return
     */
    NovelChapterEntity findTopByIdLessThanAndNovelCodeEqualsOrderByIdDesc(Integer id, String novelCode);

    /**
     * 获取下一章节
     *
     * @param id        id
     * @param novelCode 小说编号
     * @return
     */
    NovelChapterEntity findTopByIdGreaterThanAndNovelCodeEqualsOrderById(Integer id, String novelCode);

    /**
     * 更新章节内容
     *
     * @param id      id
     * @param content 小说内容
     * @return
     */
    @Modifying
    @Query(value = "UPDATE NovelChapterEntity SET content = :content WHERE id = :id")
    int updateContent(@Param("content") String content, @Param("id") Integer id);
}
