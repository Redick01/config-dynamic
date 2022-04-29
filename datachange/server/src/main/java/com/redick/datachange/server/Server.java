package com.redick.datachange.server;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.TtlMDCAdapter;
import org.slf4j.spi.MDCAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * @author liupenghui
 * @date 2022/2/14 2:08 下午
 */
@SpringBootApplication
@Slf4j
@RestController
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

    @PostConstruct
    public void init() {
        MDCAdapter mdcAdapter = TtlMDCAdapter.getInstance();
        log.info("MDCAdapter实例：{}", mdcAdapter);
    }

    @GetMapping("/MDCAdapterTest")
    public String getTest() {
        MDC.put("traceId", UUID.randomUUID().toString());
        log.info("开始测试");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                log.info("当前线程：{}", Thread.currentThread().getName().toString());
            }).start();
        }
        return "success";
    }
}
