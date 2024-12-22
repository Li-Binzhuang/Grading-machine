package org.example.LanguageSuport.LanguageSetting;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class RuntimeSettings {
    private String executorPath;        // 执行器路径
    private List<String> executorFlags; // 执行参数
    private Map<String, String> environmentVariables; // 运行时环境变量
    private List<String> allowedLibraries; // 允许加载的库
}