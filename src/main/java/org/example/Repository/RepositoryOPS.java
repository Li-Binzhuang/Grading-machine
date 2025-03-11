package org.example.Repository;

import lombok.extern.slf4j.Slf4j;
import org.example.VO.RedisKey;
import org.example.config.DataConfig;
import org.example.domain.queryTestCase.queryTestCase;
import org.example.pojo.TestCase;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RepositoryOPS implements queryTestCase {

    RedissonService redissonService;
    DataConfig dataConfig;

    public RepositoryOPS(DataConfig dataConfig, RedissonService redissonService){
        this.dataConfig=dataConfig;
        this.redissonService=redissonService;
    }

    //以题目唯一id作为查询问题的key
    @Override
    public List<TestCase> queryTestCaseById(Integer id) throws Exception {
        //构造redisKey
        String key= RedisKey.TEST_CASE_KEY + id;
        List<TestCase> list = redissonService.getList(key);//从缓存获取测试用例
        if(list!=null){
            return list;
        }
        List<TestCase> cases = new ArrayList<>();
        String sql = "SELECT * FROM test_cases WHERE question_id = 3";
        try (//从数据库获取用例
                Connection conn = DriverManager.getConnection(
                dataConfig.url, dataConfig.user, dataConfig.password);
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
            //写回缓存
            redissonService.setList(key,cases);
            return cases;
        } catch (SQLException e) {
            log.error("Database  error: {}", e.getMessage());
            throw new Exception("无法获取测试用例");
        }
    }
}
