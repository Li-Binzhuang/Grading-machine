package org.example.BaseOptions.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
// 文件清理工具
@Component
public class FileCleanupUtil {
    public static void cleanupFiles(Path... files) {
        for (Path file : files) {
            try {
                if (file != null) {
                    Files.deleteIfExists(file);
                }
            } catch (IOException e) {
                log.warn("Failed to delete file: {}", file, e);
            }
        }
    }
}