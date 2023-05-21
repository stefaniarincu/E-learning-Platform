package ro.pao.service.impl;

import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.Course;
import ro.pao.model.abstracts.Material;
import ro.pao.model.sealed.Student;
import ro.pao.repository.CourseRepository;
import ro.pao.repository.MaterialRepository;
import ro.pao.repository.StudentRepository;
import ro.pao.repository.impl.CourseRepositoryImpl;
import ro.pao.repository.impl.MaterialRepositoryImpl;
import ro.pao.repository.impl.StudentRepositoryImpl;
import ro.pao.service.CourseService;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public class CourseServiceImpl implements CourseService {
    private static final CourseRepository courseRepository = new CourseRepositoryImpl();
    private static final StudentRepository studentRepository = new StudentRepositoryImpl();
    private static final MaterialRepository<Material> materialRepository = new MaterialRepositoryImpl();
    @Override
    public Optional<Course> getById(UUID id) {
        try {
            Optional<Course> courseOptional = courseRepository.getObjectById(id);
            if (courseOptional.isPresent()) {
                Course course = courseOptional.get();

                List<Student> students = studentRepository.getAllStudentsByCourseId(id);
                List<Material> materials = materialRepository.getAllMaterialsByCourseId(id);

                course.setEnrolledStudents(students);
                course.setMaterialList(materials);

                return Optional.of(course);
            }
            return Optional.empty();
        } catch (ObjectNotFoundException e) {
            LogServiceImpl.getInstance().log(Level.WARNING, e.getMessage());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Course> getAllItems() {
        List<Course> courseList = courseRepository.getAll();

        Iterator<Course> courseIterator = courseList.iterator();
        while (courseIterator.hasNext()) {
            Course course = courseIterator.next();

            List<Student> students = studentRepository.getAllStudentsByCourseId(course.getCourseId());
            List<Material> materials = materialRepository.getAllMaterialsByCourseId(course.getCourseId());

            course.setEnrolledStudents(students);
            course.setMaterialList(materials);
        }
        return courseList;
    }

    @Override
    public void addOnlyOne(Course newObject) {
        try {
            courseRepository.addNewObject(newObject);
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void addMany(List<Course> objectList) {
        try {
            courseRepository.addAllFromGivenList(objectList);
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void removeById(UUID id) {
        courseRepository.deleteObjectById(id);
    }

    @Override
    public void updateById(UUID id, Course newObject) {
        courseRepository.updateObjectById(id, newObject);
    }

    @Override
    public List<Course> getCoursesByTeacherId(UUID teacherId) {
        List<Course> courseList = courseRepository.getAllCoursesByTeacherId(teacherId);

        Iterator<Course> courseIterator = courseList.iterator();
        while (courseIterator.hasNext()) {
            Course course = courseIterator.next();

            List<Student> students = studentRepository.getAllStudentsByCourseId(course.getCourseId());
            List<Material> materials = materialRepository.getAllMaterialsByCourseId(course.getCourseId());

            course.setEnrolledStudents(students);
            course.setMaterialList(materials);
        }
        return courseList;
    }

    @Override
    public List<Course> getCoursesByStudentId(UUID studentId) {
        List<Course> courseList = courseRepository.getAllCoursesByStudentId(studentId);

        Iterator<Course> courseIterator = courseList.iterator();
        while (courseIterator.hasNext()) {
            Course course = courseIterator.next();

            List<Student> students = studentRepository.getAllStudentsByCourseId(course.getCourseId());
            List<Material> materials = materialRepository.getAllMaterialsByCourseId(course.getCourseId());

            course.setEnrolledStudents(students);
            course.setMaterialList(materials);
        }
        return courseList;
    }
}
