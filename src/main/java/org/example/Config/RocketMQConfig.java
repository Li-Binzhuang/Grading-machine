package org.example.Config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.example.listener.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Slf4j
@Configuration
@PropertySource("classpath:application.properties")
public class RocketMQConfig {
    private static final Logger logger = LoggerFactory.getLogger(RocketMQConfig.class);
    // RocketMQ服务地址（Name Server地址）
    @Value("${rocketmq.NAME_SERVER_ADDR}")
    private   String NAME_SERVER_ADDR;

    // RocketMQ消费组名称
    @Value("${rocketmq.CONSUMER_GROUP_NAME}")
    private  String CONSUMER_GROUP_NAME;

    // 创建一个消费者实例用于消费来自生产者的题目消息
    @Bean
    public DefaultMQPushConsumer rocketMQConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP_NAME);
        consumer.setNamesrvAddr(NAME_SERVER_ADDR);
        try {
            consumer.subscribe("Problem", "*"); // 订阅关于问题的消息
            logger.info("消费者组已加入："+consumer.getConsumerGroup());
        } catch (MQClientException e) {
            throw new RuntimeException("Failed to subscribe topic.", e);
        }
        // 注册消息监听器
        //只需要监听一个消息类型一个消息队列
        RocketMQListener listener = new RocketMQListener();
        consumer.registerMessageListener(listener);
        return consumer;
    }

    @Bean
    public DefaultMQProducer MQProducer(){
        DefaultMQProducer producer = new DefaultMQProducer("system");
        producer.setNamesrvAddr(NAME_SERVER_ADDR);
        try {
            producer.start();
            while (true){

                Thread.sleep(10000);//每隔10s发送一次消息
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return producer;
    }
}
