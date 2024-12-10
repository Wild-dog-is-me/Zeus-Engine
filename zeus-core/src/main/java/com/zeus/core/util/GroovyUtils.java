package com.zeus.core.util;

import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Slf4j
public class GroovyUtils {

    private static final GroovyClassLoader GROOVY_CLASS_LOADER = new GroovyClassLoader();

    /**
     * 解析groovy脚本并缓存
     * @param instanceName
     * @param groovyCode
     * @return
     */
    public static Class parseClazz(String instanceName, String groovyCode) {
        Class groovyClazz = null;
        try {
            ZenuCache.putCodeCache(instanceName, DigestUtils.md5DigestAsHex(groovyCode.getBytes(StandardCharsets.UTF_8)));
            groovyClazz = GROOVY_CLASS_LOADER.parseClass(groovyCode);
            log.info("groovy脚本成功解析:clazz=[{}]", instanceName);
        } catch (Exception e) {
            log.info("groovy脚本解析失败:clazz=[{}]，错误信息:[{}]", instanceName, e.getMessage());
        }
        return groovyClazz;
    }
}
