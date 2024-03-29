package ro.pao.mapper;

import ro.pao.model.Grade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GradeMapper {
    private static final GradeMapper INSTANCE = new GradeMapper();

    private GradeMapper() {
    }

    public static GradeMapper getInstance() {
        return INSTANCE;
    }

    public Grade mapToGrade(ResultSet resultSet) throws SQLException {
        if(resultSet.next()) {
            return new Grade().toBuilder()
                    .gradeId(UUID.fromString(resultSet.getString("grade_id")))
                    .studentId(UUID.fromString(resultSet.getString("user_id")))
                    .grade(resultSet.getDouble("grade"))
                    .weight(resultSet.getDouble("weight"))
                    .build();
        } else {
            return null;
        }
    }

    public Grade mapToGradeWithoutNext(ResultSet resultSet) throws SQLException {
        return new Grade().toBuilder()
                .gradeId(UUID.fromString(resultSet.getString("grade_id")))
                .studentId(UUID.fromString(resultSet.getString("user_id")))
                .grade(resultSet.getDouble("grade"))
                .weight(resultSet.getDouble("weight"))
                .build();
    }

    public List<Grade> mapToGradeList(ResultSet resultSet) throws SQLException {
        List<Grade> gradeList = new ArrayList<>();

        while (resultSet.next()) {
            gradeList.add(mapToGradeWithoutNext(resultSet));
        }

        return gradeList;
    }
}
