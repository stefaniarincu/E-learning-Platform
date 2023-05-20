package ro.pao;

import ro.pao.application.Menu;
import ro.pao.gateways.Requests;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Requests requests = new Requests();
        requests.saveRequestInfo();

        Menu menu = Menu.getInstance();
        menu.demoOnTeachers();

/*
            menu.demoOnDocument();

            menu.demoOnVideo();

            menu.demoOnTest();

            menu.demoOnAllMaterials();

            menu.demoOnStudents();



            menu.demoOnAllUsers();

            if ("exit".equals(scanner.next())) {
                break;
            }
        }*/
    }
}