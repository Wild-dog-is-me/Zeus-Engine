package com.zeus.example.api;

import com.zeus.core.ZeusClient;
import com.zeus.example.service.SendService;
import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/send")
public class SendApi {

    @Resource
    private ZeusClient zeusClient;

    @GetMapping("/test")
    public void test() {

        // 获取脚本对象 用接口接收
        SendService sendService = zeusClient.getInterfaceByName("com.zeus.example.service.DingSmsService");
        sendService.send();

        // 获取GroovyObject对象
        GroovyObject groovyObject = zeusClient.getGroovyObjectByName("com.zeus.example.service.DingSmsService");
        groovyObject.invokeMethod("send", null);
        log.info("groovy object : [{}]", groovyObject);

        // 直接执行脚本对应的方法，得到返回值
        Object sendRes = zeusClient.execute("com.zeus.example.service.DingSmsService", "send", null);
        log.info("sendRes : [{}]", sendRes);

    }

}
