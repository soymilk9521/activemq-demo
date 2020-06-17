package com.lr.springboot.activemq.produce;

import javax.jms.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;


/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/06/16 22:24
 */
@Component
public class QueueProduce {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    public void produceMsg(){
        jmsMessagingTemplate.convertAndSend(queue, "******: " + UUID.randomUUID().toString().substring(0, 6));
    }

    // 间隔时间3秒钟定投
    @Scheduled(fixedDelay = 3000)
    public void produceMsgScheduled() {
        jmsMessagingTemplate.convertAndSend(queue, "******scheduled: " + UUID.randomUUID().toString().substring(0, 6));
        System.out.println("produceMsgScheduled send ok");
    }
}
