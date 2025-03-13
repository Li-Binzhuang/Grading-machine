package org.example.domain.run;

import lombok.extern.slf4j.Slf4j;
import org.example.Repository.pojo.TestCase;
import org.example.domain.VO.Result;
import org.example.domain.VO.Status;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
@Slf4j
public abstract class DefaultRunCode implements RunCodeCompile {
    String fireJailArg=new String("firejail --noprofile --net=none --cpu=1 ");
    public Result<String> runCode(Path afterCompileFilePath, List<TestCase> testCases) {
        //排除目标文件夹
        fireJailArg+="--whitelist="+ afterCompileFilePath.toAbsolutePath();
        // 准备命令行参数
        List<String> baseCommand = new ArrayList<>(Arrays.asList(fireJailArg.split(" ")));
        baseCommand.addAll(Arrays.asList(prepareLanguageRunArg().split(" ")));
        baseCommand.add(afterCompileFilePath.toAbsolutePath().toString());

        for (TestCase testCase : testCases) {
            ProcessBuilder processBuilder = new ProcessBuilder(baseCommand);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE);
            processBuilder.redirectError(ProcessBuilder.Redirect.PIPE);

            try {
                Process process = processBuilder.start();

                // 写入输入流
                try (OutputStream os = process.getOutputStream();
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os))) {
                    writer.write(testCase.getInput());
                    writer.flush();
                }

                // 获取输出
                String actualOutput = new BufferedReader(new InputStreamReader(process.getInputStream()))
                        .lines().collect(Collectors.joining("\n"));

                // 时间统计
                long startTime = System.nanoTime();
                // 等待进程执行完成 ,2S内需要完成
                boolean exitCode = process.waitFor(2,  TimeUnit.SECONDS);
                long executionTime = (System.nanoTime()  - startTime) / 1_000_000;

                // 内存统计
                long memoryUsage = getProcessMemory(process);

                // 错误处理逻辑
                if (!exitCode) {
                    String errorMsg = new BufferedReader(new InputStreamReader(process.getErrorStream()))
                            .lines().collect(Collectors.joining("\n"));
                    return Result.error("Runtime  Error",
                        String.format("Time:  %dms | Memory: %dKB", executionTime, memoryUsage),
                        Status.RUNTIME_ERROR);
                }

                // 输出比对
                if (!actualOutput.equals(testCase.getExpectedOutput()))  {
                    return Result.error("Wrong  Answer",
                        String.format("Expected:  %s | Actual: %s\nTime: %dms | Memory: %dKB",
                            testCase.getExpectedOutput(),  actualOutput, executionTime, memoryUsage),
                        Status.WRONG_ANSWER);
                }

            } catch (IOException | InterruptedException e) {
                return Result.error("System  Error", e.getMessage(),  Status.SYSTEM_ERROR);
            }
        }
        return Result.success("All  test cases passed", "Success", Status.SUCCESS);
    }

    // 准备各个语言相关的命令行参数
    public abstract String prepareLanguageRunArg();


    private long getProcessMemory(Process process) {
        // 反射获取 PID
        Long pid=null;

        try {
            if (process.getClass().getName().equals("java.lang.UNIXProcess"))  {
                // 反射获取 PID（兼容 Java 8）
                Field pidField = process.getClass().getDeclaredField("pid");
                pidField.setAccessible(true);
                pid = pidField.getLong(process);

                // 读取 /proc/<PID>/status
                Path statusPath = Path.of("/proc",  String.valueOf(pid),  "status");
                List<String> statusLines = Files.readAllLines(statusPath);
                for (String line : statusLines) {
                    if (line.startsWith("VmRSS"))  {
                        return Long.parseLong(line.split("\\s+")[1]);
                    }
                }
            }
        } catch (Exception e) {
            // Fallback to ps command
            try {
                Process psProcess = new ProcessBuilder("ps", "-o", "rss=", "-p", String.valueOf(pid)).start();
                String psOutput = new BufferedReader(new InputStreamReader(psProcess.getInputStream())).readLine();
                return psOutput != null ? Long.parseLong(psOutput.trim())  : 0;
            } catch (IOException ex) {
                return 0;
            }
        }
        return 0;
    }

}
