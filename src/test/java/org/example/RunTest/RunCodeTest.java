package org.example.RunTest;

import lombok.extern.slf4j.Slf4j;
import org.example.Repository.pojo.TestCase;
import org.example.domain.VO.Result;
import org.example.domain.run.PythonRunCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
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
        Path codeFilePath = Files.createFile(Paths.get("Main.py"));//手动创建Main.py
        Files.write(codeFilePath, code.getBytes());
        Result<String> result= pythonRunCode.runCode(codeFilePath, testCases);
        log.info(result.getStatus()+result.getMessage()+result.getData());
    }
}
