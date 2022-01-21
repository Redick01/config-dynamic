package com.redick.apollo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liupenghui
 * @date 2022/1/21 2:55 下午
 */
@SpringBootApplication
@EnableApolloConfig
public class ApplicationApo {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationApo.class, args);
    }
}
