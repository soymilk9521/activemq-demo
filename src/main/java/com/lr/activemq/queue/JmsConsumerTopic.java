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
public class JmsConsumerTopic {
    public static final String ACTIVEMQ_URL = "tcp://192.168.80.71:61616";
    private static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("我是3号消费者");
        // 1. 创建连接工场, 按照给第的URL地址，采用默认的用户名和密码
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2. 通过连接工场，获得连接connection并启动访问
        Connection connection = factory.createConnection();
        connection.start();
        // 3. 创建会话session
        // 两个参数，第一个叫事物，第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4. 创建目的地（具体是队列还是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);
        // 5. 创建消费者
        MessageConsumer consumer = session.createConsumer(topic);
        // 消息监听方式
        consumer.setMessageListener(message -> {
            if (message != null && message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("******TOPIC_NAME消费者接收消息： " + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }
}
