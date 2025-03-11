package org.example.queryTestCaseTest;

import lombok.extern.slf4j.Slf4j;
import org.example.Repository.RepositoryOPS;
import org.example.config.ApplicationConfig;
import org.example.config.DataConfig;
import org.example.config.RedissonConfig;
import org.example.config.RocketMQConfig;
import org.example.Repository.pojo.TestCase;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataConfig.class, RedissonConfig.class, RocketMQConfig.class, ApplicationConfig.class})
public class Test {
    @Autowired
    RepositoryOPS repositoryOPS;
    @org.junit.Test
    public void test() throws Exception {
        List<TestCase> testCases = repositoryOPS.queryTestCaseByQuestionId(3);
        for (TestCase testCase : testCases) {
            log.info(testCase.toString());
        }
    }

}
