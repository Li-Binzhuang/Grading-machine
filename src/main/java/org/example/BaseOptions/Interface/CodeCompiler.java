package org.example.BaseOptions.Interface;

import jdk.nashorn.internal.codegen.CompilationException;
import org.example.pojo.CompilationResult;

import java.nio.file.Path;

// 编译器接口
public interface CodeCompiler {
    CompilationResult compile(Path sourceFile, Path binaryFile) throws CompilationException;
}
