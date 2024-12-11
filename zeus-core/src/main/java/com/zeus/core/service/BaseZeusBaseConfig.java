package com.zeus.core.service;

import com.alibaba.fastjson2.JSON;
import com.zeus.core.domain.MainConfig;
import com.zeus.core.service.bean.RegisterBeanService;
import com.zeus.core.service.config.ZeusConfigProperties;
import com.zeus.core.util.GroovyUtils;
import com.zeus.core.util.ZeusCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Component
public abstract class BaseZeusBaseConfig implements ZeusBaseConfig {

    @Resource
    protected ZeusConfigProperties configProperties;

    @Resource
    private RegisterBeanService registerBeanService;

    @PostConstruct
    public void init() {
        // 启动解析并注册监听器
        log.info("[Zeus-Engine] 启动解析并注册监听器 配置名称:[{}]", configProperties.getConfigName());
        String conf = getConfigByName(configProperties.getConfigName());
        if (StringUtils.hasText(conf)) {
            bootstrap(conf);
            addListener();
            logLogo();
        }
    }

    /**
     * 1.解析主配置
     * 2.得到 groovy 配置后比对变化
     * 3.有变化的 groovy 重新注册
     *
     * @param mainConfig
     */
    public void bootstrap(String mainConfig) {
        try {
            MainConfig config = JSON.parseObject(mainConfig, MainConfig.class);
            for (String instanceName : config.getInstanceNames()) {
                String groovyCode = getConfigByName(instanceName);
                log.info("instanceName:[{}] - groovyCode:[{}]", instanceName, groovyCode);
                if (StringUtils.hasText(groovyCode) && ZeusCache.isDiff(instanceName, groovyCode)) {
                    register(instanceName, groovyCode);
                }
            }
        } catch (Exception e) {
            log.error("解析主配置失败:[{}]，失败原因:[{}]", mainConfig, e.getMessage());
        }
    }

    /**
     * 1.调用groovy加载器解析 生成class对象
     * 2.注册bean到spring容器中
     *
     * @param instanceName
     * @param groovyCode
     */
    public void register(String instanceName, String groovyCode) {
        Class groovyClazz = GroovyUtils.parseClazz(instanceName, groovyCode);
        if (Objects.nonNull(groovyClazz)) {
            Object registerBean = registerBeanService.registerBean(instanceName, groovyClazz);
            log.info("bean:[{}]已经注册到 Spring IOC 容器中", registerBean.getClass().getName());
        }
    }

    public void logLogo() {
        log.warn("\n" +
                "████████ ████████ ██     ██  ████████       ████████ ████     ██   ████████  ██ ████     ██ ████████\n" +
                "░░░░░░██ ░██░░░░░ ░██    ░██ ██░░░░░░       ░██░░░░░ ░██░██   ░██  ██░░░░░░██░██░██░██   ░██░██░░░░░ \n" +
                "     ██  ░██      ░██    ░██░██             ░██      ░██░░██  ░██ ██      ░░ ░██░██░░██  ░██░██      \n" +
                "    ██   ░███████ ░██    ░██░█████████ █████░███████ ░██ ░░██ ░██░██         ░██░██ ░░██ ░██░███████ \n" +
                "   ██    ░██░░░░  ░██    ░██░░░░░░░░██░░░░░ ░██░░░░  ░██  ░░██░██░██    █████░██░██  ░░██░██░██░░░░  \n" +
                "  ██     ░██      ░██    ░██       ░██      ░██      ░██   ░░████░░██  ░░░░██░██░██   ░░████░██      \n" +
                " ████████░████████░░███████  ████████       ░████████░██    ░░███ ░░████████ ░██░██    ░░███░████████\n" +
                "░░░░░░░░ ░░░░░░░░  ░░░░░░░  ░░░░░░░░        ░░░░░░░░ ░░      ░░░   ░░░░░░░░  ░░ ░░      ░░░ ░░░░░░░░ \n");
    }
}
