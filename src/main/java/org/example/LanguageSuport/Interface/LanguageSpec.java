package org.example.LanguageSuport.Interface;


import org.example.LanguageSuport.LanguageSetting.CompilerSettings;
import org.example.LanguageSuport.LanguageSetting.ResourceLimits;
import org.example.LanguageSuport.LanguageSetting.RuntimeSettings;
import org.example.LanguageSuport.LanguageSetting.SecuritySettings;

// 语言配置接口
public interface LanguageSpec {
    String getName();
    String getVersion();
    String getFileExtension();
    boolean needsCompilation();
    CompilerSettings getCompilerSettings();
    RuntimeSettings getRuntimeSettings();
    ResourceLimits getResourceLimits();
    SecuritySettings getSecuritySettings();
}
