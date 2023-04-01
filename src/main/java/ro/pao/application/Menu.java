package ro.pao.application;

import ro.pao.model.materials.Document;
import ro.pao.model.materials.Test;
import ro.pao.model.materials.Video;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.materials.enums.DocumentType;
import ro.pao.model.materials.enums.TestType;
import ro.pao.service.materials.DocumentService;
import ro.pao.service.materials.MaterialService;
import ro.pao.service.materials.TestService;
import ro.pao.service.materials.VideoService;
import ro.pao.service.materials.impl.DocumentServiceImpl;
import ro.pao.service.materials.impl.MaterialServiceImpl;
import ro.pao.service.materials.impl.TestServiceImpl;
import ro.pao.service.materials.impl.VideoServiceImpl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    public static Menu getInstance() {
        return (INSTANCE == null ? new Menu() : INSTANCE);
    }

    public void demoOnDocument() {

        String intro = "\n\n-----Performing different operations on a Document object-----";

        System.out.println(intro);


        Document document = Document.builder()
                .id(UUID.randomUUID())
                .creationTime(LocalDateTime.now())
                .discipline(Discipline.MATHEMATICS)
                .title("Complex Numbers")
                .description("In this material you will learn new information about complex numbers and their utility.")
                .documentType(DocumentType.COURSE)
                .build();

        documentService.addOnlyOne(document);


        Map<UUID, Document> documents = Stream.of(Document.builder()
                                .id(UUID.randomUUID())
                                .creationTime(LocalDateTime.now())
                                .discipline(Discipline.INFORMATICS)
                                .title("Recursion")
                                .description("In this material you will find some exercises that you have to solve using recursion.")
                                .documentType(DocumentType.PRACTICE)
                                .build(),
                Document.builder()
                        .id(UUID.randomUUID())
                        .creationTime(LocalDateTime.now())
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
                .creationTime(LocalDateTime.now())
                .discipline(Discipline.CHEMISTRY)
                .title("Visualization of atoms")
                .description("This is a video where you can find a short visualization atoms.")
                .duration(LocalTime.parse("00:12:32", DateTimeFormatter.ISO_TIME))
                .build();

        videoService.addOnlyOne(video);


        Map<UUID, Video> videos = Stream.of(Video.builder()
                                .id(UUID.randomUUID())
                                .creationTime(LocalDateTime.now())
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
                .creationTime(LocalDateTime.now())
                .discipline(Discipline.ENGLISH)
                .title("Past Tenses")
                .description("Here is a test that contains exercises with past tenses.")
                .testType(TestType.EXAM)
                .build();

        testService.addOnlyOne(test);

        Map<UUID, Test> tests = Stream.of(Test.builder()
                                .id(UUID.randomUUID())
                                .creationTime(LocalDateTime.now())
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
    }

    public void demoOnAllMaterials() {

        String intro = "\n\n-----Performing different operations on all kind of materials-----";

        System.out.println(intro);

        materialService.addAllKindOfMaterials();

        System.out.println("For example: we can iterate through the map of all the materials added on the website.");

        materialService.getAllItems().forEach((key, value) -> System.out.println(value.getTitle()));
    }
}
