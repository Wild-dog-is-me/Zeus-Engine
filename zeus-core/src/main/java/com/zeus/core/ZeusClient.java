package com.zeus.core;

import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Component
public class ZeusClient {

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 根据脚本名称获取脚本实例
     *
     * @param instanceName
     * @param <T>
     * @return
     */
    public <T> T getInterfaceByName(String instanceName) {
        T bean = null;
        try {
            bean = (T) applicationContext.getBean(instanceName);
        } catch (Exception e) {
            log.error("获取脚本[{}]失败，失败原因:[{}]", instanceName, e);
        }
        return bean;
    }

    /**
     * 获取groovy对象
     *
     * @param instanceName
     * @return
     */
    public GroovyObject getGroovyObjectByName(String instanceName) {
        GroovyObject groovyObject = null;
        try {
            groovyObject = (GroovyObject) getInterfaceByName(instanceName);
        } catch (Exception e) {
            log.error("获取脚本[{}]失败，失败原因:[{}]", instanceName, e);
        }
        return groovyObject;
    }

    /**
     * 执行脚本方法
     *
     * @param instanceName
     * @param methodName
     * @param args
     * @return 脚本方法返回值
     */
    public Object execute(String instanceName, String methodName, Object[] args) {
        Object resObj = null;
        GroovyObject groovyObject = getGroovyObjectByName(instanceName);
        try {
            if (Objects.nonNull(groovyObject)) {
                resObj = groovyObject.invokeMethod(methodName, args);
            }
        } catch (Exception e) {
            log.error("执行[{}]脚本[{}]方法失败，失败原因:[{}]", instanceName, methodName, e);
        }
        return resObj;
    }


}
