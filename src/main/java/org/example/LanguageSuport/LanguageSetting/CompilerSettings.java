
package org.example.LanguageSuport.LanguageSetting;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class CompilerSettings {
    private String compilerPath;        // 编译器路径
    private List<String> compilerFlags; // 编译参数
    private long compileTimeoutMs;      // 编译超时时间
    private String compilerVersion;     // 编译器版本
    private Map<String, String> environmentVariables; // 环境变量
}