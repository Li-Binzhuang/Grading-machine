package org.example;

import lombok.val;
import org.apache.rocketmq.client.exception.MQClientException;
import org.example.Config.DataBaseConfig;
import org.example.Service.RedissonService;
import org.example.Config.RedisConfig;
import org.example.Config.RedissonConfig;
import org.example.Config.RocketMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataBaseConfig.class})
public class MainTest {
    private static final Logger logger = LoggerFactory.getLogger(MainTest.class);

//    @Autowired
//    RedissonClient redissonClient;
//
//    @Autowired
//    RedissonService redissonService;

//    @Test
//    public void testRedissonService(){
//        System.out.println(redissonService.getSingleValue("ab ab"));
//    }

//    @Autowired
//    RocketMQConfig rocketMQConfig;
//    @Test
//    public void testMQ(){
//        try {
//            rocketMQConfig.rocketMQConsumer().start();
//        } catch (MQClientException e) {
//            logger.error(e.getMessage());
//        }
//    }
    @Resource(name = "connection")
    Connection conn;

    @Test
    public void testJdbc(){
        try {
            Object stmt = conn.createStatement();
            String sql = "select * from questions";
            ResultSet rs = ((java.sql.Statement) stmt).executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString("question_id") + " " + rs.getString("question_text") + " " + rs.getString("created_at") + " " + rs.getString("difficulty_level"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
