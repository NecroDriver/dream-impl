package com.xin.lemontree.controller.novel.action;

import com.xin.lemontree.common.base.BaseAction;
import com.xin.lemontree.controller.novel.service.NovelService;
import com.xin.lemontree.tools.page.Pageable;
import com.xin.lemontree.vo.ResultVo;
import com.xin.lemontree.vo.novel.NovelChapterVo;
import com.xin.lemontree.vo.novel.NovelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * @author creator 11934 2018/3/2 0002 15:33
 * @author updater 11934
 * @version 1.0.0
 * @description 小说action
 */
@Controller
@RequestMapping("/api/novel")
public class NovelAction extends BaseAction {

    /**
     * 小说service
     */
    @Autowired
    private NovelService novelService;

    /**
     * 获取小说列表
     *
     * @param novelCode 小说编号
     * @return 列表
     */
    @RequestMapping(value = "/spider/list/{novelCode}", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo spiderNovelList(@PathVariable("novelCode") String novelCode) {
        return ResultVo.newResultVo(true, "获取小说列表成功！", novelService.spiderNovelList(request, novelCode));
    }

    /**
     * 获取小说内容
     *
     * @param novelCode 小说编号
     * @return 列表
     */
    @RequestMapping(value = "/spider/content/{novelCode}", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo spiderNovelContent(@PathVariable("novelCode") String novelCode) {
        return ResultVo.newResultVo(true, "获取小说内容成功！", novelService.spiderNovelContent(novelCode));
    }

    /**
     * 获取小数更新章节
     *
     * @param novelCode 小说编号
     * @return 列表
     */
    @RequestMapping(value = "/spider/updated/{novelCode}", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo spiderUpdatedNovelList(@PathVariable("novelCode") String novelCode) {
        return ResultVo.newResultVo(true, "获取更新章节成功！", novelService.spiderUpdatedNovelList(novelCode));
    }

    /**
     * 获取小说分页
     *
     * @param pageable  分页
     * @param orderType 排序类型
     * @return 分页数据
     */
    @RequestMapping(value = "/getNovelPage", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo getNovelPage(Pageable pageable, Integer orderType) {

        /*-------------------------------------------- 日志记录 ------------------------------------------------------*/
        logger.debug("获取小说分页");

        /*-------------------------------------------- 参数校验 ------------------------------------------------------*/
        validateInteger(orderType, "排序方式");

        /*-------------------------------------------- 业务处理 ------------------------------------------------------*/
        Page<NovelVo> novelVoPage = novelService.getNovelPage(pageable, orderType);

        /*-------------------------------------------- 方法返回 ------------------------------------------------------*/
        return ResultVo.newResultVo(true, "获取小说分页成功！", novelVoPage);
    }

    /**
     * 获取小说章节列表
     *
     * @param novelCode 小说编号
     * @param pageable  分页
     * @param orderType 排序类型
     * @param keywords  关键字
     * @return 分页数据
     */
    @RequestMapping(value = "/{novelCode}/getNovelChapterPage", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo getNovelChapterPage(@PathVariable("novelCode") String novelCode, Pageable pageable, Integer orderType, String keywords) {

        /*-------------------------------------------- 日志记录 ------------------------------------------------------*/
        logger.debug("获取小说章节列表");

        /*-------------------------------------------- 参数校验 ------------------------------------------------------*/
        validateNotEmpty(novelCode, "小说编号");
        validateInteger(orderType, "排序方式");

        /*-------------------------------------------- 业务处理 ------------------------------------------------------*/
        Page<NovelChapterVo> novelChapterVoPage = novelService.getNovelChapterPage(novelCode, pageable, orderType, keywords);

        /*-------------------------------------------- 方法返回 ------------------------------------------------------*/
        return ResultVo.successVo(novelChapterVoPage);
    }

    /**
     * 获取所有小说列表
     *
     * @return 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo getNovelList() {

        /*-------------------------------------------- 日志记录 ------------------------------------------------------*/
        logger.debug("获取小说列表");

        /*-------------------------------------------- 业务处理 ------------------------------------------------------*/
        List<NovelVo> novelVoList = novelService.getNovelList();

        /*-------------------------------------------- 方法返回 ------------------------------------------------------*/
        return ResultVo.newResultVo(true, "获取小说列表成功！", novelVoList);
    }

    /**
     * 获取小说章节
     *
     * @param id id
     * @return 信息
     */
    @RequestMapping(value = "/chapter/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo getNovelChapter(@PathVariable("id") Integer id) {

        /*-------------------------------------------- 日志记录 ------------------------------------------------------*/
        logger.debug("获取小说章节");

        /*-------------------------------------------- 业务处理 ------------------------------------------------------*/
        NovelChapterVo novelChapterVo = novelService.getNovelChapter(id);

        /*-------------------------------------------- 方法返回 ------------------------------------------------------*/
        return ResultVo.successVo(novelChapterVo);
    }

    /**
     * 获取小说
     *
     * @param novelCode 小说编号
     * @return 信息
     */
    @RequestMapping(value = "/{novelCode}", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo getNovel(@PathVariable("novelCode") String novelCode) {

        /*-------------------------------------------- 日志记录 ------------------------------------------------------*/
        logger.debug("获取小说");

        /*-------------------------------------------- 业务处理 ------------------------------------------------------*/
        NovelVo novelVo = novelService.getNovel(novelCode);

        /*-------------------------------------------- 方法返回 ------------------------------------------------------*/
        return ResultVo.successVo(novelVo);
    }

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
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo addNovel(String novelName, String novelCode, String url, String coverImg, Integer flagEnd, String description) {

        /*-------------------------------------------- 日志记录 ------------------------------------------------------*/
        logger.debug("添加小说");

        /*-------------------------------------------- 业务处理 ------------------------------------------------------*/
        Integer id = novelService.addNovel(novelName, novelCode, url, coverImg, flagEnd, description);

        /*-------------------------------------------- 方法返回 ------------------------------------------------------*/
        return ResultVo.successVo(id);
    }
}
