package org.example;

import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.example.Repository.RedissonService;
import org.example.config.RedissonConfig;
import org.example.config.RocketMQConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


@ComponentScan("org.example.listener.*")
@Service
public class Main {
    public static void main(String[] args) throws Exception{
        ApplicationContext context = new AnnotationConfigApplicationContext(
                RocketMQConfig.class,
                RedissonConfig.class,
                RedissonService.class
        );
        MQPushConsumer consumer = context.getBean(MQPushConsumer.class);
        consumer.start();
    }
}