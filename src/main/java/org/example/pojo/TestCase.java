package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//测试用例基类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCase {
    private int testCaseId;
    private String input;
    private String expectedOutput;
    private long questionId;
}