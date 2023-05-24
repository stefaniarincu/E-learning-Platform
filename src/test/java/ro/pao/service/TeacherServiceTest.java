package ro.pao.service;

import org.junit.jupiter.api.Test;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.sealed.Teacher;
import ro.pao.service.impl.TeacherServiceImpl;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeacherServiceTest {
    private final TeacherService teacherService  = new TeacherServiceImpl();
    @Test
    void whenGivenTeacher_thenElementIsAdd() throws SQLException, ObjectNotFoundException {
        Teacher teacher = Teacher.builder()
                .id(UUID.randomUUID())
                .firstName("PrenumeNou")
                .lastName("NumeNou")
                .email("profemail@gmail.com")
                .password("minimumlength8")
                .degree("nuAmPusDegreeValid")
                .build();

        teacherService.addOnlyOne(teacher);

        assertEquals(1, teacherService.getTeachersByDegree(teacher.getDegree()).size());
    }
}
