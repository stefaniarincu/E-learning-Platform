package ro.pao.repository;

import ro.pao.model.Test;
import ro.pao.model.enums.TestType;

import java.sql.SQLException;
import java.util.List;

public interface TestRepository extends MaterialRepository<Test>{
    List<Test> getAllTestsByType(TestType testType) throws SQLException;
}
