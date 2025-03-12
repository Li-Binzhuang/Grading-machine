package org.example.domain.compile;

import org.example.domain.VO.Result;

import java.nio.file.Path;

public interface Compile {

    Result<Path> compile(String code);
}
