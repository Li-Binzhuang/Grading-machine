package org.example.LanguageSuport.LanguageSetting;

import lombok.Builder;
import lombok.Data;

// 基础配置类
@Data
@Builder
public class ResourceLimits {
    private long maxExecutionTimeMs;    // 最大执行时间（毫秒）
    private long maxMemoryBytes;        // 最大内存（字节）
    private int maxProcessCount;        // 最大进程数
    private long maxOutputBytes;        // 最大输出大小
    private int maxFileSize;           // 最大文件大小（KB）
}