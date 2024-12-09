package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
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
    private   String CONSUMER_GROUP_NAME;


    @Bean
    public DefaultMQPushConsumer rocketMQConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP_NAME);
        consumer.setNamesrvAddr(NAME_SERVER_ADDR);
        try {
            consumer.subscribe("Problem", "*"); // 订阅所有消息
            logger.info("消费者组已加入："+consumer.getConsumerGroup());
        } catch (MQClientException e) {
            throw new RuntimeException("Failed to subscribe topic.", e);
        }

        // 注册消息监听器
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (Message msg : msgs) {
                System.out.printf("Receive Message: %s %n", new String(msg.getBody()));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        return consumer;
    }
}
