package com.lr.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;
import java.io.IOException;

/**
 * <p>
 *  重发机制 消费者
 * </p>
 *
 * @author LR
 * @since 2020/06/13 14:13
 */
public class JmsConsumerRedelivery {
    public static final String ACTIVEMQ_URL = "tcp://192.168.80.71:61616";
    private static final String QUEUE_NAME = "queue-redelivery";
    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("我是1号消费者");
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 重发策略
        RedeliveryPolicy queuePolicy = new RedeliveryPolicy();
        queuePolicy.setRedeliveryDelay(1000);
        queuePolicy.setMaximumRedeliveries(3);
        factory.setRedeliveryPolicy(queuePolicy);

        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer consumer = session.createConsumer(queue);

        // 消息监听方式
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if (message != null && message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("******消费者接收消息 ： " + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }
}
