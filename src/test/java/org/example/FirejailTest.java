package org.example;

import org.example.Service.RedissonService;
import org.example.Config.RedisConfig;
import org.example.Config.RedissonConfig;
import org.example.Config.RocketMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RocketMQConfig.class, RedissonConfig.class, RedisConfig.class, RedissonService.class})
public class FirejailTest {
    @Test
    public void test() {
        // 用户提交的 C++ 代码
        String code = "#include <iostream>\n" +
                "int main() {\n" +
                "    std::cout << \"Hello, World!\" << std::endl;\n" +
                "    return 0;\n" +
                "}";

        try {
            // 创建临时文件
            Path codeFilePath = Files.createTempFile("Main", ".cpp");
            Path binaryFilePath = Files.createTempFile("main", "");

            // 将代码写入文件
            try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(codeFilePath))) {
                writer.print(code);
            }

            // 编译和运行代码
            ProcessBuilder pb = new ProcessBuilder("firejail", "--noprofile", "--quiet", "sh", "-c",
                    "g++ " + codeFilePath.toString() + " -o " + binaryFilePath.toString() + " && " + binaryFilePath.toString());

            pb.redirectOutput(ProcessBuilder.Redirect.PIPE);
            pb.redirectError(ProcessBuilder.Redirect.PIPE);

            // 启动进程
            Process process = pb.start();

            // 读取输出和错误信息
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

                System.out.println("输出:");
                reader.lines().forEach(System.out::println);

                System.out.println("错误:");
                errorReader.lines().forEach(System.out::println);
            }

            // 等待进程结束
            int exitCode = process.waitFor();
            System.out.println("退出码: " + exitCode);

            // 根据退出码处理结果
            if (exitCode != 0) {
                System.err.println("编译或运行失败，退出码: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 清理临时文件
            try {
                Files.deleteIfExists(Paths.get("Main.cpp"));
                Files.deleteIfExists(Paths.get("main"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
