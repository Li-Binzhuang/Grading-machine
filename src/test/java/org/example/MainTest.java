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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.Repository.pojo.TestCase;


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
    public void getTestCases() throws Exception {
        List<TestCase> cases = new ArrayList<>();
        String sql = "SELECT * FROM test_cases WHERE question_id = 3";

        try (Connection conn = DriverManager.getConnection(
                dataBaseConfig.url, dataBaseConfig.user, dataBaseConfig.password);
             PreparedStatement stmt = conn.prepareStatement(sql);  // 改用预处理语句
             ResultSet rs = stmt.executeQuery())  {

            while (rs.next())  {
                TestCase testCase = new TestCase();
                testCase.setTestCaseId(rs.getInt("test_case_id"));
                testCase.setInput(rs.getString("input"));
                testCase.setExpectedOutput(rs.getString("expected_output"));
                testCase.setQuestionId(rs.getLong("question_id"));  // bigint用getLong
                log.info(testCase.toString());
                cases.add(testCase);
            }
        } catch (SQLException e) {
            // 建议添加错误处理逻辑
            log.error("Database  error: {}", e.getMessage());
            throw new Exception("ddd");
        }
        return;
    }
}
