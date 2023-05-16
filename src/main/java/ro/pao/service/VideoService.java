package ro.pao.service;

import ro.pao.model.Video;
import ro.pao.model.enums.Discipline;

import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VideoService {
    Optional<Video> getById(UUID id);

    Map<UUID, Video> getAllItems();

    Map<UUID, Video> getVideosByMaxDuration(LocalTime duration);

    Map<UUID, Video> getMaterialsByDiscipline(Discipline discipline);

    void addOnlyOne(Video video);

    void addMany(Map<UUID, Video> videos);

    void removeById(UUID id);

    void modifyById(UUID id, Video video);
}
