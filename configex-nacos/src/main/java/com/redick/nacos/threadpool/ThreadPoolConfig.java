package com.redick.nacos.threadpool;

import com.redick.nacos.service.BeanInterface;
import com.redick.tp.dynamic.ThreadPoolBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author liupenghui
 * @date 2022/2/7 6:20 下午
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return ThreadPoolBuilder.builder().threadFactoryPrefix("example").build();
    }

    @Bean
    public BeanInterface beanInterface(){
        return new BeanInterface();
    }
}
