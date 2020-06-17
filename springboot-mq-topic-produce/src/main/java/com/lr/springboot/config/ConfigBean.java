package com.lr.springboot.config;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/06/17 6:58
 */
@Component
public class ConfigBean {

    @Value("${mytopic}")
    private String myTopic;

    @Bean
    public Topic topic() {
        return new ActiveMQTopic(myTopic);
    }
}
