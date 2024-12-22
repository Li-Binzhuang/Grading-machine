package org.example.BaseOptions.Impl;

import lombok.extern.slf4j.Slf4j;
import org.example.BaseOptions.Interface.CodeRunner;
import org.example.BaseOptions.Interface.SandboxExecutor;
import org.example.LanguageSuport.LanguageConfig;
import org.example.pojo.ResourceUsage;
import org.example.pojo.RunResult;
import org.example.pojo.RunnerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// 通用代码运行器
@Component
@Slf4j
public class UniversalCodeRunner implements CodeRunner {
    private final LanguageConfig config;
    private final SandboxExecutor sandbox;
    private final ResourceMonitor resourceMonitor;
    
    @Override
    public RunResult run(Path executablePath, String input) throws RunnerException {
        RunResult result = new RunResult();
        
        List<String> command = buildExecutionCommand(executablePath);
        
        try {
            Process process = sandbox.createSandboxedProcess(command);
            
            if (input != null && !input.isEmpty()) {
                process.getOutputStream().write(input.getBytes());
                process.getOutputStream().close();
            }
            
            long startTime = System.currentTimeMillis();
            boolean completed = process.waitFor(config.getMaxExecutionTime(), TimeUnit.MILLISECONDS);
            long endTime = System.currentTimeMillis();
            
            ResourceUsage resourceUsage = resourceMonitor.getResourceUsage(process);
            result.setResourceUsage(resourceUsage);
            
            if (!completed) {
                handleTimeout(process, result);
                return result;
            }
            
            updateRunResult(result, process, startTime, endTime);
            return result;
            
        } catch (Exception e) {
            throw new RunnerException("Execution failed", e);
        }
    }
    
    private List<String> buildExecutionCommand(Path executablePath) {
        List<String> command = new ArrayList<>();
        command.add(executablePath.toString());
        return command;
    }
    
    private void handleTimeout(Process process, RunResult result) {
        process.destroyForcibly();
        result.setExitCode(-1);
        result.setError("Execution timed out");
        result.getResourceUsage().setTimedOut(true);
    }
    
    private void updateRunResult(RunResult result, Process process, long startTime, long endTime) 
            throws IOException {
        result.setExitCode(process.exitValue());
        result.setOutput(new String(process.getInputStream().readAllBytes()));
        result.setError(new String(process.getErrorStream().readAllBytes()));
        result.getResourceUsage().setWallTimeMillis(endTime - startTime);
    }

    @Override
    public RunResult run(Path executablePath, String input) throws RunnerException {
        return null;
    }
}