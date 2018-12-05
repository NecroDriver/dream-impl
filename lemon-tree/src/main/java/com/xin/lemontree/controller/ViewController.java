package com.xin.lemontree.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author creator mafh 2017/12/18 13:53
 * @author updater mafh
 * @version 1.0.0
 * @description
 */
@Controller
public class ViewController {

    /**
     * 初始化登录页面
     *
     * @return 视图
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * 主视图
     *
     * @return view
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    /**
     * websocket
     *
     * @return view
     */
    @RequestMapping(value = "/websocket", method = RequestMethod.GET)
    public String websocket() {
        return "websocket";
    }

    /**
     * 文章
     *
     * @return view
     */
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public String article() {
        return "article";
    }

    /**
     * 小说列表
     *
     * @return view
     */
    @RequestMapping(value = "/novel/list", method = RequestMethod.GET)
    public String novelList() {
        return "novel/list";
    }
}
