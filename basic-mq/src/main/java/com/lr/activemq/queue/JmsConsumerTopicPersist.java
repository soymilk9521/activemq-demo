package com.lr.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/06/13 17:02
 */
public class JmsConsumerTopicPersist {
    public static final String ACTIVEMQ_URL = "tcp://192.168.80.71:61616";
    private static final String TOPIC_NAME = "topic_jdbc_persist";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("*****z4");
        // 1. 创建连接工场, 按照给第的URL地址，采用默认的用户名和密码
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2. 通过连接工场，获得连接connection并启动访问
        Connection connection = factory.createConnection();
        connection.setClientID("z4");
        connection.start();
        // 3. 创建会话session
        // 两个参数，第一个叫事物，第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4. 创建目的地（具体是队列还是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "topic_jdbc_persist remark...");

        Message message = topicSubscriber.receive();
        while (message != null) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("******收到的持久化topic_jdbc_persist" + textMessage.getText());
            message = topicSubscriber.receive(3000L);
        }
        session.close();
        connection.close();

        /**
         * 1. 一定要线运行一次消费者，等于向MQ注册，雷速与我订阅了这个主题
         * 2. 然后运行生产者发送信息
         * 3. 无论消费者是否在心都会接收到，不在线的话，下次连接的时候会把没有收到的消息接收下来
         */
    }
}
