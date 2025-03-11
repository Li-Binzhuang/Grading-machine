package org.example;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.example.Config.DataBaseConfig;
import org.example.Repository.RedissonService;
import org.example.Config.RedisConfig;
import org.example.Config.RedissonConfig;
import org.example.Config.RocketMQConfig;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;


@ComponentScan("org.example.Listener.*")
@Service
public class Main {
    public static void main(String[] args) throws Exception{
        ApplicationContext context = new AnnotationConfigApplicationContext(
                RocketMQConfig.class,
                RedissonConfig.class,
                RedisConfig.class,
                RedissonService.class
        );
        MQPushConsumer consumer = context.getBean(MQPushConsumer.class);
        consumer.start();
    }
}