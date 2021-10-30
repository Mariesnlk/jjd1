package jjd1_app;

import java.util.Scanner;

public class Main {

    private static final String USER_MESSAGE = "Enter IP address or 'quit' to exit";

    public static void main(String[] args) {
        runConsole();
    }

    private static void runConsole() {
        System.out.println(USER_MESSAGE);
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()) {
            String line = sc.next();
            if(!line.equals("quit")) {
                IPValidator ipValidator = new IPValidator();
                System.out.println(ipValidator.checkIpAddress(line));
            } else break;
            System.out.println(USER_MESSAGE);
        }

    }
}

