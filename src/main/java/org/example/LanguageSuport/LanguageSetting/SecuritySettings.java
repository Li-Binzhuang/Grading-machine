package org.example.LanguageSuport.LanguageSetting;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SecuritySettings {
    private List<String> allowedSystemCalls; // 允许的系统调用
    private List<String> allowedPaths;       // 允许访问的路径
    private boolean networkAccess;           // 是否允许网络访问
    private boolean fileSystemAccess;        // 是否允许文件系统访问
    private List<String> capabilities;       // Linux capabilities
}
