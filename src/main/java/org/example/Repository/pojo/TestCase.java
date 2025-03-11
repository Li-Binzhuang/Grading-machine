package org.example.Repository.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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