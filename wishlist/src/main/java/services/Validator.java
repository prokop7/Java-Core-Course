package services;

import java.util.regex.Pattern;

public class Validator {
    private static Pattern pattern = Pattern.compile("[a-zA-Z0-9]{3,}");

    public static boolean isInvalid(String login) {
        return !pattern.matcher(login).matches();
    }
}
