package org.example.pojo;

import lombok.Data;

@Data
// 扩展执行结果类
public class ExecutionResult {
    private String stdout;
    private String stderr;
    private int exitCode;
    private ResourceUsage resourceUsage;
    private CompilationResult compilationResult;
}