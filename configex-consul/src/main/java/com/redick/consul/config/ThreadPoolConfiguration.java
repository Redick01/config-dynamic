package com.redick.consul.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liupenghui
 * @date 2022/3/10 15:46
 */
@Configuration
public class ThreadPoolConfiguration {

    @Value("${corePoolSize}")
    private Integer corePoolSize;

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(corePoolSize, corePoolSize, 10000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
    }
}
