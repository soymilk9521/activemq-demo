package com.lr.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;
import java.util.UUID;

/**
 * <p>
 *  延迟发送和定时投送
 * </p>
 *
 * @author LR
 * @since 2020/06/13 13:30
 */
public class JmsProduceDelayAndSchedule {
    public static final String ACTIVEMQ_URL = "tcp://192.168.80.71:61616";
    private static final String QUEUE_NAME = "queue-delay";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer producer = session.createProducer(queue);
        for (int i = 0; i < 3; i++) {
            TextMessage message = session.createTextMessage("queue-delay --> " + UUID.randomUUID().toString());
            long delay = 3 * 1000;
            long period = 4 * 1000;
            int repeat = 5;
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, period);
            message.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, repeat);
            producer.send(message);
        }
        producer.close();
        session.close();
        connection.close();
        System.out.println("****** 消息发布到MQ完成 queue-delay");
    }
}
