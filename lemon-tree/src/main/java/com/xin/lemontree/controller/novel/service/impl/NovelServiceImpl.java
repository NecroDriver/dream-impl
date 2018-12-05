package com.xin.lemontree.controller.novel.service.impl;

import com.xin.lemontree.common.base.BaseService;
import com.xin.lemontree.common.consts.CommonConsts;
import com.xin.lemontree.common.consts.SysConfig;
import com.xin.lemontree.controller.novel.service.NovelService;
import com.xin.lemontree.controller.websocket.TokenWebSocket;
import com.xin.lemontree.dao.novel.NovelChapterDao;
import com.xin.lemontree.dao.novel.NovelDao;
import com.xin.lemontree.dao.novel.specification.NovelChapterSpecification;
import com.xin.lemontree.dao.novel.specification.NovelSpecification;
import com.xin.lemontree.entity.novel.NovelChapterEntity;
import com.xin.lemontree.entity.novel.NovelEntity;
import com.xin.lemontree.tools.convert.ConvertUtils;
import com.xin.lemontree.tools.cookie.CookieUtils;
import com.xin.lemontree.tools.jsoup.JsoupUtils;
import com.xin.lemontree.tools.jsoup.impl.NovelDocumentAnalyzer;
import com.xin.lemontree.tools.page.Pageable;
import com.xin.lemontree.vo.UserLoginVo;
import com.xin.lemontree.vo.novel.NovelChapterVo;
import com.xin.lemontree.vo.novel.NovelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @author creator 11934 2018/3/2 0002 15:25
 * @author updater 11934
 * @version 1.0.0
 * @description 小说文章service实现类
 */
@Service
@Transactional
public class NovelServiceImpl extends BaseService implements NovelService {

    /**
     * 小说解析器
     */
    @Autowired
    private NovelDocumentAnalyzer novelDocumentAnalyzer;

    /**
     * 小说章节dao
     */
    @Autowired
    private NovelChapterDao novelChapterDao;

    /**
     * 小说dao
     */
    @Autowired
    private NovelDao novelDao;

    /**
     * webSocket注入
     */
    @Autowired
    private TokenWebSocket tokenWebSocket;

    /**
     * 获取小说列表
     *
     * @param request   请求
     * @param novelCode 小说编号
     * @return 列表
     */
    @Override
    public List<Map<String, Object>> spiderNovelList(HttpServletRequest request, String novelCode) {

        /*--------------------------------------------- 参数校验 -----------------------------------------------------*/
        Assert.notNull(novelCode, "小说编号不能为空！");
        UserLoginVo user = (UserLoginVo) request.getAttribute("user");
        Assert.notNull(user, "用户信息不能为空！");
        List<Map<String, Object>> results = new ArrayList<>();

        /*--------------------------------------------- 业务处理 -----------------------------------------------------*/
        try {
            NovelEntity novelEntity = novelDao.findTopByNovelCodeEquals(novelCode);
            Assert.notNull(novelEntity, "未查询到该小说！");
            int dispOrder = 0;
            List<NovelChapterEntity> novelChapterEntityList = JsoupUtils.getEntityList(novelEntity.getUrl(), novelDocumentAnalyzer, NovelChapterEntity.class);
            for (int i = 0; i < novelChapterEntityList.size(); i++) {
                NovelChapterEntity novelChapterEntity = novelChapterEntityList.get(i);
                try {
                    Thread.sleep(500);
                    // 获取文章内容
                    Map<String, Object> contentMap = JsoupUtils.getMap(novelChapterEntity.getUrl(), novelDocumentAnalyzer);
                    novelChapterEntity.setContent(contentMap.get("content") + "");
                } catch (Exception e1) {
                    logger.debug("发生异常" + novelChapterEntity.getChapterName());
                }
                novelChapterEntity.setNovelCode(novelCode);
                novelChapterEntity.setDispOrder(++dispOrder);
                novelChapterEntity.setCreator(user.getAccount());
                novelChapterEntity.setCreateTime(new Date());
                novelChapterEntity.setCreatorIP(user.getIp());
                novelChapterEntity.setModifier(user.getAccount());
                novelChapterEntity.setModifyTime(new Date());
                novelChapterEntity.setModifierIP(user.getIp());
                // 获取比例
                int percent = (i + 1) * 100 / novelChapterEntityList.size();
                // 发送webSocket消息
                tokenWebSocket.sendMessageByToken(CookieUtils.getCookieValue(request, SysConfig.COOKIE_NAME), percent + "");
            }
            novelChapterDao.saveAll(novelChapterEntityList);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.notNull(null, "数据读取失败！");
        }

        /*--------------------------------------------- 方法返回 -----------------------------------------------------*/
        return results;
    }

    /**
     * 获取小说内容
     *
     * @param novelCode 小说编号
     * @return 列表
     */
    @Override
    public List<Map<String, Object>> spiderNovelContent(String novelCode) {

        List<Map<String, Object>> results = new ArrayList<>();
        novelChapterDao.findByNovelCodeEqualsAndContentIsNull(novelCode).forEach(novelChapterEntity -> {
            try {
                Thread.sleep(2000);
                Map<String, Object> result = JsoupUtils.getMap(novelChapterEntity.getUrl(), novelDocumentAnalyzer);
                novelChapterDao.updateContent(result.get("content") + "", novelChapterEntity.getId());
                result.put("name", novelChapterEntity.getChapterName());
                results.add(result);
            } catch (Exception e) {
                System.out.println(e);
                logger.debug("发生异常！----" + novelChapterEntity.getChapterName());
            }
        });
        return results;
    }

    /**
     * 抓取更新小说
     *
     * @param novelCode 小说编号
     * @return 列表
     */
    @Override
    public List<NovelChapterEntity> spiderUpdatedNovelList(String novelCode) {

        List<NovelChapterEntity> results = new ArrayList<>();
        try {
            NovelEntity novelEntity = novelDao.findTopByNovelCodeEquals(novelCode);
            Assert.notNull(novelEntity, "未查询到该小说！");
            NovelChapterEntity newNovelChapterEntity = novelChapterDao.findTopByNovelCodeEqualsOrderByIdDesc(novelCode);
            Assert.notNull(newNovelChapterEntity, "未查询到最新章节！");
            int dispOrder = newNovelChapterEntity.getDispOrder();
            List<NovelChapterEntity> novelChapterEntityList = JsoupUtils.getEntityList(novelEntity.getUrl(), novelDocumentAnalyzer, NovelChapterEntity.class);
            for (int i = novelChapterEntityList.size() - 1; i >= 0; i--) {
                NovelChapterEntity novelChapterEntity = novelChapterEntityList.get(i);
                if (novelChapterEntity.getChapterName().equals(newNovelChapterEntity.getChapterName())) {
                    break;
                }
                Thread.sleep(500);
                // 获取文章内容
                Map<String, Object> contentMap = JsoupUtils.getMap(novelChapterEntity.getUrl(), novelDocumentAnalyzer);
                novelChapterEntity.setContent(contentMap.get("content") + "");
                novelChapterEntity.setNovelCode(novelCode);
                novelChapterEntity.setDispOrder(++dispOrder);
                novelChapterEntity.setCreator("init");
                novelChapterEntity.setCreateTime(new Date());
                novelChapterEntity.setCreatorIP("127.0.0.1");
                novelChapterEntity.setModifier("init");
                novelChapterEntity.setModifyTime(new Date());
                novelChapterEntity.setModifierIP("127.0.0.1");
                results.add(novelChapterEntity);
            }
            Collections.reverse(results);
            novelChapterDao.saveAll(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * 获取小说列表
     *
     * @param pageable  分页
     * @param orderType 排序类型
     * @return 分页数据
     */
    @Override
    public Page<NovelVo> getNovelPage(Pageable pageable, Integer orderType) {

        /*------------------------------------------- 参数声明 ------------------------------------------*/
        Sort.Order order = new Sort.Order(orderType.equals(0) ? Sort.Direction.DESC : Sort.Direction.ASC, "id");
        Sort sort = new Sort(order);
        PageRequest pageRequest = new PageRequest(pageable.getPageNo(), pageable.getPageSize(), sort);

        /*------------------------------------------- 业务处理 ------------------------------------------*/
        Page<NovelEntity> novelEntityPage = novelDao.findAll(pageRequest);
        Page<NovelVo> novelVoPage = novelEntityPage.map(novelEntity -> ConvertUtils.convert(novelEntity, NovelVo.class));

        /*------------------------------------------- 日志记录 ------------------------------------------*/
        logger.debug("成功获取小说列表" + novelVoPage);

        /*------------------------------------------- 方法返回 ------------------------------------------*/
        return novelVoPage;
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
    @Override
    public Page<NovelChapterVo> getNovelChapterPage(String novelCode, Pageable pageable, Integer orderType, String keywords) {

        /*------------------------------------------- 参数声明 ------------------------------------------*/
        Sort.Order idOrder = new Sort.Order(orderType.equals(0) ? Sort.Direction.DESC : Sort.Direction.ASC, "id");
        Sort sort = new Sort(idOrder);
        PageRequest pageRequest = new PageRequest(pageable.getPageNo(), pageable.getPageSize(), sort);
        Specification<NovelChapterEntity> specification = NovelChapterSpecification.selectByNovleCode(novelCode, keywords);

        /*------------------------------------------- 业务处理 ------------------------------------------*/
        Page<NovelChapterEntity> novelChapterEntityPage = novelChapterDao.findAll(specification, pageRequest);
        Page<NovelChapterVo> novelChapterVoPage = novelChapterEntityPage.map(novelChapterEntity -> ConvertUtils.convert(novelChapterEntity, NovelChapterVo.class));

        /*------------------------------------------- 日志记录 ------------------------------------------*/
        logger.debug("成功获取小说章节列表" + novelChapterVoPage);

        /*------------------------------------------- 方法返回 ------------------------------------------*/
        return novelChapterVoPage;
    }

    /**
     * 获取所有小说列表
     *
     * @return 列表
     */
    @Override
    public List<NovelVo> getNovelList() {

        /*------------------------------------------- 业务处理 ------------------------------------------*/
        List<NovelEntity> novelEntityList = novelDao.findAll(NovelSpecification.selectList());
        List<NovelVo> novelVoList = new ArrayList<>();
        for (NovelEntity novelEntity : novelEntityList) {
            novelVoList.add(ConvertUtils.convert(novelEntity, NovelVo.class));
        }

        /*------------------------------------------- 日志记录 ------------------------------------------*/
        logger.debug("成功获取小说列表" + novelVoList);

        /*------------------------------------------- 方法返回 ------------------------------------------*/
        return novelVoList;
    }

    /**
     * 获取小说章节
     *
     * @param id id
     * @return 信息
     */
    @Override
    public NovelChapterVo getNovelChapter(Integer id) {

        /*------------------------------------------- 业务处理 ------------------------------------------*/
        NovelChapterEntity novelChapterEntity = novelChapterDao.findById(id).get();
        NovelChapterVo novelChapterVo = ConvertUtils.convert(novelChapterEntity, NovelChapterVo.class);
        // 获取上一章节id
        novelChapterEntity = novelChapterDao.findTopByIdLessThanAndNovelCodeEqualsOrderByIdDesc(id, novelChapterVo.getNovelCode());
        if (!ObjectUtils.isEmpty(novelChapterEntity)) {
            novelChapterVo.setPrevId(novelChapterEntity.getId());
        }
        // 获取下一章节id
        novelChapterEntity = novelChapterDao.findTopByIdGreaterThanAndNovelCodeEqualsOrderById(id, novelChapterVo.getNovelCode());
        if (!ObjectUtils.isEmpty(novelChapterEntity)) {
            novelChapterVo.setNextId(novelChapterEntity.getId());
        }

        /*------------------------------------------- 日志记录 ------------------------------------------*/
        logger.debug("成功获取小说章节" + novelChapterVo);

        /*------------------------------------------- 方法返回 ------------------------------------------*/
        return novelChapterVo;
    }

    /**
     * 获取小说
     *
     * @param novelCode 小说编号
     * @return 信息
     */
    @Override
    public NovelVo getNovel(String novelCode) {

        /*------------------------------------------- 业务处理 ------------------------------------------*/
        NovelEntity novelEntity = novelDao.findTopByNovelCodeEquals(novelCode);
        Assert.notNull(novelEntity, "未查询到该小说！");
        NovelVo novelVo = ConvertUtils.convert(novelEntity, NovelVo.class);

        /*------------------------------------------- 日志记录 ------------------------------------------*/
        logger.debug("成功获取小说" + novelVo);

        /*------------------------------------------- 方法返回 ------------------------------------------*/
        return novelVo;
    }

    /**
     * 定时抓取更新章节
     *
     * @return 更新章节数
     */
    @Override
    public Integer updateNovels() {

        /*------------------------------------------- 参数声明 ------------------------------------------*/
        Integer total = 0;

        /*------------------------------------------- 业务处理 ------------------------------------------*/
        // 获取所有的小说列表
        List<NovelEntity> novelEntityList = novelDao.findAll(NovelSpecification.selectList());
        for (NovelEntity novelEntity : novelEntityList) {
            // 获取更新数据
            List<NovelChapterEntity> novelChapterEntityList = spiderUpdatedNovelList(novelEntity.getNovelCode());
            // 使用websocket通知更新 TODO
            if (novelChapterEntityList.size() > 0) {
                // 更新数据库相关字段
                novelDao.updateFlagUpdate(novelEntity.getNovelCode(), CommonConsts.FLAG_UPDATE_YES);
                total += novelChapterEntityList.size();
            }
        }

        /*------------------------------------------- 方法返回 ------------------------------------------*/
        return total;
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
    @Override
    public Integer addNovel(String novelName, String novelCode, String url, String coverImg, Integer flagEnd, String description) {

        /*------------------------------------------- 参数声明 ------------------------------------------*/

        /*------------------------------------------- 业务处理 ------------------------------------------*/
        NovelEntity novelEntity = new NovelEntity();
        novelEntity.setNovelName(novelName);
        novelEntity.setNovelCode(novelCode);
        novelEntity.setUrl(url);
        novelEntity.setCoverImg(coverImg);
        novelEntity.setFlagDelete(CommonConsts.FLAG_DELETE_NO);
        novelEntity.setFlagUpdate(CommonConsts.FLAG_UPDATE_NO);
        novelEntity.setFlagEnd(flagEnd);
        novelEntity.setDescription(description);
        novelEntity.setCreateTime(new Date());
        novelEntity.setCreator("init");
        novelEntity.setCreatorIP("127.0.0.1");
        novelEntity.setModifyTime(new Date());
        novelEntity.setModifier("init");
        novelEntity.setModifierIP("127.0.0.1");
        novelDao.save(novelEntity);

        /*------------------------------------------- 方法返回 ------------------------------------------*/
        return novelEntity.getId();
    }
}
