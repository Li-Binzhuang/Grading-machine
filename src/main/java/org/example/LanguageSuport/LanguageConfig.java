package org.example.LanguageSuport;

import lombok.Data;

import java.util.List;

@Data
// 语言配置类
public class LanguageConfig {
    private String name;                    // 语言名称
    private String fileExtension;           // 文件扩展名
    private String compiler;                // 编译器命令
    private List<String> compilerFlags;     // 编译参数
    private String executor;                // 执行器命令（解释型语言使用）
    private List<String> executorFlags;     // 执行参数
    private long maxExecutionTime;          // 最大执行时间（毫秒）
    private long maxMemory;                 // 最大内存使用（字节）
    private boolean needsCompilation;       // 是否需要编译
    private long CompileTimeout;          // 编译超时时间（毫秒）

    // Constructor
    public LanguageConfig(String name, String fileExtension, String compiler,
                          List<String> compilerFlags, String executor, List<String> executorFlags,
                          long maxExecutionTime, long maxMemory, boolean needsCompilation) {
        this.name = name;
        this.fileExtension = fileExtension;
        this.compiler = compiler;
        this.compilerFlags = compilerFlags;
        this.executor = executor;
        this.executorFlags = executorFlags;
        this.maxExecutionTime = maxExecutionTime;
        this.maxMemory = maxMemory;
        this.needsCompilation = needsCompilation;
    }

    public long getMaxExecutionTime() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMaxExecutionTime'");
    }
}
