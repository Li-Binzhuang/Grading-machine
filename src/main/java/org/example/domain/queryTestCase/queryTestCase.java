package org.example.domain.queryTestCase;

import org.example.Repository.pojo.TestCase;

import java.util.List;

/**
 * @Description:   通过问题id查询问题的测试用例
 * @Author:         laoli
 * @CreateDate:     2025/3/10 15:58
 */
public interface queryTestCase {
    List<TestCase> queryTestCaseByQuestionId(Integer id) throws Exception;
}
