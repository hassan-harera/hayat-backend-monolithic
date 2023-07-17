package com.harera.hayat.util;

public class Subject {

    public static final class Email extends Subject {
        private final String email;

        public Email(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }
    }

    public static final class PhoneNumber extends Subject {
        private final String phoneNumber;

        public PhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }
    }

    public static final class Username extends Subject {
        private final String username;

        public Username(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }

    private Subject() {
    }
}
