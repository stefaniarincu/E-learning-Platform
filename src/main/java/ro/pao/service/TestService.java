package ro.pao.service;

import ro.pao.model.Test;
import ro.pao.model.enums.TestType;

import java.sql.SQLException;
import java.util.List;

public interface TestService extends MaterialService<Test> {
    List<Test> getAllTestsByType(TestType testType) throws SQLException;
}
