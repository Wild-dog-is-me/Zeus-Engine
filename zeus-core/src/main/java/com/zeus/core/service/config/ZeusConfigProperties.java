package com.zeus.core.service.config;

import com.zeus.core.constant.ZeusConstant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Configurable
@ConfigurationProperties(prefix = ZeusConstant.PROPERTIES_PREFIX)
public class ZeusConfigProperties {

    /**
     * 是否启动 Zeus-Engine
     */
    private Boolean enabled;

    /**
     * nacos主题配置名称
     */
    private String configName;
}
