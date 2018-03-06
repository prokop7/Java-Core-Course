package services;

import java.util.regex.Pattern;

class Validator {
    private static Pattern pattern = Pattern.compile("[a-zA-Z0-9]{3,}");

    static boolean isInvalid(String login) {
        return !pattern.matcher(login).matches();
    }
}
