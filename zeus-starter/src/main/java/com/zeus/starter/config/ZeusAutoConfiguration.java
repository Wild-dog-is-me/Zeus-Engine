package com.zeus.starter.config;

import com.zeus.core.constant.ZeusConstant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(value = com.alibaba.nacos.api.config.ConfigService.class)
@ConditionalOnProperty(value = ZeusConstant.ZEUS_ENABLED_PROPERTIES, havingValue = "true")
@ComponentScan(ZeusConstant.SCAN_PATH)
public class ZeusAutoConfiguration {


}