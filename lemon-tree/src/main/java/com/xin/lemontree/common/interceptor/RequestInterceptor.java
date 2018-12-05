package com.xin.lemontree.common.interceptor;

import com.xin.lemontree.common.consts.SysConfig;
import com.xin.lemontree.controller.user.service.UserLoginService;
import com.xin.lemontree.tools.cookie.CookieUtils;
import com.xin.lemontree.tools.request.RequestUtils;
import com.xin.lemontree.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author creator mafh 2018/2/6 11:40
 * @author updater mafh
 * @version 1.0.0
 * @description
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {

    /**
     * 用户登录service
     */
    private UserLoginService userLoginService;

    @Autowired
    public RequestInterceptor(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        String token = CookieUtils.getCookieValue(request, SysConfig.COOKIE_NAME);
        if (!StringUtils.isEmpty(token)) {
            UserLoginVo userLoginVo = userLoginService.queryUserByToken(token);
            if (!ObjectUtils.isEmpty(userLoginVo)) {
                userLoginVo.setIp(RequestUtils.getIPAddress(request));
                // 把用户信息放入request
                request.setAttribute("user", userLoginVo);
                return true;
            }
        }
        // 未通过验证，跳转到登录页面，把用户请求的url作为参数传递给登录页面。
        response.sendRedirect("/login.html?redirect=" + request.getRequestURI());
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        System.out.println("postHandle-------------看一下是在什么时候打印的");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        System.out.println("afterCompletion-------------看一下是在什么时候打印的");
    }
}
