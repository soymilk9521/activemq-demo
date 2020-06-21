package com.lr.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.util.UUID;

/**
 * <p>
 *  异步发送
 * </p>
 *
 * @author LR
 * @since 2020/06/13 13:30
 */
public class JmsProduceAsyncSend {
    public static final String ACTIVEMQ_URL = "tcp://192.168.80.71:61616";
    private static final String QUEUE_NAME = "queue-asyncSend";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        factory.setUseAsyncSend(true); // 开启异步发送
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        ActiveMQMessageProducer producer = (ActiveMQMessageProducer)session.createProducer(queue);
        for (int i = 0; i < 3; i++) {
            TextMessage textMessage = session.createTextMessage("asyncSend --> " + i);
            textMessage.setJMSMessageID(UUID.randomUUID().toString() + "--- order");
            String msgId = textMessage.getJMSMessageID();
            producer.send(textMessage, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    System.out.println(msgId + "has been OK!!") ;
                }

                @Override
                public void onException(JMSException exception) {
                    System.out.println(msgId + "has been NG!!") ;
                }
            });
        }
        producer.close();
        session.close();
        connection.close();

        System.out.println("****** 消息发布到MQ完成 asyncSend");
    }
}
