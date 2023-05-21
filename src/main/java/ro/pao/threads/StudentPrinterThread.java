package ro.pao.threads;

import ro.pao.model.sealed.Student;

import java.util.List;

public class StudentPrinterThread implements Runnable {
    private List<Student> students;

    public StudentPrinterThread(List<Student> students) {
        this.students = students;
    }

    @Override
    public void run() {
        students.forEach(std -> System.out.println(std.getFirstName() + " " + std.getLastName() + " --- " + std.getAverageGrade()));
    }
}
