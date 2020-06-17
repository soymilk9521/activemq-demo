package com.lr.springboot.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/06/16 22:19
 */
@SpringBootApplication
@EnableScheduling // 开启定时投递
public class ActiveMQProduceMain7777 {

    public static void main(String[] args) {
        SpringApplication.run(ActiveMQProduceMain7777.class, args);
    }
}
