package com.redick.nacos.threadpool;

import com.dtp.common.em.QueueTypeEnum;
import com.dtp.common.em.RejectedTypeEnum;
import com.dtp.core.support.ThreadPoolCreator;
import com.dtp.core.thread.DtpExecutor;
import com.redick.nacos.service.BeanInterface;
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
    public DtpExecutor dtpExecutor() {

        return ThreadPoolCreator.createDynamicFast("dynamic-tp-test-1");
    }

//    @Bean
//    public BeanInterface beanInterface(){
//        return new BeanInterface();
//    }
}
