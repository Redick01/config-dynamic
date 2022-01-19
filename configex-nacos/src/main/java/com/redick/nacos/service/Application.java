package com.redick.nacos.service;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liupenghui
 * @date 2022/1/19 5:47 下午
 */
@SpringBootApplication
@NacosPropertySource(dataId = "config_group", autoRefreshed = true)
@RestController
public class Application {

    @NacosValue(value ="${flag:0}" ,autoRefreshed = true)
    private String flag;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping(value = "/nacos/getConfig")
    public String getFlag() {
        return this.flag;
    }
}
