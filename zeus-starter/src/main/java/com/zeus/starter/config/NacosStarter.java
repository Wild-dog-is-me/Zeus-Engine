package com.zeus.starter.config;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.zeus.core.constant.ZeusConstant;
import com.zeus.core.service.BaseZeusBaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

@Slf4j
@Service
public class NacosStarter extends BaseZeusBaseConfig implements Listener {

    @NacosInjected
    private ConfigService configService;

    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public void receiveConfigInfo(String mainConfig) {
        log.info("nacos监听到[{}]数据更新:[{}]", configProperties.getConfigName(), mainConfig);
        bootstrap(mainConfig);
    }

    @Override
    public void addListener() {
        try {
            configService.addListener(configProperties.getConfigName(), ZeusConstant.ZEUS_ENGINE_GROUP, this);
            log.info("nacos[{}]监听器启动", configProperties.getConfigName());
        } catch (Exception e) {
            log.error("nacos[{}]监听器启动失败 -> [{}]", configProperties.getConfigName(), e.getMessage());
        }
    }

    @Override
    public String getConfigByName(String configName) {
        try {
            return configService.getConfig(configName, ZeusConstant.ZEUS_ENGINE_GROUP, 5000L);
        } catch (Exception e) {
            log.error("获取配置[{}]失败 -> [{}]", configName, e.getMessage());
        }
        return null;
    }

    @Override
    public void setConfigProperty(String key, String value) {

    }

    @Override
    public void removeConfigProperty(String key) {
        try {
            configService.removeConfig(key, ZeusConstant.ZEUS_ENGINE_GROUP);
        } catch (Exception e) {
            log.error("删除配置[{}]失败 -> [{}]", key, e.getMessage());
        }
    }
}
