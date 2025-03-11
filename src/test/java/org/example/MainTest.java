package org.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.example.Repository.RedissonService;
import org.example.config.DataConfig;
import org.example.config.RedissonConfig;
import org.example.config.RocketMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataConfig.class, RedissonConfig.class, RocketMQConfig.class})
public class MainTest {
    @Autowired
    RedissonService redissonService;
//
    @Test
    public void testRedissonService(){
        redissonService.setSingleValue("ab ab",String.valueOf(100));
        Optional<String> abAb = redissonService.getSingleValue("ab ab");
        log.info(abAb.toString());
    }

    @Autowired
    RocketMQConfig rocketMQConfig;
    @Test
    public void testMQ(){
        try {
            rocketMQConfig.rocketMQConsumer().start();
        } catch (MQClientException e) {
            log.error(e.getMessage());
        }
    }

    @Autowired
    DataConfig dataBaseConfig;

    @Test
    public void testJdbc() {
        try (
                Connection localConn = DriverManager.getConnection(
                        dataBaseConfig.url, dataBaseConfig.user, dataBaseConfig.password);
                java.sql.Statement stmt = localConn.createStatement()) {
                String sql = "select * from questions";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        log.info(rs.toString());
                    }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
