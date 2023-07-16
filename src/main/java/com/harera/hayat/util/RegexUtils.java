package com.harera.hayat.util;

public class RegexUtils {

    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{3,16}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String MOBILE_REGEX = "^[0-9]{10}$";
    private static final String PASSWORD_REGEX =
                    "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

    public static boolean isUsername(String subject) {
        return subject.matches(USERNAME_REGEX);
    }

    public static boolean isEmail(String subject) {
        return subject.matches(EMAIL_REGEX);
    }

    public static boolean isPhoneNumber(String subject) {
        return subject.matches(MOBILE_REGEX);
    }

    public static boolean isValidPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }
}
