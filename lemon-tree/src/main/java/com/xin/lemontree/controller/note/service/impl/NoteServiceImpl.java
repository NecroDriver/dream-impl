package com.xin.lemontree.controller.note.service.impl;

import com.xin.lemontree.common.base.BaseService;
import com.xin.lemontree.common.consts.CommonConsts;
import com.xin.lemontree.controller.note.service.INoteService;
import com.xin.lemontree.dao.note.LabelDao;
import com.xin.lemontree.dao.note.NoteDao;
import com.xin.lemontree.dao.note.specification.NoteSpecification;
import com.xin.lemontree.entity.note.LabelEntity;
import com.xin.lemontree.entity.note.NoteEntity;
import com.xin.lemontree.tools.convert.ConvertUtils;
import com.xin.lemontree.tools.page.Pageable;
import com.xin.lemontree.vo.UserLoginVo;
import com.xin.lemontree.vo.note.LabelVo;
import com.xin.lemontree.vo.note.NoteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @author creator mafh 2018/4/24 16:04
 * @author updater mafh
 * @version 1.0.0
 * @description 笔记service实现类
 */
@Service
public class NoteServiceImpl extends BaseService implements INoteService {

    /**
     * 笔记dao
     */
    @Autowired
    private NoteDao noteDao;

    /**
     * 标签dao
     */
    @Autowired
    private LabelDao labelDao;

    /**
     * 添加标签
     *
     * @param request   请求
     * @param labelName 标签名称
     * @return 结果
     */
    @Override
    public Map<String, Object> saveLabelInfo(HttpServletRequest request, String labelName) {

        /*----------------------------------------------- 参数声明 --------------------------------------------------*/
        Map<String, Object> resultMap = new HashMap<>();
        UserLoginVo user = (UserLoginVo) request.getAttribute("user");
        Date nowDate = new Date();

        /*----------------------------------------------- 业务处理 --------------------------------------------------*/
        LabelEntity existLabelEntity = labelDao.findByLabelNameEqualsAndFlagDeleteEquals(labelName, CommonConsts.FLAG_DELETE_NO);
        Assert.isNull(existLabelEntity, "该标签已存在");
        LabelEntity labelEntity = new LabelEntity();
        labelEntity.setLabelName(labelName);
        labelEntity.setFlagDelete(CommonConsts.FLAG_DELETE_NO);
        labelEntity.setCreateTime(nowDate);
        labelEntity.setCreator(user.getAccount());
        labelEntity.setCreatorIP(user.getIp());
        labelEntity.setModifyTime(nowDate);
        labelEntity.setModifier(user.getAccount());
        labelEntity.setModifierIP(user.getIp());
        labelDao.save(labelEntity);
        resultMap.put("insertId", labelEntity.getId());

        /*----------------------------------------------- 日志记录 --------------------------------------------------*/
        logger.debug("添加标签成功！" + resultMap);

        /*----------------------------------------------- 方法返回 --------------------------------------------------*/
        return resultMap;
    }

    /**
     * 删除标签
     *
     * @param request 请求
     * @param id      记录id
     * @return 结果
     */
    @Override
    @Transactional
    public Map<String, Object> deleteLabel(HttpServletRequest request, Integer id) {

        /*----------------------------------------------- 参数声明 --------------------------------------------------*/
        Map<String, Object> resultMap = new HashMap<>();
        UserLoginVo user = (UserLoginVo) request.getAttribute("user");
        Date nowDate = new Date();

        /*----------------------------------------------- 业务处理 --------------------------------------------------*/
        int updateNum = labelDao.updateFlagDelete(id, CommonConsts.FLAG_DELETE_YES, nowDate, user.getAccount(), user.getIp());
        resultMap.put("updateNum", updateNum);

        /*----------------------------------------------- 日志记录 --------------------------------------------------*/
        logger.debug("删除标签成功！" + resultMap);

        /*----------------------------------------------- 方法返回 --------------------------------------------------*/
        return resultMap;
    }

    /**
     * 获取标签列表
     *
     * @return 列表
     */
    @Override
    public List<LabelVo> getLabelList() {

        /*----------------------------------------------- 参数声明 --------------------------------------------------*/
        List<LabelVo> labelVos = new ArrayList<>();

        /*----------------------------------------------- 业务处理 --------------------------------------------------*/
        List<LabelEntity> labelEntities = labelDao.findAllByFlagDeleteEqualsOrderByIdDesc(CommonConsts.FLAG_DELETE_NO);
        for (LabelEntity labelEntity : labelEntities) {
            LabelVo labelVo = ConvertUtils.convert(labelEntity, LabelVo.class);
            labelVos.add(labelVo);
        }

        /*----------------------------------------------- 日志记录 --------------------------------------------------*/
        logger.debug("获取标签列表成功！");

        /*----------------------------------------------- 方法返回 --------------------------------------------------*/
        return labelVos;
    }

    /**
     * 保存笔记
     *
     * @param request 请求
     * @param title   标题
     * @param content 内容
     * @param labelId 标签id
     * @return 结果
     */
    @Override
    public Map<String, Object> saveNoteInfo(HttpServletRequest request, String title, String content, Integer labelId) {

        /*----------------------------------------------- 参数声明 --------------------------------------------------*/
        Map<String, Object> resultMap = new HashMap<>();
        UserLoginVo user = (UserLoginVo) request.getAttribute("user");
        Date nowDate = new Date();

        /*----------------------------------------------- 业务处理 --------------------------------------------------*/
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setTitle(title);
        noteEntity.setContent(content);
        noteEntity.setLabelId(labelId);
        noteEntity.setFlagDelete(CommonConsts.FLAG_DELETE_NO);
        noteEntity.setCreateTime(nowDate);
        noteEntity.setCreator(user.getAccount());
        noteEntity.setCreatorIP(user.getIp());
        noteEntity.setModifyTime(nowDate);
        noteEntity.setModifier(user.getAccount());
        noteEntity.setModifierIP(user.getIp());
        noteDao.save(noteEntity);
        resultMap.put("insertId", noteEntity.getId());

        /*----------------------------------------------- 日志记录 --------------------------------------------------*/
        logger.debug("保存笔记成功！" + resultMap);

        /*----------------------------------------------- 方法返回 --------------------------------------------------*/
        return resultMap;
    }

    /**
     * 获取笔记分页
     *
     * @param request  请求
     * @param keyword  关键字
     * @param pageable 分页
     * @return 分页
     */
    @Override
    public Page<NoteEntity> getNotePage(HttpServletRequest request, String keyword, Pageable pageable) {

        /*----------------------------------------------- 参数声明 --------------------------------------------------*/
        UserLoginVo user = (UserLoginVo) request.getAttribute("user");
        PageRequest pageRequest = new PageRequest(pageable.getPageNo(), pageable.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<NoteEntity> specification = NoteSpecification.selectListByAccount(user.getAccount(), keyword);

        /*----------------------------------------------- 业务处理 --------------------------------------------------*/
        Page<NoteEntity> noteEntityPage = noteDao.findAll(specification, pageRequest);

        /*----------------------------------------------- 日志记录 --------------------------------------------------*/
        logger.debug("获取笔记分页成功！");

        /*----------------------------------------------- 方法返回 --------------------------------------------------*/
        return noteEntityPage;
    }

    /**
     * 获取单条笔记
     *
     * @param id 笔记id
     * @return 数据
     */
    @Override
    public NoteVo getNoteInfo(Integer id) {

        /*----------------------------------------------- 业务处理 --------------------------------------------------*/
        NoteEntity noteEntity = noteDao.findByIdEquals(id);
        NoteVo noteVo = ConvertUtils.convert(noteEntity, NoteVo.class);
        noteVo.setPrevNote(noteDao.findTopByCreatorEqualsAndIdGreaterThanAndFlagDeleteEqualsOrderByIdAsc(noteVo.getCreator(), id, CommonConsts.FLAG_DELETE_NO));
        noteVo.setNextNote(noteDao.findTopByCreatorEqualsAndIdLessThanAndFlagDeleteEqualsOrderByIdDesc(noteVo.getCreator(), id, CommonConsts.FLAG_DELETE_NO));

        /*----------------------------------------------- 日志记录 --------------------------------------------------*/
        logger.debug("获取单条笔记成功！");

        /*----------------------------------------------- 方法返回 --------------------------------------------------*/
        return noteVo;
    }

    /**
     * 删除笔记
     *
     * @param request 请求
     * @param id      笔记id
     * @return 结果
     */
    @Override
    @Transactional
    public Map<String, Object> deleteNote(HttpServletRequest request, Integer id) {

        /*----------------------------------------------- 参数声明 --------------------------------------------------*/
        Map<String, Object> resultMap = new HashMap<>();
        UserLoginVo user = (UserLoginVo) request.getAttribute("user");
        Date nowDate = new Date();

        /*----------------------------------------------- 业务处理 --------------------------------------------------*/
        int updateNum = noteDao.updateFlagDelete(id, CommonConsts.FLAG_DELETE_YES, nowDate, user.getAccount(), user.getIp());
        resultMap.put("updateNum", updateNum);

        /*----------------------------------------------- 日志记录 --------------------------------------------------*/
        logger.debug("删除笔记成功！" + resultMap);

        /*----------------------------------------------- 方法返回 --------------------------------------------------*/
        return resultMap;
    }

    /**
     * 修改笔记
     *
     * @param request 请求
     * @param id      笔记id
     * @param title   标题
     * @param content 内容
     * @param labelId 标签id
     * @return 结果
     */
    @Override
    @Transactional
    public Map<String, Object> editNoteInfo(HttpServletRequest request, Integer id, String title, String content, Integer labelId) {

        /*----------------------------------------------- 参数声明 --------------------------------------------------*/
        Map<String, Object> resultMap = new HashMap<>();
        UserLoginVo user = (UserLoginVo) request.getAttribute("user");
        Date nowDate = new Date();

        /*----------------------------------------------- 业务处理 --------------------------------------------------*/
        int updateNum = noteDao.updateInfo(id, title, content, labelId, user.getAccount(), nowDate, user.getIp());
        resultMap.put("updateNum", updateNum);

        /*----------------------------------------------- 日志记录 --------------------------------------------------*/
        logger.debug("修改标签成功！" + resultMap);

        /*----------------------------------------------- 方法返回 --------------------------------------------------*/
        return resultMap;
    }
}
