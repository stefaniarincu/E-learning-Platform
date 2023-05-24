package ro.pao.service.impl;

import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.Video;
import ro.pao.model.enums.Discipline;
import ro.pao.repository.MaterialRepository;
import ro.pao.repository.impl.VideoRepositoryImpl;
import ro.pao.service.VideoService;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

public class VideoServiceImpl implements VideoService {
    private static final MaterialRepository<Video> videoRepository = new VideoRepositoryImpl();

    @Override
    public Optional<Video> getById(UUID id) {
        try {
            return videoRepository.getObjectById(id);
        } catch (ObjectNotFoundException e){
            LogServiceImpl.getInstance().log(Level.WARNING, e.getMessage());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Video> getAllItems() {
        return videoRepository.getAll();
    }

    @Override
    public void addOnlyOne(Video newObject) {
        try {
            videoRepository.addNewObject(newObject);
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void addMany(List<Video> objectList) {
        try {
            videoRepository.addAllFromGivenList(objectList);
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
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

    @Override
    public Optional<Video> getMaxDurationVideo() {
        Video maxDurationVideo = null;
        int maxDurationValue = Integer.MIN_VALUE;

        List<Video> videoList = getAllItems();

        Iterator<Video> videoIterator = videoList.iterator();
        while (videoIterator.hasNext()) {
            Video video = videoIterator.next();

            int duration = video.getDuration().getHour() + video.getDuration().getMinute() + video.getDuration().getSecond();

            if (duration > maxDurationValue) {
                maxDurationValue = duration;
                maxDurationVideo = video;
            }
        }

        return Optional.ofNullable(maxDurationVideo);
    }
}

