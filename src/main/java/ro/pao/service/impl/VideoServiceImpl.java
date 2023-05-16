package ro.pao.service.impl;

import ro.pao.model.Video;
import ro.pao.model.enums.Discipline;
import ro.pao.service.VideoService;

import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class VideoServiceImpl implements VideoService {
    private static final Map<UUID, Video> videoMap = new HashMap<>();

    @Override
    public Optional<Video> getById(UUID id) {
        return Optional.ofNullable(videoMap.getOrDefault(id, null));
    }

    @Override
    public Map<UUID, Video> getAllItems() {
        return videoMap;
    }

    @Override
    public Map<UUID, Video> getVideosByMaxDuration(LocalTime duration) {
        if (duration == null) {
            return Collections.emptyMap();
        }

        return videoMap.values().stream()
                .filter(video -> !duration.isBefore(video.getDuration()))
                .collect(Collectors.toMap(Video::getId, Function.identity()));
    }

    @Override
    public Map<UUID, Video> getMaterialsByDiscipline(Discipline discipline) {
        if (discipline == null) {
            return Collections.emptyMap();
        }

        return videoMap.values().stream()
                .filter(video -> discipline.equals(video.getDiscipline()))
                .collect(Collectors.toMap(Video::getId, Function.identity()));
    }

    @Override
    public void addOnlyOne(Video video) {
        if(video == null) {
            System.out.println("The video that you wanted to add is invalid!");
        } else if(videoMap.containsKey(video.getId())) {
            System.out.println("The video: --- " + video.getTitle() + " --- that you wanted to add is already in the map!");
        } else {
            videoMap.put(video.getId(), video);
        }
    }

    @Override
    public void addMany(Map<UUID, Video> videos) {
        for(UUID id: videos.keySet()) {
            if(videos.get(id) == null) {
                System.out.println("The video that you wanted to add is invalid!");
            } else if(!videoMap.containsKey(id)) {
                videoMap.put(id, videos.get(id));
            } else {
                System.out.println("The video: --- " + videos.get(id).getTitle() + " --- that you wanted to add is already in the map!");
            }
        }
    }

    @Override
    public void removeById(UUID id) {
        Video video = videoMap.get(id);

        if(video == null) {
            System.out.println("The video that you wanted to remove is invalid!");
        } else {
            videoMap.remove(id);
        }
    }

    @Override
    public void modifyById(UUID id, Video video) {
        if (video == null) {
            System.out.println("The test you wanted to modify is null!");
        } else if (videoMap.containsKey(id)) {
            removeById(id);
            addOnlyOne(video);
        } else {
            System.out.println("The test: --- " + video.getId() + " --- that you wanted to modify does not exist in the map!");
        }
    }
}
