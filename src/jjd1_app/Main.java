package jjd1_app;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        IPValidator ipValidator = new IPValidator();
        try {
            MyLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }
        ipValidator.runConsole();
    }

}

