package com.redick.apollo.task;

import cn.hippo4j.starter.wrapper.DynamicThreadPoolWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author liupenghui
 * @date 2022/1/25 4:36 下午
 */
@Component
public class Task {

    @Resource
    private ThreadPoolExecutor dynamicThreadPoolExecutor;

    @PostConstruct
    public void task() throws Exception {
        Thread.sleep(5000);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            Thread t = new Thread(() -> {
                System.out.println("hippo4j test : " + UUID.randomUUID().toString());
            });
            dynamicThreadPoolExecutor.execute(t);
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
