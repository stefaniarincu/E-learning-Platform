package ro.pao.service.materials.impl;

import ro.pao.model.materials.Video;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.service.materials.VideoService;

import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class VideoServiceImpl implements VideoService {
    private static Map<UUID, Video> videoMap = new HashMap<>();

    @Override
    public Optional<Video> getById(UUID id) {
        return videoMap.values().stream()
                .filter(video -> id.equals(video.getId()))
                .findAny();
    }

    @Override
    public Map<UUID, Video> getAllItems() {
        return videoMap;
    }

    @Override
    public Map<UUID, Video> getVideosByDuration(LocalTime duration) {
        return videoMap.values().stream()
                .filter(video -> duration.compareTo(video.getDuration()) >= 0)
                .collect(Collectors.toMap(Video::getId, Function.identity()));
    }

    @Override
    public Map<UUID, Video> getMaterialsByDiscipline(Discipline discipline) {
        return videoMap.values().stream()
                .filter(video -> discipline.equals(video.getDiscipline()))
                .collect(Collectors.toMap(Video::getId, Function.identity()));
    }

    @Override
    public void addOnlyOne(Video video) {
        VideoServiceImpl.videoMap.put(video.getId(), video);
    }

    @Override
    public void addMany(Map<UUID, Video> videos) {
        videoMap.putAll(videos);
    }

    @Override
    public void removeById(UUID id) {
        videoMap = videoMap.entrySet().stream()
                .filter(video -> !id.equals(video.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void modifyById(UUID id, Video video) {
        removeById(id);
        addOnlyOne(video);
    }
}
