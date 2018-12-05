package com.xin.lemontree.controller.exception;

/**
 * @author creator 11934 2018/3/15 0015 18:38
 * @author updater 11934
 * @version 1.0.0
 * @description 自定义验证异常
 */
public class ValidateException extends RuntimeException {

    public ValidateException(String message) {
        super(message);
    }
}
