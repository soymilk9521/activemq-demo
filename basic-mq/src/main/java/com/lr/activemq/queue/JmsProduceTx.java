package com.lr.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/06/13 13:30
 */
public class JmsProduceTx {

    public static final String ACTIVEMQ_URL = "tcp://192.168.80.71:61616";
    private static final String QUEUE_NAME = "queue_tx";

    public static void main(String[] args) throws JMSException {

        // 1. 创建连接工场, 按照给第的URL地址，采用默认的用户名和密码
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2. 通过连接工场，获得连接connection并启动访问
        Connection connection = factory.createConnection();
        connection.start();
        // 3. 创建会话session
        // 两个参数，第一个叫事物，第二个叫签收
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        // 4. 创建目的地（具体是队列还是主题topic）
        Queue queue = session.createQueue(QUEUE_NAME);
        // 5. 创建消息的生产者
        MessageProducer producer = session.createProducer(queue);
        // 持久化模式
        // producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        // 使用生产者发送消息
        for (int i = 0; i < 3; i++) {
            // 7. 创建消息
            TextMessage textMessage = session.createTextMessage("tx msg --> " + i);
            // 8. 通过producer发送消息
            producer.send(textMessage);
        }
        // 事务提交
        session.commit();
        // 9. 关闭资源
        producer.close();
        session.close();
        connection.close();

        System.out.println("****** tx消息发布到MQ完成");

        // try catch rollback
    }
}
