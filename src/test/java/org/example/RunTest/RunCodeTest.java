package org.example.RunTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.example.Repository.pojo.TestCase;
import org.example.config.ApplicationConfig;
import org.example.config.DataConfig;
import org.example.config.RedissonConfig;
import org.example.config.RocketMQConfig;
import org.example.domain.VO.Result;
import org.example.domain.run.PythonRunCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataConfig.class, RedissonConfig.class, RocketMQConfig.class, ApplicationConfig.class})
public class RunCodeTest {
    @Test
    public void test() throws IOException, InterruptedException {
        PythonRunCode pythonRunCode = new PythonRunCode();
        String code = "a=input()\n" +
                "print(a)";
        TestCase testCase = new TestCase();
        testCase.setInput("1");
        testCase.setExpectedOutput("1");
        List<TestCase> testCases=new ArrayList<>();
        testCases.add(testCase);
        Path codeFilePath = Paths.get("Main.py");
        // 如果文件不存在，创建新文件；如果存在，覆盖文件内容
        Files.write(codeFilePath, code.getBytes());
        Result<String> result= pythonRunCode.runCode(codeFilePath, testCases);
        log.info(result.getStatus()+result.getMessage()+result.getData());
    }

}
