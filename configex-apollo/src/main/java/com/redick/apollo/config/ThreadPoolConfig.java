package com.redick.apollo.config;

import cn.hippo4j.starter.core.DynamicThreadPool;
import cn.hippo4j.starter.toolkit.thread.ResizableCapacityLinkedBlockIngQueue;
import cn.hippo4j.starter.toolkit.thread.ThreadPoolBuilder;
import cn.hippo4j.starter.wrapper.DynamicThreadPoolWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liupenghui
 * @date 2022/1/25 4:16 下午
 */
@Configuration
public class ThreadPoolConfig {

/*    @Bean
    public DynamicThreadPoolWrapper threadPoolWrapper() {
        ThreadPoolExecutor executor = ThreadPoolBuilder.builder()
                .dynamicPool()
                .threadFactory("apollo-thread-pool")
                .build();
        return new DynamicThreadPoolWrapper("apollo-thread-pool", executor);
    }*/

    @Bean
    @DynamicThreadPool
    public ThreadPoolExecutor dynamicThreadPoolExecutor() {
        return ThreadPoolBuilder.builder()
                .threadFactory("apollo-thread-pool")
                .threadPoolId("apollo-thread-pool")
                .corePoolSize(5)
                .maxPoolNum(10)
                .workQueue(new ResizableCapacityLinkedBlockIngQueue(1024))
                .rejected(new ThreadPoolExecutor.AbortPolicy())
                .keepAliveTime(6000, TimeUnit.MILLISECONDS)
                .dynamicPool()
                .build();
    }
}
