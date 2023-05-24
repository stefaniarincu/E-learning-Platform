package ro.pao.service.impl;

import ro.pao.exceptions.MaterialNotFoundException;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.Document;
import ro.pao.model.Test;
import ro.pao.model.Video;
import ro.pao.model.abstracts.Material;
import ro.pao.model.enums.Discipline;
import ro.pao.service.MaterialService;

import java.sql.SQLException;
import java.util.*;

public class MaterialServiceImpl implements MaterialService<Material> {
    private final MaterialService<Document> documentService = new DocumentServiceImpl();
    private final MaterialService<Test> testService = new TestServiceImpl();
    private final MaterialService<Video> videoService = new VideoServiceImpl();

    @Override
    public Optional<Material> getById(UUID id) {
        Optional<? extends Material> material = documentService.getById(id);
        if (material.isPresent())
            return material.map(m -> (Material) m);

        material = testService.getById(id);
        if (material.isPresent())
            return material.map(m -> (Material) m);
        return videoService.getById(id).map(m -> m);
    }

    @Override
    public List<Material> getAllItems(){
        List<Material> materialList = new ArrayList<>();

        materialList.addAll(documentService.getAllItems());
        materialList.addAll(testService.getAllItems());
        materialList.addAll(videoService.getAllItems());

        return materialList;
    }

    @Override
    public void addOnlyOne(Material newObject) {
        if (newObject instanceof Document document) {
            documentService.addOnlyOne(document);
        } else if (newObject instanceof Test test) {
            testService.addOnlyOne(test);
        } else {
            videoService.addOnlyOne((Video) newObject);
        }
    }

    @Override
    public void addMany(List<Material> objectList) {
        for (Material material: objectList) {
            if (material instanceof Document document) {
                documentService.addOnlyOne(document);
            } else if (material instanceof Test test) {
                testService.addOnlyOne(test);
            } else {
                videoService.addOnlyOne((Video) material);
            }
        }
    }

    @Override
    public void removeById(UUID id) {
        documentService.removeById(id);
        testService.removeById(id);
        videoService.removeById(id);
    }

    @Override
    public void updateById(UUID id, Material newObject) {
        if (newObject instanceof Document document) {
            documentService.updateById(id, document);
        } else if (newObject instanceof Test test) {
            testService.updateById(id, test);
        } else {
            videoService.updateById(id, (Video) newObject);
        }
    }

    @Override
    public List<Material> getAllMaterialsByDiscipline(Discipline discipline) {
        List<Material> materialList = new ArrayList<>();

        materialList.addAll(documentService.getAllMaterialsByDiscipline(discipline));
        materialList.addAll(testService.getAllMaterialsByDiscipline(discipline));
        materialList.addAll(videoService.getAllMaterialsByDiscipline(discipline));

        return materialList;
    }
}
