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
 * @since 2020/06/13 14:13
 */
public class JmsConsumer {
//    public static final String ACTIVEMQ_URL = "tcp://192.168.80.71:61616"; // tcp
//    public static final String ACTIVEMQ_URL = "tcp://localhost:61616"; // broker
//    public static final String ACTIVEMQ_URL = "tcp://192.168.80.71:61618"; // nio
//    public static final String ACTIVEMQ_URL = "tcp://192.168.80.71:61608"; // auto+nio
    public static final String ACTIVEMQ_URL = "tcp://192.168.80.71:61616"; // jdbc
    private static final String QUEUE_NAME = "jdbc01";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("我是1号消费者");
        // 1. 创建连接工场, 按照给第的URL地址，采用默认的用户名和密码
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2. 通过连接工场，获得连接connection并启动访问
        Connection connection = factory.createConnection();
        connection.start();
        // 3. 创建会话session
        // 两个参数，第一个叫事物，第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4. 创建目的地（具体是队列还是主题topic）
        Queue queue = session.createQueue(QUEUE_NAME);
        // 5. 创建消费者
        MessageConsumer consumer = session.createConsumer(queue);
        /* 同步阻塞方式（receive()）
          while (true) {
            TextMessage message = (TextMessage)consumer.receive();
            if (message != null) {
                System.out.println("******消费者接收到消息： " + message.getText());
            }else {
                break;
            }
        }
        consumer.close();
        session.close();
        connection.close();*/

        // 消息监听方式
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if (message != null && message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("******消费者接收消息 jdbc： " + textMessage.getText());
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
        /**
         * 多个消费者时，采用平均分配的方式，类似负载均衡的轮询方式。
         */
    }
}
