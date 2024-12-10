package com.zeus.core.service;

public interface ZeusBaseConfig {

    /**
     * 启动配置变更监听器
     */
    void addListener();

    /**
     * 通过文件名获取文件内容
     */
    String getConfigByName(String name);

    /**
     * 设置配置
     */
    void setConfigProperty(String key, String value);

    /**
     * 删除配置
     */
    void removeConfigProperty(String key);
}
