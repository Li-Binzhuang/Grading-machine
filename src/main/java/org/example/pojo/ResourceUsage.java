package org.example.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

// 资源使用统计类
@Data
@Component
public class ResourceUsage{
    private long peakMemoryBytes;
    private long cpuTimeMillis;
    private long wallTimeMillis;
}