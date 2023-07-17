package com.harera.hayat.util;

public class SubjectUtils {

    public static Subject getSubject(String subject) {
        if (RegexUtils.isPhoneNumber(subject)) {
            return new Subject.PhoneNumber(subject);
        } else if (RegexUtils.isEmail(subject)) {
            return new Subject.Email(subject);
        } else if (RegexUtils.isUsername(subject)) {
            return new Subject.Username(subject);
        } else {
            return null;
        }
    }

    private SubjectUtils() {
    }
}
