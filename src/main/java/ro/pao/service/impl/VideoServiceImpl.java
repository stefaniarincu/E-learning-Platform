package ro.pao.service.impl;

import ro.pao.model.Video;
import ro.pao.model.enums.Discipline;
import ro.pao.repository.MaterialRepository;
import ro.pao.repository.impl.VideoRepositoryImpl;
import ro.pao.service.VideoService;

import java.sql.SQLException;
import java.util.*;

public class VideoServiceImpl implements VideoService {
    private static final MaterialRepository<Video> videoRepository = new VideoRepositoryImpl();

    @Override
    public Optional<Video> getById(UUID id) throws SQLException {
        return videoRepository.getObjectById(id);
    }

    @Override
    public List<Video> getAllItems() throws SQLException {
        return videoRepository.getAll();
    }

    @Override
    public void addOnlyOne(Video newObject) {
        videoRepository.addNewObject(newObject);
    }

    @Override
    public void addMany(List<Video> objectList) {
        videoRepository.addAllFromGivenList(objectList);
    }

    @Override
    public void removeById(UUID id) {
        videoRepository.deleteObjectById(id);
    }

    @Override
    public void updateById(UUID id, Video newObject) {
        videoRepository.updateObjectById(id, newObject);
    }

    @Override
    public List<Video> getAllMaterialsByDiscipline(Discipline discipline) {
        return videoRepository.getAllMaterialsByDiscipline(discipline);
    }
}

