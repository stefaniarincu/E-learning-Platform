package ro.pao.application;

import ro.pao.model.materials.Document;
import ro.pao.model.materials.Test;
import ro.pao.model.materials.Video;
import ro.pao.model.materials.abstracts.Material;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.materials.enums.DocumentType;
import ro.pao.model.materials.enums.TestType;
import ro.pao.model.materials.records.Statistic;
import ro.pao.model.users.Student;
import ro.pao.model.users.Teacher;
import ro.pao.service.materials.DocumentService;
import ro.pao.service.materials.MaterialService;
import ro.pao.service.materials.TestService;
import ro.pao.service.materials.VideoService;
import ro.pao.service.materials.impl.DocumentServiceImpl;
import ro.pao.service.materials.impl.MaterialServiceImpl;
import ro.pao.service.materials.impl.TestServiceImpl;
import ro.pao.service.materials.impl.VideoServiceImpl;
import ro.pao.service.users.StudentService;
import ro.pao.service.users.TeacherService;
import ro.pao.service.users.UserService;
import ro.pao.service.users.impl.StudentServiceImpl;
import ro.pao.service.users.impl.TeacherServiceImpl;
import ro.pao.service.users.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Menu {
    private static Menu INSTANCE;

    private final DocumentService documentService = new DocumentServiceImpl();

    private final VideoService videoService = new VideoServiceImpl();

    private final TestService testService = new TestServiceImpl();

    private final MaterialService materialService = new MaterialServiceImpl();

    private final StudentService studentService = new StudentServiceImpl();

    private final TeacherService teacherService = new TeacherServiceImpl();

    private final UserService userService = new UserServiceImpl();

    public static Menu getInstance() {
        return (INSTANCE == null ? new Menu() : INSTANCE);
    }

    public void demoOnDocument() {

        String intro = "\n\n-----Performing different operations on a Document object-----";

        System.out.println(intro);


        Document document = Document.builder()
                .id(UUID.randomUUID())
                .creationTime(LocalDateTime.now().minus(31, ChronoUnit.DAYS))
                .discipline(Discipline.MATHEMATICS)
                .title("Complex Numbers")
                .description("In this material you will learn new information about complex numbers and their utility.")
                .documentType(DocumentType.COURSE)
                .build();

        documentService.addOnlyOne(document);


        Map<UUID, Document> documents = Stream.of(Document.builder()
                                .id(UUID.randomUUID())
                                .creationTime(LocalDateTime.now().minus(1, ChronoUnit.MONTHS))
                                .discipline(Discipline.INFORMATICS)
                                .title("Recursion")
                                .description("In this material you will find some exercises that you have to solve using recursion.")
                                .documentType(DocumentType.PRACTICE)
                                .build(),
                Document.builder()
                        .id(UUID.randomUUID())
                        .creationTime(LocalDateTime.now().minus(891263, ChronoUnit.MINUTES))
                        .discipline(Discipline.PHYSICS)
                        .title("Newton's laws")
                        .description("In this material you will find the enunciation of Newton's laws and their demonstrations.")
                        .documentType(DocumentType.COURSE)
                        .build())
                .collect(Collectors.toMap(Document::getId, Function.identity()));

        documentService.addMany(documents);


        System.out.println("\nFor example: we can print every existing document type along with the documents that have that type.");

        for(DocumentType documentType: DocumentType.values()) {

            documents = documentService.getDocumentsByType(documentType);

            System.out.println(documentType.toString() + ":");

            if(documents.isEmpty()) {
                System.out.println("     No documents here!");
            }
            else {
                documents.forEach((key, value) -> System.out.println("     " + value.getTitle()));
            }
        }


        System.out.println("\n\nAnother example: we can print all the documents that belong to a certain discipline.");

        documents = documentService.getMaterialsByDiscipline(Discipline.INFORMATICS);

        System.out.println(Discipline.INFORMATICS + ":");

        if(documents.isEmpty()) {
            System.out.println("     No documents here!");
        }
        else {
            documents.forEach((key, value) -> System.out.println("     " + value.getTitle()));
        }
    }

    public void demoOnVideo() {

        String intro = "\n\n-----Performing different operations on a Video object-----";

        System.out.println(intro);


        Video video = Video.builder()
                .id(UUID.randomUUID())
                .creationTime(LocalDateTime.now().minus(1, ChronoUnit.YEARS))
                .discipline(Discipline.CHEMISTRY)
                .title("Visualization of atoms")
                .description("This is a video where you can find a short visualization atoms.")
                .duration(LocalTime.parse("00:12:32", DateTimeFormatter.ISO_TIME))
                .build();

        videoService.addOnlyOne(video);


        Map<UUID, Video> videos = Stream.of(Video.builder()
                                .id(UUID.randomUUID())
                                .creationTime(LocalDateTime.now().minus(121, ChronoUnit.HOURS))
                                .discipline(Discipline.PHYSICS)
                                .title("Natural phenomena")
                                .description("In this video you will find a video which explains how some of the natural phenomena appears.")
                                .duration(LocalTime.parse("00:22:12", DateTimeFormatter.ISO_TIME))
                                .build(),
                        Video.builder()
                                .id(UUID.randomUUID())
                                .creationTime(LocalDateTime.now())
                                .discipline(Discipline.INFORMATICS)
                                .title("Fractal numbers")
                                .description("In this video you will find the geometric representations of the fractal numbers and the short implementation of the recursive solutions for those drawings.")
                                .duration(LocalTime.parse("00:59:59", DateTimeFormatter.ISO_TIME))
                                .build())
                .collect(Collectors.toMap(Video::getId, Function.identity()));

        videoService.addMany(videos);


        System.out.println("\nFor example: we can print all the videos that take less than the time given as parameter.");

        System.out.println("Less than 32 minutes:");

        videos = videoService.getVideosByMaxDuration(LocalTime.parse("00:30:00", DateTimeFormatter.ISO_TIME));

        if(videos.isEmpty()) {
            System.out.println("     No videos here!");
        }
        else {
            videos.forEach((key, value) -> System.out.println("     " + value.getTitle()));
        }


        System.out.println("\n\nAnother example: we can print all the videos that belong to a certain discipline.");

        videos = videoService.getMaterialsByDiscipline(Discipline.CHEMISTRY);

        System.out.println(Discipline.CHEMISTRY + ":");

        if(videos.isEmpty()) {
            System.out.println("     No videos here!");
        }
        else {
            videos.forEach((key, value) -> System.out.println("     " + value.getTitle()));
        }


        System.out.println("\n\nAnother example: before editing a video.");

        videoService.getAllItems().forEach((key, value) -> System.out.println("     " + value.getTitle()));


        Optional<Video> optionalVideo = videoService.getById(video.getId());

        if(optionalVideo.isPresent()) {

            Video newVideo = optionalVideo.get();

            newVideo.setTitle("New title cuz I got bored!");

            videoService.modifyById(newVideo.getId(), newVideo);

            System.out.println("\nAfter editing the video: " + video.getTitle() + ".");

            videoService.getAllItems().forEach((key, value) -> System.out.println("     " + value.getTitle()));
        }
        else {
            System.out.println("\nThe video that you wanted to update was not found collection of videos.");
        }
    }

    public void demoOnTest() {

        String intro = "\n\n-----Performing different operations on a Test object-----";

        System.out.println(intro);

        Test test = Test.builder()
                .id(UUID.randomUUID())
                .creationTime(LocalDateTime.now().minus(12312, ChronoUnit.HALF_DAYS))
                .discipline(Discipline.ENGLISH)
                .title("Past Tenses")
                .description("Here is a test that contains exercises with past tenses.")
                .testType(TestType.EXAM)
                .build();

        testService.addOnlyOne(test);

        Map<UUID, Test> tests = Stream.of(Test.builder()
                                .id(UUID.randomUUID())
                                .creationTime(LocalDateTime.now().minus(66, ChronoUnit.DAYS))
                                .discipline(Discipline.ENGLISH)
                                .title("Past tenses")
                                .description("Here is a funny and simple quiz about past tenses.")
                                .testType(TestType.QUIZ)
                                .build(),
                        Test.builder()
                                .id(UUID.randomUUID())
                                .creationTime(LocalDateTime.now())
                                .discipline(Discipline.INFORMATICS)
                                .title("Basic coding skills")
                                .description("This test aims to verify all your knowledge from the past few courses.")
                                .testType(TestType.EXAM)
                                .build())
                .collect(Collectors.toMap(Test::getId, Function.identity()));

        testService.addMany(tests);

        System.out.println("\nFor example: we can print all the tests that belongs to a specified Discipline.");

        System.out.println(Discipline.ENGLISH + ":");

        tests = testService.getMaterialsByDiscipline(Discipline.ENGLISH);

        if(tests.isEmpty()) {
            System.out.println("     No test here!");
        }
        else {
            tests.forEach((key, value) -> System.out.println("     " + value.getTitle()));
        }

        System.out.println("\n\nAnother example: we can print all the tests that have a given type.");

        System.out.println(TestType.EXAM + ":");

        tests = testService.getTestsByType(TestType.EXAM);

        if(tests.isEmpty()) {
            System.out.println("     No tests here!");
        }
        else {
            tests.forEach((key, value) -> System.out.println("     " + value.getTitle()));
        }

        System.out.println("\n\nAnother example: before deleting a test.");
        testService.getAllItems().forEach((key, value) -> System.out.println("     " + value.getTitle()));

        Optional<Test> optionalTest = testService.getById(UUID.randomUUID());

        if(optionalTest.isPresent()) {

            testService.removeById(optionalTest.get().getId());

            System.out.println("\nAfter deleting the test: " + test.getTitle() + ".");

            testService.getAllItems().forEach((key, value) -> System.out.println("     " + value.getTitle()));
        }
        else {
            System.out.println("\nThe test you wanted to delete was not found!");
        }

        System.out.println("\nAnother example is that we can add statistic to a given test.");

        Statistic statistic = new Statistic(test, "Difficult", 33.33);
        System.out.println("    " + statistic);
    }

    public void demoOnAllMaterials() {

        String intro = "\n\n-----Performing different operations on all kind of materials-----";

        System.out.println(intro);

        materialService.addAllKindOfMaterials();

        System.out.println("For example: we can iterate through the map of all the materials added on the website.");

        materialService.getAllItems().forEach((key, value) -> System.out.println(value.getTitle()));
    }

    public void demoOnStudents() {

        String intro = "\n\n-----Performing different operations on a Student object-----";

        System.out.println(intro);


        List<Material> materialList1 = new ArrayList<>();

        videoService.getMaterialsByDiscipline(Discipline.MATHEMATICS).forEach((key, video) -> materialList1.add(video));

        testService.getTestsByType(TestType.EXAM).forEach((key, test) -> materialList1.add(test));

        documentService.getMaterialsByDiscipline(Discipline.INFORMATICS).forEach((key, doc) -> materialList1.add(doc));


        List<Material> materialList2 = new ArrayList<>();

        videoService.getMaterialsByDiscipline(Discipline.CHEMISTRY).forEach((key, video) -> materialList2.add(video));

        testService.getTestsByType(TestType.QUIZ).forEach((key, test) -> materialList2.add(test));

        documentService.getDocumentsByType(DocumentType.COURSE).forEach((key, doc) -> materialList2.add(doc));


        List<Material> materialList3 = new ArrayList<>();

        videoService.getMaterialsByDiscipline(Discipline.CHEMISTRY).forEach((key, video) -> materialList3.add(video));

        documentService.getDocumentsByType(DocumentType.COURSE).forEach((key, doc) -> materialList3.add(doc));


        TreeMap<Discipline, List<Double>> gradesTM0 = new TreeMap<>();

        gradesTM0.put(Discipline.CHEMISTRY, new ArrayList<>(Arrays.asList(6.0, 7.50)));

        gradesTM0.put(Discipline.INFORMATICS, new ArrayList<>(Arrays.asList(6.0, 7.50)));

        gradesTM0.put(Discipline.MATHEMATICS, new ArrayList<>(Arrays.asList(5.0, 5.0)));


        TreeMap<Discipline, List<Double>> gradesTM1 = new TreeMap<>();

        gradesTM1.put(Discipline.ENGLISH, new ArrayList<>(Arrays.asList(9.6, 6.4, 10.0)));

        gradesTM1.put(Discipline.MATHEMATICS, new ArrayList<>(Arrays.asList(8.6, 6.4)));

        gradesTM1.put(Discipline.CHEMISTRY, new ArrayList<>(Arrays.asList(5.0)));


        TreeMap<Discipline, List<Double>> gradesTM2 = new TreeMap<>();

        gradesTM2.put(Discipline.PHYSICS, new ArrayList<>(Arrays.asList(10.0, 8.50, 10.0)));

        gradesTM2.put(Discipline.MATHEMATICS, new ArrayList<>(Arrays.asList(8.6, 6.4, 10.0)));

        gradesTM2.put(Discipline.INFORMATICS, new ArrayList<>(Arrays.asList(5.0, 10.0)));


        TreeMap<Discipline, List<Double>> gradesTM3 = new TreeMap<>();

        gradesTM3.put(Discipline.CHEMISTRY, new ArrayList<>(Arrays.asList(10.0, 8.50)));

        gradesTM3.put(Discipline.INFORMATICS, new ArrayList<>(Arrays.asList(6.0, 9.50)));

        gradesTM3.put(Discipline.MATHEMATICS, new ArrayList<>(Arrays.asList(5.0, 5.0)));


        Student student = Student.builder()
                .id(UUID.randomUUID())
                .firstName("Ion")
                .lastName("Ionescu")
                .email("ion.ionescu@gmail")
                .password("WhiejaW")
                .materials(materialList1)
                .build();

        studentService.addOnlyOne(student);

        LinkedHashMap<UUID, Student> students = Stream.of(Student.builder()
                                .id(UUID.randomUUID())
                                .firstName("Maria")
                                .lastName("Marinescu")
                                .email("maria.marinescu@gmail")
                                .password("ParoLa")
                                .materials(materialList2)
                                .grades(gradesTM1)
                                .build(),
                        Student.builder()
                                .id(UUID.randomUUID())
                                .firstName("Matei")
                                .lastName("Marinescu")
                                .email("matei.marinescu@gmail")
                                .password("ParoLA")
                                .materials(materialList3)
                                .grades(gradesTM0)
                                .build(),
                        Student.builder()
                                .id(UUID.randomUUID())
                                .firstName("Clara")
                                .lastName("Clarescu")
                                .email("cc@gmail")
                                .password("PUnCeva")
                                .materials(materialList2)
                                .grades(gradesTM2)
                                .build(),
                        Student.builder()
                                .id(UUID.randomUUID())
                                .firstName("Sara")
                                .lastName("Pitco")
                                .email("s.pitco@gmail")
                                .password("PunAtcV")
                                .materials(materialList3)
                                .grades(gradesTM3)
                                .build())
                .collect(Collectors.toMap(Student::getId, Function.identity(), (s1, s2) -> s1, LinkedHashMap::new));

        studentService.addMany(students);


        System.out.println("\nFor example: we can try to add a new student.\n  Before trying to add:");

        studentService.getAllItems().forEach((key, std) -> System.out.println("Student: " + std.getFirstName() + " " + std.getLastName()));

        studentService.addOnlyOne(student);

        System.out.println("\n  After trying to add:");

        studentService.getAllItems().forEach((key, std) -> System.out.println("Student: " + std.getFirstName() + " " + std.getLastName()));


        System.out.println("\nAnother example: we can display all the students that used a resource from a specified discipline.");

        Map<UUID, Student> studentByDiscipline = studentService.getUsersByDiscipline(Discipline.MATHEMATICS);

        System.out.println("\n  For discipline: " + Discipline.MATHEMATICS);

        if (studentByDiscipline != null) {
            studentByDiscipline.forEach((key, std) -> System.out.println(std));
        } else {
            System.out.println("There are no students!");
        }


        System.out.println("\nAnother example: we can get all the students that have a lower or a higher grade than a specified student.");

        Optional<Student> optionalStudent = studentService.getByEmail("maria.marinescu@gmail");

        if (optionalStudent.isPresent()) {
            System.out.println("\nStudents with higher grade than " + optionalStudent.get().getAverageGrade());

            List<Student> studentByGrade = studentService.getStudentsWithHigherGrade(optionalStudent.get().getAverageGrade());

            if (!studentByGrade.isEmpty()) {
                studentByGrade.forEach(std -> System.out.println("Student: " + std.getFirstName() + " " + std.getLastName() + " -- " + std.getAverageGrade()));
            } else {
                System.out.println("There are no students!");
            }

            System.out.println("\nStudents with lower grade than " + optionalStudent.get().getAverageGrade());

            studentByGrade = studentService.getStudentsWithLowerGrade(optionalStudent.get().getAverageGrade());

            if (!studentByGrade.isEmpty()) {
                studentByGrade.forEach(std -> System.out.println("Student: " + std.getFirstName() + " " + std.getLastName() + " -- " + std.getAverageGrade()));
            } else {
                System.out.println("There are no students!");
            }
        }
    }

    public void demoOnTeachers() {

        String intro = "\n\n-----Performing different operations on a Teacher object-----";

        System.out.println(intro);


        List<Discipline> teachCourses1 = List.of(Discipline.MATHEMATICS, Discipline.INFORMATICS, Discipline.PHYSICS);

        List<Discipline> teachCourses2 = List.of(Discipline.CHEMISTRY, Discipline.PHYSICS);

        List<Discipline> teachCourses3 = List.of(Discipline.ENGLISH);


        Teacher teacher = Teacher.builder()
                .id(UUID.randomUUID())
                .firstName("Radu")
                .lastName("Popescu")
                .email("radu.pop@gmail")
                .password("WhIejaW")
                .teachCourses(teachCourses1)
                .build();

        teacherService.addOnlyOne(teacher);

        LinkedHashMap<UUID, Teacher> teachers = Stream.of(Teacher.builder()
                                .id(UUID.randomUUID())
                                .firstName("Elena")
                                .lastName("Enescu")
                                .email("ella.enescu@gmail")
                                .password("PaRoLa")
                                .teachCourses(teachCourses2)
                                .build(),
                        Teacher.builder()
                                .id(UUID.randomUUID())
                                .firstName("Alin")
                                .lastName("Alina")
                                .email("alin.al@gmail")
                                .password("PARoLA")
                                .teachCourses(teachCourses3)
                                .build())
                .collect(Collectors.toMap(Teacher::getId, Function.identity(), (s1, s2) -> s1, LinkedHashMap::new));

        teacherService.addMany(teachers);


        System.out.println("\nFor example: we can try to add a new teacher.\n  Before trying to add:");

        teacherService.getAllItems().forEach((key, tch) -> System.out.println("Teacher: " + tch.getFirstName() + " " + tch.getLastName()));

        List<Discipline> teachCourses4 = List.of(Discipline.ENGLISH, Discipline.INFORMATICS);

        teacherService.addOnlyOne(Teacher.builder()
                .id(UUID.randomUUID())
                .firstName("Carmen")
                .lastName("Ceva")
                .email("carmen.ceva@gmail")
                .password("HAshSet")
                .teachCourses(teachCourses4)
                .build());

        System.out.println("\n  After trying to add:");

        teacherService.getAllItems().forEach((key, tch) -> System.out.println("Teacher: " + tch.getFirstName() + " " + tch.getLastName()));


        System.out.println("\nAnother example: we can display all the teachers that teach a specified discipline.");

        Map<UUID, Teacher> teacherByDiscipline = teacherService.getUsersByDiscipline(Discipline.MATHEMATICS);

        System.out.println("\n  For discipline: " + Discipline.MATHEMATICS);

        if (teacherByDiscipline != null) {
            teacherByDiscipline.forEach((key, tch) -> System.out.println("Teacher: " + tch.getFirstName() + " " + tch.getLastName()));
        } else {
            System.out.println("There are no teachers!");
        }
    }

    public void demoOnAllUsers() {

        String intro = "\n\n-----Performing different operations on all kind of users-----";

        System.out.println(intro);

        userService.addAllKindOfUsers();

        System.out.println("For example: we can iterate through the map of all the users that have an account on the website.");

        userService.getAllItems().forEach((key, user) -> System.out.println("User: " + user.getFirstName() + " " + user.getLastName()));
    }
}
