package org.example.LanguageSuport;

import org.example.LanguageSuport.Interface.LanguageSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

// 语言配置管理器
@Service
public class LanguageConfigurationManager {
    private final Map<String, LanguageSpec> languageSpecs = new ConcurrentHashMap<>();

    @Autowired
    public LanguageConfigurationManager(List<LanguageSpec> specs) {
        specs.forEach(spec -> languageSpecs.put(spec.getName().toLowerCase(), spec));
    }

    public LanguageSpec getLanguageSpec(String language) {
        return languageSpecs.get(language.toLowerCase());
    }

    public void registerLanguage(LanguageSpec spec) {
        languageSpecs.put(spec.getName().toLowerCase(), spec);
    }

    public Set<String> getSupportedLanguages() {
        return new HashSet<>(languageSpecs.keySet());
    }
}
