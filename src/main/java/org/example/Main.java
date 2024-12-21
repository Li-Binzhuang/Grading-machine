package org.example;

import org.apache.rocketmq.client.exception.MQClientException;
import org.example.Service.RedissonService;
import org.example.config.RedisConfig;
import org.example.config.RedissonConfig;
import org.example.config.RocketMQConfig;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {RocketMQConfig.class, RedissonConfig.class, RedisConfig.class, RedissonService.class})
public class Main {
    public static void main(String[] args) throws MQClientException {
        RocketMQConfig rocketMQConfig = new RocketMQConfig();
        rocketMQConfig.rocketMQConsumer().start();
    }
}