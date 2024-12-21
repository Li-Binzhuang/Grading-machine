package org.example;

import org.apache.rocketmq.client.exception.MQClientException;
import org.example.Service.RedissonService;
import org.example.config.RedisConfig;
import org.example.config.RedissonConfig;
import org.example.config.RocketMQConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@ContextConfiguration(classes = {RocketMQConfig.class, RedissonConfig.class, RedisConfig.class, RedissonService.class})
public class Main {

    public static void main(String[] args) throws MQClientException {
         ApplicationContext context = new AnnotationConfigApplicationContext(
                 RedisConfig.class, RedissonConfig.class,
                 RocketMQConfig.class, RedissonService.class
         );
        // 你可以从context中获取需要的bean并开始工作
        RocketMQConfig rocketMQConfig = context.getBean(RocketMQConfig.class);
        rocketMQConfig.rocketMQConsumer().start();
    }
}