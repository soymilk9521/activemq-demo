package com.lr.springboot.activemq;

import com.lr.springboot.activemq.produce.QueueProduce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/06/16 22:32
 */
@SpringBootTest(classes = ActiveMQProduceMain7777.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestActiveMQ {

    @Resource
    private QueueProduce produce;

    @Test
    public void testSend() {
        produce.produceMsg();
    }
}
