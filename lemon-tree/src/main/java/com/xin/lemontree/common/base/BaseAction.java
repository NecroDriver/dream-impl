package com.xin.lemontree.common.base;

import com.xin.lemontree.controller.exception.ValidateException;
import com.xin.lemontree.tools.type.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * @author creator 11934 2018/3/13 0013 17:04
 * @author updater 11934
 * @version 1.0.0
 * @description 基础action，用于存放一些基础数据
 */
public class BaseAction {

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    /**
     * 说明：ModelAttribute的作用
     * 1)放置在形参上，表示引用model中的数据
     * 2）放置在方法上面：表示请求该类的每个Action前都会首先执行它，也可以将一些准备数据的操作放置在该方法里面。
     *
     * @param request
     * @param response
     */
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 日志记录
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 验证是否为整数
     *
     * @param object     对象
     * @param objectName 对象名称
     */
    protected void validateInteger(Object object, String objectName) {
        String patternStr = "\\d";
        if (!Pattern.matches(patternStr, object + "")) {
            throw new ValidateException(objectName + "应该为整数！");
        }
    }


    /**
     * 验证是否为空
     *
     * @param object     对象
     * @param objectName 对象名称
     */
    protected void validateNotEmpty(Object object, String objectName) {
        if (!StringUtils.isNotEmpty(object + "")) {
            throw new ValidateException(objectName + "不能为空！");
        }
    }
}
