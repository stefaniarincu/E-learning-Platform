package ro.pao.service;

import ro.pao.model.enums.Discipline;
import ro.pao.model.sealed.Teacher;

import java.util.*;

public interface TeacherService extends UserService<Teacher> {
    List<Teacher> getTeachersByDiscipline(Discipline discipline);
}
