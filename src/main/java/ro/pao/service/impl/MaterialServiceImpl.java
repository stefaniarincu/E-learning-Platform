package ro.pao.service.impl;

import ro.pao.model.Document;
import ro.pao.model.Test;
import ro.pao.model.Video;
import ro.pao.model.abstracts.Material;
import ro.pao.service.MaterialService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MaterialServiceImpl implements MaterialService {
    private static final Map<UUID, Material> materialMap = new HashMap<>();

    @Override
    public Map<UUID, Material> addAllKindOfMaterials() {
        DocumentServiceImpl documentService = new DocumentServiceImpl();
        Map<UUID, Document> documents = documentService.getAllItems();
        materialMap.putAll(documents);

        VideoServiceImpl videoService = new VideoServiceImpl();
        Map<UUID, Video> videos = videoService.getAllItems();
        materialMap.putAll(videos);

        TestServiceImpl testService = new TestServiceImpl();
        Map<UUID, Test> tests = testService.getAllItems();
        materialMap.putAll(tests);

        return materialMap;
    }

    @Override
    public Map<UUID, Material> getAllItems() {
        return materialMap;
    }
}
