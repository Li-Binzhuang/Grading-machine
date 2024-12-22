package org.example.LanguageSuport.LanguageSpec;

import org.example.LanguageSuport.LanguageSetting.CompilerSettings;
import org.example.LanguageSuport.LanguageSetting.ResourceLimits;
import org.example.LanguageSuport.LanguageSetting.RuntimeSettings;
import org.example.LanguageSuport.LanguageSetting.SecuritySettings;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PythonLanguageSpec extends BaseLanguageSpec {
    public PythonLanguageSpec() {
        super("Python", "3.8", ".py", false);
    }

    @Override
    public CompilerSettings getCompilerSettings() {
        return CompilerSettings.builder().build(); // Python不需要编译
    }

    @Override
    public RuntimeSettings getRuntimeSettings() {
        return RuntimeSettings.builder()
            .executorPath("/usr/bin/python3")
            .executorFlags(Arrays.asList("-u"))
            .allowedLibraries(Arrays.asList("python3.8"))
            .build();
    }

    @Override
    public ResourceLimits getResourceLimits() {
        return null;
    }

    @Override
    public SecuritySettings getSecuritySettings() {
        return null;
    }

}