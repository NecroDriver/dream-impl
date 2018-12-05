package com.xin.lemontree.tools.type;

/**
 * @author creator 11934 2018/3/16 0016 11:00
 * @author updater 11934
 * @version 1.0.0
 * @description 字符串工具类
 */
public class StringUtils {

    /**
     * 验证是否为空
     *
     * @param content 内容
     * @return 结果
     */
    public static boolean isNotEmpty(String content) {
        if (content != null && !content.trim().equals("")) {
            return true;
        }
        return false;
    }
}
