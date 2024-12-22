package org.example.BaseOptions.Impl;

import lombok.extern.slf4j.Slf4j;
import org.example.BaseOptions.Interface.CodeExecutor;
import org.example.BaseOptions.Interface.CodeExecutorFactory;
import org.example.pojo.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

// 重构后的主执行器
@Service
@Slf4j
public class UniversalCodeExecutor {
    private final CodeExecutorFactory executorFactory;
    
    @Autowired
    public UniversalCodeExecutor(CodeExecutorFactory executorFactory) {
        this.executorFactory = executorFactory;
    }
    
    public ExecutionResult execute(String language, String sourceCode, String input)
            throws ExecutionException {
        CodeExecutor executor = executorFactory.getExecutor(language);
        if (executor == null) {
            throw new IllegalArgumentException("Unsupported language: " + language);
        }
        
        return executor.execute(sourceCode, input);
    }
}