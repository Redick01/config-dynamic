package com.redick.nacos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author liupenghui
 * @date 2022/2/11 2:03 下午
 */
@Slf4j
public class BeanInterface implements InitializingBean, DisposableBean, ApplicationListener, BeanPostProcessor {

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("bean初始化");
    }

    @Override
    public void destroy() throws Exception {
        log.info("bean销毁");
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            log.info("容器刷新完毕");
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof  ThreadPoolExecutor) {
            log.info("bean初始化前：{}", beanName);
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ThreadPoolExecutor) {
            log.info("bean初始化后：{}", beanName);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
