package ro.pao;

import ro.pao.application.Menu;
import ro.pao.model.users.abstracts.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Menu menu = Menu.getInstance();

            menu.demoOnDocument();

            menu.demoOnVideo();

            menu.demoOnTest();

            if ("exit".equals(scanner.next())) {
                break;
            }
        }
    }
}