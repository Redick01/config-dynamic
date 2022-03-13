package com.redick.consul.controller;

import com.redick.consul.configbean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.endpoint.event.RefreshEventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author liupenghui
 * @date 2022/3/9 12:03
 */
@RestController
@RefreshScope
public class TestController {

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private Student student;

    @Value("${name}")
    private String name;

    @GetMapping("test1")
    public String test1() {
        return student.toString();
    }

    @GetMapping("test2")
    public String test2() {
        return name;
    }

    @GetMapping("test3")
    public String test3() {
        return threadPoolExecutor.getCorePoolSize() + ":" + threadPoolExecutor.getMaximumPoolSize();
    }
}
