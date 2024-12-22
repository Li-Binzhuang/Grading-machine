package org.example.pojo;

public class RunnerException extends Exception {
    public RunnerException(String message) {
        super(message);
    }

    public RunnerException(String message, Throwable cause) {
        super(message, cause);
    }
}
