package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
//测试用例基类
@Data
@AllArgsConstructor
public class TestCase {
    private final String input;
    private final String expectedOutput;

}