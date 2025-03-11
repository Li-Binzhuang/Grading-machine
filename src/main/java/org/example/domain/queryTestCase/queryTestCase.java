package org.example.domain.queryTestCase;

import org.example.pojo.TestCase;

import java.sql.SQLException;
import java.util.List;

public interface queryTestCase {
    List<TestCase> queryTestCaseById(Integer id) throws Exception;
}
