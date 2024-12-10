package com.zeus.core.service.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegisterBeanService {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 将groovy class 注册到 Spring IOC 容器中
     * @param name
     * @param clazz
     * @param args
     * @param <T>
     */
    public <T> T registerBean(String name, Class<T> clazz, Object... args) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        for (Object arg : args) {
            beanDefinitionBuilder.addConstructorArgValue(arg);
        }
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) context.getBeanFactory();
        if (context.containsBean(name)) {
            log.info("bean [{}] 已存在，移除原先bean并更新", name);
            beanFactory.removeBeanDefinition(name);
        }
        beanFactory.registerBeanDefinition(name, beanDefinition);
        log.info("bean [{}] 注册成功", name);
        return applicationContext.getBean(name, clazz);
    }
}
