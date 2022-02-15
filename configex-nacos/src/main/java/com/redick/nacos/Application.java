package com.redick.nacos;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.redick.tp.annotation.EnableThreadPoolMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author liupenghui
 * @date 2022/1/19 5:47 下午
 */
@SpringBootApplication
@NacosPropertySource(dataId = "config_group", autoRefreshed = true)
@EnableThreadPoolMonitor
@RestController
public class Application {

    @NacosValue(value ="${flag:0}" ,autoRefreshed = true)
    private String flag;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping(value = "/nacos/getConfig")
    public String getFlag() {
        new Thread(() -> {
            try {
                task();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return this.flag;
    }

    public void task() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            Thread.sleep(100);
            Thread t = new Thread(() -> {
                System.out.println("i am task");
            });
            threadPoolExecutor.execute(t);
        }
    }
}
