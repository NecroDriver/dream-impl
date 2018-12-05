package com.xin.lemontree.controller.novel.action;

import com.xin.lemontree.common.base.BaseAction;
import com.xin.lemontree.common.consts.SysConfig;
import com.xin.lemontree.controller.novel.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author creator 11934 2018/3/27 0027 18:59
 * @author updater 11934
 * @version 1.0.0
 * @description 小说定时任务控制器
 */
@Configuration
@EnableScheduling
@Controller
@RequestMapping("/job")
public class NovelJobAction extends BaseAction {

    /**
     * 小说service
     */
    @Autowired
    private NovelService novelService;

    /**
     * 定时抓取更新章节
     */
    @Scheduled(fixedRate = 60 * 1000 * 30)
    @RequestMapping(value = "/novels/update")
    @ResponseBody
    public void updateNovels() {
        if (SysConfig.FLAG_SPIDER) {
            /*--------------------------------------------- 日志记录 ----------------------------------------------*/
            logger.debug("定时抓取更新章节");

            /*--------------------------------------------- 业务处理 ----------------------------------------------*/
            Integer num = novelService.updateNovels();

            /*--------------------------------------------- 日志记录 ----------------------------------------------*/
            logger.debug("抓取章节数：" + num);
        }
    }
}
