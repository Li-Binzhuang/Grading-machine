package org.example.LanguageSuport.LanguageSpec;

import org.example.LanguageSuport.LanguageSetting.CompilerSettings;
import org.example.LanguageSuport.LanguageSetting.ResourceLimits;
import org.example.LanguageSuport.LanguageSetting.RuntimeSettings;
import org.example.LanguageSuport.LanguageSetting.SecuritySettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

// 具体语言实现示例
@Component
public class CppLanguageSpec extends BaseLanguageSpec {
    @Value("${languages.cpp.version}")
    private String cppVersion;

    public CppLanguageSpec() {
        super("C++", "17", ".cpp", true);
    }

    @Override
    public CompilerSettings getCompilerSettings() {
        return CompilerSettings.builder()
            .compilerPath("/usr/bin/g++")
            .compilerFlags(Arrays.asList("-O2", "-Wall", "-std=c++17"))
            .compileTimeoutMs(10000L)
            .compilerVersion(cppVersion)
            .environmentVariables(Collections.emptyMap())
            .build();
    }

    @Override
    public RuntimeSettings getRuntimeSettings() {
        return RuntimeSettings.builder()
            .executorPath(null)
            .executorFlags(Collections.emptyList())
            .environmentVariables(Collections.emptyMap())
            .allowedLibraries(Arrays.asList("libc.so", "libstdc++.so"))
            .build();
    }

    @Override
    public ResourceLimits getResourceLimits() {
        return ResourceLimits.builder()
            .maxExecutionTimeMs(5000L)
            .maxMemoryBytes(256 * 1024 * 1024L)
            .maxProcessCount(1)
            .maxOutputBytes(10 * 1024 * 1024L)
            .maxFileSize(5 * 1024)
            .build();
    }

    @Override
    public SecuritySettings getSecuritySettings() {
        return SecuritySettings.builder()
            .allowedSystemCalls(Arrays.asList("read", "write", "exit"))
            .allowedPaths(Collections.emptyList())
            .networkAccess(false)
            .fileSystemAccess(false)
            .capabilities(Collections.emptyList())
            .build();
    }
}