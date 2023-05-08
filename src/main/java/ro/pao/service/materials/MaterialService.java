package ro.pao.service.materials;

import ro.pao.model.materials.abstracts.Material;

import java.util.Map;
import java.util.UUID;

public interface MaterialService {
    Map<UUID, Material> addAllKindOfMaterials();

    Map<UUID, Material> getAllItems();
}
