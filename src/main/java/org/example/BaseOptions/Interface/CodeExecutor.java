package org.example.BaseOptions.Interface;

import org.example.pojo.ExecutionResult;

import java.util.concurrent.ExecutionException;

// 代码执行器接口
public interface CodeExecutor {
    ExecutionResult execute(String sourceCode, String input) throws ExecutionException;
}