package com.xin.lemontree.controller.novel.action;

import com.xin.lemontree.controller.novel.service.ArticleService;
import com.xin.lemontree.entity.ArticleEntity;
import com.xin.lemontree.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author creator mafh 2018/3/1 11:11
 * @author updater mafh
 * @version 1.0.0
 * @description 文章action
 */
@Controller
@RequestMapping("/api/article")
public class ArticleAction {

    /**
     * 文章service
     */
    @Autowired
    private ArticleService articleService;

    /**
     * 获取文章数据
     *
     * @param stage 期号
     * @return 文章列表
     * @throws Exception 异常
     */
    @RequestMapping(value = "/getList/stage/{stage}", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo getListForArticle(@PathVariable("stage") Integer stage) throws Exception {
        List<ArticleEntity> articleEntityList = articleService.getListForWeek(stage);
        return ResultVo.newResultVo(true, "抓取文章成功！", articleEntityList);
    }
}
