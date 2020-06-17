package com.lr.spring.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/06/17 7:01
 */
@Component
public class TopicConsumer {

    @JmsListener(destination = "${mytopic}")
    public void receive(TextMessage textMessage) throws JMSException {
        System.out.println("消费者受到订阅的主题： " + textMessage.getText());
    }
}
