package com.xin.lemontree.tools.properties;

import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * @author creator mafh 2018/1/19 10:27
 * @author updater mafh
 * @version 1.0.0
 * @description 配置文件工具类
 */
public class PropertiesUtils {

    private Properties properties = new Properties();
    private InputStream input = null;

    public PropertiesUtils(String fileName) {
        try {
            input = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
            if (null == input) {
                input = PropertiesUtils.class.getResourceAsStream(fileName);
            }
            properties.load(input);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PropertiesUtils(InputStream inputStream) {
        try {
            input = inputStream;
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭流
     */
    public void close() {
        try {
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public Object setProperties(String key, String value) {
        return properties.setProperty(key, value);
    }

    public Set<String> stringPropertyNames() {
        return properties.stringPropertyNames();
    }

    /**
     * 获取名称属性值
     *
     * @param name 名称
     * @return 属性值
     */
    private String getPropertyStr(String name) {
        return properties.getProperty(name);
    }

    public String getString(String name) {
        return getPropertyStr(name);
    }

    public String getString(String name, String defaultValue) {
        String value = getPropertyStr(name);
        if (value == null || value.trim().equals("")) {
            value = defaultValue;
        }
        return value;
    }

    public Boolean getBoolean(String name) {
        String value = getPropertyStr(name);
        return value == null ? null : Boolean.valueOf(value);
    }

    public Boolean getBoolean(String name, Boolean defaultValue) {
        String value = getPropertyStr(name);
        return value == null ? defaultValue : Boolean.valueOf(value);
    }

    public Integer getInteger(String name) {
        String value = getPropertyStr(name);
        return value == null ? null : Integer.valueOf(value);
    }

    public Integer getInteger(String name, Integer defaultValue) {
        String value = getPropertyStr(name);
        return value == null ? defaultValue : Integer.valueOf(value);
    }

    public Long getLong(String name){
        String value = getPropertyStr(name);
        return value == null ? null : Long.valueOf(value);
    }

    public Long getLong(String name, Long defaultValue) {
        String value = getPropertyStr(name);
        return value == null ? defaultValue : Long.valueOf(value);
    }
}
