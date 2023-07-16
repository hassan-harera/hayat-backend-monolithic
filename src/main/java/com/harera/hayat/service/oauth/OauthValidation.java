package com.harera.hayat.service.oauth;

import static com.harera.hayat.util.ErrorCode.FORMAT_SIGNUP_FIRST_NAME;
import static com.harera.hayat.util.ErrorCode.FORMAT_SIGNUP_LAST_NAME;
import static com.harera.hayat.util.ErrorCode.FORMAT_SIGNUP_MOBILE;
import static com.harera.hayat.util.ErrorCode.INVALID_FIREBASE_TOKEN;
import static com.harera.hayat.util.ErrorCode.MANDATORY_LAST_NAME;
import static com.harera.hayat.util.ErrorCode.MANDATORY_LOGIN_OAUTH_TOKEN;
import static com.harera.hayat.util.ErrorCode.MANDATORY_SIGNUP_FIRST_NAME;
import static com.harera.hayat.util.ErrorCode.MANDATORY_SIGNUP_LAST_NAME;
import static com.harera.hayat.util.ErrorCode.MANDATORY_SIGNUP_MOBILE;
import static com.harera.hayat.util.ErrorCode.MANDATORY_SIGNUP_OAUTH_TOKEN;
import static com.harera.hayat.util.ErrorCode.MANDATORY_SIGNUP_PASSWORD;
import static com.harera.hayat.util.ErrorCode.UNIQUE_SIGNUP_EMAIL;
import static com.harera.hayat.util.ErrorCode.UNIQUE_SIGNUP_MOBILE;
import static com.harera.hayat.util.StringUtils.isValidEmail;
import static com.harera.hayat.util.StringUtils.isValidMobile;
import static com.harera.hayat.util.StringUtils.isValidName;
import static com.harera.hayat.util.StringUtils.isValidPassword;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseToken;
import com.harera.hayat.exception.FieldFormatException;
import com.harera.hayat.exception.LoginException;
import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.exception.SignupException;
import com.harera.hayat.exception.UniqueFieldException;
import com.harera.hayat.util.ErrorCode;
import com.harera.hayat.model.oauth.OauthLoginRequest;
import com.harera.hayat.model.oauth.OauthSignupRequest;
import com.harera.hayat.repository.UserRepository;
import com.harera.hayat.service.firebase.OauthFirebaseService;

@Service
public class OauthValidation {

    private final OauthFirebaseService oauthFirebaseService;
    private final UserRepository userRepository;

    public OauthValidation(OauthFirebaseService firebaseService,
                    UserRepository userRepository) {
        this.oauthFirebaseService = firebaseService;
        this.userRepository = userRepository;
    }

    public void validateLogin(OauthLoginRequest loginRequest) {
        validateMandatory(loginRequest);
        validateOauthToken(loginRequest);
    }

    public void validateSignup(OauthSignupRequest signupRequest) {
        validateMandatory(signupRequest);
        validateFormat(signupRequest);
        validateExisting(signupRequest);
        validateOauthToken(signupRequest);
    }

    private void validateOauthToken(OauthLoginRequest loginRequest) {
        FirebaseToken firebaseToken = oauthFirebaseService
                        .getFirebaseToken(loginRequest.getFirebaseToken());
        if (userRepository.findByUid(firebaseToken.getUid()).isEmpty()) {
            throw new LoginException(INVALID_FIREBASE_TOKEN, "Invalid firebase token");
        }
    }

    private void validateOauthToken(OauthSignupRequest signupRequest) {
        FirebaseToken firebaseToken = oauthFirebaseService
                        .getFirebaseToken(signupRequest.getOauthToken());
        if (userRepository.findByUid(firebaseToken.getUid()).isPresent()) {
            throw new SignupException("user already exists");
        }
    }

    private void validateFormat(OauthSignupRequest signupRequest) {
        if (!isValidMobile(signupRequest.getMobile())) {
            throw new FieldFormatException(FORMAT_SIGNUP_MOBILE,
                            "Incorrect mobile format");
        }
        if (!isValidName(signupRequest.getFirstName())) {
            throw new FieldFormatException(FORMAT_SIGNUP_FIRST_NAME, "first_name",
                            signupRequest.getFirstName());
        }
        if (!isValidName(signupRequest.getLastName())) {
            throw new FieldFormatException(FORMAT_SIGNUP_LAST_NAME, "last_name",
                            signupRequest.getLastName());
        }
        if (!isValidPassword(signupRequest.getPassword())) {
            throw new FieldFormatException(ErrorCode.FORMAT_SIGNUP_PASSWORD, "password",
                            signupRequest.getPassword());
        }
        if (isNotBlank(signupRequest.getEmail())
                        && !isValidEmail(signupRequest.getEmail())) {
            throw new FieldFormatException(ErrorCode.FORMAT_SIGNUP_EMAIL,
                            MANDATORY_LAST_NAME);
        }
    }

    private void validateExisting(OauthSignupRequest signupRequest) {
        if (userRepository.existsByMobile(signupRequest.getMobile())) {
            throw new UniqueFieldException(UNIQUE_SIGNUP_MOBILE, "mobile",
                            signupRequest.getMobile());
        }

        if (isNotEmpty(signupRequest.getEmail())
                        && userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new UniqueFieldException(UNIQUE_SIGNUP_EMAIL, "email",
                            signupRequest.getEmail());
        }
    }

    private void validateMandatory(OauthSignupRequest signupRequest) {
        if (isEmpty(signupRequest.getOauthToken())) {
            throw new MandatoryFieldException(MANDATORY_SIGNUP_OAUTH_TOKEN,
                            "oauth_token");
        }
        if (isEmpty(signupRequest.getMobile())) {
            throw new MandatoryFieldException(MANDATORY_SIGNUP_MOBILE, "mobile");
        }
        if (isEmpty(signupRequest.getPassword())) {
            throw new MandatoryFieldException(MANDATORY_SIGNUP_PASSWORD, "password");
        }
        if (isEmpty(signupRequest.getFirstName())) {
            throw new MandatoryFieldException(MANDATORY_SIGNUP_FIRST_NAME, "first_name");
        }
        if (isEmpty(signupRequest.getLastName())) {
            throw new MandatoryFieldException(MANDATORY_SIGNUP_LAST_NAME, "last_name");
        }
    }

    private void validateMandatory(OauthLoginRequest loginRequest) {
        if (isBlank(loginRequest.getFirebaseToken())) {
            throw new MandatoryFieldException(MANDATORY_LOGIN_OAUTH_TOKEN, "oauth_token");
        }
    }
}
