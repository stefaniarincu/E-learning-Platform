package ro.pao.service;

import ro.pao.model.Video;

import java.util.Optional;

public interface VideoService extends MaterialService<Video> {
    Optional<Video> getMaxDurationVideo();
}
