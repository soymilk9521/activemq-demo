package com.lr.activemq.embed;

import org.apache.activemq.broker.BrokerService;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/06/15 22:57
 */
public class EmbedBroker {
    public static void main(String[] args) throws Exception {
        // ActiveMQ也支持在vm中通信基于嵌入式的broker
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}
