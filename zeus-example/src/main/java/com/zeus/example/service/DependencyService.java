package com.zeus.example.service;

import com.zeus.core.ZeusClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DependencyService {

    @Resource
    private ZeusClient zeusClient;

    public ZeusClient getZeusClient() {
        return zeusClient;
    }
}
