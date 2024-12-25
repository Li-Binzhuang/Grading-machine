package org.example.LanguageSuport;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.LanguageSuport.Interface.LanguageSpec;
import org.example.LanguageSuport.LanguageSetting.CompilerSettings;
import org.example.LanguageSuport.LanguageSetting.ResourceLimits;
import org.example.LanguageSuport.LanguageSetting.RuntimeSettings;
import org.example.LanguageSuport.LanguageSetting.SecuritySettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class LanguageConfigurationLoader {
    private final LanguageConfigurationManager configManager;
    
    @Value("${code.execution.config.path}")
    private String configPath;
    
    private final ObjectMapper objectMapper = new ObjectMapper()
        .enable(JsonParser.Feature.ALLOW_COMMENTS)
        .enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    //todo 需要去理解这个加载类怎么加载json并配置
    @PostConstruct
    public void loadConfigurations() throws IOException {
        File configFile = new File(configPath);
        if (!configFile.exists()) {
            log.warn("Configuration file not found at: {}", configPath);
            return;
        }

        try {
            JsonNode config = objectMapper.readTree(configFile);
            JsonNode languagesNode = config.get("languages");
            
            if (languagesNode == null || !languagesNode.isObject()) {
                throw new Exception("Invalid configuration format: 'languages' section is missing or invalid");
            }

            Iterator<Map.Entry<String, JsonNode>> languages = languagesNode.fields();
            while (languages.hasNext()) {
                Map.Entry<String, JsonNode> entry = languages.next();
                String languageName = entry.getKey();
                JsonNode languageConfig = entry.getValue();
                
                try {
                    LanguageSpec languageSpec = createLanguageSpec(languageName, languageConfig);
                    configManager.registerLanguage(languageSpec);
                    log.info("Successfully loaded configuration for language: {}", languageName);
                } catch (Exception e) {
                    log.error("Failed to load configuration for language: {}", languageName, e);
                }
            }
        } catch (IOException e) {
            log.error("Failed to load language configurations", e);
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private LanguageSpec createLanguageSpec(String languageName, JsonNode config) {
        return new DynamicLanguageSpec(
            languageName,
            parseVersion(config),
            parseFileExtension(config),
            parseNeedsCompilation(config),
            parseCompilerSettings(config),
            parseRuntimeSettings(config),
            parseResourceLimits(config),
            parseSecuritySettings(config)
        );
    }

    private String parseVersion(JsonNode config) {
        return getStringValue(config, "version", "latest");
    }

    private String parseFileExtension(JsonNode config) {
        return getStringValue(config, "fileExtension", "." + config.path("name").asText().toLowerCase());
    }

    private boolean parseNeedsCompilation(JsonNode config) {
        return config.path("compiler").path("path").isTextual();
    }

    private CompilerSettings parseCompilerSettings(JsonNode config) {
        JsonNode compilerNode = config.path("compiler");
        if (compilerNode.isMissingNode()) {
            return CompilerSettings.builder().build();
        }

        return CompilerSettings.builder()
            .compilerPath(getStringValue(compilerNode, "path", ""))
            .compilerFlags(parseStringList(compilerNode, "flags"))
            .compileTimeoutMs(getLongValue(compilerNode, "timeout", 10000L))
            .compilerVersion(getStringValue(compilerNode, "version", ""))
            .environmentVariables(parseEnvironmentVariables(compilerNode.path("env")))
            .build();
    }

    private RuntimeSettings parseRuntimeSettings(JsonNode config) {
        JsonNode runtimeNode = config.path("runtime");
        if (runtimeNode.isMissingNode()) {
            return RuntimeSettings.builder().build();
        }

        return RuntimeSettings.builder()
            .executorPath(getStringValue(runtimeNode, "path", ""))
            .executorFlags(parseStringList(runtimeNode, "flags"))
            .environmentVariables(parseEnvironmentVariables(runtimeNode.path("env")))
            .allowedLibraries(parseStringList(runtimeNode, "allowedLibraries"))
            .build();
    }

    private ResourceLimits parseResourceLimits(JsonNode config) {
        JsonNode resourcesNode = config.path("resources");
        if (resourcesNode.isMissingNode()) {
            return getDefaultResourceLimits();
        }

        return ResourceLimits.builder()
            .maxExecutionTimeMs(getLongValue(resourcesNode, "maxExecutionTime", 5000L))
            .maxMemoryBytes(getLongValue(resourcesNode, "maxMemory", 256 * 1024 * 1024L))
            .maxProcessCount(getIntValue(resourcesNode, "maxProcesses", 1))
            .maxOutputBytes(getLongValue(resourcesNode, "maxOutput", 10 * 1024 * 1024L))
            .maxFileSize(getIntValue(resourcesNode, "maxFileSize", 5 * 1024))
            .build();
    }

    private SecuritySettings parseSecuritySettings(JsonNode config) {
        JsonNode securityNode = config.path("security");
        if (securityNode.isMissingNode()) {
            return getDefaultSecuritySettings();
        }

        return SecuritySettings.builder()
            .allowedSystemCalls(parseStringList(securityNode, "allowedSystemCalls"))
            .allowedPaths(parseStringList(securityNode, "allowedPaths"))
            .networkAccess(getBooleanValue(securityNode, "networkAccess", false))
            .fileSystemAccess(getBooleanValue(securityNode, "fileSystemAccess", false))
            .capabilities(parseStringList(securityNode, "capabilities"))
            .build();
    }

    // 工具方法
    private List<String> parseStringList(JsonNode node, String fieldName) {
        JsonNode arrayNode = node.path(fieldName);
        if (!arrayNode.isArray()) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        arrayNode.forEach(item -> result.add(item.asText()));
        return result;
    }

    private Map<String, String> parseEnvironmentVariables(JsonNode envNode) {
        if (!envNode.isObject()) {
            return Collections.emptyMap();
        }

        Map<String, String> env = new HashMap<>();
        envNode.fields().forEachRemaining(entry -> 
            env.put(entry.getKey(), entry.getValue().asText()));
        return env;
    }

    private String getStringValue(JsonNode node, String field, String defaultValue) {
        JsonNode valueNode = node.path(field);
        return valueNode.isTextual() ? valueNode.asText() : defaultValue;
    }

    private long getLongValue(JsonNode node, String field, long defaultValue) {
        JsonNode valueNode = node.path(field);
        return valueNode.isNumber() ? valueNode.asLong() : defaultValue;
    }

    private int getIntValue(JsonNode node, String field, int defaultValue) {
        JsonNode valueNode = node.path(field);
        return valueNode.isNumber() ? valueNode.asInt() : defaultValue;
    }

    private boolean getBooleanValue(JsonNode node, String field, boolean defaultValue) {
        JsonNode valueNode = node.path(field);
        return valueNode.isBoolean() ? valueNode.asBoolean() : defaultValue;
    }

    private ResourceLimits getDefaultResourceLimits() {
        return ResourceLimits.builder()
            .maxExecutionTimeMs(5000L)
            .maxMemoryBytes(256 * 1024 * 1024L)
            .maxProcessCount(1)
            .maxOutputBytes(10 * 1024 * 1024L)
            .maxFileSize(5 * 1024)
            .build();
    }

    private SecuritySettings getDefaultSecuritySettings() {
        return SecuritySettings.builder()
            .allowedSystemCalls(Arrays.asList("read", "write", "exit"))
            .allowedPaths(Collections.emptyList())
            .networkAccess(false)
            .fileSystemAccess(false)
            .capabilities(Collections.emptyList())
            .build();
    }

    // 动态语言规范实现类
    @Data
    private static class DynamicLanguageSpec implements LanguageSpec {
        private final String name;
        private final String version;
        private final String fileExtension;
        private final boolean needsCompilation;
        private final CompilerSettings compilerSettings;
        private final RuntimeSettings runtimeSettings;
        private final ResourceLimits resourceLimits;
        private final SecuritySettings securitySettings;

        @Override
        public boolean needsCompilation() {
            return false;
        }
    }
}