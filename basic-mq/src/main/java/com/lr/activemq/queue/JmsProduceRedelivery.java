package com.lr.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;
import java.util.UUID;

/**
 * <p>
 *  重发机制
 * </p>
 *
 * @author LR
 * @since 2020/06/21 16:55
 */
public class JmsProduceRedelivery {
    public static final String ACTIVEMQ_URL = "tcp://192.168.80.71:61616";
    private static final String QUEUE_NAME = "queue-redelivery";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer producer = session.createProducer(queue);
        for (int i = 0; i < 3; i++) {
            TextMessage message = session.createTextMessage("queue-redelivery --> " + UUID.randomUUID().toString());
            producer.send(message);
        }
        producer.close();
        session.close();
        connection.close();
        System.out.println("****** 消息发布到MQ完成 queue-redelivery");
    }
}
