package com.xin.lemontree.entity;

import com.xin.lemontree.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author creator mafh 2018/1/17 17:35
 * @author updater mafh
 * @version 1.0.0
 * @description 用户登录实体类
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "user_login")
@Entity
@Data
public class UserLoginEntity extends BaseEntity {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Transient
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
}
