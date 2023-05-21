package ro.pao.application;

import ro.pao.gateways.Requests;
import ro.pao.model.*;
import ro.pao.model.abstracts.Material;
import ro.pao.model.enums.Discipline;
import ro.pao.model.enums.DocumentType;
import ro.pao.model.enums.TestType;
import ro.pao.model.sealed.Student;
import ro.pao.model.sealed.Teacher;
import ro.pao.model.sealed.User;
import ro.pao.service.*;
import ro.pao.service.impl.*;
import ro.pao.strategyPattern.CalculateAverageGradeWeighted;
import ro.pao.threads.StudentPrinterThread;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;


public class Menu {
    private static Menu INSTANCE;

    Random random = new Random();

    private final UserService<User> userService = new UserServiceImpl();
    private final TeacherService teacherService = new TeacherServiceImpl();
    private final StudentService studentService = new StudentServiceImpl();
    private final GradeService gradeService = new GradeServiceImpl();
    private final CourseService courseService = new CourseServiceImpl();
    private final MaterialService<Material> materialService = new MaterialServiceImpl();
    private final DocumentService documentService = new DocumentServiceImpl();
    private final TestService testService = new TestServiceImpl();
    private final VideoService videoService = new VideoServiceImpl();

    private final Requests requests = new Requests();


    public static Menu getInstance() {
        return (INSTANCE == null ? new Menu() : INSTANCE);
    }

    public void demoOnGateway() {
        requests.saveRequestInfo();
    }

    public void demoOnInsertingUsers() {
        System.out.println("-----Adding different users-----\n");

        Teacher teacher = Teacher.builder()
                .id(UUID.randomUUID())
                .firstName("Andrei")
                .lastName("Matei")
                .email("andrei.matei@gmail")
                .password("vreaParola!")
                .degree("masterand")
                .build();

        userService.addOnlyOne(teacher);

        List<Teacher> teachers = List.of(Teacher.builder()
                        .id(UUID.randomUUID())
                        .firstName("Elena")
                        .lastName("Atanasiu")
                        .email("ella.gmail@gmail")
                        .password("PpaarraRoLa")
                        .degree("lector")
                        .build(),
                Teacher.builder()
                        .id(UUID.randomUUID())
                        .firstName("Alin")
                        .lastName("Mariana")
                        .email("alinsaumariana@gmail")
                        .password("cumTeCheamaWai?")
                        .degree("lector")
                        .build());

        teacherService.addMany(teachers);

        Student student = Student.builder()
                .id(UUID.randomUUID())
                .firstName("Ioan")
                .lastName("Numedefamilie")
                .email("ion.numedefamili@gmail")
                .password("WhiedffweeejaW")
                .build();

        studentService.addOnlyOne(student);

        List<Student> students = List.of(Student.builder()
                        .id(UUID.randomUUID())
                        .firstName("Teodora")
                        .lastName("Teodorescu")
                        .email("teo.teo@gmail")
                        .password("PdfsarollllaLa")
                        .build(),
                Student.builder()
                        .id(UUID.randomUUID())
                        .firstName("Marian")
                        .lastName("Viteza")
                        .email("marian.02@gmail")
                        .password("1AAAAdfAAParoLA")
                        .build(),
                Student.builder()
                        .id(UUID.randomUUID())
                        .firstName("Miruna")
                        .lastName("Miru")
                        .email("mm@gmail")
                        .password("1PUnCevaSiNUdaBine")
                        .build());
        studentService.addMany(students);
    }

    public void demoOnInsertingGrades() {
        System.out.println("-----Adding grades-----\n");

        List<Student> studentList = studentService.getAllItems();

        Grade grade = Grade.builder()
                .gradeId(UUID.randomUUID())
                .studentId(studentList.get(random.nextInt(studentList.size())).getId())
                .grade(9.7)
                .weight(0.5)
                .build();
        gradeService.addOnlyOne(grade);
        grade = Grade.builder()
                .gradeId(UUID.randomUUID())
                .studentId(studentList.get(random.nextInt(studentList.size())).getId())
                .grade(7.44)
                .weight(0.6)
                .build();
        gradeService.addOnlyOne(grade);

        List<Grade> gradeList = List.of(Grade.builder()
                        .gradeId(UUID.randomUUID())
                        .studentId(studentList.get(random.nextInt(studentList.size())).getId())
                        .grade(10.00)
                        .weight(0.9)
                        .build(),
                Grade.builder()
                        .gradeId(UUID.randomUUID())
                        .studentId(studentList.get(random.nextInt(studentList.size())).getId())
                        .grade(9.88)
                        .weight(0.1)
                        .build(),
                Grade.builder()
                        .gradeId(UUID.randomUUID())
                        .studentId(studentList.get(random.nextInt(studentList.size())).getId())
                        .grade(4.99)
                        .weight(0.8)
                        .build());

        gradeService.addMany(gradeList);
    }

    public void demoOnStrategyDesignPattern() {

        System.out.println("Demonstration of strategy design pattern to calculate average grade for each student.");

        System.out.println("---NOT WEIGHTED---\n");
        List<Student> studentList = studentService.getAllItems();
        studentList.forEach(std -> System.out.println(std.getFirstName() + " " + std.getAverageGrade()));

        System.out.println("---WEIGHTED---\n");

        studentList.forEach(std -> std.setAverageGradeStrategy(new CalculateAverageGradeWeighted()));
        studentList.forEach(std -> studentService.updateStudentGradeList(std.getId()));

        studentList.forEach(std -> System.out.println(std.getFirstName() + " " + std.getAverageGrade()));
    }

    public void demoOnInsertingCourses() {
        System.out.println("-----Adding courses-----\n");

        List<Teacher> teacherList = teacherService.getAllItems();

        Course course = Course.builder()
                .courseId(UUID.randomUUID())
                .title("Chimie organica")
                .discipline(Discipline.CHEMISTRY)
                .teacherId(teacherList.get(random.nextInt(teacherList.size())).getId())
                .build();

        courseService.addOnlyOne(course);

        List<Course> courseList = List.of(Course.builder()
                .courseId(UUID.randomUUID())
                .title("Desen tehnic")
                .discipline(Discipline.MATHEMATICS)
                .teacherId(teacherList.get(random.nextInt(teacherList.size())).getId())
                .build(),
                Course.builder()
                        .courseId(UUID.randomUUID())
                        .title("Quantum physics")
                        .discipline(Discipline.PHYSICS)
                        .teacherId(teacherList.get(random.nextInt(teacherList.size())).getId())
                        .build());

        courseService.addMany(courseList);
    }

    public void demoOnEnrollingStudents() {
        System.out.println("-----Enrolling students-----\n");

        List<Student> studentList = studentService.getAllItems();
        List<Course> courseList = courseService.getAllItems();

        studentService.enrollStudent(studentList.get(random.nextInt(studentList.size())).getId(), courseList.get(random.nextInt(courseList.size())).getCourseId());

        studentService.enrollStudent(studentList.get(random.nextInt(studentList.size())).getId(), courseList.get(random.nextInt(courseList.size())).getCourseId());

        studentService.enrollStudent(studentList.get(random.nextInt(studentList.size())).getId(), courseList.get(random.nextInt(courseList.size())).getCourseId());

        studentService.enrollStudent(studentList.get(random.nextInt(studentList.size())).getId(), courseList.get(random.nextInt(courseList.size())).getCourseId());
    }

    public void demoOnInsertingMaterials() {
        System.out.println("-----Adding different materials-----\n");

        List<Course> courseList = courseService.getAllItems();

        Document document = Document.builder()
                .id(UUID.randomUUID())
                .creationTime(LocalDateTime.now().minus(31, ChronoUnit.DAYS))
                .title("Theory of nothing")
                .description("In this material you will learn nothing useful.")
                .courseId(courseList.get(random.nextInt(courseList.size())).getCourseId())
                .documentType(DocumentType.COURSE)
                .build();

        materialService.addOnlyOne(document);


        List<Document> documentList = List.of(Document.builder()
                                .id(UUID.randomUUID())
                                .creationTime(LocalDateTime.now().minus(1, ChronoUnit.MONTHS))
                                .title("Fractal numbers")
                                .description("In this material you will find some exercises that you have to solve using recursion.")
                                .courseId(courseList.get(random.nextInt(courseList.size())).getCourseId())
                                .documentType(DocumentType.PRACTICE)
                                .build(),
                        Document.builder()
                                .id(UUID.randomUUID())
                                .creationTime(LocalDateTime.now().minus(891263, ChronoUnit.MINUTES))
                                .title("Drawing shadows")
                                .description("In this material you will learn how to draw shadows.")
                                .courseId(courseList.get(random.nextInt(courseList.size())).getCourseId())
                                .documentType(DocumentType.IMPLEMENTATION)
                                .build());

        documentService.addMany(documentList);

        Video video = Video.builder()
                .id(UUID.randomUUID())
                .creationTime(LocalDateTime.now().minus(1, ChronoUnit.YEARS))
                .title("Visualization of something")
                .description("This is a video where you can find a short video I think.")
                .courseId(courseList.get(random.nextInt(courseList.size())).getCourseId())
                .duration(LocalTime.parse("00:05:05", DateTimeFormatter.ISO_TIME))
                .build();

        videoService.addOnlyOne(video);


       List<Video> videoList = List.of(Video.builder()
                                .id(UUID.randomUUID())
                                .creationTime(LocalDateTime.now().minus(121, ChronoUnit.HOURS))
                                .title("How to catch your shadow")
                                .description("In this video you will find absolutely nothing.")
                                .courseId(courseList.get(random.nextInt(courseList.size())).getCourseId())
                                .duration(LocalTime.parse("01:22:12", DateTimeFormatter.ISO_TIME))
                                .build(),
                        Video.builder()
                                .id(UUID.randomUUID())
                                .creationTime(LocalDateTime.now())
                                .title("The cat in the box")
                                .description("In this video you will see something amazing.")
                                .courseId(courseList.get(random.nextInt(courseList.size())).getCourseId())
                                .duration(LocalTime.parse("00:14:59", DateTimeFormatter.ISO_TIME))
                                .build());

        videoService.addMany(videoList);

        Test test = Test.builder()
                .id(UUID.randomUUID())
                .creationTime(LocalDateTime.now().minus(12312, ChronoUnit.HALF_DAYS))
                .title("Complex numbers")
                .description("Here is a test that contains exercises with complex numbers.")
                .courseId(courseList.get(random.nextInt(courseList.size())).getCourseId())
                .testType(TestType.EXAM)
                .build();

        materialService.addOnlyOne(test);

        List<Test> testList = List.of(Test.builder()
                                .id(UUID.randomUUID())
                                .creationTime(LocalDateTime.now().minus(66, ChronoUnit.DAYS))
                                .title("Recursion")
                                .description("Here is a funny and simple quiz about past recursion.")
                                .courseId(courseList.get(random.nextInt(courseList.size())).getCourseId())
                                .testType(TestType.QUIZ)
                                .build(),
                        Test.builder()
                                .id(UUID.randomUUID())
                                .creationTime(LocalDateTime.now())
                                .title("ML")
                                .description("This test aims to verify If you can have a negative score.")
                                .courseId(courseList.get(random.nextInt(courseList.size())).getCourseId())
                                .testType(TestType.EXAM)
                                .build());

        testService.addMany(testList);
    }

    public void demoOnThreads() {
        System.out.println("-----Demo on threads-----\n");
        System.out.println("-----Demo on getting a list of students with a higher average grade than a given one-----\n");

        StudentPrinterThread printerThreadHigher = new StudentPrinterThread(studentService.getStudentsWithHigherGrade(7.00));
        Thread threadHigher = new Thread(printerThreadHigher);
        threadHigher.start();
        try {
            threadHigher.join();
        } catch (InterruptedException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        System.out.println("\n-----Demo on getting a list of students with a lower average grade than a given one-----\n");

        StudentPrinterThread printerThreadLower = new StudentPrinterThread(studentService.getStudentsWithLowerGrade(7.00));
        Thread threadLower = new Thread(printerThreadLower);
        threadLower.start();
        try {
            threadLower.join();
        } catch (InterruptedException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    public void demoOnIterator() {
        System.out.println("-----Demo on retrieving the video with the maximum duration, using a function that includes an iterator-----");

        Optional<Video> videoOptional = videoService.getMaxDurationVideo();

        if (videoOptional.isPresent()) {
            Video video = videoOptional.get();
            System.out.println(video.getTitle() + " ---- " + video.getDuration());
        }

        System.out.println("-----Demo on iterator, and deleting objects from database-----");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        System.out.println("\n  Before deleting:");
        videoService.getAllItems().forEach(vid -> System.out.println("Video: " + vid.getTitle() + " ---- " + vid.getDuration().format(formatter)));

        Iterator<Video> videoIterator = videoService.getAllItems().iterator();
        while (videoIterator.hasNext()) {
            Video vid = videoIterator.next();

            if (vid.getDuration().isAfter(LocalTime.parse("00:30:00", DateTimeFormatter.ISO_TIME))) {
                videoService.removeById(vid.getId());
                videoIterator.remove();
            }
        }

        System.out.println("\n  After deleting:");
        videoService.getAllItems().forEach(vid -> System.out.println("Video: " + vid.getTitle() + " ---- " + vid.getDuration().format(formatter)));
    }
    public void demoOnLoggingErrors() {
        studentService.getById(UUID.randomUUID());

        documentService.getById(UUID.randomUUID());

        Teacher teacher = Teacher.builder()
                .id(UUID.randomUUID())
                .firstName("Ionel")
                .lastName("Ionescu")
                .email("ionel.ionescuuu@gmail")
                .password("TesteazaLoggerul")
                .degree("masterand")
                .build();

        userService.addOnlyOne(teacher);

        teacher = Teacher.builder()
                .id(UUID.randomUUID())
                .firstName("Ionel")
                .lastName("Ionescu")
                .email("ionel.ionescuuu@gmail")
                .password("TesteazaLoggerul")
                .degree("doctorand")
                .build();

        teacherService.addOnlyOne(teacher);

        Student student = Student.builder()
                .id(UUID.randomUUID())
                .firstName("Mariana")
                .lastName("MariaairaM")
                .email("mariiana@gmail")
                .password("pal")
                .build();

        studentService.addOnlyOne(student);
    }
    public void demo() {
        System.out.println("\n\n-----Demo on the joins and usage of database-----");

        List<Course> courseList = courseService.getCoursesByTeacherId(UUID.fromString("c90af071-ffb4-474b-b3f3-28ac4f85df93"));

        courseList.forEach(System.out::println);
    }
}
