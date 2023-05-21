package ro.pao;

import ro.pao.application.Menu;

public class Main {
    public static void main(String[] args) {
        Menu menu = Menu.getInstance();

        menu.demoOnGateway();

        menu.demoOnInsertingUsers();

        menu.demoOnInsertingCourses();

        menu.demoOnInsertingGrades();

        menu.demoOnEnrollingStudents();

        menu.demoOnIterator();

        menu.demoOnStrategyDesignPattern();

        menu.demoOnThreads();

        menu.demoOnLoggingErrors();

        menu.demo();
    }
}