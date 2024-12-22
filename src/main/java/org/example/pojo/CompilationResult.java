package org.example.pojo;

import lombok.Data;


@Data
// 编译结果类
public class CompilationResult {
    private boolean success;
    private String compilerOutput;
    private long compilationTime;
}
