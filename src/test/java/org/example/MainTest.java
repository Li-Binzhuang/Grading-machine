package org.example;

import org.example.Service.RedissonService;
import org.example.config.RedisConfig;
import org.example.config.RedissonConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RedissonConfig.class, RedisConfig.class,RedissonService.class})
public class MainTest {
    @Autowired
    RedissonClient redissonClient;

    @Autowired
    RedissonService redissonService;
    @Test
    public void testRedisson(){
        RMap<String, String> map = redissonClient.getMap("myMap");
        map.put("hh","nihao");
    }

    @Test
    public void testRedissonService(){
        redissonService.setSingleValue("ab ab","haha");
        System.out.println(redissonService.getSingleValue("ab ab"));
    }

}
