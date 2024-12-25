package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
class TestCase {
    public String getInput() {
        return input;
    }
    public String getExpectedOutput() {
        return expectedOutput;
    }
    private final String input;
    private final String expectedOutput;
    public TestCase(String input, String expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }
}

public class FirejailSandbox {
    public static void main(String[] args) throws IOException {
        testCode("cpp", "#include <iostream>" +
                "\n int main() {" +
                " int a, b; std::cin >> a >> b; std::cout << a - b << std::endl; " +
                "return 0; }", Arrays.asList(
                        new TestCase("1 2", "3"),
                        new TestCase("3 4", "7")));

        testCode("java", "public class Main { " +
                "public static void main(String[] args) { " +
                "java.util.Scanner scanner = new java.util.Scanner(System.in); " +
                "int a = scanner.nextInt(); int b = scanner.nextInt(); " +
                "System.out.println(a - b); } }", Arrays.asList(
                        new TestCase("1 2", "3"),
                        new TestCase("3 4", "7")));

        testCode("py", "a, b = map(int, input().split())\n" +
                "print(a - b)", Arrays.asList(new TestCase("1 2", "3"),
                        new TestCase("3 4", "7")));

    }

    public static void testCode(String language, String code, List<TestCase> testCases) throws IOException {
        Path binaryFilePath = null;
        String executeCommand = null;
        Path codeFilePath=null;

        try {
            Process compileProcess = null;
            if (language.equals("cpp")) {
                codeFilePath = Files.createFile(Paths.get("main.cpp"));//手动创建Main.java
                binaryFilePath = Files.createFile(Paths.get("main.out"));
                Files.write(codeFilePath, code.getBytes());
                compileProcess = new ProcessBuilder("g++", codeFilePath.toString(), "-o", binaryFilePath.toString()).start();
                executeCommand = binaryFilePath.toAbsolutePath().toString();
            } else if (language.equals("java")) {

                codeFilePath = Files.createFile(Paths.get("Main.java"));//手动创建Main.java
                Files.write(codeFilePath, code.getBytes());
                compileProcess = new ProcessBuilder("javac", codeFilePath.toString()).start();
                executeCommand = "java Main";

            } else if (language.equals("py")) {

                codeFilePath = Files.createFile(Paths.get("Main.py"));//手动创建Main.py
                Files.write(codeFilePath, code.getBytes());
                executeCommand = "python3 " + codeFilePath.toAbsolutePath();

            }

            if (compileProcess != null && compileProcess.waitFor() != 0) {
                System.err.println(language + " 编译失败");
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()))) {
                    errorReader.lines().forEach(System.err::println);
                }
                return;
            }

            for (TestCase testCase : testCases) {
                Path inputFilePath = Files.createTempFile("input", ".txt");
                Files.write(inputFilePath, testCase.getInput().getBytes());

                List<String> firejailArgs = new ArrayList<>();
                firejailArgs.add("firejail");
                firejailArgs.add("--noprofile");
                firejailArgs.add("--quiet");
                firejailArgs.add("--net=none");
                firejailArgs.add("--cpu=1");
                firejailArgs.add("--whitelist=" + inputFilePath.toAbsolutePath());
                if(binaryFilePath!=null)
                    firejailArgs.add("--whitelist=" + binaryFilePath.toAbsolutePath());

                // add execute command to firejail args
                Collections.addAll(firejailArgs, executeCommand.split(" "));

                ProcessBuilder pb = new ProcessBuilder(firejailArgs);
                pb.redirectInput(inputFilePath.toFile());
                pb.redirectOutput(ProcessBuilder.Redirect.PIPE);
                pb.redirectError(ProcessBuilder.Redirect.PIPE);

                Process process = pb.start();

                String actualOutput = new BufferedReader(new InputStreamReader(process.getInputStream())).lines().collect(Collectors.joining(System.lineSeparator()));
                String errorOutput = new BufferedReader(new InputStreamReader(process.getErrorStream())).lines().collect(Collectors.joining(System.lineSeparator()));

                System.out.println("输入: " + testCase.getInput());
                System.out.println("输出:");
                System.out.println(actualOutput);
                System.out.println("错误:");
                System.out.println(errorOutput);

                if (actualOutput.trim().equals(testCase.getExpectedOutput())) {
                    System.out.println("输出匹配预期结果!");
                } else {
                    System.err.println("输出不匹配预期结果。实际输出: " + actualOutput);
                }

                Files.deleteIfExists(inputFilePath);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                Files.deleteIfExists(codeFilePath);
                if (binaryFilePath != null) {
                    Files.deleteIfExists(binaryFilePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

