package ro.pao.service;

import ro.pao.model.enums.Discipline;
import ro.pao.model.Teacher;

import java.util.*;

public interface TeacherService extends UserService<Teacher> {
    List<Teacher> getTeachersByDiscipline(Discipline discipline);
}
