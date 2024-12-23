package org.example.BaseOptions.Impl;

import lombok.extern.slf4j.Slf4j;
import org.example.BaseOptions.Interface.SandboxExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Firejail沙箱实现
@Component
@Slf4j
public class FirejailSandbox implements SandboxExecutor {
    @Value("${firejail.path}")
    private  String FIREJAIL_PATH;

    @Override
    public Process createSandboxedProcess(List<String> command) throws IOException {
        List<String> sandboxedCommand = new ArrayList<>();
        sandboxedCommand.add(FIREJAIL_PATH);
        sandboxedCommand.add("--noprofile");
        sandboxedCommand.add("--quiet");
        sandboxedCommand.addAll(command);

        return new ProcessBuilder(sandboxedCommand).start();
    }
}