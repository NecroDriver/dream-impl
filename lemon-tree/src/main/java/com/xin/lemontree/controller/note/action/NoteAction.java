package com.xin.lemontree.controller.note.action;

import com.xin.lemontree.common.base.BaseAction;
import com.xin.lemontree.controller.note.service.INoteService;
import com.xin.lemontree.entity.note.NoteEntity;
import com.xin.lemontree.tools.page.Pageable;
import com.xin.lemontree.vo.ResultVo;
import com.xin.lemontree.vo.note.LabelVo;
import com.xin.lemontree.vo.note.NoteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author creator mafh 2018/4/24 15:40
 * @author updater mafh
 * @version 1.0.0
 * @description 笔记action
 */
@RestController
@RequestMapping("/api/note")
public class NoteAction extends BaseAction {

    /**
     * 笔记service
     */
    @Autowired
    private INoteService noteService;


    /**
     * 添加标签
     *
     * @param labelName 标签名称
     * @return 结果
     */
    @RequestMapping(value = "/saveLabelInfo", method = RequestMethod.POST)
    public ResultVo saveLabelInfo(String labelName) {

        /*----------------------------------------------- 日志记录 --------------------------------------------------*/
        logger.debug("添加标签");

        /*----------------------------------------------- 参数校验 --------------------------------------------------*/
        validateNotEmpty(labelName, "标签名称不能为空！");

        /*----------------------------------------------- 业务处理 --------------------------------------------------*/
        Map<String, Object> resultMap = noteService.saveLabelInfo(request, labelName);

        /*----------------------------------------------- 方法返回 --------------------------------------------------*/
        return ResultVo.successVo(resultMap);
    }

    /**
     * 删除标签
     *
     * @param id 记录id
     * @return 结果
     */
    @RequestMapping(value = "/deleteLabel", method = RequestMethod.POST)
    public ResultVo deleteLabel(Integer id) {

        /*----------------------------------------------- 日志记录 --------------------------------------------------*/
        logger.debug("删除标签");

        /*----------------------------------------------- 参数校验 --------------------------------------------------*/
        validateInteger(id, "标签id不能为空！");

        /*----------------------------------------------- 业务处理 --------------------------------------------------*/
        Map<String, Object> resultMap = noteService.deleteLabel(request, id);

        /*----------------------------------------------- 方法返回 --------------------------------------------------*/
        return ResultVo.successVo(resultMap);
    }

    /**
     * 获取标签列表
     *
     * @return 列表
     */
    @RequestMapping(value = "/getLabelList", method = RequestMethod.POST)
    public ResultVo getLabelList() {

        /*----------------------------------------------- 日志记录 --------------------------------------------------*/
        logger.debug("获取标签列表");

        /*----------------------------------------------- 业务处理 --------------------------------------------------*/
        List<LabelVo> labelList = noteService.getLabelList();

        /*----------------------------------------------- 方法返回 --------------------------------------------------*/
        return ResultVo.successVo(labelList);
    }

    /**
     * 新增笔记
     *
     * @param title   标题
     * @param content 内容
     * @param labelId 标签id
     * @return 结果
     */
    @RequestMapping(value = "/saveNoteInfo", method = RequestMethod.POST)
    public ResultVo saveNoteInfo(String title, String content, Integer labelId) {

        /*----------------------------------------------- 日志记录 --------------------------------------------------*/
        logger.debug("新增笔记");

        /*----------------------------------------------- 参数校验 --------------------------------------------------*/
        validateNotEmpty(title, "标题");
        validateNotEmpty(content, "内容");
        validateInteger(labelId, "关联标签");

        /*----------------------------------------------- 业务处理 --------------------------------------------------*/
        Map<String, Object> resultMap = noteService.saveNoteInfo(request, title, content, labelId);

        /*----------------------------------------------- 方法返回 --------------------------------------------------*/
        return ResultVo.successVo(resultMap);
    }

    /**
     * 获取笔记分页
     *
     * @param keyword  关键字
     * @param pageable 分页
     * @return 分页
     */
    @RequestMapping(value = "/getNotePage", method = RequestMethod.POST)
    public ResultVo getNotePage(String keyword, Pageable pageable) {

        /*----------------------------------------------- 日志记录 --------------------------------------------------*/
        logger.debug("获取笔记分页");

        /*----------------------------------------------- 业务处理 ----------------------------------------------*/
        Page<NoteEntity> noteEntityPage = noteService.getNotePage(request, keyword, pageable);

        /*----------------------------------------------- 方法返回 --------------------------------------------------*/
        return ResultVo.successVo(noteEntityPage);
    }


    /**
     * 获取单条笔记信息
     *
     * @param id 笔记id
     * @return 信息
     */
    @RequestMapping(value = "/getNoteInfo/{id}", method = RequestMethod.POST)
    public ResultVo getNoteInfo(@PathVariable("id") Integer id) {

        /*----------------------------------------------- 日志记录 --------------------------------------------------*/
        logger.debug("获取单条笔记信息");

        /*----------------------------------------------- 参数检验 ----------------------------------------------*/
        validateInteger(id, "笔记id不能为空！");

        /*----------------------------------------------- 业务处理 ----------------------------------------------*/
        NoteVo noteVo = noteService.getNoteInfo(id);

        /*----------------------------------------------- 方法返回 --------------------------------------------------*/
        return ResultVo.successVo(noteVo);
    }

    /**
     * 删除笔记
     *
     * @param id 笔记id
     * @return 结果
     */
    @RequestMapping(value = "/deleteNote/{id}", method = RequestMethod.POST)
    public ResultVo deleteNote(@PathVariable("id") Integer id) {

        /*----------------------------------------------- 日志记录 --------------------------------------------------*/
        logger.debug("删除笔记");

        /*----------------------------------------------- 参数校验 --------------------------------------------------*/
        validateInteger(id, "笔记id不能为空！");

        /*----------------------------------------------- 业务处理 --------------------------------------------------*/
        Map<String, Object> resultMap = noteService.deleteNote(request, id);

        /*----------------------------------------------- 方法返回 --------------------------------------------------*/
        return ResultVo.successVo(resultMap);
    }

    /**
     * 修改笔记
     *
     * @param id      笔记id
     * @param title   标题
     * @param content 内容
     * @param labelId 标签id
     * @return 结果
     */
    @RequestMapping(value = "/editNoteInfo", method = RequestMethod.POST)
    public ResultVo editNoteInfo(Integer id, String title, String content, Integer labelId) {

        /*----------------------------------------------- 日志记录 --------------------------------------------------*/
        logger.debug("修改笔记");

        /*----------------------------------------------- 参数校验 --------------------------------------------------*/
        validateInteger(id, "笔记id");
        validateNotEmpty(title, "标题");
        validateNotEmpty(content, "内容");
        validateInteger(labelId, "关联标签");

        /*----------------------------------------------- 业务处理 --------------------------------------------------*/
        Map<String, Object> resultMap = noteService.editNoteInfo(request, id, title, content, labelId);

        /*----------------------------------------------- 方法返回 --------------------------------------------------*/
        return ResultVo.successVo(resultMap);
    }
}
