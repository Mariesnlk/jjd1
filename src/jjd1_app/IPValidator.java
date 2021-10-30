package jjd1_app;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class IPValidator implements Serializable {

    private static final String IP_REGEX = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";

    private static final Pattern pattern = Pattern.compile(IP_REGEX);

    private boolean isValidWithRegex(String ipAddress) {
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

    private boolean isInBlackList(String ipAddress) {
        try (Stream<String> stream = Files.lines(Paths.get("blacklist.txt"))) {
            return stream.anyMatch(line -> line.equals(ipAddress));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String checkIpAddress(String ipAddress) {
        if (isValidWithRegex(ipAddress))
            return isInBlackList(ipAddress) ? "Access disallowed" : "Access allowed";
        else return "Invalid Ip address";
    }

}

