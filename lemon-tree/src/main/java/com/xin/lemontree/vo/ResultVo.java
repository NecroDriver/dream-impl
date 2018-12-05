package com.xin.lemontree.vo;

import lombok.Data;

/**
 * @author creator mafh 2018/1/18 15:01
 * @author updater mafh
 * @version 1.0.0
 * @description 封装结果数据
 */
@Data
public class ResultVo<T> {
    /**
     * 数据结果
     */
    private boolean result;
    /**
     * 数据
     */
    private T data;
    /**
     * 结果信息
     */
    private String message;

    public ResultVo(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public ResultVo(boolean result, T data) {
        this.result = result;
        this.data = data;
    }

    public ResultVo(boolean result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    /**
     * 生成结果静态调用方法
     *
     * @param result  结论
     * @param message 信息
     * @param data    数据
     * @return 对象
     */
    public static ResultVo newResultVo(boolean result, String message, Object data) {
        return new ResultVo(result, message, data);
    }

    /**
     * 生成成功静态调用方法
     *
     * @param data 数据
     * @return 对象
     */
    public static ResultVo successVo(Object data) {
        return new ResultVo(true, "", data);
    }

    /**
     * 生成失败静态调用方法
     *
     * @param message 信息
     * @return 对象
     */
    public static ResultVo failureVo(String message) {
        return new ResultVo(false, message, null);
    }
}
