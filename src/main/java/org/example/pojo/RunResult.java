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

    public void setResourceUsage(ResourceUsage resourceUsage2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setResourceUsage'");
    }
}
