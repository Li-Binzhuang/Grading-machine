package org.example;

import org.apache.rocketmq.client.exception.MQClientException;
import org.example.Service.RedissonService;
import org.example.Config.RedisConfig;
import org.example.Config.RedissonConfig;
import org.example.Config.RocketMQConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {RocketMQConfig.class, RedissonConfig.class, RedisConfig.class, RedissonService.class})
public class Main {

    public static void main(String[] args) throws MQClientException {
         ApplicationContext context = new AnnotationConfigApplicationContext(
                 RedisConfig.class, RedissonConfig.class,
                 RocketMQConfig.class, RedissonService.class
         );
        RocketMQConfig rocketMQConfig = context.getBean(RocketMQConfig.class);
        rocketMQConfig.rocketMQConsumer().start();
    }
}