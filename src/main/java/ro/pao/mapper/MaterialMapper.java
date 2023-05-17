package ro.pao.mapper;

import ro.pao.model.Document;
import ro.pao.model.Test;
import ro.pao.model.Video;
import ro.pao.model.abstracts.Material;
import ro.pao.model.enums.Discipline;
import ro.pao.model.enums.DocumentType;
import ro.pao.model.enums.TestType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MaterialMapper {
    private static final MaterialMapper INSTANCE = new MaterialMapper();

    private MaterialMapper() {
    }

    public static MaterialMapper getInstance() {
        return INSTANCE;
    }

    public Document mapToDocument(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Document().toBuilder()
                            .id(UUID.fromString(resultSet.getString("material_id")))
                            .creationTime(resultSet.getTimestamp("creation_time").toLocalDateTime())
                            .discipline(Discipline.valueOf(resultSet.getString("discipline")))
                            .title(resultSet.getString("title"))
                            .description(resultSet.getString("description"))
                            .documentType(DocumentType.valueOf(resultSet.getString("document_type")))
                            .build();
        } else {
            return null;
        }
    }

    private Document mapToDocumentWithoutNext(ResultSet resultSet) throws SQLException {
        return new Document().toBuilder()
                .id(UUID.fromString(resultSet.getString("material_id")))
                .creationTime(resultSet.getTimestamp("creation_time").toLocalDateTime())
                .discipline(Discipline.valueOf(resultSet.getString("discipline")))
                .title(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .documentType(DocumentType.valueOf(resultSet.getString("document_type")))
                .build();
    }

    public List<Document> mapToDocumentList(ResultSet resultSet) throws SQLException {
        List<Document> documentList = new ArrayList<>();

        while (resultSet.next()) {
            documentList.add(mapToDocumentWithoutNext(resultSet));
        }

        return documentList;
    }

    public Test mapToTest(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Test().toBuilder()
                            .id(UUID.fromString(resultSet.getString("material_id")))
                            .creationTime(resultSet.getTimestamp("creation_time").toLocalDateTime())
                            .discipline(Discipline.valueOf(resultSet.getString("discipline")))
                            .title(resultSet.getString("title"))
                            .description(resultSet.getString("description"))
                            .testType(TestType.valueOf(resultSet.getString("test_type")))
                            .build();
        } else {
            return null;
        }
    }

    private Test mapToTestWithoutNext(ResultSet resultSet) throws SQLException {
        return new Test().toBuilder()
                .id(UUID.fromString(resultSet.getString("material_id")))
                .creationTime(resultSet.getTimestamp("creation_time").toLocalDateTime())
                .discipline(Discipline.valueOf(resultSet.getString("discipline")))
                .title(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .testType(TestType.valueOf(resultSet.getString("test_type")))
                .build();
    }

    public List<Test> mapToTestList(ResultSet resultSet) throws SQLException {
        List<Test> testList = new ArrayList<>();

        while (resultSet.next()) {
            testList.add(mapToTestWithoutNext(resultSet));
        }

        return testList;
    }

    public Video mapToVideo(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Video().toBuilder()
                            .id(UUID.fromString(resultSet.getString("material_id")))
                            .creationTime(resultSet.getTimestamp("creation_time").toLocalDateTime())
                            .discipline(Discipline.valueOf(resultSet.getString("discipline")))
                            .title(resultSet.getString("title"))
                            .description(resultSet.getString("description"))
                            .duration(resultSet.getTime("duration").toLocalTime())
                            .build();
        } else {
            return null;
        }
    }

    private Video mapToVideoWithoutNext(ResultSet resultSet) throws SQLException {
        return new Video().toBuilder()
                .id(UUID.fromString(resultSet.getString("material_id")))
                .creationTime(resultSet.getTimestamp("creation_time").toLocalDateTime())
                .discipline(Discipline.valueOf(resultSet.getString("discipline")))
                .title(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .duration(resultSet.getTime("duration").toLocalTime())
                .build();
    }

    public List<Video> mapToVideoList(ResultSet resultSet) throws SQLException {
        List<Video> videoList = new ArrayList<>();

        while (resultSet.next()) {
            videoList.add(mapToVideoWithoutNext(resultSet));
        }

        return videoList;
    }

    public List<Material> mapToMaterialList(ResultSet resultSet) throws SQLException {
        List<Material> materialList = new ArrayList<>();

        while (resultSet.next()){
            if (resultSet.getString("material_type").equalsIgnoreCase("document"))
                materialList.add(mapToDocumentWithoutNext(resultSet));
            else if (resultSet.getString("material_type").equalsIgnoreCase("test"))
                materialList.add(mapToTestWithoutNext(resultSet));
            else
                materialList.add(mapToVideoWithoutNext(resultSet));
        }

        return materialList;
    }
}
