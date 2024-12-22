package org.example.pojo;

import lombok.Data;

@Data
public class RunResult {
    private ResourceUsage resourceUsage;


    public void setExitCode(int i) {
    }

    public void setError(String executionTimedOut) {
    }

    public void setOutput(String s) {
    }
}
