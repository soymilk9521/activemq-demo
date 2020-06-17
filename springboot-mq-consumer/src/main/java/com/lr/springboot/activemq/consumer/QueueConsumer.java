package com.lr.springboot.activemq.consumer;

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
 * @since 2020/06/17 6:44
 */
@Component
public class QueueConsumer {
    @JmsListener(destination = "${myqueue}")
    public void receive(TextMessage textMessage) throws JMSException {
        System.out.println("****** 消费者收到消息： " + textMessage.getText());
    }
}
