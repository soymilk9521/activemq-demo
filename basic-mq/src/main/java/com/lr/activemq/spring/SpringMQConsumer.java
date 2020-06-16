package com.lr.activemq.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/06/16 8:20
 */
@Service
public class SpringMQConsumer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQConsumer consumer = (SpringMQConsumer)ctx.getBean("springMQConsumer");
        while (consumer.jmsTemplate.receive() != null) {
            String retVal = (String) consumer.jmsTemplate.receiveAndConvert();
            System.out.println("***********消费者接收消息： " + retVal);
        }
    }
}
