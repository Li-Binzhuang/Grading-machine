package org.example.BaseOptions.Impl;

import lombok.extern.slf4j.Slf4j;
import org.example.BaseOptions.Interface.CodeCompiler;
import org.example.BaseOptions.Interface.CodeExecutor;
import org.example.BaseOptions.Interface.CodeRunner;
import org.example.BaseOptions.Interface.SandboxExecutor;
import org.example.LanguageSuport.LanguageConfig;
import org.example.pojo.CompilationResult;
import org.example.pojo.ExecutionResult;
import org.example.pojo.RunResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

import static org.example.BaseOptions.Impl.FileCleanupUtil.cleanupFiles;

// 编译型语言执行器抽象类
@Slf4j
public abstract class CompiledLanguageExecutor implements CodeExecutor {
    protected final LanguageConfig config;
    protected final CodeCompiler compiler;
    protected final CodeRunner runner;
    protected final SandboxExecutor sandbox;
    
    protected CompiledLanguageExecutor(LanguageConfig config, CodeCompiler compiler, 
                                     CodeRunner runner, SandboxExecutor sandbox) {
        this.config = config;
        this.compiler = compiler;
        this.runner = runner;
        this.sandbox = sandbox;
    }
    
    @Override
    public ExecutionResult execute(String sourceCode, String input) throws ExecutionException {
        ExecutionResult result = new ExecutionResult();
        Path sourceFile = null;
        Path binaryFile = null;
        
        try {
            sourceFile = createSourceFile(sourceCode);
            binaryFile = Files.createTempFile("program", "");
            
            CompilationResult compilationResult = compiler.compile(sourceFile, binaryFile);
            result.setCompilationResult(compilationResult);
            
            if (!compilationResult.isSuccess()) {
                return result;
            }
            
            RunResult runResult = runner.run(binaryFile, input);
            updateExecutionResult(result, runResult);
            
            return result;
        } catch (Exception e) {
            throw new ExecutionException("Execution failed", e);
        } finally {
            cleanupFiles(sourceFile, binaryFile);
        }
    }

    private void updateExecutionResult(ExecutionResult result, RunResult runResult) {

    }

    protected abstract Path createSourceFile(String sourceCode) throws IOException;
}