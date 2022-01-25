package com.redick.apollo.config;

import cn.hippo4j.starter.core.DynamicThreadPool;
import cn.hippo4j.starter.toolkit.thread.ThreadPoolBuilder;
import cn.hippo4j.starter.wrapper.DynamicThreadPoolWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;

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
                .dynamicPool()
                /**
                 * 测试线程任务装饰器.
                 * 如果需要查看详情, 跳转 {@link TaskDecoratorTest}
                 */
                .waitForTasksToCompleteOnShutdown(true)
                .awaitTerminationMillis(5000)
                .build();
    }
}
