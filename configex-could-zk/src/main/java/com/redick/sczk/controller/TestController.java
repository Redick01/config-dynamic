package com.redick.sczk.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liupenghui
 * @date 2022/3/18 11:34
 */
@RestController
@RefreshScope
public class TestController {


    @Value("${name}")
    private String name;

    @GetMapping(value = "/sc-zk/getConfig")
    public String getConfig() {
        return name;
    }
}
