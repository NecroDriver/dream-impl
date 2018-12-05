package com.xin.lemontree.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author creator mafh 2018/1/17 17:35
 * @author updater mafh
 * @version 1.0.0
 * @description 用户登录实体类
 */
@Data
public class UserLoginVo implements Serializable {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 登录账号
     */
    private String account;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 明文密码，不持久化到数据库
     */
    private String password;
    /**
     * 加密后密码
     */
    private String encryptPassword;
    /**
     * 盐
     */
    private String salt;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 平台
     */
    private String platform;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建人ip
     */
    private String creatorIP;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改人ip
     */
    private String modifierIP;
    /**
     * token
     */
    private String token;
    /**
     * ip
     */
    private String ip;
}
