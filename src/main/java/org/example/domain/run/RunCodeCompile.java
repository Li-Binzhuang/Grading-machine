package org.example.domain.run;

import org.example.Repository.pojo.TestCase;
import org.example.domain.VO.Result;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface RunCodeCompile {
    Result<String> runCode(Path afterCompileFilePath, List<TestCase> testCases);
}
