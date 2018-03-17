package server.services;

import java.util.regex.Pattern;

/***
 * Validator for Login and Password
 */
class Validator {
    private static Pattern pattern = Pattern.compile("[a-zA-Z0-9]{3,}");
    private Validator() {
    }

    static boolean isInvalid(String login) {
        return !pattern.matcher(login).matches();
    }
}