package com.xin.lemontree.controller.user.action;

import com.xin.lemontree.common.base.BaseAction;
import com.xin.lemontree.controller.user.service.UserLoginService;
import com.xin.lemontree.vo.ResultVo;
import com.xin.lemontree.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author creator mafh 2018/1/17 17:17
 * @author updater mafh
 * @version 1.0.0
 * @description
 */
@RestController
@RequestMapping("/api/user")
public class UserAction extends BaseAction {

    /**
     * 用户登录service
     */
    @Autowired
    private UserLoginService userLoginService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultVo register(String account, String userName, String password, String phone, String email) {
        Integer id = userLoginService.registerUser(account, userName, password, phone, email);
        return ResultVo.newResultVo(true, "注册成功！", id);
    }

    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultVo login(String account, String password) {
        UserLoginVo userLoginVo = userLoginService.login(request, response, account, password);
        return ResultVo.newResultVo(true, "登录成功！", userLoginVo);
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
    public ResultVo loginOut() {
        userLoginService.loginOut(request);
        return ResultVo.newResultVo(true, "退出成功！", null);
    }

    /**
     * 通过token获取用户
     *
     * @param token
     * @return userLoginVo
     */
    @RequestMapping(value = "/get/{token}", method = RequestMethod.POST)
    public ResultVo getUserByToken(String token) {
        UserLoginVo userLoginVo = userLoginService.queryUserByToken(token);
        return ResultVo.newResultVo(true, "成功获取用户信息！", userLoginVo);
    }

    /**
     * 通过cookie获取用户
     *
     * @return userLoginVo
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResultVo getUser() {
        UserLoginVo userLoginVo = userLoginService.queryUser(request);
        return ResultVo.newResultVo(true, "成功获取用户信息！", userLoginVo);
    }
}
