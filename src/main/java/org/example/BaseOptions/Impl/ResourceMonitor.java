package org.example.BaseOptions.Impl;

import com.esotericsoftware.minlog.Log;
import jdk.nashorn.internal.runtime.regexp.joni.WarnCallback;
import lombok.extern.slf4j.Slf4j;
import org.example.pojo.ResourceUsage;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 资源监控器
@Slf4j
@Component
public class ResourceMonitor {
    public ResourceUsage getResourceUsage(Process process) {
        ResourceUsage usage = new ResourceUsage();
        try {
            usage.setPeakMemoryBytes(getProcessPeakMemory(process));
        } catch (IOException e) {
            log.warn("Failed to get memory usage", e);
        }
        return usage;
    }

    private long getProcessPeakMemory(Process process) throws IOException {
        // 实现内存使用监控
        return 0;
    }
}