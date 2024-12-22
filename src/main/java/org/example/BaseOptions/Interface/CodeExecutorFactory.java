package org.example.BaseOptions.Interface;

// 代码执行器抽象工厂
public interface CodeExecutorFactory {
    CodeExecutor getExecutor(String language);
}