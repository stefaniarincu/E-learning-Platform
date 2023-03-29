package ro.pao.model.materials.abstracts;

import ro.pao.model.materials.enums.Discipline;

import java.util.UUID;

public abstract class Material {
    private UUID materialId;
    private Discipline discipline;
    private String title;
    private String description;

    public UUID getMaterialId() {
        return materialId;
    }

    public void setMaterialId(UUID materialId) {
        this.materialId = materialId;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
