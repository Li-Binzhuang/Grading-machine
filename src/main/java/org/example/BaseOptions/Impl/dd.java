package org.example.BaseOptions.Impl;

import jdk.nashorn.internal.codegen.CompilationException;
import lombok.extern.slf4j.Slf4j;
import org.example.BaseOptions.Interface.CodeCompiler;
import org.example.BaseOptions.Interface.SandboxExecutor;
import org.example.LanguageSuport.LanguageConfig;
import org.example.pojo.CompilationResult;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// C++编译器实现
@Component
@Slf4j
public class CppCompiler implements CodeCompiler {
    private final LanguageConfig config;
    private final SandboxExecutor sandbox;
    
    public CppCompiler(LanguageConfig config, SandboxExecutor sandbox) {
        this.config = config;
        this.sandbox = sandbox;
    }
    
    @Override
    public CompilationResult compile(Path sourceFile, Path binaryFile) throws CompilationException {
        CompilationResult result = new CompilationResult();
        
        List<String> command = new ArrayList<>();
        command.add(config.getCompiler());
        command.addAll(config.getCompilerFlags());
        command.add(sourceFile.toString());
        command.add("-o");
        command.add(binaryFile.toString());
        
        try {
            Process process = sandbox.createSandboxedProcess(command);
            boolean completed = process.waitFor(config.getCompileTimeout(), TimeUnit.MILLISECONDS);
            
            if (!completed) {
                process.destroyForcibly();
                result.setSuccess(false);
                result.setCompilerOutput("Compilation timed out");
                return result;
            }
            
            result.setSuccess(process.exitValue() == 0);
            result.setCompilerOutput(process.getErrorStream().toString());
            
            return result;
        } catch (Exception e) {
            throw new CompilationException("Compilation failed", e);
        }
    }
}