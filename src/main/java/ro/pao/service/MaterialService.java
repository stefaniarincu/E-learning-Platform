package ro.pao.service;

import ro.pao.model.abstracts.Material;
import ro.pao.model.enums.Discipline;

import java.util.List;

public interface MaterialService<T extends Material> extends ServiceGeneric<T>{
    List<T> getAllMaterialsByDiscipline(Discipline discipline);
}
