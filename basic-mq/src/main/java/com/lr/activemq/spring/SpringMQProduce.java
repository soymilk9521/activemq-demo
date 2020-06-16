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
public class SpringMQProduce {
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQProduce produce = (SpringMQProduce)ctx.getBean("springMQProduce");
        produce.jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("****** spring 和 ActiveMQ整合Listener......");
                return textMessage;
            }
        });
        produce.jmsTemplate.send(session -> session.createTextMessage("****** spring 和 ActiveMQ整合Listener......"));
        System.out.println("*********** 生产者发送消息完成！");
    }
}
