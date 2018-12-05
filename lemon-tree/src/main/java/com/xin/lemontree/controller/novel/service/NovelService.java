package com.xin.lemontree.controller.novel.service;

import com.xin.lemontree.entity.novel.NovelChapterEntity;
import com.xin.lemontree.tools.page.Pageable;
import com.xin.lemontree.vo.novel.NovelChapterVo;
import com.xin.lemontree.vo.novel.NovelVo;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author creator 11934 2018/3/2 0002 15:03
 * @author updater 11934
 * @version 1.0.0
 * @description 小说service
 */
public interface NovelService {

    /**
     * 获取小说列表
     *
     * @param request   请求
     * @param NovelCode 小说编号
     * @return 列表
     */
    List<Map<String, Object>> spiderNovelList(HttpServletRequest request, String NovelCode);

    /**
     * 获取小说内容
     *
     * @param novelCode 小说编号
     * @return 列表
     */
    List<Map<String, Object>> spiderNovelContent(String novelCode);

    /**
     * 抓取更新小说
     *
     * @param novelCode 小说编号
     * @return 列表
     */
    List<NovelChapterEntity> spiderUpdatedNovelList(String novelCode);

    /**
     * 获取小说列表
     *
     * @param pageable  分页
     * @param orderType 排序类型
     * @return 分页数据
     */
    Page<NovelVo> getNovelPage(Pageable pageable, Integer orderType);

    /**
     * 获取小说章节列表
     *
     * @param novelCode 小说编号
     * @param pageable  分页
     * @param orderType 排序类型
     * @param keywords  关键字
     * @return 分页数据
     */
    Page<NovelChapterVo> getNovelChapterPage(String novelCode, Pageable pageable, Integer orderType, String keywords);

    /**
     * 获取所有小说列表
     *
     * @return 列表
     */
    List<NovelVo> getNovelList();

    /**
     * 获取小说章节
     *
     * @param id id
     * @return 信息
     */
    NovelChapterVo getNovelChapter(Integer id);

    /**
     * 获取小说
     *
     * @param novelCode 小说编号
     * @return 信息
     */
    NovelVo getNovel(String novelCode);

    /**
     * 定时抓取更新章节
     *
     * @return 更新章节数
     */
    Integer updateNovels();

    /**
     * 添加小说
     *
     * @param novelName   小说名称
     * @param novelCode   小说编号
     * @param url         url
     * @param coverImg    封面路径
     * @param flagEnd     是否完结
     * @param description 简介
     * @return 结果
     */
    Integer addNovel(String novelName, String novelCode, String url, String coverImg, Integer flagEnd, String description);
}
