package org.example.domain.VO;

import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

import java.security.PublicKey;

@Data
public class Status {
    public static final String ACCEPTED = "Accepted";
    public static final String WRONG_ANSWER = "Wrong Answer";
    public static final String TIME_LIMIT_EXCEEDED = "Time Limit Exceeded";
    public static final String MEMORY_LIMIT_EXCEEDED = "Memory Limit Exceeded";
    public static final String RUNTIME_ERROR = "Runtime Error";
    public static final String COMPILE_ERROR = "Compile Error";
    public static final String SYSTEM_ERROR = "system error";
    public static final String SUCCESS = "success";
}
