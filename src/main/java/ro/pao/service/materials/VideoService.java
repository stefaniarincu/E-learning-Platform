package ro.pao.service.materials;

import ro.pao.model.materials.Document;
import ro.pao.model.materials.Video;
import ro.pao.model.materials.enums.Discipline;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VideoService {
    Optional<Video> getById(UUID id);

    Map<UUID, Video> getAllItems();

    Map<UUID, Video> getVideosByDuration(LocalTime duration);

    Map<UUID, Video> getMaterialsByDiscipline(Discipline discipline);

    void addOnlyOne(Video video);

    void addMany(Map<UUID, Video> videos);

    void removeById(UUID id);

    void modifyById(UUID id, Video video);
}
