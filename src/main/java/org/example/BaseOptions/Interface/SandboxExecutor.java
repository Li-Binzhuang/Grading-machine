package org.example.BaseOptions.Interface;

import java.io.IOException;
import java.util.List;

// 沙箱执行器接口
public interface SandboxExecutor {
    Process createSandboxedProcess(List<String> command) throws IOException;
}