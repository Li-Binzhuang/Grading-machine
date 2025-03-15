package org.example.domain.compile;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.VO.Result;
import org.example.domain.VO.Status;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public abstract class DefaultCompileImpl implements Compile {
    Path sourceFilePath;
    Path targetFilePath;
    @Override
    public Result<Path> compile(String code) {
        // 先将代码写入文件中
        try {
            sourceFilePath=createTempFile(code);
        } catch (IOException e) {
            log.info("代码写入文件异常:{}", e.getMessage());
            throw new RuntimeException(e);
        }
        // 编译代码
        try {
            this.targetFilePath=compile(sourceFilePath);
        }catch (Exception e){
            return Result.error(null, e.getMessage(), Status.COMPILE_ERROR);
        }
        //删除原文件
        boolean delete = sourceFilePath.toFile().delete();

        return Result.success(targetFilePath, "编译成功");
    }
    //将代码写入文件中，返回文件路径
    public abstract Path createTempFile(String code) throws IOException;

    //编译代码
    public abstract Path compile(Path sourceFilePath);
}
