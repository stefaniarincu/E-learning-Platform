package ro.pao.service.users.impl;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.users.Student;
import ro.pao.service.users.StudentService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {
    private static final LinkedHashMap<UUID, Student> studentMap = new LinkedHashMap<>();
    private static final Map<String, Student> studentEmailMap = new HashMap<>();

    @Override
    public Optional<Student> getById(UUID id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(studentMap.get(id));
    }

    @Override
    public Optional<Student> getByEmail(String email) {
        if (email == null) {
            return Optional.empty();
        }

        return studentEmailMap.values().stream()
                .filter(singleStudent -> email.equals(singleStudent.getEmail()))
                .findAny();
    }

    @Override
    public boolean emailExists(Student student) {
        if (student == null) {
            return false;
        }

        return studentEmailMap.values().stream()
                .anyMatch(singleStudent -> singleStudent.getEmail().equals(student.getEmail()));
    }

    @Override
    public void addOnlyOne(Student student) {
        if (!emailExists(student)) {
            student.setAverageGrade(student.calculateAverageGrade());

            studentMap.put(student.getId(), student);
            studentEmailMap.put(student.getEmail(), student);
        } else {
            System.out.println("\nEmail: " + student.getEmail() + " already used!");
        }
    }

    @Override
    public void addMany(LinkedHashMap<UUID, Student> students) {
        for(Map.Entry<UUID, Student> entry : students.entrySet()) {
            Student student = entry.getValue();

            if (!emailExists(student)) {
                student.setAverageGrade(student.calculateAverageGrade());

                studentMap.put(entry.getKey(), student);
                studentEmailMap.put(student.getEmail(), student);
            } else {
                System.out.println("\nEmail: " + student.getEmail() + " already used!");
            }
        }
    }

    @Override
    public LinkedHashMap<UUID, Student> getAllItems() {
        return studentMap;
    }

    @Override
    public Map<UUID, Student> getUsersByMaterialId(UUID materialId) {
        if(materialId == null) {
            return Collections.emptyMap();
        }

        return studentMap.values().stream()
                .filter(singleStudent -> singleStudent.getMaterials().stream()
                        .anyMatch(material -> material.getId().equals(materialId)))
                .collect(Collectors.toMap(Student::getId, Function.identity()));
    }

    @Override
    public Map<UUID, Student> getUsersByDiscipline(Discipline discipline) {
        if(discipline == null) {
            return Collections.emptyMap();
        }

        return studentMap.values().stream()
                .filter(singleStudent -> singleStudent.getMaterials().stream()
                        .anyMatch(material -> material.getDiscipline().equals(discipline)))
                .collect(Collectors.toMap(Student::getId, Function.identity()));
    }

    @Override
    public List<Student> getStudentsWithLowerGrade(Double averageGrade) {
        if(averageGrade == null) {
            return Collections.emptyList();
        }

        List<Student> students = studentMap.values().stream()
                .filter(singleStudent -> singleStudent.getAverageGrade() < averageGrade)
                .toList();

        return students.stream()
                .sorted((s1, s2) -> Double.compare(s2.getAverageGrade(), s1.getAverageGrade()))
                .toList();
    }

    @Override
    public List<Student> getStudentsWithHigherGrade(Double averageGrade) {
        if(averageGrade == null) {
            return Collections.emptyList();
        }

        List<Student> students = studentMap.values().stream()
                .filter(singleStudent -> singleStudent.getAverageGrade() > averageGrade)
                .toList();

        return students.stream()
                .sorted(Comparator.comparingDouble(Student::getAverageGrade))
                .toList();
    }

    @Override
    public void removeById(UUID id) {
        Student student = studentMap.get(id);

        if(student == null) {
            System.out.println("\nThe student account that you wanted to delete is invalid!");
        } else {
            studentMap.remove(id);
        }
    }

    @Override
    public void modifyById(UUID id, Student student) {
        if (student == null) {
            System.out.println("\nThe student account you wanted to modify is null!");
        } else if (studentMap.containsKey(id)) {
            removeById(id);
            addOnlyOne(student);
        } else {
            System.out.println("\nThe student account: --- " + student.getId() + " --- that you wanted to modify does not exist in the map!");
        }
    }
}
