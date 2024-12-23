package org.example.BaseOptions.Interface;

import org.example.pojo.RunResult;
import org.example.ExceptionHandle.RunnerException;

import java.nio.file.Path;

// 代码运行器接口
public interface CodeRunner {
    RunResult run(Path executablePath, String input) throws RunnerException;
}