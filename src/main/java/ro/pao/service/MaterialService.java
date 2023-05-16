package ro.pao.service;

import ro.pao.model.abstracts.Material;

import java.util.Map;
import java.util.UUID;

public interface MaterialService {
    Map<UUID, Material> addAllKindOfMaterials();

    Map<UUID, Material> getAllItems();
}
