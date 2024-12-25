package org.example.DAO;

import org.example.pojo.Problem;
import org.example.pojo.TestCase;

import java.util.ArrayList;

public interface TestCaseDao {
    ArrayList<TestCase> getTestCases(Problem problem);
}