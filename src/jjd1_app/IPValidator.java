package jjd1_app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class IPValidator implements Serializable {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final String IP_REGEX = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";

    private static final Pattern pattern = Pattern.compile(IP_REGEX);

    private static final String USER_MESSAGE = "Enter IP address or 'quit' to exit";

    private Stream<String> streamBlackList;

    private boolean isValidWithRegex(String ipAddress) {
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

    private Stream<String> checkLastModifiedTime() {
        String fileName = "blacklist.txt";
        try {
            Path file = Paths.get(fileName);
            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

            if (!attr.creationTime().equals(attr.lastModifiedTime())) {
                streamBlackList = Files.lines(Paths.get(fileName));
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error! Can`t find the file name or path.");
            LOGGER.log(Level.SEVERE, "Error: ", e);
        } catch (IOException e) {
            System.out.println("Error while inputting or outputting data from file!");
            LOGGER.log(Level.SEVERE, "Error: ", e);
        }

        return streamBlackList;
    }

    private boolean isInBlackList(String ipAddress) {
        return checkLastModifiedTime().anyMatch(line -> line.equals(ipAddress));
    }

    public String checkIpAddress(String ipAddress) {
        if (isValidWithRegex(ipAddress)) {
            if (isInBlackList(ipAddress)) {
                LOGGER.info("Access disallowed");
                return "Access disallowed";
            } else {
                LOGGER.info("Access allowed");
                return "Access allowed";
            }
        } else {
            LOGGER.info("Invalid Ip address");
            return "Invalid Ip address";
        }
    }

    public void runConsole() {
        System.out.println(USER_MESSAGE);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String line = sc.next();
            if (!line.equals("quit")) {
                System.out.println(checkIpAddress(line));
            } else break;
            System.out.println(USER_MESSAGE);
        }

    }

}

