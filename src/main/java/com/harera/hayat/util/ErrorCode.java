package com.harera.hayat.util;

public final class ErrorCode {

    public static final String MANDATORY_NEED_TITLE = "need_001";
    public static final String MANDATORY_NEED_COMMUNICATION_METHOD = "need_002";
    public static final String MANDATORY_NEED_CITY_ID = "need_003";
    public static final String FORMAT_NEED_TITLE = "need_004";

    public static final String MANDATORY_POSSESSION_NEED_CATEGORY_ID =
            "possession_need_001";
    public static final String MANDATORY_POSSESSION_NEED_CONDITION_ID =
            "possession_need_002";

    public static final String MANDATORY_MEDICINE_NEED_MEDICINE_ID = "medicine_need_001";
    public static final String MANDATORY_MEDICINE_NEED_MEDICINE_UNIT_ID =
            "medicine_need_002";
    public static final String MANDATORY_MEDICINE_NEED_QUANTITY = "medicine_need_003";
    public static final String FORMAT_MEDICINE_NEED_QUANTITY = "medicine_need_004";


    public static final String MANDATORY_SUBJECT = "subject_001";

    public static final String MANDATORY_UID = "uid_001";

    public static final String MANDATORY_FIRST_NAME = "first_name_001";
    public static final String FORMAT_FIRST_NAME = "first_name_002";

    public static final String MANDATORY_LAST_NAME = "last_name_001";
    public static final String FORMAT_LAST_NAME = "last_name_002";

    public static final String MANDATORY_LOGIN_OAUTH_TOKEN = "token_001";

    public static final String MANDATORY_LOGIN_SUBJECT = "username_001";

    public static final String UNIQUE_USER_NAME = "username_002";

    public static final String MANDATORY_LOGIN_PASSWORD = "password_001";

    public static final String NOT_FOUND_USERNAME_OR_PASSWORD = "login_001";

    public static final String INCORRECT_USERNAME_FORMAT = "login_002";
    public static final String UNIQUE_EMAIL = "email_001";

    /**
     * Mobile
     */
    public static final String UNIQUE_USER_MOBILE = "mobile_001";
    public static final String FORMAT_USER_MOBILE = "mobile_002";
    public static final String MANDATORY_USER_MOBILE = "mobile_003";

    public static final String FORMAT_USER_NAME_MINIMUM = "username_002";
    public static final String FORMAT_USER_NAME_LENGTH = "username_003";
    public static final String FORMAT_USER_NAME_INVALID_CHARS = "username_004";
    public static final String FORMAT_USER_PASSWORD = "password_002";
    public static final String NOT_FOUND_CITY = "city_001";
    public static final String NOT_FOUND_STATE_ID = "state_001";
    public static final String MANDATORY_MEDICINE_UNIT_ID = "medicine_unit_001";
    public static final String MANDATORY_CITY_ID = "city_002";
    public static final String FORMAT_EXPIRATION_DATE = "expiration_date_002";
    public static final String FORMAT_UNIT = "unit_001";
    public static final String MANDATORY_COMMUNICATION = "communication_001";

    public static final String NOT_FOUND_DONATION_UNIT = "donation_001";
    public static final String NOT_FOUND_DONATION_CITY = "donation_002";
    public static final String NOT_FOUND_DONATION_MEDICINE = "donation_003";

    public static final String MANDATORY_DONATION_COMMUNICATION_METHOD = "donation_004";
    public static final String MANDATORY_DONATION_EXPIRATION_DATE = "donation_005";
    public static final String MANDATORY_DONATION_TITLE = "donation_006";
    public static final String MANDATORY_DONATION_AMOUNT = "donation_007";
    public static final String MANDATORY_DONATION_DATE = "donation_011";

    public static final String FORMAT_DONATION_TITLE = "donation_008";
    public static final String FORMAT_DONATION_EXPIRATION_DATE = "donation_009";
    public static final String FORMAT_DONATION_AMOUNT = "donation_010";

    public static final String MANDATORY_FOOD_DONATION_AMOUNT = "food_donation_001";
    public static final String MANDATORY_FOOD_DONATION_FOOD_EXPIRATION_DATE =
                    "food_donation_002";
    public static final String MANDATORY_FOOD_DONATION_UNIT = "food_donation_003";

    public static final String FORMAT_FOOD_DONATION_AMOUNT = "food_donation_004";
    public static final String FORMAT_LOGIN_SUBJECT = "login_003";
    public static final String INVALID_FIREBASE_UID = "auth_001";
    public static final String NOT_FOUND_PROPERTY_DONATION = "property_donation_013";
    public static final String MANDATORY_DONATION_CITY_ID = "donation_012";

    public static final String MANDATORY_PROPERTY_DONATION_ROOMS =
                    "property_donation_001";
    public static final String MANDATORY_PROPERTY_DONATION_BATH_ROOMS =
                    "property_donation_002";
    public static final String MANDATORY_PROPERTY_DONATION_KITCHENS =
                    "property_donation_003";
    public static final String MANDATORY_PROPERTY_DONATION_PEOPLE_CAPACITY =
                    "property_donation_004";
    public static final String MANDATORY_PROPERTY_DONATION_AVAILABLE_FROM =
                    "property_donation_005";
    public static final String MANDATORY_PROPERTY_DONATION_AVAILABLE_TO =
                    "property_donation_006";
    public static final String FORMAT_PROPERTY_DONATION_PEOPLE_CAPACITY =
                    "property_donation_007";
    public static final String FORMAT_PROPERTY_DONATION_ROOMS = "property_donation_008";
    public static final String FORMAT_PROPERTY_DONATION_BATH_ROOMS =
                    "property_donation_009";
    public static final String FORMAT_PROPERTY_DONATION_KITCHENS =
                    "property_donation_010";
    public static final String FORMAT_PROPERTY_DONATION_AVAILABLE_FROM =
                    "property_donation_011";
    public static final String FORMAT_PROPERTY_DONATION_AVAILABLE_TO =
                    "property_donation_012";

    public static final String INVALID_LOGIN_CREDENTIALS = "login_004";
    public static final String INVALID_OTP = "otp_001";
    public static final String EXPIRED_OTP = "otp_002";

    public static final String MANDATORY_MEDICINE_DONATION_AMOUNT =
                    "medicine_donation_001";
    public static final String MANDATORY_MEDICINE_DONATION_MEDICINE_EXPIRATION_DATE =
                    "medicine_donation_002";
    public static final String MANDATORY_MEDICINE_DONATION_MEDICINE =
                    "medicine_donation_003";
    public static final String MANDATORY_MEDICINE_DONATION_UNIT = "medicine_donation_004";
    public static final String FORMAT_MEDICINE_DONATION_AMOUNT = "medicine_donation_005";
    public static final String FORMAT_MEDICINE_DONATION_EXPIRATION_DATE =
                    "medicine_donation_006";

    public static final String NOT_FOUND_MEDICINE_UNIT = "medicine_unit_002";
    public static final String NOT_FOUND_MEDICINE = "medicine_001";
    public static final String NOT_FOUND_FOOD_DONATION = "food_donation_005";

    public static final String MANDATORY_BOOK_DONATION_TITLE = "book_donation_001";
    public static final String FORMAT_BOOK_DONATION_TITLE = "book_donation_002";
    public static final String MANDATORY_BOOK_DONATION_AMOUNT = "book_donation_003";
    public static final String FORMAT_BOOK_DONATION_AMOUNT = "book_donation_004";

    public static final String NOT_FOUND_CLOTHING_CONDITION = "clothing_condition_001";
    public static final String FORMAT_CLOTHING_DONATION_QUANTITY =
                    "clothing_donation_001";
    public static final String MANDATORY_CLOTHING_DONATION_QUANTITY =
                    "clothing_donation_002";
    public static final String MANDATORY_CLOTHING_DONATION_CONDITION =
                    "clothing_donation_003";
    public static final String NOT_FOUND_CLOTHING_DONATION = "clothing_donation_004";

    public static final String MANDATORY_RESET_PASSWORD_MOBILE = "reset_password_001";
    public static final String MANDATORY_RESET_PASSWORD_OTP = "reset_password_002";
    public static final String MANDATORY_RESET_PASSWORD_NEW_PASSWORD =
                    "reset_password_003";
    public static final String FORMAT_RESET_PASSWORD_NEW_PASSWORD = "reset_password_004";
    public static final String FORMAT_RESET_PASSWORD_MOBILE = "reset_password_005";
    public static final String FORMAT_RESET_PASSWORD_OTP = "reset_password_006";
    public static final String NOT_FOUND_RESET_PASSWORD_MOBILE = "reset_password_007";

    public static final String MANDATORY_SIGNUP_OAUTH_TOKEN = "signup_001";
    public static final String MANDATORY_SIGNUP_MOBILE = "signup_002";
    public static final String MANDATORY_SIGNUP_FIRST_NAME = "signup_003";
    public static final String MANDATORY_SIGNUP_OTP = "signup_004";
    public static final String FORMAT_SIGNUP_OTP = "signup_005";
    public static final String MANDATORY_SIGNUP_PASSWORD = "signup_006";
    public static final String MANDATORY_SIGNUP_LAST_NAME = "signup_007";
    public static final String FORMAT_SIGNUP_EMAIL = "signup_008";
    public static final String FORMAT_SIGNUP_PASSWORD = "signup_009";
    public static final String INVALID_FIREBASE_TOKEN = "signup_010";
    public static final String FORMAT_SIGNUP_MOBILE = "signup_011";
    public static final String FORMAT_SIGNUP_FIRST_NAME = "signup_012";
    public static final String FORMAT_SIGNUP_LAST_NAME = "signup_013";
    public static final String UNIQUE_SIGNUP_MOBILE = "signup_014";
    public static final String UNIQUE_SIGNUP_EMAIL = "signup_015";

    private ErrorCode() {
    }
}
