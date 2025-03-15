package org.example.domain.compile;

import org.springframework.stereotype.Component;

import java.nio.file.Path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component("C")
public class CLinuxCompileImpl extends DefaultCompileImpl {
    private static final String COMPILER = "gcc"; // 或 clang
    private static final int TIMEOUT_SECONDS = 10;

    @Override
    public Path createTempFile(String code) throws IOException {
        Path tempFile = Files.createTempFile("compile_",  ".c");
        Files.writeString(tempFile,  code,
            StandardOpenOption.WRITE,
            StandardOpenOption.TRUNCATE_EXISTING);
        Files.setPosixFilePermissions(tempFile,
            Set.of(PosixFilePermission.OWNER_READ,
                   PosixFilePermission.OWNER_WRITE));
        return tempFile;
    }

    @Override
    public Path compile(Path sourceFilePath) {
        String targetName = sourceFilePath.getFileName().toString()
                          .replace(".c", ".out");
        Path targetFile = sourceFilePath.resolveSibling(targetName);

        Process process = null;
        try {
            process = new ProcessBuilder()
                .command(COMPILER,
                        "-o", targetFile.toString(),
                        sourceFilePath.toString()) // 添加警告参数
                .redirectErrorStream(true)
                .start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            if (!process.waitFor(TIMEOUT_SECONDS,  TimeUnit.SECONDS)) {
                throw new RuntimeException("编译超时");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (process.exitValue()  != 0) {
            String errorOutput = null;
            try {
                errorOutput = new String(process.getInputStream().readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException("编译错误:\n" + errorOutput);
        }

        return targetFile;
    }
}
