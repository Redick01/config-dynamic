package com.redick.apollo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liupenghui
 * @date 2022/1/21 3:41 下午
 */
@RestController
public class AppConfigController {

    @Value("${flag}")
    private String flag;


    @GetMapping(value = "/apollo/getConfig")
    public String getConfig() {
        return flag;
    }
}
