package com.xin.lemontree.common.consts;

import com.xin.lemontree.tools.properties.PropertiesUtils;

/**
 * @author creator mafh 2018/1/18 18:08
 * @author updater mafh
 * @version 1.0.0
 * @description 系统配置文件读取
 */
public final class SysConfig {

    /**
     * redis key前缀
     */
    public static String REDIS_USER_SESSION_KEY;
    /**
     * redis 有效时间
     */
    public static Integer SSO_SESSION_EXPIRE;
    /**
     * cookieName
     */
    public static String COOKIE_NAME;

    /**
     * csdm settings
     */
    public static String CSDN_WEEKLY_PREURL;

    /**
     * 是否抓取数据
     */
    public static boolean FLAG_SPIDER = false;

    static {
        loadFromProp();
    }

    /**
     * 读取配置文件信息
     */
    public static void loadFromProp() {
        PropertiesUtils propertiesUtils = new PropertiesUtils("application.properties");
        REDIS_USER_SESSION_KEY = propertiesUtils.getString("redis.user_session_key");
        SSO_SESSION_EXPIRE = propertiesUtils.getInteger("redis.sso_session_expire");
        COOKIE_NAME = propertiesUtils.getString("cookie.name");
        CSDN_WEEKLY_PREURL = propertiesUtils.getString("csdn.weekly.preurl");
        FLAG_SPIDER = propertiesUtils.getBoolean("flagSpider");
        propertiesUtils.close();
    }
}
