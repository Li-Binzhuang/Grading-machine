package org.example.compileTest;

import lombok.extern.slf4j.Slf4j;
import org.example.config.ApplicationConfig;
import org.example.config.DataConfig;
import org.example.config.RedissonConfig;
import org.example.config.RocketMQConfig;
import org.example.domain.VO.Result;
import org.example.domain.compile.CLinuxCompileImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataConfig.class, RedissonConfig.class, RocketMQConfig.class, ApplicationConfig.class})
public class CLinuxCompileImplTest {
    CLinuxCompileImpl cLinuxCompileimpl=new CLinuxCompileImpl();
    @Test
    public void testDangerousCode() throws IOException {
        String maliciousCode = """
            #include <stdio.h>
            int main() {
                printf("Hacking...");
                return 0;
            }
        """;
        Result<Path> compileResult = cLinuxCompileimpl.compile(maliciousCode);
        if(compileResult==null){
            throw new IOException("hahahah");
        }
    }

}
