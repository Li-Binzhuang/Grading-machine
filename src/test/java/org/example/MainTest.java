package org.example;

import org.apache.rocketmq.client.exception.MQClientException;
import org.example.Service.RedissonService;
import org.example.config.KafkaConfig;
import org.example.config.RedisConfig;
import org.example.config.RedissonConfig;
import org.example.config.RocketMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RocketMQConfig.class,RedissonConfig.class, RedisConfig.class,RedissonService.class})
public class MainTest {
    private static final Logger logger = LoggerFactory.getLogger(MainTest.class);

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    RedissonService redissonService;

    @Test
    public void testRedissonService(){
        System.out.println(redissonService.getSingleValue("ab ab"));
    }

    @Autowired
    RocketMQConfig rocketMQConfig;
    @Test
    public void testMQ(){
        try {
            rocketMQConfig.rocketMQConsumer().start();
        } catch (MQClientException e) {
            logger.error(e.getMessage());
        }
    }

    @Autowired

    @Test
    public void testKafka(){

    }

}
