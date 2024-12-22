package org.example.LanguageSuport.LanguageSpec;

import org.example.LanguageSuport.Interface.LanguageSpec;

// 抽象基类，提供基础实现
public abstract class BaseLanguageSpec implements LanguageSpec {
    private final String name;
    private final String version;
    private final String fileExtension;
    private final boolean needsCompilation;

    protected BaseLanguageSpec(String name, String version, String fileExtension, boolean needsCompilation) {
        this.name = name;
        this.version = version;
        this.fileExtension = fileExtension;
        this.needsCompilation = needsCompilation;
    }

    @Override
    public String getName() { return name; }

    @Override
    public String getVersion() { return version; }

    @Override
    public String getFileExtension() { return fileExtension; }

    @Override
    public boolean needsCompilation() { return needsCompilation; }
}

